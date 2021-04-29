/*
    Author:Parvathy Nathan S
    Batch :23, SKIB 2022
    Bootathon I Day 1 submission
*/
import AdminActions.AdminMain;
import ServiceEngineerActions.ServiceEngMain;
import UserActions.UserMain;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WelcomePanel extends JFrame {
    private JPanel panMain;
    private JPanel panSidePanel;
    private JLabel imgUser;
    private JLabel imgAdmin;
    private JPanel panSelect;
    private JPanel panUser;
    private JLabel lblTitleUser;
    private JLabel lblUserNameUser;
    private JTextField txtUserNameUser;
    private JLabel lblPassUser;
    private JButton btnRegUser;
    private JButton btnLoginUser;
    private JPasswordField jpassPassUser;
    private JPanel panServEng;
    private JTextField txtUserNameServ;
    private JLabel lblUserNameServ;
    private JLabel lblPassServ;
    private JButton btnRegisterServ;
    private JButton btnLoginServ;
    private JPasswordField jpassPassServ;
    private JLabel lblTitleServ;
    private JPanel panAdmin;
    private JTextField txtUserNameAdmin;
    private JLabel lblUserNameAdmin;
    private JLabel lblPassAdmin;
    private JButton btnLoginAdmin;
    private JPasswordField jpassPassAdmin;
    private JLabel lblTitleAdmin;
    private JPanel panStart;
    private JLabel imgLogo;
    private JLabel lblInfo;
    private JPanel panSideUser;
    private JPanel panSideServ;
    private JPanel panSideAdmin;
    private JLabel imgServEng;
    private JLabel lbluserLogin;
    private JLabel lblAdminLogin;
    private JLabel lblServEngLogin;
    private JButton btnExit;
    private JButton btnContact;
    private final Connection con = Req.DbConnect.getconnection();
    void makeNullUser(){
        txtUserNameUser.setText(null);jpassPassUser.setText(null);
    }
    void makeNullSerEng(){
        txtUserNameServ.setText(null); jpassPassServ.setText(null);
    }
    void makeNullAdmin(){
        txtUserNameAdmin.setText(null); jpassPassAdmin.setText(null);
    }
    WelcomePanel(String title){
        super(title);
        this.setContentPane(panMain);
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        imgUser.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_user.png"));
        imgAdmin.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\admin.png"));
        imgServEng.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\serv_eng.png"));
        imgLogo.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png"));

        CardLayout select = (CardLayout) panSelect.getLayout();
        select.show(panSelect, "welcome");
        panSideUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSideAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSideServ.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panSideUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                select.show(panSelect, "user");
            }
        });
        panSideAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect, "admin");
            }
        });
        panSideServ.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect, "serveng");
            }
        });
        btnRegUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LR.UserRegister("User Registration | Bike Care");
            }
        });
        btnLoginUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String query="SELECT fname from users where username=? and pass=?";
                    PreparedStatement pr = con.prepareStatement(query);
                    pr.setString(1,txtUserNameUser.getText());
                    pr.setString(2,jpassPassUser.getText());
                    ResultSet rs = pr.executeQuery();
                    if(!rs.next()){
                        JOptionPane.showMessageDialog(null,"User does not exist!","Login Failed! Incorrect Credentials",JOptionPane.INFORMATION_MESSAGE);
                        makeNullUser();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Welcome "+rs.getString(1)+"!","Login Successful!",JOptionPane.INFORMATION_MESSAGE);
                        new UserMain(txtUserNameUser.getText(),rs.getString(1),rs.getString(1)+"'s Dashbaord | Bike Care");
                        makeNullUser();
                    }
                }catch (SQLException E){
                    E.printStackTrace();
                }
            }
        });
        btnRegisterServ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LR.ServiceEngineerRegister("Engineer Registration | Bike Care");
            }
        });
        btnLoginServ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT fname,approved from serviceeng where username=? and pass=?";
                    PreparedStatement pr = con.prepareStatement(query);
                    pr.setString(1, txtUserNameServ.getText());
                    pr.setString(2, jpassPassServ.getText());
                    String uname=txtUserNameServ.getText();
                    System.out.println(uname);
                    ResultSet rs = pr.executeQuery();
                    if(!rs.next()){
                        JOptionPane.showMessageDialog(null,"Service Engineer does not exist!","Login Failed! Incorrect Credentials",JOptionPane.INFORMATION_MESSAGE);
                        makeNullSerEng();
                    }
                    else{
                        if(rs.getInt(2)==0){
                            JOptionPane.showMessageDialog(null,"Approval not yet given!","Login Failed!",JOptionPane.INFORMATION_MESSAGE);
                            makeNullSerEng();
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Welcome "+rs.getString(1)+"!","Login Successful!",JOptionPane.INFORMATION_MESSAGE);
                            makeNullSerEng();
                            new ServiceEngMain(rs.getString(1)+"'s Dashboard",uname,rs.getString(1));
                        }

                    }
                }catch (SQLException E){
                    E.printStackTrace();
                }
            }
        });
        btnLoginAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    PreparedStatement pr = con.prepareStatement("SELECT fname from admin where username=? and pass=?");
                    pr.setString(1,txtUserNameAdmin.getText());
                    pr.setString(2,jpassPassAdmin.getText());
                    ResultSet rs=pr.executeQuery();
                    if(!rs.next()){
                        JOptionPane.showMessageDialog(null,"Admin does not exist!","Login Failed! Incorrect Credentials",JOptionPane.INFORMATION_MESSAGE);
                        makeNullAdmin();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Welcome "+rs.getString(1)+"!","Login Successful!",JOptionPane.INFORMATION_MESSAGE);
                        new AdminMain(rs.getString(1)+"'s Admin Panel",rs.getString(1),txtUserNameAdmin.getText());
                        makeNullAdmin();
                    }
                }catch (SQLException E){
                    E.printStackTrace();
                }
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.setBounds(575,250,800,500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        try{
            for(UIManager.LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch(Exception E){
            System.out.println(E);
        }
        new WelcomePanel("Bike Care - Home");
    }

}
