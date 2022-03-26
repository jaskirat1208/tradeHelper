package platform.oms;

public class InstrumentPosition {
    private double mTotalBuyVal = 0;
    private double mTotalSellVal = 0;
    private int mTotalBuyQty = 0;
    private int mTotalSellQty = 0;

    public int getNetQty() {
        return mTotalBuyQty - mTotalSellQty;
    }

    /**
     *
     * @param price: Price of trade
     * @param qty: Quantity of trade
     * @param side: 1 or 2 depending on BUY or SELL
     */
    public void addInstrument(double price, int qty, int side) {
        if(side == 1) {
            mTotalBuyVal += price * qty;
            mTotalBuyQty += qty;
        }
        else if(side == 2) {
            mTotalSellVal += price * qty;
            mTotalSellQty += qty;
        }
    }

    public double getNetPnl(double ltp) {
        return - ((mTotalBuyVal - mTotalSellVal) - getNetQty() * ltp);
    }
}
