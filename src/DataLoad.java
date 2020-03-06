
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class DataLoad {

    public static void addPayments(Merchant merchant ) throws IOException, SQLException {
        Connection con = Service.getConnection();
        double sum = 0;
        double charge = 0;
        for (Payment item: merchant.getPayments()) {

            String sql = "INSERT INTO payment (id, dt, ";
            sql += " merchantId, customerId, goods, sumPaid, chargePaid) values(?,?,?,?,?,?,?) ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, item.getId());
            stmt.setDate(2, java.sql.Date.valueOf(item.getDt()));
            stmt.setInt(3, item.getMerchant().getId());
            stmt.setInt(4, item.getCust().getId());
            stmt.setString(5, item.getGoods());
            stmt.setDouble(6, item.getSumPaid());
            stmt.setDouble(7, item.getChargePaid());

            sum += item.getSumPaid();
            charge += item.getChargePaid();

            stmt.executeUpdate();
            stmt.close();

        }

        sum += charge;

        String sqlm = "UPDATE merchant set charge = ?, sent = ?, lastSent = ? where id = ? ";
        PreparedStatement stmt1 = con.prepareStatement(sqlm);
        stmt1.setInt(4, merchant.getId());
        stmt1.setDouble(1, charge);
        stmt1.setDouble(2, sum);
        stmt1.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
        stmt1.executeUpdate();
        stmt1.close();

    }

}
