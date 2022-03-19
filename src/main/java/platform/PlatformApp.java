package platform;

import strategy.BaseStrategy;

import java.util.Timer;
import java.util.TimerTask;

public class PlatformApp {
    BaseStrategy mStrategy;
    NseIndexPriceFetcher mFeedFetcher = new NseIndexPriceFetcher();
    OMService mOMService = OMService.getInstance();

    public void init() {
        Thread t = new Thread(mFeedFetcher);
        mOMService.registerPriceFetcher(mFeedFetcher);
        t.start();
    }

    public void start() {

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                if(mFeedFetcher.newFeed())
                    mStrategy.onMarketUpdate();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timertask, 10, 1000);

    }

    public  void registerStrategy(BaseStrategy strat) {
        mStrategy = strat;
        strat.registerFeedFetcher(mFeedFetcher);
    }

}
