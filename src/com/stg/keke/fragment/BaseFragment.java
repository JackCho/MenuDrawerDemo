package com.stg.keke.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stg.keke.R;
import com.stg.keke.activity.TopListener;
import com.stg.keke.util.DensityUtil;

@SuppressLint("SimpleDateFormat")
public class BaseFragment extends Fragment implements OnClickListener{

	protected Button mMore;
	protected TextView mTitle;
	protected Button mSearchBtn;
	
	protected RelativeLayout.LayoutParams mRefreshParams;
	protected RelativeLayout.LayoutParams mNormalParams;
	
	protected int topMargin;

	public void setTitle(String title){
		if (mTitle != null) {
			mTitle.setText(title);
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int width = (int) DensityUtil.getWidthInPx(getActivity());
		int height = RelativeLayout.LayoutParams.MATCH_PARENT;
		topMargin = DensityUtil.dip2px(getActivity(), 50);
		
		mRefreshParams = new RelativeLayout.LayoutParams(width, height);
		mNormalParams = new RelativeLayout.LayoutParams(width, height);
		
		mRefreshParams.topMargin = topMargin;
	}
	
	protected static final int LODING_MORE = 0;
	protected static final int REFRESH_MORE = 1;

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM-dd HH:mm");
		}
	};

	protected String getCurrentTime() {
		Date nowTime = new Date();
		String nowTimeStr = dateFormater.get().format(nowTime);
		return nowTimeStr;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_more:
			((TopListener)getActivity()).toggle();
			break;
		case R.id.main_search:
			((TopListener)getActivity()).search();
			break;

		default:
			break;
		}
	}

}
