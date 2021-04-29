package Req;

public class ServiceEngineerRequests {
    public String fname,lname, email,username;
    public ServiceEngineerRequests(String fname, String lname, String email, String username){
        this.fname=fname;
        this.lname=lname;
        this.email=email;
        this.username=username;
    }

    @Override
    public String toString() {
        return fname+" "+lname;
    }
}
