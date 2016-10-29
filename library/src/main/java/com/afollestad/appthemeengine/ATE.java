package com.afollestad.appthemeengine;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.afollestad.appthemeengine.customizers.ATEActivityThemeCustomizer;
import com.afollestad.appthemeengine.customizers.ATETaskDescriptionCustomizer;
import com.afollestad.appthemeengine.processors.Processor;
import com.afollestad.appthemeengine.util.Util;
import com.afollestad.appthemeengine.views.PreMadeView;
import com.afollestad.materialdialogs.internal.ThemeSingleton;

import java.lang.reflect.Field;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class ATE extends ATEBase {

    private static boolean isPreMadeView(@NonNull View view) {
        return view.getClass().getAnnotation(PreMadeView.class) != null;
    }

    private static boolean isChildrenBlacklistedViewGroup(@NonNull ViewGroup view) {
        // We don't want to theme children in these views
        return view instanceof ListView || view instanceof RecyclerView || view instanceof TabLayout;
    }

    @SuppressWarnings("unchecked")
    private static void performDefaultProcessing(@NonNull Context context, @NonNull View current, @Nullable String key) {
        if (current.getTag() != null && current.getTag() instanceof String) {
            // Apply default processor to view if view's tag is a String
            Processor processor = getProcessor(null); // gets default processor
            if (processor != null)
                processor.process(context, key, current, null);
        }
    }

    @SuppressWarnings("unchecked")
    private static void apply(@NonNull Context context, @NonNull ViewGroup view, @Nullable String key) {
        Processor processor = getProcessor(view.getClass());
        if (processor != null) {
            processor.process(context, key, view, null);
        }
        if (isChildrenBlacklistedViewGroup(view)) {
            performDefaultProcessing(context, view, key);
            return;
        }

        for (int i = 0; i < view.getChildCount(); i++) {
            final View current = view.getChildAt(i);
            if (current instanceof Toolbar && mToolbar == null)
                mToolbar = (Toolbar) current;

            // Pre-made views handle themselves, don't need to apply theming
            if (isPreMadeView(current)) {
                continue;
            }

            performDefaultProcessing(context, current, key);

            if (current instanceof ViewGroup) {
                // View group will apply theming to itself and then children inside
                apply(context, (ViewGroup) current, key);
            } else {
                processor = getProcessor(current.getClass());
                if (processor != null) {
                    // Apply view theming using processors, if any match
                    processor.process(context, key, current, null);
                }
            }

            if (current instanceof CoordinatorLayout) {
                ((CoordinatorLayout) current).setStatusBarBackgroundColor(Config.statusBarColor(context, key));
            }
        }
    }

    public static Config config(@NonNull Context context, @Nullable String key) {
        return new Config(context, key);
    }

    @SuppressLint("CommitPrefEdits")
    public static boolean didValuesChange(@NonNull Context context, long updateTime, @Nullable String key) {
        return ATE.config(context, key).isConfigured() && Config.prefs(context, key).getLong(Config.VALUES_CHANGED, -1) > updateTime;
    }

    public static void preApply(@NonNull Activity activity, @Nullable String key) {
        didPreApply = activity.getClass();
        mToolbar = null;

        int activityTheme = activity instanceof ATEActivityThemeCustomizer ?
                ((ATEActivityThemeCustomizer) activity).getActivityTheme() : Config.activityTheme(activity, key);
        if (activityTheme != 0) activity.setTheme(activityTheme);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            if (Config.coloredStatusBar(activity, key))
                window.setStatusBarColor(Config.statusBarColor(activity, key));
            else window.setStatusBarColor(Color.BLACK);
            if (Config.coloredNavigationBar(activity, key))
                window.setNavigationBarColor(Config.navigationBarColor(activity, key));
            else window.setNavigationBarColor(Color.BLACK);
            applyTaskDescription(activity, key);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final View decorView = activity.getWindow().getDecorView();
            boolean lightStatusEnabled = false;
            if (Config.coloredStatusBar(activity, key)) {
                final int lightStatusMode = Config.lightStatusBarMode(activity, key);
                switch (lightStatusMode) {
                    case Config.LIGHT_STATUS_BAR_OFF:
                    default:
                        break;
                    case Config.LIGHT_STATUS_BAR_ON:
                        lightStatusEnabled = true;
                        break;
                    case Config.LIGHT_STATUS_BAR_AUTO:
                        lightStatusEnabled = Util.isColorLight(Config.primaryColor(activity, key));
                        break;
                }
            }

            final int systemUiVisibility = decorView.getSystemUiVisibility();
            if (lightStatusEnabled) {
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        // MD integration
        if (Config.usingMaterialDialogs(activity, key)) {
            final ThemeSingleton md = ThemeSingleton.get();
            md.titleColor = Config.textColorPrimary(activity, key);
            md.contentColor = Config.textColorSecondary(activity, key);
            md.itemColor = md.titleColor;
            md.widgetColor = Config.accentColor(activity, key);
            md.linkColor = ColorStateList.valueOf(md.widgetColor);
            md.positiveColor = ColorStateList.valueOf(md.widgetColor);
            md.neutralColor = ColorStateList.valueOf(md.widgetColor);
            md.negativeColor = ColorStateList.valueOf(md.widgetColor);
        }
    }

    public static void apply(@NonNull View view, @Nullable String key) {
        if (view.getContext() == null)
            throw new IllegalStateException("View has no Context, use apply(Context, View, String) instead.");
        apply(view.getContext(), view, key);
    }

    @SuppressWarnings("unchecked")
    public static void apply(@NonNull Context context, @NonNull View view, @Nullable String key) {
        performDefaultProcessing(context, view, key);
        if (view instanceof ViewGroup)
            apply(context, (ViewGroup) view, key);
    }

    @SuppressWarnings("unchecked")
    public static void apply(@NonNull Activity activity, @Nullable String key) {
        if (didPreApply == null)
            preApply(activity, key);
        if (Config.coloredActionBar(activity, key)) {
            if (activity instanceof AppCompatActivity) {
                final AppCompatActivity aca = (AppCompatActivity) activity;
                if (aca.getSupportActionBar() != null) {
                    Toolbar toolbar = Util.getSupportActionBarView(aca.getSupportActionBar());
                    if (toolbar == null)
                        aca.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Config.toolbarColor(activity, key)));
                    Processor toolbarProcessor = getProcessor(Toolbar.class);
                    if (toolbarProcessor != null) {
                        // The processor handles retrieving the toolbar
                        toolbarProcessor.process(activity, key, null, null);
                    }
                }
            } else if (activity.getActionBar() != null) {
                activity.getActionBar().setBackgroundDrawable(new ColorDrawable(Config.toolbarColor(activity, key)));
            }
        }

        final ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        if (rootView instanceof DrawerLayout) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final int color = Config.coloredStatusBar(activity, key) ?
                        Color.TRANSPARENT : Color.BLACK;
                activity.getWindow().setStatusBarColor(color);
            }
            if (Config.coloredStatusBar(activity, key))
                ((DrawerLayout) rootView).setStatusBarBackgroundColor(Config.statusBarColor(activity, key));
        }

        apply(activity, rootView, key);
        didPreApply = null;
    }

    public static void apply(@NonNull android.support.v4.app.Fragment fragment, @Nullable String key) {
        if (fragment.getActivity() == null)
            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
        final View fragmentView = fragment.getView();
        if (fragmentView == null)
            throw new IllegalStateException("Fragment does not have a View yet.");
        if (fragmentView instanceof ViewGroup)
            apply(fragment.getActivity(), (ViewGroup) fragmentView, key);
        else apply(fragment.getActivity(), fragmentView, key);
        if (fragment.getActivity() instanceof AppCompatActivity)
            apply(fragment.getActivity(), key);
    }

    public static void apply(@NonNull android.app.Fragment fragment, @Nullable String key) {
        if (fragment.getActivity() == null)
            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
        else if (fragment.getView() == null)
            throw new IllegalStateException("Fragment does not have a View yet.");
        apply(fragment.getActivity(), (ViewGroup) fragment.getView(), key);
        if (fragment.getActivity() instanceof AppCompatActivity)
            apply(fragment.getActivity(), key);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void applyTaskDescription(@NonNull Activity activity, @Nullable String key) {
        int color;
        Bitmap icon = null;
        if (activity instanceof ATETaskDescriptionCustomizer) {
            final ATETaskDescriptionCustomizer customizer = (ATETaskDescriptionCustomizer) activity;
            color = customizer.getTaskDescriptionColor();
            icon = customizer.getTaskDescriptionIcon();
        } else {
            color = Config.primaryColor(activity, key);
        }

        // Task description requires fully opaque color
        color = Util.stripAlpha(color);
        // Default is app's launcher icon
        if (icon == null)
            icon = ((BitmapDrawable) activity.getApplicationInfo().loadIcon(activity.getPackageManager())).getBitmap();

        // Sets color of entry in the system recents page
        ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(
                (String) activity.getTitle(), icon, color);
        activity.setTaskDescription(td);
    }

    @SuppressWarnings("unchecked")
    public static void applyMenu(@NonNull Activity activity, @Nullable String key, @Nullable Menu menu) {
        Processor toolbarProcessor = getProcessor(Toolbar.class);
        if (toolbarProcessor != null)
            toolbarProcessor.process(activity, key, mToolbar, menu);
    }

    public static void applyOverflow(@NonNull AppCompatActivity activity, @Nullable String key) {
        final Toolbar toolbar = mToolbar != null ? mToolbar : Util.getSupportActionBarView(activity.getSupportActionBar());
        applyOverflow(activity, key, toolbar);
    }

    public static void applyOverflow(final @NonNull Activity activity, final @Nullable String key, final @Nullable Toolbar toolbar) {
        if (toolbar == null) return;
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Field f1 = Toolbar.class.getDeclaredField("mMenuView");
                    f1.setAccessible(true);
                    ActionMenuView actionMenuView = (ActionMenuView) f1.get(toolbar);
                    Field f2 = ActionMenuView.class.getDeclaredField("mPresenter");
                    f2.setAccessible(true);

                    // Actually ActionMenuPresenter
                    BaseMenuPresenter presenter = (BaseMenuPresenter) f2.get(actionMenuView);
                    Field f3 = presenter.getClass().getDeclaredField("mOverflowPopup");
                    f3.setAccessible(true);
//                    MenuPopupHelper overflowMenuPopupHelper = (MenuPopupHelper) f3.get(presenter);
//                    setTintForMenuPopupHelper(activity, overflowMenuPopupHelper, key);

                    Field f4 = presenter.getClass().getDeclaredField("mActionButtonPopup");
                    f4.setAccessible(true);
//                    MenuPopupHelper subMenuPopupHelper = (MenuPopupHelper) f4.get(presenter);
//                    setTintForMenuPopupHelper(activity, subMenuPopupHelper, key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private ATE() {
    }
}