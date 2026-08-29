# ByteBeat

**API REST de catálogo musical, favoritos e recomendação por gênero.**

*Spring Boot · Spring Data JPA · MySQL · Bean Validation · springdoc-openapi*

---

## Sobre o projeto

ByteBeat é uma API RESTful para gerenciar um catálogo de músicas, permitir
que usuários favoritem faixas e receber recomendações simples baseadas nos
gêneros que já favoritaram. O projeto nasceu como exercício de fundamentos
de Spring Boot e evoluiu para uma arquitetura em camadas (Controller →
Service → Repository), com validação de entrada, tratamento de erros
centralizado e um front-end estático simples para demonstração.

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.5 |
| Persistência | Spring Data JPA / Hibernate |
| Banco de dados | MySQL |
| Validação | Bean Validation (Jakarta) |
| Documentação | springdoc-openapi (Swagger UI) |
| Build | Maven |

## Arquitetura

```
Controller  ->  Service  ->  Repository  ->  MySQL
   |              |
   |              +-- regras de negocio, filtros, recomendacao
   +-- HTTP: status codes, request/response
```

Erros de validação (`400`) e recursos não encontrados (`404`) são tratados
de forma centralizada por um `@RestControllerAdvice`, retornando um JSON
enxuto em vez da resposta padrão verbosa do Spring.

## Como rodar localmente

### Pré-requisitos

- Java 17+
- MySQL rodando localmente, com um schema chamado `music_store` criado
- Maven (ou use o wrapper `./mvnw` incluído no projeto)

### Configuração

O projeto lê a senha do banco a partir de uma variável de ambiente — nunca
hardcoded:

```bash
# Linux / macOS
export DB_PASSWORD=sua_senha_aqui
```

```powershell
# Windows (PowerShell)
$env:DB_PASSWORD="sua_senha_aqui"
```

No IntelliJ, configure o mesmo valor em *Run Configurations → Environment
variables*.

### Executando

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`. A landing page está em
`http://localhost:8080/index.html`, e a documentação interativa da API em
`http://localhost:8080/swagger-ui/index.html`.

## Endpoints

### Músicas

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/songs` | Cria uma música |
| `GET` | `/songs` | Lista todas as músicas |
| `GET` | `/songs/{id}` | Busca uma música por id |
| `PUT` | `/songs/{id}` | Atualiza uma música |
| `DELETE` | `/songs/{id}` | Remove uma música |
| `GET` | `/songs/search?artist=&title=&genre=` | Busca combinável, todos os filtros opcionais |

### Usuários e favoritos

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/users` | Cria um usuário |
| `GET` | `/users` | Lista todos os usuários |
| `GET` | `/users/{id}` | Busca um usuário por id |
| `PUT` | `/users/{id}` | Atualiza um usuário |
| `DELETE` | `/users/{id}` | Remove um usuário |
| `POST` | `/users/{userId}/favorites/{songId}` | Favorita uma música |
| `DELETE` | `/users/{userId}/favorites/{songId}` | Remove uma música dos favoritos |
| `GET` | `/users/{userId}/recommendations` | Recomenda músicas dos gêneros favoritados, excluindo as já favoritadas |

### Formato de erros

```jsonc
// 400 - validacao
{ "title": "Uma música deve ter um título" }

// 404 - recurso nao encontrado
{ "message": "Usuário não encontrado" }
```

## Modelo de dados

```
Song                      User
- id                      - id
- title *                 - name *
- artist *                - email *
- album                   - favoriteSongs (ManyToMany com Song,
- genre                     tabela intermediaria user_favorites)
- releaseYear
- coverUrl
```

`*` campo obrigatório.

## Roadmap

- [x] CRUD de músicas com validação e tratamento de erros
- [x] Busca combinável por artista, título e gênero
- [x] Arquitetura em camadas (Service)
- [x] Usuários, favoritos e recomendação por gênero
- [ ] Integração com IA para recomendação
- [ ] Testes automatizados mais abrangentes

---

ByteBeat © 2026 — Thiago Martins