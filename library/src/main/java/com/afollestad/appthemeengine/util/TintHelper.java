package com.afollestad.appthemeengine.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.appthemeengine.R;

/**
 * @author afollestad, plusCubed
 */
public final class TintHelper {

    @SuppressLint("PrivateResource")
    @ColorInt
    private static int getDefaultRippleColor(@NonNull Context context, boolean useDarkRipple) {
        // Light ripple is actually translucent black, and vice versa
        return ContextCompat.getColor(context, useDarkRipple ?
                R.color.ripple_material_light : R.color.ripple_material_dark);
    }

    @NonNull
    private static ColorStateList getDisabledColorStateList(@ColorInt int normal, @ColorInt int disabled) {
        return new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}
        }, new int[]{
                disabled,
                normal
        });
    }

    @SuppressWarnings("deprecation")
    public static void setTintSelector(@NonNull View view, @ColorInt int color, boolean darker, boolean useDarkTheme) {
        final int disabled = ContextCompat.getColor(view.getContext(), useDarkTheme ?
                R.color.ate_disabled_button_dark : R.color.ate_disabled_button_light);
        final int pressed = Util.shiftColor(color, darker ? 0.9f : 1.1f);
        final int activated = Util.shiftColor(color, darker ? 1.1f : 0.9f);
        final int rippleColor = getDefaultRippleColor(view.getContext(), Util.isColorLight(color));

        final ColorStateList sl;
        if (view instanceof Button) {
            sl = getDisabledColorStateList(color, disabled);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                    view.getBackground() instanceof RippleDrawable) {
                RippleDrawable rd = (RippleDrawable) view.getBackground();
                rd.setColor(ColorStateList.valueOf(rippleColor));
            }

            // Disabled text color state for buttons, may get overridden later by ATE tags
            final Button button = (Button) view;
            final int defaultTextColor = Util.isColorLight(color) ?
                    Color.BLACK : Color.WHITE;
            button.setTextColor(getDisabledColorStateList(defaultTextColor, Color.BLACK));
        } else if (view instanceof FloatingActionButton) {
            // FloatingActionButton doesn't support disabled state?
            sl = new ColorStateList(new int[][]{
                    new int[]{-android.R.attr.state_pressed},
                    new int[]{android.R.attr.state_pressed}
            }, new int[]{
                    color,
                    pressed
            });
        } else {
            sl = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed},
                            new int[]{android.R.attr.state_enabled, android.R.attr.state_activated},
                            new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
                    },
                    new int[]{
                            disabled,
                            color,
                            pressed,
                            activated,
                            activated
                    }
            );
        }

        // TODO use other theme values in place of these?
        final int tintColor = Util.isColorLight(color) ? Color.BLACK : Color.WHITE;

        if (view instanceof FloatingActionButton) {
            final FloatingActionButton fab = (FloatingActionButton) view;
            fab.setRippleColor(rippleColor);
            fab.setBackgroundTintList(sl);
            if (fab.getDrawable() != null)
                fab.setImageDrawable(tintDrawable(fab.getDrawable(), tintColor));
            return;
        }

        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawable, sl);
            Util.setBackgroundCompat(view, drawable);
        }

        if (view instanceof TextView && !(view instanceof Button)) {
            final TextView tv = (TextView) view;
            tv.setTextColor(getDisabledColorStateList(tintColor, Util.adjustAlpha(tintColor, 0.4f)));
        }
    }

    @SuppressWarnings("deprecation")
    public static void setTintAuto(@NonNull View view, @ColorInt int color, boolean background) {
        final boolean isDark = !Util.isColorLight(Util.resolveColor(view.getContext(), android.R.attr.windowBackground));
        if (!background) {
            if (view instanceof RadioButton)
                setTint((RadioButton) view, color, isDark);
            else if (view instanceof SeekBar)
                setTint((SeekBar) view, color, isDark);
            else if (view instanceof ProgressBar)
                setTint((ProgressBar) view, color);
            else if (view instanceof EditText)
                setTint((EditText) view, color, isDark);
            else if (view instanceof CheckBox)
                setTint((CheckBox) view, color, isDark);
            else if (view instanceof ImageView)
                setTint((ImageView) view, color);
            else if (view instanceof Switch)
                setTint((Switch) view, color, isDark);
            else if (view instanceof SwitchCompat)
                setTint((SwitchCompat) view, color, isDark);
            else background = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                    !background && view.getBackground() instanceof RippleDrawable) {
                // Ripples for the above views (e.g. when you tap and hold a switch or checkbox)
                RippleDrawable rd = (RippleDrawable) view.getBackground();
                @SuppressLint("PrivateResource")
                final int unchecked = ContextCompat.getColor(view.getContext(),
                        isDark ? R.color.ripple_material_dark : R.color.ripple_material_light);
                final int checked = Util.adjustAlpha(color, 0.4f);
                final ColorStateList sl = new ColorStateList(
                        new int[][]{
                                new int[]{-android.R.attr.state_activated, -android.R.attr.state_checked},
                                new int[]{android.R.attr.state_activated},
                                new int[]{android.R.attr.state_checked}
                        },
                        new int[]{
                                unchecked,
                                checked,
                                checked
                        }
                );
                rd.setColor(sl);
            }
        }
        if (background) {
            // Need to tint the background of a view
            if (view instanceof FloatingActionButton || view instanceof Button) {
                setTintSelector(view, color, false, isDark);
            } else if (view.getBackground() != null) {
                Drawable drawable = view.getBackground();
                if (drawable != null) {
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, color);
                    Util.setBackgroundCompat(view, drawable);
                }
            }
        }
    }

    public static void setTint(@NonNull RadioButton radioButton, @ColorInt int color, boolean useDarker) {
        ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
        }, new int[]{
                ContextCompat.getColor(radioButton.getContext(), useDarker ? R.color.ate_disabled_radiobutton_dark : R.color.ate_disabled_radiobutton_light),
                Util.resolveColor(radioButton.getContext(), R.attr.colorControlNormal),
                color
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            radioButton.setButtonTintList(sl);
        } else {
            Drawable d = DrawableCompat.wrap(ContextCompat.getDrawable(radioButton.getContext(), R.drawable.abc_btn_radio_material));
            DrawableCompat.setTintList(d, sl);
            radioButton.setButtonDrawable(d);
        }
    }

    public static void setTint(@NonNull SeekBar seekBar, @ColorInt int color, boolean useDarker) {
        final ColorStateList s1 = getDisabledColorStateList(color,
                ContextCompat.getColor(seekBar.getContext(), useDarker ? R.color.ate_disabled_seekbar_dark : R.color.ate_disabled_seekbar_light));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            seekBar.setThumbTintList(s1);
            seekBar.setProgressTintList(s1);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
            Drawable progressDrawable = DrawableCompat.wrap(seekBar.getProgressDrawable());
            seekBar.setProgressDrawable(progressDrawable);
            DrawableCompat.setTintList(progressDrawable, s1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Drawable thumbDrawable = DrawableCompat.wrap(seekBar.getThumb());
                DrawableCompat.setTintList(thumbDrawable, s1);
                seekBar.setThumb(thumbDrawable);
            }
        } else {
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (seekBar.getIndeterminateDrawable() != null)
                seekBar.getIndeterminateDrawable().setColorFilter(color, mode);
            if (seekBar.getProgressDrawable() != null)
                seekBar.getProgressDrawable().setColorFilter(color, mode);
        }
    }

    public static void setTint(@NonNull ProgressBar progressBar, @ColorInt int color) {
        setTint(progressBar, color, false);
    }

    public static void setTint(@NonNull ProgressBar progressBar, @ColorInt int color, boolean skipIndeterminate) {
        ColorStateList sl = ColorStateList.valueOf(color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setProgressTintList(sl);
            progressBar.setSecondaryProgressTintList(sl);
            if (!skipIndeterminate)
                progressBar.setIndeterminateTintList(sl);
        } else {
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (!skipIndeterminate && progressBar.getIndeterminateDrawable() != null)
                progressBar.getIndeterminateDrawable().setColorFilter(color, mode);
            if (progressBar.getProgressDrawable() != null)
                progressBar.getProgressDrawable().setColorFilter(color, mode);
        }
    }

    public static void setTint(@NonNull EditText editText, @ColorInt int color, boolean useDarker) {
        final ColorStateList editTextColorStateList = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_pressed, -android.R.attr.state_focused},
                new int[]{}
        }, new int[]{
                ContextCompat.getColor(editText.getContext(), useDarker ? R.color.ate_disabled_edittext_dark : R.color.ate_disabled_edittext_light),
                Util.resolveColor(editText.getContext(), R.attr.colorControlNormal),
                color
        });
        if (editText instanceof AppCompatEditText) {
            ((AppCompatEditText) editText).setSupportBackgroundTintList(editTextColorStateList);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setBackgroundTintList(editTextColorStateList);
        }
    }

    public static void setTint(@NonNull CheckBox box, @ColorInt int color, boolean useDarker) {
        ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled, -android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
        }, new int[]{
                ContextCompat.getColor(box.getContext(), useDarker ? R.color.ate_disabled_checkbox_dark : R.color.ate_disabled_checkbox_light),
                Util.resolveColor(box.getContext(), R.attr.colorControlNormal),
                color
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            box.setButtonTintList(sl);
        } else {
            Drawable drawable = tintDrawable(ContextCompat.getDrawable(box.getContext(), R.drawable.abc_btn_check_material), sl);
            box.setButtonDrawable(drawable);
        }
    }

    public static void setTint(@NonNull ImageView image, @ColorInt int color) {
        image.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private static Drawable modifySwitchDrawable(@NonNull Context context, @NonNull Drawable from, @ColorInt int tint, @FloatRange(from = 0.0, to = 1.0) float alpha, boolean thumb, boolean useDarker) {
        if (alpha < 1f)
            tint = Util.adjustAlpha(tint, alpha);
        int disabled;
        if (thumb) {
            disabled = ContextCompat.getColor(context, useDarker ? R.color.ate_disabled_switch_thumb_dark : R.color.ate_disabled_switch_thumb_light);
        } else {
            disabled = ContextCompat.getColor(context, useDarker ? R.color.ate_disabled_switch_track_dark : R.color.ate_disabled_switch_track_light);
        }
        final ColorStateList sl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_enabled, -android.R.attr.state_activated, -android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled, android.R.attr.state_activated},
                        new int[]{android.R.attr.state_enabled, android.R.attr.state_checked}
                },
                new int[]{
                        disabled,
                        Color.parseColor(thumb ? "#e7e7e7" : "#9f9f9f"),
                        tint,
                        tint
                }
        );
        return tintDrawable(from, sl);
    }

    public static void setTint(@NonNull Switch switchView, @ColorInt int color, boolean useDarker) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return;
        if (switchView.getTrackDrawable() != null) {
            switchView.setTrackDrawable(modifySwitchDrawable(switchView.getContext(),
                    switchView.getTrackDrawable(), color, 0.5f, false, useDarker));
        }
        if (switchView.getThumbDrawable() != null) {
            switchView.setThumbDrawable(modifySwitchDrawable(switchView.getContext(),
                    switchView.getThumbDrawable(), color, 1.0f, true, useDarker));
        }
    }

    public static void setTint(@NonNull SwitchCompat switchView, @ColorInt int color, boolean useDarker) {
        if (switchView.getTrackDrawable() != null) {
            switchView.setTrackDrawable(modifySwitchDrawable(switchView.getContext(),
                    switchView.getTrackDrawable(), color, 0.5f, false, useDarker));
        }
        if (switchView.getThumbDrawable() != null) {
            switchView.setThumbDrawable(modifySwitchDrawable(switchView.getContext(),
                    switchView.getThumbDrawable(), color, 1.0f, true, useDarker));
        }
    }

    @Nullable
    public static Drawable tintDrawable(@Nullable Drawable drawable, @ColorInt int color) {
        if (drawable == null) return null;
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    @Nullable
    public static Drawable tintDrawable(@Nullable Drawable drawable, @NonNull ColorStateList sl) {
        if (drawable == null) return null;
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(drawable, sl);
        return drawable;
    }
}