package com.stg.keke.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.stg.keke.R;
import com.stg.keke.widget.switchbutton.SwitchButton;

public class OfflineActivity extends BaseActivity implements OnCheckedChangeListener{

	private SwitchButton mHotspot;
	private SwitchButton mInformation;
	private SwitchButton mSpecial;
	private SwitchButton mCase;
	private SwitchButton mShare;
	private SwitchButton mHire;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offline);
		
		initView();
	}

	private void initView() {
		mHotspot = (SwitchButton) findViewById(R.id.offline_hotspot_btn);
		mInformation = (SwitchButton) findViewById(R.id.offline_information_btn);
		mSpecial = (SwitchButton) findViewById(R.id.offline_special_btn);
		mCase = (SwitchButton) findViewById(R.id.offline_case_btn);
		mShare = (SwitchButton) findViewById(R.id.offline_share_btn);
		mHire = (SwitchButton) findViewById(R.id.offline_hire_btn);
		
		mHotspot.setOnCheckedChangeListener(this);
		mInformation.setOnCheckedChangeListener(this);
		mSpecial.setOnCheckedChangeListener(this);
		mCase.setOnCheckedChangeListener(this);
		mShare.setOnCheckedChangeListener(this);
		mHire.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.offline_hotspot_btn:
			
			break;
		case R.id.offline_information_btn:
			
			break;
		case R.id.offline_special_btn:
			
			break;
		case R.id.offline_case_btn:
			
			break;
		case R.id.offline_share_btn:
			
			break;
		case R.id.offline_hire_btn:
			
			break;

		default:
			break;
		}
	}
}
