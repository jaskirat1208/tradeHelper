package platform;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OMService {
    private static OMService omService = new OMService();

    private NseIndexPriceFetcher mFeedFetcher;
    private OMService() {

    }

    public void registerPriceFetcher(NseIndexPriceFetcher priceFetcher) {
        mFeedFetcher = priceFetcher;
    }
    public static OMService getInstance() {
        return omService;
    }

    public void Buy(String index) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.printf("B, %s, %f, %s%n", index, mFeedFetcher.getLastPrice(index), formatter.format(new Date()));
    }

    public void Sell(String index) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.printf("S, %s, %f, %s%n", index, mFeedFetcher.getLastPrice(index), formatter.format(new Date()));
    }
}
