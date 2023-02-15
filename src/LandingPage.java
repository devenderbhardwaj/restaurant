import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LandingPage extends MainPanel {
    LandingPage() {
        super(null);
        this.setLayout(null);
        this.setVisible(true);

        JLabel loginLabel = new JLabel("Log in as:");
        loginLabel.setHorizontalAlignment(JLabel.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 36));
        loginLabel.setForeground(Color.orange);
        loginLabel.setBounds(0, 100, 900, 60);
        loginLabel.setVisible(true);
        this.add(loginLabel);

        MyButton button1 = new MyButton("Customer");
        button1.setBounds(450, 200, 250, 60);
        this.add(button1);

        MyButton button2 = new MyButton("Owner");
        button2.setBounds(450, 300, 250, 60);
        this.add(button2);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App.displayAsCustomer(LandingPage.this);
                ;
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App.displayAsOwner(LandingPage.this);
            }
        });
    }
}
