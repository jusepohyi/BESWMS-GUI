package AdminClasses.ScheduleClasses;


import AdminClasses.Home;
import AdminClasses.PurokClasses.Puroks;
import AdminClasses.RecorderClasses.Recorders;
import AdminClasses.ResidentClasses.Residents;
import LoginClass.AdminLoginInfo;
import LoginClass.Login;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Schedules extends JFrame implements MouseListener,KeyListener, ActionListener {
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

    JLabel sortDate;
    static int sortSwitch = 1;

    DefaultTableModel tableModel = new DefaultTableModel();
    JTable scheduleListTable = new JTable(tableModel);
    JTableHeader scheduleListTableTableHeader = scheduleListTable.getTableHeader();

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> purokField = new JComboBox<>(comboBoxModel);

    public Schedules(){
        connectToDB();

        this.setTitle("Administrator - Schedules");
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
        sortDate = new JLabel();
        sortDate.setIcon(sortIcon);
        sortDate.setFocusable(false);
        sortDate.setBounds(620,117,16,16);
        sortDate.addMouseListener(this);

        homePanel.add(sortDate);
        homePanel.add(addButton);


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

        tableModel.addColumn("Schedule ID");
        tableModel.addColumn("Purok");
        tableModel.addColumn("Date");
        tableModel.addColumn("Time");

        scheduleListTable.setFillsViewportHeight(true);
        scheduleListTable.setDefaultEditor(Object.class, null);
        scheduleListTable.setPreferredScrollableViewportSize(new Dimension(679,300));
        scheduleListTable.setFont(new Font("Calibri", Font.PLAIN, 15));
        scheduleListTable.setRowHeight(25);
        scheduleListTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        scheduleListTable.getTableHeader().setResizingAllowed(false);
        scheduleListTable.addMouseListener(this);

        scheduleListTableTableHeader.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((DefaultTableCellRenderer) scheduleListTableTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        residentPanel.add(new JScrollPane(scheduleListTable));
        homePanel.add(residentPanel);

        createScheduleTable();


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

    public void createScheduleTable(){

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from schedules";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                String id = rs.getString("sched_id");
                String purok = rs.getString("sched_purok_name");
                String date = rs.getString("sched_date");
                String time = rs.getString("start_end");

                String[] data = {id, purok,date, time};
                tableModel.addRow(data);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createSchedTablePurokFiltered(){
        tableModel.setRowCount(0);
        String prk = (String) purokField.getSelectedItem();

        try {
            connectToDB();
            Statement statement = connection.createStatement();
            String sql = "SELECT * from schedules where sched_purok_name = '" + prk + "'";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("sched_id");
                String purok = rs.getString("sched_purok_name");
                String date = rs.getString("sched_date");
                String time = rs.getString("start_end");

                String[] data = {id, purok,date, time};
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
        } else if (e.getSource() == scheduleItem){
            try {
                connection.close();
                new Schedules();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getSource() == purokField) {
            if (purokField.getSelectedItem() == "All") {
                tableModel.setRowCount(0);
                createScheduleTable();
            } else {
                createSchedTablePurokFiltered();
            }
        } else if (e.getSource() == addButton){
            try {
                connection.close();
                new AddSchedule();
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
            if (purokField.getSelectedItem() == "All") {
                tableModel.setRowCount(0);
                createScheduleTable();
            }
        } else {
            if (purokField.getSelectedItem() == "All") {
                tableModel.setRowCount(0);

                try {
                    Statement statement = connection.createStatement();
                    String sql = "SELECT * from schedules where sched_date like '" + "%" + search.getText() + "%" + "'";
                    ResultSet rs = statement.executeQuery(sql);

                    while (rs.next()) {
                        String id = rs.getString("sched_id");
                        String purok = rs.getString("sched_purok_name");
                        String date = rs.getString("sched_date");
                        String time = rs.getString("start_end");

                        String[] data = {id, purok, date, time};
                        tableModel.addRow(data);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == sortDate) {
            tableModel.setRowCount(0);

            if (sortSwitch == 1) {
                try {
                    String sql = "SELECT * from schedules ORDER by sched_date ASC";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        String id = rs.getString("sched_id");
                        String purok = rs.getString("sched_purok_name");
                        String date = rs.getString("sched_date");
                        String time = rs.getString("start_end");

                        String[] data = {id, purok, date, time};
                        tableModel.addRow(data);
                    }
                    sortSwitch = 0;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (sortSwitch == 0) {
                try {
                    String sql = "SELECT * from schedules ORDER by sched_date DESC";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        String id = rs.getString("sched_id");
                        String purok = rs.getString("sched_purok_name");
                        String date = rs.getString("sched_date");
                        String time = rs.getString("start_end");

                        String[] data = {id, purok, date, time};
                        tableModel.addRow(data);
                    }
                    sortSwitch = 1;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else if (e.getSource() == scheduleListTable) {
            String a = tableModel.getValueAt(scheduleListTable.getSelectedRow(), 0).toString();

            try {
                connection.close();
                new ScheduleProfile(a);

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
}

