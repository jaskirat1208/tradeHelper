package strategy;

import platform.mktdataread.FeedReceiver;


public abstract class BaseStrategy {
    protected FeedReceiver mFeedFetcher;

    public BaseStrategy() {}

    public abstract void init();

    public void registerFeedFetcher(FeedReceiver feedFetcher) {
        mFeedFetcher = feedFetcher;
    }

    public abstract void onMarketUpdate();
}
