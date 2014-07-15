package com.stg.keke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stg.keke.R;
import com.stg.keke.util.DensityUtil;

public class InfoBottomAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	private int width;
	private int height;
	
	public InfoBottomAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
		
		width = (int) (DensityUtil.getWidthInPx(context) - DensityUtil.dip2px(context, 20));
		height = width * 107 / 296;
	}
	
	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.info_item, null);
			holder = new Viewholder();
			holder.title = (TextView) convertView.findViewById(R.id.info_item_title);
			holder.source = (TextView) convertView.findViewById(R.id.info_item_source);
			holder.time = (TextView) convertView.findViewById(R.id.info_item_time);
			holder.img = (ImageView)convertView.findViewById(R.id.info_item_img);
			convertView.setTag(holder);
		} else {
			holder = (Viewholder)convertView.getTag();
		}
		
		if(position % 2 == 0){
			holder.img.setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
			layoutParams.addRule(RelativeLayout.BELOW, R.id.info_item_source);
			holder.img.setLayoutParams(layoutParams);
		}else{
			holder.img.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	private class Viewholder{
		TextView title;
		TextView source;
		TextView time;
		ImageView img;
	}
}
