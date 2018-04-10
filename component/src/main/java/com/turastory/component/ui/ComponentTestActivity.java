package com.turastory.component.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.turastory.component.R;
import com.turastory.component.core.ComponentBaseActivity;

/**
 * Created by tura on 2018-04-02.
 */

public class ComponentTestActivity extends ComponentBaseActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_test);
    }
}
