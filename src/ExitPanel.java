import java.awt.Color;

import utilities.ThemeColor;

public class ExitPanel extends MainPanelBase{

    public ExitPanel(String name) {
        BoldRowPanel thank = new BoldRowPanel("Thank You "+name);
        thank.label.setBackground(ThemeColor.color);
        thank.label.setForeground(Color.white);  
        thank.setBackground(ThemeColor.color);  
        thank.setBounds(100,100,500,50);
        this.add(thank);
    }    
}
