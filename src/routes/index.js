import { Router } from 'express';
import userRoutes from './User.js';

const router = Router();

router.use('/users', userRoutes);

export default router;