import { Router } from "express";
import { authenticate } from "../middlewares/auth.js";
import { validateCreateBook } from '../middlewares/validate.js';
import {
    createBookController,
    getAllBooksController,
    getBookController,
    updateBookStatusController,
    deleteBookController
} from "../controllers/Book.js";

const router = Router();

router.use(authenticate);

/**
 * @swagger
 * /books:
 *   post:
 *     summary: Create a new book
 *     tags: [Books]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - title
 *             properties:
 *               title:
 *                 type: string
 *                 example: Atomic Habits
 *               authorName:
 *                 type: string
 *                 example: James Clear
 *               description:
 *                 type: string
 *                 example: Self-help book
 *     responses:
 *       201:
 *         description: Book created successfully
 *       400:
 *         description: Bad request
 */
router.post('/', validateCreateBook, createBookController);


/**
 * @swagger
 * /books:
 *   get:
 *     summary: Get all books (with optional filtering)
 *     tags: [Books]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: status
 *         schema:
 *           type: string
 *           enum: [READ, UNREAD]
 *         required: false
 *         description: Filter books by read status
 *     responses:
 *       200:
 *         description: List of books
 */
router.get('/', getAllBooksController);


/**
 * @swagger
 * /books/{id}:
 *   get:
 *     summary: Get a single book by ID
 *     tags: [Books]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         example: 1
 *     responses:
 *       200:
 *         description: Book found
 *       404:
 *         description: Book not found
 */
router.get('/:id', getBookController);


/**
 * @swagger
 * /books/{id}/status:
 *   patch:
 *     summary: Update book read status
 *     tags: [Books]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         example: 1
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - status
 *             properties:
 *               status:
 *                 type: string
 *                 enum: [READ, UNREAD]
 *                 example: READ
 *     responses:
 *       200:
 *         description: Status updated
 *       400:
 *         description: Invalid input
 */
router.patch('/:id/status', updateBookStatusController);


/**
 * @swagger
 * /books/{id}:
 *   delete:
 *     summary: Delete a book
 *     tags: [Books]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: integer
 *         example: 1
 *     responses:
 *       200:
 *         description: Book deleted successfully
 *       404:
 *         description: Book not found
 */
router.delete('/:id', deleteBookController);

export default router;