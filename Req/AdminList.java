package Req;

public class AdminList {
    public int reqid,confirmed;
    public String veh_type,veh_num,serv_type,pref_date,eng,delivery;
    public AdminList(int reqid, String veh_type, String veh_num, String serv_type, String pref_date, String eng, String delivery, int confirmed){
        this.reqid=reqid;
        this.veh_type=veh_type;
        this.veh_num=veh_num;
        this.serv_type=serv_type;
        this.pref_date=pref_date;
        this.eng=eng;
        this.delivery=delivery;
        this.confirmed=confirmed;
    }
}
