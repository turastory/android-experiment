package com.turastory.component.core;

import android.view.View;

/**
 * Created by nyh0111 on 2018-04-03.
 */

public interface UIComponent extends Component {
    
    /**
     * @return which ViewGroup to bind this ui component?
     */
    int bindingViewGroupId();
    
    /**
     * @return layout resource id that defines this ui component.
     */
    int resourceId();
    
    /**
     * Binding views and define actions if needed.
     * Only called when {@link this#attachToRoot() returns true.}
     *
     * @param parent View inflated by Android framework using {@link UIComponent#bindingViewGroupId()}
     */
    default void bindView(View parent) {
    
    }
    
    default boolean attachToRoot() {
        return true;
    }
}
