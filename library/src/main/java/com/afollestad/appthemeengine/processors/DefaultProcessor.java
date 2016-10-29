package com.afollestad.appthemeengine.processors;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.appthemeengine.Config;
import com.afollestad.appthemeengine.util.TintHelper;
import com.afollestad.appthemeengine.util.Util;

/**
 * @author Aidan Follestad (afollestad)
 */
public class DefaultProcessor implements Processor<View, Void> {

    private boolean isDark;

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable View view, @Nullable Void extra) {
        if (view == null || view.getTag() == null || !(view.getTag() instanceof String))
            return;
        isDark = !Util.isColorLight(Util.resolveColor(view.getContext(), android.R.attr.windowBackground));
        final String tag = (String) view.getTag();
        if (tag.contains(",")) {
            final String[] splitTag = tag.split(",");
            for (String part : splitTag)
                processTagPart(context, view, part, key);
        } else {
            processTagPart(context, view, tag, key);
        }
    }

    private void processTagPart(@NonNull Context context, @NonNull View current, @NonNull String tag, @Nullable String key) {
        switch (tag) {
            case KEY_BG_PRIMARY_COLOR:
                current.setBackgroundColor(Config.primaryColor(context, key));
                break;
            case KEY_BG_PRIMARY_COLOR_DARK:
                current.setBackgroundColor(Config.primaryColorDark(context, key));
                break;
            case KEY_BG_ACCENT_COLOR:
                current.setBackgroundColor(Config.accentColor(context, key));
                break;
            case KEY_BG_TEXT_PRIMARY:
                current.setBackgroundColor(Config.textColorPrimary(context, key));
                break;
            case KEY_BG_TEXT_PRIMARY_INVERSE:
                current.setBackgroundColor(Config.textColorPrimaryInverse(context, key));
                break;
            case KEY_BG_TEXT_SECONDARY:
                current.setBackgroundColor(Config.textColorSecondary(context, key));
                break;
            case KEY_BG_TEXT_SECONDARY_INVERSE:
                current.setBackgroundColor(Config.textColorSecondaryInverse(context, key));
                break;

            case KEY_TEXT_PRIMARY_COLOR:
                ((TextView) current).setTextColor(getTextSelector(Config.primaryColor(context, key), current));
                break;
            case KEY_TEXT_PRIMARY_COLOR_DARK:
                ((TextView) current).setTextColor(getTextSelector(Config.primaryColorDark(context, key), current));
                break;
            case KEY_TEXT_ACCENT_COLOR:
                ((TextView) current).setTextColor(getTextSelector(Config.accentColor(context, key), current));
                break;
            case KEY_TEXT_PRIMARY:
                ((TextView) current).setTextColor(getTextSelector(Config.textColorPrimary(context, key), current));
                break;
            case KEY_TEXT_PRIMARY_INVERSE:
                ((TextView) current).setTextColor(getTextSelector(Config.textColorPrimaryInverse(context, key), current));
                break;
            case KEY_TEXT_SECONDARY:
                ((TextView) current).setTextColor(getTextSelector(Config.textColorSecondary(context, key), current));
                break;
            case KEY_TEXT_SECONDARY_INVERSE:
                ((TextView) current).setTextColor(getTextSelector(Config.textColorSecondaryInverse(context, key), current));
                break;

            case KEY_TEXTLINK_PRIMARY_COLOR:
                ((TextView) current).setLinkTextColor(getTextSelector(Config.primaryColor(context, key), current));
                break;
            case KEY_TEXTLINK_PRIMARY_COLOR_DARK:
                ((TextView) current).setLinkTextColor(getTextSelector(Config.primaryColorDark(context, key), current));
                break;
            case KEY_TEXTLINK_ACCENT_COLOR:
                ((TextView) current).setLinkTextColor(getTextSelector(Config.accentColor(context, key), current));
                break;
            case KEY_TEXTLINK_PRIMARY:
                ((TextView) current).setLinkTextColor(getTextSelector(Config.textColorPrimary(context, key), current));
                break;
            case KEY_TEXTLINK_PRIMARY_INVERSE:
                ((TextView) current).setLinkTextColor(getTextSelector(Config.textColorPrimaryInverse(context, key), current));
                break;
            case KEY_TEXTLINK_SECONDARY:
                ((TextView) current).setLinkTextColor(getTextSelector(Config.textColorSecondary(context, key), current));
                break;
            case KEY_TEXTLINK_SECONDARY_INVERSE:
                ((TextView) current).setLinkTextColor(getTextSelector(Config.textColorSecondaryInverse(context, key), current));
                break;

            case KEY_TEXTSHADOW_PRIMARY_COLOR: {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) return;
                final TextView tv = (TextView) current;
                tv.setShadowLayer(tv.getShadowRadius(), tv.getShadowDx(), tv.getShadowDy(),
                        Config.primaryColor(context, key));
                break;
            }
            case KEY_TEXTSHADOW_PRIMARY_COLOR_DARK: {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) return;
                final TextView tv = (TextView) current;
                tv.setShadowLayer(tv.getShadowRadius(), tv.getShadowDx(), tv.getShadowDy(),
                        Config.primaryColorDark(context, key));
                break;
            }
            case KEY_TEXTSHADOW_ACCENT_COLOR: {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) return;
                final TextView tv = (TextView) current;
                tv.setShadowLayer(tv.getShadowRadius(), tv.getShadowDx(), tv.getShadowDy(),
                        Config.accentColor(context, key));
                break;
            }
            case KEY_TEXTSHADOW_PRIMARY: {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) return;
                final TextView tv = (TextView) current;
                tv.setShadowLayer(tv.getShadowRadius(), tv.getShadowDx(), tv.getShadowDy(),
                        Config.textColorPrimary(context, key));
                break;
            }
            case KEY_TEXTSHADOW_PRIMARY_INVERSE: {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) return;
                final TextView tv = (TextView) current;
                tv.setShadowLayer(tv.getShadowRadius(), tv.getShadowDx(), tv.getShadowDy(),
                        Config.textColorPrimaryInverse(context, key));
                break;
            }
            case KEY_TEXTSHADOW_SECONDARY: {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) return;
                final TextView tv = (TextView) current;
                tv.setShadowLayer(tv.getShadowRadius(), tv.getShadowDx(), tv.getShadowDy(),
                        Config.textColorSecondary(context, key));
                break;
            }
            case KEY_TEXTSHADOW_SECONDARY_INVERSE: {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) return;
                final TextView tv = (TextView) current;
                tv.setShadowLayer(tv.getShadowRadius(), tv.getShadowDx(), tv.getShadowDy(),
                        Config.textColorSecondaryInverse(context, key));
                break;
            }

            case KEY_TINT_PRIMARY_COLOR:
                TintHelper.setTintAuto(current, Config.primaryColor(context, key), false);
                break;
            case KEY_TINT_PRIMARY_COLOR_DARK:
                TintHelper.setTintAuto(current, Config.primaryColorDark(context, key), false);
                break;
            case KEY_TINT_ACCENT_COLOR:
                TintHelper.setTintAuto(current, Config.accentColor(context, key), false);
                break;
            case KEY_TINT_TEXT_PRIMARY:
                TintHelper.setTintAuto(current, Config.textColorPrimary(context, key), false);
                break;
            case KEY_TINT_TEXT_PRIMARY_INVERSE:
                TintHelper.setTintAuto(current, Config.textColorPrimaryInverse(context, key), false);
                break;
            case KEY_TINT_TEXT_SECONDARY:
                TintHelper.setTintAuto(current, Config.textColorSecondary(context, key), false);
                break;
            case KEY_TINT_TEXT_SECONDARY_INVERSE:
                TintHelper.setTintAuto(current, Config.textColorSecondaryInverse(context, key), false);
                break;

            case KEY_BG_TINT_PRIMARY_COLOR:
                TintHelper.setTintAuto(current, Config.primaryColor(context, key), true);
                break;
            case KEY_BG_TINT_PRIMARY_COLOR_DARK:
                TintHelper.setTintAuto(current, Config.primaryColorDark(context, key), true);
                break;
            case KEY_BG_TINT_ACCENT_COLOR:
                TintHelper.setTintAuto(current, Config.accentColor(context, key), true);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY:
                TintHelper.setTintAuto(current, Config.textColorPrimary(context, key), true);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_INVERSE:
                TintHelper.setTintAuto(current, Config.textColorPrimaryInverse(context, key), true);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY:
                TintHelper.setTintAuto(current, Config.textColorSecondary(context, key), true);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_INVERSE:
                TintHelper.setTintAuto(current, Config.textColorSecondaryInverse(context, key), true);
                break;

            case KEY_BG_TINT_PRIMARY_COLOR_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.primaryColor(context, key), false, isDark);
                break;
            case KEY_BG_TINT_PRIMARY_COLOR_DARK_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.primaryColorDark(context, key), false, isDark);
                break;
            case KEY_BG_TINT_ACCENT_COLOR_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.accentColor(context, key), false, isDark);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.textColorPrimary(context, key), false, isDark);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_INVERSE_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.textColorPrimaryInverse(context, key), false, isDark);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.textColorSecondary(context, key), false, isDark);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_INVERSE_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.textColorSecondaryInverse(context, key), false, isDark);
                break;

            case KEY_BG_TINT_PRIMARY_COLOR_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.primaryColor(context, key), true, isDark);
                break;
            case KEY_BG_TINT_PRIMARY_COLOR_DARK_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.primaryColorDark(context, key), true, isDark);
                break;
            case KEY_BG_TINT_ACCENT_COLOR_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.accentColor(context, key), true, isDark);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.textColorPrimary(context, key), true, isDark);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_INVERSE_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.textColorPrimaryInverse(context, key), true, isDark);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.textColorSecondary(context, key), true, isDark);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_INVERSE_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.textColorSecondaryInverse(context, key), true, isDark);
                break;
        }
    }

    private static ColorStateList getTextSelector(@ColorInt int color, View view) {
        return new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        }, new int[]{
                view instanceof Button ? Color.BLACK : Util.adjustAlpha(color, 0.15f),
                color
        });
    }

    private final static String KEY_BG_PRIMARY_COLOR = "bg_primary_color";
    private final static String KEY_BG_PRIMARY_COLOR_DARK = "bg_primary_color_dark";
    private final static String KEY_BG_ACCENT_COLOR = "bg_accent_color";
    private final static String KEY_BG_TEXT_PRIMARY = "bg_text_primary";
    private final static String KEY_BG_TEXT_PRIMARY_INVERSE = "bg_text_primary_inverse";
    private final static String KEY_BG_TEXT_SECONDARY = "bg_text_secondary";
    private final static String KEY_BG_TEXT_SECONDARY_INVERSE = "bg_text_secondary_inverse";

    private final static String KEY_TEXT_PRIMARY_COLOR = "text_primary_color";
    private final static String KEY_TEXT_PRIMARY_COLOR_DARK = "text_primary_color_dark";
    private final static String KEY_TEXT_ACCENT_COLOR = "text_accent_color";
    private final static String KEY_TEXT_PRIMARY = "text_primary";
    private final static String KEY_TEXT_PRIMARY_INVERSE = "text_primary_inverse";
    private final static String KEY_TEXT_SECONDARY = "text_secondary";
    private final static String KEY_TEXT_SECONDARY_INVERSE = "text_secondary_inverse";

    private final static String KEY_TEXTLINK_PRIMARY_COLOR = "text_link_primary_color";
    private final static String KEY_TEXTLINK_PRIMARY_COLOR_DARK = "text_link_primary_color_dark";
    private final static String KEY_TEXTLINK_ACCENT_COLOR = "text_link_accent_color";
    private final static String KEY_TEXTLINK_PRIMARY = "text_link_primary";
    private final static String KEY_TEXTLINK_PRIMARY_INVERSE = "text_link_primary_inverse";
    private final static String KEY_TEXTLINK_SECONDARY = "text_link_secondary";
    private final static String KEY_TEXTLINK_SECONDARY_INVERSE = "text_link_secondary_inverse";

    private final static String KEY_TEXTSHADOW_PRIMARY_COLOR = "text_shadow_primary_color";
    private final static String KEY_TEXTSHADOW_PRIMARY_COLOR_DARK = "text_shadow_primary_color_dark";
    private final static String KEY_TEXTSHADOW_ACCENT_COLOR = "text_shadow_accent_color";
    private final static String KEY_TEXTSHADOW_PRIMARY = "text_shadow_primary";
    private final static String KEY_TEXTSHADOW_PRIMARY_INVERSE = "text_shadow_primary_inverse";
    private final static String KEY_TEXTSHADOW_SECONDARY = "text_shadow_secondary";
    private final static String KEY_TEXTSHADOW_SECONDARY_INVERSE = "text_shadow_secondary_inverse";

    private final static String KEY_TINT_PRIMARY_COLOR = "tint_primary_color";
    private final static String KEY_TINT_PRIMARY_COLOR_DARK = "tint_primary_color_dark";
    private final static String KEY_TINT_ACCENT_COLOR = "tint_accent_color";
    private final static String KEY_TINT_TEXT_PRIMARY = "tint_text_primary";
    private final static String KEY_TINT_TEXT_PRIMARY_INVERSE = "tint_text_primary_inverse";
    private final static String KEY_TINT_TEXT_SECONDARY = "tint_text_secondary";
    private final static String KEY_TINT_TEXT_SECONDARY_INVERSE = "tint_text_secondary_inverse";

    private final static String KEY_BG_TINT_PRIMARY_COLOR = "bg_tint_primary_color";
    private final static String KEY_BG_TINT_PRIMARY_COLOR_DARK = "bg_tint_primary_color_dark";
    private final static String KEY_BG_TINT_ACCENT_COLOR = "bg_tint_accent_color";
    private final static String KEY_BG_TINT_TEXT_PRIMARY = "bg_tint_text_primary";
    private final static String KEY_BG_TINT_TEXT_PRIMARY_INVERSE = "bg_tint_text_primary_inverse";
    private final static String KEY_BG_TINT_TEXT_SECONDARY = "bg_tint_text_secondary";
    private final static String KEY_BG_TINT_TEXT_SECONDARY_INVERSE = "bg_tint_text_secondary_inverse";

    private final static String KEY_BG_TINT_PRIMARY_COLOR_SELECTOR_LIGHTER = "bg_tint_primary_color_selector_lighter";
    private final static String KEY_BG_TINT_PRIMARY_COLOR_DARK_SELECTOR_LIGHTER = "bg_tint_primary_color_dark_selector_lighter";
    private final static String KEY_BG_TINT_ACCENT_COLOR_SELECTOR_LIGHTER = "bg_tint_accent_color_selector_lighter";
    private final static String KEY_BG_TINT_TEXT_PRIMARY_SELECTOR_LIGHTER = "bg_tint_text_primary_selector_lighter";
    private final static String KEY_BG_TINT_TEXT_PRIMARY_INVERSE_SELECTOR_LIGHTER = "bg_tint_text_primary_inverse_selector_lighter";
    private final static String KEY_BG_TINT_TEXT_SECONDARY_SELECTOR_LIGHTER = "bg_tint_text_secondary_selector_lighter";
    private final static String KEY_BG_TINT_TEXT_SECONDARY_INVERSE_SELECTOR_LIGHTER = "bg_tint_text_secondary_inverse_selector_lighter";

    private final static String KEY_BG_TINT_PRIMARY_COLOR_SELECTOR_DARKER = "bg_tint_primary_color_selector_darker";
    private final static String KEY_BG_TINT_PRIMARY_COLOR_DARK_SELECTOR_DARKER = "bg_tint_primary_color_dark_selector_darker";
    private final static String KEY_BG_TINT_ACCENT_COLOR_SELECTOR_DARKER = "bg_tint_accent_color_selector_darker";
    private final static String KEY_BG_TINT_TEXT_PRIMARY_SELECTOR_DARKER = "bg_tint_text_primary_selector_darker";
    private final static String KEY_BG_TINT_TEXT_PRIMARY_INVERSE_SELECTOR_DARKER = "bg_tint_text_primary_inverse_selector_darker";
    private final static String KEY_BG_TINT_TEXT_SECONDARY_SELECTOR_DARKER = "bg_tint_text_secondary_selector_darker";
    private final static String KEY_BG_TINT_TEXT_SECONDARY_INVERSE_SELECTOR_DARKER = "bg_tint_text_secondary_inverse_selector_darker";
}