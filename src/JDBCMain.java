import DBConnections.DBConnection;
import Entities.Customer;
import Entities.Payment;
import Entities.Merchant;
import Repositories.CustomerRepository;
import Repositories.MerchantRepository;
import Repositories.PaymentRepository;
import Services.CustomerService;
import Services.MerchantService;
import Services.PaymentService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCMain {

    public static void main(String[] args) throws IOException, SQLException {

        /*********************CREATING SERVICES AND REPOSITORIES**********************************/
        PaymentRepository pmntRepo = new PaymentRepository(new DBConnection());
        MerchantService newMerchService = new MerchantService(new MerchantRepository(new DBConnection(), pmntRepo));
        PaymentService pmntService = new PaymentService(new DBConnection(), pmntRepo, newMerchService);
        CustomerService newCustService = new CustomerService();

        /*********************CREATING ENTITIES**********************************/
        Merchant merch = newMerchService.getMerchantById(2);
        Merchant merch1 = newMerchService.getMerchantById(3);
        Customer cust1 = new CustomerRepository(new DBConnection()).getById(3);

        /*********************TASK 1**********************************/
        double totalSum = newMerchService.getTotalMerchantPaid(merch);
        System.out.println("Total payment sum for merchant id " + 2 + " is " + totalSum);
        double totalSum1 = newMerchService.getTotalMerchantPaid(merch1);
        System.out.println("Total payment sum for merchant id " + 3 + " is " + totalSum1);

        /*********************TASK 2**********************************/
        List<Merchant> merchList = newMerchService.getSortedMerchantList();
        for (Merchant item:merchList) {
            System.out.println(item.toString());
        }

        /*********************TASK 3**********************************/
        LocalDate dt = LocalDate.now();
        List<Payment> pmnt = new ArrayList<>();
        pmnt.add(new Payment(13, dt, merch, cust1, "Dell computer", 800.00, 0.00));
        pmnt.add(new Payment(14, dt, merch, cust1, "Microsoft Office", 500.00, 10.00));
        pmntService.addPayments(pmnt, merch);



    }

}
