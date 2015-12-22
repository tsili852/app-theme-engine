package com.afollestad.appthemeengine;

/**
 * @author Aidan Follestad (afollestad)
 */
abstract class ConfigBase implements ConfigInterface {

    protected final static String CONFIG_PREFS_KEY_DEFAULT = "[[afollestad_app-theme-engine]]";
    protected final static String CONFIG_PREFS_KEY_CUSTOM = "[[afollestad_app-theme-engine_%s]]";
    protected final static String IS_CONFIGURED_KEY = "is_configured";
    protected final static String VALUES_CHANGED = "values_changed";
    protected final static String MARK_CHANGED = "mark_changed";

    protected final static String KEY_ACTIVITY_THEME = "activity_theme";

    protected final static String KEY_PRIMARY_COLOR = "primary_color";
    protected final static String KEY_PRIMARY_COLOR_DARK = "primary_color_dark";
    protected final static String KEY_ACCENT_COLOR = "accent_color";
    protected final static String KEY_STATUS_BAR_COLOR = "status_bar_color";
    protected final static String KEY_NAVIGATION_BAR_COLOR = "navigation_bar_color";

    protected final static String KEY_TEXT_COLOR_PRIMARY = "text_color_primary";
    protected final static String KEY_TEXT_COLOR_SECONDARY = "text_color_secondary";

    protected final static String KEY_APPLY_PRIMARYDARK_STATUSBAR = "apply_primarydark_statusbar";
    protected final static String KEY_APPLY_PRIMARY_SUPPORTAB = "apply_primary_supportab";
    protected final static String KEY_APPLY_PRIMARY_NAVBAR = "apply_primary_navbar";
    protected final static String KEY_AUTO_GENERATE_PRIMARYDARK = "auto_generate_primarydark";

    protected final static String KEY_THEMED_NAVIGATION_VIEW = "apply_navigation_view";
    protected final static String KEY_NAVIGATIONVIEW_SELECTED_TEXT = "navigation_view_selected_text";
    protected final static String KEY_NAVIGATIONVIEW_NORMAL_TEXT = "navigation_view_normal_text";
    protected final static String KEY_NAVIGATIONVIEW_SELECTED_ICON = "navigation_view_selected_icon";
    protected final static String KEY_NAVIGATIONVIEW_NORMAL_ICON = "navigation_view_normal_icon";
}