package Repositories;

import Entities.Customer;
import DBConnections.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    DBConnection connectionToDB;

    public CustomerRepository(DBConnection connectionToDB) {
        this.connectionToDB = connectionToDB;
    }

    public Customer getById(int custId) {

        Customer cust = null;
        try (Connection con = connectionToDB.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet resSet = stmt.executeQuery(

                    "select * from customer where id = " + custId);
            while (resSet.next()) {
                Integer id = resSet.getInt("id");
                String nm = resSet.getString("name");
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
                String nm = resSet.getString("name");
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
}
