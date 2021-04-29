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
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceEngineerRegister extends JFrame {
    private JLabel lblTitle;
    private JLabel imgServEngReg;
    private JLabel lblFirstName;
    private JTextField txtFirstName;
    private JPasswordField pfPass;
    private JLabel lblLastName;
    private JLabel lblEmail;
    private JLabel lblUsername;
    private JLabel lblPass;
    private JLabel lblConfPass;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtUsername;
    private JPasswordField pfCnfPass;
    private JLabel lblValFirstName;
    private JButton btnSubmit;
    private JButton btnExit;
    private JButton btnClear;
    private JLabel lblValLastName;
    private JLabel lblValEmail;
    private JLabel lblValUserName;
    private JLabel lblValPass;
    private JLabel lblValCnfPass;
    private JPanel panMain;
    private JTextField txtMobile;
    private JLabel lblMobile;
    private JLabel lblValMobile;
    private boolean submit_first_name = false, submit_last_name = false, submit_email = false,
            submit_username = false, submit_password = false, submit_cnf_pass = false,submit_mobile=false;
    private final Connection con = DbConnect.getconnection();

    public void makeNull() {
        txtFirstName.setText(null);
        lblValFirstName.setText(null);
        txtLastName.setText(null);
        lblValLastName.setText(null);
        txtEmail.setText(null);
        lblValEmail.setText(null);
        txtUsername.setText(null);
        lblValUserName.setText(null);
        pfPass.setText(null);
        lblValPass.setText(null);
        pfCnfPass.setText(null);
        lblValCnfPass.setText(null);
        lblValMobile.setText(null);
        submit_cnf_pass = false;
        submit_email = false;
        submit_first_name = false;
        submit_last_name = false;
        submit_username = false;
        submit_password = false;
        submit_mobile=false;
    }

    public ServiceEngineerRegister(String title) {
        super(title);
        this.setContentPane(panMain);
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        this.setBounds(650, 200, 600, 700);
        imgServEngReg.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\add_eng.png"));
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
        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^[a-zA-z0-9]{3,15}$";
                Pattern pat = Pattern.compile(pattern);
                Matcher mat = pat.matcher(txtUsername.getText());
                if (mat.matches()) {
                    submit_username = true;
                    lblValUserName.setText(null);
                } else {
                    submit_username = false;
                    lblValUserName.setText("Incorrect! (Length 3 to 15)");
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
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(null, "Are you sure? you want to clear the inputs");
                if (a == 0) {
                    makeNull();
                }
            }
        });
        txtMobile.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^[0-9]{10}$";
                Pattern pat = Pattern.compile(pattern);
                Matcher mat = pat.matcher(txtMobile.getText());
                if (mat.matches()) {
                    submit_mobile = true;
                    lblValMobile.setText(null);
                } else {
                    submit_mobile = false;
                    lblValMobile.setText("Not a valid phone number!");
                }
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (submit_cnf_pass && submit_password && submit_first_name && submit_last_name && submit_email && submit_username && submit_mobile) {
                    try {
                        PreparedStatement ps = con.prepareStatement("INSERT INTO serviceeng(fname,lname,email,username,pass,mobile) " + "VALUES (?,?,?,?,?,?) ");
                        ps.setString(1, txtFirstName.getText());
                        ps.setString(2, txtLastName.getText());
                        ps.setString(3, txtEmail.getText());
                        ps.setString(4, txtUsername.getText());
                        ps.setString(5, pfPass.getText());
                        ps.setString(6,txtMobile.getText());
                        ps.executeUpdate();
                        int a = JOptionPane.showConfirmDialog(null, "Successfully Registered! Do you want to login now?");
                        if (a == 0) {
                            dispose();
                        } else {
                            makeNull();
                        }
                    } catch (SQLException E) {
                        E.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Username/EMail already exists", "Error!", JOptionPane.INFORMATION_MESSAGE);
                        txtUsername.setText(null);
                        txtEmail.setText(null);
                        submit_email = false;
                        submit_username = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Correct the form errors and try again!", "Unsuccessful!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ServiceEngineerRegister("Engineer Registration | Bike Care");
    }

}
