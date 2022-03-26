package platform.mktdataread;

import com.google.gson.Gson;
import utils.FeedInfoWithTimestamp;
import utils.NiftyIndicesList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FeedReceiverFromFile extends NseIndexPriceFetcher {
    static String mFileName = "";
    static String mPrevLine = "";
    private Scanner mFileScanner;
    private NiftyIndicesList mLastFeedSnapshot;

    public FeedReceiverFromFile(String filename) {
        mFileName = filename;
        try {
            mFileScanner = new Scanner(new FileInputStream(mFileName));
        } catch (FileNotFoundException e) {
            System.err.printf("File %s not found\n", mFileName);
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized NiftyIndicesList getIndexPricesFromAPI() {
        if(! mFileScanner.hasNext())
            return null;

        String line = mFileScanner.nextLine();

        if( mPrevLine.isEmpty() || ! line.substring(31).equals(mPrevLine.substring(31))) {
            FeedInfoWithTimestamp feedInfoWithTimestamp = new Gson().fromJson(line, FeedInfoWithTimestamp.class);
            mPrevLine = line;
            mLastFeedSnapshot = feedInfoWithTimestamp.getFeedInfo();
            return feedInfoWithTimestamp.getFeedInfo();
        }
        return null;
    }

    @Override
    public boolean feedsCompleted() {
        return ! mFileScanner.hasNext();
    }

    @Override
    public NiftyIndicesList getLastFeedSnapshot() {
        return mLastFeedSnapshot;
    }
}
