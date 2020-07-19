package pharmacy.repository;

import pharmacy.domain.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static pharmacy.utils.DataBaseInit.closeDataBaseResources;
import static pharmacy.utils.DataBaseInit.initializeDataBaseConnection;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void createUser(UserData userData) {

        final String sqlCreateNewUser1 = "WITH ins1 AS (\n" +
                "INSERT INTO public.users(first_name, last_name, address, email, phone_number)\n" +
                "VALUES (?, ?, ?, ?, ?)\n" +
                "RETURNING user_id),\n" +
                "     ins2 AS (INSERT INTO public.user_credentials(login, password, user_id)\n" +
                "VALUES (?, ?, (SELECT user_id FROM ins1)))\n" +
                "\n" +
                "INSERT INTO public.pharmacy_staff(user_id, job_title, salary, pharmacy_id)\n" +
                "VALUES ((SELECT user_id FROM ins1), ?, ?, ?);";

        Connection connection = initializeDataBaseConnection();

        PreparedStatement preparedStatement1 = null;

        try {
            preparedStatement1 = connection.prepareStatement(sqlCreateNewUser1);

            preparedStatement1.setString(1, userData.getFirstName());
            preparedStatement1.setString(2, userData.getLastName());
            preparedStatement1.setString(3, userData.getAddress());
            preparedStatement1.setString(4, userData.getEmail());
            preparedStatement1.setString(5, userData.getPhoneNumber());

            preparedStatement1.setString(6, userData.getLogin());
            preparedStatement1.setString(7, userData.getPassword());

            preparedStatement1.setString(8, userData.getJobTitle());
            preparedStatement1.setInt(9, userData.getSalary());
            preparedStatement1.setInt(10, userData.getPharmacyId());

            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement1);
            System.out.println("ALL CLOSED");
        }
    }
