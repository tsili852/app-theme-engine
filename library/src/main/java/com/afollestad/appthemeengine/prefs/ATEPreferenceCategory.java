package com.afollestad.appthemeengine.prefs;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.preference.PreferenceCategory;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.R;


public class ATEPreferenceCategory extends PreferenceCategory {
    String ateKey;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ATEPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ateKey = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEPreferenceCategory,0,0).getString(R.styleable.ATEPreferenceCategory_ateKey_prefCategory_textColor);
    }

    public ATEPreferenceCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ateKey = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEPreferenceCategory,0,0).getString(R.styleable.ATEPreferenceCategory_ateKey_prefCategory_textColor);
    }

    public ATEPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        ateKey = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATEPreferenceCategory,0,0).getString(R.styleable.ATEPreferenceCategory_ateKey_prefCategory_textColor);
    }

    public ATEPreferenceCategory(Context context, String ateKey) {
        super(context);
        this.ateKey = ateKey;
    }


    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView mTitle = (TextView) view.findViewById(android.R.id.title);
        mTitle.setTextColor(Config.accentColor(view.getContext(),ateKey));
    }
}
