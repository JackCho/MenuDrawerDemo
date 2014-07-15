package com.stg.keke.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BaseFragmentAdapter extends BaseAdapter {

	private boolean isShowTop = true;
	
	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
	

	public boolean isShowTop() {
		return isShowTop;
	}

	public void setShowTop(boolean isShowTop) {
		this.isShowTop = isShowTop;
	}

}
