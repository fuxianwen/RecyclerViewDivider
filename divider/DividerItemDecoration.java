package com.fxw.kotlinlearn.widget.divider;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 标题:     普通列表diver
 * 介绍:
 * 作者:     傅显文
 * 创建时间: 2017/6/6 16:02
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration implements DiverItemInterface {
    private static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    private static final int VERTICAL = LinearLayout.VERTICAL;

    private Builder mBuilder;
    private float mDividerHeight; //分割线高度
    private Drawable mDivider;//分割线

    private boolean mLastDiverDraw = false;//默认不绘制最后一条数据分割线
    private float mLastDiverHeight = 0;//最后一一行分割线高度
    private boolean mFirstDiverDraw = false;//列表头部是否有分割线
    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;
    private final Rect mBounds = new Rect();
    private float mVerticalSpace;//水平间距
    private boolean mDrawLastCloumnDiver;//设置最后一列是否展示分割线
    private boolean mDrawFirstCloumnDiver;//第一列是否展示分割线
    private boolean mDrawFirstRowDiver;//设置第一行是否展示分割线
    private boolean mDrawLastRowDiver;//设置最后一行是否展示分割线
    private int mDiverLeftSpace;//置分割线离左边的间距
    private int mDiverRightSpace;//置分割线离右边边的间距

    private DividerItemDecoration(Builder builder) {
        mBuilder = builder;
        mDividerHeight = builder.mDividerHeight;
        mDivider = builder.mDivider;
        mLastDiverDraw = builder.mLastDiverDraw;
        mFirstDiverDraw = builder.mFirstDiverDraw;
        mLastDiverHeight = builder.mLastDiverHeight;
        mVerticalSpace = builder.mVerticalSpace;
        mDrawLastCloumnDiver = builder.mDrawLastCloumnDiver;
        mDrawFirstCloumnDiver = builder.mDrawFirstCloumnDiver;
        mDrawFirstRowDiver = builder.mDrawFirstRowDiver;
        mDrawLastRowDiver = builder.mDrawLastRowDiver;
        mDiverLeftSpace = builder.mDiverLeftSpace;
        mDiverRightSpace = builder.mDiverRightSpace;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            mOrientation = ((GridLayoutManager) parent.getLayoutManager()).getOrientation();
            drawGridVertical(c, parent);
            drawGridHorizontal(c, parent);
            return;
        }

        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            mOrientation = ((StaggeredGridLayoutManager) parent.getLayoutManager())
                    .getOrientation();
            drawGridVertical(c, parent);
            drawGridHorizontal(c, parent);
            return;
        }

        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            mOrientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (mOrientation == VERTICAL) {
                drawLinearVertical(c, parent);
            } else {
                drawLinearHorizontal(c, parent);
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawLinearVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int left;
        int right;
        int bottom;
        int top;
//        if (parent.getClipToPadding()) {
        left = parent.getPaddingLeft() + mDiverLeftSpace;
        right = parent.getWidth() - parent.getPaddingRight() - mDiverRightSpace;
//        canvas.clipRect(left, parent.getPaddingTop(), right,
//                parent.getHeight() - parent.getPaddingBottom());
//        } else {
//            left = 0;
//            right = parent.getWidth();
//        }
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));

            if (i == childCount - 1 && !mLastDiverDraw)
                top = (int) (bottom - mLastDiverHeight);
            else
                top = (int) (bottom - mDividerHeight);
            mDivider.setBounds(left , top, right, bottom);
            mDivider.draw(canvas);
            //如果列表顶部需要绘制分割线，并且是第一条数据
            if (mFirstDiverDraw && i == 0) {
                top = mBounds.top;
                bottom = (int) (top + mDividerHeight);
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }

        }
        canvas.restore();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawLinearHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int top;
        int bottom;
        int right;
        int left;
//        if (parent.getClipToPadding()) {
        top = parent.getPaddingTop() + mDiverRightSpace;
        bottom = parent.getHeight() - parent.getPaddingBottom() - mDiverLeftSpace;
