package pharmacy.utils;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DataBaseInitTest {

    @Test
    public void initializeDataBaseConnection() throws SQLException {
        Connection connection = DataBaseInit.initializeDataBaseConnection();
        assertNotNull(connection);
        connection.close();
        System.out.println("Connection closed");
    }
}
