package com.chopiter.followanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by xiekejun on 16/6/12.
 */
public class FollowAnimLayout extends RelativeLayout {

    int size;
    int duration = 100;
    int delayTime = 100;
    int offsetTime = 60;
    final int MSG_SHOW_ANIM = 100;
    final int MSG_STOP_ANIM = 101;
    final int MSG_STOP_ANIM_DELAY = 102;
    Handler mHandler = new MyHandler();

    public FollowAnimLayout(Context context) {
        super(context);
    }

    public FollowAnimLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FollowAnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateChildViewCount();

    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        updateChildViewCount();

    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateChildViewCount();

    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateChildViewCount();
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        updateChildViewCount();
    }

    private void updateChildViewCount() {
        size = getChildCount();
    }

    class MyHandler extends Handler {
        public final void dispatchMessage(Message message) {
            switch (message.what) {
                case MSG_SHOW_ANIM:
                    startAllViewAnim();
                    sendEmptyMessageDelayed(MSG_SHOW_ANIM, offsetTime);
                    return;
                case MSG_STOP_ANIM:
                    removeMessages(MSG_SHOW_ANIM);
                    return;
                case MSG_STOP_ANIM_DELAY:
                    sendEmptyMessageDelayed(MSG_STOP_ANIM, (long) ((size + 1) * duration));
                    return;
                default:
                    return;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void startAllViewAnim() {
        View followedView = getChildAt(size -1);
        for (int i = size -2 ; i >= 0; i--) {
            View followingView = getChildAt(i);
            if (followingView != null) {
                followingView.clearAnimation();
                ValueAnimator bVar = new FollowAnimator(followingView, followedView);
                bVar.setFloatValues(new float[]{0.0f, 1.0f});
                bVar.setInterpolator(new DecelerateInterpolator(1.5f));
                bVar.setStartDelay(delayTime);
                bVar.setDuration(duration);
                bVar.start();
                followedView = followingView;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (size <= 0) {
            return super.onTouchEvent(motionEvent);
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.mHandler.removeMessages(MSG_SHOW_ANIM);
                this.mHandler.removeMessages(MSG_STOP_ANIM);
                this.mHandler.sendEmptyMessage(MSG_SHOW_ANIM);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:
                this.mHandler.removeMessages(MSG_STOP_ANIM_DELAY);
                this.mHandler.sendEmptyMessage(MSG_STOP_ANIM_DELAY);
                break;
            case MotionEvent.ACTION_MOVE:
                View view = getChildAt(size -1);
                view.setTranslationX(motionEvent.getX() - view.getWidth()/2);
                view.setTranslationY(motionEvent.getY() - view.getHeight()/2);
                break;
        }
        return super.onTouchEvent(motionEvent);
    }


}
