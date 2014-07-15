package com.stg.keke.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stg.keke.R;
import com.stg.keke.activity.DetailHireActivity;
import com.stg.keke.config.From;
import com.stg.keke.util.DensityUtil;

public class HireAdapter extends BaseFragmentAdapter {

	private LayoutInflater mInflater;
	
	private int width;
	private int height;
	private Context context;

	public HireAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		width = (int) (DensityUtil.getWidthInPx(context) - DensityUtil.dip2px(context, 20));
		height = width * 107 / 296;
	}
	
	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.hire_item, null);
			holder = new Viewholder();
			holder.top = (TextView) convertView.findViewById(R.id.hire_item_top);
			holder.title = (TextView) convertView.findViewById(R.id.hire_item_title);
			holder.place = (TextView) convertView.findViewById(R.id.hire_item_place);
			holder.time = (TextView) convertView.findViewById(R.id.hire_item_time);
			holder.img = (ImageView)convertView.findViewById(R.id.hire_item_img);
			holder.jobs = (LinearLayout)convertView.findViewById(R.id.hire_item_jobs);
			convertView.setTag(holder);
		} else {
			holder = (Viewholder)convertView.getTag();
		}
		if (position == 0 && isShowTop()) {
			holder.top.setVisibility(View.VISIBLE);
		} else {
			holder.top.setVisibility(View.GONE);
		}
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
		layoutParams.addRule(RelativeLayout.BELOW, R.id.hire_item_place);
		holder.img.setLayoutParams(layoutParams);
		holder.jobs.removeAllViews();
		for (int i = 0; i < 2; i++) {
			View job = mInflater.inflate(R.layout.hire_item_item, null);
			TextView textView = (TextView) job.findViewById(R.id.job);
			textView.setText(R.string.hire_occupation);
			holder.jobs.addView(job);
			textView.setOnClickListener(mClickListener);
		}
		
		return convertView;
	}
	
	private OnClickListener mClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.job:
				Intent intent =  new Intent(context, DetailHireActivity.class);
				intent.putExtra("from", From.HIRE);
				context.startActivity(intent);
				
				((Activity)context).overridePendingTransition(R.anim.in_from_right, 0);
				break;

			default:
				break;
			}
		}
	};

	
	private class Viewholder{
		TextView top;
		TextView title;
		TextView place;
		TextView time;
		ImageView img;
		LinearLayout jobs;
	}


}
