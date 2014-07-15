package com.stg.keke.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stg.keke.R;

public class CommentAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	
	public CommentAdapter(Context context) {
		this.mInflater = LayoutInflater.from(context);
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.comment_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.comment_item_name);
			holder.time = (TextView) convertView.findViewById(R.id.comment_item_time);
			holder.content = (TextView) convertView.findViewById(R.id.comment_item_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView name;
		TextView time;
		TextView content;
	}
}
