import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Properties;

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
                con = DbConnection.getConnection();
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

        LandingPage landingPage = new LandingPage();
        frame.add(landingPage);
        frame.setVisible(true);
    }

}
