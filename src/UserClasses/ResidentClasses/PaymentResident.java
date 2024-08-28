package UserClasses.ResidentClasses;

import LoginClass.UserLoginInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentResident extends JFrame implements MouseListener, ActionListener {

    private Connection connection;

    JTextField amountField;

    JLabel back;

    JButton doneButton;

    String resPkey;

    String purok;

    int fines;

    java.util.Date date1 = new Date();

    public PaymentResident(String resId){
        resPkey = resId;


        connectToDB();

        this.setTitle("User - Payment");
        this.setSize(300, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon logo = new ImageIcon("images\\logo.jpg");
        this.setIconImage(logo.getImage());

        ImageIcon backIcon = new ImageIcon("images\\backIcon.png");
        back = new JLabel();
        back.setIcon(backIcon);
        back.setBounds(20, 30, 70, 34);
        back.addMouseListener(this);

        JLabel detailText = new JLabel("Amount:");
        detailText.setFont(new Font("Calibri", Font.BOLD, 15));
        detailText.setBounds(50,90,60,20);

        amountField = new JTextField();
        amountField.setBounds(126,87,90,25);

        doneButton = new JButton("Done");
        doneButton.setBounds(151, 130, 63, 24);
        doneButton.setFocusable(false);
        doneButton.addActionListener(this);

        this.add(doneButton);
        this.add(amountField);
        this.add(detailText);
        this.add(back);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == doneButton){
            if(amountField.getText().length() == 0){
                JOptionPane.showMessageDialog(this,"Input garbage details!");
            } else {

                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT * from residents where res_id = '"+resPkey+"'";
                    ResultSet rs = statement.executeQuery(sql);

                    if(rs.next()){
                        purok = rs.getString("purok");
                        fines = rs.getInt("fines");
                    }

                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    connectToDB();

                    String sql1 = "UPDATE residents SET fines ='" + (fines-Integer.parseInt(amountField.getText()))+ "' WHERE res_id = '" + resPkey + "'  ";
                    PreparedStatement pst1 = connection.prepareStatement(sql1);
                    pst1.execute();

                    pst1.close();
                    connection.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String sql1 = "INSERT INTO payment_record (purok, date, res_id, rec_id, amount_paid) VALUES (?,?,?,?,?)";

                try {
                    connectToDB();
                    PreparedStatement pst = connection.prepareStatement(sql1);

                    pst.setString(1, purok);
                    pst.setString(2, dateFormat.format(date1));
                    pst.setString(3, resPkey);
                    pst.setString(4, UserLoginInfo.getUsername());
                    pst.setInt(5, Integer.parseInt(amountField.getText()));

                    JOptionPane.showMessageDialog(this, "PAYMENT SUCCESSFUL!");

                    amountField.setText("");

                    pst.executeUpdate();
                    pst.close();
                    connection.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == back) {
            if (e.getSource() == back) {
                try {
                    connection.close();
                    new ResidentProfile(resPkey);
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public void connectToDB() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounts", "root", "");
        } catch (SQLException e) {
            displaySQLErrors(e);
        }
    }

    private void displaySQLErrors(SQLException e) {
        JOptionPane.showMessageDialog(this,"SQLException: " + e.getMessage()+"\nSQLState: " + e.getSQLState()+ "\nVendorError: " + e.getErrorCode());
    }
}
