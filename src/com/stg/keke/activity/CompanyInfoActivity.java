package com.stg.keke.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.stg.keke.R;

public class CompanyInfoActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_companyinfo);
		
	}
	
	public void terminate(View v){
		finish();
		overridePendingTransition(0, R.anim.out_from_left);
	}
}
