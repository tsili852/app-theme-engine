package com.afollestad.appthemeengine.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import android.widget.ScrollView;

import java.lang.reflect.Field;

/**
 * @author Aidan Follestad (afollestad)
 */
public class EdgeGlowUtil {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setEdgeGlowColor(@NonNull AbsListView listView, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field edgeGlowTop = AbsListView.class.getDeclaredField("mEdgeGlowTop");
                edgeGlowTop.setAccessible(true);
                Field edgeGlowBottom = AbsListView.class.getDeclaredField("mEdgeGlowBottom");
                edgeGlowBottom.setAccessible(true);

                EdgeEffect ee = (EdgeEffect) edgeGlowTop.get(listView);
                if (ee == null) {
                    ee = new EdgeEffect(listView.getContext());
                    edgeGlowTop.set(listView, ee);
                }
                ee.setColor(color);
                ee = (EdgeEffect) edgeGlowBottom.get(listView);
                if (ee == null) {
                    ee = new EdgeEffect(listView.getContext());
                    edgeGlowBottom.set(listView, ee);
                }
                ee.setColor(color);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setEdgeGlowColor(@NonNull ScrollView scrollView, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field edgeGlowTop = ScrollView.class.getDeclaredField("mEdgeGlowTop");
                edgeGlowTop.setAccessible(true);
                Field edgeGlowBottom = ScrollView.class.getDeclaredField("mEdgeGlowBottom");
                edgeGlowBottom.setAccessible(true);

                EdgeEffect ee = (EdgeEffect) edgeGlowTop.get(scrollView);
                if (ee == null) {
                    ee = new EdgeEffect(scrollView.getContext());
                    edgeGlowTop.set(scrollView, ee);
                }
                ee.setColor(color);
                ee = (EdgeEffect) edgeGlowBottom.get(scrollView);
                if (ee == null) {
                    ee = new EdgeEffect(scrollView.getContext());
                    edgeGlowBottom.set(scrollView, ee);
                }
                ee.setColor(color);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setEdgeGlowColor(@NonNull NestedScrollView scrollView, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field edgeGlowTop = NestedScrollView.class.getDeclaredField("mEdgeGlowTop");
                edgeGlowTop.setAccessible(true);
                Field edgeGlowBottom = NestedScrollView.class.getDeclaredField("mEdgeGlowBottom");
                edgeGlowBottom.setAccessible(true);

                EdgeEffectCompat ee = (EdgeEffectCompat) edgeGlowTop.get(scrollView);
                if (ee == null) {
                    ee = new EdgeEffectCompat(scrollView.getContext());
                    edgeGlowTop.set(scrollView, ee);
                }
                setEdgeGlowColor(ee, color);
                ee = (EdgeEffectCompat) edgeGlowBottom.get(scrollView);
                if (ee == null) {
                    ee = new EdgeEffectCompat(scrollView.getContext());
                    edgeGlowBottom.set(scrollView, ee);
                }
                setEdgeGlowColor(ee, color);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void setEdgeGlowColor(@NonNull RecyclerView recyclerView, final @ColorInt int color,
                                        @Nullable RecyclerView.OnScrollListener scrollListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (scrollListener == null) {
                scrollListener = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        EdgeGlowUtil.setEdgeGlowColor(recyclerView, color, this);
                    }
                };
                recyclerView.addOnScrollListener(scrollListener);
                return;
            }

            try {
                Field leftGlow = RecyclerView.class.getDeclaredField("mLeftGlow");
                leftGlow.setAccessible(true);
                Field topGlow = RecyclerView.class.getDeclaredField("mTopGlow");
                topGlow.setAccessible(true);
                Field rightGlow = RecyclerView.class.getDeclaredField("mRightGlow");
                rightGlow.setAccessible(true);
                Field bottomGlow = RecyclerView.class.getDeclaredField("mBottomGlow");
                bottomGlow.setAccessible(true);

                EdgeEffectCompat ee = (EdgeEffectCompat) topGlow.get(recyclerView);
                if (ee == null) {
                    ee = new EdgeEffectCompat(recyclerView.getContext());
                    topGlow.set(recyclerView, ee);
                }
                setEdgeGlowColor(ee, color);

                ee = (EdgeEffectCompat) bottomGlow.get(recyclerView);
                if (ee == null) {
                    ee = new EdgeEffectCompat(recyclerView.getContext());
                    bottomGlow.set(recyclerView, ee);
                }
                setEdgeGlowColor(ee, color);

                ee = (EdgeEffectCompat) rightGlow.get(recyclerView);
                if (ee == null) {
                    ee = new EdgeEffectCompat(recyclerView.getContext());
                    rightGlow.set(recyclerView, ee);
                }
                setEdgeGlowColor(ee, color);

                ee = (EdgeEffectCompat) leftGlow.get(recyclerView);
                if (ee == null) {
                    ee = new EdgeEffectCompat(recyclerView.getContext());
                    leftGlow.set(recyclerView, ee);
                }
                setEdgeGlowColor(ee, color);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void setEdgeGlowColor(@NonNull EdgeEffectCompat edgeEffect, @ColorInt int color) throws Exception {
        Field field = EdgeEffectCompat.class.getDeclaredField("mEdgeEffect");
        field.setAccessible(true);
        EdgeEffect effect = (EdgeEffect) field.get(edgeEffect);
        if (effect != null)
            effect.setColor(color);
    }
}
