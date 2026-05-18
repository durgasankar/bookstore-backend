import { Router } from "express";
import { authenticate } from "../middlewares/auth.js";
import { validateCreateBook, validateStatus } from '../middlewares/validate.js';
import {
    createBookController,
    getAllBooksController,
    getBookController,
    updateBookStatusController,
    deleteBookController
} from "../controllers/Book.js";

const router = Router();

router.use(authenticate);


router.post('/', validateCreateBook, createBookController);

router.get('/', getAllBooksController);

router.get('/:id', getBookController);

router.patch('/:id/status', validateStatus, updateBookStatusController);

router.delete('/:id', deleteBookController);

export default router;