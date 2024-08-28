package UserClasses;

import AdminClasses.RecorderClasses.Recorders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class UserProfile extends JFrame implements MouseListener, ActionListener {
    private Connection connection;

    String pkey;

    String uname, password, firstname, middlename, lastname, purok, contactNo;

    JLabel userDp;
    JLabel back;

    JCheckBox showCheckBox;

    JTextField usernameField;
    JTextField firstnameField;
    JTextField middlenameField;
    JTextField lastnameField;
    JTextField contactField;

    JPasswordField passwordField;

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> purokField = new JComboBox<>(comboBoxModel);

    public UserProfile(String username){
        pkey = username;

        connectToDB();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from recorder_acc where username = '" + pkey + "'";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                uname = rs.getString("username");
                password = rs.getString("password");
                firstname = rs.getString("firstname");
                middlename = rs.getString("middlename");
                lastname = rs.getString("lastname");
                purok = rs.getString("purok");
                contactNo = rs.getString("contactNum");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        this.setTitle("User - Profile");
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

        usernameField = new JTextField(username);
        usernameField.setBounds(220,210,150,25);
        usernameField.setEditable(false);

        JLabel firstnameText = new JLabel("Firstname:");
        firstnameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        firstnameText.setBounds(110, 250, 100, 30);

        firstnameField = new JTextField(firstname);
        firstnameField.setBounds(220,250,150,25);

        JLabel middlenameText = new JLabel("Middlename:");
        middlenameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        middlenameText.setBounds(110, 290, 100, 30);

        middlenameField = new JTextField(middlename);
        middlenameField.setBounds(220,290,150,25);

        JLabel lastnameText = new JLabel("Lastname:");
        lastnameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        lastnameText.setBounds(110, 330, 100, 30);

        lastnameField = new JTextField(lastname);
        lastnameField.setBounds(220,330,150,25);

        JLabel contactText = new JLabel("Contact #:");
        contactText.setFont(new Font("Calibri", Font.PLAIN, 17));
        contactText.setBounds(110, 370, 100, 30);

        contactField = new JTextField(contactNo);
        contactField.setBounds(220,370,150,25);

        JLabel passwordText = new JLabel("Password:");
        passwordText.setFont(new Font("Calibri", Font.PLAIN, 17));
        passwordText.setBounds(110, 410, 100, 30);

        passwordField = new JPasswordField(password);
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
                String a = rs.getString("purok_name");
                comboBoxModel.addElement(a);
                if (purok.equalsIgnoreCase(a)) {
                    purokField.setSelectedItem(a);
                }
            }

            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        purokField.setBounds(220,490,150,25);
        ((JLabel) purokField.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        purokField.addActionListener(this);

        setFieldsUneditable();

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

    public void setFieldsUneditable(){
        firstnameField.setEditable(false);
        middlenameField.setEditable(false);
        lastnameField.setEditable(false);
        contactField.setEditable(false);
        passwordField.setEditable(false);
        purokField.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== showCheckBox){
            if(showCheckBox.isSelected()){
                passwordField.setEchoChar((char)0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == back){
            if(e.getSource() == back) {
                try {
                    connection.close();
                    new Home(usernameField.getText(),passwordField.getText());
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
