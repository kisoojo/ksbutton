package com.zenoation.library;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by kisoojo on 2021.05.06
 */
public class KSCalendar extends LinearLayout {

    private final static int TYPE_TODAY = 1;
    public final static int TYPE_MORNING = 2;
    public final static int TYPE_AFTERNOON = 3;
    public final static int TYPE_ALL_DAY = 4;

    private final int COLUMN_HEIGHT = 60;

    private ImageView mIvPrev, mIvNext;
    private TextView mTvTitle;
    private LinearLayout mLlCalendar;

    private Calendar mCalendar;

    private ArrayList<LinearLayout> mLlRow;
    private ArrayList<TextView> mTvDay;

    private String mTodayStr;
    private int mStartIdx = 0;
    private int mHeight = 0;

    private int mPadding = 0;
    private int mSizeToday, mSizeSchedule;

    private OnClickParamListener mOnClickPrevListener;
    private OnClickParamListener mOnClickNextListener;
    private OnClickParamListener mOnClickDayListener;


    public KSCalendar(Context context) {
        super(context);
        initialize();
    }

    public KSCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public KSCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public void setOnClickPrevListener(OnClickParamListener listener) {
        mOnClickPrevListener = listener;
    }

    public void setOnClickNextListener(OnClickParamListener listener) {
        mOnClickNextListener = listener;
    }

    public void setOnClickDayListener(OnClickParamListener listener) {
        mOnClickDayListener = listener;
    }

    private void setWidth() {
        int width = Utils.getInstance().getDisplayWidth((Activity) getContext());
        measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
        width = getMeasuredWidth();
        mSizeToday = width / 7;
        mSizeToday -= mPadding;
        mSizeSchedule = mSizeToday;
        mSizeSchedule -= mPadding * 2;
    }

    private void initialize() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_kcalendar, null);
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mIvPrev = v.findViewById(R.id.iv_prev);
        mIvNext = v.findViewById(R.id.iv_next);
        mTvTitle = v.findViewById(R.id.tv_title);
        mLlCalendar = v.findViewById(R.id.ll_calendar);
        mIvPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMonth(false);
                if (mOnClickPrevListener != null) {
                    String ym = String.format(Locale.KOREA, "%04d-%02d", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1);
                    mOnClickPrevListener.onClick(ym);
                }
            }
        });
        mIvNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMonth(true);
                if (mOnClickNextListener != null) {
                    String ym = String.format(Locale.KOREA, "%04d-%02d", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1);
                    mOnClickNextListener.onClick(ym);
                }
            }
        });
        addView(v);

        mCalendar = Calendar.getInstance();
        mLlRow = new ArrayList<>();
        mTvDay = new ArrayList<>();

        mTodayStr = String.format(Locale.KOREA, "%d%d%d", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));

        mPadding = Utils.getInstance().getPxFromDp(getContext(), 5);

        setWidth();

        makeCalendar();
    }

    private void changeMonth(boolean isNext) {
        removeCalenderViews();
        mCalendar.add(Calendar.MONTH, isNext ? 1 : -1);
        makeCalendar();
    }

    private void removeCalenderViews() {
        mLlCalendar.removeAllViews();
        mLlRow.clear();
        mTvDay.clear();
    }

    private void makeCalendar() {
        mHeight = Utils.getInstance().getPxFromDp(getContext(), COLUMN_HEIGHT);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        String title = String.format(Locale.KOREA, "%d년 %d월", year, month + 1);
        mTvTitle.setText(title);

        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.DAY_OF_MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int maxDay = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int i = 0;
        int row = 0;
        int day = 1;
        int date = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        boolean isAddDay = false;
        boolean isCheckToday = false;
        while (true) {
            //줄바뀜
            if (i % 7 == 0) {
                //마지막날 지나면 더 추가 안함
                if (day > maxDay) {
                    break;
                }

                addRow();
                row++;
            }

            //날짜추가 아니고, 요일 같으면 날짜 추가 시작
            if (!isAddDay && date == i) {
                isAddDay = true;
                mStartIdx = i;
            }
            //날짜추가 이고, 마지막날 지나면 날짜 추가 중지
            else if (isAddDay && day > maxDay) {
                isAddDay = false;
            }

            addColumn(row - 1, isAddDay ? day : 0, date);

            if (isAddDay) {
                //오늘 적용
                if (!isCheckToday) {
                    String dateStr = String.format(Locale.KOREA, "%d%d%d", year, month + 1, day);
                    if (TextUtils.equals(dateStr, mTodayStr)) {
                        setSchedule(day, TYPE_TODAY);
                        isCheckToday = true;
                    }
                }

                date++;
                date %= 7;
                day++;
            }

            i++;
        }

        requestLayout();
        // invalidate();
    }

    private void addRow() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER);
        mLlCalendar.addView(linearLayout);
        mLlRow.add(linearLayout);
    }

    private void addColumn(int row, int day, int date) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER);

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params2);
        textView.setGravity(Gravity.CENTER);
        textView.setText(day == 0 ? "" : String.valueOf(day));
        if (date == 0) {
            textView.setTextColor(getResources().getColor(R.color.red));
        } else if (date == 6) {
            textView.setTextColor(getResources().getColor(R.color.blue));
        } else {
            textView.setTextColor(getResources().getColor(R.color.black));
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickDayListener != null && v.getTag() != null) {
                    String ymd = String.format(Locale.KOREA, "%04d-%02d-%02d", mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) + 1, mCalendar.get(Calendar.DAY_OF_MONTH));
                    mOnClickDayListener.onClick(ymd);
                }
            }
        });

        linearLayout.addView(textView);
        mLlRow.get(row).addView(linearLayout);
        mTvDay.add(textView);
    }

    public void setSchedule(int day, int type) {
        TextView textView = mTvDay.get(mStartIdx + day - 1);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        if (type == TYPE_TODAY) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.circle_calendar_today);
            params.width = mSizeToday;
            params.height = mSizeToday;
            textView.setLayoutParams(params);
            textView.setTag(null);
        } else if (type == TYPE_MORNING) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.circle_calendar_morning);
            params.width = mSizeSchedule;
            params.height = mSizeSchedule;
            textView.setLayoutParams(params);
            textView.setTag(TYPE_MORNING);
        } else if (type == TYPE_AFTERNOON) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.circle_calendar_afternoon);
            params.width = mSizeSchedule;
            params.height = mSizeSchedule;
            textView.setLayoutParams(params);
            textView.setTag(TYPE_AFTERNOON);
        } else if (type == TYPE_ALL_DAY) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.circle_calendar_all_day);
            params.width = mSizeSchedule;
            params.height = mSizeSchedule;
            textView.setLayoutParams(params);
            textView.setTag(TYPE_ALL_DAY);
        }
    }
}
