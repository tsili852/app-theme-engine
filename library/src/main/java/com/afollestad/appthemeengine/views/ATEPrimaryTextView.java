package com.afollestad.appthemeengine.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEPrimaryTextView extends TextView {

    public ATEPrimaryTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ATEPrimaryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ATEPrimaryTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ATEPrimaryTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setTag("text_primary");
        String key = null;
        if (attrs != null) {
            TypedArray a = null;
            try {
                a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEPrimaryTextView, 0, 0);
                try {
                    key = a.getString(R.styleable.ATEPrimaryTextView_ateKey);
                } finally {
                    a.recycle();
                }
            } finally {
                if (a != null) a.recycle();
            }
        }
        ATE.apply(context, this, key);
    }
}
