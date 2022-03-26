package strategy;

import com.google.gson.Gson;
import utils.NiftyIndicesList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArchiverStrat extends BaseStrategy {

    @Override
    public void init() {

    }

    @Override
    public void onMarketUpdate() {
        NiftyIndicesList feedInfo = mFeedFetcher.getLastFeedSnapshot();
        System.out.println();
        String jsonIndexData = new Gson().toJson(feedInfo);
        SimpleDateFormat formatter = new SimpleDateFormat("\"dd/MM/yyyy HH:mm:ss\"");
        String archiveFormatString = String.format("{\"Time\":\"%s\",\"FeedInfo\":%s}",
                formatter.format(new Date()), jsonIndexData);
        System.out.print(archiveFormatString);
    }
}
