package coe528project;

public abstract class User {
    
    String username;
    String password;
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String setUsername(String username) {
        this.username = username;
        return this.username;
    }
    
    public String setPassword(String password) {
        this.password = password;
        return this.password;
    }
}