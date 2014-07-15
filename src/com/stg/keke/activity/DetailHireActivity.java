package com.stg.keke.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.stg.keke.R;
import com.stg.keke.adapter.CommentAdapter;
import com.stg.keke.adapter.SelectAdapter;
import com.stg.keke.config.From;
import com.stg.keke.util.CommonUtils;
import com.stg.keke.util.MyAnimationListener;
import com.stg.keke.util.UIHelper;
import com.stg.keke.widget.swipback.app.SwipeBackActivity;
import com.stg.keke.widget.xlistview.IXListViewLoadMore;
import com.stg.keke.widget.xlistview.IXListViewRefreshListener;
import com.stg.keke.widget.xlistview.XListView;

public class DetailHireActivity extends SwipeBackActivity implements OnClickListener{

	private TextView mTag;
	private TextView mTime;
	private TextView mTitle;
	private WebView mWebView;
	private ImageView mComment;
	private ImageView mShare;
	private TextView mCommentNum;
	private TextView mCompanyInfo;
	private ImageView mChoose;
	private ImageView mContact;
	private XListView mListView;
	private EditText mContent;
	private Button mSendBtn;
	private View mCommentLayout;
	private View mContentLayout;
	private View mCompanyLayout;
	private CommentAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_hire);

		intiView();
	}

	private void intiView() {
		mTag = (TextView) findViewById(R.id.hire_tag);
		mTime = (TextView) findViewById(R.id.hire_time);
		mTitle = (TextView) findViewById(R.id.hire_title);
		mWebView = (WebView) findViewById(R.id.hire_webview);
		mCommentNum = (TextView) findViewById(R.id.hire_comment_num);
		mCompanyInfo = (TextView)findViewById(R.id.hire_introduction);

		mComment = (ImageView) findViewById(R.id.hire_comment);
		mChoose = (ImageView)findViewById(R.id.hire_add);
		mContact = (ImageView)findViewById(R.id.hire_contact);
		mComment = (ImageView) findViewById(R.id.hire_comment);
		mShare = (ImageView)findViewById(R.id.hire_share);

		mChoose.setOnClickListener(this);
		mContact.setOnClickListener(this);
		mCompanyInfo.setOnClickListener(this);
		mComment.setOnClickListener(this);
		mCommentNum.setOnClickListener(this);
		mShare.setOnClickListener(this);

		From from = (From) getIntent().getSerializableExtra("from");
		if (from == From.HOTSPOT) {
			mTag.setText(getString(R.string.title_hotpot));
		} else if (from == From.INFORMATION) {
			mTag.setText(getString(R.string.title_info));
		} if (from == From.SPECIAL) {
			mTag.setText(getString(R.string.title_column));
		} if (from == From.CASE) {
			mTag.setText(getString(R.string.title_case));
		} if (from == From.HOME) {
			mTag.setText(getString(R.string.title_head));
		} if(from == From.HIRE){
			mTag.setText(getString(R.string.title_hire));
		}

		mListView = (XListView) findViewById(R.id.comment_listview);
		mContent = (EditText) findViewById(R.id.comment_et);
		mSendBtn = (Button) findViewById(R.id.comment_send);

		mSendBtn.setOnClickListener(this);
		mListView.setPullRefreshEnable(mRefreshListener);
		mListView.setPullLoadEnable(mLoadMoreListener);

		mAdapter = new CommentAdapter(this);
		mListView.setAdapter(mAdapter);

		mCommentLayout = findViewById(R.id.detail_hire_comment_layout);
		mContentLayout = findViewById(R.id.detail_hire_content_layout);
		mCompanyLayout = findViewById(R.id.detail_hire_companyinfo_layout);
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
	private Dialog mShareDialog;
	private Dialog contactDialog;
	private Dialog mSelectDialog;
	private Dialog mContactDialog;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (mCommentLayout.isShown()) {
				hideCommentView();
			}else if(mCompanyLayout.isShown()){
				hideCompanyView();
			} else {
				scrollToFinishActivity();
			}
		}
		return false;
	}

	private void hideCompanyView() {
		mContentLayout.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(DetailHireActivity.this, R.anim.in_from_right);
		animation.setAnimationListener(new MyAnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				mCompanyLayout.setVisibility(View.GONE);
			}
		});
		mContentLayout.startAnimation(animation);	
	}

	private void hideCommentView() {
		mContentLayout.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(DetailHireActivity.this, R.anim.in_from_right);
		animation.setAnimationListener(new MyAnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				mCommentLayout.setVisibility(View.GONE);
			}
		});
		mContentLayout.startAnimation(animation);
	}

	public void terminate(View v) {
		if (mCommentLayout.isShown()) {
			hideCommentView();
		} else if(mCompanyLayout.isShown()){
			hideCompanyView();
		}else{
			scrollToFinishActivity();
		}
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.hire_comment:
		case R.id.hire_comment_num:
			if (!mCommentLayout.isShown()) {
				showCommentView();
			} else {
				hideCommentView();
			}
			break;

		case R.id.hire_share:
			if (mShareDialog == null) {
				mShareDialog = new Dialog(DetailHireActivity.this, R.style.login_dialog);
				mShareDialog.setCanceledOnTouchOutside(true);
				Window win = mShareDialog.getWindow();
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
				params.height = LayoutParams.WRAP_CONTENT;
				params.x = 0;
				params.y = 0;
				win.setAttributes(params);
				mShareDialog.setContentView(R.layout.dialog_share);
				mShareDialog.findViewById(R.id.share_by_sina).setOnClickListener(this);
				mShareDialog.findViewById(R.id.share_by_weixin).setOnClickListener(this);
				mShareDialog.findViewById(R.id.share_by_tencent).setOnClickListener(this);
				mShareDialog.findViewById(R.id.share_by_email).setOnClickListener(this);
				mShareDialog.findViewById(R.id.share_cancel).setOnClickListener(this);
				mShareDialog.findViewById(R.id.share_layout).setOnClickListener(this);
			}
			mShareDialog.show();
			break;
		case R.id.share_by_sina:

			mShareDialog.dismiss();
			break;
		case R.id.share_by_weixin:

			mShareDialog.dismiss();
			break;
		case R.id.share_by_tencent:

			mShareDialog.dismiss();
			break;
		case R.id.share_by_email:

			mShareDialog.dismiss();
			break;
		case R.id.share_cancel:
		case R.id.share_layout:
			mShareDialog.dismiss();
			break;
		case R.id.comment_send:
			String content = mContent.getText().toString();
			if (TextUtils.isEmpty(content)) {
				UIHelper.showToastShort(DetailHireActivity.this, getString(R.string.no_comment));
				return;
			}
			CommonUtils.hideSystemKeyBoard(DetailHireActivity.this, mContent);
			mContent.setText("");
			break;
		case R.id.hire_introduction:
			if (!mCompanyLayout.isShown()) {
				showCompanyView();
			} else {
				hideCompanyView();
			}
			break;
		case R.id.hire_add:
			if (mSelectDialog == null) {
				mSelectDialog = new Dialog(DetailHireActivity.this, R.style.login_dialog);
				mSelectDialog.setCanceledOnTouchOutside(true);
				Window win = mSelectDialog.getWindow();
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
				params.height = LayoutParams.WRAP_CONTENT;
				params.x = 0;
				params.y = 0;
				win.setAttributes(params);
				mSelectDialog.setContentView(R.layout.dailog_select);
				ListView mListView =  (ListView) mSelectDialog.findViewById(R.id.select_listview);
				SelectAdapter myAdapter = new SelectAdapter(DetailHireActivity.this);
				mListView.setAdapter(myAdapter);
				mSelectDialog.findViewById(R.id.select_cancel).setOnClickListener(this);
				mSelectDialog.findViewById(R.id.select_layout).setOnClickListener(this);
			}
			mSelectDialog.show();
			break;
		case R.id.select_layout:
			mSelectDialog.dismiss();
			break;
		case R.id.select_cancel:
			mSelectDialog.dismiss();
			break;
			
		case R.id.hire_contact:
			if (mContactDialog == null) {
				mContactDialog = new Dialog(DetailHireActivity.this, R.style.login_dialog);
				mContactDialog.setCanceledOnTouchOutside(true);
				Window win = mContactDialog.getWindow();
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
				params.height = LayoutParams.WRAP_CONTENT;
				params.x = 0;
				params.y = 0;
				win.setAttributes(params);
				mContactDialog.setContentView(R.layout.dialog_contact);
				mContactDialog.findViewById(R.id.contact_by_call).setOnClickListener(this);
				mContactDialog.findViewById(R.id.contact_by_mail).setOnClickListener(this);
				mContactDialog.findViewById(R.id.contact_by_address).setOnClickListener(this);
				mContactDialog.findViewById(R.id.contact_cancel).setOnClickListener(this);
				mContactDialog.findViewById(R.id.contact_layout).setOnClickListener(this);
			}
			mContactDialog.show();
			break;
		case R.id.contact_by_call:
			CommonUtils.toCall(DetailHireActivity.this, "63074459");
			break;
		case R.id.contact_by_mail:
			CommonUtils.sendEmail(DetailHireActivity.this, String.valueOf(R.string.email_location));
			break;
		case R.id.contact_by_address:
			CommonUtils.showMapByWeb(DetailHireActivity.this, 20, 30, String.valueOf(R.string.map_title),String.valueOf(R.string.map_location));
			break;	
		case R.id.contact_layout:	
		case R.id.contact_cancel:
			mContactDialog.dismiss();
			break;
		default:
			break;
		}
	}

	private void showCompanyView() {
		mCompanyLayout.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(DetailHireActivity.this, R.anim.out_from_right);
		animation.setAnimationListener(new MyAnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				mContentLayout.setVisibility(View.GONE);
			}
		});
		mContentLayout.startAnimation(animation);

	}

	private void showCommentView() {
		mCommentLayout.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(DetailHireActivity.this, R.anim.out_from_right);
		animation.setAnimationListener(new MyAnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				mContentLayout.setVisibility(View.GONE);
			}
		});
		mContentLayout.startAnimation(animation);

	}

}
