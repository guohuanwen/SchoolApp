package com.example.learn.ui;

import java.io.InputStream;
import java.util.List;

import com.example.learn.R;
import com.example.learn.model.UserDB;
import com.example.learn.net.Analysis;
import com.example.learn.presenter.Fragment_Login_Presenter;
import com.example.learn.tool.MyApplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Login extends Fragment {
	private View view;
	public EditText studentID;
	public EditText password;
	public EditText checkCode;
	public ImageView checkCodePhoto;
	public Button enter;
	public Button refresh;
	public CheckBox scoreCheckBox;
	public CheckBox timetableCheckBox;
	// public CheckBox bxCheckBox;
	public CheckBox gradeTestCheckBox;
	public TextView classtime;
	private UserDB userDB;
	private Context context;
	private Fragment_Login_Presenter fragment_Login_Presenter;
	private MyHandler myHandler;
	private Button schoolTime;
	private Handler acitvityHandler;

	public Fragment_Login(Handler handler) {
		acitvityHandler = handler;
	}

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_login, null, false);

		initialize();
		// 进入刷新验证码
		// fragment_Login_Presenter.getCheckCodePhoto(myHandler);
		return view;
	}

	private void initialize() {
		

		context = getActivity();

		schoolTime = (Button) view.findViewById(R.id.schoolTime);
		
		studentID = (EditText) view.findViewById(R.id.studentID);
		password = (EditText) view.findViewById(R.id.password);
		checkCode = (EditText) view.findViewById(R.id.checkCode);
		checkCodePhoto = (ImageView) view.findViewById(R.id.checkCodePhoto);
		enter = (Button) view.findViewById(R.id.enter);
		refresh = (Button) view.findViewById(R.id.refresh);
		classtime = (TextView) view.findViewById(R.id.classtime);
		scoreCheckBox = (CheckBox) view.findViewById(R.id.scoreCheckBox);
		timetableCheckBox = (CheckBox) view
				.findViewById(R.id.timetableCheckBox);
		// bxCheckBox = (CheckBox) view.findViewById(R.id.bxCheckBox);
		gradeTestCheckBox = (CheckBox) view
				.findViewById(R.id.gradeTestCheckBox);

		userDB = UserDB.getInstance(context);

		enter.setOnClickListener(new EnterListener());
		refresh.setOnClickListener(new RefreshListener());
		schoolTime.setOnClickListener(new SchoolListener());
		myHandler = new MyHandler();
		fragment_Login_Presenter = new Fragment_Login_Presenter(context,myHandler);
		fillEdit();
		// 暂时不用
		fragment_Login_Presenter.getCheckCodePhoto(myHandler);
	}

	private void fillEdit() {
		new Thread(new Runnable() {
			public void run() {
				List list = userDB.loadUser();
				if (list.size() == 2) {
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("id", (String) list.get(0));
					bundle.putString("password", (String) list.get(1));
					msg.setData(bundle);
					msg.what = 2;
					myHandler.sendMessage(msg);
				}
			}
		}).start();
	}

	public void setStudentNub(String studentID, String password) {
		this.studentID.setText(studentID);
		this.password.setText(password);
	}

	public void setCheckCodePhoto() {
		Log.i("Fragment_Login", "setCheckCodePhoto");
		Bitmap bitmap = fragment_Login_Presenter.getBitmap();
		if (bitmap != null) {
			Log.i("Fragment_Login", "bitmap==null");
			this.checkCodePhoto.setImageBitmap(bitmap);
		}
	}

	private void saveIDPassword() {
		String id = this.studentID.getText().toString();
		String password = this.password.getText().toString();
		userDB.saveUserNub(id, password);
	}

	private void sendMessage(int param) {
		Message message = new Message();
		message.what = param;
		myHandler.sendMessage(message);
	}

	class EnterListener implements OnClickListener {

		public void onClick(View v) {
			Log.i("Fragment_Login", "EnterListener");
			if (TextUtils.isEmpty(studentID.getText())) {
				// 学号为空
				Log.i("Fragment_Login", "学号为空");
				sendMessage(10);
			} else if (TextUtils.isEmpty(password.getText())) {
				// 密码为空
				sendMessage(11);
			} else if (TextUtils.isEmpty(checkCode.getText())) {
				// 验证码为空
				sendMessage(12);
			} else {
				int a[] = { 0, 0, 0 };
				if (scoreCheckBox.isChecked()) {
					a[0] = 1;
				}
				if (timetableCheckBox.isChecked()) {
					a[1] = 1;
				}
				// if (bxCheckBox.isChecked()) {
				// a[2] = 1;
				// }
				if (gradeTestCheckBox.isChecked()) {
					a[2] = 1;
				}
				fragment_Login_Presenter.logIn(//
						studentID.getText().toString(),//
						password.getText().toString(),//
						checkCode.getText().toString(),//
						schoolTime.getText().toString(),
						myHandler,//
						a);
				
			}
		}

	}

	class RefreshListener implements OnClickListener {

		public void onClick(View v) {
			fragment_Login_Presenter.getCheckCodePhoto(myHandler);
		}

	}

	class SchoolListener implements OnClickListener {
		public void onClick(View v) {
			fragment_Login_Presenter.schoolTimeListener(schoolTime);
		}
	}

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				Log.i("Fragment_Login", "handleMessage+1");
				// 更新验证码图片
				setCheckCodePhoto();
				break;
			case 2:
				Log.i("Fragment_Login", "MyHandler" + "2"
						+ msg.getData().getString("id")
						+ msg.getData().getString("password"));
				studentID.setText(msg.getData().getString("id"));
				password.setText(msg.getData().getString("password"));
				break;
			case 9:
				// 输入框为空
				Toast.makeText(context, "网络或输入内容错误", Toast.LENGTH_SHORT).show();
				fragment_Login_Presenter.getCheckCodePhoto(myHandler);
				break;
			case 10:
				// 输入框为空
				Toast.makeText(context, "学号为空", Toast.LENGTH_SHORT).show();
				fragment_Login_Presenter.getCheckCodePhoto(myHandler);
				break;
			case 11:
				// 输入框为空
				Toast.makeText(context, "密码为空", Toast.LENGTH_SHORT).show();
				fragment_Login_Presenter.getCheckCodePhoto(myHandler);
				break;
			case 12:
				// 输入框为空
				Toast.makeText(context, "验证码为空", Toast.LENGTH_SHORT).show();
				fragment_Login_Presenter.getCheckCodePhoto(myHandler);
				break;
			case 13:
				// 登录成功
				Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
				saveIDPassword();
				break;
			case 14:
				// 获取数据
				Toast.makeText(context, "正在获取数据，稍等片刻", Toast.LENGTH_SHORT)
						.show();
				break;
			case 15:
				// 获取数据
				Toast.makeText(context, "获取考试成绩成功", Toast.LENGTH_SHORT).show();
				break;
			case 16:
				// 获取数据
				Toast.makeText(context, "获取课表成功", Toast.LENGTH_SHORT).show();
				break;
			case 17:
				// 获取数据
				Message message = new Message();
				message.what = 5;
				acitvityHandler.sendMessage(message);
				break;
			default:
				break;
			}
		}

	}

}
