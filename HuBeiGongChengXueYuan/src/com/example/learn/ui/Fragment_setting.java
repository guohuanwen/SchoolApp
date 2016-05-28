package com.example.learn.ui;

import java.util.Calendar;
import java.util.Date;

import com.example.learn.R;
import com.example.learn.model.UserDB;
import com.example.learn.tool.MyDialog;
import com.example.learn.tool.MyTime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_setting extends Fragment{
	private View view;
	private Button weekNub;
	private Button about;
	private UserDB userDB;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_setting, null);
		init();
		return view;
	}
	private void init() {
		userDB=UserDB.getInstance(getActivity());
		weekNub=(Button)view.findViewById(R.id.button1);
		about=(Button)view.findViewById(R.id.button2);
		
		weekNub.setOnClickListener(new WeekListener());
		about.setOnClickListener(new AboutListener());
	}
	
	
	class WeekListener implements OnClickListener{
		public void onClick(View v) {
			final CharSequence[] a = { "第1周", "第2周", "第3周", "第4周", "第5周", "第6周",
					"第7周", "第8周", "第9周", "第10周", "第11周", "第12周", "第13周", "第14周",
					"第15周", "第16周", "第17周", "第18周", "第19周", "第20周", "第21周", "第22周",
					"第23周", "第24周", "第25周" };
			new AlertDialog.Builder(getActivity()).setTitle("设置周数")
					.setItems(a, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							userDB.saveNowWeek(a[which]+"",MyTime.getTimesWeekmorning()+"");
							
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
		}
		
	}
	
	class AboutListener implements OnClickListener{
		public void onClick(View v) {
			TextView textView=new TextView(getActivity());
			textView.setTextSize(22);
			textView.setText("软件版本：4.3 \n\n"+"软件说明：本软件是个人开发,网络和界面优化方面有所不足，后期会慢慢改善，有什么建议或者意见请发邮件至1179636441@qq.com\n\n");
			final MyDialog myDialog = new MyDialog();
			myDialog.dialogShow(4, getActivity(), textView, new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					myDialog.cancel();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					myDialog.cancel();

				}
			});
		}
		
	}
	
	
	
	

}
