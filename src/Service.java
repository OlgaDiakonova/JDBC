import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Service {

    public static Connection getConnection() throws IOException, SQLException {
        Connection conn;
        Properties props = new Properties();
        try (BufferedReader in = Files.newBufferedReader(Paths.get("app.properties"), Charset.forName("UTF-8"))) {
            props.load(in);
        }
        String dbUrl = props.getProperty("dbUrlAddress");
        String userName = props.getProperty("userName");
        String pass = props.getProperty("password");
        conn = DriverManager.getConnection(dbUrl, userName, pass);
        return conn;

    }

    public static List<Merchant> createMerchant(ResultSet resSet) throws SQLException {
        List<Merchant> merchList = new ArrayList<>();

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

            Merchant newMerch = new Merchant( id,  nm,  bankName,  swift,  account,  charge,  period,  minSum,  needToSend,  sent,  lastSent);

            merchList.add(newMerch);
        }

        return merchList;
    }

    public static Merchant getMerchantById(int merchId) throws SQLException {

        Merchant newMerch = null;
        try (Connection con = getConnection();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newMerch;
    }

    public static Customer getCustomerById(int custId) throws SQLException {

        Customer cust = null;
        try (Connection con = getConnection();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cust;
    }

        public static void createPayments(ResultSet resPmnt, Merchant merch) throws SQLException {
        List<Payment> pmntList = new ArrayList<>();

        while (resPmnt.next()) {
            Integer id = resPmnt.getInt("id");
            LocalDate dt = resPmnt.getDate("dt").toLocalDate();
            String goods = resPmnt.getString("goods");
            Double sumPaid = resPmnt.getDouble("sumPaid");
            Double chargePaid = resPmnt.getDouble("chargePaid");

            Payment newPayment = new Payment(id, dt, merch, null, goods, sumPaid, chargePaid);

            pmntList.add(newPayment);
        }

        merch.setPayments(pmntList);
    }
}
