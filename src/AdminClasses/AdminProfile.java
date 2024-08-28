package AdminClasses;

import LoginClass.AdminLoginInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class AdminProfile extends JFrame implements MouseListener, ActionListener {

    private Connection connection;

    JLabel userDp;
    JLabel back;

    JTextField usernameField;
    JPasswordField passwordField;

    JCheckBox showCheckBox;

    JButton editButton;
    JButton cancelButton;
    JButton doneButton;

    String username, password;

    public AdminProfile(String uName, String pword){
        connectToDB();

        username = uName;
        password = pword;

        this.setTitle("Administrator - Profile");
        this.setSize(500, 500);
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
        usernameText.setFont(new Font("Calibri", Font.PLAIN, 20));
        usernameText.setBounds(110,210,100,30);

        JLabel passwordText = new JLabel("Password:");
        passwordText.setFont(new Font("Calibri", Font.PLAIN, 20));
        passwordText.setBounds(110, 250, 100, 30);

        usernameField = new JTextField(username);
        usernameField.setEditable(false);
        usernameField.setBounds(220,210,150,25);

        passwordField = new JPasswordField(password);
        passwordField.setEchoChar('*');
        passwordField.setEditable(false);
        passwordField.setBounds(220,250,150,25);

        showCheckBox = new JCheckBox("show");
        showCheckBox.setFocusable(false);
        showCheckBox.setBounds(380,250,60,24);
        showCheckBox.addActionListener(this);

        editButton = new JButton("Edit");
        editButton.setBounds(310,320,60,24);
        editButton.setFocusable(false);
        editButton.addActionListener(this);

        doneButton = new JButton("Done");
        doneButton.setBounds(310,320,63,24);
        doneButton.setFocusable(false);
        doneButton.addActionListener(this);
        doneButton.setVisible(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(230,320,73,24);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);
        cancelButton.setVisible(false);

        this.add(cancelButton);
        this.add(doneButton);
        this.add(editButton);
        this.add(back);
        this.add(userDp);
        this.add(usernameText);
        this.add(passwordText);
        this.add(usernameField);
        this.add(passwordField);
        this.add(showCheckBox);

        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== showCheckBox){
            if(showCheckBox.isSelected()){
                passwordField.setEchoChar((char)0);
            } else {
                passwordField.setEchoChar('*');
            }
        } else if(e.getSource() == editButton){
            editButton.setVisible(false);
            doneButton.setVisible(true);
            cancelButton.setVisible(true);
            usernameField.setEditable(true);
            passwordField.setEditable(true);
        } else if (e.getSource() == cancelButton){
            doneButton.setVisible(false);
            usernameField.setEditable(false);
            passwordField.setEditable(false);
            editButton.setVisible(true);
            cancelButton.setVisible(false);
        } else if (e.getSource() == doneButton){
            if(passwordField.getText().equals(password)){
                JOptionPane.showMessageDialog(this,"Input matches the old password! Try again.");
            } else if (passwordField.getText().length() <= 4){
                JOptionPane.showMessageDialog(this, "Password too short! Try again.");
            } else if (usernameField.getText().length() <= 4){
                JOptionPane.showMessageDialog(this, "Username too short! Try again.");
            } else {

                try {
                    username = usernameField.getText();
                    password = passwordField.getText();

                    new AdminLoginInfo(username,password);

                    connectToDB();

                    String query = "UPDATE adminacc SET password = '"+ password  +"', username ='"+ username +"' WHERE admin_acc_id = 'abc123'";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.execute();

                    JOptionPane.showMessageDialog(this,"USERNAME & PASSWORD CHANGED SUCCESSFULLY!");
                    doneButton.setVisible(false);
                    usernameField.setEditable(false);
                    passwordField.setEditable(false);
                    editButton.setVisible(true);
                    cancelButton.setVisible(false);

                    connection.close();
                    pst.close();

                }catch (SQLException throwables) {
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
                    new Home(username, password);
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
