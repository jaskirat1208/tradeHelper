package main;

import ds.MovingAverageQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedRecorderTest {
    @Test
    void test1() {
        MovingAverageQueue queue = new MovingAverageQueue(5);
        queue.push(1);
        queue.push(2);

        assertEquals(1.5, queue.getMovingAverage());
    }
}