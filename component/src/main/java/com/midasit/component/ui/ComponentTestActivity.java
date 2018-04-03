package com.midasit.component.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.midasit.component.R;
import com.midasit.component.core.ComponentBaseActivity;

/**
 * Created by nyh0111 on 2018-04-02.
 */

public class ComponentTestActivity extends ComponentBaseActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_test);
    }
}
