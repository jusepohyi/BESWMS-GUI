package AdminClasses.RecorderClasses;

import AdminClasses.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RecorderProfile extends JFrame implements MouseListener, ActionListener {
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

    JButton editButton, doneButton, cancelButton, deleteButton;

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> purokField = new JComboBox<>(comboBoxModel);

    public RecorderProfile(String username) {
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

        this.setTitle("Administrator - Recorder Profile");
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

        editButton = new JButton("Edit");
        editButton.setBounds(310,550,60,24);
        editButton.setFocusable(false);
        editButton.addActionListener(this);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(230,550,73,24);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);
        deleteButton.setVisible(true);

        doneButton = new JButton("Done");
        doneButton.setBounds(310,550,63,24);
        doneButton.setFocusable(false);
        doneButton.addActionListener(this);
        doneButton.setVisible(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(230,550,73,24);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);
        cancelButton.setVisible(false);

        this.add(doneButton);
        this.add(cancelButton);
        this.add(deleteButton);
        this.add(editButton);
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
    
    public void setFieldEditable(){
        firstnameField.setEditable(true);
        middlenameField.setEditable(true);
        lastnameField.setEditable(true);
        contactField.setEditable(true);
        passwordField.setEditable(true);
        purokField.setEnabled(true);
    }

    public void updateRec(){
        String pword = passwordField.getText();
        String fname = capitalize(firstnameField.getText());
        String mname = capitalize(middlenameField.getText());
        String lname = capitalize(lastnameField.getText());
        String fulln = lname + ", " + fname + " " + mname;
        String contact = contactField.getText();
        String prk = (String) purokField.getSelectedItem();

        try {
            connectToDB();

            String sql1 = "UPDATE recorder_acc SET password = '" + pword + "', firstname ='" + fname + "', middlename = '" + mname + "', " +
                    "lastname ='" + lname + "', fullname ='" + fulln + "', contactNum = '"+ contact +"', purok = '"+prk+"'  WHERE username = '" + pkey + "'  ";
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.execute();

            JOptionPane.showMessageDialog(this, "RECORDER UPDATED SUCCESSFULLY!");

            pst1.close();
            connection.close();

            editButton.setVisible(true);
            deleteButton.setVisible(true);
            doneButton.setVisible(false);
            cancelButton.setVisible(false);
            setFieldsUneditable();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showCheckBox) {
            if (showCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        } else if(e.getSource() == editButton){
            setFieldEditable();
            editButton.setVisible(false);
            doneButton.setVisible(true);
            cancelButton.setVisible(true);
            deleteButton.setVisible(false);
        } else if (e.getSource() == cancelButton) {
            setFieldsUneditable();
            doneButton.setVisible(false);
            editButton.setVisible(true);
            cancelButton.setVisible(false);
            deleteButton.setVisible(true);
        } else if (e.getSource() == deleteButton){
            int dialogButton = JOptionPane.showConfirmDialog (this, "Are you sure you want to delete this recorder?","WARNING",JOptionPane.YES_NO_OPTION);
            if (dialogButton == JOptionPane.YES_OPTION) {
                try {
                    connectToDB();

                    String sql1 = "DELETE from recorder_acc WHERE username = '"+ pkey +"'";
                    PreparedStatement pst1 = connection.prepareStatement(sql1);
                    pst1.execute();

                    JOptionPane.showMessageDialog(this,"RECORDER DELETED SUCCESSFULLY!");
                    new Recorders();
                    dispose();
                    pst1.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } if(e.getSource() == doneButton){

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
            } else {
                updateRec();
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
