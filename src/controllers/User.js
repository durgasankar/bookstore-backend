import { registerUser, loginUser } from '../services/User.js'

export const registerUserController = async (req, res) => {
    try {
        await registerUser(req.body);
        return res.status(201).json({ message: 'Registration successful.' });
    } catch (error) {
        return res.status(400).json({ message: error.message });
    }
};

export const loginUserController = async (req, res) => {
    try {
        const { token, fetchedUser } = await loginUser(req.body);
        const { email, firstName, lastName, id, mobileNumber } = fetchedUser;
        return res.status(200).json({
            message: 'Login successful.',
            token,
            userInfo: { email, firstName, lastName, id, mobileNumber }
        });
    } catch (error) {
        return res.status(400).json({ message: error.message });
    }
};
