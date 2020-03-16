import DBConnections.DBConnection;
import Entities.Customer;
import Entities.Payment;
import Entities.Merchant;
import Repositories.CustomerRepository;
import Repositories.MerchantRepository;
import Services.MerchantService;
import Services.PaymentService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCMain {

    public static void main(String[] args) throws IOException, SQLException {


       MerchantService newReport = new MerchantService(new MerchantRepository(new DBConnection()));
       newReport.sumReport(1);
       newReport.sumReport(2);
       newReport.sumReport(3);

       MerchantService merchListReport = new MerchantService(new MerchantRepository(new DBConnection()));
       merchListReport.reportSortedMerchantList();

       LocalDate dt = LocalDate.now();
       Merchant merch = new MerchantRepository(new DBConnection()).getMerchantById(2);
       Customer cust1 = new CustomerRepository(new DBConnection()).getById(3);
       List<Payment> pmnt = new ArrayList<>();
       pmnt.add(new Payment(11, dt, merch, cust1,"Dell computer", 800.00, 0.00));
       pmnt.add(new Payment(12, dt, merch, cust1,"Microsoft Office", 500.00, 10.00));
       merch.setPayments(pmnt);
       PaymentService ps = new PaymentService(new DBConnection());
       ps.addPayments(merch);

    }

}
