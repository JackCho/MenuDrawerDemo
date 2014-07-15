package com.stg.keke.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.stg.keke.R;
import com.stg.keke.adapter.CommentAdapter;
import com.stg.keke.util.CommonUtils;
import com.stg.keke.util.UIHelper;
import com.stg.keke.widget.xlistview.IXListViewLoadMore;
import com.stg.keke.widget.xlistview.IXListViewRefreshListener;
import com.stg.keke.widget.xlistview.XListView;

public class CommentActivity extends Activity implements OnClickListener {

	private XListView mListView;
	private EditText mContent;
	private Button mSendBtn;

	private CommentAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		initView();
	}

	private void initView() {
		mListView = (XListView) findViewById(R.id.comment_listview);
		mContent = (EditText) findViewById(R.id.comment_et);
		mSendBtn = (Button) findViewById(R.id.comment_send);

		mSendBtn.setOnClickListener(this);
		mListView.setPullRefreshEnable(mRefreshListener);
		mListView.setPullLoadEnable(mLoadMoreListener);

		mAdapter = new CommentAdapter(this);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_send:
			String content = mContent.getText().toString();
			if (TextUtils.isEmpty(content)) {
				UIHelper.showToastShort(CommentActivity.this, R.string.no_comment);
				return;
			}
			CommonUtils.hideSystemKeyBoard(CommentActivity.this, mContent);
			mContent.setText("");
			break;

		default:
			break;
		}
	}

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent = new Intent(CommentActivity.this, DetailActicleActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, 0);
			finish();
		}
		return false;
	}

	public void terminate(View v) {
		Intent intent = new Intent(CommentActivity.this, DetailActicleActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, 0);
		finish();
	}
}
