package com.salmon.sports.spots;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mikael Malmqvist on 2015-06-02.
 */
public class JsonUtil {

    public static String toJSon(SportItem sportItem) {

        // Converting our sportItem to JSon
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("duration", sportItem.getDuration());
            jsonObj.put("date", sportItem.getDate());
            jsonObj.put("type", sportItem.getTITLE());

            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;

    }

}
