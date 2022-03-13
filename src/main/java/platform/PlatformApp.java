package platform;

import strategy.BaseStrategy;
import utils.NiftyIndices;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PlatformApp {
    BaseStrategy mStrategy;
    NseIndexPriceFetcher mFeedFetcher = new NseIndexPriceFetcher();

    public void init() {
        Thread t = new Thread(mFeedFetcher);
        t.start();
    }

    public void start() {

        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
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
