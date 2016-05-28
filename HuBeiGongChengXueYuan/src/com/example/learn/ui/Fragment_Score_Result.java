package com.example.learn.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.learn.R;
import com.example.learn.model.DataDB;
import com.example.learn.model.MyScore;
import com.example.learn.tool.MyDialog;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout.LayoutParams;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fragment_Score_Result extends Fragment {
	private View view;
	private ListView listView;
	private DataDB dataDB;
	private List nowDate;
	private List dateList;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_score_result, null, false);
		init();
		return view;

	}

	private void init() {
		nowDate=new ArrayList<MyScore>();
		dataDB = DataDB.getInstance(getActivity());
		listView = (ListView) view
				.findViewById(R.id.fragment_score_result_listview);

		ListAdapter adapter = new SimpleAdapter(getActivity(), setDataToList(),
				R.layout.list_item_score, new String[] { "name", "score" },
				new int[] { R.id.list_textView1, R.id.lsit_textView2 });
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new ListListener());
	}

	private List setDataToList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		dateList = dataDB.loadMyScore();
		Map<String, Object> map;
		String time = "";
		for (int i = 0; i < dateList.size(); i++) {

			map = new HashMap<String, Object>();
			MyScore myScore = (MyScore) dateList.get(i);
			// Log.i("Fragment_Score_Result", myScore.getName());
			if (time.equals(myScore.getTime())) {

				map.put("name", myScore.getName() + "(" + myScore.getType()
						+ ")");
				map.put("score", myScore.getScore());
				list.add(map);
				nowDate.add(myScore);
			} else {
				map.put("name", myScore.getTime());
				map.put("score", "");
				list.add(map);
				time = myScore.getTime();
				i=i-1;
				nowDate.add(myScore);
			}
		}
		Log.i("Fragment_Score_Result", list.toString());
		return list;
	}

	class ListListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			MyScore score = (MyScore) nowDate.get(position);
			ListView lv = new ListView(getActivity());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			Map<String, Object> map3 = new HashMap<String, Object>();
			Map<String, Object> map4 = new HashMap<String, Object>();
			Map<String, Object> map5 = new HashMap<String, Object>();
			map1.put("name", "课程");
			map1.put("value", score.getName());
			map2.put("name", "成绩");
			map2.put("value", score.getScore());
			map3.put("name", "学分");
			map3.put("value", score.getStudyScore());
			map4.put("name", "性质");
			map4.put("value", score.getType());
			map5.put("name", "时间");
			map5.put("value", score.getTime());
			list.add(map5);
			list.add(map1);
			list.add(map2);
			list.add(map3);
			list.add(map4);
			ListAdapter adapter = new SimpleAdapter(getActivity(), list,
					R.layout.list_item_score_detial, new String[] { "name",
							"value" }, new int[] { R.id.list_textView1,
							R.id.lsit_textView2 });
			lv.setAdapter(adapter);
			// LayoutParams layoutParams=new
			// LayoutParams(LayoutParams.MATCH_PARENT,
			// android.support.v4.view.ViewPager.LayoutParams.MATCH_PARENT);
			// lv.setLayoutParams(layoutParams);
			// lv.setBackgroundColor(R.color.bai);
			final MyDialog myDialog = new MyDialog();
			myDialog.dialogShow(4, getActivity(), lv, new OnClickListener() {

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
