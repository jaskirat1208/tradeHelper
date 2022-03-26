package strategy;

import ds.MovingAverageQueue;
import platform.oms.OMService;
import utils.NiftyIndices;
import utils.StringUtils;

public class MovingAverageCrossoverStrat extends BaseStrategy {
    static String INDEX = "NIFTY 50";
    static int SLOW_WINDOW_SIZE = 50;
    static int FAST_WINDOW_SIZE = 15;

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
        NiftyIndices feedInfo = mFeedFetcher.getLastFeedInfo(INDEX);
        double price = StringUtils.parseDoubleWCommasRemoved(feedInfo.getLast());

        mSlowMAQueue.push(price);
        mFastMAQueue.push(price);

        double slowMA = mSlowMAQueue.getMovingAverage();
        double fastMA = mFastMAQueue.getMovingAverage();
        double slowSlope = mSlowMAQueue.getSlope();
        double fastSlope = mFastMAQueue.getSlope();

//        System.out.printf("Slow MA: %f, Fast MA: %f, Slow Slope: %f, Fast slope: %s\n",
//                slowMA, fastMA, slowSlope, fastSlope);

        if(fastSlope > slowSlope && fastMA > slowMA) {
            if(lastMAComparisonResult == 1) {
                mOMService.Buy(INDEX);
            }
            lastMAComparisonResult = 2;
        }
        else if(slowSlope > fastSlope && slowMA > fastMA) {
            if(lastMAComparisonResult == 2) {
                mOMService.Sell(INDEX);
            }
            lastMAComparisonResult = 1;
        }

    }
}
