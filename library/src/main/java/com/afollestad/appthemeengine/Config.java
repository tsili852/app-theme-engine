package com.afollestad.appthemeengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.afollestad.appthemeengine.customizers.ATENavigationBarCustomizer;
import com.afollestad.appthemeengine.customizers.ATEStatusBarCustomizer;
import com.afollestad.appthemeengine.util.Util;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class Config extends ConfigBase {

    private final Context mContext;
    private final String mKey;
    private final SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    protected Config(@NonNull Context context, @Nullable String key) {
        mContext = context;
        if (key == null)
            mKey = getKey(context);
        else
            mKey = key;
        mEditor = prefs(context, key).edit();
    }

    @CheckResult
    @Override
    public boolean isConfigured() {
        return prefs(mContext, mKey).getBoolean(IS_CONFIGURED_KEY, false);
    }

    @Override
    public Config activityTheme(@StyleRes int theme) {
        mEditor.putInt(KEY_ACTIVITY_THEME, theme);
        return this;
    }

    @Override
    public Config primaryColor(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR, color);
        if (autoGeneratePrimaryDark(mContext, mKey))
            primaryColorDark(Util.darkenColor(color));
        return this;
    }

    @Override
    public Config primaryColorRes(@ColorRes int colorRes) {
        return primaryColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config primaryColorAttr(@AttrRes int colorAttr) {
        return primaryColor(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config primaryColorDark(@ColorInt int color) {
        mEditor.putInt(KEY_PRIMARY_COLOR_DARK, color);
        return this;
    }

    @Override
    public Config primaryColorDarkRes(@ColorRes int colorRes) {
        return primaryColorDark(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config primaryColorDarkAttr(@AttrRes int colorAttr) {
        return primaryColorDark(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config accentColor(@ColorInt int color) {
        mEditor.putInt(KEY_ACCENT_COLOR, color);
        return this;
    }

    @Override
    public Config accentColorRes(@ColorRes int colorRes) {
        return accentColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config accentColorAttr(@AttrRes int colorAttr) {
        return accentColor(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config statusBarColor(@ColorInt int color) {
        mEditor.putInt(KEY_STATUS_BAR_COLOR, color);
        return this;
    }

    @Override
    public Config statusBarColorRes(@ColorRes int colorRes) {
        return statusBarColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config statusBarColorAttr(@AttrRes int colorAttr) {
        return statusBarColor(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationBarColor(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATION_BAR_COLOR, color);
        return this;
    }

    @Override
    public Config navigationBarColorRes(@ColorRes int colorRes) {
        return navigationBarColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationBarColorAttr(@AttrRes int colorAttr) {
        return navigationBarColor(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config textColorPrimary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_PRIMARY, color);
        return this;
    }

    @Override
    public Config textColorPrimaryRes(@ColorRes int colorRes) {
        return textColorPrimary(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config textColorPrimaryAttr(@AttrRes int colorAttr) {
        return textColorPrimary(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config textColorSecondary(@ColorInt int color) {
        mEditor.putInt(KEY_TEXT_COLOR_SECONDARY, color);
        return this;
    }

    @Override
    public Config textColorSecondaryRes(@ColorRes int colorRes) {
        return textColorSecondary(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config textColorSecondaryAttr(@AttrRes int colorAttr) {
        return textColorSecondary(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config coloredStatusBar(boolean colored) {
        mEditor.putBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, colored);
        return this;
    }

    @Override
    public Config coloredActionBar(boolean applyToActionBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, applyToActionBar);
        return this;
    }

    @Override
    public Config coloredNavigationBar(boolean applyToNavBar) {
        mEditor.putBoolean(KEY_APPLY_PRIMARY_NAVBAR, applyToNavBar);
        return this;
    }

    @Override
    public Config autoGeneratePrimaryDark(boolean autoGenerate) {
        mEditor.putBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, autoGenerate);
        return this;
    }

    @Override
    public Config navigationViewThemed(boolean themed) {
        mEditor.putBoolean(KEY_THEMED_NAVIGATION_VIEW, themed);
        return this;
    }

    @Override
    public Config navigationViewSelectedIcon(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_SELECTED_ICON, color);
        return this;
    }

    @Override
    public Config navigationViewSelectedIconRes(@ColorRes int colorRes) {
        return navigationViewSelectedIcon(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewSelectedIconAttr(@AttrRes int colorAttr) {
        return navigationViewSelectedIcon(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationViewSelectedText(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_SELECTED_TEXT, color);
        return this;
    }

    @Override
    public Config navigationViewSelectedTextRes(@ColorRes int colorRes) {
        return navigationViewSelectedText(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewSelectedTextAttr(@AttrRes int colorAttr) {
        return navigationViewSelectedText(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationViewNormalIcon(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_NORMAL_ICON, color);
        return this;
    }

    @Override
    public Config navigationViewNormalIconRes(@ColorRes int colorRes) {
        return navigationViewNormalIcon(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewNormalIconAttr(@AttrRes int colorAttr) {
        return navigationViewNormalIcon(Util.resolveColor(mContext, colorAttr));
    }

    @Override
    public Config navigationViewNormalText(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_NORMAL_TEXT, color);
        return this;
    }

    @Override
    public Config navigationViewNormalTextRes(@ColorRes int colorRes) {
        return navigationViewNormalText(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewNormalTextAttr(@AttrRes int colorAttr) {
        return navigationViewNormalText(Util.resolveColor(mContext, colorAttr));
    }

    // Apply and commit methods

    @Override
    public void commit() {
        mEditor.putLong(VALUES_CHANGED, System.currentTimeMillis())
                .putBoolean(IS_CONFIGURED_KEY, true)
                .commit();
    }

    @Override
    public void apply(@NonNull Activity activity) {
        commit();
        ATE.apply(activity, mKey);
    }

    @Override
    public void apply(@NonNull android.support.v4.app.Fragment fragment) {
        commit();
        ATE.apply(fragment, mKey);
    }

    @Override
    public void apply(@NonNull android.app.Fragment fragment) {
        commit();
        ATE.apply(fragment, mKey);
    }

    @Override
    public void apply(@NonNull View view) {
        commit();
        ATE.apply(view.getContext(), view, mKey);
    }

    // Static getters

    @CheckResult
    @NonNull
    protected static SharedPreferences prefs(@NonNull Context context, @Nullable String key) {
        return context.getSharedPreferences(
                key != null ? String.format(CONFIG_PREFS_KEY_CUSTOM, key) : CONFIG_PREFS_KEY_DEFAULT,
                Context.MODE_PRIVATE);
    }

    @Nullable
    protected static String getKey(@NonNull Context context) {
        if (context instanceof ATEActivity)
            return ((ATEActivity) context).getATEKey();
        return null;
    }

    @Deprecated
    @CheckResult
    @StyleRes
    public static int activityTheme(@NonNull Context context) {
        return activityTheme(context, getKey(context));
    }

    @CheckResult
    @StyleRes
    public static int activityTheme(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_ACTIVITY_THEME, 0);
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int primaryColor(@NonNull Context context) {
        return prefs(context, getKey(context)).getInt(KEY_PRIMARY_COLOR, Util.resolveColor(context, R.attr.colorPrimary, Color.parseColor("#455A64")));
    }

    @CheckResult
    @ColorInt
    public static int primaryColor(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_PRIMARY_COLOR, Util.resolveColor(context, R.attr.colorPrimary, Color.parseColor("#455A64")));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int primaryColorDark(@NonNull Context context) {
        return primaryColorDark(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int primaryColorDark(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_PRIMARY_COLOR_DARK, Util.resolveColor(context, R.attr.colorPrimaryDark, Color.parseColor("#37474F")));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int accentColor(@NonNull Context context) {
        return accentColor(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int accentColor(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_ACCENT_COLOR, Util.resolveColor(context, R.attr.colorAccent, Color.parseColor("#263238")));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int statusBarColor(@NonNull Context context) {
        return statusBarColor(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int statusBarColor(@NonNull Context context, @Nullable String key) {
        if (context instanceof ATEStatusBarCustomizer)
            return ((ATEStatusBarCustomizer) context).getStatusBarColor();
        return prefs(context, key).getInt(KEY_STATUS_BAR_COLOR, primaryColorDark(context, key));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int navigationBarColor(@NonNull Context context) {
        return navigationBarColor(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int navigationBarColor(@NonNull Context context, @Nullable String key) {
        if (context instanceof ATENavigationBarCustomizer)
            return ((ATENavigationBarCustomizer) context).getNavigationBarColor();
        return prefs(context, key).getInt(KEY_NAVIGATION_BAR_COLOR, primaryColor(context, key));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int textColorPrimary(@NonNull Context context) {
        return textColorPrimary(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int textColorPrimary(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_TEXT_COLOR_PRIMARY, Util.resolveColor(context, android.R.attr.textColorPrimary));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int textColorSecondary(@NonNull Context context) {
        return textColorSecondary(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int textColorSecondary(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_TEXT_COLOR_SECONDARY, Util.resolveColor(context, android.R.attr.textColorSecondary));
    }

    @Deprecated
    @CheckResult
    public static boolean coloredStatusBar(@NonNull Context context) {
        return coloredStatusBar(context, getKey(context));
    }

    @CheckResult
    public static boolean coloredStatusBar(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, true);
    }

    @Deprecated
    @CheckResult
    public static boolean coloredActionBar(@NonNull Context context) {
        return coloredActionBar(context, getKey(context));
    }

    @CheckResult
    public static boolean coloredActionBar(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, true);
    }

    @Deprecated
    @CheckResult
    public static boolean coloredNavigationBar(@NonNull Context context) {
        return coloredNavigationBar(context, getKey(context));
    }

    @CheckResult
    public static boolean coloredNavigationBar(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_APPLY_PRIMARY_NAVBAR, false);
    }

    @Deprecated
    @CheckResult
    public static boolean autoGeneratePrimaryDark(@NonNull Context context) {
        return autoGeneratePrimaryDark(context, getKey(context));
    }

    @CheckResult
    public static boolean autoGeneratePrimaryDark(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, true);
    }

    @Deprecated
    @CheckResult
    public static boolean navigationViewThemed(@NonNull Context context) {
        return navigationViewThemed(context, getKey(context));
    }

    @CheckResult
    public static boolean navigationViewThemed(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_THEMED_NAVIGATION_VIEW, true);
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int navigationViewSelectedIcon(@NonNull Context context) {
        return navigationViewSelectedIcon(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int navigationViewSelectedIcon(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_SELECTED_ICON, accentColor(context, key));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int navigationViewSelectedText(@NonNull Context context) {
        return navigationViewSelectedText(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int navigationViewSelectedText(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_SELECTED_TEXT, accentColor(context, key));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int navigationViewNormalIcon(@NonNull Context context) {
        return navigationViewNormalIcon(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int navigationViewNormalIcon(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_NORMAL_ICON, textColorSecondary(context, key));
    }

    @Deprecated
    @CheckResult
    @ColorInt
    public static int navigationViewNormalText(@NonNull Context context) {
        return navigationViewNormalText(context, getKey(context));
    }

    @CheckResult
    @ColorInt
    public static int navigationViewNormalText(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_NORMAL_TEXT, textColorPrimary(context, key));
    }
}