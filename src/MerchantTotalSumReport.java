
import java.io.IOException;
import java.sql.*;

public class MerchantTotalSumReport {
    private int merchantId;

    public MerchantTotalSumReport(int merchantId) {
        this.merchantId = merchantId;
    }

    public void sumReport(int merch) {

        try (Connection con = Service.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resSet = stmt.executeQuery(

                    "select * from merchant where id = " + merch);

            for (Merchant newMerch : Service.createMerchant(resSet)) {


                ResultSet resPmnt = stmt.executeQuery(

                        "select * from payment where merchantId = " + merch);
                Service.createPayments(resPmnt, newMerch);

                displayReport(newMerch);

            }

        } catch (SQLException | IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }

    }

    public void displayReport(Merchant merch) {
        double sum = 0;
        for (Payment pmnts : merch.getPayments()) {
            sum += pmnts.getSumPaid();
        }

        System.out.println("Merch id: " + merch.getId() + ", merch name: " + merch.getName() +
                ", last sent date: " + merch.getLastSent() + ", total sum: " + sum);
    }

}
