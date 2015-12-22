package com.afollestad.appthemeenginesample;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.afollestad.appthemeengine.ATE;

/**
 * @author Aidan Follestad (afollestad)
 */
public class AccentHeaderView extends FrameLayout {

    public AccentHeaderView(Context context) {
        super(context);
        init(context);
    }

    public AccentHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AccentHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AccentHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setTag("bg_accent_color");
        ATE.apply(context, this, resolveString(context, R.attr.ate_key));
    }

    public static String resolveString(Context context, @AttrRes int attr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getString(0);
        } finally {
            a.recycle();
        }
    }
}
