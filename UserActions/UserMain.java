package UserActions;

import Req.DbConnect;
import Req.JobCardDetails;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMain extends JFrame {
    private JPanel panMain;
    private JPanel panSidePanel;
    private JPanel panSelect;
    private JPanel panSideReq;
    private JPanel panSideNoti;
    private JPanel panSideHistory;
    private JPanel panReq;
    private JPanel panNotification;
    private JPanel panHistory;
    private JLabel lblServiceReq;
    private JLabel lblNotification;
    private JLabel lblHistory;
    private JLabel imgServReq;
    private JLabel imgNotification;
    private JLabel imgHistory;
    private JLabel lblTitleServReq;
    private JLabel lblTitleNotification;
    private JLabel lblTitleHistory;
    private JLabel lblVehType;
    private JComboBox cmbVehType;
    private JLabel lblServType;
    private JLabel lblDate;
    private JLabel lblComm;
    private JTextArea txtAreaComments;
    private JTextField txtVehicleNum;
    private JLabel lblVehNum;
    private JLabel lblValVehNum;
    private JPanel panDatePicker;
    private JButton btnSubmit;
    private JPanel NotifList;
    private JPanel NotiInfo;
    private JList jlistNotification;
    private JLabel lblTitleJobCard;
    private JLabel lblServEngName;
    private JLabel lblScratches;
    private JTextField txtDisplayName;
    private JTextField txtDisplayDelDate;
    private JLabel lblDelDate;
    private JLabel lblQuotation;
    private JTextArea txtDisplayQuot;
    private JButton btnApprove;
    private JButton btnExit;
    private JButton btnContact;
    private JButton btnClear;
    private JPanel panWelcome;
    private JLabel imgWelcome;
    private JLabel lblWelcome;
    private JComboBox cmbServType;
    private JTextArea txtAreaDisplayScratches;
    private JTextArea txtAreaDsiplayComments;
    private JLabel lblComment;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private JDateChooser inpDate = new JDateChooser();
    private Connection con = DbConnect.getconnection();
    private boolean submit_num = false;
    public DefaultListModel<JobCardDetails> lm;
    private String uname = null;
    private int selected_job = -1;
    private int selected_rid_notif = -1;

    public void updateNotification() {
        lm = new DefaultListModel<>();
        try {
            PreparedStatement pr = con.prepareStatement("select vehicletype,vehicleNumber,engineer,scratches,quotation,estimated_delivery,jobcardid,jobcard.comments,jobcard.reqid from jobcard join request on jobcard.reqid=request.reqid where request.username=? and confirmed=?");
            pr.setString(1, uname);
            pr.setInt(2, 0);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                JobCardDetails temp = new JobCardDetails(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4).toString(), rs.getString(5), rs.getDate(6).toString(), rs.getInt(7), rs.getString(8), rs.getInt(9));
                lm.addElement(temp);
                System.out.println(temp.vehicle_num + temp.vehicle_type);
            }
            jlistNotification.setModel(lm);
        } catch (SQLException E) {
            E.printStackTrace();
        }
    }

    void makeNull() {
        txtVehicleNum.setText(null);
        submit_num = false;
        txtAreaComments.setText(null);
        lblValVehNum.setText(null);
        inpDate.setDate(null);
    }

    public void makeNullNot() {
        txtAreaDisplayScratches.setText(null);
        txtDisplayQuot.setText(null);
        txtDisplayDelDate.setText(null);
        txtDisplayName.setText(null);
    }

    public UserMain(String Username, String fname, String title) {
        super(title);
        this.uname = Username;
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        jlistNotification.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int select = jlistNotification.getSelectedIndex();
                JobCardDetails jobcard = (JobCardDetails) lm.getElementAt(select);
                txtDisplayName.setText(jobcard.engineer);
                txtAreaDisplayScratches.setText(jobcard.scratches);
                txtDisplayDelDate.setText(jobcard.delivery);
                txtDisplayQuot.setText(jobcard.quotation);
                txtAreaDsiplayComments.setText(jobcard.comments);
                selected_rid_notif = jobcard.r_id;
                selected_job = jobcard.job_id;
            }
        });
        inpDate.setDateFormatString("dd/MM/yyyy");
        inpDate.setFont(new Font("Microsoft Jhenghei", Font.BOLD, 18));
        inpDate.setBackground(new Color(29, 29, 29));
        panDatePicker.add(inpDate);
        imgServReq.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\serv_req.png"));
        imgWelcome.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\welcome.png"));
        imgHistory.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\history.png"));
        imgNotification.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\notification.png"));
        CardLayout select = (CardLayout) panSelect.getLayout();
        select.show(panSelect, "Welcome");
        panSideHistory.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSideNoti.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSideReq.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtDisplayName.setEditable(false);
        txtDisplayDelDate.setEditable(false);
        txtDisplayQuot.setEditable(false);
        txtAreaDisplayScratches.setEditable(false);
        txtAreaDisplayScratches.setEditable(false);
        panSideReq.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect, "Request");
            }
        });
        panSideNoti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                updateNotification();
                makeNullNot();
                select.show(panSelect, "Notification");
                panNotification.revalidate();
            }
        });
        panSideHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect, "History");
            }
        });
        btnApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Enter your password to confirm your approval!");
                JPasswordField pass = new JPasswordField(10);
                panel.add(label);
                panel.add(pass);
                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(null, panel, "Confirm Approval",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
                if (option == 0) {
                    String password = pass.getText();
                    System.out.println(password);
                    try {
                        PreparedStatement pr = con.prepareStatement("SELECT pass FROM users where username=?");
                        pr.setString(1, Username);
                        ResultSet rs = pr.executeQuery();
                        rs.next();
                        System.out.println(rs.getString(1));
                        if (rs.getString(1).equals(password)) {
                            PreparedStatement update = con.prepareStatement("UPDATE jobcard SET confirmed=? where jobcardid=?");
                            update.setInt(1, 1);
                            update.setInt(2, selected_job);
                            update.executeUpdate();
                            String status = "Job Card Confirmed, service has been initiated";
                            PreparedStatement upd_status = con.prepareStatement("UPDATE status SET status=? where reqid=?");
                            upd_status.setString(1, status);
                            upd_status.setInt(2, selected_rid_notif);
                            upd_status.executeUpdate();
                            JOptionPane.showMessageDialog(null, "JobCard Request have been approved successfully!", "Approval Successful", JOptionPane.INFORMATION_MESSAGE);
                            makeNullNot();
                        } else {
                            JOptionPane.showMessageDialog(null, "Password mismatch!", "Approval UnSuccessful", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException E) {
                        E.printStackTrace();
                    }
                }
            }
        });
        lblWelcome.setText("Welcome " + fname + "!");
        txtVehicleNum.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pattern = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{1,4}$";
                Pattern pat = Pattern.compile(pattern);
                Matcher match = pat.matcher(txtVehicleNum.getText());
                if (match.matches()) {
                    submit_num = true;
                    lblValVehNum.setText(null);
                } else {
                    submit_num = false;
                    lblValVehNum.setText("Incorrect! Should be in form (TN38BH7796)");
                }
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (submit_num) {
                    try {
                        System.out.println(sdf.format(inpDate.getDate()));
                        PreparedStatement check = con.prepareStatement("SELECT * FROM request WHERE vehicleNumber=?");
                        check.setString(1, txtVehicleNum.getText());
                        ResultSet chk = check.executeQuery();
                        if (!chk.next()) {
                            PreparedStatement pr = con.prepareStatement("INSERT INTO request(username,vehicletype,vehicleNumber,servicetype,prefDate,comments) VALUES(?,?,?,?,?,?)");
                            pr.setString(1, Username);
                            pr.setString(2, cmbVehType.getSelectedItem().toString());
                            pr.setString(3, txtVehicleNum.getText());
                            pr.setString(4, cmbServType.getSelectedItem().toString());
                            pr.setDate(5, Date.valueOf(sdf.format(inpDate.getDate())));
                            pr.setString(6, txtAreaComments.getText());
                            pr.executeUpdate();
                            PreparedStatement id = con.prepareStatement("SELECT reqid FROM request WHERE username=? and vehicleNumber=?");
                            id.setString(1, Username);
                            id.setString(2, txtVehicleNum.getText());
                            ResultSet rs = id.executeQuery();
                            rs.next();
                            PreparedStatement ins = con.prepareStatement("INSERT INTO status(reqid,username) VALUES(?,?)");
                            ins.setInt(1, rs.getInt(1));
                            ins.setString(2, Username);
                            ins.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Request has been registered successfully and RID is " + rs.getInt(1), "Successful", JOptionPane.INFORMATION_MESSAGE);
                            makeNull();
                        } else {
                            JOptionPane.showMessageDialog(null, "Request has been registered already and it is pending", "Kindly Wait!", JOptionPane.INFORMATION_MESSAGE);
                            makeNull();
                        }
                    } catch (SQLException E) {
                        E.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Correct the form errors and try again!", "Unsuccessful!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeNull();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.setContentPane(panMain);
        this.setBounds(200, 125, 1600, 800);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new UserMain(null, null, "User Main");
    }

}
