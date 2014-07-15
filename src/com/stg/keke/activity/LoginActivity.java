package com.stg.keke.activity;

import static com.stg.keke.config.ThirdPartyLoginConfig.QQ_AUTH_URL;
import static com.stg.keke.config.ThirdPartyLoginConfig.QQ_CLIENT_ID;
import static com.stg.keke.config.ThirdPartyLoginConfig.QQ_CONTAINS_CODE;
import static com.stg.keke.config.ThirdPartyLoginConfig.QQ_OPEN_URL;
import static com.stg.keke.config.ThirdPartyLoginConfig.SINA_AUTH_URL;
import static com.stg.keke.config.ThirdPartyLoginConfig.SINA_CONTAINS_CODE;
import static com.stg.keke.config.ThirdPartyLoginConfig.SINA_GETUID;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.stg.keke.R;
import com.stg.keke.util.HttpUtil;
import com.stg.keke.util.UIHelper;


public class LoginActivity extends BaseActivity {

	private WebView webView;

	private String uid;
	private String nickname;
	
	private String qq_access_token;
	private String qq_open_id;

	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				toLogin();
				break;
			case 5:
				new Thread(){
					@Override
					public void run() {
						nickname = getQQNick(qq_access_token, qq_open_id);
						uid = qq_open_id;
						handler.sendEmptyMessage(0);
					}
				}.start();
				break;
			default:
				break;
			}
		}
	};

	protected void onDestroy() {
		UIHelper.dismissProgressDialog();
		super.onDestroy();
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initViews();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private final void initViews() {
		webView = (WebView) findViewById(R.id.login_webview);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(false);
		settings.setDefaultTextEncodingName("UTF-8");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, final String url) {
				if (url.contains(SINA_CONTAINS_CODE)) {
					String code = url.substring(url.indexOf("code=")).replace("code=", "");
					final String posturl =SINA_GETUID + code;
					if (HttpUtil.isNetworkAvailable(LoginActivity.this)) {
						UIHelper.showProgressDialog(LoginActivity.this, "", getString(R.string.loading));
						new Thread(){
							public void run() {
								try {
									String result = HttpUtil.postRequest(posturl, null);
									JSONObject root = new JSONObject(result);
									String token = root.getString("access_token");
									nickname = getSinaNick(token,root.getString("uid"));
									uid = root.getString("uid");
									handler.sendEmptyMessage(0);
								} catch (Exception e) {
									e.printStackTrace();
								}
									
							};
						}.start();
					}
				} else if (url.contains(QQ_CONTAINS_CODE)) {
					if(HttpUtil.isNetworkAvailable(LoginActivity.this)){
						if (TextUtils.isEmpty(qq_access_token)) {
							qq_access_token = url.substring(url.indexOf("=") + 1, url.indexOf("&expires_in"));
							UIHelper.showProgressDialog(LoginActivity.this, "", getString(R.string.loading));
							new Thread(){
								public void run() {
									getOpenId();
									handler.sendEmptyMessage(5);
								};
							}.start();
						}
					}
				} else {
					view.loadUrl(url);
				}
				return true;
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

		});

		if (getIntent().getBooleanExtra("issina", false)) {
			webView.loadUrl(SINA_AUTH_URL);
		} else {
			webView.loadUrl(QQ_AUTH_URL);
		}
	}
	
	private final void toLogin(){
	}

	private final String getSinaNick(String access_token, String uid) {
		try {
			String url = "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid;
			String sina_result = HttpUtil.getRequest(url, null);
			Log.e("test", "sina_result---->" + sina_result);
			return new JSONObject(sina_result).getString("name");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private final String getQQNick(String token, String uid) {
		try {
			String url = "https://graph.qq.com/user/get_user_info?oauth_consumer_key="
					+ QQ_CLIENT_ID
					+ "&openid="
					+ uid
					+ "&access_token="
					+ token;
			String qq_result = HttpUtil.getRequest(url, null);
			Log.e("test", "qq_result---->" + qq_result);
			return new JSONObject(qq_result) .getString("nickname");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private final String getOpenId() {
		try {
			String url = QQ_OPEN_URL + qq_access_token;
			String result = HttpUtil.getRequest(url, null);
			result = result.substring(result.indexOf("(") + 1, result.indexOf(")"));
			qq_open_id = new JSONObject(result).getString("openid");
			return qq_open_id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
