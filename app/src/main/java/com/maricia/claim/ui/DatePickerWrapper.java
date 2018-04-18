package com.maricia.claim.ui;

import android.view.View;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by maricia on 3/25/2018.
 */

public class DatePickerWrapper implements View.OnClickListener, View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
    private final TextView display;
    private DatePickerDialog dialog = null;
    private Date currentDate = null;

    public DatePickerWrapper(TextView display) {
        this.display = display;
        this.display.setFocusable(true);
        this.display.setClickable(true);
        this.display.setOnClickListener(this);
        this.display.setOnFocusChangeListener(this);
        this.setDate(new Date());
    }


    void openDatePickerDialog(){
        if(dialog == null){
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(getDate());
            dialog = new DatePickerDialog(display.getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        dialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //date is picked
        final Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        setDate(calendar.getTime());

    }

    @Override
    public void onClick(View v) {

        openDatePickerDialog();

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            openDatePickerDialog();
        }
    }

    public void setDate( final Date date) {
        if (date == null){
            try {
                throw new IllegalAccessException("date may not be null");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        this.currentDate = (Date) date.clone();
        this.display.setText(dateFormat.format(currentDate));

        if (this.dialog != null){
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(currentDate);
            this.dialog.updateDate(
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            );
        }
    }

    public Date getDate(){
        return currentDate;
    }
}
