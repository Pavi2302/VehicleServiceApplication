package Req;

public class Delivery {
    public String veh_type,veh_num,serv_type,pref_date,comments,uname,quotation,mail_uname;
    public int rid;
    public Delivery(String veh_type, String veh_num, String serv_type, String pref_date, String comments, String uname, int rid,String quotation, String mail_uname){
        this.veh_type=veh_type;
        this.veh_num=veh_num;
        this.serv_type=serv_type;
        this.pref_date=pref_date;
        this.comments=comments;
        this.uname=uname;
        this.rid=rid;
        this.quotation=quotation;
        this.mail_uname=mail_uname;
    }

    @Override
    public String toString() {
        return veh_type+"--->"+veh_num;
    }
}
