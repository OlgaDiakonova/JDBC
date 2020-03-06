import java.io.IOException;
import java.sql.*;
import java.util.Collections;
import java.util.List;

public class MerchantListReport {

    public void ListReport() {
        List<Merchant> merchList;
        try (Connection con = Service.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resSet = stmt.executeQuery(

                    "select * from merchant");

            merchList = Service.createMerchant(resSet);
            Collections.sort(merchList, new SortByName());

            for (Merchant item : merchList) {

                System.out.println(item.toString());
            }
        } catch (SQLException |
                IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }

    }
}
