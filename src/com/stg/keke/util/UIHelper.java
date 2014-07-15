package com.stg.keke.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stg.keke.R;

public class UIHelper {

	private static ProgressDialog progressDialog;
	private static Dialog mDialog;
	private static Toast mNormalToast;

	public static void hideTitle(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public static void showPd(Context context) {
		showLoadDialog(context, context.getString(R.string.loading));
	}

	public static void showProgressDialog(Context context, String title,
			String msg) {
		if (progressDialog != null && progressDialog.isShowing() == true) {
			return;
		}
		progressDialog = ProgressDialog.show(context, title, msg, true, true);
	}

	public static void showLoadDialog(Context context, String msg) {
		mDialog = new Dialog(context,R.style.CustomProgressDialog);
		View view = LayoutInflater.from(context).inflate(R.layout.load, null); 
		ImageView img_loading = (ImageView) view.findViewById(R.id.img_load);
		RelativeLayout img_close = (RelativeLayout) view.findViewById(R.id.img_cancel);
		img_close.setVisibility(View.GONE);
		TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		tv_msg.setText(msg);
		RotateAnimation rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.refresh); 
		img_loading.setAnimation(rotateAnimation);
		mDialog.setContentView(view);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.show();
		mDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				return true;
			}
		});
	}

	public static void disLoadDialog() {
		if (null != mDialog && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	public static void showProgressDialog(Context context, int titleId,
			int msgId) {
		showProgressDialog(context, context.getString(titleId),
				context.getString(msgId));
	}

	public static void dismissProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	public static void showToastShort(Context context, String content) {
		showToast(context, content, 0);
	}

	public static void showToastShort(Context context, int resId) {
		showToastShort(context, context.getString(resId));
	}

	public static void showToastLong(Context context, String content) {
		showToast(context, content, 1);
	}

	public static void showToastLong(Context context, int resId) {
		showToastLong(context, context.getString(resId));
	}

	public static void showToast(Context context, String content, int duration) {
		if (mNormalToast == null) {
			mNormalToast = Toast.makeText(context, content, duration);
			mNormalToast.show();
		} else {
			mNormalToast.setText(content);
			mNormalToast.show();
		}
	}

	public static void toast(Context context, String content) {
		showToastShort(context, content);
	}

	public static void toast(Context context, int contentid) {
		toast(context, context.getString(contentid));
	}

	public static void showToast(Context context, int resId, int duration) {
		showToast(context, context.getString(resId), duration);
	}

}
