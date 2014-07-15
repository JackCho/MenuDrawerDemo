package com.stg.keke.adapter;

import com.stg.keke.R;
import com.stg.keke.activity.DetailHireActivity;
import com.stg.keke.config.From;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SelectAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context ctx;

	public SelectAdapter(Context ctx){
		mInflater = LayoutInflater.from(ctx);
		this.ctx = ctx;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			Viewholder holder = null;
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.dialog_select_item, null);
				holder = new Viewholder();
				holder.emp = (TextView) convertView.findViewById(R.id.select_emp);
				holder.emp.setOnClickListener(mClickListener);
				convertView.setTag(holder);
			}else{
				holder = (Viewholder)convertView.getTag();
			}
		
			holder.emp.setText("北京-网络专员");
		
		return convertView;
	}
	
	private OnClickListener mClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.select_emp:
				Intent intent =  new Intent(ctx, DetailHireActivity.class);
				intent.putExtra("from", From.HIRE);
				ctx.startActivity(intent);
				
				((Activity)ctx).overridePendingTransition(R.anim.in_from_right, 0);
				break;

			default:
				break;
			}
		}
	};
	
	private class Viewholder{
		TextView emp;
	}


}
