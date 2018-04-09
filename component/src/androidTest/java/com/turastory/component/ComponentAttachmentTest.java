package com.turastory.component;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.turastory.component.ui.ComponentTestActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by nyh0111 on 2018-04-03.
 */

@RunWith(AndroidJUnit4.class)
public class ComponentAttachmentTest {
    
    @Rule
    public ActivityTestRule<ComponentTestActivity> activityRule =
        new ActivityTestRule<>(ComponentTestActivity.class, false, true);
    
    @Test
    public void test_attachShowTextComponent() {
    }
}
