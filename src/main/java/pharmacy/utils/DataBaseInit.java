package pharmacy.utils;

import java.sql.*;

public class DataBaseInit {

    private static final String POSTGRES_JDBC_URL = "jdbc:postgresql://balarama.db.elephantsql.com:5432/hlqpjaqb";
    private static final String POSTGRES_USER_NAME = "hlqpjaqb";
    private static final String POSTGRES_USER_PASS = "VXJZE-kE4jRKdasnT2MQos4LgcOATz7G";

    public static Connection initializeDataBaseConnection() {
        try {
            System.out.println("Establishing database connection");
            return DriverManager.getConnection(POSTGRES_JDBC_URL, POSTGRES_USER_NAME, POSTGRES_USER_PASS);
        } catch (SQLException e) {
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
