import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;

class CustomerPanel extends JPanel {
    Customer customer;
    JLabel namLabel;
    JLabel phonJLabel;

    CustomerPanel (Customer customer) {
        this.customer = customer;

        this.namLabel = new JLabel(customer.name);
        this.namLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        this.phonJLabel = new JLabel(customer.phone);
        this.phonJLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        this.namLabel.setBounds(100,5,200,40);
        this.phonJLabel.setBounds(400,5,200,40);

        this.setLayout(null);
        this.add(namLabel);
        this.add(phonJLabel);
    }
}
class CustomerListJPanel extends JPanel{
    ArrayList<Customer> customers;
    Connection con;

    JPanel temPanel ;
    JScrollPane jsp;
    {
        try {
            con = App.con;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    CustomerListJPanel() {
        this.customers = Customer.getCustomers(con);
        this.setLayout(new BorderLayout());

        temPanel = new JPanel(new GridLayout(customers.size()+1,1,0,10));

        temPanel.add(new BoldRowPanel("Customers"));
        for (Customer customer : customers) {
            temPanel.add(new CustomerPanel(customer));
        }
        
        jsp = new JScrollPane(
            temPanel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
        this.add(jsp);
        this.setBackground(Color.white);
    }
}
public class DisCustomerPanel extends MainPanel{
    JButton backButton = new JButton("Back");
    CustomerListJPanel customerListJPanel ;

    DisCustomerPanel(MainPanel prevPanel) {
        super(prevPanel);
        backButton.setBounds(0, 0, 250, 60);
        backButton.setForeground(Color.black);
        backButton.setFont(new Font("Arial", Font.PLAIN, 28));
        this.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
        
        customerListJPanel = new CustomerListJPanel();
        customerListJPanel.setBounds(getVisibleRect());
        customerListJPanel.setBounds(0, 60,1085, 400);
        this.add(customerListJPanel);
    }

}
