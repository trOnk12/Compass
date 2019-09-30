package com.example.compass.utills;

public class LowPassFilterStrategy implements SignalSmoothingStrategy{

    private static final float ALPHA = 0.2f;

     float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;

        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }

    @Override
    public float[] compute(float[] dataInput,float[] dataOutput) {
        return lowPass(dataInput,dataOutput);
    }
}
