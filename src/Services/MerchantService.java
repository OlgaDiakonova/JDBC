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

    public Merchant addMerchantToDB(MerchantRepository mR, int id) throws SQLException {

        return mR.getMerchantById(id);
    }

    public void reportSortedMerchantList() {

        try{
            List<Merchant> merchList = merchRepository.getMerchantList();

            Collections.sort(merchList, new Comparator<Merchant>() {
                @Override
                public int compare(Merchant o1, Merchant o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });

            for (Merchant item : merchList) {

                System.out.println(item.toString());
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }

    }

    public void sumReport(int merch) {

        try {
            Merchant newMerch = merchRepository.getMerchantById(merch);
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
