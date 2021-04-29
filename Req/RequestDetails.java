package Req;

public class RequestDetails {
    public String vehicle_type,vehicle_num, service_type,pref_date, comments, mail_uname;
    public int reqid;
    public  RequestDetails(int reqid, String vehicle_type, String vehicle_num, String service_type, String pref_date, String comments, String username){
        this.reqid=reqid;
        this.vehicle_type=vehicle_type;
        this.vehicle_num=vehicle_num;
        this.service_type=service_type;
        this.pref_date=pref_date;
        this.comments=comments;
        this.mail_uname=username;
    }

    @Override
    public String toString() {
        return vehicle_type +"--->"+vehicle_num;
    }
}
