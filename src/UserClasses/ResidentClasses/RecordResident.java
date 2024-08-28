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

public class RecordResident extends JFrame implements MouseListener, ActionListener {
    private Connection connection;

    JTextField garbageDetail;

    JLabel back;

    JButton doneButton;

    String resPkey;

    String purok;

    java.util.Date date1 = new Date();

    public RecordResident(String resId){
        resPkey = resId;


        connectToDB();

        this.setTitle("User - Record");
        this.setSize(500, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon logo = new ImageIcon("images\\logo.jpg");
        this.setIconImage(logo.getImage());

        ImageIcon backIcon = new ImageIcon("images\\backIcon.png");
        back = new JLabel();
        back.setIcon(backIcon);
        back.setBounds(20, 30, 70, 34);
        back.addMouseListener(this);

        JLabel detailText = new JLabel("Details:");
        detailText.setFont(new Font("Calibri", Font.BOLD, 15));
        detailText.setBounds(50,70,50,20);

        garbageDetail = new JTextField();
        garbageDetail.setBounds(50,90,400,25);

        doneButton = new JButton("Done");
        doneButton.setBounds(387, 130, 63, 24);
        doneButton.setFocusable(false);
        doneButton.addActionListener(this);


        this.add(doneButton);
        this.add(garbageDetail);
        this.add(detailText);
        this.add(back);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == doneButton){
            if(garbageDetail.getText().length() == 0){
                JOptionPane.showMessageDialog(this,"Input garbage details!");
            } else {

                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT * from residents where res_id = '"+resPkey+"'";
                    ResultSet rs = statement.executeQuery(sql);

                    if(rs.next()){
                        purok = rs.getString("purok");

                    }

                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String sql1 = "INSERT INTO schedule_record (purok, date, res_id, rec_id, detail) VALUES (?,?,?,?,?)";

                try {
                    PreparedStatement pst = connection.prepareStatement(sql1);

                    pst.setString(1, purok);
                    pst.setString(2, dateFormat.format(date1));
                    pst.setString(3, resPkey);
                    pst.setString(4, UserLoginInfo.getUsername());
                    pst.setString(5, garbageDetail.getText());

                    JOptionPane.showMessageDialog(this, "RESIDENT RECORDED SUCCESSFULLY!");

                    garbageDetail.setText("");

                    pst.executeUpdate();
                    pst.close();
                    connection.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
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
