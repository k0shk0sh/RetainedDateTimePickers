package com.fastaccess.datetimepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.fastaccess.datetimepicker.callback.DatePickerCallback;

import java.util.Calendar;

/**
 * Created by Kosh on 27 Sep 2016, 10:31 PM
 */

public class DatePickerFragmentDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DateTimeBuilder builder;

    private DatePickerCallback callback;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() != null && getParentFragment() instanceof DatePickerCallback) {
            // priority to fragments.
            callback = (DatePickerCallback) getParentFragment();
        } else if (context instanceof DatePickerCallback) {
            callback = (DatePickerCallback) context;
        } else {
            throw new RuntimeException(String.format("%s must implement DatePickerCallback", context.getClass().getSimpleName()));
        }
    }

    @Override public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        setupArguments(savedInstanceState);
        final Calendar calendar = Calendar.getInstance();
        if (builder.getSelectedDate() != 0) {
            calendar.setTimeInMillis(builder.getSelectedDate());
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), builder.getThemeResId(), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (builder.getMinDate() != 0) {
            datePickerDialog.getDatePicker().setMinDate(builder.getMinDate());
        }
        if (builder.getMaxDate() != 0) {
            datePickerDialog.getDatePicker().setMaxDate(builder.getMaxDate());
        }
        return datePickerDialog;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("builder", builder);
    }

    @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar date = Calendar.getInstance();
        date.set(year, month, dayOfMonth);
        if (!builder.isWithTime()) {
            callback.onDateSet(date.getTimeInMillis());
        } else {
            TimePickerFragmentDialog.newInstance(date.getTimeInMillis(), builder).show(getActivity().getSupportFragmentManager(),
                    "TimePickerFragmentDialog");
        }
        dismiss();
    }

    private void setupArguments(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            builder = getArguments().getParcelable("builder");
        } else {
            builder = savedInstanceState.getParcelable("builder");
        }
    }

    public static DatePickerFragmentDialog newInstance(@NonNull DateTimeBuilder builder) {
        DatePickerFragmentDialog dialog = new DatePickerFragmentDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("builder", builder);
        dialog.setArguments(bundle);
        return dialog;
    }

    public static DatePickerFragmentDialog newInstance(boolean withTime) {
        return newInstance(DateTimeBuilder.get().witTime(withTime));
    }

    public static DatePickerFragmentDialog newInstance() {
        return newInstance(DateTimeBuilder.get());
    }
}
