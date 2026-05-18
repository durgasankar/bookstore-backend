import { Router } from 'express';
import userRoutes from './User.js';
import bookRoutes from './Book.js';

const router = Router();

router.use('/users', userRoutes);
router.use('/books', bookRoutes);

export default router;