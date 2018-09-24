package com.shu.popularmoviesstage1;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    static List<MovieData> extractMovieData(String jsonString) {

        MovieData d = new MovieData(348350
                , "Solo: A Star Wars Story"
                , 1727
                , false
                , 6.7
                , 269.214
                , "/4oD6VEccFkorEBTEDXtpLAaz0Rl.jpg"
                , "en"
                , "Solo: A Star Wars Story"
                , new ArrayList<Integer>() {{
            add(28);
            add(12);
            add(878);
        }}
                , "/96B1qMN9RxrAFu6uikwFhQ6N6J9.jpg"
                , false
                , "Through a series of daring escapades deep within a dark and dangerous criminal underworld, Han Solo meets his mighty future copilot Chewbacca and encounters the notorious gambler Lando Calrissian."
                , "2018-05-15");

        List<MovieData> list = new ArrayList<MovieData>();
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);
        list.add(d);

        return list;
    }


    public static int display_width(Context context) {
        float width = context.getResources().getDisplayMetrics().widthPixels
                / context.getResources().getDisplayMetrics().density;
        return (int) width;
    }
}