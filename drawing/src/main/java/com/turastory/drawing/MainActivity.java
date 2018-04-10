package com.turastory.drawing;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

/**
 * Created by tura on 2018-04-05.
 */

public class MainActivity extends AppCompatActivity {

    private RoundPeakProgressBar progressBar;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        progressBar = findViewById(R.id.progress_bar);
        
        findViewById(R.id.Reset).setOnClickListener(v -> randomize());
        
        findViewById(R.id.see_chart_button).setOnClickListener(v -> {
            seeChart();
        });
    }
    
    private void randomize() {
        ValueAnimator animator = readyProgressBarAnimation(new Random().nextFloat() * 100);
        animator.addUpdateListener(animation -> progressBar.setProgress((Float) animation.getAnimatedValue()));
        animator.start();
    }
    
    private ValueAnimator readyProgressBarAnimation(float v) {
        ValueAnimator anim = ValueAnimator.ofFloat(progressBar.getProgress(), v);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        return anim;
    }
    
    private void seeChart() {
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }
}
