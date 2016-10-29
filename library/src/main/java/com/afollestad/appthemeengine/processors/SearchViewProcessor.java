package com.afollestad.appthemeengine.processors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.appthemeengine.util.TintHelper;
import com.afollestad.appthemeengine.util.Util;

import java.lang.reflect.Field;

/**
 * @author Aidan Follestad (afollestad)
 */
public class SearchViewProcessor implements Processor<View, Integer> {

    private void tintImageView(Object target, Field field, int tintColor) throws Exception {
        field.setAccessible(true);
        final ImageView imageView = (ImageView) field.get(target);
        if (imageView.getDrawable() != null)
            imageView.setImageDrawable(TintHelper.tintDrawable(imageView.getDrawable(), tintColor));
    }

    @Override
    public void process(@NonNull Context context, @Nullable String key, @Nullable View target, @Nullable Integer tintColor) {
        if (target == null || tintColor == null) return;
        final Class<?> cls = target.getClass();
        try {
            final Field mSearchSrcTextViewField = cls.getDeclaredField("mSearchSrcTextView");
            mSearchSrcTextViewField.setAccessible(true);
            final TextView mSearchSrcTextView = (TextView) mSearchSrcTextViewField.get(target);
            mSearchSrcTextView.setTextColor(tintColor);
            mSearchSrcTextView.setHintTextColor(Util.adjustAlpha(tintColor, 0.5f));

            Field field = cls.getDeclaredField("mSearchButton");
            tintImageView(target, field, tintColor);
            field = cls.getDeclaredField("mGoButton");
            tintImageView(target, field, tintColor);
            field = cls.getDeclaredField("mCloseButton");
            tintImageView(target, field, tintColor);
            field = cls.getDeclaredField("mVoiceButton");
            tintImageView(target, field, tintColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
