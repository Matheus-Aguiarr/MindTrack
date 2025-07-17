# 🧠 MindTrack API

MindTrack é uma API RESTful desenvolvida em Java com Spring Boot para auxiliar estudantes a organizarem seus estudos por meio da criação de matérias, metas de estudo e anotações.

---

## 🚀 Como rodar o projeto localmente

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL
- IDE (IntelliJ, VSCode ou outra de sua preferência)

### Passos

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/matheus-aguiarr/mindtrack.git
   ```

2. **Configure o banco de dados PostgreSQL:**
   - Crie um banco chamado `mindtrack`.
   - Atualize as credenciais do banco no arquivo `src/main/resources/application.properties`.

3. **Rode as migrations do Flyway:**
   - As migrations estão no diretório `resources/db.migration`.

4. **Compile e execute o projeto:**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Acesse a API:**
   ```
   http://localhost:8080
   ```

---

## 🧱 Estrutura do Projeto

```
com.api.mindtrack
├── controller         # Endpoints HTTP
├── domain             # Models, DTOs e Repositórios
│   ├── note
│   ├── studygoal
│   ├── subject
│   └── user
├── infra
│   ├── exceptions     # Tratamento de erros
│   └── security       # Autenticação e JWT
├── service            # Regras de negócio
├── resources
│   └── db.migration   # Migrations Flyway
```

---

## 🔐 Autenticação

- Todas as rotas (exceto `/auth/login` e `/auth/register`) exigem **token JWT** no header da requisição:

```http
Authorization: Bearer <seu_token_aqui>
```

---

## 📡 Endpoints da API

### 🔑 AuthController `/auth`

| Método | Rota               | Descrição                              |
|--------|--------------------|----------------------------------------|
| POST   | `/login`           | Autentica o usuário e retorna um token |
| POST   | `/register`        | Cadastra um novo usuário               |
| GET    | `/user/profile`    | Retorna dados do usuário autenticado   |

---

### 🎯 GoalController `/goal`

| Método  | Rota                      | Descrição                                  |
|---------|---------------------------|--------------------------------------------|
| POST    | `/`                       | Cria uma nova meta de estudo               |
| PUT     | `/done/{goalId}`          | Marca uma meta como concluída              |
| PATCH   | `/{goalId}`               | Edita uma meta existente                   |
| GET     | `/?subjectId={id}`        | Lista metas vinculadas a uma matéria       |
| DELETE  | `/{goalId}`               | Deleta uma meta por ID                     |

---

### 📝 NoteController `/note`

| Método  | Rota                         | Descrição                                  |
|---------|------------------------------|--------------------------------------------|
| POST    | `/`                          | Cria uma nova anotação                     |
| GET     | `/subject/{subjectId}`       | Lista anotações por matéria                |
| GET     | `/{noteId}`                  | Retorna uma anotação específica            |
| PUT     | `/{noteId}`                  | Edita uma anotação existente               |
| DELETE  | `/{noteId}`                  | Deleta uma anotação por ID                 |

---

### 📚 SubjectController `/subject`

| Método  | Rota                      | Descrição                                  |
|---------|---------------------------|--------------------------------------------|
| POST    | `/`                       | Cria uma nova matéria                      |
| GET     | `/user`                   | Lista matérias do usuário autenticado      |
| GET     | `/{subjectId}`            | Retorna uma matéria específica             |
| PUT     | `/{subjectId}`            | Edita os dados de uma matéria              |
| DELETE  | `/{subjectId}`            | Deleta uma matéria por ID                  |

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Security + JWT
- JPA + Hibernate
- PostgreSQL
- Flyway (migrations)
- Lombok
- Maven

---

## 🧪 Testes

Use ferramentas como Postman ou Insomnia para testar os endpoints.

Lembre-se de adicionar o token JWT no header das rotas protegidas:

```
Authorization: Bearer <token>
```
---
Front-End do projeto: [MindTrack](https://github.com/Matheus-Aguiarr/mindtrack-frontend)
Deploy: [MindTrack-Web](https://mindtrack-study.vercel.app/)
Feito por Matheus Aguiar - [Portfolio](https://matheusaguiar.vercel.app)
