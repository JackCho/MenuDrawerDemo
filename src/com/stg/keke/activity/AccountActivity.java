package com.stg.keke.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.stg.keke.R;

public class AccountActivity extends BaseActivity implements OnClickListener{

	private Dialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		findViewById(R.id.account_sina_layout).setOnClickListener(this);
		findViewById(R.id.account_qq_layout).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.account_sina_layout:
			if (mDialog == null) {
				mDialog = new Dialog(AccountActivity.this, R.style.login_dialog);
				mDialog.setCanceledOnTouchOutside(true);
				Window win = mDialog.getWindow();
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
				params.height = LayoutParams.WRAP_CONTENT;
				params.x = 0;
				params.y = 0;
				win.setAttributes(params);
				mDialog.setContentView(R.layout.dialog_logout);
				mDialog.findViewById(R.id.logout_confirm).setOnClickListener(this);
				mDialog.findViewById(R.id.logout_cancel).setOnClickListener(this);
				mDialog.findViewById(R.id.logout_layout).setOnClickListener(this);
			}
			mDialog.show();
			break;
		case R.id.account_qq_layout:
			if (mDialog == null) {
				mDialog = new Dialog(AccountActivity.this, R.style.login_dialog);
				mDialog.setCanceledOnTouchOutside(true);
				Window win = mDialog.getWindow();
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
				params.height = LayoutParams.WRAP_CONTENT;
				params.x = 0;
				params.y = 0;
				win.setAttributes(params);
				mDialog.setContentView(R.layout.dialog_logout);
				mDialog.findViewById(R.id.logout_confirm).setOnClickListener(this);
				mDialog.findViewById(R.id.logout_cancel).setOnClickListener(this);
				mDialog.findViewById(R.id.logout_layout).setOnClickListener(this);
			}
			mDialog.show();
			break;
		case R.id.logout_confirm:
			
			break;
		case R.id.logout_cancel:
			mDialog.dismiss();
			break;
		case R.id.logout_layout:
			mDialog.dismiss();
			break;

		default:
			break;
		}
	}
}
