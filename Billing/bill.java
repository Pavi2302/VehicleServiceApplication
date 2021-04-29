package Billing;
import ServiceEngineerActions.JobCard;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class bill extends JFrame {
    private JPanel PanMain;
    private JPanel FreeService;
    private JPanel SeperatePartsService;
    private JPanel Billing;
    private JTextField a0TextField3;
    private JTextField a0TextField4;
    private JTextField a0TextField5;
    private JLabel lblFilters;
    private JTextField a0TextField6;
    private JTextField a0TextField7;
    private JTextField a0TextField8;
    private JTextField a0TextField9;
    private JTextField a0TextField10;
    private JTextField a0TextField11;
    private JTextField a0TextField12;
    private JTextField a0TextField13;
    private JTextField a0TextField14;
    private JTextField a0TextField15;
    private JTextField a0TextField16;
    private JTextField a0TextField17;
    private JTextField a0TextField18;
    private JTextField a0TextField19;
    private JTextField a0TextField20;
    private JTextArea textArea1;
    private JButton receiptButton;
    private JButton printButton;
    private JButton exitButton;
    private JLabel lblTax;
    private JTextField a0TextField;
    private JLabel lblSub;
    private JTextField a0TextField1;
    private JLabel lblTotal;
    private JTextField a0TextField2;
    private JLabel lblTaxandtotalsum;
    private JButton totalButton;
    private JCheckBox BatteriesCheckbox;
    private JCheckBox OilCheckBox;
    private JButton resetButton;
    private JCheckBox filterCheckbox;
    private JCheckBox refillCheckbox;
    private JCheckBox coolantCheckbox;
    private JCheckBox chargingCheckbox;
    private JCheckBox powerCheckbox;
    private JCheckBox greeseCheckbox;
    private JCheckBox beltCheckbox;
    private JCheckBox tiresCheckbox;
    private JCheckBox lightCheckbox;
    private JCheckBox beltsCheckbox;
    private JCheckBox barkingCheckbox;
    private JCheckBox chargingoilCheckbox;
    private JCheckBox clutchCheckbox;
    private JCheckBox lightningCheckbox;
    private JCheckBox sparkCheckbox;
    private JCheckBox lubricantsCheckbox;
    private JButton btnClear;
    private JLabel imgLogoMain;
    private JPanel panTitle;
    private JButton btnGenerate;

    String iTax, iSub, iTotal;
    double[] itemcost = new double[100];

    void clear() {
        textArea1.setText("=======================================" + "\n"
                + "\t" + "Bike Care System" + "\n" +
                "\t" + "Contact No.-9344493572" + "\n"
                + "\t" + "Address-Coimbatore" + "\n" +
                "=======================================");
    }

    public bill(String title, JobCard obj) {
        super(title);
        this.setContentPane(PanMain);
        textArea1.setAutoscrolls(true);
        this.setIconImage(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png").getImage());
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(null, "Do you want to Exit?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (a == 0) {
                    dispose();
                } else if (a == 1) {
                    System.out.println("No");
                }
            }
        });
        imgLogoMain.setIcon(new ImageIcon("F:\\Codes\\Java\\Vehicle Management System\\rsz_logo_edited.png"));
        textArea1.setText("=======================================" + "\n"
                + "\t" + "Bike Care System" + "\n" +
                "\t" + "Contact No.-9344493572" + "\n"
                + "\t" + "Address-Coimbatore" + "\n" +
                "=======================================");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textArea1.print();
                } catch (PrinterException e1) {
                    System.err.format("No Printer Format", e1.getMessage());
                }
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OilCheckBox.setSelected(false);
                filterCheckbox.setSelected(false);
                refillCheckbox.setSelected(false);
                coolantCheckbox.setSelected(false);
                chargingCheckbox.setSelected(false);
                powerCheckbox.setSelected(false);
                greeseCheckbox.setSelected(false);
                beltCheckbox.setSelected(false);
                tiresCheckbox.setSelected(false);
                lightCheckbox.setSelected(false);
                a0TextField3.setText("0");
                a0TextField4.setText("0");
                a0TextField5.setText("0");
                a0TextField6.setText("0");
                a0TextField7.setText("0");
                a0TextField8.setText("0");
                a0TextField9.setText("0");
                a0TextField10.setText("0");
                a0TextField11.setText("0");
                a0TextField12.setText("0");


                BatteriesCheckbox.setSelected(false);
                beltsCheckbox.setSelected(false);
                barkingCheckbox.setSelected(false);
                chargingoilCheckbox.setSelected(false);
                clutchCheckbox.setSelected(false);
                lightningCheckbox.setSelected(false);
                sparkCheckbox.setSelected(false);
                lubricantsCheckbox.setSelected(false);
                a0TextField13.setText("0");
                a0TextField14.setText("0");
                a0TextField15.setText("0");
                a0TextField16.setText("0");
                a0TextField17.setText("0");
                a0TextField18.setText("0");
                a0TextField19.setText("0");
                a0TextField20.setText("0");

                a0TextField.setText("0");
                a0TextField1.setText("0");
                a0TextField2.setText("0");

                int refs = 5015 + (int) (Math.random() * 17238);
                String cRef = " ";
                cRef += refs + 1560;
            }
        });
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemcost[0] = Double.parseDouble(a0TextField3.getText()) * 100;
                itemcost[1] = Double.parseDouble(a0TextField4.getText()) * 150;
                itemcost[2] = Double.parseDouble(a0TextField5.getText()) * 80;
                itemcost[3] = Double.parseDouble(a0TextField6.getText()) * 100;
                itemcost[4] = Double.parseDouble(a0TextField7.getText()) * 60;
                itemcost[5] = Double.parseDouble(a0TextField8.getText()) * 150;
                itemcost[6] = Double.parseDouble(a0TextField9.getText()) * 100;
                itemcost[7] = Double.parseDouble(a0TextField10.getText()) * 200;
                itemcost[8] = Double.parseDouble(a0TextField11.getText()) * 100;
                itemcost[9] = Double.parseDouble(a0TextField12.getText()) * 60;

                itemcost[10] = Double.parseDouble(a0TextField13.getText()) * 750;
                itemcost[11] = Double.parseDouble(a0TextField14.getText()) * 250;
                itemcost[12] = Double.parseDouble(a0TextField15.getText()) * 600;
                itemcost[13] = Double.parseDouble(a0TextField16.getText()) * 400;
                itemcost[14] = Double.parseDouble(a0TextField17.getText()) * 300;
                itemcost[15] = Double.parseDouble(a0TextField18.getText()) * 200;
                itemcost[16] = Double.parseDouble(a0TextField19.getText()) * 200;
                itemcost[17] = Double.parseDouble(a0TextField20.getText()) * 550;

                itemcost[18] = itemcost[0] + itemcost[1] + itemcost[2] + itemcost[3] + itemcost[4] + itemcost[5] + itemcost[6] + itemcost[7] + itemcost[8] + itemcost[9];
                itemcost[19] = itemcost[10] + itemcost[11] + itemcost[12] + itemcost[13] + itemcost[14] + itemcost[15] + itemcost[16] + itemcost[17];

                itemcost[20] = itemcost[18] + itemcost[19];
                iTax = String.format("Rs %.2f", (itemcost[20] / 100) * 12);
                iSub = String.format("Rs %.2f", itemcost[20]);
                iTotal = String.format("Rs %.2f", itemcost[20] + ((itemcost[20] / 100)) * 12);

                a0TextField.setText(iTax);
                a0TextField1.setText(iSub);
                a0TextField2.setText(iTotal);
            }
        });
        textArea1.setEditable(false);
        receiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                textArea1.setEnabled(true);
                int refs = 1325 + (int) (Math.random() * 4238);
                Calendar timer = Calendar.getInstance();
                timer.getTime();
                SimpleDateFormat tTime = new SimpleDateFormat("HH:mm:ss");
                tTime.format(timer.getTime());
                SimpleDateFormat tDate = new SimpleDateFormat("dd-MMM-yyyy");
                tDate.format(timer.getTime());
                textArea1.append("\nSerial Number\t" + refs +
                        "\n=======================================\t" +
                        "\n=======================================\t");
                if (OilCheckBox.isSelected()) {
                    textArea1.append("\nChange the Engine Oil\t" + a0TextField3.getText() + "\t100");
                    a0TextField.setText("1");
                }
                if (filterCheckbox.isSelected()) {
                    textArea1.append("\nReplace the filters\t" + a0TextField4.getText() + "\t150");
                    a0TextField4.setText("1");
                }
                if (refillCheckbox.isSelected()) {
                    textArea1.append("\nCheck brake fluid/clutch\t" + a0TextField5.getText() + "\t80");
                    a0TextField5.setText("1");
                }
                if (coolantCheckbox.isSelected()) {
                    textArea1.append("\nCheck the charging systems\t" + a0TextField7.getText() + "\t100");
                    a0TextField6.setText("1");
                }
                if (chargingCheckbox.isSelected()) {
                    textArea1.append("\nCheck the charging systems\t" + a0TextField7.getText() + "\t60");
                    a0TextField7.setText("1");
                }
                if (powerCheckbox.isSelected()) {
                    textArea1.append("\nPower Fluid             \t" + a0TextField8.getText() + "\t150");
                    a0TextField8.setText("1");
                }
                if (greeseCheckbox.isSelected()) {
                    textArea1.append("\nGrease and lubrication:\t" + a0TextField9.getText() + "\t100");
                    a0TextField9.setText("1");
                }
                if (beltCheckbox.isSelected()) {
                    textArea1.append("\nTiming belt or Timing chain\t" + a0TextField10.getText() + "\t200");
                    a0TextField10.setText("1");
                }
                if (tiresCheckbox.isSelected()) {
                    textArea1.append("\nCheck condition of the tires:\t" + a0TextField11.getText() + "\t100");
                    a0TextField11.setText("1");
                }
                if (lightCheckbox.isSelected()) {
                    textArea1.append("\nCheck for Proper operation of all electrical comp:\t" + a0TextField12.getText() + "\t60");
                    a0TextField12.setText("1");
                }
                if (BatteriesCheckbox.isSelected()) {
                    textArea1.append("\nAutomotive batteries\t" + a0TextField13.getText() + "\t750");
                    a0TextField13.setText("1");
                }
                if (beltsCheckbox.isSelected()) {
                    textArea1.append("\nAutomotive belts\t" + a0TextField14.getText() + "\t250");
                    a0TextField14.setText("1");
                }
                if (barkingCheckbox.isSelected()) {
                    textArea1.append("\nBarking system\t" + a0TextField15.getText() + "\t600");
                    a0TextField15.setText("1");
                }
                if (chargingoilCheckbox.isSelected()) {
                    textArea1.append("\nCharging coils          \t" + a0TextField16.getText() + "\t400");
                    a0TextField16.setText("1");
                }
                if (clutchCheckbox.isSelected()) {
                    textArea1.append("\nClutch and pressure plates:\t" + a0TextField17.getText() + "\t300");
                    a0TextField17.setText("1");
                }
                if (lightningCheckbox.isSelected()) {
                    textArea1.append("\nLightning coils and stators\t" + a0TextField18.getText() + "\t200");
                    a0TextField18.setText("1");
                }
                if (sparkCheckbox.isSelected()) {
                    textArea1.append("\nSpark plugs            \t" + a0TextField19.getText() + "\t200");
                    a0TextField19.setText("1");
                }
                if (lubricantsCheckbox.isSelected()) {
                    textArea1.append("\nLubricants             \t" + a0TextField20.getText() + "\t550");
                    a0TextField20.setText("1");
                }
                textArea1.append("\n=======================================\t" +
                        "\nTax\t" + iTax +
                        "\nSub Total\t" + iSub +
                        "\nTotal\t" + iTotal +
                        "\n=======================================\t" +
                        "\nDate" + tDate.format(timer.getTime()) +
                        "\nTime" + tTime.format(timer.getTime()) +
                        "\nThank you!!!! Visit Again.!!");

            }
        });
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obj.txtAreaQuot.setText(null);
                obj.txtAreaQuot.setText(textArea1.getText());
            }
        });

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new bill("Bills", null);
    }

}
