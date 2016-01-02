# App Theme Engine (BETA)

App Theme Engine is a library that makes it easy for developers to implement a theme system in 
their apps, similar to what's seen in [Cabinet](https://play.google.com/store/apps/details?id=com.afollestad.cabinet) 
and [Impression](https://github.com/afollestad/impression).

Download the [latest sample APK](https://github.com/afollestad/app-theme-engine/raw/master/sample/Sample.apk) to check it out! 
 The sample's icon was designed by [Alex Mueller](https://plus.google.com/109174583408308276235).

![Showcase](https://raw.githubusercontent.com/afollestad/app-theme-engine/master/art/showcase.png)

# When To NOT Use This Library

If your app has two themes, a light theme and a dark theme, do not use this library to configure them. 
Only use this library if you intend to give the user the ability to change the color of UI elements in your app.﻿

---

# Table of Contents

1. [Gradle Dependency](https://github.com/afollestad/app-theme-engine#gradle-dependency)
    1. [Repository](https://github.com/afollestad/app-theme-engine#repository)
    2. [Dependency](https://github.com/afollestad/app-theme-engine#dependency)
2. [Config](https://github.com/afollestad/app-theme-engine#config)
    1. [Modifiers](https://github.com/afollestad/app-theme-engine#modifiers)
    2. [Keys](https://github.com/afollestad/app-theme-engine#keys)
    3. [Default Configuration](https://github.com/afollestad/app-theme-engine#default-configuration)
    4. [Value Retrieval](https://github.com/afollestad/app-theme-engine#value-retrieval)
    5. [Customizers](https://github.com/afollestad/app-theme-engine#customizers)
    6. [Marking as Changed](https://github.com/afollestad/app-theme-engine#marking-as-changed)
3. [Basics of Applying](https://github.com/afollestad/app-theme-engine#basics-of-applying)
    1. [ATEActivity](https://github.com/afollestad/app-theme-engine#ateactivity)
    2. [Custom Activities](https://github.com/afollestad/app-theme-engine#custom-activities)
    3. [Fragments](https://github.com/afollestad/app-theme-engine#fragments)
    3. [Toolbars, Menus and Overflows](https://github.com/afollestad/app-theme-engine#toolbars-menus-and-overflows)
    4. [Individual Views and Lists](https://github.com/afollestad/app-theme-engine#individual-views-and-lists)
    5. [DrawerLayout and NavigationViews](https://github.com/afollestad/app-theme-engine#drawerlayout-and-navigationviews)
    6. [Task Description (Recents)](https://github.com/afollestad/app-theme-engine#task-description-recents)
    7. [TabLayouts](https://github.com/afollestad/app-theme-engine#tablayouts)
4. [Tags](https://github.com/afollestad/app-theme-engine#tags)
    1. [Background Colors](https://github.com/afollestad/app-theme-engine#background-colors) 
    2. [Text Colors](https://github.com/afollestad/app-theme-engine#text-colors)
    3. [Text Link Colors](https://github.com/afollestad/app-theme-engine#text-link-colors)
    4. [Text Shadow Colors](https://github.com/afollestad/app-theme-engine#text-shadow-colors)
    5. [Tint Colors](https://github.com/afollestad/app-theme-engine#tint-colors)
    6. [TabLayouts - Continued](https://github.com/afollestad/app-theme-engine#tablayouts-continued)
5. [Pre-made Views](https://github.com/afollestad/app-theme-engine#pre-made-views)
6. [Material Dialogs Integration](https://github.com/afollestad/app-theme-engine#material-dialogs-integration)
7. [Preference UI](https://github.com/afollestad/app-theme-engine#preference-ui)

---

# Gradle Dependency

[ ![JitPack](https://img.shields.io/github/release/afollestad/app-theme-engine.svg?label=jitpack) ](https://jitpack.io/#afollestad/app-theme-engine)
[![Build Status](https://travis-ci.org/afollestad/app-theme-engine.svg)](https://travis-ci.org/afollestad/app-theme-engine)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

#### Repository

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

#### Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {
	...
	compile('com.github.afollestad:app-theme-engine:0.6.0@aar') {
		transitive = true
	}
}
```

---

# Config

By default, Android app themes are static. They cannot be changed dynamically after an APK is built. This 
library allows you to dynamically change theme colors at runtime.

All configuration options are persisted using SharedPreferences, meaning once you set them, you don't have 
to set them again unless you want the value to be changed from what it was previously.

#### Modifiers

Here are a few configuration methods that can be used:

```java
// Context and optional Config key as parameters to config()
ATE.config(this, null) 
    // 0 to disable, sets a default theme for all Activities which use this config key
    .activityTheme(R.style.my_theme)
    // true by default, colors support action bars and toolbars
    .coloredActionBar(true)
    // defaults to colorPrimary attribute value
    .primaryColor(color)
    // when true, primaryColorDark is auto generated from primaryColor
    .autoGeneratePrimaryDark(true) 
    // defaults to colorPrimaryDark attribute value
    .primaryColorDark(color)
    // defaults to colorAccent attribute value
    .accentColor(color)
    // by default, is equal to primaryColorDark's value
    .statusBarColor(color)
    // true by default, setting to false disables coloring even if statusBarColor is set
    .coloredStatusBar(true)
    // dark status bar icons on Marshmallow (API 23)+, auto uses light status bar mode when primaryColor is light
    .lightStatusBarMode(Config.LIGHT_STATUS_BAR_AUTO)
    // sets a color for all toolbars, defaults to primaryColor() value.
    // this also gets correctly applied to CollapsingToolbarLayouts.
    .toolbarColor(color)
    // when on, makes the toolbar navigation icon, title, and menu icons black  
    lightToolbarMode(Config.LIGHT_TOOLBAR_AUTO)
    // by default, is equal to primaryColor unless coloredNavigationBar is false
    .navigationBarColor(color)
    // false by default, setting to false disables coloring even if navigationBarColor is set
    .coloredNavigationBar(false)
    // defaults to ?android:textColorPrimary attribute value
    .textColorPrimary(color)
    // defaults to ?android:textColorPrimaryInverse attribute value
    .textColorPrimaryInverse(color)
    // defaults to ?android:textColorSecondary attribute value
    .textColorSecondary(color)
    // defaults to ?android:textColorSecondaryInverse attribute value
    .textColorSecondaryInverse(color)
    // true by default, setting to false disables the automatic use of the next 4 modifiers.
    .navigationViewThemed(true) 
    // Color of selected NavigationView item icon. Defaults to your accent color.
    .navigationViewSelectedIcon(color)
    // Color of selected NavigationView item text. Defaults to your accent color.
    .navigationViewSelectedText(color)
    // Color of non-selected NavigationView item icon. Defaults to Material Design guideline color.
    .navigationViewNormalIcon(color)
    // Color of non-selected NavigationView item text. Defaults to Material Design guideline color.
    .navigationViewNormalText(color)
    // Background of selected NavigationView item. Defaults to Material Design guideline color.
    .navigationViewSelectedBg(color)
    // application target as parameter, accepts different parameter types/counts
    .apply(this);
```

There's also color resource and color attribute variations of the color modifiers. For an example: 
rather than using `primaryColor(int)`, you could use `primaryColorRes(int)` or `primaryColorAttr(int)` 
in order to pass a value in the format `R.color.resourceValue` or `R.attr.attributeValue`.

#### Keys

The second parameter of `ATE.config(Context, String)` was null above, because it's optional. You can instead 
pass a String of any value as a key. This will allow you to keep separate configurations, which can be applied 
to different Activities, Fragments, Views, at will. Passing null specifies to use the default. You could have 
two Activities which store their own separate theme values independently, or you could have two configurations 
for a light and dark theme.

The [Basics of Applying](https://github.com/afollestad/app-theme-engine#basics-of-applying) section will go over this a bit more.

#### Default Configuration

If you want to setup a default configuration the first time your app is run, you can use code like this:

```java
if (!ATE.config(this, null).isConfigured()) {
    // Setup default options for the default (null) key
}
```

Again, the second parameter of `config(Context, String)` is an optional key.

---

You can also pass a number to `isConfigured(int)` which allows you to perform configuration upgrades/overrides.

```java
if (!ATE.config(this, null).isConfigured(1)) {
    // Setup default options for the default (null) key, version 1
}

// LATER, you want to override previously set defaults for existing users...

if (!ATE.config(this, null).isConfigured(2)) {
    // Setup default options for the default (null) key, version 2
}
```

#### Value Retrieval

Using the `Config` class, you can retrieve your theme values (if you need to for any reason). For an example:

```java
int primaryColor = Config.primaryColor(this, null);
```

And yet again, the second parameter is an optional key.

#### Customizers

Customizers are interfaces your Activities can implement to specify theme values without saving them 
in your Configuration (if you don't want to use separate keys for different screens).

```java
public class MyActivity extends AppCompatActivity 
        implements ATEActivityThemeCustomizer, ATEToolbarCustomizer, ATEStatusBarCustomizer, ATETaskDescriptionCustomizer, ATENavigationBarCustomizer {
    
    @StyleRes
    @Override
    public int getActivityTheme() {
        // Self explanatory. Can be used to override activityTheme() config value if set.
        return R.style.my_activity_theme;
    }
    
    @Config.LightToolbarMode
    @Override
    public int getLightToolbarMode() {
        // When on, toolbar icons and text are made black when the toolbar background is light 
        return Config.LIGHT_TOOLBAR_AUTO;
    }
    
    @ColorInt
    @Override
    public int getToolbarColor() {
        // Normally toolbars are the primary theme color
        return Color.BLACK;
    }
    
    @ColorInt
    @Override
    public int getStatusBarColor() {
        // Normally the status bar is a darker version of the primary theme color
        return Color.RED;
    }
    
    @Config.LightStatusBarMode
    @Override
    public int getLightStatusBarMode() {
        // When on, status bar icons and text are made black when the primary theme color is light (API 23+)
        return Config.LIGHT_STATUS_BAR_AUTO;
    }
    
    @ColorInt
    @Override
    public int getTaskDescriptionColor() {
        // Task description is the color of your Activity's entry in Android's recents screen.
        // Alpha component of returned color is always stripped.
        return Color.GREEN;
    }
    
    @Nullable
    @Override
    public Bitmap getTaskDescriptionIcon() {
        // Returning null falls back to the default (app's launcher icon)
        return null;
    }
    
    @ColorInt
    @Override
    public int getNavigationBarColor() {
        // Navigation bar is usually either black, or equal to the primary theme color
        return Color.BLUE;
    }
}
```

You can override some or all, to fit your needs. But again, you don't *need* to use these if you use keys for 
different configurations.

#### Marking as Changed

In the sample project, you can switch between a light and dark theme. The preference that says whether or 
not the dark theme is active is not part of ATE. The sample project tells MainActivity that it
needs to restart on return from the Settings Screen when the dark theme has been toggled.

```java
// Second parameter is an optional Config key
Config.markChanged(this, null);
```

This method tells all already running Activities that the configuration has been changed since they were 
first opened, without having to edit other configuration values.

You can mark multiple configuration keys as changed:

```java
Config.markChanged(this, "light_theme", "dark_theme");
```

---

# Basics of Applying

Once you have configurations set, you can apply the theme engine to Activities, Fragments, and even 
individual views.

#### ATEActivity

As seen in the sample project, you can have all Activities in your app extends `ATEActivity`. This will do
all the heavy lifting for you.

```java
public class MyActivity extends ATEActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
    }
}
```

If you were to leave the Activity, change theme values (e.g. in a Settings screen), and come back,
the Activity would automatically recreate itself.

You can also change theme values in real time within the Activity using the `ATE.apply()` or `Config#apply()` methods.

---

The [Config](https://github.com/afollestad/app-theme-engine#config) section emphasized the fact that you can
use keys to separate different theme configurations. `ATEActivity` has an optional override method called 
`getATEKey()` which can be used to specify a configuration to use in individual activities.

```java
public class MyActivity extends ATEActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
    }
    
    @Nullable
    @Override
    protected String getATEKey() {
        return getClass().getName();
    }
}
```

The value returned here is used in many other areas too. For an example, there are two versions of almost every method. 
One that accepts a config key, one that doesn't. `ATE.config(Context, String)` is a good example. If you were to use 
 `ATE.config(Context)` and pass the above `Activity` as the `Context`, it would automatically use the return value 
 of `getATEKey()` as the second parameter even though it wasn't directly specified.

#### Custom Activities

If you don't use `ATEActivity`, there's a few things you have to do:

```java
public class MyActivity extends AppCompatActivity {

    private long updateTime = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Apply theming to status bar, nav bar, and task description (recents).
        // Second parameter is optional config key.
        ATE.preApply(this, null);
        super.onCreate(savedInstanceState);
        
        // Always call BEFORE apply()
        setContentView(R.layout.my_layout);
        
        // Store the time the engine was initially applied, so the Activity can restart when coming back after changes
        updateTime = System.currentTimeMillis();
        
        // Apply colors to other views in the Activity. Call after all initial view setup, including toolbars!
        // Second parameter is optional config key.
        ATE.apply(this, null);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // If values were applied/committed (from Config) since the Activity was created, recreate it now
        // Third parameter is optional config key.
        if (ATE.didValuesChange(this, updateTime, null))
            recreate();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        // Applies tinting to menu icons and overflow button if necessary (if toolbar background is light colored)
        ATE.applyMenu(this, getATEKey(), menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        // Applies tinting to widgets (e.g. checkboxes) inside the overflow menu popup
        ATE.applyOverflow(this, getATEKey());
        return super.onMenuOpened(featureId, menu);
    }
}
```

#### Fragments

You can also apply theming to views in a Fragment:

```java
public class MyFragment extends Fragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Second parameter is optional config key.
        ATE.apply(this, null);
    }
}
```

#### Toolbars, Menus, and Overflows

ATE will automatically theme your toolbars or support action bars (if you use `ATEActivity` *or* follow
the directions in [Custom Activities](https://github.com/afollestad/app-theme-engine#custom-activities)). 

If `lightToolbarMode` is set to `Config.LIGHT_TOOLBAR_ON`, or if it's set to *auto* and the toolbar 
background is light: the navigation icon (e.g. back or drawer), title, and menu icons will be tinted 
black. Otherwise they will be white as normal. 

ATE will automatically theme widgets in your overflow menu, such as checkboxes and radio buttons. It 
will also correctly theme `CollapsingToolbarLayout`'s.

#### Individual Views and Lists

You theme individual views like this:

```java
ATE.apply(view, null);
```

The second parameter is an optional Config key.

---

When working with lists, you have to apply the theme engine to each individual view in your adapter.

For *RecyclerViews*:

```java
public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public MyAdapter() {
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View list = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(list);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Setup views
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            // It's recommended you only apply the theme the first time the holder is created.
            // Second parameter is optional key.
            ATE.apply(itemView, null);
        }
    }
}
```

For *ListViews*:

```java
public static class MyAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            // Only apply the first time the view is created.
            // Second parameter is optional key.
            ATE.apply(convertView, null);
        }
        return convertView;
    }
}
```

#### DrawerLayout and NavigationViews

ATE will automatically adapt when your Activity has a `DrawerLayout` at its root. When `coloredStatusBar()` 
is set to true, the primary dark theme color will be applied to the `DrawerLayout` rather than directly to 
the Window status bar. Thus, the status bar will be transparent when the drawer is open, and your theme
color when it's closed. You don't have to manually do anything.

---

If you use `NavigationView` from the Design Support Library, ATE will by default theme it. There are 
NavigationView theming configuration methods discussed in the [Modifiers](https://github.com/afollestad/app-theme-engine#modifiers) 
section. But the default values match the [Material Design Guidelines](https://www.google.com/design/spec/patterns/navigation-drawer.html).

#### Task Description (Recents)

You don't have to do anything extra for this. Your app's Android recents (multi-tasking) entry will 
be themed to your primary color automatically.

There is however an `ATETaskDescriptionCustomizer` that's discussed in the [Customizers](https://github.com/afollestad/app-theme-engine#customizers)
 section.
 
 
#### TabLayouts

ATE will automatically theme your `TabLayout`'s. By default, it will make the selected tab indicator
and tab text white if your TabLayout background is dark. If the TabLayout background is light, it will 
make the indicator and text black. 

If you wrap your `TabLayout` with an `AppBarLayout` and set a background to the `AppBarLayout`, ATE 
will base the default tab indicator/text colors on its background instead.

However, there are tag values you can set to easily modify these colors. They are discussed in 
[TabLayouts - Continued](https://github.com/afollestad/app-theme-engine#tablayouts-continued).

---

# Tags

If you haven't used tags before, they can be applied to views directly from your XML layouts:

```xml
<View
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:tag="tag-value-here" />
```

The theme engine allows you to apply theme colors to any view using tags. **You can even use multiple tags, separated by commas**:

```xml
<View
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:tag="tag-one,tag-two,tag-three" />
```

Here's a list of available tag values:

#### Background Colors

You can change the background of any type of view.

1. `bg_primary_color` - sets the background to the primary color.
2. `bg_primary_color_dark` - sets the background to the primary dark color.
3. `bg_accent_color` - sets the background to the accent color.
4. `bg_text_primary` - sets the background to the primary text color.
5. `bg_text_primary_inverse` - sets the background to the inverse primary text color.
6. `bg_text_secondary` - sets the background to the secondary text color.
7. `bg_text_secondary_inverse` - sets the background to the inverse secondary text color.

#### Text Colors

You can only change the text color of a view that extends `TextView`, which includes `Button`'s.

1. `text_primary_color` - sets the text color to the primary color.
2. `text_primary_color_dark` - sets the text color to the primary dark color.
3. `text_accent_color` - sets the text color to the accent color.
4. `text_primary` - sets the text color to the primary text color.
5. `text_primary_inverse` - sets the text color to the inverse primary text color.
6. `text_secondary` - sets the text color to the secondary text color.
7. `text_secondary_inverse` - sets the text color to the inverse secondary text color.

#### Text Link Colors

This should only really be needed on `TextView'`s, it changes the color of links when TextViews are linkable.

1. `text_link_primary_color` - sets the link text color to the primary color.
2. `text_link_primary_color_dark` - sets the link text color to the primary dark color.
3. `text_link_accent_color` - sets the link text color to the accent color.
4. `text_link_primary` - sets the link text color to the primary text color.
5. `text_link_primary_inverse` - sets the link text color to the inverse primary text color.
6. `text_link_secondary` - sets the link text color to the secondary text color.
7. `text_link_secondary_inverse` - sets the link text color to the inverse secondary text color.

#### Text Shadow Colors

This can be used on `TextView'`s, it changes the `shadowColor` value.

1. `text_shadow_primary_color` - sets the link text color to the primary color.
2. `text_shadow_primary_color_dark` - sets the link text color to the primary dark color.
3. `text_shadow_accent_color` - sets the link text color to the accent color.
4. `text_shadow_primary` - sets the link text color to the primary text color.
5. `text_shadow_primary_inverse` - sets the link text color to the inverse primary text color.
6. `text_shadow_secondary` - sets the link text color to the secondary text color.
7. `text_shadow_secondary_inverse` - sets the link text color to the inverse secondary text color.

#### Tint Colors

You can tint `CheckBox`'s, `RadioButton`'s, `ProgressBar`'s, `EditText`'s, `SeekBar`'s, and `ImageView`'s. 

1. `tint_primary_color` - tints the view with the primary color.
2. `tint_primary_color_dark` - tints the view with the primary dark color.
3. `tint_accent_color` - tints the view with the accent color.
4. `tint_text_primary` - tints the view with the primary text color.
5. `tint_text_primary_inverse` - tints the view with the inverse primary text color.
6. `tint_text_secondary` - tints the view with the secondary text color.
7. `tint_text_secondary_inverse` - tints the view with the inverse secondary text color.

---

Background tints work on any type of view:

1. `bg_tint_primary_color` - tints the view background with the primary color.
2. `bg_tint_primary_color_dark` - tints the view background with the primary dark color.
3. `bg_tint_accent_color` - tints the view background with the accent color.
4. `bg_tint_text_primary` - tints the view background with the primary text color.
5. `bg_tint_text_primary_inverse` - tints the view background with the inverse primary text color.
6. `bg_tint_text_secondary` - tints the view background with the secondary text color.
7. `bg_tint_text_secondary_inverse` - tints the view background with the inverse secondary text color.

---

You can even use background tint selectors:

1. `bg_tint_primary_color_selector_lighter` - tints the view background with a primary color selector, which is lighter when pressed.
2. `bg_tint_primary_color_dark_selector_lighter` - tints the view background with a primary dark color selector, which is lighter when pressed.
3. `bg_tint_accent_color_selector_lighter` - tints the view background with a accent color selector, which is lighter when pressed.
4. `bg_tint_text_primary_selector_lighter` - tints the view background with a primary text color selector, which is lighter when pressed.
5. `bg_tint_text_secondary_selector_lighter` - tints the view background with a secondary text color selector, which is lighter when pressed.
6. `bg_tint_primary_color_selector_darker` - tints the view background with a primary color selector, which is lighter when pressed.
6. `bg_tint_primary_color_selector_darker` - tints the view background with a primary color selector, which is lighter when pressed.
7. `bg_tint_primary_color_dark_selector_darker` - tints the view background with a primary dark color selector, which is lighter when pressed.
8. `bg_tint_accent_color_selector_darker` - tints the view background with a accent color selector, which is lighter when pressed.
9. `bg_tint_text_primary_selector_darker` - tints the view background with a primary text color selector, which is lighter when pressed.
10. `bg_tint_text_primary_inverse_selector_darker` - tints the view background with a inverse primary text color selector, which is lighter when pressed.
11. `bg_tint_text_secondary_selector_darker` - tints the view background with a secondary text color selector, which is lighter when pressed.
12. `bg_tint_text_secondary_inverse_selector_darker` - tints the view background with a inverse secondary text color selector, which is lighter when pressed.

#### TabLayouts - Continued

There are tag values to modify the color of TabLayout tab text:

1. `tab_text_primary_color` - uses the primary color for for the tab text color.
2. `tab_text_primary_color_dark` - uses the primary dark color for for the tab text color.
3. `tab_text_accent_color` - uses the accent color for for the tab text color.
4. `tab_text_primary` - uses the primary text color for for the tab text color.
5. `tab_text_primary_inverse` - uses the inverse primary text color for for the tab text color.
6. `tab_text_secondary` - uses the secondary text color for for the tab text color.
7. `tab_text_secondary_inverse` - uses the inverse secondary color for for the tab text color.

**Note** that the selected tab text will be fully opaque; non-selected tabs will have 25% transparency in 
their text color.

---

1. `tab_indicator_primary_color` - uses the primary color for for the selected tab indicator color.
2. `tab_indicator_primary_color_dark` - uses the primary dark color for for the selected tab indicator color.
3. `tab_indicator_accent_color` - uses the accent color for for the selected tab indicator color.
4. `tab_indicator_text_primary` - uses the primary text color for for the selected tab indicator color.
5. `tab_indicator_text_primary_inverse` - uses the inverse primary text color for for the selected tab indicator color.
6. `tab_indicator_text_secondary` - uses the secondary text color for for the selected tab indicator color.
7. `tab_indicator_text_secondary_inverse` - uses the inverse secondary text color for for the selected tab indicator color.

**Note** that the indicator color is used for the color of tab icons, if they are used.

---

# Pre-made Views

Seven views come stock with this library:

1. `ATECheckBox` - tints itself to the accent color.
2. `ATERadioButton` - tints itself to the accent color.
3. `ATEEditText` - tints itself to the accent color
4. `ATEProgressBar` - tints itself to the accent color.
5. `ATESeekBar` - tints itself to the accent color.
6. `ATEPrimaryTextView` - sets its text color to the primary text color.
7. `ATESecondaryTextView` - sets its text color to the secondary text color.

All that they really do is set their own tag to one of the tag values in the previous section,
and then apply theming to themselves using the individual view `apply()` method.

---

If you use Config keys in your app, you may want to apply theme to these pre-made views:

```xml
<com.afollestad.appthemeengine.ATECheckBox
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:ateKey_checkBox="your-config-key-here" />
```

All of the different views have a different suffix to `app:ateKey_`, since using the same attribute 
name for all of them would result in duplicate errors.

You can even use theme attributes in your Activity themes and reference them for this value (which is 
done in the sample project since different keys are used for a light and dark theme).

---

# Material Dialogs Integration

If you want your primary/secondary text color and accent color to be applied to dialogs created using
my [Material Dialogs](https://github.com/afollestad/material-dialogs) library, it only takes one config option:

```java
ATE.config(this, null)
    .usingMaterialDialogs(true)
    .commit();
```

---

# Preference UI

As seen in the sample project, ATE includes a set of pre-made Preference classes that handle theming 
their own UI in your settings screen. They also use [Material Dialogs](https://github.com/afollestad/material-dialogs), 
and enable Material Dialogs integration automatically when used. The preference classes include:

1. `ATEDialogPreference`
2. `ATEListPreference`
3. `ATECheckBoxPreference`
4. `ATEEditTextPreference`
5. `ATEMultiSelectPreference`
6. `ATEColorPreference` – doesn't actually display a dialog, just displays a color indicator on the right. Setting display color and displaying a dialog is done from the settings screen.

In your settings screen, the title will be themed to the primary text color, the summary will be 
themed to the secondary text color. The actual dialogs are themed using the logic in 
[Material Dialogs Integration](https://github.com/afollestad/app-theme-engine#material-dialogs-integration).

---

Like pre-made views, you can specify config keys through your XML. For an example, you can use a 
theme attribute set from your Activity theme, which specifies a string (see the sample project):

```xml
<com.afollestad.appthemeengine.prefs.ATEColorPreference
        android:key="primary_color"
        android:persistent="false"
        android:summary="@string/primary_color_summary"
        android:title="@string/primary_color"
        app:ateKey_pref_color="?ate_key" />
```

`app:ateKey_pref_` is suffixed with the preference type. Android Studio will auto complete the name for you for other preference types.