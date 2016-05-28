package com.example.learn.ui;

import java.util.List;

import com.example.learn.MainActivity;
import com.example.learn.R;
import com.example.learn.model.DataDB;
import com.example.learn.net.NetPresenter;
import com.example.learn.tool.ListViewFitScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

public class Fragment_News extends Fragment{

	private View view;
	private ListViewFitScrollView listView;
	private DataDB dataDB;
	private Myhandler myhandler;
	private ScrollView mScrollView; 
	private List list;
	private Context context;
	private PullToRefreshScrollView mPullRefreshScrollView;
	private Handler activityHandler;
	public Fragment_News(Handler handler) {
		activityHandler=handler;
	}
	
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_news,null,false);
		init();
		setPull();
		initList();
		return view;
	}
	
	private void init() {
		context=getActivity();
		mPullRefreshScrollView = (PullToRefreshScrollView) view
				.findViewById(R.id.pull_refresh_scrollview);
		
		myhandler=new Myhandler();
		dataDB=DataDB.getInstance(getActivity());
		listView=(ListViewFitScrollView)view.findViewById(R.id.listView);
		
		
		Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_SHORT).show();
	}
	
	
	private void setListView(ListView listView,List list){
		ListAdapter adapter=new ArrayAdapter(getActivity(), R.layout.item_onetext, R.id.textView, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ListListener());
	}
	
	
	private void initList(){
		new Thread(new Runnable() {
			public void run() {
				list=dataDB.loadNewsTitle();
				sendMessage(2);
			}
		}).start();
	}
	
	private void setPull() {
		// TODO Auto-generated method stub
		
		mPullRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
		mPullRefreshScrollView
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
					public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
						new GetDataTask().execute();
					}
				});

		mScrollView = mPullRefreshScrollView.getRefreshableView();
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
				NetPresenter netPresenter=NetPresenter.getInstence();
				int i=netPresenter.getNews();
			
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// Do some stuff here
			// Call onRefreshComplete when the list has been refreshed.
			
			mPullRefreshScrollView.onRefreshComplete();
			sendMessage(1);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
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
				Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				list=dataDB.loadNewsTitle();
//				Log.i("Fragment_News","handleMessage:"+1+"List:"+list.toString());
				setListView(listView, list);
				break;
			case 2:
//				Log.i("Fragment_News","handleMessage:"+1+"List:"+list.toString());
				setListView(listView, list);
				break;
			case 3:
				
				break;
				
			default:
				break;
			}
		}
		
	}
	
	 private String newsText;
	 class ListListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Message message=new Message();
			message.what=4;
			Bundle bundle=new Bundle();
			bundle.putInt("param", position);
			message.setData(bundle);
			activityHandler.sendMessage(message);
			
		}
		 
	 }
	
}