//        canvas.clipRect(parent.getPaddingLeft(), top,
//                parent.getWidth() - parent.getPaddingRight(), bottom);
//        } else {
//            top = 0;
//            bottom = parent.getHeight();
//        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            right = mBounds.right + Math.round(ViewCompat.getTranslationX(child));

            if (i == childCount - 1 && !mLastDiverDraw)
                left = (int) (right - mLastDiverHeight);
            else
                left = (int) (right - mDividerHeight);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);

            //如果列表顶部需要绘制分割线，并且是第一条数据
            if (mFirstDiverDraw && i == 0) {
                right = mBounds.left;
                left = (int) (right - parent.getPaddingLeft());
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            mOrientation = ((GridLayoutManager) parent.getLayoutManager()).getOrientation();
            if (mOrientation == VERTICAL)
                setVerticalGridOffset(outRect, view, parent, state);
            else
                setHorizontalGridOffset(outRect, view, parent, state);
            return;
        }

        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            mOrientation = ((StaggeredGridLayoutManager) parent.getLayoutManager())
                    .getOrientation();
            if (mOrientation == VERTICAL)
                setVerticalGridOffset(outRect, view, parent, state);
            else
                setHorizontalGridOffset(outRect, view, parent, state);
            return;
        }

        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            mOrientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            setLinearOffset(outRect, view, parent, state);
        }
    }


    @Override
    public void setLinearOffset(Rect outRect, View view, RecyclerView parent,
                                RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams())
                .getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();
        if (mOrientation == VERTICAL) {
            //如果只有一条数据 最后一条数据不绘制，第一条要绘制
            if (!mLastDiverDraw && childCount == 1 && mFirstDiverDraw) {
                outRect.set(0, (int) mDividerHeight, 0, (int) mLastDiverHeight);
                return;
            }
            //最后一条不绘制
            if (!mLastDiverDraw && itemPosition == childCount - 1) {
                outRect.set(0, 0, 0, (int) mLastDiverHeight);
                return;
            }
            //第一条绘制 并且 是第一条数据
            if (mFirstDiverDraw && itemPosition == 0) {
                outRect.set(0, (int) mDividerHeight, 0, (int) mDividerHeight);
                return;
            }
            outRect.set(0, 0, 0, (int) mDividerHeight);
        } else {
            //最后一条不绘制
            if (!mLastDiverDraw && itemPosition == childCount - 1) {
                outRect.set(0, 0, (int) mLastDiverHeight, 0);
                return;
            }
            outRect.set(0, 0, (int) mDividerHeight, 0);
        }
    }

    @Override
    public void drawGridVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top;
            int bottom;
            int left;
            int right;
            top = child.getTop() - params.topMargin;
            bottom = child.getBottom() + params.bottomMargin;
            if (mOrientation == VERTICAL) {

                if (mDrawFirstCloumnDiver && isFirstColum(parent, i, getSpanCount(parent), childCount)) {
                    right = child.getLeft() + params.leftMargin;
                    left = (int) (right - mDividerHeight);
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                left = child.getRight() + params.rightMargin;
                if (!mDrawLastCloumnDiver && isLastColum(parent, i, getSpanCount(parent), childCount))
                    right = left;
                else
                    right = left + (int) mDividerHeight;
            } else {
                if (mFirstDiverDraw || mDrawFirstRowDiver && isFirstRaw(parent, i, getSpanCount(parent), childCount)) {
                    left = (int) (child.getLeft() - mVerticalSpace);
                    right = child.getLeft();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                left = child.getRight() + params.rightMargin;
                if (!mDrawLastRowDiver && !mLastDiverDraw && isLastRaw(parent, i, getSpanCount(parent), childCount))
                    right = left + (int) mLastDiverHeight;
                else
                    right = left + (int) mVerticalSpace;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);

        }
    }

    @Override
    public void drawGridHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int left;
            int right;
            int top;
            int bottom;

            left = child.getLeft() - params.leftMargin;
            if (mOrientation == VERTICAL) {
                if (!mDrawLastCloumnDiver && isLastColum(parent, i, getSpanCount(parent), childCount))
                    right = child.getRight() + params.rightMargin;
                else
                    right = (int) (child.getRight() + params.rightMargin + mDividerHeight);

                if (mDrawFirstCloumnDiver && isFirstColum(parent, i, getSpanCount(parent), childCount)) {
                    left = (int) (left - mDividerHeight);
                }
                if (mDrawFirstRowDiver || mFirstDiverDraw && isFirstRaw(parent, i, getSpanCount(parent), childCount)) {
                    top = child.getTop();
                    bottom = (int) (top - mVerticalSpace);
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                top = child.getBottom() + params.bottomMargin;
                if (!mDrawLastRowDiver && !mLastDiverDraw && isLastRaw(parent, i, getSpanCount(parent), childCount))
                    bottom = (int) (top + mLastDiverHeight);
                else
                    bottom = (int) (top + mVerticalSpace);

            } else {
                if (!mDrawLastRowDiver && !mLastDiverDraw && isLastRaw(parent, i, getSpanCount(parent), childCount))
                    right = (int) (child.getRight() + params.rightMargin + mLastDiverHeight);
                else
                    right = (int) (child.getRight() + params.rightMargin + mVerticalSpace);

                if (mFirstDiverDraw || mDrawFirstRowDiver && isFirstRaw(parent, i, getSpanCount(parent), childCount))
                    left = (int) (left - mVerticalSpace);

                if (mDrawFirstCloumnDiver && isFirstColum(parent, i, getSpanCount(parent), childCount)) {
                    top = child.getTop();
                    bottom = (int) (top - mDividerHeight);
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                top = child.getBottom() + params.bottomMargin;
                if (!mDrawLastCloumnDiver && isLastColum(parent, i, getSpanCount(parent), childCount))
                    bottom = top;
                else {
                    bottom = (int) (top + mDividerHeight);

                }

            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    @Override
    public int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    @Override
    public boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        // 如果是最后一列，则不需要绘制右边
        return (pos + 1) % spanCount == 0;
    }

    @Override
    public boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        // 如果是最后一行，则不需要绘制底部
//            if (childCount % spanCount == 0) {
//                childCount = childCount - spanCount;
//            } else {
//                childCount = childCount - childCount % spanCount;
//            }
//            return pos >= childCount;
        // 如果是最后一行，则不需要绘制底部
        return ((pos + spanCount) / spanCount) >= ((childCount + spanCount - 1) / spanCount);
    }

    @Override
    public boolean isFirstColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        return (pos + 1) % spanCount == 1;
    }

    @Override
    public boolean isFirstRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        return (pos * 1.0f) / spanCount < 1;

    }

    @Override
    public void setVerticalGridOffset(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int itemPosition = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int column = itemPosition % spanCount; // item column

        int bottom = 0;
        int top = 0;
        int left = 0;
        int right = 0;
        left = (int) (column * mDividerHeight / spanCount);
        right = (int) (mDividerHeight - (column + 1) * mDividerHeight / spanCount);
        bottom = (int) mVerticalSpace;
        if (!mLastDiverDraw && isLastRaw(parent, itemPosition, spanCount, childCount))
            bottom = (int) mLastDiverHeight;
        if (mFirstDiverDraw && isFirstRaw(parent, itemPosition, spanCount, childCount))
            top = (int) mVerticalSpace;
        outRect.set(left, top, right, bottom);
    }

    @Override
    public void setHorizontalGridOffset(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int column = itemPosition % spanCount; // item column

        int bottom = 0;
        int top = 0;
        int left = 0;
        int right = 0;
        if (isFirstColum(parent, itemPosition, spanCount, childCount)) {
            top = 0;
            bottom = (int) (mDividerHeight / spanCount * (spanCount - 1));
        } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {
            top = (int) (mDividerHeight / spanCount * (spanCount - 1));
            bottom = 0;
        } else {
            top = (int) (column * mDividerHeight / spanCount);
            bottom = (int) (mDividerHeight - (column + 1) * mDividerHeight / spanCount);
        }
        right = (int) mVerticalSpace;
        if (!mLastDiverDraw && isLastRaw(parent, itemPosition, spanCount, childCount))
            right = (int) mLastDiverHeight;
        outRect.set(left, top, right, bottom);
    }


    public static class Builder {
        private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private float mDividerHeight;//分割线高度
        private Drawable mDivider;//分割线

        private boolean mLastDiverDraw = false;//默认不绘制最后一条数据分割线
        private float mLastDiverHeight = 0;//最后一条分割线高度
        private boolean mFirstDiverDraw = false;//列表头部是否有分割线
        /**
         * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
         */
        private Context mContext;
        private float mVerticalSpace;//水平间距
        private boolean mDrawLastCloumnDiver;//设置最后一列是否展示分割线
        private boolean mDrawFirstCloumnDiver;//第一列是否展示分割线
        private boolean mDrawFirstRowDiver;//设置第一行是否展示分割线
        private boolean mDrawLastRowDiver;//设置最后一行是否展示分割线
        private int mDiverLeftSpace;//置分割线离左边的间距
        private int mDiverRightSpace;//置分割线离右边边的间距

        public Builder(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            mContext = context;
            a.recycle();

            if (mDivider != null) {
                setDiverHeight(mDivider.getIntrinsicHeight());
            }
        }

        /**
         * 设置分割线
         *
         * @param color  分割线颜色
         * @param height 分割线高度
         */
        public Builder(@ColorInt int color, float height) {
            mDivider = new ColorDrawable(color);
            setDiverHeight(height);
        }

        /**
         * Sets the {@link Drawable} for this divider.
         *
         * @param drawable Drawable that should be used as a divider.
         */
        public Builder setDrawable(@DrawableRes int drawable) {
            mDivider = ContextCompat.getDrawable(mContext, drawable);
            setDiverHeight(mDivider.getIntrinsicHeight());
            return this;
        }

        /**
         * 设置分割线离左边的间距 仅仅支持 LinearLayoutManager类型
         *
         * @param space
         * @return
         */
        public Builder setDiverLeftSpace(int space) {
            mDiverLeftSpace = space;
            return this;
        }

        /**
         * 设置分割线离右边边的间距 仅仅支持 LinearLayoutManager类型
         *
         * @param space
         * @return
         */
        public Builder setDiverRightSpace(int space) {
            mDiverRightSpace = space;
            return this;
        }

        /**
         * 设置是否绘制最后一条数据分割线
         *
         * @param drawLastDiver true 绘制  false不绘制  默认false
         * @return
         */
        public Builder setDrawLastDiver(boolean drawLastDiver) {
            setDrawLastDiver(drawLastDiver, 0);
            return this;
        }

        /**
         * 设置是否绘制最后一条数据分割线
         *
         * @param drawLastDiver   true 绘制  false不绘制  默认false
         *                        如果lastDiverHeight != 0 最后一条分割线会绘制
         *                        解决矩形分割线一条数据有矩形分割线很丑问题
         * @param lastDiverHeight 最后一条分割线高度 0 等于不绘制 仅当drawLastDiver = false 有效
         * @return
         */
        public Builder setDrawLastDiver(boolean drawLastDiver, int lastDiverHeight) {
            mLastDiverDraw = drawLastDiver;
            mLastDiverHeight = lastDiverHeight;
            return this;
        }

        /**
         * 设置头部是否绘制数据分割线
         * 只支持VERTICAL情况
         * HORIZONTAL可以配合padding设置是否展示头部
         *
         * @param drawFirstDiver true 绘制  false不绘制  默认false
         * @return
         */
        public Builder setDrawFirstDiver(boolean drawFirstDiver) {
            mFirstDiverDraw = drawFirstDiver;
            return this;
        }

        /**
         * 设置分割线高度
         *
         * @param height
         * @return
         */
        public Builder setDiverHeight(float height) {
            mDividerHeight = height;
            mVerticalSpace = height;
            return this;
        }

        /**
         * 设置宫格类型横向间距
         *
         * @param horizontalSpace
         * @return
         */
        public Builder setHorizontalSpace(int horizontalSpace) {
            mDividerHeight = horizontalSpace;
            return this;
        }

        /**
         * 设置宫格类型水平间距
         *
         * @param verticalSpace
         * @return
         */
        public Builder setVerticalSpace(float verticalSpace) {
            mVerticalSpace = verticalSpace;
            return this;
        }

        /**
         * 设置宫格类型最后一列是否展示分割线 配合paddingright 使用
         *
         * @param drawRightLastDiver
         * @return
         */
        public Builder setDrawLastCloumnDiver(boolean drawRightLastDiver) {
            mDrawLastCloumnDiver = drawRightLastDiver;
            return this;
        }

        /**
         * 设置宫格类型第一列是否展示分割线 配合paddingleft 使用
         *
         * @param drawLeftFirstDiver
         * @return
         */
        public Builder setDrawFirstCloumnDiver(boolean drawLeftFirstDiver) {
            mDrawFirstCloumnDiver = drawLeftFirstDiver;
            return this;
        }

        /**
         * 设置宫格类型第一行是否展示分割线 配合paddingleft 使用
         *
         * @param drawLeftFirstDiver
         * @return
         */
        public Builder setDrawFirstRowDiver(boolean drawLeftFirstDiver) {
            mDrawFirstRowDiver = drawLeftFirstDiver;
            return this;
        }

        /**
         * 设置宫格类型第一行是否展示分割线 配合paddingleft 使用
         *
         * @param drawLeftFirstDiver
         * @return
         */
        public Builder setDrawLastRowDiver(boolean drawLeftFirstDiver) {
            mDrawLastRowDiver = drawLeftFirstDiver;
            return this;
        }

        public DividerItemDecoration build() {
            return new DividerItemDecoration(this);
        }
    }

}
