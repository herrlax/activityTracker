package com.salmon.sports.spots;

import java.util.Date;

/**
 * Created by Mikael Malmqvist on 2015-05-24.
 */
abstract class SportItem {
    private double duration;
    private String date;
    static final int ICON = R.drawable.greencircle;
    static final int INVICON = R.drawable.greencircle;
    static final String TITLE = "";

    public String getDate() {
        return date;
    }

    public double getDuration() {
        return duration;
    }

    public int getICON() {
        return ICON;
    }

    public SportItem(String date, double duration) {
        this.date = date;
        this.duration = duration;
    }

    public abstract String getTITLE();

    // Parsing duration as xx h yy min
    public String toString() {

        int hours = (int) duration;
        duration = Math.round(duration * 100.0);
        duration = duration / 100.0;
        System.out.println("DURATION IS: " + duration);

        int min = (int)((duration % 1) * 60);

        String hurDur = "";

        if(hours > 0) {
            hurDur = hours + " h ";
        }

        if(min > 0) {
            hurDur += min + " min ";
        }

        return hurDur;

    }

}
