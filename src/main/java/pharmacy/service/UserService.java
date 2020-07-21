package pharmacy.service;

import pharmacy.domain.UserData;

import java.util.List;

public interface UserService {

    void addNewUser(String firstName, String lastName, String address, String email, String phoneNumber,
                    String login, String password, String jobTitle, int salary, int pharmacyId);

    void updateUserData(String firstName, String lastName, String address, String email, String phoneNumber,
                        String login, String password, String jobTitle, int salary, int pharmacyId);

    void removeUser(int index);

    UserData readUserData();

    UserData readUnitUser();

    void updateAllEmployeeList();

    String[] getAllEmployeeList();

    String[] getUnitEmployeeList();

    void setUserDataList();

    List<UserData> getUserDataList();

    void setUserData(int index);

    void setAllEmployeeList();

    void setUnitEmployeeList();

    void setUsersByUnit();

    List<UserData> getUsersByUnit();

    void setUnitUser(int index);
}
