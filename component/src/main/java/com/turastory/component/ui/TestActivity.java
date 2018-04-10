package com.turastory.component.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.turastory.component.R;
import com.turastory.component.component.ShowTextComponent;
import com.turastory.component.component.ShowTextOnButtonClick;
import com.turastory.component.component.WithNoAttach;
import com.turastory.component.core.ComponentBaseFragment;

/**
 * Created by tura on 2018-04-03.
 */

public class TestActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    
        findViewById(R.id.show_text_button).setOnClickListener(v -> {
            ComponentBaseFragment fragment = new AttachmentTestFragment().addComponent(new ShowTextComponent());
            showFragment(R.id.content_container, fragment);
        });
        
        findViewById(R.id.show_text_when_button_clicked_button).setOnClickListener(v -> {
            ComponentBaseFragment fragment = new AttachmentTestFragment().addComponent(new ShowTextOnButtonClick());
            showFragment(R.id.content_container, fragment);
        });
    
        findViewById(R.id.with_no_attach_root_button).setOnClickListener(v -> {
            ComponentBaseFragment fragment = new AttachmentTestFragment().addComponent(new WithNoAttach());
            showFragment(R.id.content_container, fragment);
        });
    }
    
    private void showFragment(int container, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    
        Fragment prev = getSupportFragmentManager().findFragmentByTag("test");
        if (prev != null) {
            ft.remove(prev);
        }
    
        ft.addToBackStack(null)
            .replace(container, fragment)
            .commit();
    }
}
