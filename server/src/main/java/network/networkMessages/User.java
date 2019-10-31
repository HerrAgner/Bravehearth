package network.networkMessages;

public class User {

    private String username;
    private Avatar avatar;

    public User() {}

    public User(String username, Avatar avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getUsername() { return username; }
}
