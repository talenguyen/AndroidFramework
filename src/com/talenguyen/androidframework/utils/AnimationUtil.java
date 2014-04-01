package com.talenguyen.androidframework.utils;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

public class AnimationUtil {

	private static final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
	
	public static void slideToRight(View target, long duration, AnimatorListener animator) {
		final ObjectAnimator objA = ObjectAnimator.ofFloat(target, "translationX", -target.getWidth(), 0);
		objA.setDuration(duration);
		objA.setInterpolator(interpolator);
		if (animator != null) {
			objA.addListener(animator);
		}
		objA.start();
	}
	
	public static void slideToLeft(View target, long duration, AnimatorListener animator) {
		final ObjectAnimator objA = ObjectAnimator.ofFloat(target, "translationX", 0, -target.getWidth());
		objA.setDuration(duration);
		objA.setInterpolator(interpolator);
		if (animator != null) {
			objA.addListener(animator);
		}
		objA.start();
	}
	
	public static void appearFromLeft(View target, long duration) {
		Animation animation = new TranslateAnimation(-target.getWidth(), 0,0, 0);
		animation.setDuration(duration);
//		animation.setFillAfter(true);
		target.startAnimation(animation);
		target.setVisibility(View.VISIBLE);
	}
	
	public static void disappearToLeft(View target, long duration) {
		Animation animation = new TranslateAnimation(0, -target.getWidth(),0, 0);
		animation.setDuration(duration);
//		animation.setFillAfter(true);
		target.startAnimation(animation);
		target.setVisibility(View.GONE);
	}
}
