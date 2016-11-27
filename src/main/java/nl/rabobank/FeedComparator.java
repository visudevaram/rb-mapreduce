package nl.rabobank;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by JayaramanJ on 25-11-2016.
 */
public class FeedComparator implements Comparator<Feed> {

     public int compare(Feed f1, Feed f2) {

        int result = f1.getBankName().compareTo(f2.getBankName());
        if (result != 0)
        {
            return result;
        }
        result = f1.getFeetDate().compareTo(f2.getFeetDate());
        if (result != 0)
        {
            return result;
        }
        return result;
        // f1.getFeetDate().compareTo(f2.getFeetDate());
    }
}
