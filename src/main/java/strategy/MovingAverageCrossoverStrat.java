package strategy;

import ds.MovingAverageQueue;
import platform.OMService;

public class MovingAverageCrossoverStrat extends BaseStrategy {
    static String INDEX = "NIFTY 50";
    static int SLOW_WINDOW_SIZE = 100;
    static int FAST_WINDOW_SIZE = 10;

    private MovingAverageQueue mSlowMAQueue = new MovingAverageQueue(SLOW_WINDOW_SIZE);
    private MovingAverageQueue mFastMAQueue = new MovingAverageQueue(FAST_WINDOW_SIZE);
    private OMService mOMService = OMService.getInstance();

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

        System.out.printf("Slow MA: %f, Fast MA: %f%n", slowMA, fastMA);

        if(fastMA > slowMA) {
            if(lastMAComparisonResult == 1) {
                mOMService.Buy(INDEX);
            }
            lastMAComparisonResult = 2;
        }
        else if(slowMA > fastMA) {
            if(lastMAComparisonResult == 2) {
                mOMService.Sell(INDEX);
            }
            lastMAComparisonResult = 1;
        }

    }
}
