package com.fxw.kotlinlearn.widget.divider;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 标题:     绘制宫格分割线方法
 * 介绍:
 * 作者:     傅显文
 * 创建时间: 2017/6/14 16:29
 */

interface DiverItemInterface {
    /**
     * 绘制水平分割线方法
     *
     * @param canvas Canvas
     * @param parent RecyclerView
     */
    void drawGridVertical(Canvas canvas, RecyclerView parent);

    /**
     * 绘制横向分割线方法
     *
     * @param canvas Canvas
     * @param parent RecyclerView
     */
    void drawGridHorizontal(Canvas canvas, RecyclerView parent);

    /**
     * 计算列数
     *
     * @param parent 控件
     * @return 返回列数
     */
    int getSpanCount(RecyclerView parent);

    /**
     * 判断是否是最后一列
     *
     * @param parent     recyclerview
     * @param pos        当前下标
     * @param spanCount  一行的条目数
     * @param childCount 总条目数
     * @return true      是最后一列
     */
    boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount);

    /**
     * 宫格类型判断是否是最后一行
     *
     * @param parent     RecyclerView
     * @param pos        当前下标
     * @param spanCount  列数
     * @param childCount 总数item个数
     * @return true      是最后一行
     */
    boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount);

    /**
     * 宫格类型判断是否是第一列
     *
     * @param parent     RecyclerView
     * @param pos        当前下标
     * @param spanCount  列数
     * @param childCount 总数item个数
     * @return true      是最后一列
     */
    boolean isFirstColum(RecyclerView parent, int pos, int spanCount, int childCount);

    /**
     * 宫格类型判断是否是第一行
     *
     * @param parent     RecyclerView
     * @param pos        当前下标
     * @param spanCount  列数
     * @param childCount 总数item个数
     * @return true      是第一行
     */
    boolean isFirstRaw(RecyclerView parent, int pos, int spanCount, int childCount);

    /**
     * Vertical宫格 分割线绘制 {@link android.support.v7.widget.GridLayoutManager}
     * {@link android.support.v7.widget.StaggeredGridLayoutManager}
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    void setVerticalGridOffset(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state);

    /**
     * Horizontal宫格 分割线绘制{@link android.support.v7.widget.GridLayoutManager}
     * {@link android.support.v7.widget.StaggeredGridLayoutManager}
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    void setHorizontalGridOffset(Rect outRect, View view, RecyclerView parent,
                                 RecyclerView.State state);

    /**
     * 普通列表分割线绘制{@link android.support.v7.widget.LinearLayoutManager}
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    void setLinearOffset(Rect outRect, View view, RecyclerView parent,
                         RecyclerView.State state);

    /**
     * Vertical 普通列表分割线绘制{@link android.support.v7.widget.LinearLayoutManager}
     *
     * @param canvas
     * @param parent
     */
    void drawLinearVertical(Canvas canvas, RecyclerView parent);

    /**
     * Horizontal 普通列表分割线绘制 {@link android.support.v7.widget.LinearLayoutManager}
     *
     * @param canvas
     * @param parent
     */
    void drawLinearHorizontal(Canvas canvas, RecyclerView parent);
}

