package ds;

import java.util.LinkedList;
import java.util.Queue;

public class MovingAverageQueue {
    private int mSize;
    private double rollingSum = 0;
    private double mTail = 0;
    private Queue<Double> pricesQueue = new LinkedList<>();

    public MovingAverageQueue(int size) {
        mSize = size;
    }

    public void push(double price) {
        mTail = price;
        rollingSum += price;
        if(pricesQueue.size() == mSize) {
            rollingSum -= pricesQueue.remove();
        }

        pricesQueue.add(price);
    }

    public double getMovingAverage() {
        if(pricesQueue.size() == 0)
            return 0;

        return rollingSum/pricesQueue.size();
    }

    public double getSlope() {
        double head = pricesQueue.peek();
        return (mTail - head)/pricesQueue.size();
    }
}
