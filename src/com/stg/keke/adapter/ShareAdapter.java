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

public class ShareAdapter extends BaseFragmentAdapter {

	private LayoutInflater mInflater;
	
	private int width;
	private int height;
	private int topMargin;

	public ShareAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		
		width = (int) (DensityUtil.getWidthInPx(context) - DensityUtil.dip2px(context, 20));
		height = width * 107 / 296;
		topMargin = DensityUtil.dip2px(context, 5);
	}
	
	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.share_item, null);
			holder = new Viewholder();
			holder.top = (TextView) convertView.findViewById(R.id.share_item_top);
			holder.title = (TextView) convertView.findViewById(R.id.share_item_title);
			holder.place = (TextView) convertView.findViewById(R.id.share_item_place);
			holder.time = (TextView) convertView.findViewById(R.id.share_item_time);
			holder.attendNum = (TextView) convertView.findViewById(R.id.share_item_attend_num);
			holder.img = (ImageView)convertView.findViewById(R.id.share_item_img);
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
		layoutParams.addRule(RelativeLayout.BELOW, R.id.share_item_place);
		layoutParams.topMargin = topMargin;
		holder.img.setLayoutParams(layoutParams);
		
		return convertView;
	}
	
	private class Viewholder{
		TextView top;
		TextView title;
		TextView place;
		TextView time;
		TextView attendNum;
		ImageView img;
	}

}
