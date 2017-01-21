package com.fastaccess.datetimepicker.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fastaccess.datetimepicker.DatePickerFragmentDialog;
import com.fastaccess.datetimepicker.DateTimeBuilder;
import com.fastaccess.datetimepicker.TimePickerFragmentDialog;
import com.fastaccess.datetimepicker.callback.DatePickerCallback;
import com.fastaccess.datetimepicker.callback.TimePickerCallback;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.fastaccess.datetimepicker.sample.SampleHelper.getDateAndTime;
import static com.fastaccess.datetimepicker.sample.SampleHelper.getDateOnly;
import static com.fastaccess.datetimepicker.sample.SampleHelper.getTimeOnly;

/**
 * Created by Kosh on 28 Sep 2016, 9:05 AM
 */

public class SampleFragment extends Fragment implements DatePickerCallback, TimePickerCallback {

    @BindView(R.id.fromFragment) View fromFragment;
    @BindView(R.id.results) TextView results;
    private Unbinder unbinder;

    @OnClick({R.id.datePickerOnly, R.id.datePickerMinMaxPicker, R.id.timePickerOnly,
            R.id.dateAndTimePicker, R.id.ultimatePicker, R.id.timePickerOnly24Hours})
    void onClick(View view) {
        Calendar minDate = Calendar.getInstance();
        minDate.set(2016, minDate.get(Calendar.MONTH), minDate.get(Calendar.DAY_OF_MONTH));
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(2016, minDate.get(Calendar.MONTH) + 1, minDate.get(Calendar.DAY_OF_MONTH));
        switch (view.getId()) {
            case R.id.datePickerOnly:
                DatePickerFragmentDialog.newInstance().show(getChildFragmentManager(), SampleHelper.DATE_PICKER_FRAGMENT_DIALOG);
                break;
            case R.id.datePickerMinMaxPicker:
                DatePickerFragmentDialog.newInstance(DateTimeBuilder.newInstance()
                        .withMinDate(minDate.getTimeInMillis())
                        .withMaxDate(maxDate.getTimeInMillis()))
                        .show(getChildFragmentManager(), SampleHelper.DATE_PICKER_FRAGMENT_DIALOG);
                break;
            case R.id.timePickerOnly:
                TimePickerFragmentDialog.newInstance().show(getChildFragmentManager(), SampleHelper.TIME_PICKER_FRAGMENT_DIALOG);
                break;
            case R.id.timePickerOnly24Hours:
                TimePickerFragmentDialog.newInstance(true).show(getChildFragmentManager(), SampleHelper.TIME_PICKER_FRAGMENT_DIALOG);
                break;
            case R.id.dateAndTimePicker:
                DatePickerFragmentDialog.newInstance(true).show(getChildFragmentManager(), SampleHelper.DATE_PICKER_FRAGMENT_DIALOG);
                break;
            case R.id.ultimatePicker:
                Calendar currentDate = Calendar.getInstance();
                currentDate.set(2016, currentDate.get(Calendar.MONTH) + 1, currentDate.get(Calendar.DAY_OF_MONTH));
                DatePickerFragmentDialog.newInstance(
                        DateTimeBuilder.newInstance()
                                .withTime(true)
                                .with24Hours(true)
                                .withSelectedDate(currentDate.getTimeInMillis())
                                .withMinDate(minDate.getTimeInMillis())
                                .withMaxDate(maxDate.getTimeInMillis())
                                .withCurrentHour(12)
                                .withCurrentMinute(30)
                                .withTheme(R.style.PickersTheme))
                        .show(getChildFragmentManager(), SampleHelper.DATE_PICKER_FRAGMENT_DIALOG);
                break;
        }
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buttons_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fromFragment.setVisibility(View.GONE);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override public void onDateSet(long date) {
        results.setText(getDateOnly(date));
    }

    @Override public void onTimeSet(long time, long date) {
        results.setText(String.format("Full Date: %s\nTime Only: %s", getDateAndTime(date), getTimeOnly(time)));
    }
}
