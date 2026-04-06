import express from 'express';
import dotenv from 'dotenv';
import sequelize from './src/configs/database.js';
import { routes } from './src/Routes/IndexRoute.js';

dotenv.config();

const PORT = process.env.PORT || 5000;

const app = express();

app.use(express.json());

// Register routes
routes.forEach(route => {
    app[route.method](route.path, route.handler);
});

// ✅ Start server ONLY if DB connects
const startServer = async () => {
    try {
        await sequelize.authenticate();
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