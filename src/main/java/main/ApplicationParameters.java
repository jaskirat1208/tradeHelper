package main;

import utils.ConfigFileReader;
import utils.StringUtils;

import java.io.IOException;
import java.util.Date;

public class ApplicationParameters {
    enum TradingMode {
        LIVE,
        SIM,
        RECORD
    }

    TradingMode mode;
    String strategy;
    String date;
    String logFolder;
    String inputFileFolder;

    private static ApplicationParameters params = new ApplicationParameters();

    public static ApplicationParameters getInstance() {
        return params;
    }

    public void LoadParams(String filename) throws IOException {
        ConfigFileReader configFileReader = ConfigFileReader.getInstance();
        configFileReader.loadConfigFile(filename);

        String modeString = configFileReader.getValue("MODE", "LIVE");
        mode = TradingMode.valueOf(modeString);
        date = configFileReader.getValue("DATE", StringUtils.YYYYMMDD(new Date()));
        logFolder = configFileReader.getValue("LOG_FOLDER", "D:\\tradingData\\logs\\");
        inputFileFolder = configFileReader.getValue("INPUT_FILE_FOLDER", "D:\\tradingData\\feedInfo\\");
    }
}
