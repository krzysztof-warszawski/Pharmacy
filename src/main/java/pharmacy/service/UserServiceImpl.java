package pharmacy.service;

import pharmacy.domain.UserData;
import pharmacy.repository.UserRepository;
import pharmacy.repository.UserRepositoryImpl;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserData newUserData;
    private UserData updateUserData;
    private List<UserData> userDataList;
    private String[] allEmployeeList;
    private String[] unitEmployeeList;
    private List<UserData> usersByUnit;
    private UserData unitUser;
    private int pharmacyId;

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
        setUserDataList();
        setAllEmployeeList();
    }

    public UserServiceImpl(int pharmacyId) {
        this.userRepository = new UserRepositoryImpl();
        this.pharmacyId = pharmacyId;
        setUsersByUnit();
        setUnitEmployeeList();
    }

    // =================== CRUD for the User ===================
    @Override
    public void addNewUser(String firstName, String lastName, String address, String email, String phoneNumber,
                           String login, String password, String jobTitle, int salary, int pharmacyId) {
        this.newUserData = new UserData(firstName, lastName, address, email, phoneNumber, login,
                password, jobTitle, salary, pharmacyId);
        userRepository.createUser(newUserData);
    }

    @Override
    public void setUserDataForUpdate(String firstName, String lastName, String address, String email, String phoneNumber,
                                     String login, String password, String jobTitle, int salary, int pharmacyId) {
        int userId = updateUserData.getUserId();
        this.updateUserData = new UserData(firstName, lastName, address, email, phoneNumber, login,
                password, jobTitle, salary, pharmacyId);
        updateUserData.setUserId(userId);
        userRepository.updateUser(updateUserData);
    }

    @Override
    public void removeUser(int index) {
        int uid = getUserDataList().get(index).getUserId();
        userRepository.deleteUser(uid);
        updateAllEmployeeList();
    }

    @Override
    public UserData readUserData() {
        return updateUserData;
    }

    // =================== Helping methods ===================

    @Override
    public UserData readUnitUser() {
        return unitUser;
    }

    @Override
    public void updateAllEmployeeList() {
        setUserDataList();
        setAllEmployeeList();
    }

    @Override
    public void updateUnitEmployeeList() {
        setUsersByUnit();
        setUnitEmployeeList();
    }

    // =================== Getters/Setters ===================
    @Override
    public String[] getAllEmployeeList() {
        return allEmployeeList;
    }

    @Override
    public String[] getUnitEmployeeList() {
        return unitEmployeeList;
    }

    @Override
    public void setUserDataList() {
        this.userDataList = userRepository.getAllUsers();
    }

    @Override
    public List<UserData> getUserDataList() {
        return userDataList;
    }

    @Override
    public void setUpdateUserData(int index) {
        int userId = getUserDataList().get(index).getUserId();
        this.updateUserData = userRepository.readUser(userId);
    }

    @Override
    public void setAllEmployeeList() {
        this.allEmployeeList = new String[getUserDataList().size()];
        int i = 0;
        for (UserData ud : getUserDataList()) {
            getAllEmployeeList()[i] = ud.getName();
            i++;
        }
    }

    @Override
    public void setUnitEmployeeList() {
        this.unitEmployeeList = new String[getUsersByUnit().size()];
        int i = 0;
        for (UserData ud : getUsersByUnit()) {
            getUnitEmployeeList()[i] = ud.getName();
            i++;
        }
    }

    @Override
    public void setUsersByUnit() {
        this.usersByUnit = userRepository.getAllUsersByUnit(pharmacyId);
    }

    @Override
    public List<UserData> getUsersByUnit() {
        return usersByUnit;
    }

    @Override
    public void setUnitUser(int index) {
        this.unitUser = getUsersByUnit().get(index);
    }
}