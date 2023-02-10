import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Properties;
//Test comment
public class App {
    static Connection con;
    static MyFrame frame;
    static MainPanel panel;
    static MyBill myBill;
    static AsCustomer asCustomer;
    static AsOwner asOwner;
    static MenuForOwner menuForOwner;
    static DisCustomerPanel customerPanel;
    static ExitPanel exitPanel;

    /*
     * Get connection to database
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/" + "restaurant";
        String user, password;
        if ((new File("login.properties")).exists()) {
            Properties prop = new Properties();
            try {
                FileInputStream fis = new FileInputStream("login.properties");
                prop.load(fis);
                fis.close();
                user = prop.getProperty("username");
                System.out.println(user);
                password = prop.getProperty("password");
                System.out.println(password);
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                File file = new File("login.properties");
                file.delete();
                System.out.println("printing error");
                System.out.println(e.toString());
            } catch (Exception e) {
                System.out.println("printing error");
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
                System.out.println("jdialog window closed event received");
            }

            public void windowClosing(WindowEvent e) {
                System.out.println("jdialog window closing event received");
                System.exit(1);
            }
        });
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jd.setModal(false);
                System.out.println("ok button pressed");
                jd.setVisible(false);
                jd.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cancel button pressed");
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

    public static void displayMenuForOwner(MainPanel prevPanel) {
        asOwner.setVisible(false);
        menuForOwner = new MenuForOwner(prevPanel);
        menuForOwner.setVisible(true);
        App.frame.add(menuForOwner);
    }

    public static void displayExitPanel(MainPanel prevPanel, String name) {
        myBill.setVisible(false);
        exitPanel = new ExitPanel(prevPanel, name);
        exitPanel.setVisible(true);
        App.frame.add(exitPanel);
    }

    public static void displayMyBill(ArrayList<FoodOrder> foodOrders, MainPanel prevPanel) {
        asCustomer.setVisible(false);
        myBill = new MyBill(foodOrders, prevPanel);
        myBill.setVisible(true);
        App.frame.add(myBill);
    }

    public static void displayAsCustomer(MainPanel prevPanel) {
        prevPanel.setVisible(false);
        asCustomer = new AsCustomer(prevPanel);
        asCustomer.setVisible(true);
        App.frame.add(asCustomer);
    }

    public static boolean verifyOwner() throws WrongDetails {
        String ownerName, ownerPassword;
        if ((new File("owner.properties")).exists()) {
            
            Properties prop = new Properties();
            try {
                FileInputStream fis = new FileInputStream("owner.properties");
                prop.load(fis);
                fis.close();
                ownerName = prop.getProperty("name");
                ownerPassword = prop.getProperty("password");

                JLabel nLabel = new JLabel("Name");
                JTextField nameField = new JTextField(12);
                JPanel namPanel = new JPanel();
                namPanel.add(nLabel);
                namPanel.add(Box.createHorizontalStrut(15));
                namPanel.add(nameField);

                JLabel passwordLabel = new JLabel("Password");
                JTextField passwordField = new JTextField(12);
                JPanel passwordPanel = new JPanel();
                passwordPanel.add(passwordLabel);
                passwordPanel.add(Box.createHorizontalStrut(15));
                passwordPanel.add(passwordField);

                JPanel myPanel = new JPanel(new GridLayout(4, 1));
                myPanel.add(namPanel);
                myPanel.add(passwordPanel);
                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Login as owner", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String enteredName = nameField.getText();
                    String enteredPassword = passwordField.getText();
            
                    if (enteredName.equals(ownerName) && enteredPassword.equals(ownerPassword)) {
                        return true;
                    } else {
                        WrongDetails t = new WrongDetails();
                        throw t;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(asCustomer, e.toString(),"",JOptionPane.ERROR_MESSAGE);
                System.out.println(e.toString());
            }
        } else {
            JLabel headLabel = new JLabel("");
            headLabel.setText("Create a owner account: ");
            JLabel nLabel = new JLabel("Name");
            JTextField xField = new JTextField(12);
            JPanel namPanel = new JPanel();
            namPanel.add(nLabel);
            namPanel.add(Box.createHorizontalStrut(15));
            namPanel.add(xField);

            JLabel pJLabel = new JLabel("Password");
            JTextField yField = new JTextField(12);
            JPanel phnPanel = new JPanel();
            phnPanel.add(pJLabel);
            phnPanel.add(Box.createHorizontalStrut(15));
            phnPanel.add(yField);

            JPanel myPanel = new JPanel(new GridLayout(4, 1));
            myPanel.add(headLabel);
            myPanel.add(namPanel);
            myPanel.add(phnPanel);
            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Create Owner Account", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String enteredName = xField.getText();
                String enteredPassword = yField.getText();
                Properties prop = new Properties();
                prop.put("name", enteredName);
                prop.put("password", enteredPassword);
                String path = "owner.properties";
                try {
                    FileOutputStream outputStrem = new FileOutputStream(path);
                    prop.store(outputStrem, "This file contains login details for owner");
                    return true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Login details cannot be saved");
                }
            }
        }

        return false;
    }

    public static void displayAsOwner(MainPanel prevPanel) {
        try {
            if (verifyOwner()) {
                asOwner = new AsOwner(prevPanel);
                prevPanel.setVisible(false);
                asOwner.setVisible(true);
                frame.add(asOwner);
            }
        } catch (Exception | WrongDetails e) {
            JOptionPane.showMessageDialog(prevPanel, e.toString(), null, JOptionPane.ERROR_MESSAGE, null);
        }

    }

    public static void main(String[] args) {
        while (true) {
            try {
                con = getConnection();
                break;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(asCustomer, "Enter correct username and password. " + e.toString(), "",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.out.println(e.toString());

            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(asCustomer,
                        "com.mysql.cj.jdbc.Driver not found. Make sure jdbc driver is visible to project."
                                + e.toString(),
                        null, 0, null);
                e.printStackTrace();
                System.exit(0);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(asCustomer, e.toString(), null, JOptionPane.ERROR_MESSAGE, null);
            }
        }

        frame = new MyFrame();
        JLabel heading = new JLabel();
        heading.setText("Star Restaurant");
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setFont(new Font("MV Boli", Font.BOLD, 40));
        heading.setForeground(Color.yellow);
        heading.setBorder(BorderFactory.createLineBorder(new Color(200, 78, 123), 5));
        heading.setBounds(50, 10, 1000, 75);
        frame.add(heading);

        panel = new MainPanel(null);
        panel.setLayout(null);
        panel.setVisible(true);
        frame.add(panel);

        JLabel loginLabel = new JLabel("Log in as:");
        loginLabel.setHorizontalAlignment(JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 36));
        loginLabel.setForeground(Color.orange);
        loginLabel.setBounds(0, 100, 900, 60);
        loginLabel.setVisible(true);
        panel.add(loginLabel);

        MyButton button1 = new MyButton("Customer");
        button1.setBounds(450, 200, 250, 60);
        panel.add(button1);

        MyButton button2 = new MyButton("Owner");
        button2.setBounds(450, 300, 250, 60);
        panel.add(button2);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAsCustomer(panel);
                ;
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAsOwner(panel);
            }
        });

        frame.setVisible(true);
    }

}
