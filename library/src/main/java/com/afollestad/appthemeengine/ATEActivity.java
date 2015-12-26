package com.afollestad.appthemeengine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ATE.applyOverflow(this, getATEKey());

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        ATE.applyMenu(this, getATEKey(), menu);
        ATE.applyOverflow(this, getATEKey());

        return super.onPrepareOptionsMenu(menu);
    }
}