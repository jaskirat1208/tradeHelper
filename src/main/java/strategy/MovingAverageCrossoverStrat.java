package strategy;

import ds.MovingAverageQueue;
import platform.NseIndexPriceFetcher;

public class MovingAverageCrossoverStrat extends BaseStrategy {
    static String INDEX = "NIFTY 50";
    static int SLOW_WINDOW_SIZE = 100;
    static int FAST_WINDOW_SIZE = 10;

    private MovingAverageQueue mSlowMAQueue = new MovingAverageQueue(SLOW_WINDOW_SIZE);
    private MovingAverageQueue mFastMAQueue = new MovingAverageQueue(FAST_WINDOW_SIZE);

    /**
     * 0 => Both are equal
     * 1 => Slow MA > Fast MA
     * 2 => Slow MA < Fast MA
     */
    private short lastMAComparisonResult = 0;


    @Override
    public void init() {

    }


    @Override
    public void onMarketUpdate() {
        double price = mFeedFetcher.getLastPrice(INDEX);
        mSlowMAQueue.push(price);
        mFastMAQueue.push(price);

        double slowMA = mSlowMAQueue.getMovingAverage();
        double fastMA = mFastMAQueue.getMovingAverage();

        if(slowMA > fastMA) {
            if(lastMAComparisonResult == 2) {
                System.out.println("Buy Signal Triggered");
            }
            lastMAComparisonResult = 1;
        }
        else if(slowMA < fastMA) {
            if(lastMAComparisonResult == 1) {
                System.out.println("Sell Signal Triggered");
            }
            lastMAComparisonResult = 2;
        }

    }
}
