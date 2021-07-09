package com.zenoation.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;
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


    /**
     * 표시할 수 있는 화면 가로크기
     */
    public int getDisplayWidth(Activity activity) {
        Rect displayRectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        return displayRectangle.width();
    }

    /**
     * 표시할 수 있는 화면 세로크기
     */
    public int getDisplayHeight(Activity activity) {
        Rect displayRectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        return displayRectangle.height();
    }
}
