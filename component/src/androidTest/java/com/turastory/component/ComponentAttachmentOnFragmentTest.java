package com.turastory.component;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.turastory.component.ui.TestActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by nyh0111 on 2018-04-03.
 */

@RunWith(AndroidJUnit4.class)
public class ComponentAttachmentOnFragmentTest {
    
    @Rule
    public ActivityTestRule<TestActivity> activityRule =
        new ActivityTestRule<>(TestActivity.class, false, true);
    
    @Test
    public void test_attachShowTextComponent() {
        onView(withId(R.id.show_text_button))
            .perform(click());
        
        onView(withText("testtest"))
            .check(matches(isDisplayed()));
    }
    
    @Test
    public void test_attachComponentIncludingButton() {
        onView(withId(R.id.show_text_when_button_clicked_button))
            .perform(click());
    
        onView(withText("click me!"))
            .check(matches(isDisplayed()))
            .perform(click());
    
        onView(withText("Hello There!"))
            .check(matches(isDisplayed()));
    }
}
