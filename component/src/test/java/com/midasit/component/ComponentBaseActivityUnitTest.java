package com.midasit.component;

import com.midasit.component.component.EmptyComponent;
import com.midasit.component.component.TextComponent;
import com.midasit.component.core.Component;
import com.midasit.component.ui.ComponentTestActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nyh0111 on 2018-04-02.
 */

@RunWith(RobolectricTestRunner.class)
public class ComponentBaseActivityUnitTest {
    
    ComponentTestActivity activity;
    
    @Before
    public void readyMainActivity() {
        activity = Robolectric.setupActivity(ComponentTestActivity.class);
    }
    
    @Test
    public void test_addAndRemoveComponentUsingComponent() {
        Component emptyComponent = new EmptyComponent();
        
        activity.addComponent(emptyComponent);
        assertThat(activity.componentCount(), is(1));
        
        activity.removeComponent(emptyComponent);
        assertThat(activity.componentCount(), is(0));
    }
    
    @Test
    public void test_addAndRemoveComponentUsingName() {
        Component emptyComponent = new EmptyComponent();
        
        activity.addComponent(emptyComponent);
        activity.addComponent(emptyComponent);
        assertThat(activity.componentCount(), is(2));
        
        activity.removeComponent(emptyComponent.name());
        assertThat(activity.componentCount(), is(0));
    }
    
    @Test
    public void test_getComponentUsingName() {
        Component empty1 = new EmptyComponent();
        Component text = new TextComponent();
        Component empty2 = new EmptyComponent();
        
        activity.addComponent(empty1);
        activity.addComponent(text);
        activity.addComponent(empty2);
    
        assertThat(activity.getComponent("empty").get(), allOf(notNullValue(), is(empty1)));
        assertThat(activity.getComponent("text").get(), allOf(notNullValue(), is(text)));
        assertThat(activity.getComponents("empty").size(), is(2));
    }
}
