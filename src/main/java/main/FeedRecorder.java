package main;

import platform.mktdataread.NseIndexPriceFetcher;
import utils.RequestHelper;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FeedRecorder extends TimerTask {
    static final String API_URL = "https://www1.nseindia.com/live_market/dynaContent/live_watch/stock_watch/liveIndexWatchData.json";
    static final String FILE_PATH = "D:\\tradingData\\feedInfo\\";

    private FileWriter mFileWriter;
    private String mPrevJsonIndexData = "";
    NseIndexPriceFetcher priceFetcher = new NseIndexPriceFetcher();
    Timer mFeedTimer = new Timer();

    public void init() {
        try {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String filename = String.format("trades.%s.json", format.format(date));
            mFileWriter = new FileWriter(FILE_PATH + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        mFeedTimer.schedule(this, 1000, 1000);
    }

    public void getMarketDataFeedString() {
        RequestHelper helper = new RequestHelper();
        String jsonIndexData = helper.CommonHttpDataFetcher(API_URL);
        if(mPrevJsonIndexData.isEmpty() || ! jsonIndexData.equals(mPrevJsonIndexData)) {
            mPrevJsonIndexData = jsonIndexData;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                mFileWriter.write(String.format("{\"Time\":\"%s\",\"FeedInfo\":%s}\n", formatter.format(new Date()), jsonIndexData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    @Override
    public void run() {
        if(canStop()) {
            this.cancel();
            mFeedTimer.cancel();
            System.out.println("Stopping the recording");
        }
        try {
            getMarketDataFeedString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean canStop() {
        LocalTime nowTime = LocalTime.now();
        LocalTime stopTime = LocalTime.of(15, 30);
        LocalTime startTime = LocalTime.of(8, 0);

        return nowTime.isAfter(stopTime) || nowTime.isBefore(startTime);
    }

    public static void main(String[] args) {
        FeedRecorder feedRecorder = new FeedRecorder();

        feedRecorder.init();
        feedRecorder.start();
    }
}
