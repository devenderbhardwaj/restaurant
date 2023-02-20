import java.awt.event.*;

public class AsOwner extends MainPanel {
    MyButton seeMenuButton = new MyButton("Menu");
    MyButton seeCustomers = new MyButton("Customers");
    
    public AsOwner(MainPanelBase prevPanel) {
        super(prevPanel);

        this.seeMenuButton.setBounds(200,200,250,60);
        this.add(seeMenuButton);
        this.seeMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App.displayMenuForOwner(AsOwner.this);
            }
        }) ;

        this.seeCustomers.setBounds(200,300,250,60);
        this.add(seeCustomers);
        this.seeCustomers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayCustomers();
            }
        });
    }

    public void displayCustomers() {
        DisCustomerPanel disCustomerPanel = new DisCustomerPanel(this);
        App.frame.add(disCustomerPanel);
        this.setVisible(false);
    }
}
