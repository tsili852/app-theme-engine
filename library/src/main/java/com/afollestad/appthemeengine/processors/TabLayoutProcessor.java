package com.afollestad.appthemeengine.processors;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.TintHelper;
import com.afollestad.appthemeengine.util.Util;

/**
 * @author Aidan Follestad (afollestad)
 */
public class TabLayoutProcessor implements Processor<TabLayout, Void> {

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable TabLayout view, @Nullable Void extra) {
        if (view == null)
            return;
        if (view.getTag() == null || !(view.getTag() instanceof String)) {
            processTagPart(context, view, null, key);
            return;
        }

        final String tag = (String) view.getTag();
        if (tag.contains(",")) {
            final String[] splitTag = tag.split(",");
            for (String part : splitTag)
                processTagPart(context, view, part, key);
        } else {
            processTagPart(context, view, tag, key);
        }
    }

    private static void processTagPart(@NonNull Context context, @NonNull TabLayout view, @Nullable String tag, @Nullable String key) {
        int tabTextColorSelected = Color.WHITE, tabIndicatorColorSelected = Color.WHITE;

        Drawable bg = view.getBackground();
        if (view.getParent() != null && view.getParent() instanceof AppBarLayout &&
                ((AppBarLayout) view.getParent()).getBackground() instanceof ColorDrawable) {
            bg = ((AppBarLayout) view.getParent()).getBackground();
        }

        if (bg != null && bg instanceof ColorDrawable) {
            final ColorDrawable cd = (ColorDrawable) view.getBackground();
            if (Util.isColorLight(cd.getColor()))
                tabTextColorSelected = tabIndicatorColorSelected = Color.BLACK;
        }

        if (tag != null) {
            switch (tag) {
                case KEY_TAB_TEXT_PRIMARY_COLOR:
                    tabTextColorSelected = Config.primaryColor(context, key);
                    break;
                case KEY_TAB_TEXT_PRIMARY_COLOR_DARK:
                    tabTextColorSelected = Config.primaryColorDark(context, key);
                    break;
                case KEY_TAB_TEXT_ACCENT_COLOR:
                    tabTextColorSelected = Config.accentColor(context, key);
                    break;
                case KEY_TAB_TEXT_PRIMARY:
                    tabTextColorSelected = Config.textColorPrimary(context, key);
                    break;
                case KEY_TAB_TEXT_PRIMARY_INVERSE:
                    tabTextColorSelected = Config.textColorPrimaryInverse(context, key);
                    break;
                case KEY_TAB_TEXT_SECONDARY:
                    tabTextColorSelected = Config.textColorSecondary(context, key);
                    break;
                case KEY_TAB_TEXT_SECONDARY_INVERSE:
                    tabTextColorSelected = Config.textColorSecondaryInverse(context, key);
                    break;

                case KEY_TAB_INDICATOR_PRIMARY_COLOR:
                    tabIndicatorColorSelected = Config.primaryColor(context, key);
                    break;
                case KEY_TAB_INDICATOR_PRIMARY_COLOR_DARK:
                    tabIndicatorColorSelected = Config.primaryColorDark(context, key);
                    break;
                case KEY_TAB_INDICATOR_ACCENT_COLOR:
                    tabIndicatorColorSelected = Config.accentColor(context, key);
                    break;
                case KEY_TAB_INDICATOR_TEXT_PRIMARY:
                    tabIndicatorColorSelected = Config.textColorPrimary(context, key);
                    break;
                case KEY_TAB_INDICATOR_TEXT_PRIMARY_INVERSE:
                    tabIndicatorColorSelected = Config.textColorPrimaryInverse(context, key);
                    break;
                case KEY_TAB_INDICATOR_TEXT_SECONDARY:
                    tabIndicatorColorSelected = Config.textColorSecondary(context, key);
                    break;
                case KEY_TAB_INDICATOR_TEXT_SECONDARY_INVERSE:
                    tabIndicatorColorSelected = Config.textColorSecondaryInverse(context, key);
                    break;
            }
        }

        view.setTabTextColors(Util.adjustAlpha(tabTextColorSelected, 0.35f), tabTextColorSelected);
        view.setSelectedTabIndicatorColor(tabIndicatorColorSelected);

        final ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_selected},
                new int[]{android.R.attr.state_selected}
        },
                new int[]{
                        Util.adjustAlpha(tabIndicatorColorSelected, 0.35f),
                        tabIndicatorColorSelected
                });
        for (int i = 0; i < view.getTabCount(); i++) {
            final TabLayout.Tab tab = view.getTabAt(i);
            if (tab != null && tab.getIcon() != null) {
                TintHelper.tintDrawable(tab.getIcon(), sl);
            }
        }
    }

    private final static String KEY_TAB_TEXT_PRIMARY_COLOR = "tab_text_primary_color";
    private final static String KEY_TAB_TEXT_PRIMARY_COLOR_DARK = "tab_text_primary_color_dark";
    private final static String KEY_TAB_TEXT_ACCENT_COLOR = "tab_text_accent_color";
    private final static String KEY_TAB_TEXT_PRIMARY = "tab_text_primary";
    private final static String KEY_TAB_TEXT_PRIMARY_INVERSE = "tab_text_primary_inverse";
    private final static String KEY_TAB_TEXT_SECONDARY = "tab_text_secondary";
    private final static String KEY_TAB_TEXT_SECONDARY_INVERSE = "tab_text_secondary_inverse";

    private final static String KEY_TAB_INDICATOR_PRIMARY_COLOR = "tab_indicator_primary_color";
    private final static String KEY_TAB_INDICATOR_PRIMARY_COLOR_DARK = "tab_indicator_primary_color_dark";
    private final static String KEY_TAB_INDICATOR_ACCENT_COLOR = "tab_indicator_accent_color";
    private final static String KEY_TAB_INDICATOR_TEXT_PRIMARY = "tab_indicator_text_primary";
    private final static String KEY_TAB_INDICATOR_TEXT_PRIMARY_INVERSE = "tab_indicator_text_primary_inverse";
    private final static String KEY_TAB_INDICATOR_TEXT_SECONDARY = "tab_indicator_text_secondary";
    private final static String KEY_TAB_INDICATOR_TEXT_SECONDARY_INVERSE = "tab_indicator_text_secondary_inverse";
}