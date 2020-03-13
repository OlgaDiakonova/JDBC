import java.util.Comparator;
import Entities.Merchant;

public class SortByName implements Comparator<Merchant> {

    @Override
    public int compare(Merchant o1, Merchant o2) {
        return o2.getName().compareTo(o1.getName());
    }
}
