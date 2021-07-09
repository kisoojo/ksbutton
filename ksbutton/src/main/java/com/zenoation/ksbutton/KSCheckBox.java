package com.zenoation.ksbutton;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;

/**
 * Created by kisoojo on 2020.12.23
 */
public class KSCheckBox extends AppCompatCheckBox {
    private int mDrawableCheckedId, mDrawableUncheckedId;
    private float mWidth, mHeight;
    private int mColor;

    private Drawable mDrawableChecked, mDrawableUnchecked;

    public KSCheckBox(@NonNull Context context) {
        super(context);
    }

    public KSCheckBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onCreate(context, attrs);
    }

    public KSCheckBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(context, attrs);
    }

    private void onCreate(Context context, AttributeSet attrs) {
        mDrawableCheckedId = context.obtainStyledAttributes(attrs, R.styleable.KSCheckBoxAttr).getResourceId(R.styleable.KSCheckBoxAttr_drawableChecked, R.drawable.checkbox_gray);
        mDrawableUncheckedId = context.obtainStyledAttributes(attrs, R.styleable.KSCheckBoxAttr).getResourceId(R.styleable.KSCheckBoxAttr_drawableUnchecked, R.drawable.checkbox_gray);
        mWidth = context.obtainStyledAttributes(attrs, R.styleable.KSTextViewAttr).getDimension(R.styleable.KSTextViewAttr_drawableWidth, 0);
        mHeight = context.obtainStyledAttributes(attrs, R.styleable.KSTextViewAttr).getDimension(R.styleable.KSTextViewAttr_drawableHeight, 0);
        mColor = context.obtainStyledAttributes(attrs, R.styleable.KSCheckBoxAttr).getResourceId(R.styleable.KSCheckBoxAttr_drawableColor, 0);
        if (mWidth == 0 && mHeight == 0) {
            mWidth = Utils.getInstance().getPxFromDp(context, 20);
        }
        this.setBackgroundColor(getResources().getColor(R.color.transparent));
        this.setButtonDrawable(null);
        int dp = getCompoundDrawablePadding();
        if (dp == 0) {
            this.setCompoundDrawablePadding(Utils.getInstance().getPxFromDp(getContext(), 10));
        }

        setDrawable(true);
        setDrawable(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setLayout();
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        requestLayout();
    }

    private void setDrawable(boolean isChecked) {
        float width = mWidth;
        float height = mHeight;

        Drawable d = ContextCompat.getDrawable(getContext(), isChecked ? mDrawableCheckedId : mDrawableUncheckedId);

        float r;
        if (mWidth == 0) {
            r = (float) d.getIntrinsicWidth() / d.getIntrinsicHeight();
            width = (height * r);
        } else if (mHeight == 0) {
            r = (float) d.getIntrinsicHeight() / d.getIntrinsicWidth();
            height = (width * r);
        }
        float w = d.getIntrinsicWidth() / width;
        float h = d.getIntrinsicHeight() / height;
        d.setBounds(0, 0, (int) (d.getIntrinsicWidth() / w), (int) (d.getIntrinsicHeight() / h));

        if (isChecked) {
            if (mColor == 0) {
                mColor = R.color.colorPrimary;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                d.setTint(ContextCompat.getColor(getContext(), mColor));
            } else {
                d.setColorFilter(ContextCompat.getColor(getContext(), mColor), PorterDuff.Mode.SRC_ATOP);
            }
            mDrawableChecked = d;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                d.setTintList(null);
            } else {
                d.setColorFilter(null);
            }
            mDrawableUnchecked = d;
        }
    }

    private void setLayout() {
        if (isChecked()) {
            if (getLayoutDirection() == LAYOUT_DIRECTION_LTR) {
                this.setCompoundDrawables(mDrawableChecked, null, null, null);
            } else {
                this.setCompoundDrawables(null, null, mDrawableChecked, null);
            }
        } else {
            if (getLayoutDirection() == LAYOUT_DIRECTION_LTR) {
                this.setCompoundDrawables(mDrawableUnchecked, null, null, null);
            } else {
                this.setCompoundDrawables(null, null, mDrawableUnchecked, null);
            }
        }
    }
}
