package com.turastory.component.component;

import com.turastory.component.R;
import com.turastory.component.core.UIComponent;

/**
 * Created by nyh0111 on 2018-04-03.
 */

public class ShowTextComponent implements UIComponent {
    @Override
    public String name() {
        return "showText";
    }
    
    @Override
    public int bindingViewGroupId() {
        return R.id.universal_container;
    }
    
    @Override
    public int resourceId() {
        return R.layout.layout_show_text;
    }
}
