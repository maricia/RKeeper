package com.maricia.claim.ui;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by maricia on 3/25/2018.
 */

public class IconPickerWrapper implements RadioGroup.OnCheckedChangeListener {


    private TextView label;

    public IconPickerWrapper(final TextView label){
        this.label = label;
    }

    public void setLabelText(final CharSequence text){

        label.setText(text);
    }

    @Override
    public void onCheckedChanged(final RadioGroup group, final int checkedId) {

        final View selected = group.findViewById(checkedId);
        setLabelText(group.findViewById(checkedId).getContentDescription());

    }
}
