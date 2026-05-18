import {
    createBook,
    getAllBooks,
    getBookById,
    updateBookStatus,
    deleteBook
} from "../services/Book.js";

// Create
export const createBookController = async (req, res) => {
    try {
        const book = await createBook(req.body, req.user?.userId);
        res.status(201).json(book);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
};

// Get All (with filter)
export const getAllBooksController = async (req, res) => {
    try {
        const { status } = req.query;
        const books = await getAllBooks(req.user?.userId, status);
        res.status(200).json(books);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
};

// Get single
export const getBookController = async (req, res) => {
    try {
        const book = await getBookById(req.params.id);
        res.status(200).json(book);
    } catch (error) {
        res.status(404).json({ message: error.message });
    }
};

// Update status
export const updateBookStatusController = async (req, res) => {
    try {
        const { status } = req.body;
        const book = await updateBookStatus(req.params.id, status);
        res.status(200).json(book);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
};

// Delete
export const deleteBookController = async (req, res) => {
    try {
        await deleteBook(req.params.id);
        res.status(200).json({ message: 'Book deleted successfully' });
    } catch (error) {
        res.status(404).json({ message: error.message });
    }
};
``