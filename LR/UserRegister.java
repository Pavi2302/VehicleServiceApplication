package LR;
import Req.DbConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegister extends JFrame {
    private JPanel panMain;
    private JLabel imgUserReg;
    private JLabel lblTitle;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblUserName;
    private JLabel lblEmail;
    private JLabel lblCustID;
    private JLabel lblPassword;
    private JLabel lblCnfPass;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtCustID;
    private JTextField txtUserName;
    private JPasswordField pfPass;
    private JPasswordField pfCnfPass;
    private JButton btnSubmit;
    private JButton btnClear;
    private JLabel lblValFirstName;
    private JLabel lblValLastName;
    private JLabel lblValEmail;
    private JLabel lblValCustID;
    private JLabel lblValUsername;
    private JLabel lblValPass;
    private JLabel lblValCnfPass;
    private JButton btnExit;
    private final Connection con = DbConnect.getconnection();
    private boolean submit_first_name = false, submit_last_name = false, submit_email = false,
            submit_username = false, submit_password = false, submit_cust_id = false, submit_cnf_pass = false;

    public void makeNull() {
        txtFirstName.setText(null);
        lblValFirstName.setText(null);
        txtLastName.setText(null);
        lblValLastName.setText(null);
        txtEmail.setText(null);
        lblValEmail.setText(null);
        txtUserName.setText(null);
        lblValUsername.setText(null);
        pfPass.setText(null);
        lblValPass.setText(null);
        pfCnfPass.setText(null);
        lblValCnfPass.setText(null);
        txtCustID.setText(null);
        lblValCustID.setText(null);
        submit_cnf_pass = false;
        submit_email = false;
        submit_first_name = false;
        submit_last_name = false;
        submit_username = false;
        submit_password = false;
        submit_cust_id = false;
    }

    public UserRegister(String title) {
        super(title);
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        this.setContentPane(panMain);
        imgUserReg.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\add_user.png"));
        txtFirstName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^[a-z A-Z]{5,30}$";
                Pattern pat = Pattern.compile(pattern);
                Matcher match = pat.matcher(txtFirstName.getText());
                if (match.matches()) {
                    submit_first_name = true;
                    lblValFirstName.setText(null);
                } else {
                    submit_first_name = false;
                    lblValFirstName.setText("Only Alphabets! (Length:5 to 30)");
                }
            }
        });
        txtLastName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^[a-z A-Z]{5,30}$";
                Pattern pat = Pattern.compile(pattern);
                Matcher match = pat.matcher(txtLastName.getText());
                if (match.matches()) {
                    submit_last_name = true;
                    lblValLastName.setText(null);
                } else {
                    submit_last_name = false;
                    lblValLastName.setText("Only Alphabets! (Length:5 to 30)");
                }
            }
        });
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9].+[a-z]{2,8}$";
                Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                Matcher match = pat.matcher(txtEmail.getText());
                if (match.matches()) {
                    submit_email = true;
                    lblValEmail.setText(null);
                } else {
                    submit_email = false;
                    lblValEmail.setText("Incorrect Mail ID!");
                }
            }
        });
        txtUserName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^[a-zA-z0-9]{3,15}$";
                Pattern pat = Pattern.compile(pattern);
                Matcher mat = pat.matcher(txtUserName.getText());
                if (mat.matches()) {
                    submit_username = true;
                    lblValUsername.setText(null);
                } else {
                    submit_username = false;
                    lblValUsername.setText("Incorrect! (Length 3 to 15)");
                }
            }
        });
        txtCustID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^[0-9]{5,6}$";
                Pattern pat = Pattern.compile(pattern);
                Matcher mat = pat.matcher(txtCustID.getText());
                if (mat.matches()) {
                    submit_cust_id = true;
                    lblValCustID.setText(null);
                } else {
                    submit_cust_id = false;
                    lblValCustID.setText("Incorrect! Only six digit number");
                }
            }
        });
        pfPass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z[^0-9a-zA-Z]]{6,}$";
                Pattern pat = Pattern.compile(pattern);
                Matcher mat = pat.matcher(pfPass.getText());
                if (mat.matches()) {
                    submit_password = true;
                    lblValPass.setText(null);
                } else {
                    submit_password = false;
                    lblValPass.setText("Must contain a number (Length>6)");
                }
            }
        });
        pfCnfPass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (pfCnfPass.getText().equals(pfPass.getText())) {
                    submit_cnf_pass = true;
                    lblValCnfPass.setText(null);
                } else {
                    submit_cnf_pass = false;
                    lblValCnfPass.setText("Passwords Not Matching");
                }
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (submit_cnf_pass && submit_password && submit_first_name && submit_last_name && submit_email && submit_username && submit_cust_id) {
                    try {
                        String check = "SELECT valid FROM cust_unique where custid=?";
                        PreparedStatement chk = con.prepareStatement(check);
                        chk.setString(1, txtCustID.getText());
                        ResultSet up_rs = chk.executeQuery();
                        if (!up_rs.next()) {
                            JOptionPane.showMessageDialog(null, "Customer ID does not exist!", "Regsitration Failed", JOptionPane.INFORMATION_MESSAGE);
                            makeNull();
                        } else {
                            if (up_rs.getInt(1) == 0) {
                                JOptionPane.showMessageDialog(null, "Customer ID already taken!", "Regsitration Failed", JOptionPane.INFORMATION_MESSAGE);
                                makeNull();
                            } else {
                                String query = "INSERT INTO users(fname,lname,username,pass,email,custid) VALUES(?,?,?,?,?,?)";
                                PreparedStatement pr = con.prepareStatement(query);
                                pr.setString(1, txtFirstName.getText());
                                pr.setString(2, txtLastName.getText());
                                pr.setString(3, txtUserName.getText());
                                pr.setString(4, pfPass.getText());
                                pr.setString(5, txtEmail.getText());
                                pr.setString(6, txtCustID.getText());
                                pr.executeUpdate();
                                String update = "UPDATE cust_unique SET valid=? where custid=?";
                                PreparedStatement up = con.prepareStatement(update);
                                up.setInt(1, 0);
                                up.setString(2, txtCustID.getText());
                                up.executeUpdate();
                                int a = JOptionPane.showConfirmDialog(null, "Successfully Registered! Do you want to login now?");
                                if (a == 0) {
                                    dispose();
                                } else {
                                    makeNull();
                                }
                            }
                        }
                    } catch (SQLException E) {
                        JOptionPane.showMessageDialog(null, "Username/EMail already exists", "Error!", JOptionPane.INFORMATION_MESSAGE);
                        txtUserName.setText(null);
                        txtEmail.setText(null);
                        submit_email = false;
                        submit_username = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Correct the form errors and try again!", "Unsuccessful!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(null, "Are you sure? you want to clear the inputs");
                if (a == 0) {
                    makeNull();
                }
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.setBounds(650, 200, 600, 700);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new UserRegister("User Registration | Bike Care");
    }

}
