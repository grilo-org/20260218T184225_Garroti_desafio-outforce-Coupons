# Coupons API --- Desafio Técnico Outforce (Pleno)

## 🚀 Tecnologias Utilizadas

-   Java 21
-   Spring Boot 4
-   Spring Data JPA
-   H2 Database (in-memory)
-   JUnit 5
-   Springdoc OpenAPI (Swagger)
-   Docker
-   Docker Compose

------------------------------------------------------------------------

## 📦 Como Executar com Docker (Recomendado)

### 1️⃣ Build e start da aplicação

Na raiz do projeto:

``` bash
docker compose up --build
```

A aplicação ficará disponível em:

    http://localhost:8080

### 2️⃣ Para parar os containers

``` bash
docker compose down
```

------------------------------------------------------------------------

## 📄 Documentação Swagger

Após subir a aplicação:

Swagger UI:

    http://localhost:8080/swagger-ui/index.html

OpenAPI JSON:

    http://localhost:8080/v3/api-docs

------------------------------------------------------------------------

## 🗄 H2 Console

URL:

    http://localhost:8080/h2-console

Credenciais:

-   JDBC URL:

```{=html}
<!-- -->
```
    jdbc:h2:mem:outforce

-   Username:

```{=html}
<!-- -->
```
    sa

-   Password: (vazio)

------------------------------------------------------------------------

## 🧪 Como Executar os Testes

### Executar todos os testes

``` bash
./mvnw test
```

Windows (PowerShell):

``` powershell
.\mvnw test
```

------------------------------------------------------------------------

## ▶️ Executar sem Docker (opcional)

Caso queira rodar localmente:

``` bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

    http://localhost:8080

------------------------------------------------------------------------

## 📌 Endpoints Principais

### Criar Cupom

**POST** `/coupons`

Exemplo:

``` bash
curl -X POST http://localhost:8080/coupons \
-H "Content-Type: application/json" \
-d '{
  "code": "AB-12@C3",
  "description": "Cupom de desconto",
  "discountValue": 10.5,
  "expirationDate": "2026-12-31",
  "published": true
}'
```

⚠️ O campo `code` é sanitizado automaticamente (caracteres especiais são
removidos).

------------------------------------------------------------------------

### Deletar Cupom (Soft Delete)

**DELETE** `/coupons/{{id}}`

``` bash
curl -X DELETE http://localhost:8080/coupons/{{id}}
```

------------------------------------------------------------------------

## 📚 Regras de Negócio Implementadas

-   Code obrigatório, alfanumérico com 6 caracteres
-   Caracteres especiais são removidos antes de salvar
-   Discount mínimo: 0.5
-   ExpirationDate não pode estar no passado
-   Soft delete (deletedAt)
-   Não é possível deletar um cupom já deletado

------------------------------------------------------------------------

## 🧠 Arquitetura

As regras de negócio estão encapsuladas em objetos de domínio:

-   `Coupon`
-   `CouponCode`
-   `DiscountValue`

Separação clara entre:

-   Camada de API
-   Application Service
-   Domínio
-   Infraestrutura (JPA)
