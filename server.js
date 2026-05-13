import dotenv from 'dotenv';
import sequelize from './src/configs/database.js';
import app from './src/app.js';

dotenv.config();
const PORT = process.env.PORT || 5000;

// starting server after db connection
const startServer = async () => {
    try {
        await sequelize.authenticate();
        // create table if not exist
        await sequelize.sync()
        console.log('✅ Database connected successfully');
        app.listen(PORT, () => {
            console.log(`🚀 Server started on port ${PORT}`);
        });
    } catch (error) {
        console.error('❌ Database connection failed:', error.message);
        process.exit(1); // stopping the process if db fails.
    }
};

startServer();