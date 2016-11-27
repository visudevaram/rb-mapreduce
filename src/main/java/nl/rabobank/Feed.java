package nl.rabobank;

import java.util.Date;

/**
 * Created by JayaramanJ on 25-11-2016.
 */
public class Feed {
    
    private final String bankName;

    private final Date feetDate;

    private final String feedBy;


    private final String feed;

    public Feed(String bankName, Date feetDate, String feedBy, String feed) {
        this.bankName = bankName;
        this.feetDate = feetDate;
        this.feedBy = feedBy;
        this.feed = feed;
    }

    public Date getFeetDate() {
        return feetDate;
    }

    public String getFeedBy() {
        return feedBy;
    }


    public String getFeed() {
        return feed;
    }

    public String getBankName() {
        return bankName;
    }

}
