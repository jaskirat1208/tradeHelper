package platform.oms;

import platform.mktdataread.FeedReceiver;
import utils.NiftyIndices;
import utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PnlCalculator {
    private FeedReceiver mFeedReceiver;
    private HashMap<String, InstrumentPosition> mInstrumentWisePositionHashMap = new HashMap<>();


    public void registerPriceFetcher(FeedReceiver feedReceiver) {
        mFeedReceiver = feedReceiver;
    }

    /**
     * Add instrument for Pnl Calculation
     * 1 => Buy
     * 2 => Sell
     *
     * @param symbol  => Symbol to add in Pnl Calculator
     * @param side =>
     */
    public void addInstrument(String symbol, int side) {
        InstrumentPosition instrumentPosition = mInstrumentWisePositionHashMap.get(symbol);
        if(instrumentPosition == null) {
            instrumentPosition = new InstrumentPosition();
            mInstrumentWisePositionHashMap.put(symbol, instrumentPosition);
        }
        double ltp = StringUtils.parseDoubleWCommasRemoved(mFeedReceiver.getLastFeedInfo(symbol).getLast());
        instrumentPosition.addInstrument(ltp, 1, side);
    }

    public double getTotalExpectedPnl() {
        double pnl = 0;
        for (Map.Entry<String, InstrumentPosition> entry:
             mInstrumentWisePositionHashMap.entrySet()) {
            String instrument = entry.getKey();
            NiftyIndices feedInfo = mFeedReceiver.getLastFeedInfo(instrument);
            if(feedInfo == null)
                continue;
            double ltp = StringUtils.parseDoubleWCommasRemoved(feedInfo.getLast());
            pnl += entry.getValue().getNetPnl(ltp);
        }
        return pnl;
    }

}
