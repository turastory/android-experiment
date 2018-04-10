package com.turastory.mvp.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.turastory.mvp.R;

/**
 * Created by tura on 2018-03-29.
 */

public class HistoryActivity extends AppCompatActivity {
    
    private RecyclerView historiesView;
    private Button backButton;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        
        historiesView = findViewById(R.id.history_view);
        backButton = findViewById(R.id.back_button);
    }
}
