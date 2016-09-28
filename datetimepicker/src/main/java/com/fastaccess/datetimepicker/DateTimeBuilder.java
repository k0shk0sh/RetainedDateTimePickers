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
    private boolean twentyFourTimeFormat;
    private long selectedDate;
    private int currentHour;
    private int currentMinute;
    private int themeResId;

    public static DateTimeBuilder get() {
        return new DateTimeBuilder().withCurrentHour(-1).withCurrentMinute(-1);
    }

    public DateTimeBuilder withMinDate(long minDate) {
        this.minDate = minDate;
        return this;
    }

    public DateTimeBuilder withMaxDate(long maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public DateTimeBuilder witTime(boolean withTime) {
        this.withTime = withTime;
        return this;
    }

    public DateTimeBuilder with24Hours(boolean is24Hours) {
        this.twentyFourTimeFormat = is24Hours;
        return this;
    }

    public DateTimeBuilder withSelectedDate(long selectedDate) {
        this.selectedDate = selectedDate;
        return this;
    }

    public DateTimeBuilder withCurrentHour(int setCurrentHour) {
        this.currentHour = setCurrentHour;
        return this;
    }

    public DateTimeBuilder withCurrentMinute(int setCurrentMinute) {
        this.currentMinute = setCurrentMinute;
        return this;
    }

    public DateTimeBuilder withTheme(@StyleRes int themeResId) {
        this.themeResId = themeResId;
        return this;
    }

    int getCurrentHour() {
        return currentHour;
    }

    int getCurrentMinute() {
        return currentMinute;
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
        return twentyFourTimeFormat;
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
        dest.writeByte(this.twentyFourTimeFormat ? (byte) 1 : (byte) 0);
        dest.writeLong(this.selectedDate);
        dest.writeInt(this.currentHour);
        dest.writeInt(this.currentMinute);
        dest.writeInt(this.themeResId);
    }

    protected DateTimeBuilder(Parcel in) {
        this.minDate = in.readLong();
        this.maxDate = in.readLong();
        this.withTime = in.readByte() != 0;
        this.twentyFourTimeFormat = in.readByte() != 0;
        this.selectedDate = in.readLong();
        this.currentHour = in.readInt();
        this.currentMinute = in.readInt();
        this.themeResId = in.readInt();
    }

    public static final Creator<DateTimeBuilder> CREATOR = new Creator<DateTimeBuilder>() {
        @Override public DateTimeBuilder createFromParcel(Parcel source) {return new DateTimeBuilder(source);}

        @Override public DateTimeBuilder[] newArray(int size) {return new DateTimeBuilder[size];}
    };
}
