package network.networkMessages;

public class Login {
    private String username;
    private String character;

    public Login(){

    }

    public Login(String username, String character) {
        this.username = username;
        this.character = character;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
