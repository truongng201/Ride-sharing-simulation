package me.truongng.journeymapapi.models;

public class RefreshToken {
    private String id;
    private String token;
    private User user;

    public RefreshToken() {
    }

    public RefreshToken(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return user.getId();
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", userID='" + getUserID() + '\'' +
                '}';
    }
}
