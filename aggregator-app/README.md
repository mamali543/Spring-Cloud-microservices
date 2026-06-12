# Microservices — Spring Cloud (Exercise 00: So many services)

A Spring Cloud aggregator system. For a given country, it returns a single JSON object
combining **country information** (from [restcountries.com](https://restcountries.com))
and **COVID-19 statistics** (from [covid-api.com](https://covid-api.com/api)).

The two data services stay independent; the aggregator merges their responses. The only
public entry point is the API Gateway on port **80**.

## Architecture

```
                 ┌─────────────┐
 user request ──▶│ API Gateway │  :80  (only published port)
                 └──────┬──────┘
                        │ lb://aggregator-service   (resolved via Eureka)
                        ▼
                 ┌──────────────────┐        ┌─────────────┐
                 │ Aggregator       │◀──────▶│   Eureka    │ :8761
                 │ (Feign by name)  │        │  Registry   │
                 └───────┬──────────┘        └─────────────┘
            ┌────────────┴────────────┐
            ▼                          ▼
   ┌──────────────────┐      ┌──────────────────┐
   │ countries-service│      │  covid-service   │
   │  → restcountries │      │  → covid-api.com │
   └──────────────────┘      └──────────────────┘
```

## Services

| Module             | Port            | Role                                                        |
|--------------------|-----------------|-------------------------------------------------------------|
| `eureka-service`   | 8761 (fixed)    | Service discovery / registry (Netflix Eureka)               |
| `api-gateway`      | 80 (fixed)      | Single public entry point; routes to the aggregator         |
| `countries-service`| random          | Proxy to restcountries.com                                  |
| `covid-service`    | random          | Proxy to covid-api.com                                      |
| `aggregator-service`| random         | Calls both services via Feign and merges into `CombinedData`|
| `common-dtos`      | —               | Shared DTOs (`CountryData`, `CovidStats`, `CombinedData`)   |

Only Eureka (8761) and the API Gateway (80) use fixed ports. Every other service binds a
**random port at each startup** (`server.port: 0`) and is reached only by its registered
name through Eureka — the aggregator has no hostname/port links to the data services.

No service has its own database; all data is fetched live from the third-party APIs.

## Endpoints

| Service     | URI                                                  |
|-------------|------------------------------------------------------|
| Countries   | `GET /countries-management/countries/{name}`         |
| Covid       | `GET /covid-management/countries/{iso}`              |
| Aggregator  | `GET /information-management/countries/{name}`       |
| **Public**  | `GET http://localhost/information-management/countries/{name}` (via gateway) |

Example:

```bash
curl http://localhost/information-management/countries/MA
```

## Running with Docker

Requirements: Docker + Docker Compose.

```bash
cd aggregator-app
docker compose up --build
```

This builds and starts all five services on the `app-network` bridge network. Startup order
is handled via `depends_on` (the data services wait for Eureka to be healthy). Give the
services a few seconds to register with Eureka before sending requests.

- Eureka dashboard: <http://localhost:8761>
- Public API: <http://localhost/information-management/countries/MA>

Stop everything:

```bash
docker compose down
```

Because the data services use random ports and are not published to the host, they are
**not** directly reachable from your machine — that is intentional and required by the
subject. Reach them only through the gateway/aggregator.

## Third-party APIs used

### restcountries.com — used by `countries-service`

> ⚠️ **Upstream status (as of 2026-06-12):** the free `restcountries.com` API has been
> **deprecated and sunset**. Every `v3.1` (and `v5`) endpoint now responds with `301`/a
> `{"success": false, ... "deprecated"}` notice instead of country data, pointing to
> <https://restcountries.com/docs/legacy-api-deprecation>. As a result the
> `/information-management/countries/{name}` endpoint currently returns HTTP 500 from
> `countries-service` (it cannot parse the deprecation HTML/JSON into `CountryData`).
> This is an **external provider change**, not a defect in this project — the code was
> correct against the API as documented below. To restore live data, migrate
> `countries-service` to a still-available source (e.g. the World Bank API
> `api.worldbank.org` or `countriesnow.space`). `covid-service` / covid-api.com is unaffected.

- **Base URL:** `https://restcountries.com/v3.1`
- **Method:** `GET /name/{name}?fields=name,capital,population`
- **Purpose:** look up a country by (partial) name and return its name, capital and population.
- **Response:** a JSON **array** of countries; the service takes the first element.
- **Example:** `GET https://restcountries.com/v3.1/name/Morocco?fields=name,capital,population`

```json
[
  {
    "name": { "common": "Morocco", "official": "Kingdom of Morocco" },
    "capital": ["Rabat"],
    "population": 37457971
  }
]
```

### covid-api.com — used by `covid-service`

- **Base URL:** `https://covid-api.com/api`
- **Method:** `GET /reports?iso={iso}`
- **Purpose:** return COVID-19 reports for a country, identified by its **ISO code** (e.g. `MAR`, `FRA`, `USA`).
- **Response:** a JSON object with a `data` array of per-region reports.
- **Example:** `GET https://covid-api.com/api/reports?iso=MAR`

```json
{
  "data": [
    {
      "date": "2022-04-16",
      "confirmed": 1164760,
      "deaths": 16070,
      "recovered": 1112233,
      "active": 36457,
      "fatality_rate": 0.0138,
      "region": { "iso": "MAR", "name": "Morocco" }
    }
  ]
}
```

> Note on identifiers: `countries-service` accepts a country **name** (e.g. `Morocco`),
> while `covid-api.com` expects an **ISO** code. When testing the combined endpoint, use a
> value that both APIs understand for the given service.

## Notes on the subject requirements

- **Random ports** — all services except Eureka and the gateway start on a random port and
  are discovered by name (`server.port: 0` + a UUID-based Eureka `instance-id`).
- **No database** — none of the services persist data. The `data.sql` file required by
  Chapter III is included in each module but, since there is no datasource, Spring Boot does
  not execute it; it documents the sample test data instead. This is a known tension between
  the "no database" requirement and the "each project shall contain a data.sql" rule.
- **Single entry point** — only the API Gateway (port 80) is published to the host.

## Tech stack

- Java 21, Spring Boot 3.2.x, Spring Cloud 2023.0.x
- Netflix Eureka (discovery), Spring Cloud Gateway (edge), OpenFeign (internal calls),
  WebClient (external calls), Lombok, Maven (multi-module), Docker Compose


how to test since the covid and country services ports are random:
cd aggregator-app
PORT=$(curl -s -H "Accept: application/json" http://localhost:8761/eureka/apps/COVID-SERVICE \
  | python3 -c "import sys,json;i=json.load(sys.stdin)['application']['instance'];i=i[0] if isinstance(i,list) else i;print(i['port']['\$'])")
docker compose exec -T covid-service curl -s "http://localhost:$PORT/covid-management/countries/USA"
