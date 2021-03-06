package pharmacy.domain;

import java.util.Objects;

public class UserData {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private String login;
    private String password;
    private String jobTitle;
    private int salary;
    private int pharmacyId;
    private int userId;

    public UserData(){ }

    public UserData(String firstName, String lastName, String address, String email, String phoneNumber,
                    String login, String password, String jobTitle, int salary, int pharmacyId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.pharmacyId = pharmacyId;
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(int pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", pharmacyId=" + pharmacyId +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData userData = (UserData) o;
        return getUserId() == userData.getUserId() &&
                Objects.equals(getFirstName(), userData.getFirstName()) &&
                Objects.equals(getLastName(), userData.getLastName()) &&
                Objects.equals(getLogin(), userData.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }
}
