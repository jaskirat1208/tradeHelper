package platform.oms;

import platform.mktdataread.FeedReceiver;
import utils.NiftyIndices;

public class OMService {
    private static OMService omService = new OMService();
    private PnlCalculator pnlCalculator = new PnlCalculator();

    private FeedReceiver mFeedFetcher;
    private OMService() {

    }

    public void registerPriceFetcher(FeedReceiver priceFetcher) {
        mFeedFetcher = priceFetcher;
        pnlCalculator.registerPriceFetcher(priceFetcher);
    }
    public static OMService getInstance() {
        return omService;
    }

    public void Buy(String index) {
        NiftyIndices feedInfo = mFeedFetcher.getLastFeedInfo(index);
        pnlCalculator.addInstrument(index, 1);
        System.out.printf("B, %s, %s, %s, %s%n",
                index, feedInfo.getLast(), feedInfo.getTimeVal(), pnlCalculator.getTotalExpectedPnl());
    }

    public void Sell(String index) {
        NiftyIndices feedInfo = mFeedFetcher.getLastFeedInfo(index);
        pnlCalculator.addInstrument(index, 2);
        System.out.printf("S, %s, %s, %s %s%n",
                index, feedInfo.getLast(), feedInfo.getTimeVal(), pnlCalculator.getTotalExpectedPnl());
    }
}
