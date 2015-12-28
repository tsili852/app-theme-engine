package com.afollestad.appthemeengine.processors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.appthemeengine.ATEOnMenuItemClickListener;
import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.TintHelper;
import com.afollestad.appthemeengine.util.Util;

import java.lang.reflect.Field;

/**
 * @author Aidan Follestad (afollestad)
 */
public class ToolbarProcessor implements Processor<Toolbar, Menu> {

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable Toolbar toolbar, @Nullable Menu menu) {
        if (toolbar == null && context instanceof AppCompatActivity)
            toolbar = Util.getSupportActionBarView(((AppCompatActivity) context).getSupportActionBar());
        if (toolbar == null) return;

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
                if (toolbar.getBackground() != null && toolbar.getBackground() instanceof ColorDrawable) {
                    final ColorDrawable toolbarBg = (ColorDrawable) toolbar.getBackground();
                    isLightMode = Util.isColorLight(toolbarBg.getColor());
                } else {
                    Log.d("ATE", "Toolbar does not use a ColorDrawable for its background, can't determine its color.");
                    isLightMode = false;
                }
                break;
        }

        final int color = isLightMode ? Color.BLACK : Color.WHITE;

        // Tint the toolbar title and navigation icon (e.g. back, drawer, etc.)
        toolbar.setTitleTextColor(color);
        if (toolbar.getNavigationIcon() != null)
            toolbar.setNavigationIcon(TintHelper.tintDrawable(toolbar.getNavigationIcon(), color));

        // Tint visible action button icons on the toolbar
        if (menu == null) menu = toolbar.getMenu();
        if (menu != null && menu.size() > 0) {
            for (int i = 0; i < menu.size(); i++) {
                final MenuItem item = menu.getItem(i);
                if (item.getIcon() != null)
                    item.setIcon(TintHelper.tintDrawable(item.getIcon(), color));
            }
        }

        if (context instanceof Activity) {
            // Set color of the overflow icon
            Util.setOverflowButtonColor((Activity) context, color);
            // Setup overflow expansion listeners to tint overflow menu widgets
            try {
                /*final Field menuField = Toolbar.class.getDeclaredField("mMenuBuilderCallback");
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
                }*/

                //OnMenuItemClickListener to tint submenu items
                final Field menuItemClickListener = Toolbar.class.getDeclaredField("mOnMenuItemClickListener");
                menuItemClickListener.setAccessible(true);

                Toolbar.OnMenuItemClickListener currentClickListener = (Toolbar.OnMenuItemClickListener) menuItemClickListener.get(toolbar);
                if (!(currentClickListener instanceof ATEOnMenuItemClickListener)) {
                    final ATEOnMenuItemClickListener newClickListener = new ATEOnMenuItemClickListener(
                            (Activity) context, key, currentClickListener, toolbar);
                    toolbar.setOnMenuItemClickListener(newClickListener);
                }


                //TODO: Not working - views aren't inflated yet when overflow is pressed, which makes sense.
                //Need to find another way to tint the menu items after they are inflated
                //Thinking about this a bit more, probably just discard this because this will only work for overflow menus, not other menus

                /*final String overflowDescription = context.getString(R.string.abc_action_menu_overflow_description);
                final ViewGroup decorView = (ViewGroup) ((Activity)context).getWindow().getDecorView();
                final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
                final Toolbar finalToolbar = toolbar;
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        final ArrayList<View> outViews = new ArrayList<>();
                        decorView.findViewsWithText(outViews, overflowDescription,
                                View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                        if (outViews.isEmpty()) return;
                        final AppCompatImageView overflow = (AppCompatImageView) outViews.get(0);

                        try {
                            Field listenerInfoField = View.class.getDeclaredField("mListenerInfo");
                            listenerInfoField.setAccessible(true);
                            Object listenerInfo = listenerInfoField.get(overflow);

                            Field listenerField = listenerInfo.getClass().getDeclaredField("mOnTouchListener");
                            listenerField.setAccessible(true);
                            final View.OnTouchListener listener = (View.OnTouchListener) listenerField.get(listenerInfo);

                            overflow.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    ATE.applyOverflow((AppCompatActivity) context, key, finalToolbar);
                                    return listener.onTouch(v, event);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        Util.removeOnGlobalLayoutListener(decorView, this);
                    }
                });*/


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}