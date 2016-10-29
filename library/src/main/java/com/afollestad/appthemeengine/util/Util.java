package com.afollestad.appthemeengine.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.WindowDecorActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.afollestad.appthemeengine.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class Util {

    public static int adjustAlpha(@ColorInt int color, @FloatRange(from = 0.0, to = 1.0) float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static int resolveColor(Context context, @AttrRes int attr) {
        return resolveColor(context, attr, 0);
    }

    public static int resolveColor(Context context, @AttrRes int attr, int fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getColor(0, fallback);
        } finally {
            a.recycle();
        }
    }

    @ColorInt
    public static int shiftColor(@ColorInt int color, @FloatRange(from = 0.0f, to = 2.0f) float by) {
        if (by == 1f) return color;
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= by; // value component
        return Color.HSVToColor(hsv);
    }

    @ColorInt
    public static int darkenColor(@ColorInt int color) {
        return shiftColor(color, 0.9f);
    }

    public static boolean isColorLight(@ColorInt int color) {
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }

    @Nullable
    public static Toolbar getSupportActionBarView(@Nullable ActionBar ab) {
        if (ab == null || !(ab instanceof WindowDecorActionBar)) return null;
        try {
            WindowDecorActionBar decorAb = (WindowDecorActionBar) ab;
            Field field = WindowDecorActionBar.class.getDeclaredField("mDecorToolbar");
            field.setAccessible(true);
            ToolbarWidgetWrapper wrapper = (ToolbarWidgetWrapper) field.get(decorAb);
            field = ToolbarWidgetWrapper.class.getDeclaredField("mToolbar");
            field.setAccessible(true);
            return (Toolbar) field.get(wrapper);
        } catch (Throwable t) {
            throw new RuntimeException("Failed to retrieve Toolbar from AppCompat support ActionBar: " + t.getMessage(), t);
        }
    }

    public static void setOverflowButtonColor(@NonNull Activity activity, final @ColorInt int color) {
        final String overflowDescription = activity.getString(R.string.abc_action_menu_overflow_description);
        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ArrayList<View> outViews = new ArrayList<>();
                decorView.findViewsWithText(outViews, overflowDescription,
                        View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                if (outViews.isEmpty()) return;
                final AppCompatImageView overflow = (AppCompatImageView) outViews.get(0);
                TintHelper.tintDrawable(overflow.getDrawable(), color);
                removeOnGlobalLayoutListener(decorView, this);
            }
        });
    }

    @SuppressWarnings("deprecation")
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }

    @SuppressWarnings("deprecation")
    public static void setBackgroundCompat(@NonNull View view, @Nullable Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else view.setBackgroundDrawable(drawable);
    }

    public static int stripAlpha(@ColorInt int color) {
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
    }

    private Util() {
    }
}