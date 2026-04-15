import bcrypt from 'bcrypt';
import User from "../models/User.js";
import { generateToken } from '../configs/jwt.js';

export const registerUser = async data => {
    const { firstName, lastName, email, mobileNumber, gender, password } = data;
    // existing user checker
    const existingUser = await User.count({ where: { email } });
    if (existingUser > 0) {
        throw new Error('User already exist.')
    }
    // Password hashing
    const hashedPassword = await bcrypt.hash(password, 10);
    const createdUser = User.create({
        firstName,
        lastName,
        email,
        mobileNumber,
        gender,
        isVerified: false,
        password: hashedPassword
    });
    return createdUser;
}

export const loginUser = async data => {
    const { email, password } = data;
    // whether new user or existing
    const fetchedUser = await User.findOne({ where: { email } });
    if (!fetchedUser) {
        throw new Error("User doesn't exist");
    }
    // valid cred cheker
    const isValidPassword = await bcrypt.compare(password, fetchedUser.password);
    if (!isValidPassword) {
        throw new Error('Invalid credentials');
    }
    // generate jwt token and share
    const token = generateToken({ userId: fetchedUser.id, email: fetchedUser.email });
    return { token, fetchedUser };

}