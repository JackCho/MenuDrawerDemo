package com.stg.keke.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.stg.keke.R;
import com.stg.keke.activity.DetailActicleActivity;
import com.stg.keke.adapter.InfoBottomAdapter;
import com.stg.keke.adapter.InfoTopAdapter;
import com.stg.keke.config.From;
import com.stg.keke.widget.HorizontalListView;
import com.stg.keke.widget.xlistview.IXListViewLoadMore;
import com.stg.keke.widget.xlistview.IXListViewRefreshListener;
import com.stg.keke.widget.xlistview.XListView;

public class InfomationFragment extends BaseFragment {

	private XListView mbottomListView;
	private HorizontalListView mtopListView;

	private InfoTopAdapter mTopAdapter;
	private InfoBottomAdapter mBottomAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_info,container,false);
		mMore = (Button) view.findViewById(R.id.main_more);
		mTitle = (TextView) view.findViewById(R.id.main_title);
		mSearchBtn = (Button) view.findViewById(R.id.main_search);
		
		mMore.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);
		
		//测试数据
		ArrayList<String> lists = new ArrayList<String>();
		lists.add("国内资讯");
		lists.add("国外资讯");
		lists.add("电子商务");
		lists.add("网络营销");
		lists.add("网络营销");
		lists.add("网络营销");
		lists.add("网络营销");
		lists.add("网络营销");

		initView(view);
		initTopListView(lists);
		initBottomListView();

		return view;

	}

	private void initTopListView(ArrayList<String> lists) {
		mTopAdapter = new InfoTopAdapter(getActivity(),lists);
		mtopListView.setAdapter(mTopAdapter);
	}

	private void initView(View view) {
		mtopListView = (HorizontalListView)view.findViewById(R.id.info_fragmet_top_listview);
		mbottomListView = (XListView)view.findViewById(R.id.info_fragment_bottom_listview);

		mbottomListView.setOnItemClickListener(mItemClickListener);
		mbottomListView.setPullRefreshEnable(mRefreshListener);
		mbottomListView.setPullLoadEnable(mLoadMoreListener);

		mtopListView.setOnItemClickListener(mTopItemClickListener);
	}


	private OnItemClickListener mTopItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			initBottomListView();
			mTopAdapter.setPos(position);
			mTopAdapter.notifyDataSetChanged();
		}
	};


	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getActivity(), DetailActicleActivity.class);
			intent.putExtra("from", From.INFORMATION);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.in_from_right, 0);
		}
	};

	private IXListViewRefreshListener mRefreshListener = new IXListViewRefreshListener() {

		@Override
		public void onRefresh() {

		}
	};

	private IXListViewLoadMore mLoadMoreListener = new IXListViewLoadMore() {

		@Override
		public void onLoadMore() {

		}
	};

	protected void initBottomListView() {
		mbottomListView.showFooter();
		mbottomListView.startPullLoad();
		mbottomListView.showPullLoad();
		mBottomAdapter = new InfoBottomAdapter(getActivity());
		mbottomListView.setAdapter(mBottomAdapter);
	}
	

}
