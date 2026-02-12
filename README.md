 # WarehouseLite

WarehouseLite is a console-based inventory management system built using Java and MySQL.  
The application allows basic warehouse operations such as adding products, updating stock, selling items, and deleting products from the database.

This project was developed to practice Java programming concepts and JDBC database connectivity.

 

## Features

- Add new products
- View all products
- Update product quantity
- Sell products with stock validation
- Low stock alert notification
- Delete products
- Data stored in MySQL database

 

## Technologies Used

- Java (JDK 17 or higher)
- MySQL
- JDBC (MySQL Connector)
- VS Code



## Database Setup

1. Create a database named:

- warehouse

2.Run the following SQL command:

- CREATE TABLE products (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
quantity INT NOT NULL,
price DOUBLE NOT NULL
);

 

## Compile the Project
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d out src/db/.java src/model/.java src/service/*.java src/App.java

 

## Run the Application
java -cp "out;lib/mysql-connector-j-9.6.0.jar" App

 

## Author

Sandeep Kumar


