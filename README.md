# ShoppingBasket

## Visão Geral

O ShoppingBasket é uma aplicação Java Spring Boot para gerenciamento de cestas de compras, produtos e métodos de pagamento. O sistema permite criar cestas, adicionar/remover produtos, consultar produtos e realizar pagamentos, integrando-se a um serviço externo de produtos (PlatziStore).

## Funcionalidades
- Cadastro e consulta de produtos
- Criação e gerenciamento de cestas de compras
- Adição e remoção de produtos na cesta
- Processamento de pagamento
- Integração com serviço externo de produtos
- Tratamento centralizado de exceções

## Arquitetura
- **Spring Boot**: Framework principal
- **Feign Client**: Integração com serviço externo (PlatziStore)
- **JPA/Hibernate**: Persistência de dados
- **Camadas**: Controller, Service, Repository, Entity, Exception

## Estrutura de Pastas
```
src/main/java/dev/eltoncosta/ShoppingBasket/
  ├── client/           # Integração com serviços externos
  ├── controller/       # Controllers REST
  ├── entity/           # Entidades JPA
  ├── exceptions/       # Tratamento de exceções
  ├── repository/       # Repositórios JPA
  └── service/          # Lógica de negócio
```

## Configuração

### Pré-requisitos
- Java 17+
- Maven 3.8+
- Docker (opcional, para banco de dados)

### Variáveis de Ambiente
Configure o arquivo `src/main/resources/application.yml` conforme necessário para banco de dados e URLs externas.

### Subindo o Banco de Dados (opcional)
Se desejar usar Docker:
```bash
docker-compose up -d
```

### Build e Execução
```bash
./mvnw clean install
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## Exemplos de Uso da API

### 1. Listar Produtos
`GET /products`

### 2. Criar Cesta
`POST /baskets`
```json
{
  "customerId": 1
}
```

### 3. Adicionar Produto à Cesta
`POST /baskets/{basketId}/products`
```json
{
  "productId": 123,
  "quantity": 2
}
```

### 4. Realizar Pagamento
`POST /baskets/{basketId}/payment`
```json
{
  "paymentMethod": "CREDIT_CARD"
}
```

## Testes
Para rodar os testes automatizados:
```bash
./mvnw test
```

## Contribuição
Pull requests são bem-vindos! Para grandes mudanças, abra uma issue primeiro para discutir o que você gostaria de mudar.

## Licença
Este projeto está sob a licença MIT.

