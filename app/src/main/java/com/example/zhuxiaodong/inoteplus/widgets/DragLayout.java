package com.example.zhuxiaodong.inoteplus.widgets;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.zhuxiaodong.inoteplus.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by zhuxiaodong on 2018/8/5.
 */

public class DragLayout extends FrameLayout{
    private static final boolean IS_SHOW_SHADOW = true;
    //handles the gestures
    private GestureDetectorCompat gestureDetector;
    //helper whtn dragging the view
    private ViewDragHelper dragHelper;
    //listener for dragging events
    private DragListener dragListener;
    //range of horizontal drags
    private int range;

    private int width;

    private int height;
    //the distance of main view to the left of ViewGroup
    private int mainLeft;
    private Context context;
    private ImageView ivShadow;
    //left layout
    private RelativeLayout vgLeft;
    //right (main layout)
    private CustomRelativeLayout vgMain;
    //deafult cloase
    private Status status = Status.CLOSE;


    private final ViewDragHelper.Callback dragHelperCallback = new ViewDragHelper.Callback() {
        /**
         * slide horizontally
         * @param child Child view being dragged
         * @param left Attempted motion along the X axis
         * @param dx Proposed change in position for left
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (mainLeft + dx < 0) {
                return 0;
            } else if (mainLeft + dx > range) {
                return range;
            } else {
                return left;
            }
        }

        /**
         * intercepter
         * @param child Child the user is attempting to capture
         * @param pointerId ID of the pointer attempting the capture
         * @return
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }
        /**
         * set the greatest distance for horizontal drag
         * @param child Child view to check width
         * @return
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return width;
        }

        /**
         * callback function for release after dragging child view
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (xvel > 0) {
                open();
            } else if (xvel < 0) {
                close();
            } else if (releasedChild == vgMain && mainLeft > range * 0.3) {
                open();
            } else if (releasedChild == vgLeft && mainLeft > range * 0.7) {
                open();
            } else {
                close();
            }
        }

        /**
         * @param changedView View whose position changed
         * @param left New X coordinate of the left edge of the view
         * @param top New Y coordinate of the top edge of the view
         * @param dx Change in X position from the last call
         * @param dy Change in Y position from the last call
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            if (changedView == vgMain) {
                mainLeft = left;
            } else {
                mainLeft = mainLeft + left;
            }
            if (mainLeft < 0) {
                mainLeft = 0;
            } else if (mainLeft > range) {
                mainLeft = range;
            }

            if (IS_SHOW_SHADOW) {
                ivShadow.layout(mainLeft, 0, mainLeft + width, height);
            }
            if (changedView == vgLeft) {
                vgLeft.layout(0, 0, width, height);
                vgMain.layout(mainLeft, 0, mainLeft + width, height);
            }

            dispatchDragEvent(mainLeft);
        }
    };

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        gestureDetector = new GestureDetectorCompat(context, new YScrollDetector());
        dragHelper = ViewDragHelper.create(this, dragHelperCallback);
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            return Math.abs(dy) <= Math.abs(dx);
        }
    }

    /**
     * drag related interface
     */
    public interface DragListener {

        public void onOpen();

        public void onClose();

        public void onDrag(float percent);
    }
    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    /**
     * initiations
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (IS_SHOW_SHADOW) {
            ivShadow = new ImageView(context);
            ivShadow.setImageResource(R.mipmap.shadow);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(ivShadow, 1, lp);
        }
        vgLeft = (RelativeLayout) getChildAt(0);
        vgMain = (CustomRelativeLayout) getChildAt(IS_SHOW_SHADOW ? 2 : 1);
        vgMain.setDragLayout(this);
        vgLeft.setClickable(true);
        vgMain.setClickable(true);
    }

    public ViewGroup getVgMain() {
        return vgMain;
    }

    public ViewGroup getVgLeft() {
        return vgLeft;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = vgLeft.getMeasuredWidth();
        height = vgLeft.getMeasuredHeight();
        range = (int) (width * 0.8f);
    }

    /**
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        vgLeft.layout(0, 0, width, height);
        vgMain.layout(mainLeft, 0, mainLeft + width, height);
    }

    /**
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev) && gestureDetector.onTouchEvent(ev);
    }

    /**
     * intercepts event and send to ViewDragHelper
     * @param e
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        try {
            dragHelper.processTouchEvent(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * handles drag events
     * @param mainLeft
     */
    private void dispatchDragEvent(int mainLeft) {
        if (dragListener == null) {
            return;
        }
        float percent = mainLeft / (float) range;
        animateView(percent);
        dragListener.onDrag(percent);
        Status lastStatus = status;
        if (lastStatus != getStatus() && status == Status.CLOSE) {
            dragListener.onClose();
        } else if (lastStatus != getStatus() && status == Status.OPEN) {
            dragListener.onOpen();
        }
    }

    /**
     * @param percent
     */
    private void animateView(float percent) {
        float f1 = 1 - percent * 0.5f;

        ViewHelper.setTranslationX(vgLeft, -vgLeft.getWidth() / 2.5f + vgLeft.getWidth() / 2.5f * percent);
        if (IS_SHOW_SHADOW) {
            //scale according to shadow size
            ViewHelper.setScaleX(ivShadow, f1 * 1.2f * (1 - percent * 0.10f));
            ViewHelper.setScaleY(ivShadow, f1 * 1.85f * (1 - percent * 0.10f));
        }
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public enum Status {
        DRAG, OPEN, CLOSE
    }

    /**
     * @return
     */
    public Status getStatus() {
        if (mainLeft == 0) {
            status = Status.CLOSE;
        } else if (mainLeft == range) {
            status = Status.OPEN;
        } else {
            status = Status.DRAG;
        }
        return status;
    }

    public void open() {
        open(true);
    }

    public void open(boolean animate) {
        if (animate) {
            //continues to move
            if (dragHelper.smoothSlideViewTo(vgMain, range, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            vgMain.layout(range, 0, range * 2, height);
            dispatchDragEvent(range);
        }
    }

    public void close() {
        close(true);
    }

    public void close(boolean animate) {
        if (animate) {
            //continues to move
            if (dragHelper.smoothSlideViewTo(vgMain, 0, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            vgMain.layout(0, 0, width, height);
            dispatchDragEvent(0);
        }
    }
}
