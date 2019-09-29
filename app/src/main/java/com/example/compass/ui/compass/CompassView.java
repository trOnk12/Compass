package com.example.compass.ui.compass;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.example.compass.R;

public class CompassView extends FrameLayout {

    private CompassNeedleView compassNeedleView;
    private CompassPlateView compassPlateView;

    private Integer currentCompassRotation = 0;

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.compass_view, this);
        compassNeedleView = findViewById(R.id.compass_needle_view);
        compassPlateView = findViewById(R.id.compass_plate_view);
    }

    public void rotateCompass(Integer degree) {
        Integer rotationDelta = currentCompassRotation - degree;
        setRotation(-(currentCompassRotation+rotationDelta));
        currentCompassRotation = degree;
    }

    public void rotateNeedle(Float degree) {
        compassNeedleView.rotateNeedle(degree);
    }

    public void drawIndicator(Double degree) {
        compassPlateView.drawLatLngIndicator(degree);
    }

}
