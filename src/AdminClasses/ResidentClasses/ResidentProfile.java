package AdminClasses.ResidentClasses;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ResidentProfile extends JFrame implements MouseListener, ActionListener{
    private Connection connection;

    JLabel userDp;
    JLabel back;

    String pkey;

    String firstname, middlename, lastname, purok, contactNo;
    int bal;

    JTextField firstnameField;
    JTextField middlenameField;
    JTextField lastnameField;
    JTextField contactField;
    JTextField balanceTextField;

    JButton editButton, doneButton, cancelButton, deleteButton, addFinesButton;

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> purokField = new JComboBox<>(comboBoxModel);

    JPanel residentInfoPanel;

    DefaultTableModel tableModel = new DefaultTableModel();
    JTable recordHistoryTable = new JTable(tableModel);
    JTableHeader recordHistoryTableTableHeader = recordHistoryTable.getTableHeader();

    DefaultTableModel tableModel1 = new DefaultTableModel();
    JTable paymentHistoryTable = new JTable(tableModel1);
    JTableHeader paymentHistoryTableTableHeader = paymentHistoryTable.getTableHeader();

    public ResidentProfile(String id){
        pkey = id;

        connectToDB();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from residents where res_id = '" + pkey + "'";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                firstname = rs.getString("firstname");
                middlename = rs.getString("middlename");
                lastname = rs.getString("lastname");
                purok = rs.getString("purok");
                contactNo = rs.getString("contactNum");
                bal = rs.getInt("fines");
            }
            rs.close(); // delete kung error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        this.setTitle("Administrator - Resident Profile");
        this.setSize(500, 600);this.setSize(1000, 600);this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        firstnameField = new JTextField(firstname);
        firstnameField.setBounds(220,210,150,25);

        JLabel middlenameText = new JLabel("Middlename:");
        middlenameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        middlenameText.setBounds(110, 250, 100, 30);

        middlenameField = new JTextField(middlename);
        middlenameField.setBounds(220,250,150,25);

        JLabel lastnameText = new JLabel("Lastname:");
        lastnameText.setFont(new Font("Calibri", Font.PLAIN, 17));
        lastnameText.setBounds(110, 290, 100, 30);

        lastnameField = new JTextField(lastname);
        lastnameField.setBounds(220,290,150,25);

        JLabel contactText = new JLabel("Contact #:");
        contactText.setFont(new Font("Calibri", Font.PLAIN, 17));
        contactText.setBounds(110, 330, 100, 30);

        contactField = new JTextField(contactNo);
        contactField.setBounds(220,330,150,25);

        JLabel purokText = new JLabel("Purok:");
        purokText.setFont(new Font("Calibri", Font.PLAIN, 17));
        purokText.setBounds(110, 370, 200, 30);

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

        purokField.setBounds(220,370,150,25);
        ((JLabel) purokField.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        purokField.addActionListener(this);

        setFieldsUneditable();

        JLabel balanceText = new JLabel("Balance:");
        balanceText.setFont(new Font("Calibri", Font.PLAIN, 17));
        balanceText.setBounds(110, 410, 200, 30);

        balanceTextField = new JTextField(String.valueOf(bal));
        balanceTextField.setBounds(220,410,150,25);
        balanceTextField.setEditable(false);

        editButton = new JButton("Edit");
        editButton.setBounds(310,450,60,24);
        editButton.setFocusable(false);
        editButton.addActionListener(this);

        doneButton = new JButton("Done");
        doneButton.setBounds(310,450,63,24);
        doneButton.setFocusable(false);
        doneButton.addActionListener(this);
        doneButton.setVisible(false);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(230,450,73,24);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);
        deleteButton.setVisible(true);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(230,450,73,24);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);
        cancelButton.setVisible(false);

        addFinesButton = new JButton("Add Fines");
        addFinesButton.setBounds(110,450,89,24);
        addFinesButton.setFocusable(false);
        addFinesButton.addActionListener(this);
        //addFinesButton.setVisible(false);

        residentInfoPanel = new JPanel();
        residentInfoPanel.setBounds(400,26,560,450);
        residentInfoPanel.setLayout(new FlowLayout());
        residentInfoPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));

        JLabel recordHistoryText = new JLabel("Record History");
        recordHistoryText.setFont(new Font("Calibri", Font.PLAIN, 30));
        recordHistoryText.setVerticalAlignment(SwingConstants.CENTER);
        recordHistoryText.setHorizontalAlignment(SwingConstants.CENTER);
        recordHistoryText.setForeground(new Color(0, 0, 0));
        recordHistoryText.setBounds(0,40,225,40);

        tableModel.addColumn("Record ID");
        tableModel.addColumn("Date");
        tableModel.addColumn("Detail");
        tableModel.addColumn("Recorder ID");

        recordHistoryTable.setFillsViewportHeight(true);
        recordHistoryTable.setDefaultEditor(Object.class, null);
        recordHistoryTable.setPreferredScrollableViewportSize(new Dimension(550,130));
        recordHistoryTable.setFont(new Font("Calibri", Font.PLAIN, 15));
        recordHistoryTable.setRowHeight(25);
        recordHistoryTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        recordHistoryTable.getTableHeader().setResizingAllowed(false);
        recordHistoryTable.addMouseListener(this);

        recordHistoryTableTableHeader.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((DefaultTableCellRenderer) recordHistoryTableTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        tableModel1.addColumn("Payment ID");
        tableModel1.addColumn("Date");
        tableModel1.addColumn("Amount");
        tableModel1.addColumn("Recorder ID");

        paymentHistoryTable.setFillsViewportHeight(true);
        paymentHistoryTable.setDefaultEditor(Object.class, null);
        paymentHistoryTable.setPreferredScrollableViewportSize(new Dimension(550,130));
        paymentHistoryTable.setFont(new Font("Calibri", Font.PLAIN, 15));
        paymentHistoryTable.setRowHeight(25);
        paymentHistoryTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        paymentHistoryTable.getTableHeader().setResizingAllowed(false);
        paymentHistoryTable.addMouseListener(this);

        paymentHistoryTableTableHeader.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((DefaultTableCellRenderer) paymentHistoryTableTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        JLabel paymentHistoryText = new JLabel("Payment History");
        paymentHistoryText.setFont(new Font("Calibri", Font.PLAIN, 30));
        paymentHistoryText.setVerticalAlignment(SwingConstants.CENTER);
        paymentHistoryText.setHorizontalAlignment(SwingConstants.CENTER);
        paymentHistoryText.setForeground(new Color(0, 0, 0));
        paymentHistoryText.setBounds(0,40,225,40);


        residentInfoPanel.add(recordHistoryText);
        residentInfoPanel.add(new JScrollPane(recordHistoryTable));
        residentInfoPanel.add(paymentHistoryText);
        residentInfoPanel.add(new JScrollPane(paymentHistoryTable));

        createRecordedHistoryTable();
        createRecordedPaymentHistoryTable();



        this.add(residentInfoPanel);

        this.add(balanceTextField);
        this.add(balanceText);
        this.add(addFinesButton);
        this.add(cancelButton);
        this.add(deleteButton);
        this.add(doneButton);
        this.add(editButton);
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

    public void setFieldsUneditable(){
        firstnameField.setEditable(false);
        middlenameField.setEditable(false);
        lastnameField.setEditable(false);
        contactField.setEditable(false);
        purokField.setEnabled(false);
    }

    public void setFieldEditable(){
        firstnameField.setEditable(true);
        middlenameField.setEditable(true);
        lastnameField.setEditable(true);
        contactField.setEditable(true);
        purokField.setEnabled(true);
    }

    public void createRecordedHistoryTable(){

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from schedule_record where res_id = '"+ pkey +"' ORDER BY date ASC";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                String schedRecID = rs.getString("scheduleRecord_id");
                String date = rs.getString("date");
                String rec_id = rs.getString("rec_id");
                String detail = rs.getString("detail");

                String[] data = {schedRecID,date, detail, rec_id};
                tableModel.addRow(data);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createRecordedPaymentHistoryTable() {

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from payment_record where res_id = '" + pkey + "' ORDER BY date ASC";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String schedRecID = rs.getString("paymentRecord_id");
                String date = rs.getString("date");
                String rec_id = rs.getString("rec_id");
                String amount = rs.getString("amount_paid");

                String[] data = {schedRecID, date, amount, rec_id};
                tableModel1.addRow(data);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateRec(){
        String fname = capitalize(firstnameField.getText());
        String mname = capitalize(middlenameField.getText());
        String lname = capitalize(lastnameField.getText());
        String fulln = lname + ", " + fname + " " + mname;
        String contact = contactField.getText();
        String prk = (String) purokField.getSelectedItem();

        try {
            connectToDB();

            String sql1 = "UPDATE residents SET firstname ='" + fname + "', middlename = '" + mname + "', " + "lastname ='" + lname + "', fullname ='" + fulln + "', contactNum = '"+ contact +"', purok = '"+prk+"'  WHERE res_id = '" + pkey + "'  ";
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.execute();

            JOptionPane.showMessageDialog(this, "RESIDENT UPDATED SUCCESSFULLY!");

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
        if(e.getSource() == editButton){
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
            int dialogButton = JOptionPane.showConfirmDialog (this, "Are you sure you want to delete this resident?","WARNING",JOptionPane.YES_NO_OPTION);
            if (dialogButton == JOptionPane.YES_OPTION) {
                try {
                    connectToDB();

                    String sql1 = "DELETE from residents WHERE res_id = '"+ pkey +"'";
                    PreparedStatement pst1 = connection.prepareStatement(sql1);
                    pst1.execute();

                    JOptionPane.showMessageDialog(this,"RESIDENT DELETED SUCCESSFULLY!");
                    new Residents();
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
            } else if (contactField.getText().length() != 11){
                JOptionPane.showMessageDialog(this,"Contact Number must be 11 digits! Try again.");
            } else if (!contactField.getText().matches("\\b\\d+\\b")) {
                JOptionPane.showMessageDialog(this,"Contact Number must not contain letters! Try again.");
            } else {
                updateRec();
            }
        } if (e.getSource() == addFinesButton){
            try {
                connectToDB();

                String sql1 = "UPDATE residents SET fines ='" + (bal+100)+ "' WHERE res_id = '" + pkey + "'  ";
                PreparedStatement pst1 = connection.prepareStatement(sql1);
                pst1.execute();

                dispose();
                new AdminClasses.ResidentClasses.ResidentProfile(pkey);
                JOptionPane.showMessageDialog(this, "FINES ADDED SUCCESSFULLY!");
                pst1.close();
                connection.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
