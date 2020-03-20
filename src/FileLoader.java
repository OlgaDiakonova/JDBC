import Entities.Customer;
import Entities.Merchant;
import Entities.Payment;
import Services.CustomerService;
import Services.MerchantService;
import Services.PaymentService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class FileLoader {

    private MerchantService merchService;
    private PaymentService pmntService;
    private CustomerService custService;

    public FileLoader(MerchantService merchService, PaymentService pmntService, CustomerService custService) {
        this.merchService = merchService;
        this.pmntService = pmntService;
        this.custService = custService;
    }

    public PaymentService getPmntService() {
        return pmntService;
    }

    public void setPmntService(PaymentService pmntService) {
        this.pmntService = pmntService;
    }

    public CustomerService getCustService() {
        return custService;
    }

    public void setCustService(CustomerService custService) {
        this.custService = custService;
    }

    public MerchantService getMerchService() {
        return merchService;
    }

    public void setMerchService(MerchantService merchService) {
        this.merchService = merchService;
    }

    public void createMerchantsFromFile(String csvFile) {
        Set<Merchant> merchList = getMerchantData(csvFile);
        for (Merchant item : merchList) {
            if(merchService.ifUnique(item.getName())) {
                merchService.loadMerchantToDB(item);
            }
        }
    }

    public void createPaymentsFromFile(String csvFile) {
        Set<Payment> pmntSet = getPaymentData(csvFile);
        List<Payment> pmntList = new ArrayList<>(pmntSet);
        pmntService.loadPaymentToDB(pmntList);
    }

    public void createCustomersFromFile(String csvFile) {
        List<Customer> custSet = new ArrayList<>(getCustomerData(csvFile));
        List<Customer> custList = custService.getCustomerList();
        custSet.removeAll(custList);
        custService.loadCustomersToDB(custSet);
    }

    public HashSet<Customer> getCustomerData(String csvFile) {
        String line = "";
        String cvsSplitBy = ",";
        String[] dataString = null;
        HashSet<Customer> custSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            br.readLine();
            while ((line = br.readLine()) != null) {
                dataString = line.split(cvsSplitBy);
                Customer newCustomer = new Customer(0, dataString[0], dataString[1], dataString[2], dataString[3],
                        dataString[4], java.sql.Date.valueOf(dataString[5]).toLocalDate());

                custSet.add(newCustomer);
                System.out.println("Customer [name= " + dataString[0] + " , address =" + dataString[1] + "]");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return custSet;
    }

    public HashSet<Payment> getPaymentData(String csvFile) {
        String line = "";
        String cvsSplitBy = ",";
        String[] dataString = null;
        HashSet<Payment> paymentSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            br.readLine();
            while ((line = br.readLine()) != null) {
                dataString = line.split(cvsSplitBy);
                Merchant merch = merchService.getMerchantByName(dataString[2]);
                Customer cust = custService.getByName(dataString[1]);
                Payment newPayment = new Payment(0, java.sql.Date.valueOf(dataString[0]).toLocalDate(),
                        merch, cust, dataString[3], Double.valueOf(dataString[4]), 0.00);

                paymentSet.add(newPayment);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return paymentSet;
    }


    public HashSet<Merchant> getMerchantData(String csvFile) {
        String line = "";
        String cvsSplitBy = ",";
        String[] dataString = null;
        HashSet<Merchant> setMerchant = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            br.readLine();
            while ((line = br.readLine()) != null) {
                dataString = line.split(cvsSplitBy);
                Merchant newMerch = new Merchant(0, dataString[0], dataString[1], dataString[2], dataString[3], Double.parseDouble(dataString[4]),
                        Integer.valueOf(dataString[5]), Double.valueOf(dataString[6]), Double.valueOf(dataString[7]),
                        Double.valueOf(dataString[8]), null);

                setMerchant.add(newMerch);
                System.out.println("Merchant [name= " + dataString[0] + " , bank name=" + dataString[1] + "]");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return setMerchant;
    }

}
