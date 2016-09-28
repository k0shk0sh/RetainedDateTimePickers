package com.fastaccess.datetimepicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import com.fastaccess.datetimepicker.callback.TimePickerCallback;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kosh on 27 Sep 2016, 10:31 PM
 */

public class TimePickerFragmentDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private long date;
    private DateTimeBuilder builder;

    private TimePickerCallback callback;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        // priority to fragments.
        if (getParentFragment() != null && getParentFragment() instanceof TimePickerCallback) {
            callback = (TimePickerCallback) getParentFragment();
        } else if (context instanceof TimePickerCallback) {
            callback = (TimePickerCallback) context;
        } else {
            throw new RuntimeException(String.format("%s must implement TimePickerCallback", context.getClass().getSimpleName()));
        }
    }

    @Override public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        setupArguments(savedInstanceState);
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), builder.getThemeResId(), this,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), builder.is24Hours());
        if (builder.getCurrentHour() != -1 && builder.getCurrentMinute() != -1) {
            timePickerDialog.updateTime(builder.getCurrentHour(), builder.getCurrentMinute());
        }
        return timePickerDialog;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("date", date);
        outState.putParcelable("builder", builder);
    }

    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar datetime = Calendar.getInstance();
        // first append selected hour and minute
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);
        long time = datetime.getTimeInMillis();
        if (date != 0) {
            datetime.setTimeInMillis(date);
        } else {
            datetime.setTime(new Date());
        }
        // then append it back after time is set
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);
        callback.onTimeSet(time, datetime.getTimeInMillis());
        dismiss();
    }

    private void setupArguments(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            date = getArguments().getLong("date");
            builder = getArguments().getParcelable("builder");
        } else {
            date = savedInstanceState.getLong("date");
            builder = savedInstanceState.getParcelable("builder");
        }
    }

    static TimePickerFragmentDialog newInstance(long date, @NonNull DateTimeBuilder builder) {
        TimePickerFragmentDialog dialog = new TimePickerFragmentDialog();
        Bundle bundle = new Bundle();
        bundle.putLong("date", date);
        bundle.putParcelable("builder", builder);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static TimePickerFragmentDialog newInstance(@NonNull DateTimeBuilder builder) {
        return newInstance(0, builder);
    }

    public static TimePickerFragmentDialog newInstance(boolean is24Hours) {
        return newInstance(0, DateTimeBuilder.get().with24Hours(is24Hours));
    }

    public static TimePickerFragmentDialog newInstance() {
        return newInstance(0, DateTimeBuilder.get());
    }
}
