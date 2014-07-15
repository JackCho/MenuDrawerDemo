package com.stg.keke.activity;

import java.util.ArrayList;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.stg.keke.R;
import com.stg.keke.adapter.MenuAdapter;
import com.stg.keke.adapter.MenuAdapter.ViewHolder;
import com.stg.keke.fragment.BaseFragment;
import com.stg.keke.fragment.CaseFragment;
import com.stg.keke.fragment.HireFragment;
import com.stg.keke.fragment.HomeFragment;
import com.stg.keke.fragment.HotspotFragment;
import com.stg.keke.fragment.InfomationFragment;
import com.stg.keke.fragment.SettingFragment;
import com.stg.keke.fragment.ShareFragment;
import com.stg.keke.fragment.SpecialFragment;
import com.stg.keke.model.Item;
import com.stg.keke.util.CommonUtils;

public class MainActivity extends FragmentActivity implements View.OnClickListener, TopListener{


    private MenuDrawer mMenuDrawer;

    private ImageView mMenuAvatar;
    private TextView mMenuName;
    private GridView mMenuGridView;
    
    private EditText mSearchEt;
    
    private BaseFragment fragment;
    
    private List<Item> items;
    private MenuAdapter mAdapter;
    
    private final int[] selects = {R.drawable.btn_menu_hot_spot_2, R.drawable.btn_menu_information_2, R.drawable.btn_menu_special_2,
    						R.drawable.btn_menu_case_2, R.drawable.btn_menu_share_2, R.drawable.btn_menu_recruitment_2,
    						R.drawable.btn_menu_home_2, R.drawable.btn_menu_download_2, R.drawable.btn_menu_set_up_2};
    
    private final int[] pics = {R.drawable.menu_hotspot_selector, R.drawable.menu_information_selector, R.drawable.menu_special_selector,
				    		R.drawable.menu_case_selector, R.drawable.menu_share_selector, R.drawable.menu_hire_selector,
				    		R.drawable.menu_home_selector, R.drawable.menu_download_selector, R.drawable.menu_setting_selector};
    
    private final int[] titles = {R.string.title_hotpot,R.string.title_info,
    		R.string.title_column,R.string.title_case,
    		R.string.title_share,R.string.title_hire,R.string.title_head,
    		R.string.title_offline,R.string.title_setting};
    
    private Dialog mLoginDialog;
    private Dialog mSearchDialog;
    
    private Handler mHandler;
    
    @Override
    public void onCreate(Bundle inState) {
        super.onCreate(inState);
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, Position.LEFT, MenuDrawer.MENU_DRAG_WINDOW);
        mMenuDrawer.setContentView(R.layout.activity_main);
        mMenuDrawer.setMenuView(R.layout.menu);
        
        mHandler = new Handler();
        
