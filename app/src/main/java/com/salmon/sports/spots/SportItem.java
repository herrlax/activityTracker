package com.salmon.sports.spots;

import java.util.Date;

/**
 * Created by Mikael Malmqvist on 2015-05-24.
 */
abstract class SportItem {
    private double duration;
    private String date;
    private final int ICON = R.drawable.greencircle;

    public String getDate() {
        return date;
    }

    public int getICON() {
        return ICON;
    }

    public SportItem(String date, double duration) {
        this.date = date;
        this.duration = duration;
    }

    public abstract String getTITLE();

    public String toString() {
        return duration + " h ";
    }

}
