package com.example.learn.ui;

import com.example.learn.R;
import com.example.learn.tool.MyApplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Score extends Fragment{
	private View view;
	private static final String[] CONTENT = new String[] { "成绩", "奖学金评分", "等级考试"};
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_score, null, false);
		init();
		return view;
	
	}
	
	private void init(){
		FragmentPagerAdapter adapter = new GoogleMusicAdapter(getChildFragmentManager());

        ViewPager pager = (ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(adapter);

//        TabPageIndicator indicator = (TabPageIndicator)view.findViewById(R.id.indicator);
//        PagerTitleStrip pagerTitleStrip=(PagerTitleStrip)view.findViewById(R.id.title);
//        pagerTitleStrip.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
        PagerTabStrip pagerTabStrip=(PagerTabStrip)view.findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_PX, MyApplication.getScreenWidthHeight()[0]/25);
        pagerTabStrip.setAlpha(0.8f);
        pagerTabStrip.setClickable(true);
        //        indicator.setViewPager(pager);
	}
	
	
	 class GoogleMusicAdapter extends FragmentPagerAdapter {
	        public GoogleMusicAdapter(FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public Fragment getItem(int position) {
	        	Fragment fragment;
	        	Log.i("Fragment_Score", position+"");
	        	switch (position) {
	        	
				case 0:
					fragment=new Fragment_Score_Result();
					break;
				case 2:
					fragment=new Fragment_Score_Grade();
					break;
				case 1:
//					fragment=new TestFragment();
					fragment=new Fragment_Score_StudyMoney();
					break;
				default:
					fragment=new TestFragment();
					break;
				}
	            return fragment;
	        }

	        @Override
	        public CharSequence getPageTitle(int position) {
	            return CONTENT[position % CONTENT.length].toUpperCase();
	        }

	        @Override
	        public int getCount() {
	          return CONTENT.length;
	        }
	    }
	

}
