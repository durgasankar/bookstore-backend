import express from 'express';
import cors from 'cors';
import swaggerUi from 'swagger-ui-express';
import routes from './routes/index.js';
import swaggerSpec from './configs/swagger.js';


const app = express();

app.use(cors());
app.use(express.json());

// Route registation
app.use('/api', routes);

// swagger routes
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

// global error handling for 500 statuses
// app.use((err, req, res, next) => {
//     res.status(500).json({ message: 'Internal Server Error!' })
// })

export default app;