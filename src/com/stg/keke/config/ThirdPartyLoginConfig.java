package com.stg.keke.config;

public class ThirdPartyLoginConfig {

	public static final int SINA_LOGIN = 0;
	public static final int QQ_LOGIN = 1;

	private static final String SINA_CLIENT_ID = "2946848067";
	private static final String SINA_CALLBACK = "https://openapi.baidu.com/social/oauth/2.0/receiver";
	private static final String SINA_CLIENT_SECRET = "404ed85f3738ea9f626ab5d38e0dae0d";
	public static final String SINA_AUTH_URL = "https://api.weibo.com/oauth2/authorize?client_id="
			+ SINA_CLIENT_ID
			+ "&redirect_uri="
			+ SINA_CALLBACK
			+ "&response_type=code&display=mobile&state=authorize";
	public static final String SINA_CONTAINS_CODE = SINA_CALLBACK
			+ "?state=authorize&code=";
	public static final String SINA_GETUID = "https://api.weibo.com/oauth2/access_token?client_id="
			+ SINA_CLIENT_ID
			+ "&client_secret="
			+ SINA_CLIENT_SECRET
			+ "&grant_type=authorization_code&redirect_uri="
			+ SINA_CALLBACK
			+ "&code=";
	

	public static final String QQ_CLIENT_ID = "100396857";
	public static final String QQ_AUTH_URL = "https://graph.qq.com/oauth2.0/authorize?status_userip=&display=mobile&scope=add_share,add_t,get_simple_userinfo&redirect_uri=auth://tauth.qq.com/&response_type=token&client_id="
			+ QQ_CLIENT_ID;
	public static final String QQ_CONTAINS_CODE = "auth://tauth.qq.com/?#access_token=";
	public static final String QQ_OPEN_URL = "https://graph.qq.com/oauth2.0/me?access_token=";
	


	public static String getSinaUidUrl(String url) {
		return SINA_GETUID + url.replace(SINA_CONTAINS_CODE, "");
	}

}
