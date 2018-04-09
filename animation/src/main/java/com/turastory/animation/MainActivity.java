package com.turastory.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;

public class MainActivity extends AppCompatActivity {
    
    private boolean visibility;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ViewGroup container = findViewById(R.id.container);
        TextView text = findViewById(R.id.text);
        
        container.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(container);
            
            visibility = !visibility;
            text.setVisibility(visibility ? View.VISIBLE : View.GONE);
        });
    }
}
