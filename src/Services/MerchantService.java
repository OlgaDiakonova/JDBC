package Services;

import Entities.Merchant;
import Entities.Payment;
import Repositories.MerchantRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MerchantService {

    MerchantRepository merchRepository;

    public MerchantService(MerchantRepository merchRepository) {
        this.merchRepository = merchRepository;
    }

    public Merchant getMerchantById(int id) throws SQLException {

        return merchRepository.getMerchantById(id);
    }

    public Merchant getMerchantByName(String name) throws SQLException {

        return merchRepository.getMerchantByName(name);
    }

    public List<Merchant> getMerchantList() {

        return merchRepository.getMerchantList();
    }

    public List<Merchant> getSortedMerchantList() {

        List<Merchant> merchList = new ArrayList<>();
        merchList = getMerchantList();

        Collections.sort(merchList, (o1, o2) -> o2.getName().compareTo(o1.getName()));

        return merchList;

    }

    public double getTotalMerchantPaid(Merchant newMerch) {
        double sum = 0;
        for (Payment item: newMerch.getPayments()) {
            sum += item.getSumPaid();
        };
        return sum;
    }

    public void updateMerchant(Merchant merch, double sum, double charge){
        merchRepository.updateMerchant(merch, sum, charge);
        merch.setNeedToSend(merch.getNeedToSend() + sum);
        merch.setCharge(merch.getCharge() + charge);

    }

    public void sendFunds(Merchant merch){
        if (merch.getNeedToSend() > merch.getMinSum()) {
            merchRepository.sendFunds(merch);
        }else {
            System.out.println("Total sum is less than minimum need to send sum! Money won't be sent to merchant!");
        }
    }

    public boolean ifUnique(String mName){

        Merchant merch = merchRepository.getMerchantByName(mName);
        if(merch == null){
            return true;
        }else{
            return false;
        }

    }

    public void loadMerchantToDB(Merchant merch){
        merchRepository.loadMerchantToDB(merch);
    }

}
