package lk.ijse.dto;

public class UserDTO {

    private String userId;
    private String userType;
    private String username;
    private String password;
    private String email;
    private String mobile;

    public UserDTO() {
    }

    public UserDTO(String userId, String userType, String username, String password, String email, String mobile) {
        this.userId = userId;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }

    public UserDTO(String userId,String userName,String password,String email,String mobile){
        this.userId = userId;
        this.username = userName;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
