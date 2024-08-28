package AdminClasses.ScheduleClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScheduleProfile extends JFrame implements MouseListener, ActionListener{
    private Connection connection;

    JLabel back;

    String pkey, purok;

    JButton editButton, doneButton, cancelButton, deleteButton;

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

    SimpleDateFormat day = new SimpleDateFormat("dd");
    SimpleDateFormat month = new SimpleDateFormat("MM");
    SimpleDateFormat year = new SimpleDateFormat("yyyy");
    SimpleDateFormat hour = new SimpleDateFormat("HH");
    SimpleDateFormat minutes = new SimpleDateFormat("mm");

    Date dd, mm, yyyy;
    Time hh1,hh2, mm2, mm1;

    public ScheduleProfile(String schedID){
        pkey = schedID;
        connectToDB();



        this.setTitle("Administrator - Schedule Info");
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

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT sched_date from schedules where sched_id = '"+ pkey +"'";
            ResultSet rs = statement.executeQuery(sql);

            if(rs.next()) {
                mm = rs.getDate("sched_date");
                monthComboBox.setSelectedIndex(Integer.parseInt(month.format(mm)));
            }
            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        monthComboBox.setBounds(170,100,70,25);
        monthComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) monthComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        monthComboBox.addActionListener(this);


        JLabel dayText = new JLabel("Day:");
        dayText.setFont(new Font("Calibri", Font.PLAIN, 17));
        dayText.setBounds(290, 70, 100, 30);

        dayComboBoxModel.addElement("dd");

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT sched_date from schedules where sched_id = '"+ pkey +"'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                dd = rs.getDate("sched_date");
                //Date tempTime = day.parse(a);
                for(int i = 1; i <= 31; i++){
                    if(i<10){
                        dayComboBoxModel.addElement("0" + i);
                    } else {
                        dayComboBoxModel.addElement(String.valueOf(i));
                    }
                }
                dayComboBox.setSelectedItem(day.format(dd));
            }
            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT sched_date from schedules where sched_id = '"+ pkey +"'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                yyyy = rs.getDate("sched_date");
                //Date tempTime = day.parse(a);
                yearComboBoxModel.addElement("2021");
                yearComboBoxModel.addElement("2022");
                yearComboBoxModel.addElement("2023");
                yearComboBoxModel.addElement("2024");
                yearComboBoxModel.addElement("2025");

                yearComboBox.setSelectedItem(year.format(yyyy));
            }
            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT start from schedules where sched_id = '"+ pkey +"'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                hh1 = rs.getTime("start");
                //Date tempTime = day.parse(a);
                for(int i = 0; i <= 23; i++){
                    if(i<10){
                        hour1ComboBoxModel.addElement("0" + i);
                    } else {
                        hour1ComboBoxModel.addElement(String.valueOf(i));
                    }
                }
                hour1ComboBox.setSelectedItem(hour.format(hh1));
            }
            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        hour1ComboBox.setBounds(170,190,70,25);
        hour1ComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) hour1ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        hour1ComboBox.addActionListener(this);

        minutes1ComboBoxModel.addElement("mm");

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT start from schedules where sched_id = '"+ pkey +"'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                mm1 = rs.getTime("start");
                //Date tempTime = day.parse(a);
                for(int i = 0; i <= 59; i++){
                    if(i<10){
                        minutes1ComboBoxModel.addElement("0" + i);
                    } else {
                        minutes1ComboBoxModel.addElement(String.valueOf(i));
                    }
                }
                minutes1ComboBox.setSelectedItem(minutes.format(mm1));
            }
            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        minutes1ComboBox.setBounds(290,190,70,25);
        minutes1ComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) minutes1ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        minutes1ComboBox.addActionListener(this);

        JLabel toText = new JLabel("To:");
        toText.setFont(new Font("Calibri", Font.PLAIN, 17));
        toText.setBounds(170, 230, 100, 30);

        hour2ComboBoxModel.addElement("HH");

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT end from schedules where sched_id = '"+ pkey +"'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                hh2 = rs.getTime("end");
                //Date tempTime = day.parse(a);
                for(int i = 0; i <= 23; i++){
                    if(i<10){
                        hour2ComboBoxModel.addElement("0" + i);
                    } else {
                        hour2ComboBoxModel.addElement(String.valueOf(i));
                    }
                }
                System.out.println();
                hour2ComboBox.setSelectedItem(hour.format(hh2));
            }
            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        hour2ComboBox.setBounds(170,260,70,25);
        hour2ComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) hour2ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        hour2ComboBox.addActionListener(this);

        minutes2ComboBoxModel.addElement("mm");

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT end from schedules where sched_id = '"+ pkey +"'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                mm2 = rs.getTime("end");
                //Date tempTime = day.parse(a);
                for(int i = 0; i <= 59; i++){
                    if(i<10){
                        minutes2ComboBoxModel.addElement("0" + i);
                    } else {
                        minutes2ComboBoxModel.addElement(String.valueOf(i));
                    }
                }
                minutes2ComboBox.setSelectedItem(minutes.format(mm2));
            }
            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        minutes2ComboBox.setBounds(290,260,70,25);
        minutes2ComboBox.setForeground(new Color(0, 0, 0));
        ((JLabel) minutes2ComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        minutes2ComboBox.addActionListener(this);

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT sched_purok_name from schedules where sched_id = '"+ pkey +"'";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                purok = rs.getString("sched_purok_name");

            }

            rs.close(); //kuhaon kung mag error
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * from purok";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                String a = rs.getString("purok_name");
                comboBoxModel.addElement(a);
                if(purok.equalsIgnoreCase(a)){
                    purokField.setSelectedItem(a);
                }
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

        setFieldsUneditable();

        purokField.addActionListener(this);

        editButton = new JButton("Edit");
        editButton.setBounds(420,400,60,24);
        editButton.addActionListener(this);

        doneButton = new JButton("Done");
        doneButton.setBounds(420,400,63,24);
        doneButton.setFocusable(false);
        doneButton.addActionListener(this);
        doneButton.setVisible(false);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(340,400,73,24);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);
        deleteButton.setVisible(true);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(340,400,73,24);
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);
        cancelButton.setVisible(false);

        this.add(cancelButton);
        this.add(deleteButton);
        this.add(doneButton);
        this.add(editButton);
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

    public void setFieldsUneditable(){
        dayComboBox.setEnabled(false);
        monthComboBox.setEnabled(false);
        yearComboBox.setEnabled(false);
        hour1ComboBox.setEnabled(false);
        minutes1ComboBox.setEnabled(false);
        hour2ComboBox.setEnabled(false);
        minutes2ComboBox.setEnabled(false);
        purokField.setEnabled(false);
    }

    public void setFieldEditable(){
        dayComboBox.setEnabled(true);
        monthComboBox.setEnabled(true);
        yearComboBox.setEnabled(true);
        hour1ComboBox.setEnabled(true);
        minutes1ComboBox.setEnabled(true);
        hour2ComboBox.setEnabled(true);
        minutes2ComboBox.setEnabled(true);
        purokField.setEnabled(true);
    }

    public void updateSchedule(Date a, Date b, Date c, SimpleDateFormat d, SimpleDateFormat e){

        //tring sql1 = "INSERT into schedules (sched_purok_name, sched_date, start, end, start_end) VALUES (?,?,?,?,?)";

        String sql1 = "UPDATE schedules SET sched_purok_name ='" + (String) purokField.getSelectedItem() + "', sched_date = '" + d.format(a) + "', " +
                "start ='" + e.format(b) + "', end ='" + e.format(c) + "', start_end = '"+ e.format(b) + " - " + e.format(c)+"' WHERE sched_id = '" + pkey + "'";

        try {
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            pst1.execute();

            JOptionPane.showMessageDialog(this, "SCHEDULE UPDATED SUCCESSFULLY!");

            pst1.close();
            connection.close();

            setFieldsUneditable();

            editButton.setVisible(true);
            deleteButton.setVisible(true);
            doneButton.setVisible(false);
            cancelButton.setVisible(false);


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
        }  else if (e.getSource() == deleteButton){
            int dialogButton = JOptionPane.showConfirmDialog (this, "Are you sure you want to delete this schedule?","WARNING",JOptionPane.YES_NO_OPTION);
            if (dialogButton == JOptionPane.YES_OPTION) {
                try {
                    connectToDB();

                    String sql1 = "DELETE from schedules WHERE sched_id = '"+ pkey +"'";
                    PreparedStatement pst1 = connection.prepareStatement(sql1);
                    pst1.execute();

                    JOptionPane.showMessageDialog(this,"SCHEDULE DELETED SUCCESSFULLY!");
                    new Schedules();
                    dispose();
                    pst1.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } else if (e.getSource() == doneButton){
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
                //SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MMM-dd");
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
                        updateSchedule(dateFinal, fromTimeFinal, toTimeFinal, dateFormat, timeFormat);
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

