package com.afollestad.appthemeengine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.afollestad.appthemeengine.processors.DefaultProcessor;
import com.afollestad.appthemeengine.processors.ListViewProcessor;
import com.afollestad.appthemeengine.processors.NavigationViewProcessor;
import com.afollestad.appthemeengine.processors.NestedScrollViewProcessor;
import com.afollestad.appthemeengine.processors.Processor;
import com.afollestad.appthemeengine.processors.RecyclerViewProcessor;
import com.afollestad.appthemeengine.processors.ScrollViewProcessor;
import com.afollestad.appthemeengine.processors.SearchViewProcessor;
import com.afollestad.appthemeengine.processors.TabLayoutProcessor;
import com.afollestad.appthemeengine.processors.ToolbarProcessor;

import java.util.HashMap;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATEBase {

    protected final static String DEFAULT_PROCESSOR = "[default]";

    private static HashMap<String, Processor> mProcessors;

    private static void initProcessors() {
        mProcessors = new HashMap<>();
        mProcessors.put(DEFAULT_PROCESSOR, new DefaultProcessor());
        mProcessors.put(ScrollView.class.getName(), new ScrollViewProcessor());
        mProcessors.put(NestedScrollView.class.getName(), new NestedScrollViewProcessor());
        mProcessors.put(ListView.class.getName(), new ListViewProcessor());
        mProcessors.put(RecyclerView.class.getName(), new RecyclerViewProcessor());
        mProcessors.put(Toolbar.class.getName(), new ToolbarProcessor());
        mProcessors.put(NavigationView.class.getName(), new NavigationViewProcessor());
        mProcessors.put(TabLayout.class.getName(), new TabLayoutProcessor());
        mProcessors.put(SearchView.class.getName(), new SearchViewProcessor());
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends View> Processor<T, ?> getProcessor(@Nullable Class<T> viewClass) {
        if (mProcessors == null)
            initProcessors();
        if (viewClass == null)
            return mProcessors.get(DEFAULT_PROCESSOR);
        Processor processor = mProcessors.get(viewClass.getName());
        if (processor != null)
            return processor;
        Class<?> current = viewClass;
        while (true) {
            current = current.getSuperclass();
            if (current == null) break;
            processor = mProcessors.get(current.getName());
            if (processor != null) break;
        }
        return processor;
    }

    public static <T extends View> void registerProcessor(@NonNull Class<T> viewCls, @NonNull Processor<T, ?> processor) {
        if (mProcessors == null)
            initProcessors();
        mProcessors.put(viewCls.getName(), processor);
    }

    protected static Class<?> didPreApply = null;
    protected static Toolbar mToolbar = null;
}