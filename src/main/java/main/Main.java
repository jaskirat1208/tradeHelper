package main;

import platform.NseIndexPriceFetcher;
import platform.PlatformApp;
import strategy.BaseStrategy;
import strategy.MovingAverageCrossoverStrat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    static class CustomStrat extends BaseStrategy {
        @Override
        public void init() {
        }


        @Override
        public void onMarketUpdate() {
            System.out.println(mFeedFetcher.getLastPrice("NIFTY 50"));
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String fileName = String.format("D:\\tradingData\\stratLogs.%s.txt", format.format(date));
        File file = new File(fileName);
        PrintStream stream = null;
        try {
            stream = new PrintStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.setOut(stream);
        PlatformApp app = new PlatformApp();
        app.init();

        BaseStrategy strat = new MovingAverageCrossoverStrat();
        app.registerStrategy(strat);

        app.start();
    }

}
