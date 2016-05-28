package com.example.learn.tool;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class MyApplication extends Application {
	/*
	 * 在<application中添加
	 * 
    	android:name="com.example.learn.tool.MyApplication"
    */
	private static Context context;

	@Override
	public void onCreate() {
		Log.i("MyApplication", "onCreate");
		context = getApplicationContext();
	}

	public static Context getContext() {
		Log.i("MyApplication", "getContext");
		return context;
	}
	
	public static int[] getScreenWidthHeight(){
		int a[]={0,0};
		// 方法1 Android获得屏幕的宽和高    
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);   
        Display display = windowManager.getDefaultDisplay();    
        int screenWidth = screenWidth = display.getWidth();    
        int screenHeight = screenHeight = display.getHeight();
		a[0]=screenWidth;
		a[1]=screenHeight;
        return a;
		
	}

}
