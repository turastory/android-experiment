package com.midasit.mvp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.midasit.mvp.R;
import com.midasit.mvp.recharge.RechargeActivity;

/**
 * Created by nyh0111 on 2018-03-29.
 */

public class MainActivity extends AppCompatActivity {
    
    private TextView pointsText;
    private Button rechargeButton;
    private Button historyButton;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        pointsText = findViewById(R.id.points_text);
        rechargeButton = findViewById(R.id.recharge_points_button);
        historyButton = findViewById(R.id.recharge_history_button);
        
        rechargeButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RechargeActivity.class));
        });
    }
}
