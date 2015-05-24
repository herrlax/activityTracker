package com.salmon.sports.spots;

import java.util.Date;

/**
 * Created by Mikael Malmqvist on 2015-05-24.
 */
abstract class SportItem {
    private double duration;
    private Date date;

    public SportItem(Date date, double duration) {
        this.date = date;
        this.duration = duration;
    }

    public abstract String getTITLE();

}