        initMenu();
        initContent();
    }

	private void initContent() {
		replaceFragment(6);
	}

	private void replaceFragment(final int pos) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if(fragment != null){
			transaction.detach(fragment);
		}
		
		switch (pos) {
		case 0:
			fragment = new HotspotFragment();
			break;
		case 1:
			fragment = new InfomationFragment();
			break;
		case 2:
			fragment = new SpecialFragment();
			break;
		case 3:
			fragment = new CaseFragment();
			break;
		case 4:
			fragment = new ShareFragment();
			break;	
		case 5:
			fragment = new HireFragment();
			break;
		case 6:
			fragment = new HomeFragment();
			break;	
		case 8:
			fragment = new SettingFragment();
			break;
		default:
			break;
		}
		transaction.replace(R.id.main_content_layout, fragment);
		transaction.commit();
	}

	private void initMenu() {
		mMenuAvatar = (ImageView) findViewById(R.id.menu_avatar);
        mMenuName = (TextView) findViewById(R.id.menu_name);
        mMenuGridView = (GridView) findViewById(R.id.menu_grid);
        mMenuGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mMenuAvatar.setOnClickListener(this);
        
        items = new ArrayList<Item>();
        int length = pics.length;
        Item item = null;
	    for (int i = 0; i < length; i++) {
	    	item = new Item(pics[i], titles[i]);
	    	items.add(item);
		}
        mAdapter = new MenuAdapter(this, items);
        mMenuGridView.setAdapter(mAdapter);
        mMenuGridView.setOnItemClickListener(mItemClickListener);
        
        showSelect(6);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMenuDrawer.toggleMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mLoginDialog != null && mLoginDialog.isShowing()) {
        	mLoginDialog.dismiss();
        	return;
        }

        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }
        
        if (mSearchDialog != null && mSearchDialog.isShowing()) {
        	mSearchDialog.dismiss();
        	return;
		}
       
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.menu_avatar:
			if (mLoginDialog == null) {
				mLoginDialog = new Dialog(MainActivity.this, R.style.login_dialog);
				mLoginDialog.setCanceledOnTouchOutside(true);
				Window win = mLoginDialog.getWindow();
				LayoutParams params = new LayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
				params.height = LayoutParams.WRAP_CONTENT;
				params.x = 0;
				params.y = 0;
				win.setAttributes(params);
				mLoginDialog.setContentView(R.layout.dialog_login);
				mLoginDialog.findViewById(R.id.login_by_qq).setOnClickListener(this);
				mLoginDialog.findViewById(R.id.login_by_sina).setOnClickListener(this);
				mLoginDialog.findViewById(R.id.login_cancel).setOnClickListener(this);
				mLoginDialog.findViewById(R.id.login_layout).setOnClickListener(this);
			}
			mLoginDialog.show();
			break;
		case R.id.login_by_qq:
			Intent intent_qq = new Intent(MainActivity.this, LoginActivity.class);
			intent_qq.putExtra("issina", false);
			startActivity(intent_qq);
			overridePendingTransition(R.anim.in_from_right, 0);
			mLoginDialog.dismiss();
			break;
		case R.id.login_by_sina:
			Intent intent_sina = new Intent(MainActivity.this, LoginActivity.class);
			intent_sina.putExtra("issina", true);
			startActivity(intent_sina);
			overridePendingTransition(R.anim.in_from_right, 0);
			mLoginDialog.dismiss();
			break;
		case R.id.login_cancel:
		case R.id.login_layout:
			mLoginDialog.dismiss();
			break;
		case R.id.search_cancel_btn:
		case R.id.search_layout:
			mSearchDialog.dismiss();
			break;

		default:
			break;
		}
    }

	private void showSearchView() {
		if (mSearchDialog == null) {
			mSearchDialog = new Dialog(MainActivity.this, R.style.search_dialog);
			Window win = mSearchDialog.getWindow();
			LayoutParams params = new LayoutParams();
			params.width = LayoutParams.MATCH_PARENT;
			params.height = LayoutParams.MATCH_PARENT;
			params.x = 0;
			params.y = 0;
			win.setAttributes(params);
			mSearchDialog.setContentView(R.layout.dialog_search);
			mSearchDialog.findViewById(R.id.search_cancel_btn).setOnClickListener(this);
			mSearchDialog.findViewById(R.id.search_layout).setOnClickListener(this);
			mSearchEt = (EditText) mSearchDialog.findViewById(R.id.search_et);
			mSearchEt.setOnEditorActionListener(mEditorActionListener);
		}
		
		mSearchEt.requestFocus();
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				CommonUtils.showSystemKeyBoard(mSearchEt);
			}
		}, 300);
		
		mSearchDialog.show();
	}
    
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mAdapter.notifyDataSetChanged();
			showSelect(position);
		}
	};
	
	private void showSelect(final int pos) {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				ViewHolder holder = (ViewHolder) mMenuGridView.getChildAt(pos).getTag();
		        holder.pic.setBackgroundResource(selects[pos]);
		        //排除离线下载
		        if (pos != 7) {
		        	replaceFragment(pos);
			        mMenuDrawer.closeMenu();
			        mHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							 fragment.setTitle(getString(titles[pos]));
						}
					}, 200);
				}
			}
		}, 200);
	}
	
	private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView v, int actionId,KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
				InputMethodManager manager = (InputMethodManager) mSearchEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				String keyWord = mSearchEt.getText().toString();
				Log.e("test", "keyWord---->" + keyWord);
			}
			return false;
		}
	};

	@Override
	public void toggle() {
		mMenuDrawer.toggleMenu();
	}

	@Override
	public void search() {
		showSearchView();
	}
}
