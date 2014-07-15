package com.stg.keke.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.stg.keke.R;
import com.stg.keke.model.Item;

public class MenuAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<Item> items;

	public MenuAdapter(Context context, List<Item> items) {
		this.mInflater = LayoutInflater.from(context);
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
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
			convertView = mInflater.inflate(R.layout.menu_item, null);
			holder = new ViewHolder();
			holder.pic = (ImageView) convertView.findViewById(R.id.menu_item_pic);
			holder.title = (TextView) convertView.findViewById(R.id.menu_item_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Item item = items.get(position);
		holder.pic.setBackgroundResource(item.getDrawable());
		holder.title.setText(item.getTitle());
		return convertView;
	}
	
	public class ViewHolder {
		public ImageView pic;
		public TextView title;
	}
}
