package com.fastaccess.datetimepicker;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StyleRes;

/**
 * Created by Kosh on 27 Sep 2016, 11:53 PM
 */

public class DateTimeBuilder implements Parcelable {
    private long minDate;
    private long maxDate;
    private boolean withTime;
    private boolean is24Hours;
    private long selectedDate;
    private int setCurrentHour;
    private int setCurrentMinute;
    private int themeResId;

    public static DateTimeBuilder get() {
        return new DateTimeBuilder();
    }

    public DateTimeBuilder withMinDate(long minDate) {
        this.minDate = minDate;
        return this;
    }

    public DateTimeBuilder withMaxDate(long maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public DateTimeBuilder withWithTime(boolean withTime) {
        this.withTime = withTime;
        return this;
    }

    public DateTimeBuilder withIs24Hours(boolean is24Hours) {
        this.is24Hours = is24Hours;
        return this;
    }

    public DateTimeBuilder withSelectedDate(long selectedDate) {
        this.selectedDate = selectedDate;
        return this;
    }

    public DateTimeBuilder withSetCurrentHour(int setCurrentHour) {
        this.setCurrentHour = setCurrentHour;
        return this;
    }

    public DateTimeBuilder withSetCurrentMinute(int setCurrentMinute) {
        this.setCurrentMinute = setCurrentMinute;
        return this;
    }

    public DateTimeBuilder withTheme(@StyleRes int themeResId) {
        this.themeResId = themeResId;
        return this;
    }

    int getSetCurrentHour() {
        return setCurrentHour;
    }

    int getSetCurrentMinute() {
        return setCurrentMinute;
    }

    long getMinDate() {
        return minDate;
    }

    long getMaxDate() {
        return maxDate;
    }

    boolean isWithTime() {
        return withTime;
    }

    boolean is24Hours() {
        return is24Hours;
    }

    long getSelectedDate() {
        return selectedDate;
    }

    int getThemeResId() {
        return themeResId;
    }

    DateTimeBuilder() {}

    @Override public int describeContents() { return 0; }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.minDate);
        dest.writeLong(this.maxDate);
        dest.writeByte(this.withTime ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is24Hours ? (byte) 1 : (byte) 0);
        dest.writeLong(this.selectedDate);
        dest.writeInt(this.setCurrentHour);
        dest.writeInt(this.setCurrentMinute);
        dest.writeInt(this.themeResId);
    }

    protected DateTimeBuilder(Parcel in) {
        this.minDate = in.readLong();
        this.maxDate = in.readLong();
        this.withTime = in.readByte() != 0;
        this.is24Hours = in.readByte() != 0;
        this.selectedDate = in.readLong();
        this.setCurrentHour = in.readInt();
        this.setCurrentMinute = in.readInt();
        this.themeResId = in.readInt();
    }

    public static final Creator<DateTimeBuilder> CREATOR = new Creator<DateTimeBuilder>() {
        @Override public DateTimeBuilder createFromParcel(Parcel source) {return new DateTimeBuilder(source);}

        @Override public DateTimeBuilder[] newArray(int size) {return new DateTimeBuilder[size];}
    };
}
