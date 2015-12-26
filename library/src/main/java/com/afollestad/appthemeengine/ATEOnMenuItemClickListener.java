package com.afollestad.appthemeengine;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * @author Daniel Ciao (plusCubed)
 */
public class ATEOnMenuItemClickListener implements Toolbar.OnMenuItemClickListener {

    private Activity mContext;
    private String mKey;
    private Toolbar.OnMenuItemClickListener mParentListener;
    private Toolbar mToolbar;

    public ATEOnMenuItemClickListener(Activity context, String key, Toolbar.OnMenuItemClickListener parentCb, Toolbar toolbar) {
        mContext = context;
        mKey = key;
        mParentListener = parentCb;
        mToolbar = toolbar;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ATE.applyOverflow(mContext, mKey, mToolbar);
        return mParentListener.onMenuItemClick(item);
    }
}
