import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

class ColumnHeader extends JPanel {
    JLabel name;
    JLabel price;
    JLabel include;
    JLabel quantityLabel;

    ColumnHeader() {
        name = new JLabel("Food");
        price = new JLabel("Price");
        include = new JLabel("Include");
        quantityLabel = new JLabel("Quantity");

        Font font = new Font("Arial", Font.BOLD, 28);

        this.setLayout(null);

        name.setFont(font);
        name.setBounds(100, 10, 150, 35);

        price.setFont(font);
        price.setBounds(450, 10, 150, 35);

        include.setFont(font);
        include.setBounds(650, 10, 150, 35);

        quantityLabel.setFont(font);
        quantityLabel.setBounds(800, 10, 150, 35);

        this.add(name);
        this.add(price);
        this.add(include);
        this.add(quantityLabel);
    }
}

class MenuJPanel extends JPanel {
    JPanel temPanel = new JPanel();
    JScrollPane jsp;
    ArrayList<ItemPanel> aItemPanels = new ArrayList<ItemPanel>();
    Connection con = App.con;
    Menu menuObj;

    // temPanel
    {
        temPanel.setLayout(new GridLayout(18, 1, 0, 20));
        temPanel.setBackground(Color.white);
    }

    // Getting menu
    {
        try {
            menuObj = new Menu(con);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Adding panels in array
    // Correct
    {
        for (Food f : menuObj.menu) {
            aItemPanels.add(new ItemPanel(f));
        }
    }

    // Test

    MenuJPanel() {
        this.setBounds(0, 110, 1085, 400);
        this.setLayout(new BorderLayout());
        int i = 0;
        for (ItemPanel itemPanel : aItemPanels) {
            if (i == 0) {
                BoldRowPanel m = new BoldRowPanel("North Indian");
                temPanel.add(m);
            } else if (i == 5) {
                temPanel.add(new BoldRowPanel("South Indian"));
            } else if (i == 10) {
                temPanel.add(new BoldRowPanel("Chinese"));
            }
            temPanel.add(itemPanel);
            ++i;
        }
        jsp = new JScrollPane(temPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(jsp, BorderLayout.CENTER);
        this.setBackground(Color.orange);
    }

    public ArrayList<FoodOrder> getFoodOrder() {
        ArrayList<FoodOrder> foodOrders = new ArrayList<FoodOrder>();
        int i = 1;
        for (ItemPanel itemPanel : aItemPanels) {
            if (itemPanel.getCheckState()) {
                FoodOrder fOrder = new FoodOrder(itemPanel.food, itemPanel.getQuantity(), i);
                ++i;
                foodOrders.add(fOrder);
            }
        }
        return foodOrders;
    }
}

public class AsCustomer extends MainPanel {
    MenuJPanel menuJPanel;
    ColumnHeader ch = new ColumnHeader();
    MyButton orderButton = new MyButton("Place Order");

    public AsCustomer(MainPanelBase prevPanel) {
        super(prevPanel); 
        
        this.menuJPanel = new MenuJPanel();
        this.add(menuJPanel);
        
        ch.setBounds(0, 60, 1100, 50);
        this.add(ch);

        orderButton.setBounds(400, 510, 250, 60);
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<FoodOrder> fOrders = menuJPanel.getFoodOrder();
                FoodOrder.generateBill(fOrders);
                App.displayMyBill(fOrders, AsCustomer.this);
            }
        });
        this.add(orderButton);
    }  
}
