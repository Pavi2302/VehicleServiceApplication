package Req;

public class ResponseDetails {
    public String vehicle_type,vehicle_num, service_type,time, comments;
    public int reqid;
    public ResponseDetails(String vehicle_type, String vehicle_num, String service_type, String time, String comments,int reqid){
        this.reqid=reqid;
        this.vehicle_type=vehicle_type;
        this.vehicle_num=vehicle_num;
        this.service_type=service_type;
        this.time=time;
        this.comments=comments;
    }

    @Override
    public String toString() {
        return vehicle_type +"--->"+vehicle_num+"--->"+time;
    }
}
