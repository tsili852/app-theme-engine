package com.afollestad.appthemeengine;

import android.app.Activity;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

/**
 * @author Aidan Follestad (afollestad)
 */
interface ConfigInterface {

    boolean isConfigured();

    // Primary colors

    Config primaryColor(@ColorInt int color);

    Config primaryColorRes(@ColorRes int colorRes);

    Config primaryColorAttr(@AttrRes int colorAttr);

    Config autoGeneratePrimaryDark(boolean autoGenerate);

    Config primaryColorDark(@ColorInt int color);

    Config primaryColorDarkRes(@ColorRes int colorRes);

    Config primaryColorDarkAttr(@AttrRes int colorAttr);

    // Accent colors

    Config accentColor(@ColorInt int color);

    Config accentColorRes(@ColorRes int colorRes);

    Config accentColorAttr(@AttrRes int colorAttr);

    // Status bar color

    Config statusBarColor(@ColorInt int color);

    Config statusBarColorRes(@ColorRes int colorRes);

    Config statusBarColorAttr(@AttrRes int colorAttr);

    // Primary text color

    Config textColorPrimary(@ColorInt int color);

    Config textColorPrimaryRes(@ColorRes int colorRes);

    Config textColorPrimaryAttr(@AttrRes int colorAttr);

    // Secondary text color

    Config textColorSecondary(@ColorInt int color);

    Config textColorSecondaryRes(@ColorRes int colorRes);

    Config textColorSecondaryAttr(@AttrRes int colorAttr);

    // Toggle configurations

    Config coloredStatusBar(boolean colored);

    Config coloredActionBar(boolean applyToActionBar);

    Config coloredNavigationBar(boolean applyToNavBar);

    Config navigationViewThemed(boolean themed);

    // NavigationView colors

    Config navigationViewSelectedIcon(@ColorInt int color);

    Config navigationViewSelectedIconRes(@ColorRes int colorRes);

    Config navigationViewSelectedIconAttr(@AttrRes int colorAttr);

    Config navigationViewNormalIcon(@ColorInt int color);

    Config navigationViewNormalIconRes(@ColorRes int colorRes);

    Config navigationViewNormalIconAttr(@AttrRes int colorAttr);

    Config navigationViewSelectedText(@ColorInt int color);

    Config navigationViewSelectedTextRes(@ColorRes int colorRes);

    Config navigationViewSelectedTextAttr(@AttrRes int colorAttr);

    Config navigationViewNormalText(@ColorInt int color);

    Config navigationViewNormalTextRes(@ColorRes int colorRes);

    Config navigationViewNormalTextAttr(@AttrRes int colorAttr);

    // Commit/apply

    void commit();

    void apply(@NonNull Activity activity);

    void apply(@NonNull android.support.v4.app.Fragment fragment);

    void apply(@NonNull android.app.Fragment fragment);

    void apply(@NonNull View view);
}
