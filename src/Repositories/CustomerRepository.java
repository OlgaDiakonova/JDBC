package Repositories;

import Entities.Customer;
import DBConnections.DBConnection;
import Entities.Payment;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CustomerRepository {

    DBConnection connectionToDB;
    PaymentRepository pmntRepo;

    public CustomerRepository(DBConnection connectionToDB,  PaymentRepository pmntRepo) {
        this.connectionToDB = connectionToDB;
        this.pmntRepo = pmntRepo;
    }

    public Customer getById(int custId) {

        Customer cust = null;
        try (Connection con = connectionToDB.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resSet = stmt.executeQuery(

                    "select * from customer where id = " + custId);
            while (resSet.next()) {
                Integer id = resSet.getInt("id");
                String nm = resSet.getString("cName");
                String address = resSet.getString("address");
                String email = resSet.getString("email");
                String ccNo = resSet.getString("ccNo");
                String ccType = resSet.getString("ccType");
                LocalDate maturity = resSet.getDate("maturity").toLocalDate();

                cust = new Customer(id, nm, address,email,ccNo,ccType,maturity);

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cust;
    }

    public Customer getByName(String custName) {

        Customer cust = null;
        String query = "select * from customer where cName = ?";
        try (Connection con = connectionToDB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, custName);
            ResultSet resSet = ps.executeQuery();
            while (resSet.next()) {
                Integer id = resSet.getInt("id");
                String nm = resSet.getString("cName");
                String address = resSet.getString("address");
                String email = resSet.getString("email");
                String ccNo = resSet.getString("ccNo");
                String ccType = resSet.getString("ccType");
                LocalDate maturity = resSet.getDate("maturity").toLocalDate();

                cust = new Customer(id, nm, address,email,ccNo,ccType,maturity);

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return cust;
    }

    public List<Customer> getCustomerList() {

        List<Customer> custList = new ArrayList();
        Customer cust = null;
        try (Connection con = connectionToDB.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resSet = stmt.executeQuery(

                    "select * from customer");
            while (resSet.next()) {
                Integer id = resSet.getInt("id");
                String nm = resSet.getString("cName");
                String address = resSet.getString("address");
                String email = resSet.getString("email");
                String ccNo = resSet.getString("ccNo");
                String ccType = resSet.getString("ccType");
                LocalDate maturity = resSet.getDate("maturity").toLocalDate();

                cust = new Customer(id, nm, address,email,ccNo,ccType,maturity);
                custList.add(cust);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return custList;
    }

    public void loadCustomersToDB(List<Customer> custList) {

        String sql = "INSERT INTO customer (id, cName, ";
        sql += " address, email, ccNo, ccType, maturity) values(?,?,?,?,?,?,?) ";

        try (Connection con = connectionToDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            for (Customer item : custList) {
                stmt.setInt(1, item.getId());
                stmt.setString(2, item.getName());
                stmt.setString(3, item.getAddress());
                stmt.setString(4, item.getEmail());
                stmt.setString(5, item.getCcNo());
                stmt.setString(6, item.getCcType());
                stmt.setDate(7, java.sql.Date.valueOf(item.getMaturity()));

                stmt.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomersByPeriod(Timestamp startDt, Timestamp endDt){
        List<Customer> cashCust = new ArrayList<>();
        List<Payment> pmntList = pmntRepo.getPaymentsByPeriod(startDt, endDt);
        for (Payment item: pmntList) {
            if(cashCust.contains(item.getCust())){
                Customer cust = cashCust.get(cashCust.indexOf(item.getCust()));
                cust.setPayment(item);
            }else{
                Customer cust = item.getCust();
                cust.setPayment(item);
                cashCust.add(cust);
            }

        }

        return cashCust;

    }
}
