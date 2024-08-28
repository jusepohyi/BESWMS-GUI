package AdminClasses.PurokClasses;

import AdminClasses.Home;
import AdminClasses.RecorderClasses.AddRecorder;
import AdminClasses.RecorderClasses.Recorders;
import AdminClasses.ResidentClasses.Residents;
import AdminClasses.ScheduleClasses.Schedules;
import LoginClass.AdminLoginInfo;
import LoginClass.Login;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Puroks extends JFrame implements MouseListener, ActionListener, KeyListener  {
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

    JLabel sortPurokName;
    static int sortSwitch = 1;

    DefaultTableModel tableModel = new DefaultTableModel();
    JTable purokListTable = new JTable(tableModel);
    JTableHeader purokListTableTableHeader = purokListTable.getTableHeader();

    public Puroks(){
        this.setTitle("Administrator - Puroks");
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
        addButton.setBounds(575,450,60,24);
        addButton.setFocusable(false);
        addButton.addActionListener(this);

        ImageIcon sortIcon = new ImageIcon("images\\sort.png");
        sortPurokName = new JLabel();
        sortPurokName.setIcon(sortIcon);
        sortPurokName.setFocusable(false);
        sortPurokName.setBounds(610,117,16,16);
        sortPurokName.addMouseListener(this);

        homePanel.add(addButton);
        homePanel.add(sortPurokName);

        JLabel searchText = new JLabel("Search:");
        searchText.setFont(new Font("Calibri", Font.BOLD, 15));
        searchText.setBounds(345,76,50,20);

        search = new JTextField();
        search.setBounds(405,72,160,25);
        search.addKeyListener(this);

        homePanel.add(searchText);
        homePanel.add(search);

        JPanel purokPanel = new JPanel();
        purokPanel.setBounds(110,105,750,400);
        purokPanel.setLayout(new FlowLayout());

        tableModel.addColumn("List of Puroks");

        purokListTable.setFillsViewportHeight(true);
        purokListTable.setDefaultEditor(Object.class, null);
        purokListTable.setPreferredScrollableViewportSize(new Dimension(300,300));
        purokListTable.setFont(new Font("Calibri", Font.PLAIN, 15));
        purokListTable.setRowHeight(25);
        purokListTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        purokListTable.getTableHeader().setResizingAllowed(false);
        purokListTable.addMouseListener(this);

        purokListTableTableHeader.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((DefaultTableCellRenderer) purokListTableTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        purokPanel.add(new JScrollPane(purokListTable));
        homePanel.add(purokPanel);

        createPurokTable();


        this.add(homePanel);
        this.setLayout(null);
        this.setVisible(true);
    }

    public void createPurokTable(){
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from purok ORDER by purok_name ASC";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                String purokname = rs.getString("purok_name");

                String[] data = {purokname};
                tableModel.addRow(data);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createMenuBar(){
        connectToDB();
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
        } else if (e.getSource() == addButton){
            try {
                connection.close();
                new AddPurok();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == sortPurokName){
            tableModel.setRowCount(0);

            if(sortSwitch == 1) {
                try {
                    String sql = "SELECT * from purok ORDER by purok_name ASC";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        String pname = rs.getString("purok_name");


                        String[] data = {pname};
                        tableModel.addRow(data);
                    }
                    sortSwitch = 0;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else if (sortSwitch == 0){

                try {
                    String sql = "SELECT * from purok ORDER by purok_name DESC";
                    PreparedStatement pst = connection.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {
                        String pname = rs.getString("purok_name");


                        String[] data = {pname};
                        tableModel.addRow(data);

                    }
                    sortSwitch = 1;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else if (e.getSource() == purokListTable) {
            String a = tableModel.getValueAt(purokListTable.getSelectedRow(), 0).toString();

            try {
                connection.close();
                new PurokProfile(a);

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
            createPurokTable();
        } else {
            tableModel.setRowCount(0);

            try {
                Statement statement = connection.createStatement();
                String sql = "SELECT * from purok where purok_name like '" + "%" + search.getText()+ "%" + "'";
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    String pname = rs.getString("purok_name");


                    String[] data = {pname};
                    tableModel.addRow(data);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
