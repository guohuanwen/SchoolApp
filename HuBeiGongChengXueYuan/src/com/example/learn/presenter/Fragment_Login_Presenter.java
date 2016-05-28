package com.example.learn.presenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.learn.net.NetPresenter;
import com.example.learn.ui.Fragment_Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.Button;

public class Fragment_Login_Presenter {
	// private Fragment_Login fragment_Login;
	private NetPresenter netPresenter;
	private Context context;
	private Handler handler;

	// private Handler fragment_Login_Myhandler;

	public Fragment_Login_Presenter(Context context,Handler handler) {
		// fragment_Login = new Fragment_Login();
		// fragment_Login_Myhandler = fragment_Login.new MyHandler();
		this.context=context;
		this.handler=handler;
		netPresenter = NetPresenter.getInstence();
	}

	/**
	 * 向Fragment_Login发送消息
	 */

	private void sendMessageToFragment_Login(int param, Handler handler) {
		Message message = new Message();
		message.what = param;
		handler.sendMessage(message);
	}

	/**
	 * 获取验证码图片,并通知Fragment_Login更新验证码
	 */
	private Bitmap bitmap;

	public Bitmap getBitmap() {
		return this.bitmap;
	}

	public void getCheckCodePhoto(final Handler handler) {
		new Thread(new Runnable() {
			public void run() {
				// bitmap可能为null
//				 if (netPresenter.getCookie() == 1) {
				try{
					bitmap = netPresenter.getCheckCodePhoto();
					sendMessageToFragment_Login(1, handler);
				}catch(Exception e){
					Log.e("getCheckCodePhoto",e.toString());
				}
				
				// saveMyBitmap("bitmap", bitmap);
				// sendMessageToFragment_Login(1);

				// } else {
				// 检查网络，稍后重试
//				 }
			}
		}).start();
	}

	/**
	 * 如果返回1，则验证成功，返回0则用户名，密码或者验证码错误
	 */
	private int logNub = 0;

	public int logIn(final String id, final String password,
			final String checkCode, final String classTime,final Handler handler, final int a[]) {
		new Thread(new Runnable() {
			public void run() {
				logNub = netPresenter.logIn(id, password, checkCode);
				Log.i("Fragment_Login_Presenter", "logIn"+logNub);
				if (logNub == 1) {
					// 准备页面
					netPresenter.getPrepare();
					sendMessageToFragment_Login(13, handler);
					sendMessageToFragment_Login(14, handler);
					if (a[0] == 1) {
						if (getScore() == 1) {
							sendMessageToFragment_Login(15, handler);
						}
					}
					if (a[1] == 1) {
						if (getMyClass(classTime) == 1) {
							sendMessageToFragment_Login(16, handler);
						}
					}
					// if (a[2] == 1) {
					// getAllClass();
					// }
					if (a[2] == 1) {
						getTest();
						// sendMessageToFragment_Login(17, handler);}
					}

					 sendMessageToFragment_Login(17, handler);
				}else{
					sendMessageToFragment_Login(9, handler);
				}
			}
		}).start();

		return logNub;
	}

	/**
	 * 期末成绩 if return 1,success;else return 0,fail
	 */
	private int scoreNub = 0;

	public int getScore() {
		Log.i("Fragment_Login_Presenter", "getScore");
		scoreNub = netPresenter.getScore();

		return scoreNub;
	}

	/**
	 * 校园新闻 if return 1,success;else return 0,fail
	 */
	private int newsNub = 0;

	public int getNews() {
		newsNub = netPresenter.getNews();

		return newsNub;
	}

	/**
	 * 课表 if return 1,success;else return 0,fail
	 */
	private int classNub = 0;

	public int getMyClass(String time) {
		classNub = netPresenter.getClass(time);
		return classNub;
	}

	/**
	 * 大学全部课程 if return 1,success;else return 0,fail
	 */
	private int allClassNub = 0;

	public int getAllClass() {

		allClassNub = netPresenter.getAllClass();

		return allClassNub;
	}

	/**
	 * 等级考试成绩 if return 1,success;else return 0,fail
	 */
	private int testNub = 0;

	public int getTest() {
		testNub = netPresenter.getTest();

		return testNub;
	}
	
	public void schoolTimeListener(final Button button){
		final CharSequence[] a = { "2010-2011-1", "2010-2011-2", "2011-2012-1", "2011-2012-2", "2012-2013-1",
				"2012-2013-2", "2013-2014-1","2013-2014-2","2014-2015-1","2014-2015-2",
				"2015-2016-1","2015-2016-2","2016-2017-1","2016-2017-2",
				"2017-2018-1" ,"2017-2018-2","2018-2019-1","2018-2019-2"};
		new AlertDialog.Builder(context).setTitle("设置课表")
				.setItems(a, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						button.setText(a[which]+"");
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}

}
