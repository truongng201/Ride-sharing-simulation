package me.truongng.journeymapapi.models;

public class User {
    private String id;
    private String username;
    private String email;
    private String hashed_password;
    private String image_url;
    private Boolean is_verified;
    // private String phone_number;
    private Config.Role role;

    public User() {
    }

    public User(
            String id,
            String username,
            String email,
            String hashed_password,
            String image_url,
            Boolean is_verified,
            // String phone_number,
            Config.Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hashed_password = hashed_password;
        this.image_url = image_url;
        this.is_verified = is_verified;
        // this.phone_number = phone_number;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return hashed_password;
    }

    public void setPassword(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Boolean getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Boolean is_verified) {
        this.is_verified = is_verified;
    }

    // public String getPhone_number() {
    // return phone_number;
    // }

    // public void setPhone_number(String phone_number) {
    // this.phone_number = phone_number;
    // }

    public String getRole() {
        switch (role) {
            case ADMIN:
                return "ADMIN";
            case CUSTOMER:
                return "CUSTOMER";
            case DRIVER:
                return "DRIVER";
            default:
                return "CUSTOMER";
        }
    }

    public void setRole(String role) {
        switch (role) {
            case "ADMIN":
                this.role = Config.Role.ADMIN;
                break;
            case "CUSTOMER":
                this.role = Config.Role.CUSTOMER;
                break;
            case "DRIVER":
                this.role = Config.Role.DRIVER;
                break;
            default:
                this.role = Config.Role.CUSTOMER;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", hashed_password='" + hashed_password + '\'' +
                ", image_url='" + image_url + '\'' +
                ", is_verified=" + is_verified +
                '}';
    }
}
