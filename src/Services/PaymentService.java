package Services;
import DBConnections.DBConnection;
import Entities.Customer;
import Entities.Merchant;
import Entities.Payment;
import Repositories.PaymentRepository;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class PaymentService {
    DBConnection connectionToDB;
    PaymentRepository pmntRepo;
    MerchantService merchService;

    public PaymentService(DBConnection connectionToDB, PaymentRepository paymentRepo,MerchantService merchService) {
        this.connectionToDB = connectionToDB;
        this.pmntRepo = paymentRepo;
        this.merchService = merchService;
    }

    public void addPayments(List<Payment> pmnts, Merchant merch) {

       try (Connection conn= connectionToDB.getConnection()){
            conn.setAutoCommit(false);

            pmntRepo.addPayment(pmnts);

            double sum = 0;
            double charge = 0;

            for (Payment item : pmnts) {
                sum += item.getSumPaid();
                charge += item.getChargePaid();
            }
            merchService.updateMerchant(merch, sum, charge);

            merchService.sendFunds(merch);

            conn.commit();

        }catch (IOException | SQLException e){
            e.printStackTrace();
        }

    }

    public List<Payment> getPaymnetsByMerchant(Merchant merch) throws SQLException {

        return pmntRepo.getPaymentByMerchant(merch);
    }

    public List<Payment> getPaymentsByCustomer(Customer cust) throws SQLException {

        return pmntRepo.getPaymentsListByCustomer(cust);
    }

    public void loadPaymentToDB(List<Payment> pmntList){
        pmntRepo.addPayment(pmntList);
    }


}
