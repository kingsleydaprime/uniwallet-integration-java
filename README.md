Fair. Here's a leaner version:

```markdown
# Uniwallet Integration API

Spring Boot REST API integrating with ITC's Uniwallet payment platform.

## Setup

1. Create `src/main/resources/application-local.yml`:
   ```yaml
   uniwallet:
     base-url: https://uniwalletsandbox.transflowitc.com
     api-key: your-api-key
     country: GH
     transflow-id: your-transflow-id
     product-id: your-product-id
   ```

2. Run:
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
   ```

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/wallet/name-enquiry` | Name lookup |
| POST | `/api/wallet/debit` | Debit customer |
| POST | `/api/wallet/credit` | Credit customer |
| GET | `/api/wallet/status/{refNo}` | Transaction status |
| POST | `/api/wallet/provision-sandbox` | Provision sandbox |
| POST | `/api/callback` | Receive callbacks |

## Stack
Java 21 · Spring Boot · Maven · Lombok
```
