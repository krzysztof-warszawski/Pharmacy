package pharmacy.service;

import pharmacy.domain.UserProfileData;
import pharmacy.repository.UserProfileRepository;
import pharmacy.repository.UserProfileRepositoryImpl;

public class UserProfileService {

    private static final UserProfileRepository userProfileRepository = new UserProfileRepositoryImpl();
    private static UserProfileData userProfileData;

    public static void initializeUserProfile(String userLogin, String userPassword) {
        userProfileData = userProfileRepository.initializeUserProfile(userLogin,userPassword);
    }

    public static String getFirstName() {
        return userProfileData.getFirstName();
    }

    public String getAddress() {
        return userProfileData.getAddress();
    }

    public String getEmail() {
        return userProfileData.getEmail();
    }

    public String getLogin() {
        return userProfileData.getLogin();
    }

    public String getPassword() {
        return userProfileData.getPassword();
    }

    public static String getJobTitle() {
        return userProfileData.getJobTitle();
    }

    public int getSalary() {
        return userProfileData.getSalary();
    }

    public static int getPharmacyId() {
        return userProfileData.getPharmacyId();
    }

    public static int getUserId() {
        return userProfileData.getUserId();
    }

    public static boolean isCorrect() {
        return userProfileData.isUserAuthorized();
    }
}
