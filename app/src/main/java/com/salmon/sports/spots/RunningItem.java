package com.salmon.sports.spots;

import java.util.Date;

/**
 * Created by Mikael Malmqvist on 2015-05-24.
 */
public class RunningItem extends SportItem {
    private final String TITLE = "running";

    public RunningItem(Date date, double duration) {
        super(date, duration);
    }

    @Override
    public String getTITLE() {
        return TITLE;
    }
}
