package com.fastaccess.datetimepicker.callback;

/**
 * Created by Kosh on 27 Sep 2016, 10:33 PM
 */

public interface TimePickerCallback {

    /**
     * @param timeOnly
     *         time only
     * @param dateWithTime
     *         full date with time
     */
    void onTimeSet(long timeOnly, long dateWithTime);
}
