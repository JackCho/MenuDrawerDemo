package com.stg.keke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stg.keke.R;
import com.stg.keke.util.DensityUtil;

public class HomeAdapter extends BaseFragmentAdapter {

	private LayoutInflater mInflater;
	
	private RelativeLayout.LayoutParams params ;

	public HomeAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		int width = (int) DensityUtil.getWidthInPx(context);
		int height = width * 17 /32;
		params = new RelativeLayout.LayoutParams(width, height);
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.home_item, null);
			holder = new ViewHolder();
			holder.top = (TextView) convertView.findViewById(R.id.home_item_top);
			holder.title = (TextView) convertView.findViewById(R.id.home_item_title);
			holder.source = (TextView) convertView.findViewById(R.id.home_item_source);
			holder.time = (TextView) convertView.findViewById(R.id.home_item_time);
			holder.firstView =  convertView.findViewById(R.id.home_item_1_layout);
			holder.secondView = convertView.findViewById(R.id.home_item_2_layout);
			holder.firstImg = (ImageView) convertView.findViewById(R.id.home_item_top_img);
			holder.firstTitle = (TextView) convertView.findViewById(R.id.home_item_top_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == 0 && isShowTop()) {
			holder.top.setVisibility(View.VISIBLE);
		} else {
			holder.top.setVisibility(View.GONE);
		}
		if (position == 0) {
			holder.firstView.setVisibility(View.VISIBLE);
			holder.secondView.setVisibility(View.GONE);
			
			holder.firstImg.setLayoutParams(params);
		} else {
			holder.firstView.setVisibility(View.GONE);
			holder.secondView.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView top;
		TextView title;
		TextView source;
		TextView time;
		
		View firstView;
		View secondView;
		
		ImageView firstImg;
		TextView firstTitle;
	}
}
