package com.afollestad.appthemeengine;

import android.app.Activity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.Toolbar;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ATEMenuPresenterCallback implements MenuPresenter.Callback {

    public ATEMenuPresenterCallback(Activity context, String key, MenuPresenter.Callback parentCb, Toolbar toolbar) {
        mContext = context;
        mKey = key;
        mParentCb = parentCb;
        mToolbar = toolbar;
    }

    private Activity mContext;
    private String mKey;
    private MenuPresenter.Callback mParentCb;
    private Toolbar mToolbar;

    @Override
    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        if (mParentCb != null)
            mParentCb.onCloseMenu(menu, allMenusAreClosing);
    }

    @Override
    public boolean onOpenSubMenu(MenuBuilder subMenu) {
        if (mParentCb != null)
            mParentCb.onOpenSubMenu(subMenu);
        ATE.applyOverflow(mContext, mKey, mToolbar);
        return true;
    }
}
