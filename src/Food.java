import utilities.ProvidePadding;
import java.sql.*;

public class Food {
    String fname ;
    int price ;
    int fid;
    String category;

    public Food(String fname, String category, int price) {
        this.fname = fname;
        this.category = category;
        this.price = price;
    }
    public Food(String fname, String category, int price,int fid) {
        this.fname = fname;
        this.category = category;
        this.price = price;
        this.fid = fid;
    }

    public Food(Food f) {
        this.fid = f.fid;
        this.fname = f.fname;
        this.price = f.price;
        this.category = f.category;
    }
    public String toString() {
        String str = ProvidePadding.paddBoth(String.valueOf(this.fid),10,' ');
        str += ProvidePadding.paddLeft(fname, 30, ' ');
        str += ProvidePadding.paddBoth("$"+this.price, 20, ' ');
        return str;
    }
    public static boolean updateFood(Connection con, int fid, String name, int price) {
        boolean flag = false ;
        String query = "UPDATE menu SET fname = ?, price = ? WHERE fid = ?";
        try {
            PreparedStatement psmt = con.prepareStatement(query);
            psmt.setString(1, name);
            psmt.setString(2, String.valueOf(price));
            psmt.setString(3, String.valueOf(fid));
            psmt.executeUpdate();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag; 
    }
}