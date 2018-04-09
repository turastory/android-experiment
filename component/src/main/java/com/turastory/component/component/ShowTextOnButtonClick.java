package com.turastory.component.component;

import android.view.View;
import android.widget.TextView;

import com.turastory.component.R;
import com.turastory.component.core.UIComponent;

/**
 * Created by nyh0111 on 2018-04-03.
 */

public class ShowTextOnButtonClick implements UIComponent {
    @Override
    public String name() {
        return "showTextOnButton";
    }
    
    @Override
    public int bindingViewGroupId() {
        return R.id.universal_container;
    }
    
    @Override
    public int resourceId() {
        return R.layout.layout_show_text_on_button_click;
    }
    
    @Override
    public void bindView(View parent) {
        TextView text = parent.findViewById(R.id.hello_text_view);
        
        parent.findViewById(R.id.click_me_button).setOnClickListener(v -> {
            text.setVisibility(View.VISIBLE);
        });
    }
}
