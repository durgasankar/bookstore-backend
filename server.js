import dotenv from 'dotenv';
import sequelize from './src/configs/database.js';
import app from './src/app.js';

dotenv.config();
const PORT = process.env.PORT || 5000;

// ✅ Start server ONLY if DB connects
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
        process.exit(1); // stop app if DB fails
    }
};

startServer();