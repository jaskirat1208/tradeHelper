package platform.mktdataread;

import com.google.gson.Gson;
import utils.NiftyIndices;
import utils.NiftyIndicesList;
import utils.RequestHelper;

import java.io.IOException;
import java.util.HashMap;

public class NseIndexPriceFetcher extends FeedReceiver {
    static final String API_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/liveIndexWatchData.json";

    private HashMap<String, NiftyIndices> mIndicesHashMap = new HashMap<>();

    private boolean mNewFeedReceived = false;
    private NiftyIndicesList mLastFeedInfo = null;

    protected synchronized NiftyIndicesList getIndexPricesFromAPI() {
        System.out.println("Trying to fetch prices from exchange:");

        RequestHelper helper = new RequestHelper();
        String jsonIndexData = helper.CommonHttpDataFetcher(API_URL);

        NiftyIndicesList feedInfo = new Gson().fromJson(jsonIndexData, NiftyIndicesList.class);
        mLastFeedInfo = feedInfo;
        return feedInfo;
    }

    public NseIndexPriceFetcher() {
    }

    @Override
    public NiftyIndices getLastFeedInfo(String index) {
        return mIndicesHashMap.get(index);
    }

    @Override
    public NiftyIndicesList getLastFeedSnapshot() {
        return null;
    }

    public boolean newFeed() {
        /* Mark feed as stale */
        mNewFeedReceived = false;
        try {
            NiftyIndicesList indicesList = getIndexPricesFromAPI();

            if(indicesList == null) {
                return mNewFeedReceived;
            }

            for(NiftyIndices indexInfo: indicesList.getData()) {
                String instrumentName = indexInfo.getIndexName();

                NiftyIndices prevIndexInfo = mIndicesHashMap.get(instrumentName);
                mIndicesHashMap.put(instrumentName, indexInfo);

                if(prevIndexInfo == null || !indexInfo.getLast().equals(prevIndexInfo.getLast()))
                    mNewFeedReceived = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mNewFeedReceived;
    }
}
