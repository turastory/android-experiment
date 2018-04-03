package com.midasit.component.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyh0111 on 2018-04-03.
 */

public abstract class ComponentBaseFragment extends Fragment {

    // Component Handling
    protected List<Component> components = new ArrayList<>();
    
    private boolean viewCreated;
    
    public ComponentBaseFragment addComponent(Component component) {
        components.add(component);
     
        if (component instanceof UIComponent) {
            if (viewCreated) {
                renderUiComponent(
                    LayoutInflater.from(getView().getContext()),
                    getView(),
                    (UIComponent) component);
            }
        }
        
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
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewCreated = false;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = createView(inflater, container, savedInstanceState);
        
        Stream.of(components)
            .filter(component -> component instanceof UIComponent)
            .map(component -> (UIComponent) component)
            .forEach(uiComponent -> renderUiComponent(inflater, view, uiComponent));
        
        viewCreated = true;
        
        return view;
    }
    
    private void renderUiComponent(LayoutInflater inflater, View view, UIComponent uiComponent) {
        ViewGroup parent = view.findViewById(uiComponent.bindingViewGroupId());
        
        if (parent.getChildCount() > 0)
            parent.removeAllViews();
        
        inflater.inflate(uiComponent.resourceId(), parent, uiComponent.attachToRoot());
        
        if (uiComponent.attachToRoot())
            uiComponent.bindView(parent);
    }
    
    public abstract View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
