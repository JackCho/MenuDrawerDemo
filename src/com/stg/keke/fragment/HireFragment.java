package com.stg.keke.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.stg.keke.R;
import com.stg.keke.activity.CompanyInfoActivity;
import com.stg.keke.adapter.HireAdapter;
import com.stg.keke.config.From;
import com.stg.keke.widget.xlistview.IXListViewLoadMore;
import com.stg.keke.widget.xlistview.IXListViewRefreshListener;
import com.stg.keke.widget.xlistview.XListView;
import com.stg.keke.widget.xlistview.XListView.IStartScroll;

@SuppressLint("HandlerLeak")
public class HireFragment extends BaseFragment {

	private HireAdapter mAdapter;
	private XListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_hire,container,false);
		mMore = (Button) view.findViewById(R.id.main_more);
		mTitle = (TextView) view.findViewById(R.id.main_title);
		mSearchBtn = (Button) view.findViewById(R.id.main_search);
		mListView = (XListView)view.findViewById(R.id.hire_listview);
		
		mListView.setOnItemClickListener(mItemClickListener);
		mListView.setPullRefreshEnable(mRefreshListener);
		mListView.setPullLoadEnable(mLoadMoreListener);
		mListView.setStartScroll(mStartScroll);
		
		mMore.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);
		
		mAdapter = new HireAdapter(getActivity());
		mListView.setAdapter(mAdapter);
		
		return view;
		
	}
	
	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getActivity(), CompanyInfoActivity.class);
			intent.putExtra("from", From.HIRE);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.in_from_left, 0);
			
		}
	};
	
	private IXListViewRefreshListener mRefreshListener = new IXListViewRefreshListener() {
		
		@Override
		public void onRefresh() {
			mHandler.sendEmptyMessageDelayed(0, 2000);
		}
	};
	
	private IXListViewLoadMore mLoadMoreListener = new IXListViewLoadMore() {
		
		@Override
		public void onLoadMore() {
			
		}
	};
	
	private IStartScroll mStartScroll = new IStartScroll() {

		@Override
		public void startScroll() {
			mAdapter.setShowTop(false);
			mAdapter.notifyDataSetChanged();
			mListView.setLayoutParams(mRefreshParams);
		}
	};
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				mListView.stopRefresh(getCurrentTime());
				mAdapter.setShowTop(true);
				mAdapter.notifyDataSetChanged();
				mListView.setLayoutParams(mNormalParams);
				break;

			default:
				break;
			}
		}
	};
}
