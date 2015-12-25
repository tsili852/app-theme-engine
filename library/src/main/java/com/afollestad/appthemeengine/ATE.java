package com.afollestad.appthemeengine;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.appthemeengine.customizers.ATEActivityThemeCustomizer;
import com.afollestad.appthemeengine.customizers.ATETaskDescriptionCustomizer;
import com.afollestad.appthemeengine.util.EdgeGlowUtil;
import com.afollestad.appthemeengine.util.TintHelper;
import com.afollestad.appthemeengine.util.Util;
import com.afollestad.appthemeengine.views.PreMadeView;

import java.lang.reflect.Field;

/**
 * @author Aidan Follestad (afollestad)
 */
public final class ATE extends ATEBase {

    private static void processTagPart(@NonNull Context context, @NonNull View current, @NonNull String tag, @Nullable String key) {
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
                ((TextView) current).setTextColor(Config.primaryColor(context, key));
                break;
            case KEY_TEXT_PRIMARY_COLOR_DARK:
                ((TextView) current).setTextColor(Config.primaryColorDark(context, key));
                break;
            case KEY_TEXT_ACCENT_COLOR:
                ((TextView) current).setTextColor(Config.accentColor(context, key));
                break;
            case KEY_TEXT_PRIMARY:
                ((TextView) current).setTextColor(Config.textColorPrimary(context, key));
                break;
            case KEY_TEXT_PRIMARY_INVERSE:
                ((TextView) current).setTextColor(Config.textColorPrimaryInverse(context, key));
                break;
            case KEY_TEXT_SECONDARY:
                ((TextView) current).setTextColor(Config.textColorSecondary(context, key));
                break;
            case KEY_TEXT_SECONDARY_INVERSE:
                ((TextView) current).setTextColor(Config.textColorSecondaryInverse(context, key));
                break;

            case KEY_TEXTLINK_PRIMARY_COLOR:
                ((TextView) current).setLinkTextColor(Config.primaryColor(context, key));
                break;
            case KEY_TEXTLINK_PRIMARY_COLOR_DARK:
                ((TextView) current).setLinkTextColor(Config.primaryColorDark(context, key));
                break;
            case KEY_TEXTLINK_ACCENT_COLOR:
                ((TextView) current).setLinkTextColor(Config.accentColor(context, key));
                break;
            case KEY_TEXTLINK_PRIMARY:
                ((TextView) current).setLinkTextColor(Config.textColorPrimary(context, key));
                break;
            case KEY_TEXTLINK_PRIMARY_INVERSE:
                ((TextView) current).setLinkTextColor(Config.textColorPrimaryInverse(context, key));
                break;
            case KEY_TEXTLINK_SECONDARY:
                ((TextView) current).setLinkTextColor(Config.textColorSecondary(context, key));
                break;
            case KEY_TEXTLINK_SECONDARY_INVERSE:
                ((TextView) current).setLinkTextColor(Config.textColorSecondaryInverse(context, key));
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
                TintHelper.setTintSelector(current, Config.primaryColor(context, key), false);
                break;
            case KEY_BG_TINT_PRIMARY_COLOR_DARK_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.primaryColorDark(context, key), false);
                break;
            case KEY_BG_TINT_ACCENT_COLOR_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.accentColor(context, key), false);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.textColorPrimary(context, key), false);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_INVERSE_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.textColorPrimaryInverse(context, key), false);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.textColorSecondary(context, key), false);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_INVERSE_SELECTOR_LIGHTER:
                TintHelper.setTintSelector(current, Config.textColorSecondaryInverse(context, key), false);
                break;

            case KEY_BG_TINT_PRIMARY_COLOR_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.primaryColor(context, key), true);
                break;
            case KEY_BG_TINT_PRIMARY_COLOR_DARK_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.primaryColorDark(context, key), true);
                break;
            case KEY_BG_TINT_ACCENT_COLOR_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.accentColor(context, key), true);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.textColorPrimary(context, key), true);
                break;
            case KEY_BG_TINT_TEXT_PRIMARY_INVERSE_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.textColorPrimaryInverse(context, key), true);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.textColorSecondary(context, key), true);
                break;
            case KEY_BG_TINT_TEXT_SECONDARY_INVERSE_SELECTOR_DARKER:
                TintHelper.setTintSelector(current, Config.textColorSecondaryInverse(context, key), true);
                break;
        }
    }

    private static void processNavigationView(@NonNull NavigationView view, @Nullable String key) {
        if (!Config.navigationViewThemed(view.getContext(), key))
            return;
        final ColorStateList iconSl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        Config.navigationViewNormalIcon(view.getContext(), key),
                        Config.navigationViewSelectedIcon(view.getContext(), key)
                });
        final ColorStateList textSl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        Config.navigationViewNormalText(view.getContext(), key),
                        Config.navigationViewSelectedText(view.getContext(), key)
                });
        view.setItemTextColor(textSl);
        view.setItemIconTintList(iconSl);
        final View headerView = view.getHeaderView(0);
        if (headerView != null) apply(headerView, key);
    }

    private static boolean lightStatusBarEnabled(@NonNull Context context, @Nullable String key) {
        final int lightStatusMode = Config.lightStatusBarMode(context, key);
        return lightStatusMode != Config.LIGHT_STATUS_BAR_OFF &&
                (lightStatusMode == Config.LIGHT_STATUS_BAR_ON || Util.isColorLight(Config.statusBarColor(context, key)));
    }

    protected static void processToolbar(@NonNull Context context, @Nullable String key, @Nullable Toolbar toolbar, @Nullable Menu menu) {
        if (toolbar == null && context instanceof AppCompatActivity)
            toolbar = Util.getSupportActionBarView(((AppCompatActivity) context).getSupportActionBar());
        if (toolbar == null) return;

        boolean tinted = lightStatusBarEnabled(context, key);
        if (toolbar.getBackground() != null && toolbar.getBackground() instanceof ColorDrawable) {
            final ColorDrawable toolbarBg = (ColorDrawable) toolbar.getBackground();
            tinted = Util.isColorLight(toolbarBg.getColor());
        }
        final int color = tinted ? Color.BLACK : Color.WHITE;
        toolbar.setTitleTextColor(color);
        if (toolbar.getNavigationIcon() != null)
            toolbar.setNavigationIcon(TintHelper.tintDrawable(toolbar.getNavigationIcon(), color));
        if (menu == null)
            menu = toolbar.getMenu();
        if (menu != null && menu.size() > 0) {
            for (int i = 0; i < menu.size(); i++) {
                final MenuItem item = menu.getItem(i);
                if (item.getIcon() != null)
                    item.setIcon(TintHelper.tintDrawable(item.getIcon(), color));
            }
        }
        if (context instanceof Activity) {
            Util.setOverflowButtonColor((Activity) context, color);

            try {
                final Field menuField = Toolbar.class.getDeclaredField("mMenuBuilderCallback");
                menuField.setAccessible(true);
                final Field presenterField = Toolbar.class.getDeclaredField("mActionMenuPresenterCallback");
                presenterField.setAccessible(true);
                final Field menuViewField = Toolbar.class.getDeclaredField("mMenuView");
                menuViewField.setAccessible(true);

                final MenuPresenter.Callback currentPresenterCb = (MenuPresenter.Callback) presenterField.get(toolbar);
                if (!(currentPresenterCb instanceof ATEMenuPresenterCallback)) {
                    final ATEMenuPresenterCallback newPresenterCb = new ATEMenuPresenterCallback(
                            (Activity) context, key, currentPresenterCb, toolbar);
                    final MenuBuilder.Callback currentMenuCb = (MenuBuilder.Callback) menuField.get(toolbar);
                    toolbar.setMenuCallbacks(newPresenterCb, currentMenuCb);
                    ActionMenuView menuView = (ActionMenuView) menuViewField.get(toolbar);
                    if (menuView != null)
                        menuView.setMenuCallbacks(newPresenterCb, currentMenuCb);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void processTag(@NonNull Context context, @NonNull View current, @Nullable String key) {
        final String tag = (String) current.getTag();
        if (tag.contains(",")) {
            final String[] splitTag = tag.split(",");
            for (String part : splitTag)
                processTagPart(context, current, part, key);
        } else {
            processTagPart(context, current, tag, key);
        }
    }

    private static boolean isPreMadeView(@NonNull View view) {
        return view.getClass().getAnnotation(PreMadeView.class) != null;
    }

    private static void apply(@NonNull Context context, @NonNull ViewGroup view, @Nullable String key) {
        if (view instanceof AbsListView) {
            EdgeGlowUtil.setEdgeGlowColor((AbsListView) view, Config.accentColor(context, key));
            return;
        } else if (view instanceof RecyclerView) {
            EdgeGlowUtil.setEdgeGlowColor((RecyclerView) view, Config.accentColor(context, key));
            return;
        }

        for (int i = 0; i < view.getChildCount(); i++) {
            final View current = view.getChildAt(i);
            if (isPreMadeView(current)) {
                // Pre-made views handle themselves, don't need to apply theming here
                continue;
            }
            if (current instanceof NavigationView) {
                processNavigationView((NavigationView) current, key);
            } else {
                if (current instanceof Toolbar) {
                    mToolbar = (Toolbar) current;
                    processToolbar(context, key, mToolbar, null);
                } else if (current instanceof ScrollView) {
                    EdgeGlowUtil.setEdgeGlowColor((ScrollView) current, Config.accentColor(context, key));
                } else if (current instanceof AbsListView) {
                    EdgeGlowUtil.setEdgeGlowColor((AbsListView) current, Config.accentColor(context, key));
                } else if (current instanceof RecyclerView) {
                    EdgeGlowUtil.setEdgeGlowColor((RecyclerView) current, Config.accentColor(context, key));
                }
                if (current.getTag() != null && current.getTag() instanceof String) {
                    processTag(context, current, key);
                }
                if (current instanceof ViewGroup) {
                    apply(context, (ViewGroup) current, key);
                }
            }
        }
    }

    @Deprecated
    public static Config config(@NonNull Context context) {
        return config(context, null);
    }

    public static Config config(@NonNull Context context, @Nullable String key) {
        return new Config(context, key);
    }

    @Deprecated
    public static boolean didValuesChange(@NonNull Context context, long updateTime) {
        return didValuesChange(context, updateTime, null);
    }

    @SuppressLint("CommitPrefEdits")
    public static boolean didValuesChange(@NonNull Context context, long updateTime, @Nullable String key) {
        return ATE.config(context, key).isConfigured() && Config.prefs(context, key).getLong(Config.VALUES_CHANGED, -1) > updateTime;
    }

    @Deprecated
    public static void preApply(@NonNull Activity activity) {
        preApply(activity, null);
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
            final int lightStatusMode = Config.lightStatusBarMode(activity, key);
            boolean lightStatusEnabled = false;
            switch (lightStatusMode) {
                default: // OFF
                    break;
                case Config.LIGHT_STATUS_BAR_ON:
                    lightStatusEnabled = true;
                    break;
                case Config.LIGHT_STATUS_BAR_AUTO:
                    lightStatusEnabled = Util.isColorLight(Config.statusBarColor(activity, key));
                    break;
            }
            if (lightStatusEnabled)
                decorView.setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            else decorView.setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS);
        }
    }

    @Deprecated
    public static void apply(@NonNull View view) {
        apply(view, null);
    }

    public static void apply(@NonNull View view, @Nullable String key) {
        if (view.getContext() == null)
            throw new IllegalStateException("View has no Context, use apply(Context, View, String) instead.");
        apply(view.getContext(), view, key);
    }

    @Deprecated
    public static void apply(@NonNull Context context, @NonNull View view) {
        apply(context, view, null);
    }

    public static void apply(@NonNull Context context, @NonNull View view, @Nullable String key) {
        if (view.getTag() != null && view.getTag() instanceof String)
            processTag(context, view, key);
        if (view instanceof ViewGroup)
            apply(context, (ViewGroup) view, key);
    }

    @Deprecated
    public static void apply(@NonNull Activity activity) {
        apply(activity, (String) null);
    }

    public static void apply(@NonNull Activity activity, @Nullable String key) {
        if (didPreApply == null)
            preApply(activity, key);
        if (Config.coloredActionBar(activity, key)) {
            if (activity instanceof AppCompatActivity) {
                final AppCompatActivity aca = (AppCompatActivity) activity;
                if (aca.getSupportActionBar() != null) {
                    Toolbar toolbar = Util.getSupportActionBarView(aca.getSupportActionBar());
                    if (toolbar != null)
                        Util.setBackgroundCompat(toolbar, new ColorDrawable(Config.primaryColor(activity, key)));
                    else
                        aca.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Config.primaryColor(activity, key)));
                    processToolbar(activity, key, null, null);
                }
            } else if (activity.getActionBar() != null) {
                activity.getActionBar().setBackgroundDrawable(new ColorDrawable(Config.primaryColor(activity, key)));
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

    @Deprecated
    public static void apply(@NonNull android.support.v4.app.Fragment fragment) {
        apply(fragment, null);
    }

    public static void apply(@NonNull android.support.v4.app.Fragment fragment, @Nullable String key) {
        if (fragment.getActivity() == null)
            throw new IllegalStateException("Fragment is not attached to an Activity yet.");
        else if (fragment.getView() == null)
            throw new IllegalStateException("Fragment does not have a View yet.");
        apply(fragment.getActivity(), (ViewGroup) fragment.getView(), key);
        if (fragment.getActivity() instanceof AppCompatActivity)
            apply(fragment.getActivity(), key);
    }

    @Deprecated
    public static void apply(@NonNull android.app.Fragment fragment) {
        apply(fragment, null);
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
        final int color = activity instanceof ATETaskDescriptionCustomizer ?
                ((ATETaskDescriptionCustomizer) activity).getTaskDescriptionColor() :
                Config.primaryColor(activity, key);
        // Sets color of entry in the system recents page
        ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(
                (String) activity.getTitle(),
                ((BitmapDrawable) activity.getApplicationInfo().loadIcon(activity.getPackageManager())).getBitmap(),
                color);
        activity.setTaskDescription(td);
    }

    public static void applyMenu(@NonNull Activity activity, @Nullable String key, @Nullable Menu menu) {
        processToolbar(activity, key, mToolbar, menu);
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
                    MenuPopupHelper overflowMenuPopupHelper = (MenuPopupHelper) f3.get(presenter);
                    setTintForMenuPopupHelper(activity, overflowMenuPopupHelper, key);

                    Field f4 = presenter.getClass().getDeclaredField("mActionButtonPopup");
                    f4.setAccessible(true);
                    MenuPopupHelper subMenuPopupHelper = (MenuPopupHelper) f4.get(presenter);
                    setTintForMenuPopupHelper(activity, subMenuPopupHelper, key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void setTintForMenuPopupHelper(final @NonNull Activity context, @Nullable MenuPopupHelper menuPopupHelper, final @Nullable String key) {
        if (menuPopupHelper != null) {
            final ListView listView = menuPopupHelper.getPopup().getListView();
            listView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    try {
                        Field checkboxField = ListMenuItemView.class.getDeclaredField("mCheckBox");
                        checkboxField.setAccessible(true);
                        Field radioButtonField = ListMenuItemView.class.getDeclaredField("mRadioButton");
                        radioButtonField.setAccessible(true);

                        for (int i = 0; i < listView.getChildCount(); i++) {
                            View v = listView.getChildAt(i);
                            if (!(v instanceof ListMenuItemView)) continue;
                            ListMenuItemView iv = (ListMenuItemView) v;

                            CheckBox check = (CheckBox) checkboxField.get(iv);
                            if (check != null) {
                                TintHelper.setTint(check, Config.accentColor(context, key));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                    check.setBackground(null);
                            }

                            RadioButton radioButton = (RadioButton) radioButtonField.get(iv);
                            if (radioButton != null) {
                                TintHelper.setTint(radioButton, Config.accentColor(context, key));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                    radioButton.setBackground(null);
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        listView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        //noinspection deprecation
                        listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }
    }

    private ATE() {
    }
}