//    @Override
//    public void createUser(UserData userData) {
//
//        final String sqlCreateNewUser1 = "INSERT INTO public.users(\n" +
//                "    first_name, last_name, address, email, phone_number)\n" +
//                "VALUES (?, ?, ?, ?, ?);";
//
//        final String sqlCreateNewUser2 = "INSERT INTO public.user_credentials(\n" +
//                "    login, password, user_id)\n" +
//                "VALUES (?, ?, (SELECT MAX(users.user_id) FROM public.users));";
//
//        final String sqlCreateNewUser3 = "INSERT INTO public.pharmacy_staff(\n" +
//                "    user_id, job_title, salary, pharmacy_id)\n" +
//                "VALUES ((SELECT MAX(users.user_id) FROM public.users), ?, ?, ?);";
//
//        Connection connection = initializeDataBaseConnection();
//
//        PreparedStatement preparedStatement1 = null;
//        PreparedStatement preparedStatement2 = null;
//        PreparedStatement preparedStatement3 = null;
//
//        try {
//            preparedStatement1 = connection.prepareStatement(sqlCreateNewUser1);
//            preparedStatement2 = connection.prepareStatement(sqlCreateNewUser2);
//            preparedStatement3 = connection.prepareStatement(sqlCreateNewUser3);
//
//            preparedStatement1.setString(1, userData.getFirstName());
//            preparedStatement1.setString(2, userData.getLastName());
//            preparedStatement1.setString(3, userData.getAddress());
//            preparedStatement1.setString(4, userData.getEmail());
//            preparedStatement1.setString(5, userData.getPhoneNumber());
//
//            preparedStatement2.setString(1, userData.getLogin());
//            preparedStatement2.setString(2, userData.getPassword());
//
//            preparedStatement3.setString(1, userData.getJobTitle());
//            preparedStatement3.setInt(2, userData.getSalary());
//            preparedStatement3.setInt(3, userData.getPharmacyId());
//
//            preparedStatement1.executeUpdate();
//            preparedStatement2.executeUpdate();
//            preparedStatement3.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
//            throw new RuntimeException("Error during invoke SQL query");
//        } finally {
//            closeDataBaseResources(connection, preparedStatement1);
//            closeDataBaseResources(connection, preparedStatement2);
//            closeDataBaseResources(connection, preparedStatement3);
//        }
//    }

    @Override
    public UserData readUser(int userId) {

        final String sqlGetData = "SELECT users.user_id, first_name, last_name, address, email,\n" +
                "       phone_number, login, password, job_title, salary, pharmacy_id FROM users\n" +
                "INNER JOIN pharmacy_staff ps\n" +
                "    ON users.user_id = ps.user_id\n" +
                "INNER JOIN user_credentials uc\n" +
                "    ON users.user_id = uc.user_id\n" +
                "WHERE users.user_id=?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        UserData userData = new UserData();

        try {
            preparedStatement = connection.prepareStatement(sqlGetData);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                userData.setUserId(resultSet.getInt("user_id"));
                userData.setFirstName(resultSet.getString("first_name"));
                userData.setLastName(resultSet.getString("last_name"));
                userData.setAddress(resultSet.getString("address"));
                userData.setEmail(resultSet.getString("email"));
                userData.setPhoneNumber(resultSet.getString("phone_number"));
                userData.setLogin(resultSet.getString("login"));
                userData.setPassword(resultSet.getString("password"));
                userData.setJobTitle(resultSet.getString("job_title"));
                userData.setSalary(resultSet.getInt("salary"));
                userData.setPharmacyId(resultSet.getInt("pharmacy_id"));

            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }
        return userData;
    }

    @Override
    public void updateUser(UserData userData) {

        final String sqlUserUpdate1 = "UPDATE public.users\n" +
                "    SET first_name=?, last_name=?, address=?, email=?, phone_number=?\n" +
                "WHERE user_id = ?;";

        final String sqlUserUpdate2 = "UPDATE user_credentials\n" +
                "    SET login = ?, password = ?\n" +
                "WHERE user_id = ?;";

        final String sqlUserUpdate3 = "UPDATE pharmacy_staff\n" +
                "    SET job_title = ?, salary = ?, pharmacy_id = ?\n" +
                "WHERE user_id = ?;";

        Connection connection = initializeDataBaseConnection();

        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;

        try {
            preparedStatement1 = connection.prepareStatement(sqlUserUpdate1);
            preparedStatement2 = connection.prepareStatement(sqlUserUpdate2);
            preparedStatement3 = connection.prepareStatement(sqlUserUpdate3);

            preparedStatement1.setString(1, userData.getFirstName());
            preparedStatement1.setString(2, userData.getLastName());
            preparedStatement1.setString(3, userData.getAddress());
            preparedStatement1.setString(4, userData.getEmail());
            preparedStatement1.setString(5, userData.getPhoneNumber());
            preparedStatement1.setInt(6, userData.getUserId());

            preparedStatement2.setString(1, userData.getLogin());
            preparedStatement2.setString(2, userData.getPassword());
            preparedStatement2.setInt(3, userData.getUserId());

            preparedStatement3.setString(1, userData.getJobTitle());
            preparedStatement3.setInt(2, userData.getSalary());
            preparedStatement3.setInt(3, userData.getPharmacyId());
            preparedStatement3.setInt(4, userData.getUserId());

            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement1);
            closeDataBaseResources(connection, preparedStatement2);
            closeDataBaseResources(connection, preparedStatement3);
        }
    }

    @Override
    public void deleteUser(int userId) {

        final String sqlRemoveUserI = "DELETE FROM public.users\n" +
                "WHERE user_id =?;";
        final String sqlRemoveUserII = "DELETE FROM public.user_credentials\n" +
                "WHERE user_id =?;";
        final String sqlRemoveUserIII = "DELETE FROM public.pharmacy_staff\n" +
                "WHERE user_id =?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatementI = null;
        PreparedStatement preparedStatementII = null;
        PreparedStatement preparedStatementIII = null;

        try {
            preparedStatementI = connection.prepareStatement(sqlRemoveUserI);
            preparedStatementII = connection.prepareStatement(sqlRemoveUserII);
            preparedStatementIII = connection.prepareStatement(sqlRemoveUserIII);

            preparedStatementI.setInt(1,userId);
            preparedStatementII.setInt(1,userId);
            preparedStatementIII.setInt(1,userId);

            preparedStatementI.executeUpdate();
            preparedStatementII.executeUpdate();
            preparedStatementIII.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatementI);
            closeDataBaseResources(connection, preparedStatementII);
            closeDataBaseResources(connection, preparedStatementIII);
        }
    }

    @Override
    public List<UserData> getAllUsers() {
        final String sqlGetAllUsersData = "SELECT users.user_id, first_name, last_name, address, email,\n" +
                "       phone_number, login, job_title, salary, pharmacy_id FROM users\n" +
                "INNER JOIN pharmacy_staff ps\n" +
                "           ON users.user_id = ps.user_id\n" +
                "INNER JOIN user_credentials uc\n" +
                "           ON users.user_id = uc.user_id\n" +
                "ORDER BY first_name;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        List<UserData> userDataList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sqlGetAllUsersData);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserData userData = new UserData();

                userData.setUserId(resultSet.getInt("user_id"));
                userData.setFirstName(resultSet.getString("first_name"));
                userData.setLastName(resultSet.getString("last_name"));
                userData.setAddress(resultSet.getString("address"));
                userData.setEmail(resultSet.getString("email"));
                userData.setPhoneNumber(resultSet.getString("phone_number"));
                userData.setLogin(resultSet.getString("login"));
                userData.setJobTitle(resultSet.getString("job_title"));
                userData.setSalary(resultSet.getInt("salary"));
                userData.setPharmacyId(resultSet.getInt("pharmacy_id"));

                userDataList.add(userData);
            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }

        return userDataList;
    }

    @Override
    public List<UserData> getAllUsersByUnit(int pharmacyId) {

        final String sqlGetAllUsersData = "SELECT users.user_id, first_name, last_name, address, email,\n" +
                "       phone_number, login, job_title, salary FROM users\n" +
                "INNER JOIN pharmacy_staff ps\n" +
                "           ON users.user_id = ps.user_id\n" +
                "INNER JOIN user_credentials uc\n" +
                "           ON users.user_id = uc.user_id\n" +
                "WHERE pharmacy_id=?\n" +
                "ORDER BY first_name;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        List<UserData> userDataList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sqlGetAllUsersData);
            preparedStatement.setInt(1, pharmacyId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UserData userData = new UserData();

                userData.setUserId(resultSet.getInt("user_id"));
                userData.setFirstName(resultSet.getString("first_name"));
                userData.setLastName(resultSet.getString("last_name"));
                userData.setAddress(resultSet.getString("address"));
                userData.setEmail(resultSet.getString("email"));
                userData.setPhoneNumber(resultSet.getString("phone_number"));
                userData.setLogin(resultSet.getString("login"));
                userData.setJobTitle(resultSet.getString("job_title"));
                userData.setSalary(resultSet.getInt("salary"));

                userDataList.add(userData);
            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }

        return userDataList;
    }
}
