package main;

import utils.RequestHelper;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FeedRecorder extends TimerTask {
    static final String API_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/liveIndexWatchData.json";
    static final String FILE_PATH = "D:\\tradingData\\";

    private FileWriter mFileWriter;

    public void init() {
        try {
            Date date = new Date();
            String filename = String.format("trades.%s.json", date);
            mFileWriter = new FileWriter(FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(this, 1000, 1000);
    }

    public void getMarketDataFeedString() {
        RequestHelper helper = new RequestHelper();
        String jsonIndexData = helper.CommonHttpDataFetcher(API_URL);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            mFileWriter.write(String.format("{\"Time\":\"%s\",\"FeedInfo\":%s}\n", formatter.format(new Date()), jsonIndexData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        getMarketDataFeedString();
    }

    public static void main(String[] args) {
        FeedRecorder feedRecorder = new FeedRecorder();

        feedRecorder.init();
        feedRecorder.start();
    }
}
