package AdminActions;
import Req.AdminList;
import Req.DbConnect;
import Req.ServiceEngineerRequests;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminMain extends JFrame {
    private JPanel panSidePanel;
    private JPanel panSideCreateCust;
    private JLabel lblCreateCustIDSide;
    private JLabel imgCreateCustId;
    private JPanel panSidePendingEng;
    private JLabel lblPendServEngRequests;
    private JLabel imgPendingEngReq;
    private JPanel panSideReqUnderProg;
    private JLabel lblPendingRequestsSide;
    private JLabel imgReqUnderProg;
    private JPanel panSelect;
    private JPanel panRCreateCustId;
    private JLabel lblTitleCreateCustID;
    private JLabel lblIDtobGen;
    private JButton btnGenerate;
    private JButton btnClear;
    private JList jlistEngReq;
    private JTextField txtDisplayFirstName;
    private JButton btnApprove;
    private JPanel panReqUnderProg;
    private JLabel lblTitleReqUnderProg;
    private JPanel panWelcome;
    private JLabel imgWelcome;
    private JLabel lblWelcome;
    private JLabel imgStatusCheck;
    private JTextField txtIDtobGen;
    private JTextField txtDisplayLastName;
    private JTextField txtDisplayEmail;
    private JPanel panReqUnderProgList;
    private JTable tabAllReq;
    private JTextField txtReqIDStatus;
    private JButton btnGetStatus;
    private JTextArea textAreaDisplayStatus;
    private JPanel panMain;
    private JPanel panSideStatusCheck;
    private JLabel lblValGenerated;
    private JLabel lblTitleStatus;
    private JLabel lblReqID;
    private JLabel lblStatusInfo;
    private JLabel lblTitleNotification;
    private JPanel EngReqfList;
    private JPanel EngReqInfo;
    private JLabel lblTitleJobCard;
    private JLabel lblFNameEng;
    private JLabel lblLastNameEng;
    private JLabel lblEmail;
    private boolean btn_generate = false;
    private Connection con = DbConnect.getconnection();
    private DefaultListModel<ServiceEngineerRequests> penReqDm;
    private String selected_username = null;
    private ArrayList<AdminList> admin_details;

    void updateEngDetails() {
        penReqDm = new DefaultListModel<>();
        try {
            PreparedStatement pr = con.prepareStatement("SELECT fname,lname,email,username from serviceeng where approved=?");
            pr.setInt(1, 0);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                ServiceEngineerRequests temp = new ServiceEngineerRequests(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                penReqDm.addElement(temp);
            }
            jlistEngReq.setModel(penReqDm);
        } catch (SQLException E) {
            E.printStackTrace();
        }

    }

    void GenerateTable() {

        try {
            admin_details = new ArrayList<>();
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("select request.reqid,vehicletype,vehicleNumber,servicetype,prefDate,engineer,estimated_delivery,confirmed from request join jobcard where request.reqid=jobcard.reqid");
            while (rs.next()) {
                AdminList temp = new AdminList(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5).toString(), rs.getString(6), rs.getDate(7).toString(), rs.getInt(8));
                admin_details.add(temp);
            }
        } catch (SQLException E) {
            E.printStackTrace();
        }
        DefaultTableModel tabMod = new DefaultTableModel(null, new String[]{"RID", "Veh Type", "Veh Num", "Serv Type", "Pref Date", "Engineer", "Est Del", "Confirmed"});
        Object[] row = new Object[8];
        for (int i = 0; i < admin_details.size(); i++) {
            row[0] = admin_details.get(i).reqid;
            row[1] = admin_details.get(i).veh_type;
            row[2] = admin_details.get(i).veh_num;
            row[3] = admin_details.get(i).serv_type;
            row[4] = admin_details.get(i).pref_date;
            row[5] = admin_details.get(i).eng;
            row[6] = admin_details.get(i).delivery;
            if (admin_details.get(i).confirmed == 1) {
                row[7] = "Yes";
            } else {
                row[7] = "No";
            }
            tabMod.addRow(row);
        }
        tabAllReq.setModel(tabMod);
    }

    public AdminMain(String title, String fname, String uname) {
        this.setContentPane(panMain);
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        imgCreateCustId.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\create_cust_id.png"));
        imgWelcome.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\welcome.png"));
        imgReqUnderProg.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\all_pending_req.png"));
        imgPendingEngReq.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\pending_eng_req.png"));
        lblWelcome.setText("Welcome " + fname + "!");
        tabAllReq.setRowHeight(5);
        CardLayout select = (CardLayout) panSelect.getLayout();
        select.show(panSelect, "Welcome");
        panSideReqUnderProg.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSidePendingEng.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSideCreateCust.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panSideCreateCust.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect, "CreateCustID");
            }
        });
        panSidePendingEng.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect, "CreateCustID");
                updateEngDetails();
            }
        });
        panSideReqUnderProg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                select.show(panSelect, "All Pending Req");
                GenerateTable();
            }
        });
        txtDisplayEmail.setEditable(false);
        txtDisplayFirstName.setEditable(false);
        txtDisplayLastName.setEditable(false);
        jlistEngReq.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = jlistEngReq.getSelectedIndex();
                ServiceEngineerRequests approve = penReqDm.getElementAt(index);
                selected_username = approve.username;
                txtDisplayFirstName.setText(approve.fname);
                txtDisplayLastName.setText(approve.lname);
                txtDisplayEmail.setText(approve.email);
            }
        });
        btnApprove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(null, "Do you want to approve the selected applicant?");
                if (a == 0) {
                    try {
                        PreparedStatement upd = con.prepareStatement("UPDATE serviceeng SET approved=? where username=?");
                        upd.setInt(1, 1);
                        upd.setString(2, selected_username);
                        upd.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Approved successfully!");
                    } catch (SQLException E) {
                        E.printStackTrace();
                    }
                }
            }
        });

        txtIDtobGen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                Pattern pat = Pattern.compile("^[1-9][0-9]?$|^100$");
                Matcher mat = pat.matcher(txtIDtobGen.getText());
                if (mat.matches()) {
                    btn_generate = true;
                    lblValGenerated.setText(null);
                } else {
                    btn_generate = false;
                    lblValGenerated.setText("Only numbers between 1-100");
                }
            }
        });
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btn_generate) {
                    int a = JOptionPane.showConfirmDialog(null, "Are you sure you want to continue");
                    if (a == 0) {
                        try {
                            int cnt = 0;
                            while (cnt != Integer.parseInt(txtIDtobGen.getText())) {
                                PreparedStatement pr = con.prepareStatement("INSERT INTO cust_unique(custid) values(ceiling(RAND()*1000000))");
                                pr.executeUpdate();
                                cnt++;
                            }
                        } catch (SQLException E) {
                            E.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Enter only numbers between 1-100");
                }
            }
        });
        btnGetStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int a = Integer.parseInt(txtReqIDStatus.getText());
                    PreparedStatement get_res = con.prepareStatement("SELECT status from status where reqid=?");
                    get_res.setInt(1, a);
                    ResultSet rs = get_res.executeQuery();
                    rs.next();
                    textAreaDisplayStatus.setText(rs.getString(1));


                } catch (SQLException E) {
                    E.printStackTrace();
                } catch (Exception E) {
                    JOptionPane.showMessageDialog(null, "Not a Integer!");
                }

            }
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminMain("Admin Panel | Bike Care", null, null);
    }


}
