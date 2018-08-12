package com.example.zhuxiaodong.inoteplus.entities;

import android.content.Context;

import java.util.Random;

/**
 * list of colors available
 * Created by zhuxiaodong on 2018/8/8.
 */

public class ColorList {
    private static ColorList mInstance = null;
    private Context mContext;
    private String[] colors;

    public static ColorList getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ColorList(context);
        }
        return mInstance;
    }

    private ColorList(Context context) {
        this.mContext = context;
        colors = new String[]{"#09A9FF", "#3E51B1", "#673BB7", "#4BAA50", "#F94336", "#0A9B88"};
    }

    public String[] getColors() {
        return colors;
    }

    public String getRandomColor() {
        return colors[new Random().nextInt(colors.length)];
    }
}
