package com.afollestad.appthemeengine.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.R;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEColorPreference extends Preference {

    private View mView;
    private int color;
    private int border;

    public ATEColorPreference(Context context) {
        this(context, null, 0);
        init(context, null);
    }

    public ATEColorPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public ATEColorPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private String mKey;

    private void init(Context context, AttributeSet attrs) {
        setLayoutResource(R.layout.ate_preference_custom);
        setWidgetLayoutResource(R.layout.ate_preference_color);
        setPersistent(false);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEColorPreference, 0, 0);
            try {
                mKey = a.getString(R.styleable.ATEColorPreference_ateKey_pref_color);
            } finally {
                a.recycle();
            }
        }

        if (!Config.usingMaterialDialogs(context, mKey)) {
            ATE.config(context, mKey)
                    .usingMaterialDialogs(true)
                    .commit();
        }
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mView = view;
        ATE.apply(view, mKey);
        invalidateColor();
    }

    public void setColor(int color, int border) {
        this.color = color;
        this.border = border;
        invalidateColor();
    }

    private void invalidateColor() {
        if (mView != null) {
            BorderCircleView circle = (BorderCircleView) mView.findViewById(R.id.circle);
            if (this.color != 0) {
                circle.setVisibility(View.VISIBLE);
                circle.setBackgroundColor(color);
                circle.setBorderColor(border);
            } else {
                circle.setVisibility(View.GONE);
            }
        }
    }
}