package com.talenguyen.androidframework.module.fullscreen;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends FragmentActivity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 100;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	
	protected FrameLayout mContentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mContentView = new FrameLayout(this);
		mContentView.setBackgroundColor(Color.BLACK);
		
		mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		setContentView(mContentView);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, mContentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

//		// Set up the user interaction to manually show or hide the system UI.
//		mContentView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				delayedHide(AUTO_HIDE_DELAY_MILLIS);
//			}
//		});

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.removeCallbacks(mFullscreenRunnable);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(AUTO_HIDE_DELAY_MILLIS);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		getLayoutInflater().inflate(layoutResID, mContentView);
	}
	
	@Override
	public void setContentView(View view, LayoutParams params) {
		mContentView.addView(view, params);
	}
	
	@Override
	public void setContentView(View view) {
		if (view == mContentView) {
			super.setContentView(mContentView);
		} else {
			this.setContentView(view, null);
		}
	}
	

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
			mHideHandler.removeCallbacks(mFullscreenRunnable);
			mHideHandler.postDelayed(mFullscreenRunnable, 200);
		}
	};
	Runnable mFullscreenRunnable = new Runnable() {
		
		@Override
		public void run() {
			onFullscreen();
		}
	};
	
	protected void onFullscreen() {
		
	}

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
