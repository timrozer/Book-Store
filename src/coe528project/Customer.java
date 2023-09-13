package coe528project;

public class Customer extends User{
    private int points;
    private String status; 

    Customer(String username, String password) {//constructor//
        this.password = password;
        this.username = username;
        points = 0;
        setStatus(points);
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points += points;
        setStatus(this.points);
    }
    private void setStatus(int points) {
        if(points>1000){
            status = "Gold";
        }
        else status = "Silver";
    }
    
    public String getStatus() {
        return status;
    }
}

