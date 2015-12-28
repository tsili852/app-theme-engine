package com.afollestad.appthemeengine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.afollestad.appthemeengine.processors.DefaultProcessor;
import com.afollestad.appthemeengine.processors.ListViewProcessor;
import com.afollestad.appthemeengine.processors.NavigationViewProcessor;
import com.afollestad.appthemeengine.processors.Processor;
import com.afollestad.appthemeengine.processors.RecyclerViewProcessor;
import com.afollestad.appthemeengine.processors.ScrollViewProcessor;
import com.afollestad.appthemeengine.processors.ToolbarProcessor;

import java.util.HashMap;

/**
 * @author Aidan Follestad (afollestad)
 */
class ATEBase {

    private static HashMap<String, Processor> mProcessors;

    private static void initProcessors() {
        mProcessors = new HashMap<>();
        mProcessors.put("[default]", new DefaultProcessor());
        mProcessors.put(ScrollView.class.getName(), new ScrollViewProcessor());
        mProcessors.put(ListView.class.getName(), new ListViewProcessor());
        mProcessors.put(RecyclerView.class.getName(), new RecyclerViewProcessor());
        mProcessors.put(Toolbar.class.getName(), new ToolbarProcessor());
        mProcessors.put(NavigationView.class.getName(), new NavigationViewProcessor());
    }

    @SuppressWarnings("unchecked")
    @Nullable
    protected static <T extends View> Processor<T, ?> getProcessor(@Nullable Class<T> viewClass) {
        if (mProcessors == null)
            initProcessors();
        if (viewClass == null)
            return mProcessors.get("[default]");
        Processor processor = mProcessors.get(viewClass.getName());
        if (processor != null)
            return processor;
        Class<?> current = viewClass.getSuperclass();
        while (current != null && processor == null) {
            processor = mProcessors.get(current.getName());
            current = current.getSuperclass();
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