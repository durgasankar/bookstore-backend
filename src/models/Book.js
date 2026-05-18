import { DataTypes } from "sequelize";
import sequelize from "../configs/database.js";
import User from "./User.js";

const Book = sequelize.define('Book', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    title: {
        type: DataTypes.STRING,
        allowNull: false
    },
    authorName: {
        type: DataTypes.STRING,
        allowNull: true
    },
    read_status: {
        type: DataTypes.ENUM('READ', 'UNREAD'),
        defaultValue: 'UNREAD'
    },
    description: {
        type: DataTypes.TEXT,
        allowNull: true
    },
    addedOn: {
        type: DataTypes.DATE,
        defaultValue: DataTypes.NOW
    }
}, {
    tableName: 'books',
    underscored: true
})

User.hasMany(Book, { foreignKey: 'user_id' });
Book.belongsTo(User, { foreignKey: 'user_id' });

export default Book;