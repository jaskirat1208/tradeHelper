package ds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovingAverageQueueTest {

    @Test
    void test1() {
        MovingAverageQueue queue = new MovingAverageQueue(5);
        queue.push(1);
        queue.push(2);

        assertEquals(1.5, queue.getMovingAverage());
        assertEquals(0.5, queue.getSlope());
    }
}