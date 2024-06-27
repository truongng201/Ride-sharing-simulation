package me.truongng.journeymapapi.models;

public class RefreshToken {
    private String id;
    private String token;
    private String ipAddress;
    private String userAgent;
    private String deviceType;
    private User user;

    public RefreshToken() {
    }

    public RefreshToken(
            String token,
            String ipAddress,
            String userAgent,
            String deviceType,
            User user) {
        this.token = token;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.deviceType = deviceType;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getUserID() {
        return user.getId();
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", userID='" + getUserID() + '\'' +
                '}';
    }
}
