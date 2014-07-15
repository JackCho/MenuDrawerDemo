package com.stg.keke.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stg.keke.R;
import com.stg.keke.util.DensityUtil;

public class FontActivity extends BaseSpecialActivity{

	private ImageView mLine;
	private ImageView mBall;
	private TextView mSize_28;
	private TextView mSize_32;
	private TextView mSize_36;
	private TextView mSize_48;
	private TextView mNotice;
	
	private int maxLeft;
	private int maxRight;
	private int totalWidth;
	private int drag_left; 
	
	private RelativeLayout.LayoutParams params;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_font);
		initView();
		
		int width = DensityUtil.dip2px(this, 25);
		params = new RelativeLayout.LayoutParams(width, width);
	}

	private void initView() {
		mLine = (ImageView) findViewById(R.id.font_line_bg);
		mBall = (ImageView) findViewById(R.id.font_ball);
		mSize_28 = (TextView) findViewById(R.id.font_size_28);
		mSize_32 = (TextView) findViewById(R.id.font_size_32);
		mSize_36 = (TextView) findViewById(R.id.font_size_36);
		mSize_48 = (TextView) findViewById(R.id.font_size_48);
		mNotice = (TextView) findViewById(R.id.font_notice);
		
		mBall.setOnTouchListener(mDragListener);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				maxLeft = mLine.getLeft() - DensityUtil.dip2px(FontActivity.this, 7);
				totalWidth = mLine.getWidth();
				int eachLength = totalWidth / 3;
				int top = mLine.getTop() - DensityUtil.dip2px(FontActivity.this, 15);
				maxRight = (int) DensityUtil.getWidthInPx(FontActivity.this);
				
				int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
				RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width, width);
				params1.leftMargin = maxLeft;
				params1.topMargin = top;
				RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(width, width);
				params2.leftMargin = maxLeft + eachLength;
				params2.topMargin = top;
				RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(width, width);
				params3.leftMargin = maxLeft + 2 * eachLength;
				params3.topMargin = top;
				RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(width, width);
				params4.leftMargin = maxLeft + 3 * eachLength;
				params4.topMargin = top;
				mSize_28.setLayoutParams(params1);
				mSize_32.setLayoutParams(params2);
				mSize_36.setLayoutParams(params3);
				mSize_48.setLayoutParams(params4);
				
				maxRight = maxRight - DensityUtil.dip2px(FontActivity.this, 10);
				drag_left = maxLeft -  DensityUtil.dip2px(FontActivity.this, 1);
			}
		}, 200);
	}
	
	private OnTouchListener mDragListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int x = (int) event.getRawX();
			int tmp = 0;
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				tmp = (int) event.getX();
			break;
			case MotionEvent.ACTION_MOVE:
				int right = x + v.getWidth() - tmp;
				int left = x - tmp;
				if (right >= maxRight) {
					right = maxRight - 1;
					left = right - v.getWidth();
				}
				if (v.getLeft() < drag_left) {
					left = drag_left;
				}
				params.leftMargin = left;
				params.topMargin = v.getTop();
				mBall.setLayoutParams(params);
				float textSize = 14 + (left - drag_left) * 1.0f / totalWidth * 6; 
				mNotice.setTextSize(textSize);
			default:
				break;
			}
			return true;
		}
	};
}
