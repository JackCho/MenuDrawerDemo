package com.stg.keke.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.stg.keke.R;
import com.stg.keke.activity.AboutActivity;
import com.stg.keke.activity.AccountActivity;
import com.stg.keke.activity.FeedbackActivity;
import com.stg.keke.activity.FontActivity;
import com.stg.keke.activity.OfflineActivity;
import com.stg.keke.activity.TopListener;
import com.stg.keke.util.CommonUtils;
import com.stg.keke.util.UIHelper;
import com.stg.keke.widget.switchbutton.SwitchButton;

public class SettingFragment extends BaseFragment implements OnCheckedChangeListener{
	
	private View mAccount;
	private View mFont;
	private SwitchButton mPushBtn;
	private View mOffline;
	private SwitchButton mRingtoneBtn;
	private SwitchButton m2gBtn;
	private View mClearCache;
	private View mFeedback;
	private View mRate;
	private View mVersion;
	private View mAbout;
	private View mRecommend;
	private TextView mCacheSize;
	private TextView mVersionName;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		initView(view);
		
		return view;
	}

	private void initView(View view) {
		mMore = (Button) view.findViewById(R.id.main_more);
		mTitle = (TextView) view.findViewById(R.id.main_title);
		mSearchBtn = (Button) view.findViewById(R.id.main_search);
		
		mAccount = view.findViewById(R.id.setting_account_layout);
		mFont = view.findViewById(R.id.setting_font_layout);
		mPushBtn = (SwitchButton) view.findViewById(R.id.setting_push_btn);
		mOffline = view.findViewById(R.id.setting_offline_layout);
		mRingtoneBtn = (SwitchButton) view.findViewById(R.id.setting_ringtone_btn);
		m2gBtn = (SwitchButton) view.findViewById(R.id.setting_2g_btn);
		mClearCache = view.findViewById(R.id.setting_clear_layout);
		mFeedback = view.findViewById(R.id.setting_feedback_layout);
		mRate = view.findViewById(R.id.setting_rate_layout);
		mVersion = view.findViewById(R.id.setting_version_layout);
		mAbout = view.findViewById(R.id.setting_about_layout);
		mRecommend = view.findViewById(R.id.setting_recommend_layout);
		mCacheSize = (TextView) view.findViewById(R.id.setting_cache_size);
		mVersionName = (TextView) view.findViewById(R.id.setting_version_name);
		
		mAccount.setOnClickListener(this);
		mFont.setOnClickListener(this);
		mOffline.setOnClickListener(this);
		mClearCache.setOnClickListener(this);
		mFeedback.setOnClickListener(this);
		mRate.setOnClickListener(this);
		mVersion.setOnClickListener(this);
		mAbout.setOnClickListener(this);
		mRecommend.setOnClickListener(this);
		
		mMore.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);
		
		mPushBtn.setOnCheckedChangeListener(this);
		m2gBtn.setOnCheckedChangeListener(this);
		mRingtoneBtn.setOnCheckedChangeListener(this);
		
		try {
			mVersionName.setText(CommonUtils.getVersionName(getActivity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_account_layout:
			startActivity(new Intent(getActivity(), AccountActivity.class));
			getActivity().overridePendingTransition(R.anim.in_from_right, 0);
			break;
		case R.id.setting_font_layout:
			startActivity(new Intent(getActivity(), FontActivity.class));
			getActivity().overridePendingTransition(R.anim.in_from_right, 0);
			break;
		case R.id.setting_offline_layout:
			startActivity(new Intent(getActivity(), OfflineActivity.class));
			getActivity().overridePendingTransition(R.anim.in_from_right, 0);
			break;
		case R.id.setting_clear_layout:
			clearCache();
			break;
		case R.id.setting_feedback_layout:
			startActivity(new Intent(getActivity(), FeedbackActivity.class));
			getActivity().overridePendingTransition(R.anim.in_from_right, 0);
			break;
		case R.id.setting_rate_layout:
			Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
			Intent intent = new Intent(Intent.ACTION_VIEW,uri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.in_from_right, 0);
			break;
		case R.id.setting_version_layout:
			UIHelper.showToastShort(getActivity(), "您使用的当前版本是最新的");
			break;
		case R.id.setting_about_layout:
			startActivity(new Intent(getActivity(), AboutActivity.class));
			getActivity().overridePendingTransition(R.anim.in_from_right, 0);
			break;
		case R.id.setting_recommend_layout:
			
			break;
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

	private void clearCache() {
		new AlertDialog.Builder(getActivity())
		.setTitle("确认清除应用缓存")
		.setItems(new String[] {"确定","取消"},
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								mCacheSize.setText("0.0MB");
								UIHelper.showToastShort(getActivity(), "已经为您清除完毕");
								break;
							case 1:
								dialog.dismiss();
								break;
							default:
								break;
							}
						}
		}).show();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.setting_push_btn:
			
			break;
		case R.id.setting_ringtone_btn:
			
			break;
		case R.id.setting_2g_btn:
			
			break;

		default:
			break;
		}
	}

}
