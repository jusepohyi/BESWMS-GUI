package AdminClasses.ScheduleClasses;

import AdminClasses.ResidentClasses.Residents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddSchedule extends JFrame implements MouseListener, ActionListener{
    private Connection connection;

    JLabel back;

    JButton addButton;

    DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> purokField = new JComboBox<>(comboBoxModel);

    DefaultComboBoxModel<String> monthComboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> monthComboBox = new JComboBox<>(monthComboBoxModel);

    DefaultComboBoxModel<String> dayComboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> dayComboBox = new JComboBox<>(dayComboBoxModel);

    DefaultComboBoxModel<String> yearComboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> yearComboBox = new JComboBox<>(yearComboBoxModel);

    DefaultComboBoxModel<String> hour1ComboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> hour1ComboBox = new JComboBox<>(hour1ComboBoxModel);

    DefaultComboBoxModel<String> minutes1ComboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> minutes1ComboBox = new JComboBox<>(minutes1ComboBoxModel);

    DefaultComboBoxModel<String> hour2ComboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> hour2ComboBox = new JComboBox<>(hour2ComboBoxModel);

    DefaultComboBoxModel<String> minutes2ComboBoxModel = new DefaultComboBoxModel<>();
    JComboBox<String> minutes2ComboBox = new JComboBox<>(minutes2ComboBoxModel);

    SimpleDateFormat dateFormatOnTable = new SimpleDateFormat("EEE, MM-dd-yyyy");

    public AddSchedule(){
        connectToDB();

        this.setTitle("Administrator - Add Schedule");
        this.setSize(650, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon logo = new ImageIcon("images\\logo.jpg");
        this.setIconImage(logo.getImage());

        ImageIcon backIcon = new ImageIcon("images\\backIcon.png");
        back = new JLabel();
        back.setIcon(backIcon);
        back.setBounds(20,30,70,34);
        back.addMouseListener(this);

        JLabel dateText = new JLabel("Date:");
        dateText.setFont(new Font("Calibri", Font.PLAIN, 17));
        dateText.setBounds(110, 50, 100, 30);

        JLabel monthText = new JLabel("Month:");
        monthText.setFont(new Font("Calibri", Font.PLAIN, 17));
        monthText.setBounds(170, 70, 100, 30);

        monthComboBoxModel.addElement("MMM"); monthComboBoxModel.addElement("Jan"); monthComboBoxModel.addElement("Feb"); monthComboBoxModel.addElement("Mar");
        monthComboBoxModel.addElement("Apr"); monthComboBoxModel.addElement("May"); monthComboBoxModel.addElement("Jun"); monthComboBoxModel.addElement("Jul");
        monthComboBoxModel.addElement("Aug"); monthComboBoxModel.addElement("Sep"); monthComboBoxModel.addElement("Oct"); monthComboBoxModel.addElement("Nov");
        monthComboBoxModel.addElement("Dec");

        monthComboBox.setBounds(170,100,70,25);
        monthComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) monthComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        monthComboBox.addActionListener(this);


        JLabel dayText = new JLabel("Day:");
        dayText.setFont(new Font("Calibri", Font.PLAIN, 17));
        dayText.setBounds(290, 70, 100, 30);

        dayComboBoxModel.addElement("dd");
        for(int i = 1; i <= 31; i++){
            if(i<10){
                dayComboBoxModel.addElement("0" + i);
            } else {
                dayComboBoxModel.addElement(String.valueOf(i));
            }
        }

        dayComboBox.setBounds(290,100,70,25);
        dayComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) dayComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        dayComboBox.addActionListener(this);

        JLabel yearText = new JLabel("year:");
        yearText.setFont(new Font("Calibri", Font.PLAIN, 17));
        yearText.setBounds(410, 70, 100, 30);

        yearComboBoxModel.addElement("yyyy");
        yearComboBoxModel.addElement("2021");
        yearComboBoxModel.addElement("2022");
        yearComboBoxModel.addElement("2023");
        yearComboBoxModel.addElement("2024");
        yearComboBoxModel.addElement("2025");
        
        yearComboBox.setBounds(410,100,70,25);
        yearComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) yearComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        yearComboBox.addActionListener(this);

        JLabel timeText = new JLabel("Time:");
        timeText.setFont(new Font("Calibri", Font.PLAIN, 17));
        timeText.setBounds(110, 140, 100, 30);

        JLabel fromText = new JLabel("From:");
        fromText.setFont(new Font("Calibri", Font.PLAIN, 17));
        fromText.setBounds(170, 160, 100, 30);

        hour1ComboBoxModel.addElement("HH");
        for(int i = 0; i <= 23; i++){
            if(i<10){
                 hour1ComboBoxModel.addElement("0" + i);
            } else {
                 hour1ComboBoxModel.addElement(String.valueOf(i));
            }
        }
        hour1ComboBox.setBounds(170,190,70,25);
        hour1ComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) hour1ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        hour1ComboBox.addActionListener(this);

        minutes1ComboBoxModel.addElement("mm");
        for(int i = 0; i <= 59; i++){
            if(i<10){
                minutes1ComboBoxModel.addElement("0" + i);
            } else {
                minutes1ComboBoxModel.addElement(String.valueOf(i));
            }
        }

        minutes1ComboBox.setBounds(290,190,70,25);
        minutes1ComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) minutes1ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        minutes1ComboBox.addActionListener(this);

        JLabel toText = new JLabel("To:");
        toText.setFont(new Font("Calibri", Font.PLAIN, 17));
        toText.setBounds(170, 230, 100, 30);

        hour2ComboBoxModel.addElement("HH");
        for(int i = 0; i <= 23; i++){
            if(i<10){
                hour2ComboBoxModel.addElement("0" + i);
            } else {
                hour2ComboBoxModel.addElement(String.valueOf(i));
            }
        }

        hour2ComboBox.setBounds(170,260,70,25);
        hour2ComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) hour2ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        hour2ComboBox.addActionListener(this);

        minutes2ComboBoxModel.addElement("mm");
        for(int i = 0; i <= 59; i++){
            if(i<10){
                minutes2ComboBoxModel.addElement("0" + i);
            } else {
                minutes2ComboBoxModel.addElement(String.valueOf(i));
            }
        }


        minutes2ComboBox.setBounds(290,260,70,25);
        minutes2ComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) minutes2ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        minutes2ComboBox.addActionListener(this);

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from purok";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String purok = rs.getString("purok_name");
                comboBoxModel.addElement(purok);
            }

            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        JLabel purokText = new JLabel("Purok:");
        purokText.setFont(new Font("Calibri", Font.PLAIN, 17));
        purokText.setBounds(110, 320, 200, 30);

        purokField.setBounds(220,320,150,25);
        ((JLabel) purokField.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        purokField.setSelectedIndex(0);
        purokField.addActionListener(this);

        addButton = new JButton("Add");
        addButton.setBounds(420,400,60,24);
        addButton.setFocusable(true);
        addButton.addActionListener(this);

        this.add(addButton);

        this.add(purokText);
        this.add(purokField);
        this.add(minutes2ComboBox);
        this.add(hour2ComboBox);
        this.add(toText);
        this.add(minutes1ComboBox);
        this.add(hour1ComboBox);
        this.add(fromText);
        this.add(timeText);
        this.add(yearComboBox);
        this.add(yearText);
        this.add(dayComboBox);
        this.add(dayText);
        this.add(monthComboBox);
        this.add(monthText);
        this.add(dateText);
        this.add(back);

        this.setLayout(null);
        this.setVisible(true);
    }

    public void addSchedule(Date a, Date b, Date c, SimpleDateFormat d, SimpleDateFormat e){

        String sql1 = "INSERT into schedules (sched_purok_name, sched_date, start, end, start_end) VALUES (?,?,?,?,?)";


        try {
            PreparedStatement pst = connection.prepareStatement(sql1);

            pst.setString(1, (String) purokField.getSelectedItem());
            pst.setString(2, d.format(a));
            pst.setString(3, e.format(b));
            pst.setString(4, e.format(c));
            pst.setString(5, e.format(b) + " - " + e.format(c));

            JOptionPane.showMessageDialog(this, "SCHEDULE ADDED SUCCESSFULLY!");

            dayComboBox.setSelectedItem("dd");
            monthComboBox.setSelectedItem("MMM");
            yearComboBox.setSelectedItem("yyyy");
            hour1ComboBox.setSelectedItem("HH");
            hour2ComboBox.setSelectedItem("HH");
            minutes1ComboBox.setSelectedItem("mm");
            minutes2ComboBox.setSelectedItem("mm");

            pst.executeUpdate();
            pst.close();
            connection.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton) {
            connectToDB();

             if (monthComboBoxModel.getSelectedItem() == "MMM"){
                JOptionPane.showMessageDialog(this, "Month must not be empty!");
            } else if(dayComboBoxModel.getSelectedItem() == "dd"){
                JOptionPane.showMessageDialog(this, "Day must not be empty!");
            } else if (yearComboBoxModel.getSelectedItem() == "yyyy"){
                JOptionPane.showMessageDialog(this, "Year must not be empty!");
            } else if (hour1ComboBoxModel.getSelectedItem()  == "HH" || hour2ComboBoxModel.getSelectedItem() == "HH"){
                JOptionPane.showMessageDialog(this, "Hour must not be empty!");
            } else if (minutes2ComboBoxModel.getSelectedItem() == "mm"  || minutes2ComboBoxModel.getSelectedItem() == "mm"){
                JOptionPane.showMessageDialog(this, "Minutes must not be empty!");
            } else {

                 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                 SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MMM-dd");
                 SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                 String year = (String) yearComboBoxModel.getSelectedItem();
                 int month = monthComboBox.getSelectedIndex();
                 String day = (String) dayComboBoxModel.getSelectedItem();

                 String hour1 = (String) hour1ComboBoxModel.getSelectedItem();
                 String minutes1 = (String) minutes1ComboBoxModel.getSelectedItem();

                 String hour2 = (String) hour2ComboBoxModel.getSelectedItem();
                 String minutes2 = (String) minutes2ComboBoxModel.getSelectedItem();
                 try {

                     Date dateFinal = dateFormat.parse(year+ "-"+month+"-"+day);
                     Date fromTimeFinal = timeFormat.parse(hour1 + ":" + minutes1+ ":" + "00");
                     Date toTimeFinal = timeFormat.parse(hour2 + ":" + minutes2 +":"+"00");
                     Date curDate = new Date();

                     Statement statement = connection.createStatement();
                     String sql = "SELECT * from schedules";
                     ResultSet rs = statement.executeQuery(sql);

                     boolean idk = true;

                     while (rs.next()){
                         if(rs.getString("sched_date").compareTo(dateFormat.format(dateFinal)) == 0){
                             JOptionPane.showMessageDialog(this,"Can't accept duplicate schedules!");
                             idk = false;
                             break;
                         } else if (dateFormat.format(curDate).compareTo(dateFormat.format(dateFinal)) > 0){
                             JOptionPane.showMessageDialog(this, "Date has already passed!");
                             idk = false;
                             break;
                         }
                     }

                     if(idk){
                         addSchedule(dateFinal, fromTimeFinal, toTimeFinal, dateFormat, timeFormat);
                     } else {
                         System.out.println("do not execute");
                     }


                 } catch (SQLException | ParseException throwables) {
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
                    new Schedules();
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
