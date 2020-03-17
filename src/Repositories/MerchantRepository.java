package Repositories;

import Entities.Merchant;
import DBConnections.DBConnection;
import Entities.Payment;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MerchantRepository {

    DBConnection connectionToDB;
    PaymentRepository pmntRepo;

    public PaymentRepository getPmntRepo() {
        return pmntRepo;
    }

    public void setPmntRepo(PaymentRepository pmntRepo) {
        this.pmntRepo = pmntRepo;
    }

    public MerchantRepository(DBConnection connectionToDB, PaymentRepository PaymnetRepo) {
        this.connectionToDB = connectionToDB;
        this.pmntRepo = PaymnetRepo;
    }

    public Merchant getMerchantById(int merchId)  {

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

            List<Payment> pmntList = pmntRepo.getPaymentByMerchant(newMerch);
            newMerch.setPayments(pmntList);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return newMerch;
    }

    public List<Merchant> getMerchantList() {

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

                List<Payment> pmntList = pmntRepo.getPaymentByMerchant(newMerch);
                newMerch.setPayments(pmntList);

                merchantList.add(newMerch);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return merchantList;
    }

    public void updateMerchant(Merchant merch, double sum, double charge) {
        try {
            Connection con = connectionToDB.getConnection();
            String sqlm = "UPDATE merchant set charge = ?, needToSend = ? where id = ? ";
            PreparedStatement stmt1 = con.prepareStatement(sqlm);
            stmt1.setInt(3, merch.getId());
            stmt1.setDouble(1, charge);
            stmt1.setDouble(2, sum);
            stmt1.executeUpdate();
            stmt1.close();
        }catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 2020-03-16 change to try with resources 
    public void sendFunds(Merchant merch) {
        String sqlm = "UPDATE merchant set charge = ?, needToSend = ?, lastSent = ? , sent = ? where id = ? ";
        try (Connection con = connectionToDB.getConnection();
            PreparedStatement stmt1 = con.prepareStatement(sqlm)){

            stmt1.setInt(5, merch.getId());
            stmt1.setDouble(1, 0);
            stmt1.setDouble(2, 0);
            stmt1.setDate(3, Date.valueOf(LocalDate.now()));
            stmt1.setDouble(4, merch.getNeedToSend()-merch.getCharge());
            stmt1.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }



}
