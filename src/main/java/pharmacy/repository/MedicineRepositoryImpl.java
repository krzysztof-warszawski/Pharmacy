package pharmacy.repository;

import pharmacy.domain.MedicineData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static pharmacy.utils.DataBaseInit.closeDataBaseResources;
import static pharmacy.utils.DataBaseInit.initializeDataBaseConnection;

public class MedicineRepositoryImpl implements MedicineRepository {

    @Override
    public void createMedicine(MedicineData medicineData) {
        final String sqlAddMedicine1 = "WITH new_medicine AS(\n" +
                "    INSERT INTO medicines(medicine_name, price, medicine_description)\n" +
                "        VALUES (?, ?, ?)\n" +
                "        RETURNING medicine_id AS id),\n" +
                "    storage_update (ph_id) AS(\n" +
                "        SELECT DISTINCT pharmacy_id FROM pharmacy_storage\n" +
                ")\n" +
                "INSERT INTO pharmacy_storage(pharmacy_id, medicine_id, quantity)\n" +
                "SELECT ph_id, (SELECT id FROM new_medicine), 45 FROM storage_update;";


        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement1 = null;

        try {
            preparedStatement1 = connection.prepareStatement(sqlAddMedicine1);

            preparedStatement1.setString(1, medicineData.getMedicineName());
            preparedStatement1.setDouble(2, medicineData.getPrice());
            preparedStatement1.setString(3, medicineData.getMedicineDescription());

            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement1);
        }
    }

    @Override
    public MedicineData readMedicine(int medicineId) {
        final String sqlGetData = "SELECT medicine_id, medicine_name, price, medicine_description\n" +
                "   FROM public.medicines WHERE medicine_id=?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        MedicineData medicineData = new MedicineData();

        try {
            preparedStatement = connection.prepareStatement(sqlGetData);
            preparedStatement.setInt(1,medicineId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                medicineData.setMedicineId(resultSet.getInt("medicine_id"));
                medicineData.setMedicineName(resultSet.getString("medicine_name"));
                medicineData.setPrice(resultSet.getDouble("price"));
                medicineData.setMedicineDescription(resultSet.getString("medicine_description"));
            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }

        return medicineData;
    }

    @Override
    public void updateMedicine(MedicineData medicineData) {
        final String sqlUpdateMedicine = "UPDATE public.medicines\n" +
                "    SET medicine_name=?, price=?, medicine_description=?\n" +
                "WHERE medicine_id=?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlUpdateMedicine);

            preparedStatement.setString(1, medicineData.getMedicineName());
            preparedStatement.setDouble(2, medicineData.getPrice());
            preparedStatement.setString(3, medicineData.getMedicineDescription());
            preparedStatement.setInt(4, medicineData.getMedicineId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }
    }

    @Override
    public void deleteMedicine(int medicineId) {
        final String sqlRemoveMedicine = "DELETE FROM public.medicines\n" +
                "WHERE medicine_id=?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlRemoveMedicine);
            preparedStatement.setInt(1, medicineId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }
    }

    @Override
    public List<MedicineData> getAllMedicines() {
        final String sqlGetAllMedicines = "SELECT medicine_id, medicine_name, price, medicine_description\n" +
                "    FROM public.medicines;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        List<MedicineData> medicineDataList = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(sqlGetAllMedicines);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MedicineData medicineData = new MedicineData();

                medicineData.setMedicineId(resultSet.getInt("medicine_id"));
                medicineData.setMedicineName(resultSet.getString("medicine_name"));
                medicineData.setPrice(resultSet.getDouble("price"));
                medicineData.setMedicineDescription(resultSet.getString("medicine_description"));

                medicineDataList.add(medicineData);
            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        } finally {
            closeDataBaseResources(connection, preparedStatement);
        }

        return medicineDataList;
    }
}
