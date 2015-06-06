package com.salmon.sports.spots;

import java.util.Date;

/**
 * Created by Mikael Malmqvist on 2015-05-24.
 */
public class RunningItem extends SportItem {
    static final String TITLE = "running";
    static final int ICON = R.drawable.runningnoshadow;
    static final int INVICON = R.drawable.invrunning;

    public int getICON() {
        return ICON;
    }


    public RunningItem(String date, double duration) {
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
