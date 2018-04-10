package com.turastory.component.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turastory.component.R;
import com.turastory.component.core.ComponentBaseFragment;

/**
 * Created by tura on 2018-04-03.
 */

public class AttachmentTestFragment extends ComponentBaseFragment {
    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attachment_test, container, false);
    }
}
