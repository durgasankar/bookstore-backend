export const validateRegister = (req, res, next) => {
    const { firstName, email, mobileNumber, gender, password } = req.body;

    if (!firstName || !email || !mobileNumber || !gender || !password) {
        return res.status(400).json({
            message: 'All fields are required.'
        });
    }
    next();
};

export const validateLogin = (req, res, next) => {
    const { email, password } = req.body;
    if (!email || !password) {
        return res.status(400).json({
            message: 'Email and password are required.'
        });
    }
    next();
};

export const validateCreateBook = (req, res, next) => {
    const { title } = req.body;
    if (!email || !password) {
        return res.status(400).json({
            message: 'Book title is required.'
        });
    }
    next();
};
