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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.stg.keke.R;
import com.stg.keke.adapter.CommentAdapter;
import com.stg.keke.util.CommonUtils;
import com.stg.keke.util.MyAnimationListener;
import com.stg.keke.util.UIHelper;
import com.stg.keke.widget.swipback.app.SwipeBackActivity;
import com.stg.keke.widget.xlistview.IXListViewLoadMore;
import com.stg.keke.widget.xlistview.IXListViewRefreshListener;
import com.stg.keke.widget.xlistview.XListView;

public class DetailShareActivity extends SwipeBackActivity implements OnClickListener{
   
	private TextView mPublishTime;
	private TextView mTitle;
	private TextView mPlace;
	private TextView mTime;
	private TextView mHost;
	private TextView mPhone;
	private TextView mContact;
	private WebView mWebView;
	private ImageView mComment;
	private ImageView mShare;
	private ImageView mCollection;
	private ImageView mSignUp;
	private TextView mCollectionNum;
	private TextView mCommentNum;
	
	private Dialog mShareDialog;
	private XListView mListView;
	private EditText mContent;
	private Button mSendBtn;
	private CommentAdapter mAdapter;
	private View mCommentLayout;
	private View mContentLayout;
	
	private Dialog mAttendDialog;
	private EditText mName;
	private EditText mCompanyName;
	private EditText mTel;
	private EditText mAttendNum;
	private TextView mFee;
	private CheckBox mAliPay;
	private CheckBox mBankPay;
	private CheckBox mScenePay;
	private Button 	 mConfirm;
	private Button 	 mCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_share);
		
		initView();
	}
	
	
	private void initView() {
		mPublishTime = (TextView) findViewById(R.id.share_article_time);
		mTitle = (TextView) findViewById(R.id.share_article_title);
		mWebView = (WebView) findViewById(R.id.share_article_webview);
		mComment = (ImageView) findViewById(R.id.share_article_comment);
		mShare = (ImageView) findViewById(R.id.share_article_share);
		mCollection = (ImageView) findViewById(R.id.share_article_collection);
		mCollectionNum = (TextView) findViewById(R.id.share_article_collection_num);
		mCommentNum = (TextView) findViewById(R.id.share_article_comment_num);
		mPlace = (TextView)findViewById(R.id.share_article_place_detail);
		mPhone = (TextView)findViewById(R.id.share_article_phone_detial);
		mSignUp = (ImageView)findViewById(R.id.share_article_signup);
		
		mSignUp.setOnClickListener(this);
		mPhone.setOnClickListener(this);
		mPlace.setOnClickListener(this);
		mComment.setOnClickListener(this);
		mCommentNum.setOnClickListener(this);
		mShare.setOnClickListener(this);
		mCollection.setOnClickListener(this);
		mCollectionNum.setOnClickListener(this);
		
		mListView = (XListView) findViewById(R.id.comment_listview);
		mContent = (EditText) findViewById(R.id.comment_et);
		mSendBtn = (Button) findViewById(R.id.comment_send);

		mSendBtn.setOnClickListener(this);
		mListView.setPullRefreshEnable(mRefreshListener);
		mListView.setPullLoadEnable(mLoadMoreListener);

		mAdapter = new CommentAdapter(this);
		mListView.setAdapter(mAdapter);
		
		mCommentLayout = findViewById(R.id.share_detail_article_comment_layout);
		mContentLayout = findViewById(R.id.share_detail_article_content_layout);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share_article_collection:
		case R.id.share_article_collection_num:
			mCollection.setImageResource(R.drawable.btn_share_heart_down2);
			Animation scale_anim_1 = AnimationUtils.loadAnimation(DetailShareActivity.this, R.anim.scale);
			Animation scale_anim_2 = AnimationUtils.loadAnimation(DetailShareActivity.this, R.anim.scale);
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
		case R.id.share_article_comment:
		case R.id.share_article_comment_num:
			if (!mCommentLayout.isShown()) {
				showCommentView();
			} else {
				hideCommentView();
			}
			
			break;
		case R.id.share_article_share:
			showShareDialog();
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
		case R.id.share_article_phone_detial:
			CommonUtils.toCall(this, "13661985020");
			break;
		case R.id.share_article_place_detail:
			CommonUtils.showMapByWeb(this, 20, 10, getString(R.string.map_title),  getString(R.string.map_location));
			break;
		case R.id.share_article_signup:
			showSignupDialog();
			break;
		case R.id.comment_send:
			String content = mContent.getText().toString();
			if (TextUtils.isEmpty(content)) {
				UIHelper.showToastShort(DetailShareActivity.this, getString(R.string.no_comment));
				return;
			}
			CommonUtils.hideSystemKeyBoard(DetailShareActivity.this, mContent);
			mContent.setText("");
			break;	
		case R.id.share_btn_submit:
			
			break;
		case R.id.share_btn_cancel:
			mAttendDialog.dismiss();
			break;
		default:
			break;
		}
	}


	private void showShareDialog() {
		if (mShareDialog == null) {
			mShareDialog = new Dialog(DetailShareActivity.this, R.style.login_dialog);
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
	}

	private void hideCommentView() {
		mContentLayout.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(DetailShareActivity.this, R.anim.in_from_right);
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
		Animation animation = AnimationUtils.loadAnimation(DetailShareActivity.this, R.anim.out_from_right);
		animation.setAnimationListener(new MyAnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				mContentLayout.setVisibility(View.GONE);
			}
		});
		mContentLayout.startAnimation(animation);
	}

	private void showSignupDialog() {
		if (mAttendDialog == null) {
			mAttendDialog = new Dialog(DetailShareActivity.this, R.style.login_dialog);
			mAttendDialog.setCanceledOnTouchOutside(true);
			Window win = mAttendDialog.getWindow();
			LayoutParams params = new LayoutParams();
			params.width = LayoutParams.MATCH_PARENT;
			params.height = LayoutParams.MATCH_PARENT;
			params.x = 0;
			params.y = 0;
			win.setAttributes(params);
			mAttendDialog.setContentView(R.layout.dialog_attend);
			mName = (EditText) mAttendDialog.findViewById(R.id.share_signup_name);
			mCompanyName = (EditText) mAttendDialog.findViewById(R.id.share_signup_company);
			mTel = (EditText) mAttendDialog.findViewById(R.id.share_signup_host_telphone);
			mAttendNum = (EditText) mAttendDialog.findViewById(R.id.share_signup_phone_joinin);
			mFee = (TextView) mAttendDialog.findViewById(R.id.share_signup_contacter_detial);
			mAliPay = (CheckBox) mAttendDialog.findViewById(R.id.share_cb_zhifu);
			mBankPay = (CheckBox) mAttendDialog.findViewById(R.id.share_cb_bank);
			mScenePay = (CheckBox) mAttendDialog.findViewById(R.id.share_cb_scene);
			mConfirm = (Button) mAttendDialog.findViewById(R.id.share_btn_submit);
			mCancel = (Button) mAttendDialog.findViewById(R.id.share_btn_cancel);
			
			mConfirm.setOnClickListener(this);
			mCancel.setOnClickListener(this);
			
			mAliPay.setOnCheckedChangeListener(mCheckedChangeListener);
			mBankPay.setOnCheckedChangeListener(mCheckedChangeListener);
			mScenePay.setOnCheckedChangeListener(mCheckedChangeListener);
		}
		mAttendDialog.show();
	}

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
	
	private OnCheckedChangeListener  mCheckedChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				mAliPay.setChecked(false);
				mBankPay.setChecked(false);
				mScenePay.setChecked(false);
				switch (buttonView.getId()) {
				case R.id.share_cb_zhifu:
					mAliPay.setChecked(true);
					break;
				case R.id.share_cb_bank:
					mBankPay.setChecked(true);
					break;
				case R.id.share_cb_scene:
					mScenePay.setChecked(true);
					break;

				default:
					break;
				}
			}
		}
	};

	
}
