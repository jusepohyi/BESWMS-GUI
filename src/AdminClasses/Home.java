package AdminClasses;

import AdminClasses.PurokClasses.Puroks;
import AdminClasses.RecorderClasses.Recorders;
import AdminClasses.ResidentClasses.Residents;
import AdminClasses.ScheduleClasses.Schedules;
import LoginClass.Login;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Home extends JFrame implements ActionListener, MouseListener {
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

    JPanel homePanel;

    JLabel userDp;

    DefaultTableModel tableModel = new DefaultTableModel();
    JTable announcementTable = new JTable(tableModel);
    JTableHeader announcementTableTableHeader = announcementTable.getTableHeader();

    DefaultTableModel tableModel1 = new DefaultTableModel();
    JTable finesTable = new JTable(tableModel1);
    JTableHeader finesTableTableHeader = finesTable.getTableHeader();

    String username, password;

    public Home(String uName, String pWord){
        connectToDB();

        username = uName;
        password = pWord;

        this.setTitle("Administrator - Home");
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

        ImageIcon userIcon = new ImageIcon("images\\qwer.png");
        userDp = new JLabel(username);
        userDp.setFont(new Font("Montserrat", Font.PLAIN, 18));
        userDp.setVerticalAlignment(SwingConstants.CENTER);
        userDp.setHorizontalAlignment(SwingConstants.CENTER);
        userDp.setVerticalTextPosition(SwingConstants.BOTTOM);
        userDp.setHorizontalTextPosition(SwingConstants.CENTER);
        userDp.setIcon(userIcon);
        userDp.setForeground(new Color(0,0,0));
        userDp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userDp.setBounds(10,10,120,120);
        userDp.addMouseListener(this);

        JLabel announcementText = new JLabel("Announcements");
        announcementText.setFont(new Font("Calibri", Font.ITALIC, 30));
        announcementText.setVerticalAlignment(SwingConstants.CENTER);
        announcementText.setHorizontalAlignment(SwingConstants.CENTER);
        announcementText.setForeground(new Color(0, 0, 0));
        announcementText.setBounds(0,40,225,40);

        JPanel announcementPanel = new JPanel();
        announcementPanel.setBounds(170,80,750,400);
        announcementPanel.setLayout(new FlowLayout());

        tableModel.addColumn("Today's Schedule:");
        tableModel.addColumn(" ");
        tableModel.addColumn(" ");

        announcementTable.setFillsViewportHeight(true);
        announcementTable.setDefaultEditor(Object.class, null);
        announcementTable.setPreferredScrollableViewportSize(new Dimension(730,25));
        announcementTable.setFont(new Font("Calibri", Font.PLAIN, 15));
        announcementTable.setRowHeight(25);
        announcementTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        announcementTable.getTableHeader().setResizingAllowed(false);
        announcementTable.addMouseListener(this);

        announcementTableTableHeader.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((DefaultTableCellRenderer)announcementTableTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        tableModel1.addColumn("Fines:");
        tableModel1.addColumn(" ");
        tableModel1.addColumn(" ");

        finesTable.setFillsViewportHeight(true);
        finesTable.setDefaultEditor(Object.class, null);
        finesTable.setPreferredScrollableViewportSize(new Dimension(730,230));
        finesTable.setFont(new Font("Calibri", Font.PLAIN, 15));
        finesTable.setRowHeight(25);
        finesTable.getTableHeader().setReorderingAllowed(false); // not allow re-ordering of columns
        finesTable.getTableHeader().setResizingAllowed(false);

        finesTableTableHeader.setFont(new Font("Calibri", Font.PLAIN, 20));
        ((DefaultTableCellRenderer)finesTableTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        homePanel.add(announcementPanel);

        announcementPanel.add(announcementText);
        announcementPanel.add(new JScrollPane(announcementTable));
        announcementPanel.add(new JScrollPane(finesTable));

        createFinesTable();
        createAnnouncementTable();
        homePanel.add(userDp);

        this.add(homePanel);
        this.setLayout(null);
        this.setVisible(true);

    }

    public void createAnnouncementTable(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        java.util.Date curDate = new Date();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from schedules where sched_date = '"+ dateFormat.format(curDate) +"'";
            ResultSet rs = statement.executeQuery(sql);

            if(rs.next()){
                String purok = rs.getString("sched_purok_name");
                String date = rs.getString("sched_date");
                String time = rs.getString("start_end");

                String[] data = {purok,date, time};
                tableModel.addRow(data);
            } else {
                String purok = "--------------------------";
                String date = "--------------------------";
                String time = "--------------------------";

                String[] data = {purok,date, time};
                tableModel.addRow(data);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createFinesTable(){

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from residents ORDER  BY FINES DESC";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                String name = rs.getString("fullname");
                String fines = rs.getString("fines");
                String purok = rs.getString("purok");

                String[] data = {name, purok,fines};
                tableModel1.addRow(data);
            }
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
                new Home(username, password);
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
        } else if (e.getSource() == scheduleItem){
            try {
                connection.close();
                new Schedules();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }  else if (e.getSource() == residentItem){
            try {
                connection.close();
                new Residents();
                dispose();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == userDp){
            try {
                connection.close();
                new AdminProfile(username,password);
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
