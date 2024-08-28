package AdminClasses.RecorderClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class AddRecorder extends JFrame implements MouseListener, ActionListener{
    private Connection connection;

    JLabel userDp;
    JLabel back;

    JCheckBox showCheckBox;

    JTextField usernameField;
    JTextField firstnameField;
    JTextField middlenameField;
    JTextField lastnameField;
    JTextField contactField;

    JPasswordField passwordField;
    
    JButton addButton;

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> purokField = new JComboBox<>(comboBoxModel);

    public AddRecorder(){
        connectToDB();

        this.setTitle("Administrator - Add Recorder");
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

        JLabel usernameText =new JLabel("Username:");
        usernameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        usernameText.setBounds(110,210,100,30);

        usernameField = new JTextField();
        usernameField.setBounds(220,210,150,25);

        JLabel firstnameText = new JLabel("Firstname:");
        firstnameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        firstnameText.setBounds(110, 250, 100, 30);

        firstnameField = new JTextField();
        firstnameField.setBounds(220,250,150,25);

        JLabel middlenameText = new JLabel("Middlename:");
        middlenameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        middlenameText.setBounds(110, 290, 100, 30);

        middlenameField = new JTextField();
        middlenameField.setBounds(220,290,150,25);

        JLabel lastnameText = new JLabel("Lastname:");
        lastnameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        lastnameText.setBounds(110, 330, 100, 30);

        lastnameField = new JTextField();
        lastnameField.setBounds(220,330,150,25);

        JLabel contactText = new JLabel("Contact #:");
        contactText.setFont(new Font("Calibri", Font.PLAIN, 17));
        contactText.setBounds(110, 370, 100, 30);

        contactField = new JTextField();
        contactField.setBounds(220,370,150,25);

        JLabel passwordText = new JLabel("Password:");
        passwordText.setFont(new Font("Calibri", Font.PLAIN, 17));
        passwordText.setBounds(110, 410, 100, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(220,410,150,25);

        showCheckBox = new JCheckBox("show");
        showCheckBox.setFocusable(false);
        showCheckBox.setBounds(380,410,60,24);
        showCheckBox.addActionListener(this);

        JLabel purokText = new JLabel("Purok Assignment:");
        purokText.setFont(new Font("Calibri", Font.PLAIN, 17));
        purokText.setBounds(110, 450, 200, 30);

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

        purokField.setBounds(220,490,150,25);
        ((JLabel) purokField.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        purokField.setSelectedIndex(0);
        purokField.addActionListener(this);

        addButton = new JButton("Add");
        addButton.setBounds(310,550,60,24);
        addButton.setFocusable(false);
        addButton.addActionListener(this);

        this.add(addButton);
        this.add(showCheckBox);
        this.add(purokText);
        this.add(purokField);
        this.add(passwordText);
        this.add(passwordField);
        this.add(contactText);
        this.add(contactField);
        this.add(lastnameText);
        this.add(lastnameField);
        this.add(middlenameText);
        this.add(middlenameField);
        this.add(firstnameText);
        this.add(firstnameField);
        this.add(usernameText);
        this.add(usernameField);
        this.add(back);
        this.add(userDp);

        this.setLayout(null);
        this.setVisible(true);
    }

    public void addRecorder(){
        String sql = "INSERT into recorder_acc (username,password,firstname,middlename,lastname, fullname, purok, contactNum) VALUES (?,?,?,?,?,?,?,?)";

        String uname = usernameField.getText();
        String pword = passwordField.getText();
        String fname = firstnameField.getText();
        String mname = middlenameField.getText();
        String lname = lastnameField.getText();
        String fulln = lname + ", " + fname + " " + mname;
        String contact = contactField.getText();
        String prk = (String) purokField.getSelectedItem();

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, (uname));
            pst.setString(2, (pword));
            pst.setString(3, capitalize(fname));
            pst.setString(4, capitalize(mname));
            pst.setString(5, capitalize(lname));
            pst.setString(6, capitalize(fulln));
            pst.setString(7, capitalize(prk));
            pst.setString(8, contact);

            JOptionPane.showMessageDialog(this, "RECORDER ADDED SUCCESSFULLY!");

            usernameField.setText("");
            passwordField.setText("");
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
        if(e.getSource()== showCheckBox){
            if(showCheckBox.isSelected()){
                passwordField.setEchoChar((char)0);
            } else {
                passwordField.setEchoChar('*');
            }
        } else if(e.getSource() == addButton){

            connectToDB();

            if(firstnameField.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this, "First name must not be empty.");
            }else if ( lastnameField.getText().length() <= 0){
                JOptionPane.showMessageDialog(this,"Last name must not be empty.");
            } else if (usernameField.getText().length() <= 0){
                JOptionPane.showMessageDialog(this, "Username must not be empty.");
            } else if (usernameField.getText().matches("(.*\\s+.*)")){
                JOptionPane.showMessageDialog(this, "Username must not contain spaces.");
            } else if (passwordField.getText().length() < 8){ //\A(?=\S*?[0-9])(?=\S*?[a-z])(?=\S*?[A-Z])(?=\S*?[@#$%^&+=])\S{8,}\z
                JOptionPane.showMessageDialog(this,"Password must be 8 characters above.");
            } else if (contactField.getText().length() != 11){
                JOptionPane.showMessageDialog(this,"Contact Number must be 11 digits! Try again.");
            } else if (!contactField.getText().matches("\\b\\d+\\b")) {
                JOptionPane.showMessageDialog(this,"Contact Number must not contain letters! Try again.");
            } else if (passwordField.getText().matches("(.*\\s+.*)")){
                JOptionPane.showMessageDialog(this, "Password must not contain spaces.");
            }else {

                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT username, contactNum from recorder_acc";
                    ResultSet rs = statement.executeQuery(sql);

                    boolean idk = true;
                    while(rs.next()){
                        if(rs.getString("username").equals(usernameField.getText())){
                            JOptionPane.showMessageDialog(this,"Username already in use! Try again.");
                            idk = false;
                        } else if (rs.getString("contactNum").equals(contactField.getText())){
                            idk = false;
                            JOptionPane.showMessageDialog(this, "Contact number is already in use! Try again.");
                        }
                    }

                    if(idk){
                        addRecorder();
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
                    new Recorders();
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
