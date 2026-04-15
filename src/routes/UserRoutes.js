import { registerUser } from "../services/User.js";

export const registerRoute = {
    path: '/api/register',
    method: 'post',
    handler: async (req, res) => {
        try {
            const {
                firstName,
                email,
                mobileNumber,
                gender,
                password,
            } = req.body;
            if (!firstName || !email || !mobileNumber || !gender || !password) {
                res.status(400).json({ message: 'All fields are required.' });
            }
            await registerUser(req.body);
            res.status(201).json({ message: 'Registration successful.' });
        } catch (error) {
            res.status(400).json({ message: error.message });
        }
    }
}