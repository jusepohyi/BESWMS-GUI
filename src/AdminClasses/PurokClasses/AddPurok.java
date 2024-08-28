package AdminClasses.PurokClasses;

import AdminClasses.RecorderClasses.Recorders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddPurok extends JFrame implements MouseListener, ActionListener {
    private Connection connection;

    JLabel back;

    JTextField puroknameField;

    JButton addButton;


    public AddPurok(){
        connectToDB();

        this.setTitle("Administrator - Add Purok");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon logo = new ImageIcon("images\\logo.jpg");
        this.setIconImage(logo.getImage());

        ImageIcon backIcon = new ImageIcon("images\\backIcon.png");
        back = new JLabel();
        back.setIcon(backIcon);
        back.setBounds(20,30,70,34);
        back.addMouseListener(this);

        JLabel puroknameText =new JLabel("Purok Name:");
        puroknameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        puroknameText.setBounds(110,100,100,30);

        puroknameField = new JTextField();
        puroknameField.setBounds(220,100,150,25);

        addButton = new JButton("Add");
        addButton.setBounds(310,150,60,24);
        addButton.setFocusable(false);
        addButton.addActionListener(this);

        this.add(addButton);
        this.add(puroknameText);
        this.add(puroknameField);
        this.add(back);
        
        this.setLayout(null);
        this.setVisible(true);
    }

    public void addPurok(){
        String sql = "INSERT into purok (purok_name) VALUES (?)";
        String pname = "Purok " + capitalize(puroknameField.getText());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, (pname));

            JOptionPane.showMessageDialog(this, "PUROK ADDED SUCCESSFULLY!");

            puroknameField.setText("");

            pst.executeUpdate();
            pst.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton) {
            connectToDB();

            if(puroknameField.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this, "Purok name must not be empty.");
            } else {
                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT purok_name from purok";
                    ResultSet rs = statement.executeQuery(sql);

                    boolean idk = true;
                    while(rs.next()){
                        if(rs.getString("purok_name").equalsIgnoreCase("Purok " + puroknameField.getText())){
                            JOptionPane.showMessageDialog(this,"Purok name already in use! Try again.");
                            idk = false;
                        }
                    }

                    if(idk){
                        addPurok();
                    } else {
                        System.out.println("do not execute");
                    }

                    statement.close();
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == back){
            if(e.getSource() == back) {
                try {
                    connection.close();
                    new Puroks();
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

    public static String capitalize(String str){
        String[] words = str.split("\\s");
        StringBuilder sb = new StringBuilder();

        for(String s: words){
            if(!s.equals("")){
                sb.append(Character.toUpperCase(s.charAt(0)));
                sb.append(s.substring(1));
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
