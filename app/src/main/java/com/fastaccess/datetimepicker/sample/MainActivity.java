package com.fastaccess.datetimepicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fastaccess.datetimepicker.DatePickerFragmentDialog;
import com.fastaccess.datetimepicker.DateTimeBuilder;
import com.fastaccess.datetimepicker.TimePickerFragmentDialog;
import com.fastaccess.datetimepicker.callback.DatePickerCallback;
import com.fastaccess.datetimepicker.callback.TimePickerCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DatePickerCallback, TimePickerCallback {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.results) TextView results;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.datePickerOnly, R.id.datePickerMinMaxPicker, R.id.timePickerOnly,
            R.id.dateAndTimePicker, R.id.ultimatePicker, R.id.timePickerOnly24Hours})
    void onClick(View view) {
        Calendar minDate = Calendar.getInstance();
        minDate.set(2016, minDate.get(Calendar.MONTH), minDate.get(Calendar.DAY_OF_MONTH));
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(2016, minDate.get(Calendar.MONTH) + 1, minDate.get(Calendar.DAY_OF_MONTH));
        switch (view.getId()) {
            case R.id.datePickerOnly:
                DatePickerFragmentDialog.newInstance().show(getSupportFragmentManager(), "DatePickerFragmentDialog");
                break;
            case R.id.datePickerMinMaxPicker:
                DatePickerFragmentDialog.newInstance(DateTimeBuilder.get()
                        .withMinDate(minDate.getTimeInMillis())
                        .withMaxDate(maxDate.getTimeInMillis()))
                        .show(getSupportFragmentManager(), "DatePickerFragmentDialog");
                break;
            case R.id.timePickerOnly:
                TimePickerFragmentDialog.newInstance().show(getSupportFragmentManager(), "TimePickerFragmentDialog");
                break;
            case R.id.timePickerOnly24Hours:
                TimePickerFragmentDialog.newInstance(true).show(getSupportFragmentManager(), "TimePickerFragmentDialog");
                break;
            case R.id.dateAndTimePicker:
                DatePickerFragmentDialog.newInstance(true).show(getSupportFragmentManager(), "DatePickerFragmentDialog");
                break;
            case R.id.ultimatePicker:
                Calendar currentDate = Calendar.getInstance();
                currentDate.set(2016, currentDate.get(Calendar.MONTH) + 1, currentDate.get(Calendar.DAY_OF_MONTH));
                DatePickerFragmentDialog.newInstance(DateTimeBuilder.get()
                        .withWithTime(true)
                        .withIs24Hours(true)
                        .withSelectedDate(currentDate.getTimeInMillis())
                        .withMinDate(minDate.getTimeInMillis())
                        .withMaxDate(maxDate.getTimeInMillis())
                        .withSetCurrentHour(12)
                        .withSetCurrentMinute(30)
                        .withTheme(R.style.PickersTheme))
                        .show(getSupportFragmentManager(), "DatePickerFragmentDialog");
                break;
        }
    }

    @Override public void onDateSet(long date) {
        results.setText(getDateOnly(date));
    }

    @Override public void onTimeSet(long time, long date) {
        results.setText(String.format("Full Date: %s\nTime Only: %s", getDateAndTime(date), getTimeOnly(time)));
    }

    private String getDateOnly(long time) {
        return new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(time);
    }

    private String getDateAndTime(long time) {
        SimpleDateFormat sample = new SimpleDateFormat("dd MMM yyyy, hh:mma", Locale.getDefault());
        return sample.format(new Date(time));
    }

    private String getTimeOnly(long time) {
        SimpleDateFormat sample = new SimpleDateFormat("hh:mma", Locale.getDefault());
        return sample.format(time);
    }
}
