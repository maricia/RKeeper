package com.maricia.claim.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maricia.claim.R;
import com.maricia.claim.ui.DatePickerWrapper;

import java.util.Date;

public class DatePickerLayout extends LinearLayout {
    private TextView label;
    private DatePickerWrapper wrapper;

    /*
    The parameters for the inflate method are the layout resource, the ViewGroup (in this case,
    DatePickerLayout) that will contain the layout, and whether or not to actually attach
    the elements of the layout resource to the ViewGroup.
    As you are using a merge element in the layout resource, the third parameter must be true,
    because otherwise the contents of the layout will be lost.
     */
    void initialize(final Context context) {
        setOrientation(VERTICAL);

        LayoutInflater.from(context).inflate(R.layout.widget_date_picker, this, true);

        label = (TextView) getChildAt(0);
        wrapper = new DatePickerWrapper((TextView) getChildAt(1));
    }

    public DatePickerLayout(Context context) {
        super(context);
        initialize(context);
    }

    public DatePickerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DatePickerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }



    public void setDate(final Date date) {
        wrapper.setDate(date);
    }

    public Date getDate() {
        return wrapper.getDate();
    }

    public void setLabel(final CharSequence text) {
        label.setText(text);
    }

    public void setLabel(final int resid) {
        label.setText(resid);
    }

    public CharSequence getLabel() {
        return label.getText();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return new SavedState(
                super.onSaveInstanceState(),
                getDate().getTime(), getLabel());
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setDate(new Date(savedState.timestamp));
        setLabel(savedState.label);
    }








/*
 Objects crossing between Activity instances need to be Parcelable,
 because Android may need to store the objects temporarily through the Activity life cycle.
 Being able to store just the important bits of data and state, instead of the entire widget tree,
 s very useful for conserving memory when the user has a lot of applications running.
 BaseSavedState implements Parcelable and will allow the DatePickerLayout to
 remember its state when the Activity is destroyed and recreated by the system.
 */


    private static class SavedState extends BaseSavedState {
        final long timestamp;
        final CharSequence label;

        public SavedState(final Parcelable superState, final long timestamp, final CharSequence label) {
            super(superState);
            this.timestamp = timestamp;
            this.label = label;
        }

        SavedState(final Parcel in) {
            super(in);
            this.timestamp = in.readLong();
            this.label = TextUtils.CHAR_SEQUENCE_CREATOR
                    .createFromParcel(in);
        }

        @Override
        public void writeToParcel(final Parcel out, final int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(timestamp);
            TextUtils.writeToParcel(label, out, flags);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(final Parcel source) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
        };




    }



}
