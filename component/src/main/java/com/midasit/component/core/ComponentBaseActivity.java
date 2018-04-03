package com.midasit.component.core;

import android.support.v7.app.AppCompatActivity;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyh0111 on 2018-04-02.
 */

public abstract class ComponentBaseActivity extends AppCompatActivity {
    List<Component> components = new ArrayList<>();
    
    public ComponentBaseActivity addComponent(Component component) {
        components.add(component);
        return this;
    }
    
    public Optional<Component> getComponent(final String componentName) {
        return Stream.of(components)
            .filter(component -> component.name().equals(componentName))
            .findFirst();
    }
    
    public List<Component> getComponents(final String componentName) {
        return Stream.of(components)
            .filter(component -> component.name().equals(componentName))
            .toList();
    }
    
    public void removeComponent(Component component) {
        components.remove(component);
    }
    
    public void removeComponent(final String componentName) {
        List<Component> removingComponents = Stream.of(components)
            .filter(component -> component.name().equals(componentName))
            .toList();
        
        components.removeAll(removingComponents);
    }
    
    public int componentCount() {
        return components.size();
    }
}
