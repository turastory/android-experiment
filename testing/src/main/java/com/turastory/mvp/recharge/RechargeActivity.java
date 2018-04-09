package com.turastory.mvp.recharge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.turastory.mvp.R;

/**
 * Created by nyh0111 on 2018-03-29.
 */

public class RechargeActivity extends AppCompatActivity {
    
    private Button backButton;
    private Button rechargeButton;
    private EditText rechargeEditText;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        
        backButton = findViewById(R.id.back_button);
        rechargeButton = findViewById(R.id.recharge_button);
        rechargeEditText = findViewById(R.id.recharge_edit_text);
    }
}
