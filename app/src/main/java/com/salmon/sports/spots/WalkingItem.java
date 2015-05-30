package com.salmon.sports.spots;

import java.util.Date;

/**
 * Created by Mikael Malmqvist on 2015-05-24.
 */
public class WalkingItem extends SportItem {
    private final String TITLE = "walking";

    private final int ICON = R.drawable.walking;

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
