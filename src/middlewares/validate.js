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
    if (!title) {
        return res.status(400).json({
            message: 'Book title is required.'
        });
    }
    next();
};

export const validateStatus = (req, res, next) => {
    const { status } = req.body;
    if (!status) {
        return res.status(400).json({
            message: 'Book title is required.'
        });
    }
    if (!['READ', 'UNREAD'].includes(status)) {
        return res.status(400).json({ message: "Invalid status value" });
    }
    next();
}

export const validateSearchInput = (req, res, next) => {
    console.log('object')
    const { title } = req.query;
    if (!title) {
        return res.status(400).json({
            message: 'Search param is required.'
        });
    }
    if (title.length < 3) {
        return res.status(400).json({ message: "Search input should be atleast 3 characters." });
    }
    next();
}
