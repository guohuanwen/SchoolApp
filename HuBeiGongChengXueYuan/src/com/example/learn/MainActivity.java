package com.example.learn;

import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bluemor.reddotface.util.Callback;
import com.bluemor.reddotface.util.Invoker;
import com.bluemor.reddotface.util.Util;
import com.bluemor.reddotface.view.DragLayout;
import com.bluemor.reddotface.view.DragLayout.DragListener;
import com.example.learn.R;
import com.example.learn.model.MyClass;
import com.example.learn.ui.Fragment_Login;
import com.example.learn.ui.Fragment_MyClass;
import com.example.learn.ui.Fragment_News;
import com.example.learn.ui.Fragment_NewsText;
import com.example.learn.ui.Fragment_Score;
import com.example.learn.ui.Fragment_Score_Grade;
import com.example.learn.ui.Fragment_setting;
import com.gitonway.lee.niftymodaldialogeffects.lib.effects.NewsPaper;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends FragmentActivity {

	private MyHandler myHandler;
	private DragLayout dl;
	private ListView lv;
	// private TextView tv_noimg;
	private ImageView iv_icon, iv_bottom;
	private FrameLayout frameLayout;
	private Fragment fragment_myClass;
	private Fragment fragment_NewsText;
	private Fragment nowFragment;
	 private Fragment fragment_login;
	 private Fragment_Score fragment_Score;
	 private Fragment fragment_AllClass;
	 private Fragment fragment_News;
	 private Fragment fragment_Setting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Util.initImageLoader(this);

		// 初始化
		initView();
		

		initDragLayout();
		// changeFrameLayout(fragment_login);
		// 费时动作
		initLongTime();

	}

	// 侧方菜单
	private void initDragLayout() {

		dl.setDragListener(new DragListener() {
			@Override
			public void onOpen() {
				lv.smoothScrollToPosition(new Random().nextInt(30));
			}

			@Override
			public void onClose() {
				// shake();
			}

			@Override
			public void onDrag(float percent) {
				ViewHelper.setAlpha(iv_icon, 1 - percent);
			}
		});
	}

	private void changeFrameLayout(Fragment targetFragment) {
		if (nowFragment != null) {
			if (nowFragment == fragment_NewsText) {
				removeFrameLayout(nowFragment);
				nowFragment=null;
			}
		}
		Log.i("MainActivity", "changeFrameLayout");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.add(R.id.main_fragment, targetFragment, "fragment");
		ft.replace(R.id.main_frameLayout, targetFragment, "fragment");
		ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.commit();
	}
	
	
	private void hideReplaceFrameLayout(Fragment targetFragment){
		Log.i("MainActivity","hideFragmentLayout");
		FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
		if(nowFragment!=null){
			ft.hide(nowFragment);
		}
		if(targetFragment.isAdded()){
			ft.show(targetFragment);
		}else{
			ft.add(R.id.main_frameLayout, targetFragment, "fragment");
		}
		
		ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.commit();
		nowFragment = targetFragment;
	}

	private void addFrameLayout(Fragment targetFragment) {
		Log.i("MainActivity", "changeFrameLayout");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.main_frameLayout, targetFragment, "fragment");
		// ft.replace(R.id.main_frameLayout, targetFragment, "fragment");
		ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.commit();
		nowFragment = targetFragment;
	}

	private void removeFrameLayout(Fragment targetFragment) {
		Log.i("MainActivity", "changeFrameLayout");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.remove(targetFragment);
		// ft.replace(R.id.main_frameLayout, targetFragment, "fragment");
		ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.commit();
	}

	/**
	 * 费时命令
	 */
	private void initLongTime() {
		new Thread(new Runnable() {
			public void run() {
				fragment_myClass = new Fragment_MyClass();
				sengTheMessage(0);
				fragment_login=new Fragment_Login(myHandler);
				fragment_Score=new Fragment_Score();
				fragment_News=new Fragment_News(myHandler);
				fragment_Setting=new Fragment_setting();
			}
		}).start();
	}

	private void initView() {

		dl = (DragLayout) findViewById(R.id.dl);
		frameLayout = (FrameLayout) findViewById(R.id.main_frameLayout);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
		
//		iv_icon.setBackgroundResource(R.drawable.my_launcher);
//		iv_bottom.setBackgroundResource(R.drawable.my_launcher);
		
		myHandler = new MyHandler();

		
		// 侧方listview
		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.item_text, new String[] { "课表", "登录", "成绩", "新闻","设置" }));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.i("MainActivity", "onItemClick" + position);
				// Util.t(getApplicationContext(), "click " + position);
				final int aa = position;
				switch (position) {
				case 0:
					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(150);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							fragment_myClass = new Fragment_MyClass();
							sengTheMessage(aa);
						}
					}).start();
					break;
				case 1:
					sengTheMessage(position);
					break;
				case 2:
					sengTheMessage(position);
					break;
				case 3:
					sengTheMessage(position);
					break;
				case 4:
					sengTheMessage(6);
					break;
				default:
					sengTheMessage(1);
					break;
				}
				dl.close();
			}
		});
		iv_icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dl.open();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// loadImage();
	}

	private void sengTheMessage(int param) {
		Message message = new Message();
		message.what = param;
		myHandler.sendMessage(message);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// Log.i("MainActivity",
		// "dispatchTouchEvent:"+super.dispatchTouchEvent(ev));
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.i("MainActivity",
		// "dispatchTouchEvent:"+super.dispatchTouchEvent(ev));
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}
	
	public Fragment getNowFragment(){
		return this.nowFragment;
	}

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				hideReplaceFrameLayout(fragment_myClass);
				break;
			case 1:
//				Fragment fragment1 = new Fragment_Login(myHandler);
				hideReplaceFrameLayout(fragment_login);
				break;
			case 2:
//				Fragment fragment2 = new Fragment_Score();
				hideReplaceFrameLayout(fragment_Score);
				break;
			case 3:
//				Fragment fragment3 = new Fragment_News(myHandler);
				hideReplaceFrameLayout(fragment_News);
				break;
			case 4:
				int param = msg.getData().getInt("param");
				fragment_NewsText = new Fragment_NewsText(param);
				hideReplaceFrameLayout(fragment_NewsText);
				break;
			case 5:
				initLongTime();
				break;
			case 6:
//				Fragment fragment4 = new Fragment_setting();
				hideReplaceFrameLayout(fragment_Setting);
				break;
			default:
				break;
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (nowFragment != null) {
				if (nowFragment == fragment_NewsText) {
					hideReplaceFrameLayout(fragment_News);
					nowFragment=fragment_News;
				} else {
					exit();
				}
			}else{
				exit();
			}
		}

		return false;
	}

	private long exitTime = System.currentTimeMillis();

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 1000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);
		}
	}

}
