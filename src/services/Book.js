import Book from "../models/Book.js";

// Create book
export const createBook = async (data, userId) => {
    const { title, authorName, description } = data;
    if (!title) {
        throw new Error("Title is required");
    }
    const book = await Book.create({
        title,
        authorName,
        description,
        user_id: userId
    });
    return book;
};

// Get all books with filter
export const getAllBooks = async (userId, status) => {
    let where = { user_id: userId, is_deleted: false };
    if (status) {
        where.read_status = status.toUpperCase();
    }
    const books = await Book.findAll({ where });
    return books;
};

// Get single book
export const getBookById = async (id) => {
    const book = await Book.findByPk(id);
    if (!book) {
        throw new Error("Book not found");
    }
    return book;
};

// Update status
export const updateBookStatus = async (id, status) => {
    const book = await Book.findByPk(id);
    if (!book) {
        throw new Error("Book not found");
    }
    if (!['READ', 'UNREAD'].includes(status.toUpperCase())) {
        throw new Error("Invalid status");
    }
    book.read_status = status.toUpperCase();
    await book.save();
    return book;
};

// Delete book
export const deleteBook = async (id) => {
    const book = await Book.findByPk(id);
    if (!book || book.is_deleted) {
        throw new Error("Book not found");
    }
    book.is_deleted = true;
    await book.save();
};