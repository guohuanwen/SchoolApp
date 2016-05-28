package com.example.learn.ui;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learn.R;
import com.example.learn.model.UserDB;
import com.example.learn.presenter.Fragment_MyClass_Presenter;
import com.example.learn.tool.MyTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment_MyClass extends Fragment implements View.OnClickListener {
	private Button ty1;
	private Button ty2;
	private Button ty3;
	private Button ty4;
	private Button ty5;
	private Button ty6;
	private Button ty7;
	private Button ty8;
	private Button ty9;
	private Button ty10;
	private Button ty11;
	private Button ty12;
	private Button ty13;
	private Button ty14;
	private Button ty15;
	private Button ty16;
	private Button ty17;
	private Button ty18;
	private Button ty19;
	private Button ty20;
	private Button ty21;
	private Button ty22;
	private Button ty23;
	private Button ty24;
	private Button ty25;
	private Button ty26;
	private Button ty27;
	private Button ty28;
	private Button ty29;
	private Button ty30;
	private Button ty31;
	private Button ty32;
	private Button ty33;
	private Button ty34;
	private Button ty35;
	private Button ty36;
	private Button ty37;
	private Button ty38;
	private Button ty39;
	private Button ty40;
	private Button ty41;
	private Button ty42;
	private Button time;
	// private ImageButton Back;
	// private Button Time;
	private ArrayList<Button> button = new ArrayList<Button>();
	private View view;
	private Fragment_MyClass_Presenter fragment_MyClass_Presenter;
	private MyHandler myHandler;
	private UserDB userDB;
	private Context context;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_myclass, null, false);
		Log.i("Frament_myClass", "OnCreateView");
		// 资源指定
		setRES();
		Log.v("1", "aaa");
		// 填充button List
		FillButtonList();

		// 为button添加tag
		setButtonTag();
		// 填充数据
		setMyClass();
		return view;
	}

	private void setRES() {
		context = getActivity();
		// Back = (ImageButton) view.findViewById(R.id.myclassBack);
		// Back.setOnClickListener(new BackListener());
		// Time = (Button) view.findViewById(R.id.myclassTime);
		// Time.setOnClickListener(new TimeListener());

		userDB = UserDB.getInstance(context);
		myHandler = new MyHandler();

		fragment_MyClass_Presenter = new Fragment_MyClass_Presenter(myHandler,
				getActivity());

		time = (Button) view.findViewById(R.id.time);
		time.setOnClickListener(new TimeListener());

		ty1 = (Button) view.findViewById(R.id.textView1);
		ty2 = (Button) view.findViewById(R.id.textView2);
		ty3 = (Button) view.findViewById(R.id.textView3);
		ty4 = (Button) view.findViewById(R.id.textView4);
		ty5 = (Button) view.findViewById(R.id.textView5);
		ty6 = (Button) view.findViewById(R.id.textView6);
		ty7 = (Button) view.findViewById(R.id.textView7);
		ty8 = (Button) view.findViewById(R.id.textView8);
		ty9 = (Button) view.findViewById(R.id.textView9);
		ty10 = (Button) view.findViewById(R.id.textView10);
		ty11 = (Button) view.findViewById(R.id.textView11);
		ty12 = (Button) view.findViewById(R.id.textView12);
		ty13 = (Button) view.findViewById(R.id.textView13);
		ty14 = (Button) view.findViewById(R.id.textView14);
		ty15 = (Button) view.findViewById(R.id.textView15);
		ty16 = (Button) view.findViewById(R.id.textView16);
		ty17 = (Button) view.findViewById(R.id.textView17);
		ty18 = (Button) view.findViewById(R.id.textView18);
		ty19 = (Button) view.findViewById(R.id.textView19);
		ty20 = (Button) view.findViewById(R.id.textView20);
		ty21 = (Button) view.findViewById(R.id.textView21);
		ty22 = (Button) view.findViewById(R.id.textView22);
		ty23 = (Button) view.findViewById(R.id.textView23);
		ty24 = (Button) view.findViewById(R.id.textView24);
		ty25 = (Button) view.findViewById(R.id.textView25);
		ty26 = (Button) view.findViewById(R.id.textView26);
		ty27 = (Button) view.findViewById(R.id.textView27);
		ty28 = (Button) view.findViewById(R.id.textView28);
		ty29 = (Button) view.findViewById(R.id.textView29);
		ty30 = (Button) view.findViewById(R.id.textView30);
		ty31 = (Button) view.findViewById(R.id.textView31);
		ty32 = (Button) view.findViewById(R.id.textView32);
		ty33 = (Button) view.findViewById(R.id.textView33);
		ty34 = (Button) view.findViewById(R.id.textView34);
		ty35 = (Button) view.findViewById(R.id.textView35);
		ty36 = (Button) view.findViewById(R.id.textView36);
		ty37 = (Button) view.findViewById(R.id.textView37);
		ty38 = (Button) view.findViewById(R.id.textView38);
		ty39 = (Button) view.findViewById(R.id.textView39);
		ty40 = (Button) view.findViewById(R.id.textView40);
		ty41 = (Button) view.findViewById(R.id.textView41);
		ty42 = (Button) view.findViewById(R.id.textView42);

	}

	private void setMyClass() {
		String week[] = userDB.loadNowWeek();
		Calendar calendar = Calendar.getInstance();
		if (!week[0].equals("0")) {
			long now = MyTime.getTimesWeekmorning();
			long last = Long.valueOf(week[1]);

			String lastWeek = week[0];
			Pattern p = Pattern.compile("(\\d+)");
			Matcher m = p.matcher(lastWeek);
			String find = "0";
			while (m.find()) {
				find = m.group(1).toString();
			}

			Date date = new Date(last);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			// 上次星期几
			int lastweek = c.get(Calendar.DAY_OF_WEEK) + 1;

			// 上次课表周数
			int myWeek = Integer.valueOf(find);

			// 上次到今天的天数
			int day = (int) ((now - last) / (1000 * 60 * 60 * 24));

			int passWeek = (day + lastweek) / 7;

			int param = myWeek + passWeek;
			Log.i("Fragment_MyClass", passWeek + "," + myWeek);
			fragment_MyClass_Presenter.setClassButtonText(button, "" + param);
			time.setText("第" + param + "周");
		} else {
			fragment_MyClass_Presenter.setClassButtonText(button, "" + 1);
			time.setText("第" + 1 + "周");
		}
	}

	class TimeListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			fragment_MyClass_Presenter.timeButoonListener(time);
		}
	}

	// 填充button list
	private void FillButtonList() {
		button.add(ty1);
		button.add(ty2);
		button.add(ty3);
		button.add(ty4);
		button.add(ty5);
		button.add(ty6);
		button.add(ty7);
		button.add(ty8);
		button.add(ty9);
		button.add(ty10);
		button.add(ty11);
		button.add(ty12);
		button.add(ty13);
		button.add(ty14);
		button.add(ty15);
		button.add(ty16);
		button.add(ty17);
		button.add(ty18);
		button.add(ty19);
		button.add(ty20);
		button.add(ty21);
		button.add(ty22);
		button.add(ty23);
		button.add(ty24);
		button.add(ty25);
		button.add(ty26);
		button.add(ty27);
		button.add(ty28);
		button.add(ty29);
		button.add(ty30);
		button.add(ty31);
		button.add(ty32);
		button.add(ty33);
		button.add(ty34);
		button.add(ty35);
		button.add(ty36);
		button.add(ty37);
		button.add(ty38);
		button.add(ty39);
		button.add(ty40);
		button.add(ty41);
		button.add(ty42);
	}

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				String text = msg.getData().getString("text");
				String nub = msg.getData().getString("nub");
				button.get(Integer.valueOf(nub)).setText(text);
				break;

			case 2:
				Bundle bundle = msg.getData();
				String nuber = bundle.getString("nub");
				fragment_MyClass_Presenter.setClassButtonText(button, nuber);
				break;
			case 3:
				for (int i = 0; i < button.size(); i++) {
					((Button) button.get(i)).setText("");
				}
				break;
			default:
				break;
			}
		}

	}

	private void setButtonTag() {
		for (int i = 0; i < button.size(); i++) {
			Button b = (Button) button.get(i);
			b.setTag(i);
			b.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		Integer tag = (Integer) v.getTag();
		Log.i("Fragment_MyClass", tag + "");
		fragment_MyClass_Presenter.touchButton(tag + 1);
	}

}
