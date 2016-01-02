package com.afollestad.appthemeengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.afollestad.appthemeengine.customizers.ATENavigationBarCustomizer;
import com.afollestad.appthemeengine.customizers.ATEStatusBarCustomizer;
import com.afollestad.appthemeengine.customizers.ATEToolbarCustomizer;
import com.afollestad.appthemeengine.util.Util;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.afollestad.materialdialogs.util.DialogUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean isConfigured(@IntRange(from = 0, to = Integer.MAX_VALUE) int version) {
        final SharedPreferences prefs = prefs(mContext, mKey);
        final int lastVersion = prefs.getInt(IS_CONFIGURED_VERSION_KEY, -1);
        if (version > lastVersion) {
            prefs.edit().putInt(IS_CONFIGURED_VERSION_KEY, version).commit();
            return false;
        }
        return true;
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
    public Config toolbarColor(@ColorInt int color) {
        mEditor.putInt(KEY_TOOLBAR_COLOR, color);
        return this;
    }

    @Override
    public Config toolbarColorRes(@ColorRes int colorRes) {
        return toolbarColor(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config toolbarColorAttr(@AttrRes int colorAttr) {
        return toolbarColor(Util.resolveColor(mContext, colorAttr));
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
    public Config lightStatusBarMode(@LightStatusBarMode int mode) {
        mEditor.putInt(KEY_LIGHT_STATUS_BAR_MODE, mode);
        return this;
    }

    @Override
    public Config lightToolbarMode(@LightToolbarMode int mode) {
        mEditor.putInt(KEY_LIGHT_TOOLBAR_MODE, mode);
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

    @Override
    public Config navigationViewSelectedBg(@ColorInt int color) {
        mEditor.putInt(KEY_NAVIGATIONVIEW_SELECTED_BG, color);
        return this;
    }

    @Override
    public Config navigationViewSelectedBgRes(@ColorRes int colorRes) {
        return navigationViewSelectedBg(ContextCompat.getColor(mContext, colorRes));
    }

    @Override
    public Config navigationViewSelectedBgAttr(@AttrRes int colorAttr) {
        return navigationViewSelectedBg(DialogUtils.resolveColor(mContext, colorAttr));
    }

    // Misc

    @Override
    public Config usingMaterialDialogs(boolean enabled) {
        mEditor.putBoolean(KEY_USING_MATERIAL_DIALOGS, enabled);
        return this;
    }

    // Apply and commit methods

    @Override
    public void commit() {
        mEditor.putLong(VALUES_CHANGED, System.currentTimeMillis())
                .putBoolean(IS_CONFIGURED_KEY, true)
                .commit();

        // MD integration
        if (Config.usingMaterialDialogs(mContext, mKey)) {
            final ThemeSingleton md = ThemeSingleton.get();
            md.titleColor = Config.textColorPrimary(mContext, mKey);
            md.contentColor = Config.textColorSecondary(mContext, mKey);
            md.itemColor = md.titleColor;
            md.widgetColor = Config.accentColor(mContext, mKey);
            md.linkColor = ColorStateList.valueOf(md.widgetColor);
            md.positiveColor = ColorStateList.valueOf(md.widgetColor);
            md.neutralColor = ColorStateList.valueOf(md.widgetColor);
            md.negativeColor = ColorStateList.valueOf(md.widgetColor);
        }
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

    public static void markChanged(@NonNull Context context, @Nullable String... keys) {
        if (keys == null) {
            new Config(context, null).commit();
        } else {
            for (String key : keys)
                new Config(context, key).commit();
        }
    }

    @Nullable
    protected static String getKey(@NonNull Context context) {
        if (context instanceof ATEActivity)
            return ((ATEActivity) context).getATEKey();
        return null;
    }

    @CheckResult
    @StyleRes
    public static int activityTheme(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_ACTIVITY_THEME, 0);
    }

    @CheckResult
    @ColorInt
    public static int primaryColor(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_PRIMARY_COLOR, Util.resolveColor(context, R.attr.colorPrimary, Color.parseColor("#455A64")));
    }

    @CheckResult
    @ColorInt
    public static int primaryColorDark(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_PRIMARY_COLOR_DARK, Util.resolveColor(context, R.attr.colorPrimaryDark, Color.parseColor("#37474F")));
    }

    @CheckResult
    @ColorInt
    public static int accentColor(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_ACCENT_COLOR, Util.resolveColor(context, R.attr.colorAccent, Color.parseColor("#263238")));
    }

    @CheckResult
    @ColorInt
    public static int statusBarColor(@NonNull Context context, @Nullable String key) {
        if (context instanceof ATEStatusBarCustomizer)
            return ((ATEStatusBarCustomizer) context).getStatusBarColor();
        else if (!coloredStatusBar(context, key))
            return Color.BLACK;
        return prefs(context, key).getInt(KEY_STATUS_BAR_COLOR, primaryColorDark(context, key));
    }

    @CheckResult
    @ColorInt
    public static int toolbarColor(@NonNull Context context, @Nullable String key) {
        if (context instanceof ATEToolbarCustomizer)
            return ((ATEToolbarCustomizer) context).getToolbarColor();
        return prefs(context, key).getInt(KEY_TOOLBAR_COLOR, primaryColor(context, key));
    }

    @CheckResult
    @ColorInt
    public static int navigationBarColor(@NonNull Context context, @Nullable String key) {
        if (context instanceof ATENavigationBarCustomizer)
            return ((ATENavigationBarCustomizer) context).getNavigationBarColor();
        return prefs(context, key).getInt(KEY_NAVIGATION_BAR_COLOR, primaryColor(context, key));
    }

    @CheckResult
    @ColorInt
    public static int textColorPrimary(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_TEXT_COLOR_PRIMARY, Util.resolveColor(context, android.R.attr.textColorPrimary));
    }

    @CheckResult
    @ColorInt
    public static int textColorPrimaryInverse(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_TEXT_COLOR_PRIMARY_INVERSE, Util.resolveColor(context, android.R.attr.textColorPrimaryInverse));
    }

    @CheckResult
    @ColorInt
    public static int textColorSecondary(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_TEXT_COLOR_SECONDARY, Util.resolveColor(context, android.R.attr.textColorSecondary));
    }

    @CheckResult
    @ColorInt
    public static int textColorSecondaryInverse(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_TEXT_COLOR_SECONDARY_INVERSE, Util.resolveColor(context, android.R.attr.textColorSecondaryInverse));
    }

    @CheckResult
    public static boolean coloredStatusBar(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_APPLY_PRIMARYDARK_STATUSBAR, true);
    }

    @CheckResult
    public static boolean coloredActionBar(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_APPLY_PRIMARY_SUPPORTAB, true);
    }

    @CheckResult
    public static boolean coloredNavigationBar(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_APPLY_PRIMARY_NAVBAR, false);
    }

    @SuppressWarnings("ResourceType")
    @CheckResult
    @LightStatusBarMode
    public static int lightStatusBarMode(@NonNull Context context, @Nullable String key) {
        if (context instanceof ATEStatusBarCustomizer)
            return ((ATEStatusBarCustomizer) context).getLightStatusBarMode();
        return prefs(context, key).getInt(KEY_LIGHT_STATUS_BAR_MODE, Config.LIGHT_STATUS_BAR_AUTO);
    }

    @SuppressWarnings("ResourceType")
    @CheckResult
    @LightToolbarMode
    public static int lightToolbarMode(@NonNull Context context, @Nullable String key) {
        if (context instanceof ATEToolbarCustomizer)
            return ((ATEToolbarCustomizer) context).getLightToolbarMode();
        return prefs(context, key).getInt(KEY_LIGHT_TOOLBAR_MODE, Config.LIGHT_TOOLBAR_AUTO);
    }

    @CheckResult
    public static boolean autoGeneratePrimaryDark(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_AUTO_GENERATE_PRIMARYDARK, true);
    }

    @CheckResult
    public static boolean navigationViewThemed(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_THEMED_NAVIGATION_VIEW, true);
    }

    @CheckResult
    @ColorInt
    public static int navigationViewSelectedIcon(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_SELECTED_ICON, accentColor(context, key));
    }

    @CheckResult
    @ColorInt
    public static int navigationViewSelectedText(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_SELECTED_TEXT, accentColor(context, key));
    }

    @CheckResult
    @ColorInt
    public static int navigationViewNormalIcon(@NonNull Context context, @Nullable String key, boolean darkTheme) {
        final int defaultColor = ContextCompat.getColor(context, darkTheme ?
                R.color.ate_navigationview_normalicon_dark : R.color.ate_navigationview_normalicon_light);
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_NORMAL_ICON, defaultColor);
    }

    @CheckResult
    @ColorInt
    public static int navigationViewNormalText(@NonNull Context context, @Nullable String key, boolean darkTheme) {
        final int defaultColor = ContextCompat.getColor(context, darkTheme ?
                R.color.ate_navigationview_normaltext_dark : R.color.ate_navigationview_normaltext_light);
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_NORMAL_TEXT, defaultColor);
    }

    @CheckResult
    @ColorInt
    public static int navigationViewSelectedBg(@NonNull Context context, @Nullable String key, boolean darkTheme) {
        final int defaultColor = ContextCompat.getColor(context, darkTheme ?
                R.color.ate_navigationview_selectedbg_dark : R.color.ate_navigationview_selectedbg_light);
        return prefs(context, key).getInt(KEY_NAVIGATIONVIEW_SELECTED_BG, defaultColor);
    }

    @CheckResult
    public static boolean usingMaterialDialogs(@NonNull Context context, @Nullable String key) {
        return prefs(context, key).getBoolean(KEY_USING_MATERIAL_DIALOGS, false);
    }


    @IntDef({LIGHT_STATUS_BAR_OFF, LIGHT_STATUS_BAR_ON, LIGHT_STATUS_BAR_AUTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LightStatusBarMode {
    }

    @IntDef({LIGHT_TOOLBAR_OFF, LIGHT_TOOLBAR_ON, LIGHT_TOOLBAR_AUTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LightToolbarMode {
    }

    public static final int LIGHT_STATUS_BAR_OFF = 0;
    public static final int LIGHT_STATUS_BAR_ON = 1;
    public static final int LIGHT_STATUS_BAR_AUTO = 2;

    public static final int LIGHT_TOOLBAR_OFF = 0;
    public static final int LIGHT_TOOLBAR_ON = 1;
    public static final int LIGHT_TOOLBAR_AUTO = 2;
}