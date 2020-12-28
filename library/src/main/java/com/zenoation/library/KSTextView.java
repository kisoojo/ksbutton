package com.zenoation.library;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by kisoojo on 2020.12.24
 */
public class KSTextView extends AppCompatTextView {
    private float mWidth, mHeight;

    public KSTextView(@NonNull Context context) {
        super(context);
        mWidth = 0;
        mHeight = 0;
    }

    public KSTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onCreate(context, attrs);
    }

    public KSTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(context, attrs);
    }

    private void onCreate(Context context, AttributeSet attrs) {
        mWidth = context.obtainStyledAttributes(attrs, R.styleable.KSTextViewAttr).getDimension(R.styleable.KSTextViewAttr_drawableWidth, 0);
        mHeight = context.obtainStyledAttributes(attrs, R.styleable.KSTextViewAttr).getDimension(R.styleable.KSTextViewAttr_drawableHeight, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setDrawableSize();
    }

    private void setDrawableSize() {
        if (mWidth == 0 && mHeight == 0) {
            return;
        }

        float width = mWidth;
        float height = mHeight;

        Drawable[] drawables = getCompoundDrawables();
        for (Drawable d : drawables) {
            if (d == null) {
                continue;
            }
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
        }
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }
}
