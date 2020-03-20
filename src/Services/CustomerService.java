package Services;

import Entities.Customer;
import Repositories.CustomerRepository;

import java.util.List;

public class CustomerService {

    CustomerRepository custRepo;

    public CustomerRepository getCustRepo() {
        return custRepo;
    }

    public void setCustRepo(CustomerRepository custRepo) {
        this.custRepo = custRepo;
    }

    public CustomerService(CustomerRepository custRepo) {
        this.custRepo = custRepo;
    }

    public Customer getById(int id){
        return custRepo.getById(id);
    }

    public Customer getByName(String name){
        return custRepo.getByName(name);
    }

    public List<Customer> getCustomerList(){
        return custRepo.getCustomerList();
    }

    public void loadCustomersToDB(List<Customer> custList){
        custRepo.loadCustomersToDB(custList);
    }


}
