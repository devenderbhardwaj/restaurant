import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

import java.util.Properties;class DbConnection {
    /*
     * Get connection to database
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/" + "restaurant";
        String user, password;
        if ((new File("login.properties")).exists()) {
            Properties properties = new Properties();
            try {
                FileInputStream fis = new FileInputStream("login.properties");
                properties.load(fis);
                fis.close();
                user = properties.getProperty("username");
                password = properties.getProperty("password");
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                File file = new File("login.properties");
                file.delete();
                System.out.println("Printing error");
                System.out.println(e.toString());
            } catch (Exception e) {
                System.out.println("Printing error");
                System.out.println(e.toString());
            }
        }
        JLabel messageLabel = new JLabel("Enter username and password of database");
        messageLabel.setBounds(30, 10, 300, 25);

        JLabel userNamLabel = new JLabel("User name:");
        userNamLabel.setBounds(30, 50, 120, 30);
        JTextField userNameField = new JTextField();
        userNameField.setBounds(140, 50, 120, 30);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 90, 120, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(140, 90, 120, 30);

        JCheckBox saveLoginDetaiCheckBox = new JCheckBox();
        saveLoginDetaiCheckBox.setBounds(50, 160, 210, 30);
        saveLoginDetaiCheckBox.setText("Save username and password");

        JButton okButton = new JButton("Ok");
        okButton.setBounds(50, 200, 80, 40);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(180, 200, 80, 40);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(null);
        myPanel.add(messageLabel);
        myPanel.add(userNamLabel);
        myPanel.add(userNameField);
        myPanel.add(passwordLabel);
        myPanel.add(passwordField);
        myPanel.add(saveLoginDetaiCheckBox);
        myPanel.add(okButton);
        myPanel.add(cancelButton);
        myPanel.setBounds(0, 0, 300, 200);
        JPanel nePanel = new JPanel(new BorderLayout());
        nePanel.add(myPanel, BorderLayout.CENTER);

        JDialog jd = new JDialog();
        jd.setTitle("Log in to database");
        jd.add(nePanel);
        jd.setModal(true);
        jd.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.out.println("Jdialog window closed event received");
            }

            public void windowClosing(WindowEvent e) {
                System.out.println("Jdialog window closing event received");
                System.exit(1);
            }
        });
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jd.setModal(false);
                jd.setVisible(false);
                jd.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        jd.setSize(350, 300);
        jd.setVisible(true);

        user = userNameField.getText();
        password = String.valueOf(passwordField.getPassword());
        Connection con = DriverManager.getConnection(url, user, password);
        if (saveLoginDetaiCheckBox.isSelected()) {
            Properties prop = new Properties();
            prop.put("username", user);
            prop.put("password", password);
            String path = "login.properties";
            try {
                FileOutputStream outputStrem = new FileOutputStream(path);
                prop.store(outputStrem, "This file contains login details for database");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(jd, "Login details cannot be saved");
            }
        }
        return con;
    }
}
