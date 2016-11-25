package nl.rabobank;

import java.util.Comparator;

/**
 * Created by JayaramanJ on 25-11-2016.
 */
public class DateComparator implements Comparator<Feed> {

    public int compare(Feed f1, Feed f2) {
        return f1.getFeetDate().compareTo(f2.getFeetDate());
    }
}
