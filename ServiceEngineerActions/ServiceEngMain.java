package ServiceEngineerActions;
import Req.*;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServiceEngMain extends JFrame {
    private JPanel panMain;
    private JPanel panSide;
    private JPanel panSelect;
    private JPanel panPendingReq;
    private JPanel panPendingTake;
    private JPanel panPendingDelivery;
    private JPanel panSideReq;
    private JPanel panSideTake;
    private JPanel panSideDel;
    private JLabel lblPendingReq;
    private JLabel lblPeningTake;
    private JLabel lblPendingDel;
    private JLabel imgRequests;
    private JLabel imgTakes;
    private JLabel imgDel;
    private JLabel lblTitleReq;
    private JLabel lblPendingTake;
    private JLabel lblTitleDel;
    private JList jlistReq;
    private JLabel lblTitleJobCard;
    private JLabel lblVehicleType;
    private JLabel lblVehicleNum;
    private JTextField txtDisplayVehType;
    private JTextField txtDisplayVehicleNum;
    private JTextField txtDisplayServType;
    private JTextField txtDisplayPrefDate;
    private JTextArea txtAreaDisplayComments;
    private JButton btnExit;
    private JButton btnContactUs;
    private JLabel lblServType;
    private JLabel lblPrefDate;
    private JLabel lblComments;
    private JButton btnTake;
    private JPanel panListReq;
    private JPanel panInfoReq;
    private JPanel panListTake;
    private JPanel panTakeInfo;
    private JLabel lblTakeTitleDet;
    private JLabel lblTakeNum;
    private JLabel lblTakeType;
    private JLabel lblTakeServType;
    private JLabel lblTakeTime;
    private JList jlistTake;
    private JButton btnCreateJob;
    private JLabel lblTakeComments;
    private JTextField txtDisplayTakeVehType;
    private JTextField txtDisplayTakeVehNum;
    private JTextField txtDisplayTakeServType;
    private JTextField txtDisplayTakeTime;
    private JTextArea txtAreaDisplayTakeComment;
    private JPanel panWelcome;
    private JLabel imgWelcome;
    private JLabel lblWelcomeMessage;
    private JList jlistDel;
    private JPanel panListDel;
    private JPanel panInfoDel;
    private JLabel lblTitleDelInfo;
    private JLabel lblDelVehicleType;
    private JLabel lblDelVehicleNum;
    private JLabel lblDelServiceType;
    private JLabel lblDelPromDate;
    private JLabel lblDelComments;
    private JTextField txtDisplayDelVehType;
    private JTextField txtDisplayDelVehNum;
    private JTextField txtDisplayDelServType;
    private JTextField txtDisplayDelPromDate;
    private JTextArea txtAreaDisplayDelComments;
    private JButton btnCompleted;
    private JButton btnUpdateJob;
    private JTextArea txtAreaDisplayDelQuotation;
    private JLabel lblQuotDel;
    private DefaultListModel<RequestDetails> reqDM;
    private DefaultListModel<ResponseDetails> takeDM;
    private DefaultListModel<Delivery> delDM;
    private final Connection con = DbConnect.getconnection();
    private int selected_req_id=-1,selected_job_req_id=-1,selected_job_upd_req_id=-1;
    String selected_date=null, uname,selected_veh_num_mail=null;
    void makenull(){
        txtDisplayDelServType.setText(null);
        txtDisplayPrefDate.setText(null);
        txtAreaDisplayTakeComment.setText(null);
        txtAreaDisplayComments.setText(null);
        txtAreaDisplayDelQuotation.setText(null);
        txtAreaDisplayDelComments.setText(null);
        txtDisplayDelPromDate.setText(null);
        txtDisplayDelVehNum.setText(null);
        txtDisplayDelVehType.setText(null);
        txtDisplayServType.setText(null);
        txtDisplayTakeServType.setText(null);
        txtDisplayTakeVehNum.setText(null);
        txtDisplayTakeTime.setText(null);
        txtDisplayVehicleNum.setText(null);
        txtDisplayTakeVehType.setText(null);
        txtDisplayVehType.setText(null);
    }
    void updateReqPane(){
        try{
            reqDM = new DefaultListModel<>();
            PreparedStatement get = con.prepareStatement("SELECT reqid,vehicletype,vehicleNumber,servicetype,prefDate,comments,username FROM request where attended=? ORDER BY prefDate");
            get.setInt(1,0);
            ResultSet reqSet = get.executeQuery();
            while(reqSet.next()){
                RequestDetails temp = new RequestDetails(reqSet.getInt(1),reqSet.getString(2),reqSet.getString(3),reqSet.getString(4),reqSet.getDate(5).toString(),reqSet.getString(6),reqSet.getString(7));
                reqDM.addElement(temp);
            }
            jlistReq.setModel(reqDM);
        }catch (SQLException E){
            E.printStackTrace();
        }
    }
    void updateTakePane(){
        try{
            takeDM = new DefaultListModel<>();
            PreparedStatement get = con.prepareStatement("select vehicletype,vehicleNumber,servicetype,taketime,comments,request.reqid from request join response where request.reqid=response.reqid and taken=? and takedate=CURDATE() and uname=? ORDER BY taketime;");
            get.setInt(1,0);
            get.setString(2,uname);
            ResultSet takeSet = get.executeQuery();
            while(takeSet.next()){
                ResponseDetails temp = new ResponseDetails(takeSet.getString(1),takeSet.getString(2),takeSet.getString(3),takeSet.getTime(4).toString(),takeSet.getString(5),takeSet.getInt(6));
                takeDM.addElement(temp);
            }
            jlistTake.setModel(takeDM);

        }catch (SQLException E){
            E.printStackTrace();
        }
    }
    void updateDeliveryPane(){
        try{
            delDM = new DefaultListModel<>();
            PreparedStatement get = con.prepareStatement("select vehicletype,vehicleNumber,servicetype,prefDate,request.comments,jobcard.username,request.reqid,quotation,request.username from jobcard join request where jobcard.reqid=request.reqid and confirmed=? and jobcard.username=? ORDER BY estimated_delivery");
            get.setInt(1,1);
            get.setString(2,uname);
            ResultSet delSet = get.executeQuery();
            while(delSet.next()){
                    Delivery temp = new Delivery(delSet.getString(1),delSet.getString(2),delSet.getString(3),delSet.getDate(4).toString(),delSet.getString(5),delSet.getString(6),delSet.getInt(7),delSet.getString(8),delSet.getString(9));
                delDM.addElement(temp);
            }
            jlistDel.setModel(delDM);

        }catch (SQLException E){
            E.printStackTrace();
        }
    }
    public ServiceEngMain(String title, String username, String fname){
        super(title);
        uname=username;
        this.setContentPane(panMain);
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        System.out.println(username);
        imgWelcome.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\welcome.png"));
        lblWelcomeMessage.setText("Welcome "+fname+"!");
        CardLayout select = (CardLayout) panSelect.getLayout();
        select.show(panSelect,"Welcome");
        imgDel.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\pending_del.png"));
        imgRequests.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\pending_req.png"));
        imgTakes.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\pending_take.png"));
        panSideTake.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSideDel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSideReq.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtDisplayPrefDate.setEditable(false);
        txtDisplayVehicleNum.setEditable(false);
        txtDisplayVehType.setEditable(false);
        txtAreaDisplayComments.setEditable(false);
        txtDisplayServType.setEditable(false);
        txtDisplayTakeServType.setEditable(false);
        txtDisplayTakeTime.setEditable(false);
        txtDisplayTakeVehNum.setEditable(false);
        txtDisplayTakeVehType.setEditable(false);
        txtAreaDisplayTakeComment.setEditable(false);
        txtDisplayDelPromDate.setEditable(false);
        txtDisplayDelServType.setEditable(false);
        txtDisplayDelVehNum.setEditable(false);
        txtDisplayDelVehType.setEditable(false);
        txtAreaDisplayDelComments.setEditable(false);

        jlistReq.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index=jlistReq.getSelectedIndex();
                RequestDetails disp = reqDM.getElementAt(index);
                selected_req_id=disp.reqid;
                selected_date=disp.pref_date;
                selected_veh_num_mail=disp.vehicle_num;
                txtDisplayVehType.setText(disp.vehicle_type);
                txtDisplayVehicleNum.setText(disp.vehicle_num);
                txtAreaDisplayComments.setText(disp.comments);
                txtDisplayPrefDate.setText(disp.pref_date);
                txtDisplayServType.setText(disp.service_type);
            }
        });
        jlistTake.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index=jlistTake.getSelectedIndex();
                ResponseDetails disp = takeDM.getElementAt(index);
                selected_job_req_id=disp.reqid;
                txtDisplayTakeVehType.setText(disp.vehicle_type);
                txtDisplayTakeVehNum.setText(disp.vehicle_num);
                txtDisplayTakeServType.setText(disp.service_type);
                txtDisplayTakeTime.setText(disp.time);
                txtAreaDisplayTakeComment.setText(disp.comments);
            }
        });
        jlistDel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index=jlistDel.getSelectedIndex();
                Delivery del_details = delDM.getElementAt(index);
                selected_job_upd_req_id=del_details.rid;
                selected_veh_num_mail=del_details.veh_num;
                txtDisplayDelVehType.setText(del_details.veh_type);
                txtDisplayDelVehNum.setText(del_details.veh_num);
                txtDisplayDelServType.setText(del_details.serv_type);
                txtDisplayDelPromDate.setText(del_details.pref_date);
                txtAreaDisplayDelComments.setText(del_details.comments);
                txtAreaDisplayDelQuotation.setText(del_details.quotation);
            }
        });
        btnTake.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Enter the take time!");

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 24);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                SpinnerDateModel model = new SpinnerDateModel();
                model.setValue(calendar.getTime());

                JSpinner spinner = new JSpinner(model);
                JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
                DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
                formatter.setAllowsInvalid(false);
                formatter.setOverwriteMode(true);
                spinner.setEditor(editor);
                panel.add(label);
                panel.add(spinner);
                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(null, panel, "Take time?",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);

                if(option == 0){
                    Object value = spinner.getValue();
                    String time = null;
                    if(value instanceof Date){
                        Date d = (Date) value;
                        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                        time = df.format(d);
                        System.out.println(time);
                    }
                   try {
                        System.out.println(username);
                        PreparedStatement precheck = con.prepareStatement("SELECT * FROM response WHERE reqid=?");
                        precheck.setInt(1,selected_req_id);
                        ResultSet pre_chk=precheck.executeQuery();
                        pre_chk.next();
                        if(pre_chk.next()){
                            JOptionPane.showMessageDialog(null, "Job already taken by another service engineer! Try again", "Failed!", JOptionPane.INFORMATION_MESSAGE);
                            updateReqPane();
                        }
                        else {
                            PreparedStatement check = con.prepareStatement("SELECT * FROM response WHERE uname=? and taketime=? and takedate=?");
                            check.setString(1, username);
                            check.setTime(2, Time.valueOf(time));
                            check.setDate(3, java.sql.Date.valueOf(selected_date));
                            ResultSet rs = check.executeQuery();
                            if (!rs.next()) {
                                PreparedStatement res = con.prepareStatement("INSERT INTO response(uname,reqid,taketime,takedate) VALUES(?,?,?,?)");
                                res.setString(1, username);
                                res.setInt(2, selected_req_id);
                                res.setTime(3, Time.valueOf(time));
                                res.setDate(4, java.sql.Date.valueOf(selected_date));
                                res.executeUpdate();
                                PreparedStatement up = con.prepareStatement("UPDATE request SET attended=? where reqid=?");
                                up.setInt(1, 1);
                                up.setInt(2, selected_req_id);
                                up.executeUpdate();
                                String status = "Taken by Mr." + fname;
                                PreparedStatement upd_status = con.prepareStatement("UPDATE status SET status=? where reqid=?");
                                upd_status.setString(1, status);
                                upd_status.setInt(2, selected_req_id);
                                upd_status.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Job taken successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                                PreparedStatement details = con.prepareStatement("SELECT mobile FROM serviceeng WHERE username=?");
                                details.setString(1, username);
                                ResultSet num = details.executeQuery();
                                num.next();
                                String mobile_num = num.getString(1);
                                String message = "Dear Customer,\nYour request for service of Vehicle Number: " + selected_veh_num_mail + " will be taken by our Service Engineer " + fname + " at " + time + ", below are the contact details of the service engineer.\n" +
                                        "Name       \t:Mr." + fname +
                                        "\nContact No\t:" + mobile_num +
                                        "\n\n\nWith Regards\nBike Care Team";
                                PreparedStatement mail = con.prepareStatement("SELECT email from request join users where request.username=users.username and reqid=?");
                                mail.setInt(1, selected_req_id);
                                ResultSet mail_op = mail.executeQuery();
                                mail_op.next();
                                System.out.println(mail_op.getString(1));
                                String rec = mail_op.getString(1);
                                Runnable mail_send = new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            SendEmail.sendEmail(rec, message, "Take Time Alloted for your service!");
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }
                                    }
                                };
                                Thread send_mail = new Thread(mail_send);
                                send_mail.start();
                                makenull();
                            } else {
                                JOptionPane.showMessageDialog(null, "Take time already alloted for another customer! Try again with different time", "Failed!", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }catch (SQLException E){
                        E.printStackTrace();
                    } catch (Exception exception) {
                       exception.printStackTrace();
                   }
                }

            }
        });
        btnCompleted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int op = JOptionPane.showConfirmDialog(null, "Are you sure want to send delivery mail to the customer and close the request?");
                if (op == 0) {
                    try {
                        String status = "Service Completed! Delivery mail sent!";
                        PreparedStatement upd_status = con.prepareStatement("UPDATE status SET status=? where reqid=?");
                        upd_status.setString(1, status);
                        upd_status.setInt(2, selected_job_upd_req_id);
                        upd_status.executeUpdate();
                        PreparedStatement mail = con.prepareStatement("SELECT email from request join users where request.username=users.username and reqid=?");
                        mail.setInt(1,selected_job_req_id);
                        ResultSet mail_op=mail.executeQuery();
                        PreparedStatement del = con.prepareStatement("DELETE FROM request WHERE reqid=?");
                        del.setInt(1, selected_job_upd_req_id);
                        del.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Request Closed successfully!");
                        String message = "Dear Customer,\nThe service process of your vehicle number"+" has been completed! This mail is to confirm today's delivery of your vehicle\n\nWith Regards\nBike Care Team";

                        mail_op.next();
                        System.out.println(mail_op.getString(1));
                        String recieve = mail_op.getString(1);
                        Runnable mail_send = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SendEmail.sendEmail(recieve,message,"Service Completed!");
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        };
                        Thread send_mail = new Thread(mail_send);
                        send_mail.start();
                        makenull();
                    } catch (SQLException E) {
                        E.printStackTrace();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        btnUpdateJob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JobCard("Update Job Card | Bike Care",username,fname,selected_job_upd_req_id,true);
                makenull();
            }
        });
        btnCreateJob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    new JobCard("New Job Card | Bike Care",username,fname,selected_job_req_id);
                    makenull();
            }
        });
        panSideReq.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect,"Requests");
                updateReqPane();
            }
        });
        panSideDel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect,"Delivery");
                updateDeliveryPane();
            }
        });
        panSideTake.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect,"Take");
                updateTakePane();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds(200,125,1600,800);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        new ServiceEngMain("Service Engineer Dashboard",null,null);
    }

}
