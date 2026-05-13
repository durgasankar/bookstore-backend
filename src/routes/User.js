// import { registerUser } from "../services/User.js";

import { Router } from "express";
import { validateLogin, validateRegister } from "../middlewares/validate.js";
import { loginUserController, registerUserController } from "../controllers/User.js";

const router = Router();

/**
 * @swagger
 * /users/register:
 *   post:
 *     summary: Register user
 *     tags: [Users]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - firstName
 *               - email
 *               - mobileNumber
 *               - gender
 *               - password
 *             properties:
 *               firstName:
 *                 type: string
 *                 example: John
 *               email:
 *                 type: string
 *                 example: username@email.com
 *               mobileNumber:
 *                 type: string
 *                 example: "1234567890"
 *               gender:
 *                 type: string
 *                 example: male
 *               password:
 *                 type: string
 *                 example: password
 *     responses:
 *       201:
 *         description: User registered successfully
 */
router.post('/register', validateRegister, registerUserController);


/**
 * @swagger
 * /users/login:
 *   post:
 *     summary: Login user
 *     tags: [Users]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - email
 *               - password
 *             properties:
 *               email:
 *                 type: string
 *                 example: username@email.com
 *               password:
 *                 type: string
 *                 example: password
 *     responses:
 *       200:
 *         description: Login successful
 */
router.post('/login', validateLogin, loginUserController);

export default router;
``