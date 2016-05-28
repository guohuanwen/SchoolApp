package com.example.learn.ui;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.learn.R;
import com.example.learn.model.DataDB;
import com.example.learn.model.MyScore;

import android.R.fraction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Fragment_Score_StudyMoney extends Fragment {
	private View view;
	private ListView listView;
	private DataDB dataDB;
	private TextView finallyScore;
//	private EditText algorithm;
	private LayoutInflater listContainer;
	private List checkList;
	private List allCheckData;
	private MyAdapter myAdapter;
	private List check = new ArrayList<Integer>();
	private List score = new ArrayList<String>();
	private List studyScore = new ArrayList<String>();
	private List nowData=new ArrayList<MyScore>();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.fragment_score_studymoney, null, false);
		init();
		setScoreText();
		return view;
	}

	private void init() {
		allCheckData = new ArrayList<Integer>();
//		algorithm = (EditText) view.findViewById(R.id.studyMoneyEditText);
		finallyScore = (TextView) view.findViewById(R.id.studyMoneyTextView);
		listView = (ListView) view
				.findViewById(R.id.fragment_score_studentscore_listview);
		dataDB = DataDB.getInstance(getActivity());
		myAdapter = new MyAdapter(getActivity(), R.layout.list_item_studyscore,
				new int[] { R.id.list_textView1, R.id.list_checkbox },
				setDataToList());
		listView.setAdapter(myAdapter);
		listView.setOnItemClickListener(new ListListener());
	}

	private void setScoreText() {
		finallyScore.setText("综合得分：" + calculateScore());
	}

	private float calculateScore() {
		float re = 0.0f;
		float nub = 0;
		for (Object i : check) {
			int a = (Integer) i;
			if (!((String)studyScore.get(a)).equals("")) {
				float study = Float.valueOf((String) studyScore.get(a));
				float testScore = Float.valueOf((String) score.get(a));
				re = re + study * testScore;
				nub = nub + study;
			}
		}
		return re / nub;
	}

	private List setDataToList() {
		List list = new ArrayList<String>();
		List dateList = dataDB.loadMyScore();
		String nowTime = "";
		for (int i = 0; i < dateList.size(); i++) {
			MyScore myScore = (MyScore) dateList.get(i);
			// Log.i("Fragment_Score_StudyMoney", myScore.getName());
			if (nowTime.equals(myScore.getTime())) {
				list.add(myScore.getName());
				score.add(myScore.getScore());
				studyScore.add(myScore.getStudyScore());
//				nowData.add(myScore);
			} else {
				allCheckData.add(i+allCheckData.size());
				Log.i("Fragment_Score_StudyMoney", allCheckData.toString());
				list.add(myScore.getTime());
				nowTime = myScore.getTime();
				score.add("");
				studyScore.add("");
				i=i-1;
//				nowData.add(myScore);
			}
		}
		// Log.i("Fragment_Score_StudyMoney", list.toString());
		return list;
	}

	class ListListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			int data = 0;
			for (int i = 0; i < allCheckData.size(); i++) {
				// 如果点击的是时间item，选中该时间下的所有item
				if (position == (Integer) allCheckData.get(i)) {
					// 如果点击的是最后一个时间item
					if (i == (allCheckData.size() - 1)) {
						List a = checkNub((Integer) allCheckData.get(i),
								myAdapter.getCount());
						if (check.size() == 0) {
							check = inListAdd(a, check);
						} else {
							int size = check.size();
							check = inListDel(a, check);
							if (check.size() == size) {
								check = inListAdd(a, check);
							}
						}
					}
					// 不是最后一个时间按钮
					else {
						List a = checkNub((Integer) allCheckData.get(i),
								(Integer) allCheckData.get(i + 1));
						if (check.size() == 0) {
							check = inListAdd(a, check);
						} else {
							int size = check.size();
							check = inListDel(a, check);
							if (check.size() == size) {
								check = inListAdd(a, check);
							}
						}
					}
					data = 1;
					break;
				}

			}
			// 0:没有选中上面有时间的item
			if (data == 0) {
				List l = new ArrayList<Integer>();
				l.add(position);
				int size = check.size();
				check = inListDel(l, check);
				if (check.size() == size) {
					check = inListAdd(l, check);
				}
			}
			myAdapter.setCheckBoxListTrue(check);
			Log.i("Fragment_Score_StudyMoney",
					"ListListener" + check.toString());
			myAdapter.notifyDataSetChanged();
			setScoreText();
		}

		private List inListAdd(List add, List list) {
			// 分别取出需要添加的数与原list中的数比对
			for (int j = 0; j < add.size(); j++) {
				int param = (Integer) add.get(j);
				int q = 0;
				for (int i = 0; i < list.size(); i++) {
					if (param == (Integer) list.get(i)) {
						list.remove(i);
						q = 1;
						break;
					}
				}
				if (q == 0) {
					list.add(param);
				}
			}
			return list;
		}

		private List inListDel(List add, List list) {
			// 分别取出需要添加的数与原list中的数比对
			for (int j = 0; j < add.size(); j++) {
				int param = (Integer) add.get(j);
				int q = 0;
				for (int i = 0; i < list.size(); i++) {
					// 有相同的数就抹掉
					if (param == (Integer) list.get(i)) {
						list.remove(i);
						q = 1;
						break;
					}
				}
			}

			return list;
		}

		public List checkNub(int small, int large) {
			List list = new ArrayList<Integer>();

			if (large - small > 0) {
				for (int i = small; i < large; i++) {
					list.add(i);
				}
			}
			Log.i("Fragment_Score_StudyMoney", "checkNub：" + small + ","
					+ large + "," + list.size());
			return list;
		}

	}

	class MyAdapter extends BaseAdapter {

		private LayoutInflater listContainer;
		private List nameList;
		private int resource;
		private int[] sonRes;
		private List checkList;

		// 定义内部类，需要在listItem中显示的内容
		class MyListItem {
			TextView name;
			CheckBox check;

		}

		// 构造函数
		public MyAdapter(Context context, int resource, int[] resParam,
				List list) {
			this.listContainer = LayoutInflater.from(context);
			this.nameList = list;
			this.resource = resource;
			this.sonRes = resParam;
			checkList = new ArrayList<Integer>();
			Log.i("Fragment_Score_StudyMoney",
					"MyAdapter" + nameList.toString());
		}

		// 此方法返回item中显示的内容
		public View getView(int position, View convertView, ViewGroup parent) {
			MyListItem myListItem;

			if (convertView != null) {
				// convertView
				myListItem = (MyListItem) convertView.getTag();
			} else {
				convertView = listContainer.inflate(resource, null);
				myListItem = new MyListItem();
				myListItem.check = (CheckBox) convertView
						.findViewById(sonRes[1]);
				myListItem.name = (TextView) convertView
						.findViewById(sonRes[0]);
				convertView.setTag(myListItem);
			}
			// Log.i("Fragment_Score_StudyMoney", (String)
			// nameList.get(position));
			myListItem.name.setText((String) nameList.get(position));
			Log.i("Fragment_Score_StudyMoney",
					"checkList" + checkList.toString());
			myListItem.check.setChecked(false);
			for (int i = 0; i < checkList.size(); i++) {
				if (position == (Integer) checkList.get(i)) {
					myListItem.check.setChecked(true);
				}
			}
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return nameList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public void notifyDataSetChanged() {
			// TODO Auto-generated method stub
			super.notifyDataSetChanged();
		}

		public void setCheckBoxListTrue(List list) {
			checkList = list;
		}

	}

}
