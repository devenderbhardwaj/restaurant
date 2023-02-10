import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;

public class ItemPanelForOwner extends JPanel {
    Food food;
    JLabel name;
    JLabel price;
    JButton changeButton;

    Border border = BorderFactory.createLineBorder(Color.black, 5);

    ItemPanelForOwner(Food f) {
        this.food = f;
        this.setBackground(Color.white);

        this.name = new JLabel(f.fname);
        this.name.setFont(new Font("Arial", Font.PLAIN, 24));

        this.price = new JLabel("$" + String.valueOf(f.price));
        this.price.setFont(new Font("Arial", Font.PLAIN, 24));

        this.changeButton = new JButton("Change Details");
        this.changeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel message = new JLabel("Add details for new item");
            message.setPreferredSize(new Dimension(300,40));
            message.setFont(new Font("Arial",Font.BOLD,16));

            JLabel foodNameLabel = new JLabel("New Name");
            foodNameLabel.setBounds(0,0,80,25);
            JTextField foodNamField = new JTextField(15);
            foodNamField.setBounds(90,0,100,25);
            JPanel foodNamPanel = new JPanel(null);
            foodNamPanel.add(foodNameLabel);
            foodNamPanel.add(Box.createHorizontalStrut(10));
            foodNamPanel.add(foodNamField);

            JLabel foodPriceLabel = new JLabel("New Price");
            foodPriceLabel.setBounds(0,0,80,25);
            JTextField foodPriceField = new JTextField(3);
            foodPriceField.setBounds(90,0,50,25);
            JPanel foodPricPanel = new JPanel(null);
            foodPricPanel.add(foodPriceLabel);
            foodPricPanel.add(Box.createHorizontalStrut(20));
            foodPricPanel.add(foodPriceField);

            foodPriceField.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char ch = e.getKeyChar();
                    if (!Character.isDigit(ch)){
                        e.consume();
                    }
                }
            });
            JPanel panel = new JPanel(new GridLayout(3,1,0,0));
            panel.add(message);
            panel.add(foodNamPanel);
            panel.add(foodPricPanel);
            int result = JOptionPane.showConfirmDialog(ItemPanelForOwner.this, panel, "Change food details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Food newFood = new Food(foodNamField.getText(),f.category,Integer.valueOf(foodPriceField.getText()));
                Food.updateFood(App.con, f.fid, newFood.fname, newFood.price);
                ItemPanelForOwner.this.food = newFood;
                ItemPanelForOwner.this.name.setText(newFood.fname);
                ItemPanelForOwner.this.price.setText("$" + String.valueOf(newFood.price));
            
            }
        } 
        });
        this.setLayout(null);
        this.name.setBounds(100, 5, 300, 35);
        this.price.setBounds(500, 5, 100, 35);
        this.changeButton.setBounds(680,5,200,35);

        this.add(name);
        this.add(this.price);
        this.add(this.changeButton);

    }
}
