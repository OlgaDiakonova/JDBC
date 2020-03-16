package Services;

import Entities.Merchant;
import Entities.Payment;
import Repositories.MerchantRepository;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MerchantService {

    MerchantRepository merchRepository;

    public MerchantService(MerchantRepository merchRepository) {
        this.merchRepository = merchRepository;
    }

    public Merchant getMerchantById(int id) throws SQLException {

        return merchRepository.getMerchantById(id);
    }

    public List<Merchant> getMerchantList() throws SQLException {

        return merchRepository.getMerchantList();
    }

    // TODO: 2020-03-12 return something from each method with void. Create methods to get everything from repos

    public void reportSortedMerchantList() {

        try{
            List<Merchant> merchList = getMerchantList();

            Collections.sort(merchList, (o1, o2) -> o2.getName().compareTo(o1.getName()));

            for (Merchant item : merchList) {

                System.out.println(item.toString());
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }

    }

    public void sumReport(int merch) {

        try {
            Merchant newMerch = getMerchantById(merch);
            displayReport(newMerch);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void displayReport(Merchant merch) {
        double sum = 0;
        for (Payment pmnts : merch.getPayments()) {
            sum += pmnts.getSumPaid();
        }

        System.out.println("Merch id: " + merch.getId() + ", merch name: " + merch.getName() +
                ", last sent date: " + merch.getLastSent() + ", total sum: " + sum);
    }
}
