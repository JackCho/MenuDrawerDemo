package com.stg.keke.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.stg.keke.R;
import com.stg.keke.util.MyAnimationListener;

@SuppressLint("HandlerLeak")
public class LoadingActivity extends Activity{

	private ImageView mAd;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		mAd = (ImageView) findViewById(R.id.loading_ad);

		mHandler.sendEmptyMessageDelayed(0, 800);
	}

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				int type = Animation.RELATIVE_TO_SELF;
				TranslateAnimation animation = new TranslateAnimation(type, 0, type, 0, type, -1, type, 0);
				animation.setDuration(1200);
				animation.setInterpolator(new DecelerateInterpolator(2.0f));
				animation.setAnimationListener(new MyAnimationListener(){
					@Override
					public void onAnimationEnd(Animation animation) {
						mHandler.sendEmptyMessageDelayed(1, 400);
					}
				});
				mAd.setVisibility(View.VISIBLE);
				mAd.clearAnimation();
				mAd.startAnimation(animation);
				break;
			case 1:
				Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, 0);
				mHandler.sendEmptyMessageDelayed(2, 1000);
				break;
			case 2:
				finish();
				break;

			default:
				break;
			}
		}
	};

}
