package com.midasit.component;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.midasit.component.component.TextComponent;
import com.midasit.component.core.ComponentBaseActivity;

/**
 * Created by nyh0111 on 2018-04-02.
 */

public class MainActivity extends ComponentBaseActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
