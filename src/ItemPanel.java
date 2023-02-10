import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalCheckBoxIcon;

import java.awt.event.*;


public class ItemPanel extends JPanel {
    Food food;
    JLabel name ;
    JLabel price ;
    JCheckBox includeItem ;
    JTextField quantityTextField;
    Border border = BorderFactory.createLineBorder(Color.black,5);

    ItemPanel(Food f) {
        this.food = f;
        this.setBackground(Color.white);

        this.name = new JLabel(f.fname);
        this.name.setFont(new Font("Arial",Font.PLAIN,24));

        this.price = new JLabel("$"+String.valueOf(f.price));
        this.price.setFont(new Font("Arial",Font.PLAIN,24));

        this.includeItem = new JCheckBox();
        includeItem.setIcon (new MetalCheckBoxIcon() {
            protected int getControlSize() { return 20; }
        });
        includeItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    ItemPanel.this.quantityTextField.setEnabled(true);
                    ItemPanel.this.quantityTextField.setText("1");
                } else {
                    ItemPanel.this.quantityTextField.setEnabled(false);
                }
                
            }
        });
        this.quantityTextField = new JTextField(3);
        this.quantityTextField.setFont(new FontUIResource("Arial",Font.PLAIN,24));
        this.quantityTextField.setEnabled(false);
        quantityTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (!Character.isDigit(ch)){
                    e.consume();
                }
            }
        });
        this.setLayout(null);
        this.name.setBounds(100, 5, 300, 35);
        this.price.setBounds(450,5,100,35);
        this.includeItem.setBounds(660,5,50,35);
        this.quantityTextField.setBounds(810,5,50,35);

        this.add(this.name);
        this.add(this.price);
        this.add(this.includeItem);
        this.add(this.quantityTextField);
    }
    public int getQuantity() {
        String s =  this.quantityTextField.getText();
        return Integer.parseInt(s);
    }
    public boolean getCheckState() {
        return this.includeItem.isSelected();
    }
}