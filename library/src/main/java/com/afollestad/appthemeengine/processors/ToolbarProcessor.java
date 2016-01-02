package com.afollestad.appthemeengine.processors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.appthemeengine.ATE;
import com.afollestad.appthemeengine.ATEMenuPresenterCallback;
import com.afollestad.appthemeengine.ATEOnMenuItemClickListener;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.TintHelper;
import com.afollestad.appthemeengine.util.Util;

import java.lang.reflect.Field;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ToolbarProcessor implements Processor<Toolbar, Menu> {

    @SuppressWarnings("unchecked")
    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable Toolbar toolbar, @Nullable Menu menu) {
        if (toolbar == null && context instanceof AppCompatActivity)
            toolbar = Util.getSupportActionBarView(((AppCompatActivity) context).getSupportActionBar());
        if (toolbar == null) return;
        final int toolbarColor = Config.toolbarColor(context, key);

        CollapsingToolbarLayout collapsingToolbar = null;
        if (toolbar.getParent() instanceof CollapsingToolbarLayout) {
            collapsingToolbar = (CollapsingToolbarLayout) toolbar.getParent();
            collapsingToolbar.setStatusBarScrimColor(Config.statusBarColor(context, key));
            collapsingToolbar.setContentScrim(new ColorDrawable(toolbarColor));
        } else {
            Util.setBackgroundCompat(toolbar, new ColorDrawable(toolbarColor));
        }


        boolean isLightMode;
        @Config.LightToolbarMode
        final int lightToolbarMode = Config.lightToolbarMode(context, key);
        switch (lightToolbarMode) {
            case Config.LIGHT_TOOLBAR_ON:
                isLightMode = true;
                break;
            case Config.LIGHT_TOOLBAR_OFF:
                isLightMode = false;
                break;
            default:
            case Config.LIGHT_TOOLBAR_AUTO:
                isLightMode = Util.isColorLight(toolbarColor);
                break;
        }

        final int tintColor = isLightMode ? Color.BLACK : Color.WHITE;

        // Tint the toolbar title
        if (collapsingToolbar != null)
            collapsingToolbar.setCollapsedTitleTextColor(tintColor);
        else
            toolbar.setTitleTextColor(tintColor);

        // Tint the toolbar navigation icon (e.g. back, drawer, etc.)
        if (toolbar.getNavigationIcon() != null)
            toolbar.setNavigationIcon(TintHelper.tintDrawable(toolbar.getNavigationIcon(), tintColor));

        // Tint visible action button icons on the toolbar
        if (menu == null) menu = toolbar.getMenu();
        if (menu != null && menu.size() > 0) {
            for (int i = 0; i < menu.size(); i++) {
                final MenuItem item = menu.getItem(i);
                if (item.getIcon() != null)
                    item.setIcon(TintHelper.tintDrawable(item.getIcon(), tintColor));

                // Search view theming
                if (item.getActionView() != null &&
                        (item.getActionView() instanceof android.widget.SearchView ||
                                item.getActionView() instanceof android.support.v7.widget.SearchView)) {
                    Processor processor = ATE.getProcessor(android.support.v7.widget.SearchView.class);
                    if (processor != null)
                        processor.process(context, key, item.getActionView(), tintColor);
                }
            }
        }

        if (context instanceof Activity) {
            // Set color of the overflow icon
            Util.setOverflowButtonColor((Activity) context, tintColor);

            try {
                // Tint immediate overflow menu items
                final Field menuField = Toolbar.class.getDeclaredField("mMenuBuilderCallback");
                menuField.setAccessible(true);
                final Field presenterField = Toolbar.class.getDeclaredField("mActionMenuPresenterCallback");
                presenterField.setAccessible(true);
                final Field menuViewField = Toolbar.class.getDeclaredField("mMenuView");
                menuViewField.setAccessible(true);
                final MenuPresenter.Callback currentPresenterCb = (MenuPresenter.Callback) presenterField.get(toolbar);
                if (!(currentPresenterCb instanceof ATEMenuPresenterCallback)) {
                    final ATEMenuPresenterCallback newPresenterCb = new ATEMenuPresenterCallback(
                            (Activity) context, key, currentPresenterCb, toolbar);
                    final MenuBuilder.Callback currentMenuCb = (MenuBuilder.Callback) menuField.get(toolbar);
                    toolbar.setMenuCallbacks(newPresenterCb, currentMenuCb);
                    ActionMenuView menuView = (ActionMenuView) menuViewField.get(toolbar);
                    if (menuView != null)
                        menuView.setMenuCallbacks(newPresenterCb, currentMenuCb);
                }

                // OnMenuItemClickListener to tint submenu items
                final Field menuItemClickListener = Toolbar.class.getDeclaredField("mOnMenuItemClickListener");
                menuItemClickListener.setAccessible(true);
                Toolbar.OnMenuItemClickListener currentClickListener = (Toolbar.OnMenuItemClickListener) menuItemClickListener.get(toolbar);
                if (!(currentClickListener instanceof ATEOnMenuItemClickListener)) {
                    final ATEOnMenuItemClickListener newClickListener = new ATEOnMenuItemClickListener(
                            (Activity) context, key, currentClickListener, toolbar);
                    toolbar.setOnMenuItemClickListener(newClickListener);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}