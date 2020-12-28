package com.zenoation.library;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;


/**
 * Created by kisoojo on 2020.02.03
 */
public class Utils {
    private Toast mToast;

    private Utils() {
    }

    //private static class LazyHolder {
    //    private static final Utils INSTANCE = new Utils();
    //}
    //
    //public static Utils getInstance() {
    //    return LazyHolder.INSTANCE;
    //}

    private volatile static Utils uniqueInstance;

    public static Utils getInstance() {
        if (uniqueInstance == null) {
            synchronized (Utils.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Utils();
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * px을 dp로 변환
     */
    public int getDpFromPx(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / displayMetrics.density);
    }

    /**
     * dp를 px로 변환
     */
    public int getPxFromDp(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * displayMetrics.density);
    }
}
