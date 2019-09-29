package com.example.compass.utills;


import java.util.ArrayDeque;

public class LowPassFilterStrategy implements SignalSmoothingStrategy {

    private float sumSin, sumCos;

    private ArrayDeque<Float> queue = new ArrayDeque<Float>();

    private void add(float radians) {
        sumSin += (float) Math.sin(radians);
        sumCos += (float) Math.cos(radians);

        queue.add(radians);

        int LENGTH = 10;

        if (queue.size() > LENGTH) {
            float old = queue.poll();
            sumSin -= Math.sin(old);
            sumCos -= Math.cos(old);
        }
    }

    private float average() {
        int size = queue.size();

        return (float) Math.atan2(sumSin / size, sumCos / size);
    }

    @Override
    public double compute(float data) {
        add(data);

        return Math.toDegrees(average());
    }
}
