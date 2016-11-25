package nl.rabobank;

import java.util.Date;

/**
 * Created by JayaramanJ on 25-11-2016.
 */
public class Feed {

    private final Date feetDate;

    private final String feedBy;

    private final String location;

    private final String feed;

    public Feed(Date feetDate, String feedBy, String location, String feed) {
        this.feetDate = feetDate;
        this.feedBy = feedBy;
        this.location = location;
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

    public String getLocation() {
        return location;
    }
}
