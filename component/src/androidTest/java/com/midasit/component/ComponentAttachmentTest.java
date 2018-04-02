package com.midasit.component;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;

/**
 * Created by nyh0111 on 2018-04-02.
 */

@RunWith(AndroidJUnit4.class)
public class ComponentAttachmentTest {
    
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    
    @Test
    public void test_attachSimpleTextComponent() {
//        SimpleTextComponent component = new SimpleTextComponent();
//
//        activityTestRule.getActivity().addComponent(component);
//        onView(withId(R.id.simple_text)).check(matches(isDisplayed()));
    }
}
