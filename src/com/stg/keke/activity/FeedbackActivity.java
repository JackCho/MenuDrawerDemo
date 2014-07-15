package com.stg.keke.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.stg.keke.R;

public class FeedbackActivity extends BaseActivity{

	private EditText mFeedback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		
		mFeedback = (EditText) findViewById(R.id.feedback_et);
		mFeedback.setOnEditorActionListener(mEditorActionListener);
	}
	
	private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView v, int actionId,KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_SEND) {
				InputMethodManager manager = (InputMethodManager) mFeedback.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				String content = mFeedback.getText().toString();
			}
			return false;
		}
	};
}
