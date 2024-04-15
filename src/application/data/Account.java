package application.data;

public class Account {
    private String username;
    private String email;
    private String hashPassword;

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.hashPassword = password.hashCode() + "";
    }

    public Account() {

    }

    // Getters and setters
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

    public String getHashPassword() {
        return hashPassword;
    }

    public void setPassword(String password) {
        this.hashPassword = password.hashCode() + "";
    }

    public static void main(String[] args) {
        String password = "12345678";
        System.out.println(password.hashCode() + "");
    }
}