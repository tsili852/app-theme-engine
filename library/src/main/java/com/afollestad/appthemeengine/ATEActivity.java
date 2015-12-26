package com.afollestad.appthemeengine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEActivity extends AppCompatActivity {

    private long updateTime = -1;

    @Nullable
    protected String getATEKey() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ATE.preApply(this, getATEKey());
        super.onCreate(savedInstanceState);
        updateTime = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ATE.apply(this, getATEKey());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ATE.didValuesChange(this, updateTime, getATEKey()))
            recreate();
    }
}