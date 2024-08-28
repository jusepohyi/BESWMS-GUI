package AdminClasses.PurokClasses;

import AdminClasses.RecorderClasses.Recorders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PurokProfile extends JFrame implements MouseListener, ActionListener {
    private Connection connection;

    String pkey;

    JTextField puroknameField;

    JButton editButton, doneButton, cancelButton, deleteButton;

    JLabel back;

    public PurokProfile(String purokname){
        pkey = purokname;

        connectToDB();

        this.setTitle("Administrator - Purok Info");
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

        String a = pkey;
        a = a.replace("Purok ", "");

        System.out.println(a);

        puroknameField = new JTextField(a);
        puroknameField.setBounds(220,100,150,25);
        puroknameField.setEditable(false);

        editButton = new JButton("Edit");
        editButton.setBounds(310,150,60,24);
        editButton.setFocusable(false);
        editButton.addActionListener(this);

        doneButton = new JButton("Done");
        doneButton.setBounds(310,150,63,24);
        doneButton.setFocusable(false);
        doneButton.addActionListener(this);
        doneButton.setVisible(false);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(230,150,73,24);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);
        deleteButton.setVisible(true);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(230,150,73,24);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);
        cancelButton.setVisible(false);

        this.add(cancelButton);
        this.add(deleteButton);
        this.add(doneButton);
        this.add(editButton);
        this.add(puroknameText);
        this.add(puroknameField);
        this.add(back);

        this.setLayout(null);
        this.setVisible(true);

    }

    public void editPurok(){
        //edit all puroks of residents and schedules

        String updatedPName = "Purok " + capitalize(puroknameField.getText());


        try {
            connectToDB();

            String sql1 = "UPDATE recorder_acc SET purok = '"+updatedPName+"' WHERE purok = '"+ pkey +"'";
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.execute();

            pst1.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            connectToDB();

            String sql1 = "UPDATE residents SET purok = '"+updatedPName +"' WHERE purok = '"+ pkey +"'";
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.execute();

            pst1.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            connectToDB();

            String sql1 = "UPDATE schedules SET sched_purok_name = '"+updatedPName +"' WHERE sched_purok_name = '"+ pkey +"'";
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.execute();

            pst1.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            String sql = "UPDATE purok SET purok_name = '" + updatedPName  +  "' where purok_name = '" + pkey + "'";
            PreparedStatement pst1 = connection.prepareStatement(sql);
            pst1.execute();

            JOptionPane.showMessageDialog(this, "PUROK NAME EDITED SUCCESSFULLY!");

            puroknameField.setEditable(false);
            deleteButton.setVisible(true);
            editButton.setVisible(true);
            cancelButton.setVisible(false);
            doneButton.setVisible(false);
            pst1.executeUpdate();
            pst1.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == editButton){
            puroknameField.setEditable(true);
            editButton.setVisible(false);
            doneButton.setVisible(true);
            cancelButton.setVisible(true);
            deleteButton.setVisible(false);
        } else if (e.getSource() == cancelButton) {
            puroknameField.setEditable(false);
            doneButton.setVisible(false);
            editButton.setVisible(true);
            cancelButton.setVisible(false);
            deleteButton.setVisible(true);
        } else if (e.getSource() == deleteButton){
            int dialogButton = JOptionPane.showConfirmDialog (this, "Are you sure you want to delete this purok? All data will be lost including its schedules and list of residents.","WARNING",JOptionPane.YES_NO_OPTION);
            if (dialogButton == JOptionPane.YES_OPTION) {

                try {
                    connectToDB();

                    String aa = " ";

                    String sql1 = "UPDATE recorder_acc SET purok = '"+ aa+"' WHERE purok = '"+ pkey +"'";
                    PreparedStatement pst1 = connection.prepareStatement(sql1);
                    pst1.execute();

                    pst1.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                //delete residents or i blank lang ilang puroks

                try {
                    connectToDB();

                    String sql1 = "DELETE from residents WHERE purok = '"+ pkey +"'";
                    PreparedStatement pst1 = connection.prepareStatement(sql1);
                    pst1.execute();

                    pst1.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    connectToDB();

                    String sql1 = "DELETE from schedules WHERE sched_purok_name = '"+ pkey +"'";
                    PreparedStatement pst1 = connection.prepareStatement(sql1);
                    pst1.execute();

                    pst1.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                try {
                    connectToDB();

                    String sql1 = "DELETE from purok WHERE purok_name = '"+ pkey +"'";
                    PreparedStatement pst1 = connection.prepareStatement(sql1);
                    pst1.execute();

                    JOptionPane.showMessageDialog(this,"PUROK DELETED SUCCESSFULLY!");
                    new Puroks();
                    dispose();
                    pst1.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else if(e.getSource() == doneButton) {
            connectToDB();

            if (puroknameField.getText().length() <= 0) {
                JOptionPane.showMessageDialog(this, "Purok name must not be empty.");
            } else {
                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT purok_name from purok";
                    ResultSet rs = statement.executeQuery(sql);

                    boolean idk = true;
                    while (rs.next()) {
                        if (rs.getString("purok_name").equalsIgnoreCase("Purok " + puroknameField.getText())) {
                            JOptionPane.showMessageDialog(this, "Purok name already in use! Try again.");
                            idk = false;
                        }
                    }

                    if (idk) {
                        editPurok();
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
