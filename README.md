# 🍽️ Restaurantes API

Este projeto implementa uma **API RESTful** para a gestão de restaurantes e seus respectivos pratos, seguindo os princípios da **Clean Architecture**. Ele permite operações completas de **CRUD** tanto para restaurantes quanto para os pratos associados a cada um deles.

---

## 📌 Funcionalidades

- Cadastro, consulta, atualização e exclusão de **restaurantes**;
- Gerenciamento de **pratos** por restaurante:
  - Criação, listagem, busca e atualização de pratos específicos.

---

## 🧠 Arquitetura

O projeto foi estruturado com base na **Clean Architecture**, garantindo:
- **Separação de responsabilidades** entre camadas;
- Independência de frameworks e tecnologias externas;
- Facilitação para manutenção, testes e evolução do sistema.

### 📂 Camadas

- `controllers`: Entrada da aplicação (HTTP);
- `usecases`: Lógica de negócio;
- `gateways`: Interfaces com fontes externas (banco de dados);
- `mappers`: Conversão entre entidades e DTOs;
- `presenters`: Preparação de respostas;
- `entities`: Regras de negócio puras.

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos
- Docker e Docker Compose instalados

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/4ndro0idj5/restaurantes-api
   cd restaurantes-api
   ```

2. Execute a aplicação com Docker Compose:
   ```bash
   docker-compose up
   ```

3. Acesse os serviços:
    - API de Restaurantes (Swagger UI): http://localhost:8080/swagger-ui/index.html
    - API de Usuários (Swagger UI): http://localhost:8081/swagger-ui/index.html

---

## 🧪 Testes

- **Testes Unitários**: `@WebMvcTest` com `Mockito`
- **Testes de Integração**: `@SpringBootTest` com banco H2
- **Ambiente isolado**: via `application-test.yml`
- **Integração com API de Usuários**: usando Docker Compose



## 📦 Coleções para Teste (Postman)

- Acesse a Collection do Postman com todos os endpoints prontos para teste:
  👉 [Collection Postman - Restaurantes API](https://www.postman.com/api-team-5046/workspace/restaurantes-api/collection/41367352-fa4d9251-a533-4ed9-b9c8-0bc03328acc9?action=share&source=copy-link&creator=41367352)

---

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Web / Spring Data JPA / Bean Validation
- PostgreSQL
- H2 Database (para testes)
- Docker e Docker Compose
- JaCoCo
- Swagger (springdoc-openapi) para documentação da API
- JUnit / Mockito

---

## 🙋‍♂️ Autor

**Anderson Argollo**  
[LinkedIn](www.linkedin.com/in/andersonlargollo) 

---

