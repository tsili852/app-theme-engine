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
        return ContextCompat.getColor(context, useDarkRipple ?
                R.color.ripple_material_dark : R.color.ripple_material_light);
    }

    @SuppressWarnings("deprecation")
    public static void setTintSelector(@NonNull View view, @ColorInt int color, boolean darker) {
        final int pressed = Util.shiftColor(color, darker ? 0.9f : 1.1f);
        final int activated = Util.shiftColor(color, darker ? 1.1f : 0.9f);
        final int rippleColor = getDefaultRippleColor(view.getContext(), Util.isColorLight(color));

        final ColorStateList sl;
        if (view instanceof Button && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sl = ColorStateList.valueOf(color);
            if (view.getBackground() instanceof RippleDrawable) {
                RippleDrawable rd = (RippleDrawable) view.getBackground();
                rd.setColor(ColorStateList.valueOf(rippleColor));
            }
        } else {
            sl = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_pressed, -android.R.attr.state_activated, -android.R.attr.state_checked},
                            new int[]{android.R.attr.state_pressed, -android.R.attr.state_activated, -android.R.attr.state_checked},
                            new int[]{-android.R.attr.state_pressed, android.R.attr.state_activated, -android.R.attr.state_checked},
                            new int[]{-android.R.attr.state_pressed, -android.R.attr.state_activated, android.R.attr.state_checked}
                    },
                    new int[]{
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

        if (view instanceof TextView) {
            final TextView tv = (TextView) view;
            tv.setTextColor(tintColor);
        }
    }

    @SuppressWarnings("deprecation")
    public static void setTintAuto(@NonNull View view, @ColorInt int color, boolean background) {
        if (!background) {
            if (view instanceof RadioButton)
                setTint((RadioButton) view, color);
            else if (view instanceof SeekBar)
                setTint((SeekBar) view, color);
            else if (view instanceof ProgressBar)
                setTint((ProgressBar) view, color);
            else if (view instanceof EditText)
                setTint((EditText) view, color);
            else if (view instanceof CheckBox)
                setTint((CheckBox) view, color);
            else if (view instanceof ImageView)
                setTint((ImageView) view, color);
            else if (view instanceof Switch)
                setTint((Switch) view, color);
            else if (view instanceof SwitchCompat)
                setTint((SwitchCompat) view, color);
            else background = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                    !background && view.getBackground() instanceof RippleDrawable) {
                // Ripples for the above views (e.g. when you tap and hold a switch or checkbox)
                RippleDrawable rd = (RippleDrawable) view.getBackground();
                final int unchecked = Util.adjustAlpha(Color.parseColor("#9f9f9f"), 0.4f);
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
                setTintSelector(view, color, false);
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

    public static void setTint(@NonNull RadioButton radioButton, @ColorInt int color) {
        ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        }, new int[]{
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

    public static void setTint(@NonNull SeekBar seekBar, @ColorInt int color) {
        ColorStateList s1 = ColorStateList.valueOf(color);
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

    private static ColorStateList createEditTextColorStateList(@NonNull Context context, @ColorInt int color) {
        int[][] states = new int[3][];
        int[] colors = new int[3];
        int i = 0;
        states[i] = new int[]{-android.R.attr.state_enabled};
        colors[i] = Util.resolveColor(context, R.attr.colorControlNormal);
        i++;
        states[i] = new int[]{-android.R.attr.state_pressed, -android.R.attr.state_focused};
        colors[i] = Util.resolveColor(context, R.attr.colorControlNormal);
        i++;
        states[i] = new int[]{};
        colors[i] = color;
        return new ColorStateList(states, colors);
    }

    public static void setTint(@NonNull EditText editText, @ColorInt int color) {
        ColorStateList editTextColorStateList = createEditTextColorStateList(editText.getContext(), color);
        if (editText instanceof AppCompatEditText) {
            ((AppCompatEditText) editText).setSupportBackgroundTintList(editTextColorStateList);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setBackgroundTintList(editTextColorStateList);
        }
    }

    public static void setTint(@NonNull CheckBox box, @ColorInt int color) {
        ColorStateList sl = new ColorStateList(new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        }, new int[]{
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

    private static Drawable modifySwitchDrawable(@NonNull Drawable from, @ColorInt int tint, @FloatRange(from = 0.0, to = 1.0) float alpha, boolean thumb) {
        if (alpha < 1f)
            tint = Util.adjustAlpha(tint, alpha);
        final ColorStateList sl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_activated, -android.R.attr.state_checked},
                        new int[]{android.R.attr.state_activated},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        Color.parseColor(thumb ? "#e7e7e7" : "#9f9f9f"),
                        tint,
                        tint
                }
        );
        return tintDrawable(from, sl);
    }

    public static void setTint(@NonNull Switch switchView, @ColorInt int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) return;
        if (switchView.getTrackDrawable() != null)
            switchView.setTrackDrawable(modifySwitchDrawable(switchView.getTrackDrawable(), color, 0.5f, false));
        if (switchView.getThumbDrawable() != null)
            switchView.setThumbDrawable(modifySwitchDrawable(switchView.getThumbDrawable(), color, 1.0f, true));
    }

    public static void setTint(@NonNull SwitchCompat switchView, @ColorInt int color) {
        if (switchView.getTrackDrawable() != null)
            switchView.setTrackDrawable(modifySwitchDrawable(switchView.getTrackDrawable(), color, 0.5f, false));
        if (switchView.getThumbDrawable() != null)
            switchView.setThumbDrawable(modifySwitchDrawable(switchView.getThumbDrawable(), color, 1.0f, true));
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