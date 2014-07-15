package com.stg.keke.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.stg.keke.R;
import com.stg.keke.adapter.CommentAdapter;
import com.stg.keke.config.From;
import com.stg.keke.util.CommonUtils;
import com.stg.keke.util.MyAnimationListener;
import com.stg.keke.util.UIHelper;
import com.stg.keke.widget.swipback.app.SwipeBackActivity;
import com.stg.keke.widget.xlistview.IXListViewLoadMore;
import com.stg.keke.widget.xlistview.IXListViewRefreshListener;
import com.stg.keke.widget.xlistview.XListView;

public class DetailActicleActivity extends SwipeBackActivity implements OnClickListener{

	private TextView mTag;
	private TextView mTime;
	private TextView mTitle;
	private TextView mSource;
	private TextView mEditor;
	private WebView mWebView;
	private ImageView mComment;
	private ImageView mShare;
	private ImageView mCollection;
	private TextView mCollectionNum;
	private TextView mCommentNum;
	
	//评论页面
	private XListView mListView;
	private EditText mContent;
	private Button mSendBtn;
	
	private View mCommentLayout;
	private View mContentLayout;

	private CommentAdapter mAdapter;
	
	private Dialog mDialog;

	private static final int LODING_MORE = 0;
	private static final int REFRESH_MORE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_detail_article);
		
		initView();
	}
	
	private void initView() {
		mTag = (TextView) findViewById(R.id.article_tag);
		mTime = (TextView) findViewById(R.id.article_time);
		mTitle = (TextView) findViewById(R.id.article_title);
		mSource = (TextView) findViewById(R.id.article_source);
		mEditor = (TextView) findViewById(R.id.article_editor);
		mWebView = (WebView) findViewById(R.id.article_webview);
		mComment = (ImageView) findViewById(R.id.article_comment);
		mShare = (ImageView) findViewById(R.id.article_share);
		mCollection = (ImageView) findViewById(R.id.article_collection);
		mCollectionNum = (TextView) findViewById(R.id.article_collection_num);
		mCommentNum = (TextView) findViewById(R.id.article_comment_num);
		
		mComment.setOnClickListener(this);
		mCommentNum.setOnClickListener(this);
		mShare.setOnClickListener(this);
		mCollection.setOnClickListener(this);
		mCollectionNum.setOnClickListener(this);
		
		From from = (From) getIntent().getSerializableExtra("from");
		if (from == From.HOTSPOT) {
			mTag.setText("最新热点");
		} else if (from == From.INFORMATION) {
			mTag.setText("资讯");
		} if (from == From.SPECIAL) {
			mTag.setText("专栏");
		} if (from == From.CASE) {
			mTag.setText("营销案例");
		} if (from == From.HOME) {
			mTag.setText("首页");
		} 
		
		mListView = (XListView) findViewById(R.id.comment_listview);
		mContent = (EditText) findViewById(R.id.comment_et);
		mSendBtn = (Button) findViewById(R.id.comment_send);

		mSendBtn.setOnClickListener(this);
		mListView.setPullRefreshEnable(mRefreshListener);
		mListView.setPullLoadEnable(mLoadMoreListener);

		mAdapter = new CommentAdapter(this);
		mListView.setAdapter(mAdapter);
		
		mCommentLayout = findViewById(R.id.detail_article_comment_layout);
		mContentLayout = findViewById(R.id.detail_article_content_layout);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.article_collection:
		case R.id.article_collection_num:
			mCollection.setImageResource(R.drawable.btn_share_heart_down2);
			Animation scale_anim_1 = AnimationUtils.loadAnimation(DetailActicleActivity.this, R.anim.scale);
			Animation scale_anim_2 = AnimationUtils.loadAnimation(DetailActicleActivity.this, R.anim.scale);
			scale_anim_1.setAnimationListener(new MyAnimationListener(){
				
				@Override
				public void onAnimationStart(Animation animation) {
					mCollectionNum.setVisibility(View.VISIBLE);
					String str = mCollectionNum.getText().toString();
					if (TextUtils.isEmpty(str)) {
						mCollectionNum.setText("1");
					} else {
						try {
							int num = Integer.parseInt(str);
							mCollectionNum.setText(String.valueOf(++num));
						} catch (Exception e) {
							e.printStackTrace();
							mCollectionNum.setText("1");
						}
					}
				}
			});
			mCollection.startAnimation(scale_anim_1);
			mCollectionNum.startAnimation(scale_anim_2);
			break;
		case R.id.article_comment:
		case R.id.article_comment_num:
			if (!mCommentLayout.isShown()) {
				showCommentView();
			} else {
				hideCommentView();
			}
			break;
		case R.id.article_share:
			if (mDialog == null) {
				mDialog = new Dialog(DetailActicleActivity.this, R.style.login_dialog);
				mDialog.setCanceledOnTouchOutside(true);
				Window win = mDialog.getWindow();
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
				params.height = LayoutParams.WRAP_CONTENT;
				params.x = 0;
				params.y = 0;
				win.setAttributes(params);
				mDialog.setContentView(R.layout.dialog_share);
				mDialog.findViewById(R.id.share_by_sina).setOnClickListener(this);
				mDialog.findViewById(R.id.share_by_weixin).setOnClickListener(this);
				mDialog.findViewById(R.id.share_by_tencent).setOnClickListener(this);
				mDialog.findViewById(R.id.share_by_email).setOnClickListener(this);
				mDialog.findViewById(R.id.share_cancel).setOnClickListener(this);
				mDialog.findViewById(R.id.share_layout).setOnClickListener(this);
			}
			mDialog.show();
			break;
		case R.id.share_by_sina:
			
			mDialog.dismiss();
			break;
		case R.id.share_by_weixin:
			
			mDialog.dismiss();
			break;
		case R.id.share_by_tencent:
			
			mDialog.dismiss();
			break;
		case R.id.share_by_email:
			
			mDialog.dismiss();
			break;
		case R.id.share_cancel:
		case R.id.share_layout:
			mDialog.dismiss();
			break;
		case R.id.comment_send:
			String content = mContent.getText().toString();
			if (TextUtils.isEmpty(content)) {
				UIHelper.showToastShort(DetailActicleActivity.this, "评论内容不能为空");
				return;
			}
			CommonUtils.hideSystemKeyBoard(DetailActicleActivity.this, mContent);
			mContent.setText("");
			break;
		default:
			break;
		}
	}

	private void hideCommentView() {
		mContentLayout.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(DetailActicleActivity.this, R.anim.in_from_right);
		animation.setAnimationListener(new MyAnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				mCommentLayout.setVisibility(View.GONE);
			}
		});
		mContentLayout.startAnimation(animation);
	}


	private void showCommentView() {
		mCommentLayout.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(DetailActicleActivity.this, R.anim.out_from_right);
		animation.setAnimationListener(new MyAnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				mContentLayout.setVisibility(View.GONE);
			}
		});
		mContentLayout.startAnimation(animation);
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
			if (mCommentLayout.isShown()) {
				hideCommentView();
			} else {
				scrollToFinishActivity();
			}
		}
		return false;
	}

	public void terminate(View v) {
		if (mCommentLayout.isShown()) {
			hideCommentView();
		} else {
			scrollToFinishActivity();
		}
	}

	@SuppressLint("SimpleDateFormat")
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM-dd HH:mm");
		}
	};

	private String getCurrentTime() {
		Date nowTime = new Date();
		String nowTimeStr = dateFormater.get().format(nowTime);
		return nowTimeStr;
	}

}
