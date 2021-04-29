package Req;

public class JobCardDetails {
    public int job_id,r_id;
    public String vehicle_num,vehicle_type,engineer, scratches, quotation, delivery, comments;
    public JobCardDetails(String vehicle_type, String vehicle_num,String engineer, String scratches, String quotation, String delivery, int job_id, String comments, int rid){
        this.vehicle_type=vehicle_type;
        this.vehicle_num=vehicle_num;
        this.engineer=engineer;
        this.scratches=scratches;
        this.quotation=quotation;
        this.delivery=delivery;
        this.job_id=job_id;
        this.comments=comments;
        this.r_id=rid;
    }

    @Override
    public String toString() {
        return "Vehicle :"+vehicle_type+"---->"+" "+vehicle_num;

    }
}
