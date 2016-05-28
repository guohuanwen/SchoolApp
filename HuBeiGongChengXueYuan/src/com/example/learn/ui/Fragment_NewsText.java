package com.example.learn.ui;

import java.util.List;

import com.example.learn.R;
import com.example.learn.model.DataDB;
import com.example.learn.net.Analysis;
import com.example.learn.net.NetPresenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_NewsText extends Fragment{
	private TextView textView;
	private View view;
	private int urlNub;
	private DataDB dataDB;
	private String newsText;
	private Myhandler myhandler;
	
	public Fragment_NewsText(int param){
		urlNub=param;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_newstext,null,false);
		init();
		getNewsText();
		return view;
	}
	
	private void init() {
		myhandler=new Myhandler();
		dataDB=DataDB.getInstance(getActivity());
		textView=(TextView)view.findViewById(R.id.textView);
		Toast.makeText(getActivity(), "正在加载", Toast.LENGTH_SHORT).show();
	}
	private void getNewsText(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				List list=dataDB.loadNewsUrl();
				String param=(String)list.get(urlNub);
				NetPresenter netPresenter=NetPresenter.getInstence();
				newsText=netPresenter.getNewsText(param);
				sendMessage(0);
			}
		}).start();
	}
	
	
	private void sendMessage(int param){
		Message message=new Message();
		message.what=param;
		myhandler.sendMessage(message);
	}
	
	 class Myhandler extends Handler{
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					Analysis analysis=new Analysis();
					String text=analysis.analysisNewText(newsText);
					textView.setText(text);
					break;
				
				default:
					break;
				}
			}
			
		}
	 
	 
	 

}
