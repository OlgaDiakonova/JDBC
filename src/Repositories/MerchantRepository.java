package Repositories;

import Entities.Merchant;
import DBConnections.DBConnection;
import Entities.Payment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MerchantRepository {

    DBConnection connectionToDB;
    PaymentRepository pmntRepo;

    // TODO: 2020-03-12 use pmntRepo 

    public MerchantRepository(DBConnection connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public Merchant getMerchantById(int merchId) throws SQLException {

        Merchant newMerch = null;
        try (Connection con = connectionToDB.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resSet = stmt.executeQuery(

                    "select * from merchant where id = " + merchId);
            while (resSet.next()) {
                Integer id = resSet.getInt("id");
                String nm = resSet.getString("name");
                LocalDate lastSent = resSet.getDate("lastSent").toLocalDate();
                String bankName = resSet.getString("bankName");
                String swift = resSet.getString("swift");
                String account = resSet.getString("account");
                Double charge = resSet.getDouble("charge");
                Integer period = resSet.getInt("period");
                Double minSum = resSet.getDouble("minSum");
                Double needToSend = resSet.getDouble("needToSend");
                Double sent = resSet.getDouble("sent");

                newMerch = new Merchant(id, nm, bankName, swift, account, charge, period, minSum, needToSend, sent, lastSent);
           }

            List<Payment> pmntList = new PaymentRepository(connectionToDB).getPaymentByMerchant(newMerch);
            newMerch.setPayments(pmntList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newMerch;
    }

    public List<Merchant> getMerchantList() throws SQLException {

        Merchant newMerch = null;
        List<Merchant> merchantList = new ArrayList<>();
        try (Connection con = connectionToDB.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resSet = stmt.executeQuery(

                    "select * from merchant");
            while (resSet.next()) {
                Integer id = resSet.getInt("id");
                String nm = resSet.getString("name");
                LocalDate lastSent = resSet.getDate("lastSent").toLocalDate();
                String bankName = resSet.getString("bankName");
                String swift = resSet.getString("swift");
                String account = resSet.getString("account");
                Double charge = resSet.getDouble("charge");
                Integer period = resSet.getInt("period");
                Double minSum = resSet.getDouble("minSum");
                Double needToSend = resSet.getDouble("needToSend");
                Double sent = resSet.getDouble("sent");

                newMerch = new Merchant(id, nm, bankName, swift, account, charge, period, minSum, needToSend, sent, lastSent);
                merchantList.add(newMerch);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return merchantList;
    }

}
