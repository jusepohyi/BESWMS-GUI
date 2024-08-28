package AdminClasses.ResidentClasses;

import AdminClasses.Home;
import AdminClasses.PurokClasses.Puroks;
import AdminClasses.RecorderClasses.RecorderProfile;
import AdminClasses.RecorderClasses.Recorders;
import AdminClasses.ScheduleClasses.Schedules;
import LoginClass.AdminLoginInfo;
import LoginClass.Login;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Residents extends JFrame implements ActionListener, KeyListener, MouseListener{
    private Connection connection;

    JMenuBar menuBar;

    JMenu menuMenu;
    JMenu manageMenu;

    JMenuItem recorderItem;
    JMenuItem residentItem;
    JMenuItem purokItem;
    JMenuItem scheduleItem;
    JMenuItem logoutItem;
    JMenuItem homeItem;

    JTextField search;

    JPanel homePanel;

    JButton addButton;

    JLabel sortFname;
    static int sortSwitch = 1;

    DefaultTableModel tableModel = new DefaultTableModel();
    JTable residentListTable = new JTable(tableModel);
    JTableHeader residentTableTableHeader = residentListTable.getTableHeader();

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> purokField = new JComboBox<>(comboBoxModel);

    public Residents(){
        connectToDB();

        this.setTitle("Administrator - Residents");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon logo = new ImageIcon("images\\logo.jpg");
        this.setIconImage(logo.getImage());

        createMenuBar();

        homePanel = new JPanel();
        homePanel.setBackground(new Color(240,240,240));
        homePanel.setOpaque(true);
        homePanel.setBounds(0,0,1000,600);
        homePanel.setLayout(null);

        addButton = new JButton("Add");
        addButton.setBounds(765,450,60,24);
        addButton.setFocusable(false);
        addButton.addActionListener(this);

        ImageIcon sortIcon = new ImageIcon("images\\sort.png");
        sortFname = new JLabel();
        sortFname.setIcon(sortIcon);
        sortFname.setFocusable(false);
        sortFname.setBounds(570,117,16,16);
        sortFname.addMouseListener(this);

        homePanel.add(addButton);
        homePanel.add(sortFname);

        JLabel searchText = new JLabel("Search:");
        searchText.setFont(new Font("Calibri", Font.BOLD, 15));
        searchText.setBounds(145,76,50,20);

        search = new JTextField();
        search.setBounds(205,72,160,25);
        search.addKeyListener(this);

        createPurokListComboBox();

        JPanel residentPanel = new JPanel();
        residentPanel.setBounds(110,105,750,400);
        residentPanel.setLayout(new FlowLayout());

        tableModel.addColumn("Resident ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Purok");

        residentListTable.setFillsViewportHeight(true);
        residentListTable.setDefaultEditor(Object.class, null);
        residentListTable.setPreferredScrollableViewportSize(new Dimension(679,300));
        residentListTable.setFont(new Font("Calibri", Font.PLAIN, 15));
        residentListTable.setRowHeight(25);
        residentListTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        residentListTable.getTableHeader().setResizingAllowed(false);
        residentListTable.addMouseListener(this);

        residentTableTableHeader.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((DefaultTableCellRenderer) residentTableTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        residentPanel.add(new JScrollPane(residentListTable));
        homePanel.add(residentPanel);

        createResidentTable();


        homePanel.add(searchText);
        homePanel.add(search);

        this.add(homePanel);
        this.setLayout(null);
        this.setVisible(true);

    }

    public void createPurokListComboBox(){

        comboBoxModel.addElement("All");

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from purok";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String purok = rs.getString("purok_name");
                comboBoxModel.addElement(purok);
            }

            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        purokField.setBounds(685,72,140,25);
        ((JLabel) purokField.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        purokField.setSelectedIndex(0);
        purokField.addActionListener(this);

        homePanel.add(purokField);

    }

    public void createResidentTable(){

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from residents";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                String username = rs.getString("res_id");
                String fullname = rs.getString("fullname");
                String purok = rs.getString("purok");

                String[] data = {username, fullname, purok};
                tableModel.addRow(data);
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createResTablePurokFiltered(){
        tableModel.setRowCount(0);
        String prk = (String) purokField.getSelectedItem();

        try {
            connectToDB();
            Statement statement = connection.createStatement();
            String sql = "SELECT * from residents where purok = '" + prk + "'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                String username = rs.getString("res_id");
                String fullname = rs.getString("fullname");
                String purok = rs.getString("purok");

                String[] data = {username, fullname, purok};
                tableModel.addRow(data);
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createMenuBar(){
        menuBar = new JMenuBar();

        menuMenu = new JMenu("Menu");
        manageMenu = new JMenu("Manage");

        homeItem = new JMenuItem("Home");
        logoutItem = new JMenuItem("Logout");
        recorderItem = new JMenuItem("Recorders");
        residentItem = new JMenuItem("Residents");
        purokItem = new JMenuItem("Purok");
        scheduleItem = new JMenuItem("Schedules");

        homeItem.addActionListener(this);
        logoutItem.addActionListener(this);
        recorderItem.addActionListener(this);
        residentItem.addActionListener(this);
        purokItem.addActionListener(this);
        scheduleItem.addActionListener(this);

        menuMenu.add(homeItem);
        menuMenu.add(logoutItem);

        manageMenu.add(recorderItem);
        manageMenu.add(residentItem);
        manageMenu.add(purokItem);
        manageMenu.add(scheduleItem);

        menuBar.add(menuMenu);
        menuBar.add(manageMenu);
        this.setJMenuBar(menuBar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logoutItem){
            int dialogButton = JOptionPane.showConfirmDialog (this, "Are you sure you want to logout?","WARNING",JOptionPane.YES_NO_OPTION);
            if (dialogButton == JOptionPane.YES_OPTION) {
                try {
                    connection.close();
                    new Login();
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else if (e.getSource() == homeItem){
            try {
                connection.close();
                new Home(AdminLoginInfo.getUsername(),AdminLoginInfo.getPassword());
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == recorderItem){
            try {
                connection.close();
                new Recorders();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == purokItem){
            try {
                connection.close();
                new Puroks();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == residentItem){
            try {
                connection.close();
                new Residents();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == purokField) {
            if (purokField.getSelectedItem() == "All") {
                tableModel.setRowCount(0);
                createResidentTable();
            } else {
                createResTablePurokFiltered();
            }
        } else if (e.getSource() == scheduleItem){
            try {
                connection.close();
                new Schedules();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }  else if (e.getSource() == addButton){
            try {
                connection.close();
                new AddResident();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(search.getText().length() == 0){
            tableModel.setRowCount(0);
            createResidentTable();
        } else {
            tableModel.setRowCount(0);

            try {
                Statement statement = connection.createStatement();
                String sql = "SELECT * from residents where fullname like '" + "%" + search.getText() + "%" + "'";
                ResultSet rs = statement.executeQuery(sql);

                while(rs.next()){
                    String username = rs.getString("res_id");
                    String fullname = rs.getString("fullname");
                    String purok = rs.getString("purok");

                    String[] data = {username, fullname, purok};
                    tableModel.addRow(data);
                }
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == sortFname){
            tableModel.setRowCount(0);

            if(sortSwitch == 1) {
                try {
                    String sql = "SELECT * from residents ORDER by fullname ASC";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    while(rs.next()){
                        String username = rs.getString("res_id");
                        String fullname = rs.getString("fullname");
                        String purok = rs.getString("purok");

                        String[] data = {username, fullname, purok};
                        tableModel.addRow(data);
                    }
                    rs.close();
                    sortSwitch = 0;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else if (sortSwitch == 0){

                try {
                    String sql = "SELECT * from residents ORDER by fullname DESC";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    while(rs.next()){
                        String username = rs.getString("res_id");
                        String fullname = rs.getString("fullname");
                        String purok = rs.getString("purok");

                        String[] data = {username, fullname, purok};
                        tableModel.addRow(data);
                    }
                    rs.close();
                    sortSwitch = 1;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else if (e.getSource() == residentListTable){
            String a = tableModel.getValueAt(residentListTable.getSelectedRow(), 0).toString();
            try {
                connection.close();
                new ResidentProfile(a);
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
