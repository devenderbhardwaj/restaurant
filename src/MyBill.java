import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import utilities.ThemeColor;

class ColumnHeaderForBill extends JPanel {
    JLabel name;
    JLabel price;
    JLabel quantity;
    JLabel total;

    ColumnHeaderForBill() {
        name = new JLabel("Food");
        price = new JLabel("Price");
        quantity = new JLabel("Quantity");
        total = new JLabel("Total");

        Font font = new Font("Arial", Font.BOLD, 28);

        this.setLayout(null);

        name.setFont(font);
        name.setBounds(100, 10, 150, 35);

        price.setFont(font);
        price.setBounds(450, 10, 150, 35);

        quantity.setFont(font);
        quantity.setBounds(650, 10, 150, 35);

        total.setFont(font);
        total.setBounds(840, 10, 150, 35);

        this.add(name);
        this.add(price);
        this.add(quantity);
        this.add(total);
    }
}

class BillJPanel extends JPanel {
    JPanel temPanel = new JPanel(new GridLayout(18, 1, 0, 15));
    JScrollPane jsp;
    ArrayList<OrderPanel> aOrderPanels = new ArrayList<OrderPanel>();

    public BillJPanel(ArrayList<FoodOrder> foodOrders) {
        this.setBounds(0, 110, 1085, 400);
        this.setLayout(new BorderLayout());
        temPanel.setBackground(Color.white);

        temPanel.add(new BoldRowPanel("Bill"));
        for (FoodOrder fOrder : foodOrders) {
            temPanel.add(new OrderPanel(fOrder));
        }
        String totAmount = String.valueOf(FoodOrder.generateBill(foodOrders));
        temPanel.add(new BoldRowPanel("Total : " + totAmount));
        jsp = new JScrollPane(temPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(jsp, BorderLayout.CENTER);
    }

}

class MyBill extends MainPanel {
    JButton backButton = new JButton("Back");
    BillJPanel billJPanel;
    ColumnHeaderForBill ch = new ColumnHeaderForBill();
    JButton payButton = new JButton("Pay");

    public MyBill(ArrayList<FoodOrder> foodOrders, MainPanel prevPanel) {
        super(prevPanel);
        this.setBackground(ThemeColor.color);
        this.billJPanel = new BillJPanel(foodOrders);
        this.add(billJPanel);
        backButton.setBounds(0, 0, 250, 60);
        backButton.setForeground(Color.black);
        backButton.setFont(new Font("Arial", Font.PLAIN, 28));
        this.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
        ch.setBounds(0, 60, 1100, 50);
        this.add(ch);

        payButton.setBounds(400, 510, 250, 60);
        payButton.setForeground(Color.black);
        payButton.setFont(new Font("Arial", Font.PLAIN, 28));
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JLabel nLabel = new JLabel("Name");
                JTextField xField = new JTextField(12);
                JPanel namPanel = new JPanel();
                namPanel.add(nLabel);
                namPanel.add(Box.createHorizontalStrut(15));
                namPanel.add(xField);

                JLabel pJLabel = new JLabel("Phone");
                JTextField phoneField = new JTextField(12);
                JPanel phnPanel = new JPanel();
                phnPanel.add(pJLabel);
                phnPanel.add(Box.createHorizontalStrut(15));
                phnPanel.add(phoneField);
                phoneField.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        char ch = e.getKeyChar();
                        if (!Character.isDigit(ch)){
                            e.consume();
                        }
                    }
                });
                JLabel label = new JLabel("Proceed with payment of " + String.valueOf(FoodOrder.generateBill(foodOrders)));
                JPanel myPanel = new JPanel(new GridLayout(4,1));
                myPanel.add(namPanel);
                myPanel.add(phnPanel);
                myPanel.add(label);
                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Please Enter ", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String name = xField.getText();
                    String phone = phoneField.getText();
                    try {
                        Customer.addCustomer(App.con, new Customer(name, phone));
                        App.displayExitPanel(MyBill.this,xField.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(prevPanel, ex.toString());
                    }
                    System.out.println("x value: " + xField.getText());
                    System.out.println("y value: " + phoneField.getText());
                }
                // String[] options = { "Yes", "No" };

                // int x = JOptionPane.showOptionDialog(
                //         MyBill.this,
                //         "Proceed with payment of " + String.valueOf(FoodOrder.generateBill(foodOrders)),
                //         "Pay Bill",
                //         JOptionPane.OK_CANCEL_OPTION,
                //         JOptionPane.PLAIN_MESSAGE,
                //         null,
                //         options,
                //         0);
                // System.out.println(x);
                // if (x == 0) {
                // }
            }
        });
        this.add(payButton);
    }
}