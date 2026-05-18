# 📚 Bookstore API

A RESTful API for managing books with authentication using Node.js, Express, and JWT.

---

## 🚀 Features

- User Registration & Login (JWT)
- CRUD operations for books
- Mark books as Read / Unread
- Filter books by status
- Swagger API documentation
- Validation & error handling
- Search books by title

---

## 📁 Project Structure

src/
  configs/
  controllers/
  middlewares/
  models/
  routes/
  services/
  app.js

---

## ⚙️ Setup

```bash
git clone <repo>
cd bookstore-api
npm install
npm run dev
```

Create `.env` file:

```env
PORT=5000
# Postgres db connections
DB_HOST=localhost
DB_NAME=bookstore
DB_USER=postgres
DB_PASSWORD=root
DB_DIALECT=postgres
# JWT auth
JWT_SECRET=super_secret_key
```

---

## 🔐 Authentication

Use JWT token:

Authorization: Bearer <token>

---

## 📘 APIs

### Users

POST /users/register
POST /users/login

### Books (Protected)

POST /books
GET /books
GET /books/:id
PATCH /books/:id/status
DELETE /books/:id
GET /books/search/:title

---

## 📊 Book Fields

- title
- authorName
- description
- status (READ / UNREAD)
- date

---

## 🔍 Filtering

GET /books?status=READ
GET /books?status=UNREAD

---

## 📄 Swagger

http://localhost:5000/api-docs

---

## 👨‍💻 Author

Durgasankar Mishra
