package Repositories;

import DBConnections.DBConnection;
import Entities.Customer;
import Entities.Merchant;
import Entities.Payment;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {

    DBConnection connectionToDB;

    public PaymentRepository(DBConnection connectionToDB) throws SQLException {
        this.connectionToDB = connectionToDB;
    }

    public DBConnection getConnectionToDB() {
        return connectionToDB;
    }

    public void setConnectionToDB(DBConnection connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public List<Payment> getPaymentByMerchant(Merchant merch)  {

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

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return pmntList;
    }

    public List<Payment> getPaymentsListByCustomer(Customer cust) {

        List<Payment> paymentList = new ArrayList();
        Payment payment = null;
        try (Connection con = connectionToDB.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resPmnt = stmt.executeQuery(

                    "select * from payment where customerId = " + cust.getId());
            while (resPmnt.next()) {
                Integer id = resPmnt.getInt("id");
                LocalDate dt = resPmnt.getDate("dt").toLocalDate();
                Merchant merch = new MerchantRepository(connectionToDB, this).getMerchantById(resPmnt.getInt("merchantId"));
                String goods = resPmnt.getString("goods");
                Double sumPaid = resPmnt.getDouble("sumPaid");
                Double chargePaid = resPmnt.getDouble("chargePaid");

                payment = new Payment(id, dt, merch, cust, goods, sumPaid, chargePaid);
                paymentList.add(payment);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return paymentList;
    }

    public void addPayment(List<Payment> payments) {

        String sql = "INSERT INTO payment (id, dt, ";
        sql += " merchantId, customerId, goods, sumPaid, chargePaid) values(?,?,?,?,?,?,?) ";

        try (Connection con = connectionToDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            for (Payment item : payments) {

                stmt.setInt(1, item.getId());
                stmt.setDate(2, java.sql.Date.valueOf(item.getDt()));
                stmt.setInt(3, item.getMerchant().getId());
                stmt.setInt(4, item.getCust().getId());
                stmt.setString(5, item.getGoods());
                stmt.setDouble(6, item.getSumPaid());
                stmt.setDouble(7, item.getChargePaid());

                stmt.executeUpdate();

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
