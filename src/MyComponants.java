import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilities.ThemeColor;

class BoldRowPanel extends JPanel {
    JLabel label ;
    BoldRowPanel(String text) {
        label = new JLabel();
        label.setText(text);
        label.setVisible(true);
        label.setForeground(ThemeColor.color);
        label.setOpaque(true);
        label.setFont(new Font("Arial",Font.BOLD,32));
        label.setHorizontalTextPosition(JLabel.LEFT);
        this.add(label);
    }
}
class MyButton extends JButton {
    MyButton(String text) {
        this.setText(text);
        this.setForeground(Color.black);
        this.setFont(new Font("Arial", Font.PLAIN, 28));
    }
}
class MyFrame extends JFrame{
    MyFrame() {
        this.setTitle("Restaurant Management");
        this.setSize(1100, 700);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.getContentPane().setBackground(ThemeColor.color);
        this.setVisible(true);
    }
}
class MainPanelBase extends JPanel {
    MainPanelBase() {
        this.setLayout(null);
        this.setBackground(ThemeColor.color);  
        this.setBounds(0,100,1100,600);
    }
}
class MainPanel extends MainPanelBase {
    MyButton backButton = new MyButton("Back");
    MainPanelBase backMainPanel ;

    public void back() {
        this.setVisible(false);
        this.backMainPanel.setVisible(true);
    }
    
    MainPanel(MainPanelBase prevPanel) {
        this.backMainPanel = prevPanel;
        backButton.setBounds(0, 0, 250, 60);
        this.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
    }
}
