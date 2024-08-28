package AdminClasses.ResidentClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddResident extends JFrame implements MouseListener, ActionListener{
    private Connection connection;

    JLabel userDp;
    JLabel back;

    JTextField firstnameField;
    JTextField middlenameField;
    JTextField lastnameField;
    JTextField contactField;

    JButton addButton;

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> purokField = new JComboBox<>(comboBoxModel);

    public AddResident(){
        connectToDB();

        this.setTitle("Administrator - Add Resident");
        this.setSize(500, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon logo = new ImageIcon("images\\logo.jpg");
        this.setIconImage(logo.getImage());

        ImageIcon backIcon = new ImageIcon("images\\backIcon.png");
        back = new JLabel();
        back.setIcon(backIcon);
        back.setBounds(20,30,70,34);
        back.addMouseListener(this);

        ImageIcon userIcon = new ImageIcon("images\\user (2).png");
        userDp = new JLabel();
        userDp.setFont(new Font("Montserrat", Font.PLAIN, 18));
        userDp.setVerticalAlignment(SwingConstants.CENTER);
        userDp.setHorizontalAlignment(SwingConstants.CENTER);
        userDp.setVerticalTextPosition(SwingConstants.BOTTOM);
        userDp.setHorizontalTextPosition(SwingConstants.CENTER);
        userDp.setIcon(userIcon);
        userDp.setBounds(175,25,128,128);

        JLabel firstnameText = new JLabel("Firstname:");
        firstnameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        firstnameText.setBounds(110, 210, 100, 30);

        firstnameField = new JTextField();
        firstnameField.setBounds(220,210,150,25);

        JLabel middlenameText = new JLabel("Middlename:");
        middlenameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        middlenameText.setBounds(110, 250, 100, 30);

        middlenameField = new JTextField();
        middlenameField.setBounds(220,250,150,25);

        JLabel lastnameText = new JLabel("Lastname:");
        lastnameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        lastnameText.setBounds(110, 290, 100, 30);

        lastnameField = new JTextField();
        lastnameField.setBounds(220,290,150,25);

        JLabel contactText = new JLabel("Contact #:");
        contactText.setFont(new Font("Calibri", Font.PLAIN, 17));
        contactText.setBounds(110, 330, 100, 30);

        contactField = new JTextField();
        contactField.setBounds(220,330,150,25);

        JLabel purokText = new JLabel("Purok:");
        purokText.setFont(new Font("Calibri", Font.PLAIN, 17));
        purokText.setBounds(110, 370, 200, 30);

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from purok";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String purok = rs.getString("purok_name");
                comboBoxModel.addElement(purok);
            }

            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        purokField.setBounds(220,370,150,25);
        ((JLabel) purokField.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        purokField.setSelectedIndex(0);
        purokField.addActionListener(this);

        addButton = new JButton("Add");
        addButton.setBounds(310,450,60,24);
        addButton.setFocusable(false);
        addButton.addActionListener(this);

        this.add(addButton);
        this.add(purokText);
        this.add(purokField);
        this.add(contactText);
        this.add(contactField);
        this.add(lastnameText);
        this.add(lastnameField);
        this.add(middlenameText);
        this.add(middlenameField);
        this.add(firstnameText);
        this.add(firstnameField);
        this.add(back);
        this.add(userDp);

        this.setLayout(null);
        this.setVisible(true);
    }

    public void addResident(){
        String sql = "INSERT into residents (firstname,middlename,lastname, fullname, purok, contactNum) VALUES (?,?,?,?,?,?)";

        String fname = firstnameField.getText();
        String mname = middlenameField.getText();
        String lname = lastnameField.getText();
        String fulln = lname + ", " + fname + " " + mname;
        String contact = contactField.getText();
        String prk = (String) purokField.getSelectedItem();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, capitalize(fname));
            pst.setString(2, capitalize(mname));
            pst.setString(3, capitalize(lname));
            pst.setString(4, capitalize(fulln));
            pst.setString(5, capitalize(prk));
            pst.setString(6, contact);

            JOptionPane.showMessageDialog(this, "RESIDENT ADDED SUCCESSFULLY!");

            firstnameField.setText("");
            middlenameField.setText("");
            lastnameField.setText("");
            contactField.setText("");

            pst.executeUpdate();
            pst.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton){

            connectToDB();

            if(firstnameField.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this, "First name must not be empty.");
            }else if ( lastnameField.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this, "Last name must not be empty.");
            }else if (contactField.getText().length() != 11){
                JOptionPane.showMessageDialog(this,"Contact Number must be 11 digits! Try again.");
            } else if (!contactField.getText().matches("\\b\\d+\\b")) {
                JOptionPane.showMessageDialog(this,"Contact Number must not contain letters! Try again.");
            }else {

                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT contactNum from residents";
                    ResultSet rs = statement.executeQuery(sql);

                    boolean idk = true;
                    while(rs.next()){
                        if (rs.getString("contactNum").equals(contactField.getText())){
                            idk = false;
                            JOptionPane.showMessageDialog(this, "Contact number is already in use! Try again.");
                        }
                    }

                    if(idk){
                        addResident();
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
                    new Residents();
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
