package com.zenoation.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import java.util.Locale;

import static androidx.core.content.ContextCompat.getColor;

/**
 * Created by kisoojo on 2020.02.03
 */

public class KSButton extends AppCompatButton {
    private boolean mIsRound = false;
    private float mCornerRadius = 0;
    private boolean mIsBgColor = true;
    private int mBgColorNormal;
    private int mBgColorPress;

    public KSButton(Context context) {
        super(context);
        setBgColor();
    }

    public KSButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate(context, attrs);
    }

    public KSButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(context, attrs);
    }

    private void onCreate(Context context, AttributeSet attrs) {
        mIsRound = context.obtainStyledAttributes(attrs, R.styleable.KSButtonAttr).getBoolean(R.styleable.KSButtonAttr_isRound, false);
        mCornerRadius = context.obtainStyledAttributes(attrs, R.styleable.KSButtonAttr).getDimension(R.styleable.KSButtonAttr_cornerRadius, 0.0f);
        setBgColor();
    }

    private void setBgColor() {
        try {
            ColorDrawable drawable = (ColorDrawable) getBackground();
            mBgColorNormal = drawable.getColor();
        } catch (Exception e) {
            //Dlog.e(e.getMessage());
            //e.printStackTrace();
            mIsBgColor = false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mIsBgColor) {
            String c1 = String.format(Locale.KOREA, "#%08x", mBgColorNormal);
            String c2 = String.format(Locale.KOREA, "#%08x", getColor(getContext(), R.color.trans_75));
            String c = mixColor(c1, c2);
            mBgColorPress = Color.parseColor(c);

            StateListDrawable res = new StateListDrawable();

            GradientDrawable press = new GradientDrawable();
            press.setShape(mIsRound?GradientDrawable.OVAL:GradientDrawable.RECTANGLE);
            press.setCornerRadius(mCornerRadius);
            press.setColor(mBgColorPress);

            GradientDrawable normal = new GradientDrawable();
            normal.setShape(mIsRound?GradientDrawable.OVAL:GradientDrawable.RECTANGLE);
            normal.setCornerRadius(mCornerRadius);
            normal.setColor(mBgColorNormal);

            //res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(mBgColorPress));
            //res.addState(new int[]{}, new ColorDrawable(mBgColorNormal));

            res.addState(new int[]{android.R.attr.state_pressed}, press);
            res.addState(new int[]{}, normal);
            this.setBackground(res);
        }
    }

    private String mixColor(String color1, String color2) {
        int[] c1 = new int[4];
        int[] c2 = new int[4];
        int[] c3 = new int[4];

        c1[3] = Integer.parseInt(color1.substring(7), 16);
        c1[2] = Integer.parseInt(color1.substring(5, 7), 16);
        c1[1] = Integer.parseInt(color1.substring(3, 5), 16);
        c1[0] = Integer.parseInt(color1.substring(1, 3), 16);

        c2[3] = Integer.parseInt(color2.substring(7), 16);
        c2[2] = Integer.parseInt(color2.substring(5, 7), 16);
        c2[1] = Integer.parseInt(color2.substring(3, 5), 16);
        c2[0] = Integer.parseInt(color2.substring(1, 3), 16);

        c3[3] = (c1[3] + c2[3]) / 2;
        c3[2] = (c1[2] + c2[2]) / 2;
        c3[1] = (c1[1] + c2[1]) / 2;
        c3[0] = (c1[0] + c2[0]) / 2;

        return String.format(Locale.KOREA, "#%s%s%s%s", toHexString(c3[0]), toHexString(c3[1]), toHexString(c3[2]), toHexString(c3[3]));
    }

    private String toHexString(int dec) {
        String hex = Integer.toHexString(dec);
        return hex.length()==1?"0"+hex:hex;
    }

    //@SuppressLint("ClickableViewAccessibility")
    //@Override
    //public boolean onTouchEvent(MotionEvent event) {
    //    if (mIsBgColor) {
    //        switch (event.getAction()) {
    //            case MotionEvent.ACTION_DOWN:
    //                setBackgroundColor(mBgColorPress);
    //                break;
    //            case MotionEvent.ACTION_UP:
    //                setBackgroundColor(mBgColorNormal);
    //                break;
    //        }
    //    }
    //    return super.onTouchEvent(event);
    //}
}
