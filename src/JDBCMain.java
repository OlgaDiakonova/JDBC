import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCMain {

    public static void main(String[] args) throws IOException, SQLException {

       MerchantTotalSumReport newReport = new MerchantTotalSumReport(1);
       newReport.sumReport(1);
       newReport.sumReport(2);
       newReport.sumReport(3);

       MerchantListReport merchListReport = new MerchantListReport();
       merchListReport.ListReport();

       DataLoad dl = new DataLoad();
       LocalDate dt = LocalDate.now();
       Merchant merch = Service.getMerchantById(2);
       Customer cust1 = Service.getCustomerById(3);
       List<Payment> pmnt = new ArrayList<>();
       pmnt.add(new Payment(11, dt, merch, cust1,"Dell computer", 800.00, 0.00));
       pmnt.add(new Payment(12, dt, merch, cust1,"Microsoft Office", 500.00, 10.00));
       merch.setPayments(pmnt);
       dl.addPayments(merch);

    }

}
