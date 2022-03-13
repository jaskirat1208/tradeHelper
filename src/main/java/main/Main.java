package main;

import platform.NseIndexPriceFetcher;
import platform.PlatformApp;
import strategy.BaseStrategy;
import strategy.MovingAverageCrossoverStrat;

public class Main {

    static class CustomStrat extends BaseStrategy {
        @Override
        public void init() {
        }


        @Override
        public void onMarketUpdate() {
            System.out.println(mFeedFetcher.getLastPrice("NIFTY 50"));
        }
    }

    public static void main(String[] args) {
        PlatformApp app = new PlatformApp();
        app.init();

        BaseStrategy strat = new MovingAverageCrossoverStrat();
        app.registerStrategy(strat);

        app.start();
    }

}
