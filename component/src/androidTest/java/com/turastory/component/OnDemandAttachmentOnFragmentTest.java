package com.turastory.component;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.turastory.component.ui.OnDemandTestActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by tura on 2018-04-03.
 */

@RunWith(AndroidJUnit4.class)
public class OnDemandAttachmentOnFragmentTest {
    
    @Rule
    public ActivityTestRule<OnDemandTestActivity> activityRule =
        new ActivityTestRule<>(OnDemandTestActivity.class, false, true);
    
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
    
    @Test
    public void test_attachAndSwitch() {
        onView(withId(R.id.show_text_when_button_clicked_button))
            .perform(click());
        
        onView(withId(R.id.click_me_button))
            .check(matches(isDisplayed()));
        
        onView(withId(R.id.show_text_button))
            .perform(click());
        
        onView(withId(R.id.click_me_button))
            .check(doesNotExist());
    }
}
