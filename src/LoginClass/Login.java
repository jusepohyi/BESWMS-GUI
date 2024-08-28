package LoginClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener{
    private Connection connection;

    JTextField usernameTextField;
    JPasswordField jPasswordField;

    JComboBox<String> usersComboBox;

    JButton loginButton;

    public Login(){
        connectToDB();

        this.setTitle("Barangay Ecological Solid Waste Management System - Login");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon logo = new ImageIcon("images\\logo.jpg");
        this.setIconImage(logo.getImage());

        JLabel beswmsTitle = new JLabel("B.E.S.W.M.S");
        beswmsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        beswmsTitle.setFont(new Font("Oswald Regular", Font.BOLD, 100));
        beswmsTitle.setForeground(new Color(0, 0, 0));
        beswmsTitle.setBounds(250, 0, 465, 110);

        JLabel usernameText = new JLabel("Username:");
        usernameText.setFont(new Font("Calibri", Font.PLAIN, 20));
        usernameText.setForeground(new Color(0, 0, 0));
        usernameText.setBounds(350, 60, 1000, 600);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(450, 342, 180, 30);

        JLabel passwordText = new JLabel(" Password:");
        passwordText.setFont(new Font("Calibri", Font.PLAIN, 20));
        passwordText.setForeground(new Color(0, 0, 0));
        passwordText.setBounds(350, 98, 1000, 600);

        jPasswordField = new JPasswordField();
        jPasswordField.setBounds(450, 382, 180, 30);
        jPasswordField.setEchoChar('*');

        loginButton = new JButton("Login");
        loginButton.setBounds(565, 415, 65, 22);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        JLabel loginAsText = new JLabel("Login as:");
        loginAsText.setHorizontalAlignment(JLabel.CENTER);
        loginAsText.setFont(new Font("Montserrat", Font.PLAIN, 20));
        loginAsText.setForeground(new Color(0, 0, 0));
        loginAsText.setBounds(0, -80, 1000, 600);

        String[] users = {"Administrator", "Recorder"};
        usersComboBox = new JComboBox<>(users);
        usersComboBox.setFont(new Font("Montserrat", Font.PLAIN, 20));
        usersComboBox.setForeground(new Color(0, 0, 0));
        usersComboBox.setBounds(415, 240, 170, 22);
        ((JLabel) usersComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        usersComboBox.addActionListener(this);
        usersComboBox.setSelectedIndex(0);

        this.getRootPane().setDefaultButton(loginButton);
        this.add(beswmsTitle);
        this.add(usernameText);
        this.add(passwordText);
        this.add(loginAsText);
        this.add(usersComboBox);
        this.add(usernameTextField);
        this.add(jPasswordField);
        this.add(loginButton);

        this.setLayout(null);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton){
            String username = usernameTextField.getText();
            String password = jPasswordField.getText();
            if(usersComboBox.getSelectedIndex() == 0){
                try {
                    Statement stm = connection.createStatement();
                    ResultSet rs = stm.executeQuery("SELECT * FROM adminacc where username ='" + username + "'and password ='" + password + "' and admin_acc_id = 'abc123' ");

                    if(rs.next()) {
                        connection.close();
                        new AdminLoginInfo(username,password);
                        new AdminClasses.Home(AdminLoginInfo.getUsername(), AdminLoginInfo.getPassword());
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect username or password! Try again.");
                        usernameTextField.setText("");
                        jPasswordField.setText("");
                    }
                    stm.close();
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else {
                try {
                    Statement stm = connection.createStatement();
                    ResultSet rs = stm.executeQuery("SELECT * FROM recorder_acc where username ='" + username + "'and password ='" + password +"'");

                    if(rs.next()){

                        new UserLoginInfo(username,password);
                        new UserClasses.Home(username,password);
                        connection.close();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect username or password! Try again.");
                        usernameTextField.setText("");
                        jPasswordField.setText("");
                    }
                    stm.close();
                    rs.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        }
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
