import Book from "../models/Book.js";
import { Op } from "sequelize";

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

// Get all books with filter default empty
export const getAllBooks = async (status) => {
    let where = { is_deleted: false };
    if (status) {
        where.readStatus = status.toUpperCase();
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
    console.log(book)
    if (!book) {
        throw new Error("Book not found");
    }
    if (!['READ', 'UNREAD'].includes(status.toUpperCase())) {
        throw new Error("Invalid status");
    }
    book.readStatus = status.toUpperCase();
    await book.save();
    return book;
};

// Delete book
export const deleteBook = async (id) => {
    const book = await Book.findByPk(id);
    if (!book || book.isDeleted) {
        throw new Error("Book not found");
    }
    book.isDeleted = true;
    await book.save();
};

// Search book by title
export const searchBooks = async (title) => {
    const books = await Book.findAll({
        where: {
            is_deleted: false,
            title: {
                [Op.iLike]: `%${title}%`,
            },
        },
    });
    if (!books.length) {
        throw new Error("No matching books found");
    }
    return books;
};
