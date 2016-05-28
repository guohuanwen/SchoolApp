package com.example.learn.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.learn.R;
import com.example.learn.model.DataDB;
import com.example.learn.model.MyClass;
import com.example.learn.model.WeekClass;
import com.example.learn.tool.MyApplication;
import com.example.learn.tool.MyDialog;

import android.R.string;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fragment_MyClass_Presenter {
	private DataDB dataDB;
	private Context context;
	private Handler handler;
	private List classList;

	public Fragment_MyClass_Presenter(Handler handler, Context context) {
		this.handler = handler;
		this.context = context;
		dataDB = DataDB.getInstance(context);
	}

	public void setClassButtonText(final List list, final String week) {

		new Thread(new Runnable() {
			public void run() {
				classList = dataDB.loadMyClass(week);
				Log.i("Fragment_MyClass_Presenter", list.size() + "=="
						+ classList.size());
				Message msg;
				Bundle bundle;
				Message m = new Message();
				m.what = 3;
				handler.sendMessage(m);

				for (int i = 0; i < classList.size(); i++) {
					
					msg = new Message();
					bundle = new Bundle();
					WeekClass weekClass = (WeekClass) classList.get(i);
					
					int a = Integer.valueOf(weekClass.getClassNum()) - 1;
					bundle.putString("text", weekClass.getClassName() + "@"
							+ weekClass.getClassPlace());
					bundle.putString("nub", a + "");
					msg.setData(bundle);
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	
	private String id = "0";
	public void touchButton(final int param) {
		boolean find = false;
		
		WeekClass weekClass = new WeekClass();
		weekClass.setClassName("");
		weekClass.setClassNum("");
		weekClass.setClassPlace("");
		weekClass.setClassWeek("");
		weekClass.setTeatherName("");
		weekClass.setMatchWeek("");
		for (int i = 0; i < classList.size(); i++) {
			weekClass = (WeekClass) classList.get(i);
			if (weekClass.getClassNum().equals(param + "")) {
				find = true;
				id=weekClass.getId();
				break;
			}
		}
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_class,
				null);
		final EditText className = (EditText) view
				.findViewById(R.id.list_textView2);
		final EditText teacherName = (EditText) view
				.findViewById(R.id.list_textView4);
		final EditText classPlace = (EditText) view
				.findViewById(R.id.list_textView6);
		final EditText classWeek = (EditText) view
				.findViewById(R.id.list_textView8);
		if (find) {
			className.setText(weekClass.getClassName());
			teacherName.setText(weekClass.getTeatherName());
			classPlace.setText(weekClass.getClassPlace());
			classWeek.setText(weekClass.getClassWeek());
		}
		final MyDialog myDialog = new MyDialog();
		myDialog.dialogShow(4, context, view, new OnClickListener() {
			@Override
			public void onClick(View v) {
				dataDB.saveMyClass(id,className.getText().toString(), teacherName
						.getText().toString(), classPlace.getText().toString(),
						classWeek.getText().toString(), param);
				myDialog.cancel();
			}
		}, new OnClickListener() {

			@Override
			public void onClick(View v) {
				myDialog.cancel();

			}
		});
	}

	public void timeButoonListener(final Button button) {

		final CharSequence[] a = { "第1周", "第2周", "第3周", "第4周", "第5周", "第6周",
				"第7周", "第8周", "第9周", "第10周", "第11周", "第12周", "第13周", "第14周",
				"第15周", "第16周", "第17周", "第18周", "第19周", "第20周", "第21周", "第22周",
				"第23周", "第24周", "第25周" };
		new AlertDialog.Builder(context).setTitle("设置周数")
				.setItems(a, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						button.setText(a[which] + "");
						Message message = new Message();
						message.what = 2;
						Bundle bundle = new Bundle();
						int a = which + 1;
						bundle.putString("nub", a + "");
						message.setData(bundle);
						handler.sendMessage(message);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}
}
