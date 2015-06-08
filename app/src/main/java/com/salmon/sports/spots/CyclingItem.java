package com.salmon.sports.spots;

/**
 * Created by Mikael Malmqvist on 2015-06-08.
 */
public class CyclingItem extends SportItem {
    static final String TITLE = "cycling";
    static final int ICON = R.drawable.cycling;
    static final int INVICON = R.drawable.invcycling;

    public int getICON() {
        return ICON;
    }


    public CyclingItem(String date, double duration) {
        super(date, duration);
    }

    @Override
    public String getTITLE() {
        return TITLE;
    }

    @Override
    public String toString() {
        return super.toString() + TITLE;
    }
}
