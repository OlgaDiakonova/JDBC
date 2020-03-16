package Repositories;

import DBConnections.DBConnection;
import Entities.Customer;
import Entities.Merchant;
import Entities.Payment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {

    DBConnection connectionToDB;

    public PaymentRepository(DBConnection connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public DBConnection getConnectionToDB() {
        return connectionToDB;
    }

    public void setConnectionToDB(DBConnection connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public List<Payment> getPaymentByMerchant(Merchant merch) throws SQLException {

        Payment newPayment = null;
        List<Payment> pmntList = new ArrayList<>();
        try (Connection con = connectionToDB.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resPmnt = stmt.executeQuery(

                    "select * from payment where merchantId = " + merch.getId());
            while (resPmnt.next()) {
                Integer id = resPmnt.getInt("id");
                LocalDate dt = resPmnt.getDate("dt").toLocalDate();
                Customer cust = new CustomerRepository(connectionToDB).getById(resPmnt.getInt("merchantId"));
                String goods = resPmnt.getString("goods");
                Double sumPaid = resPmnt.getDouble("sumPaid");
                Double chargePaid = resPmnt.getDouble("chargePaid");

                newPayment = new Payment(id, dt, merch, cust, goods, sumPaid, chargePaid);

                pmntList.add(newPayment);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pmntList;
    }

    public List<Payment> getPaymentsListByCustomer(Customer cust) throws SQLException {

        List<Payment> paymentList = new ArrayList();
        Payment payment = null;
        try (Connection con = connectionToDB.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resPmnt = stmt.executeQuery(

                    "select * from payment where customerId = " + cust.getId());
            while (resPmnt.next()) {
                Integer id = resPmnt.getInt("id");
                LocalDate dt = resPmnt.getDate("dt").toLocalDate();
                Merchant merch = new MerchantRepository(connectionToDB).getMerchantById(resPmnt.getInt("merchantId"));
                String goods = resPmnt.getString("goods");
                Double sumPaid = resPmnt.getDouble("sumPaid");
                Double chargePaid = resPmnt.getDouble("chargePaid");

                payment = new Payment(id, dt, merch, cust, goods, sumPaid, chargePaid);
                paymentList.add(payment);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return paymentList;
    }
}
