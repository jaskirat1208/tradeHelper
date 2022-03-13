package platform;

import com.google.gson.Gson;
import utils.NiftyIndices;
import utils.NiftyIndicesList;
import utils.RequestHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class NseIndexPriceFetcher extends TimerTask {
    static final String API_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/liveIndexWatchData.json";

    private HashMap<String, NiftyIndices> mIndicesHashMap = new HashMap<>();

    private int mExecutionCount = 0;

    private synchronized void getIndexPricesFromAPI() throws IOException, InterruptedException {
        RequestHelper helper = new RequestHelper();
        String jsonIndexData = helper.CommonHttpDataFetcher(API_URL);

        NiftyIndicesList indicesList = new Gson().fromJson(jsonIndexData, NiftyIndicesList.class);
        for(NiftyIndices indexInfo: indicesList.getData()) {
            String instrumentName = indexInfo.getIndexName();
            mIndicesHashMap.put(instrumentName, indexInfo);
        }
        mExecutionCount++;
    }

    public NseIndexPriceFetcher() {
        this.run();
        Timer timer = new Timer();
        timer.schedule(this, 1000, 5000);
    }

    public double getLastPrice(String index) {
        NiftyIndices indexInfo = mIndicesHashMap.get(index);
        if(indexInfo == null)
            return 0;

        String lastPriceString = indexInfo.getLast();
        System.out.println("Getting price from API Call: " + this.mExecutionCount);
        return Double.parseDouble(lastPriceString.replace(",", ""));
    }


    @Override
    public void run() {
        try {
            System.out.println("Trying to fetch prices from exchange:");
            getIndexPricesFromAPI();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
