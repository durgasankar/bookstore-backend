import bcrypt from 'bcrypt';
import User from "../models/User.js";

export const registerUser = async data => {
    const { firstName, lastName, email, mobileNumber, gender, password } = data;
    // existing user checker
    const existingUser = User.hasOne({ where: email })
    if (existingUser) {
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