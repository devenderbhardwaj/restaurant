import java.sql.Connection;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import utilities.ThemeColor;

class ColumnHeader2 extends JPanel {
    JLabel name ;
    JLabel price;
    
    ColumnHeader2() {
        name = new JLabel("Food");
        price = new JLabel("Price");
        
        Font font = new Font("Arial",Font.BOLD,28);

        this.setLayout(null);

        name.setFont(font);
        name.setBounds(100,10,150,35);

        price.setFont(font);
        price.setBounds(450,10,150,35);

        this.add(name);
        this.add(price); 
    }
}

class MenuJPanel2 extends JPanel {
    JPanel temPanel = new JPanel();
    JScrollPane jsp ;
    ArrayList<ItemPanelForOwner> aItemPanelForOwners = new ArrayList<ItemPanelForOwner>();
    Connection con;
    Menu menuObj;

    //temPanel
    {
        temPanel.setLayout(new GridLayout(18,1,0,10));
        temPanel.setBackground(Color.white);
    }

    //Connection
    {
        try {
            con = App.con;
            menuObj = new Menu(con);
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    //Adding panels in array
    //Correct
    {
        for (Food f : menuObj.menu) {
            aItemPanelForOwners.add(new ItemPanelForOwner(f));
        }
    }

    //Test 
    
    MenuJPanel2() {
        this.setBounds(0, 110,1085, 400);
        this.setLayout(new BorderLayout());
        int i = 0;
        for (ItemPanelForOwner itemPanel : aItemPanelForOwners) {
            if ( i == 0) {
                BoldRowPanel m = new BoldRowPanel("North Indian");
                temPanel.add(m);
            }
            else if (i == 5) {
                temPanel.add(new BoldRowPanel("South Indian"));
            } else if (i == 10) {
                temPanel.add(new BoldRowPanel("Chinese"));
            }
            temPanel.add(itemPanel);
            ++i;
        }
        jsp = new JScrollPane(temPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(jsp,BorderLayout.CENTER);
    }
}
class MenuForOwner extends MainPanel {
    MenuJPanel2 menuJPanel ;
    ColumnHeader2 ch = new ColumnHeader2();

    public MenuForOwner(MainPanelBase prevPanel) {
        super(prevPanel);
        this.setBackground(ThemeColor.color);
        this.menuJPanel = new MenuJPanel2();
        this.add(menuJPanel);
        ch.setBounds(0,60,1100,50);
        this.add(ch);
    }
}
