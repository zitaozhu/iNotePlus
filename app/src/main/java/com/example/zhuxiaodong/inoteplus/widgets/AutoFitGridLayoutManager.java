package com.example.zhuxiaodong.inoteplus.widgets;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zhuxiaodong on 2018/8/8.
 */

public class AutoFitGridLayoutManager extends GridLayoutManager {

    private int columnSpan;
    private boolean columnSpanChanged = true;
    
    public AutoFitGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        setColumnSpan(columnSpan);
    }

    public void setColumnSpan(int newColumnSpan) {
        if (newColumnSpan > 0 && newColumnSpan != columnSpan) {
            columnSpan = newColumnSpan;
            columnSpanChanged = true;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (columnSpanChanged && columnSpan > 0) {
            int totalSpace;
            if (getOrientation() == VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            } else {
                totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            }
            int spanCount = Math.max(1, totalSpace / columnSpan);
            setSpanCount(spanCount);
            columnSpanChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}
