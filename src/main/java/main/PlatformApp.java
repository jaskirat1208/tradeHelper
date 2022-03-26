package main;

import platform.mktdataread.FeedReceiver;
import platform.oms.OMService;
import strategy.BaseStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class PlatformApp {
    BaseStrategy mStrategy;
    FeedReceiver mFeedFetcher;
    OMService mOMService = OMService.getInstance();

    public void init() {
    }

    public void registerPriceFetcher(FeedReceiver feedFetcher) {
        mFeedFetcher = feedFetcher;
        mOMService.registerPriceFetcher(mFeedFetcher);
    }
    
    public void start() {
        Timer timer = new Timer();

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                if(canStop()) {
                    this.cancel();
                    timer.cancel();
                }

                if(mFeedFetcher.newFeed())
                    mStrategy.onMarketUpdate();
            }
        };
        timer.schedule(timertask, 10, 1);

    }

    private boolean canStop() {
        return mFeedFetcher.feedsCompleted();
    }

    public void registerStrategy(BaseStrategy strat) {
        mStrategy = strat;
        strat.registerFeedFetcher(mFeedFetcher);
    }

}
