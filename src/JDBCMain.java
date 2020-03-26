import DBConnections.DBConnection;
import Entities.Customer;
import Repositories.CustomerRepository;
import Repositories.MerchantRepository;
import Repositories.PaymentRepository;
import Services.CustomerService;
import Services.MerchantService;
import Services.PaymentService;
import java.sql.SQLException;
import java.sql.Timestamp;


public class JDBCMain {

    public static void main(String[] args) throws SQLException {

        /*********************CREATING SERVICES AND REPOSITORIES**********************************/
        DBConnection connect = new DBConnection();
        PaymentRepository pmntRepo = new PaymentRepository(connect);
        MerchantService newMerchService = new MerchantService(new MerchantRepository(connect, pmntRepo));
        PaymentService pmntService = new PaymentService(connect, pmntRepo, newMerchService);
        CustomerService newCustService = new CustomerService(new CustomerRepository(connect, pmntRepo));
//
//        /*********************CREATING ENTITIES**********************************/
//        Merchant merch = newMerchService.getMerchantById(2);
//        Merchant merch1 = newMerchService.getMerchantById(3);
//        Customer cust1 = new CustomerRepository(connect).getById(3);
//
//        /*********************TASK 1**********************************/
//        double totalSum = newMerchService.getTotalMerchantPaid(merch);
//        System.out.println("Total payment sum for merchant id " + 2 + " is " + totalSum);
//        double totalSum1 = newMerchService.getTotalMerchantPaid(merch1);
//        System.out.println("Total payment sum for merchant id " + 3 + " is " + totalSum1);
//
//        /*********************TASK 2**********************************/
//        List<Merchant> merchList = newMerchService.getSortedMerchantList();
//        for (Merchant item:merchList) {
//            System.out.println(item.toString());
//        }
//
//        /*********************TASK 3**********************************/
//        LocalDate dt = LocalDate.now();
//        List<Payment> pmnt = new ArrayList<>();
//        pmnt.add(new Payment(13, dt, merch, cust1, "Dell computer", 800.00, 0.00));
//        pmnt.add(new Payment(14, dt, merch, cust1, "Microsoft Office", 500.00, 10.00));
//        pmntService.addPayments(pmnt, merch);

          String csvFile = "C:\\Users\\admin\\Desktop\\Merchants_2020_03_08.csv";
          String csvFile1 = "C:\\Users\\admin\\Desktop\\Customers_2020_03_08.csv";
          String csvFile2 = "C:\\Users\\admin\\Desktop\\Payments_2020_03_08.csv";
          //FileLoader newFL = new FileLoader(newMerchService, pmntService, newCustService);
          //newFL.createMerchantsFromFile(csvFile);
          //newFL.createCustomersFromFile(csvFile1);
          //newFL.createPaymentsFromFile(csvFile2);

        Timestamp startDt = Timestamp.valueOf("2020-03-01 00:00:00");
        Timestamp endDt = Timestamp.valueOf("2020-03-10 23:59:59");
        Customer mostActiveCust = newCustService.findTheMostActive(startDt, endDt);
        System.out.println(mostActiveCust);


    }

}


