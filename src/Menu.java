import java.util.ArrayList;
import utilities.ProvidePadding;
import java.sql.*;

public class Menu {
    ArrayList<Food> menu = new ArrayList<Food>();
    public Menu(Connection con) throws SQLException{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from menu");

        while (rs.next()) {
            int fid = rs.getInt("fid");
            Food f = new Food(
                    rs.getString("fname"),
                    rs.getString("category"),
                    rs.getInt("price"),
                    fid);
            this.menu.add(f);
        }
    }
    
    public Food getFood(int fid) {
        for (Food food : this.menu) {
            if (food.fid == fid) {
                return food;
            }
        }
        return null;
    }

    public void displayMenu() {
        String header = ProvidePadding.paddBoth("No.", 10, ' ');
        header += ProvidePadding.paddLeft("Food", 30, ' ');
        header += ProvidePadding.paddBoth("Price", 20, ' ');
        for (int i = 0; i < this.menu.size(); ++i) {
            Food food = this.menu.get(i);
            int fid = food.fid;
            if (fid < 6) {
                if (fid == 1) {
                    System.out.println("\nNorth Indian: \n");
                    System.out.println(header + "\n");
                }
                System.out.println(food);
            } else if (fid < 11) {
                if (fid == 6) {
                    System.out.println("\nSouth Indian: \n");
                    System.out.println(header + "\n");
                }
                System.out.println(food);
            } else {
                if (fid == 11) {
                    System.out.println("\nChinese\n");
                    System.out.println(header + "\n");
                }
                System.out.println(food);
            }
        }
    }

}
