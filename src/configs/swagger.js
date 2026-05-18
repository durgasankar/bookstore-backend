import swaggerJSDoc from "swagger-jsdoc";

const options = {
    definition: {
        openapi: "3.0.0",
        info: { title: "Book API", version: "1.0.0" },
        servers: [{ url: `http://localhost:${process.env.PORT}/api` }],
        components: {
            securitySchemes: {
                bearerAuth: { type: "http", scheme: "bearer" }
            }
        },
        paths: {
            "/users/register": {
                post: {
                    tags: ["Users"],
                    summary: "Register",
                    requestBody: {
                        required: true,
                        content: {
                            "application/json": {
                                example: {
                                    firstName: "John",
                                    lastName: "Cena",
                                    email: "username@email.com",
                                    mobileNumber: "1234567890",
                                    gender: "male",
                                    password: "password"
                                }
                            }
                        }
                    },
                    responses: {
                        201: { description: "Success" },
                        400: { description: "Error" }
                    }
                }
            },
            "/users/login": {
                post: {
                    tags: ["Users"],
                    summary: "Login",
                    requestBody: {
                        required: true,
                        content: {
                            "application/json": {
                                example: {
                                    email: "username@email.com",
                                    password: "password"
                                }
                            }
                        }
                    },
                    responses: {
                        200: { description: "Token returned" },
                        401: { description: "Invalid credentials" }
                    }
                }
            },
            "/books": {
                post: {
                    tags: ["Books"],
                    security: [{ bearerAuth: [] }],
                    summary: "Create book",
                    requestBody: {
                        required: true,
                        content: {
                            "application/json": {
                                example: {
                                    title: "Atomic Habits",
                                    authorName: "James Clear",
                                    description: "Self-help book"
                                }
                            }
                        }
                    },
                    responses: { 201: { description: "Created" } }
                },
                get: {
                    tags: ["Books"],
                    security: [{ bearerAuth: [] }],
                    summary: "Get books",
                    parameters: [
                        {
                            name: "status",
                            in: "query",
                            required: false,
                            schema: {
                                type: "string",
                                enum: ["READ", "UNREAD", ""],
                                default: ""
                            },
                            description: "Filter books: READ, UNREAD or leave empty for all"
                        }
                    ],
                    responses: { 200: { description: "List returned" } }
                }
            },
            "/books/{id}": {
                get: {
                    tags: ["Books"],
                    security: [{ bearerAuth: [] }],
                    summary: "Get book",
                    parameters: [{ name: "id", in: "path", required: true, example: 1 }],
                    responses: { 200: {}, 404: { description: "Not found" } }
                },
                delete: {
                    tags: ["Books"],
                    security: [{ bearerAuth: [] }],
                    summary: "Delete book",
                    parameters: [{ name: "id", in: "path", required: true, example: 1 }],
                    responses: { 200: { description: "Deleted" } }
                }
            },
            "/books/{id}/status": {
                patch: {
                    tags: ["Books"],
                    security: [{ bearerAuth: [] }],
                    summary: "Update status",
                    parameters: [{ name: "id", in: "path", required: true, example: 1 }],
                    requestBody: {
                        required: true,
                        content: {
                            "application/json": {
                                example: { status: "READ" }
                            }
                        }
                    },
                    responses: { 200: {}, 400: { description: "Invalid" } }
                }
            }
        },
        tags: [{ name: "Users" }, { name: "Books" }]
    },
    apis: []
};

export default swaggerJSDoc(options);