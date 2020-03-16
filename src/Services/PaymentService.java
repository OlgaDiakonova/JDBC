package Services;
import DBConnections.DBConnection;
import Entities.Merchant;
import Entities.Payment;
import Repositories.PaymentRepository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentService {
    DBConnection connectionToDB;
    PaymentRepository pmntRepo;

    // TODO: 2020-03-12 move addPayments to Repository and updateMerchant to Repository 

    public PaymentService(DBConnection connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public void addPayments(Merchant merchant ) throws IOException, SQLException {
        Connection con = connectionToDB.getConnection();
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
