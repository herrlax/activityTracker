package com.salmon.sports.spots;

import java.util.Date;

/**
 * Created by Mikael Malmqvist on 2015-05-24.
 */
public class WalkingItem extends SportItem {
    static final String TITLE = "walking";

    static final int ICON = R.drawable.walking;
    static final int INVICON = R.drawable.invwalking;

    public int getICON() {
        return ICON;
    }

    public WalkingItem(String date, double duration) {
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
