package pharmacy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataBaseInit {

    public static Connection initializeDataBaseConnection() {
        try(InputStream input = DataBaseInit.class.getClassLoader().getResourceAsStream("application.properties")) {

            Properties properties = new Properties();
            properties.load(input);

            System.out.println("Establishing database connection");
            return DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));

        } catch (SQLException | IOException e) {

            System.err.println("Server can't initialize database connection: \n" + e);
            throw new RuntimeException("Server can't initialize database connection");
        }
    }

    public static void closeDataBaseResources(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                System.out.println("Closing statement");
                statement.close();
            }
            if (connection != null) {
                System.out.println("Closing connection");
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error during closing database resources: \n" + e);
            throw new RuntimeException("Error during closing database resources");
        }
    }
}
