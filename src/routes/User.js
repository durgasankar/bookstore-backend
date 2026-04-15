// import { registerUser } from "../services/User.js";

import { Router } from "express";
import { validateLogin, validateRegister } from "../middlewares/validate.js";
import { loginUserController, registerUserController } from "../controllers/User.js";

const router = Router();

router.post('/register', validateRegister, registerUserController);
router.post('/login', validateLogin, loginUserController);

export default router;
``