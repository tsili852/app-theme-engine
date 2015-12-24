package com.afollestad.appthemeenginesample.prefs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeenginesample.R;
import com.afollestad.appthemeenginesample.Util;
import com.afollestad.materialdialogs.prefs.MaterialListPreference;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEListPreference extends MaterialListPreference {

    public ATEListPreference(Context context) {
        super(context);
    }

    public ATEListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ATEListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ATEListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        ATE.apply(view, Util.resolveString(view.getContext(), R.attr.ate_key));
    }
}
