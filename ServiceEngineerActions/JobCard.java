package ServiceEngineerActions;

import Req.DbConnect;
import com.toedter.calendar.JDateChooser;
import Billing.bill;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

public class JobCard extends JFrame {

    public JPanel panMain;
    public JLabel imgJobCard;
    public JLabel lblTitleJC;
    public JLabel lblFirstName;
    private JTextArea txtAreaScratches;
    public JTextArea txtAreaQuot;
    private JButton btnSubmit;
    private JButton btnGenerateQuot;
    private JLabel lblQuotation;
    private JLabel lblDate;
    private JPanel panDate;
    private JLabel lblComments;
    private JTextArea txtAreaComments;
    private Connection con = DbConnect.getconnection();
    public JobCard obj;
    void openbill(){
        new bill("Bill Generator", this);
    }
    JobCard(String title, String username, String fname, int rid){
        super(title);
        obj=this;
        this.setContentPane(panMain);
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        JDateChooser dc = new JDateChooser();
        imgJobCard.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\jobcard.png"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dc.setDateFormatString("dd/MM/yyyy");
        dc.setFont(new Font("Microsoft Jhenghei",Font.BOLD,18));
        dc.setBackground(new Color(29,29,29));
        panDate.add(dc);
        this.setBounds(600,100,800,900);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);


        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtAreaQuot.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Quotation is empty! Kindly generate Quotation and proceed!");
                }
                else {
                    try {
                        PreparedStatement pr = con.prepareStatement("INSERT INTO jobcard(reqid,engineer,scratches,quotation,comments,estimated_delivery,username)" +
                                "VALUES (?,?,?,?,?,?,?)");
                        pr.setInt(1, rid);
                        pr.setString(2, fname);
                        pr.setString(3,txtAreaScratches.getText());
                        pr.setString(4,txtAreaQuot.getText());
                        pr.setString(5,txtAreaComments.getText());
                        pr.setDate(6,Date.valueOf(sdf.format(dc.getDate())));
                        pr.setString(7,username);
                        pr.executeUpdate();
                        PreparedStatement upd_taken=con.prepareStatement("UPDATE response set taken=? where reqid=?");
                        upd_taken.setInt(1,1);
                        upd_taken.setInt(2,rid);
                        upd_taken.executeUpdate();
                        String status = "Job Card created by Mr."+fname +"! Waiting for confirmation";
                        PreparedStatement upd_status = con.prepareStatement("UPDATE status SET status=? where reqid=?");
                        upd_status.setString(1,status);
                        upd_status.setInt(2,rid);
                        upd_status.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Job card has been created successfully!");
                        dispose();
                    } catch (SQLException E) {
                        E.printStackTrace();
                    }
                }
            }
        });
        btnGenerateQuot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openbill();
            }
        });
    }
    JobCard(String title, String username, String fname, int rid,boolean update) {
        this.setContentPane(panMain);
        JDateChooser dc = new JDateChooser();
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        imgJobCard.setIcon(new ImageIcon("E:\\Vehicle Management System\\jobcard.png"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dc.setDateFormatString("dd/MM/yyyy");
        dc.setFont(new Font("Microsoft Jhenghei",Font.BOLD,18));
        dc.setBackground(new Color(29,29,29));
        panDate.add(dc);
        txtAreaScratches.setEditable(false);
        btnSubmit.setText("Update");
        try{
            PreparedStatement fill = con.prepareStatement("SELECT scratches,quotation,comments,estimated_delivery from jobcard where reqid=?");
            fill.setInt(1,rid);
            ResultSet fill_job = fill.executeQuery();
            fill_job.next();
            txtAreaScratches.setText(fill_job.getString(1));
            txtAreaQuot.setText(fill_job.getString(2));
            txtAreaComments.setText(fill_job.getString(3));
            dc.setDate(fill_job.getDate(4));
        }catch (SQLException E){
            E.printStackTrace();
        }
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtAreaQuot.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Quotation is empty! Kindly generate Quotation and proceed!");
                }
                else{
                    try {
                        PreparedStatement pr = con.prepareStatement("UPDATE jobcard set quotation=?,comments=?,estimated_delivery=?,confirmed=? where reqid=?");
                        pr.setString(1,txtAreaQuot.getText());
                        pr.setString(2, txtAreaComments.getText());
                        pr.setDate(3,Date.valueOf(sdf.format(dc.getDate())));
                        pr.setInt(4,0);
                        pr.setInt(5,rid);
                        pr.executeUpdate();
                        String status = "Job Card updated by Mr."+fname +"! Waiting for confirmation";
                        PreparedStatement upd_status = con.prepareStatement("UPDATE status SET status=? where reqid=?");
                        upd_status.setString(1,status);
                        upd_status.setInt(2,rid);
                        upd_status.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Job card has been updated successfully!");
                        dispose();
                    } catch (SQLException E) {
                        E.printStackTrace();
                    }
                }
            }

        });
        btnGenerateQuot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openbill();
            }
        });
        this.setBounds(600,100,800,900);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }


    public static void main(String[] args) {
        new JobCard("New Jobcard",null,null,-1);


    }
}
