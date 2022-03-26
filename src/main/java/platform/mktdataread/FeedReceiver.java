package platform.mktdataread;

import utils.NiftyIndices;
import utils.NiftyIndicesList;

public abstract class FeedReceiver {
    public FeedReceiver() {}

    public boolean newFeed() { return true; }
    public boolean feedsCompleted() { return false; }
    public abstract NiftyIndices getLastFeedInfo(String instrument);

    public abstract NiftyIndicesList getLastFeedSnapshot();
}
