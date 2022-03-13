package strategy;

import platform.NseIndexPriceFetcher;


public abstract class BaseStrategy {
    protected NseIndexPriceFetcher mFeedFetcher;

    public BaseStrategy() {}

    public abstract void init();

    public void registerFeedFetcher(NseIndexPriceFetcher feedFetcher) {
        mFeedFetcher = feedFetcher;
    }

    public abstract void onMarketUpdate();
}
