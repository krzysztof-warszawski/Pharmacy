package pharmacy.repository;

import pharmacy.domain.UserData;

import java.util.List;

public interface UserRepository {

    void createUser(UserData userData);

    UserData readUser(int userId);

    void updateUser(UserData userData);

    void deleteUser(int userId);

    List<UserData> getAllUsers();

    List<UserData> getAllUsersByPharmacy(int pharmacyId);
}
