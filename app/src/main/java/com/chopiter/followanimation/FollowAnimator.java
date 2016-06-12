package com.chopiter.followanimation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by xiekejun on 16/6/12.
 */
public class FollowAnimator extends ValueAnimator {
    float followedX = 0.0f;
    float followedY = 0.0f;
    float mFollowingX = 0.0f;
    float mFollowingY = 0.0f;

    public FollowAnimator(final View followingView, final View followedView) {
        addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float floatValue = ((Float) animation.getAnimatedValue()).floatValue();
                float x = (followedX * floatValue) + ((1.0f - floatValue) * mFollowingX);
                float y = (followedY * floatValue) + ((1.0f - floatValue) * mFollowingY);
                followingView.setTranslationX(x);
                followingView.setTranslationY(y);
            }
        });
        addListener(new AnimatorListener() {
            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationStart(Animator animation) {
                mFollowingX = followingView.getTranslationX();
                mFollowingY = followingView.getTranslationY();
                //制造一点错位
                followedX = followedView.getTranslationX() + 10.0f;
                followedY = followedView.getTranslationY() - 0.4375f;
            }
        });
    }
}