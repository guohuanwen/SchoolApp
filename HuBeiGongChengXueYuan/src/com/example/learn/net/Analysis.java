package com.example.learn.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import android.R.integer;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.learn.model.AllClass;
import com.example.learn.model.DataDB;
import com.example.learn.model.GradeTest;
import com.example.learn.model.MyClass;
import com.example.learn.model.MyScore;
import com.example.learn.tool.MyApplication;

public class Analysis {
	private Context context;
	private DataDB dataDB;

	public Analysis() {
		context = MyApplication.getContext();
		dataDB = DataDB.getInstance(context);
	}

	

	// html解析，只提取科目与成绩
	public void analysisScore(String source) {

		String test = testString("score.txt");

		StringBuffer sff = new StringBuffer();
		String html = source;

		org.jsoup.nodes.Document doc = Jsoup.parse(source);

		Elements table = doc.select("tr[class=smartTr]");
		Elements text = table.select("td[height=23]");
		int i = 1;
		MyScore myScore = new MyScore();
		List list = new ArrayList<MyScore>();
		for (org.jsoup.nodes.Element link : text) {
			Log.i("Analysis", link.text());

			if (i % 13 == 4) {
				myScore.setTime(link.text());
			}
			if (i % 13 == 5) {
				myScore.setName(link.text());
			}

			if (i % 13 == 6) {
				myScore.setScore(link.text());
			}
			if (i % 13 == 9) {
				myScore.setType(link.text());
			}
			if (i % 13 == 10) {
				myScore.setStudyTime(link.text());
			}
			if (i % 13 == 11) {
				myScore.setStudyScore(link.text());
				list.add(myScore);
				myScore = new MyScore();
			}

			i++;

		}
		dataDB.saveMyScore(list);

	}

	// 新闻网页解析新闻标题与地址
	public void analysisNews(String html) {
		List list1 = new ArrayList<String>();
		List list2 = new ArrayList<String>();
		if (null == html) {
			return;
		}
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements class1 = doc.select("div[id=HeadNewsTitle]>a");
		list1.add(class1.attr("href"));
		list2.add(class1.attr("title"));
		Elements class2 = doc.select("div[class=list]>ul>li>a[class=gray]");
		for (org.jsoup.nodes.Element link : class2) {
			list1.add(link.attr("href"));
			list2.add(link.attr("title"));
		}
		dataDB.saveNews(list2, list1);

	}

	// 解析新闻内容
	public String analysisNewText(String source) {
		org.jsoup.nodes.Document doc = Jsoup.parse(source);
		Elements links_class = doc.select("div[class=text]");
		links_class.toString();
//		links_class=links_class.select("p[style=text-align:left]");
		String text = links_class.text();
		Log.i("Analysis", text);
		return text;
	}

	// 解析大学全部课程
	public void analyseAllClass(String source) {

		String test = testString("allClass.txt");

		List list = new ArrayList<AllClass>();

		org.jsoup.nodes.Document doc = Jsoup.parse(test);

		Elements table = doc.select("table[border=1]");
		Elements links_class = table.select("td[height=23]");
		int a = 1;
		AllClass allClass = new AllClass();
		for (org.jsoup.nodes.Element link : links_class) {
			String text = link.text();
			Log.i("Analysis", a+"___"+text);
			if (a % 12 == 4) {
				allClass.setName(text);
			}
			if (a % 12 == 5) {
				allClass.setStudyTime(text);
			}
			if (a % 12 == 6) {
				allClass.setStudyScore(text);
			}
			if (a % 12 == 8) {
				allClass.setTerm(text);
			}
			if (a % 12 == 11) {
				allClass.setTestType(text);
				
				list.add(allClass);
				allClass = new AllClass();
			}

			a++;
		}
		dataDB.saveAllClass(list);

	}

	// 大学等级证书考试解析
	public void analysisGradeTest(String source) {
		String testText=testString("grade.txt");
		List list = new ArrayList<GradeTest>();
		ArrayList<String> list1 = new ArrayList<String>();
		if (null == source) {
			return;
		}
		String html = source;
		org.jsoup.nodes.Document doc = Jsoup.parse(source);

		Elements links_class = doc.select("td[height=23]");
		for (org.jsoup.nodes.Element link : links_class) {
			String text = link.text();
			list1.add(text);
		}
		for (int i = 0; i <= list1.size(); i++) {
			if ((i + 1) % 14 == 6) {
				GradeTest gradeTest = new GradeTest();
				gradeTest.setName(list1.get(i));
				gradeTest.setEnd(list1.get(i + 1));
				gradeTest.setScore(list1.get(i + 4));
				list.add(gradeTest);
			}
		}
		dataDB.saveGradeTest(list);

	}

	ArrayList<MyClass> classNum = new ArrayList<MyClass>();

	public void analysisMyClass(String source) {
		String test = testString("myClass.txt");
		String html = source;

		org.jsoup.nodes.Document doc = Jsoup.parse(source);

		for (int one = 1; one <= 6; one++) {
			for (int two = 1; two <= 7; two++) {
				Elements class1 = doc.select("div[id=" + one + "-" + two
						+ "-2]");
				Elements class2 = doc.select("div[id=" + one + "-" + two
						+ "-1]");
				ArrayList<String> Class = new ArrayList<String>();
//				Log.i("Analysis", class2.text().toString());
//				System.out.println("1----"+class2.toString());
//				System.out.println("2----"+class1.toString());
				
				String end="";
				String str = class1.toString();
				end=str.replace("<br />", "$");
				end=end.replace("<nobr>", "$");
				end=end.replace("</nobr>", "$");
				end=end.replace("&nbsp;", "$");
				end=end.replace(" ", "");
				end=end.replace("\n", "");
				end=end.replace("$$$$", "$");
				end=end.replace("$$$", "$");
				end=end.replace("$$", "$");
				end=end.replace("$", "$&");
				System.out.println("match==="+end);
				
				 
				Pattern pattern1 = Pattern.compile("\\&(.*?)\\$");
				Matcher matcher1 = pattern1.matcher(end);
				while (matcher1.find()) {
//					System.out.println("3----"+matcher1.group(1));
					Class.add(matcher1.group(1));
				}
				AnalysisTwo(Class);
			}
		}
		dataDB.saveMyClass(classNum);
	}

	@SuppressWarnings("unused")
	private void AnalysisTwo(ArrayList<String> Class) {
		int n = Class.size();
		if(n!=0){
		Pattern p=Pattern.compile("(.*?)周\\[(.*?)");
		//该节点一共几次课
		//周数所在list的位子
		ArrayList<String> place=new ArrayList<String>(); 
		for(int i=0;i<Class.size();i++){
			Matcher m=p.matcher((String)Class.get(i));
			if(m.find()){
				place.add(i+"");
			}
		}
		
		ArrayList<String> ClassName = new ArrayList<String>();
		ArrayList<String> Teacher = new ArrayList<String>();
		ArrayList<String> Place = new ArrayList<String>();
		ArrayList<String> Time = new ArrayList<String>();
		int ke=place.size();
		
		int pp=Class.size()/ke;
		Log.i("AnaLysis",Class.toString());
		Log.i("AnaLysis", place.toString()+",size="+ke+",pp="+pp);
		for(int j=0;j<ke;j++){
			int we=Integer.valueOf(place.get(j));
			ClassName.add(Class.get(pp*(ke-1)));
			Teacher.add(Class.get(we-1));
			Time.add(Class.get(we));
			Place.add(Class.get(we+1));
		}
		
		
		

		MyClass myClass = new MyClass();
		
			int yy = classNum.size() + 1;

			myClass.setClassName(ClassName);
			myClass.setClassNum(yy + "");
			myClass.setClassPlace(Place);
			myClass.setTeacherName(Teacher);
			myClass.setClassWeek(Time);
			myClass.setMatchWeek(analysisTime(Time));
			classNum.add(myClass);

		} else {
			// System.out.println(ClassName+"---"+Teacher+"---"+Place+"---"+Time);
			classNum.add(null);
		}
}
	


	private ArrayList<String> analysisTime(ArrayList<String> Ti) {
		ArrayList<String> myList = new ArrayList<String>();
		for (int nub = 0; nub < Ti.size(); nub++) {
			String myTime = Ti.get(nub);
			if ("".equals(myTime) == false) {
				Log.i("Analysis", myTime);
				// 匹配单周
				int danzhou = 0;
				int shuangzhou = 0;
				Pattern dan = Pattern.compile("(.*?)单周");
				Matcher Mdan = dan.matcher(myTime);
				while (Mdan.find()) {
					myTime = myTime.replaceAll("单", "");
					danzhou = 1;
				}
				// 匹配双周
				Pattern shuang = Pattern.compile("(.*?)双周");
				Matcher Mshuang = shuang.matcher(myTime);
				while (Mshuang.find()) {
					myTime = myTime.replaceAll("双", "");
					shuangzhou = 1;
				}
				// System.out.println("MyTime---"+MyTime);

				// 解析周数，此时没有 单 双 字,开始解析周数
				Pattern pattern1 = Pattern.compile("(.*?)周");
				Matcher matcher1 = pattern1.matcher(myTime);
				String add = "";
				while (matcher1.find()) {
					String Time = matcher1.group(1);
					// 形如1，2，3，4周 1-2，4-5，7周
					Pattern pattern5 = Pattern.compile("(.*?)\\,");
					Matcher matcher5 = pattern5.matcher(Time);

					if (matcher5.find()) {

						Log.i("Analysis", "," + Time);

						// 形如1，2，3，4周
						Pattern pattern2 = Pattern.compile("\\,");
						Matcher matcher2 = pattern5.matcher(Time + ",");
						while (matcher2.find()) {
							String a = match2(matcher2.group(1), danzhou,
									shuangzhou);
							if ("".equals(a) == false) {
								add = add + a + ",";
							}

						}

						myList.add(add);
						// 没有，形如 1 1-2
					} else {
						String ar = match2(Time, danzhou, shuangzhou);
						Log.i("Analysis", ar);
						myList.add(ar);
					}

				}

			}
		}
		return myList;
	}
	
	public String matchWeek(String param){
		String re="";
		String myTime = param;
		if ("".equals(myTime) == false) {
			Log.i("Analysis", myTime);
			// 匹配单周
			int danzhou = 0;
			int shuangzhou = 0;
			Pattern dan = Pattern.compile("(.*?)单周");
			Matcher Mdan = dan.matcher(myTime);
			while (Mdan.find()) {
				myTime = myTime.replaceAll("单", "");
				danzhou = 1;
			}
			// 匹配双周
			Pattern shuang = Pattern.compile("(.*?)双周");
			Matcher Mshuang = shuang.matcher(myTime);
			while (Mshuang.find()) {
				myTime = myTime.replaceAll("双", "");
				shuangzhou = 1;
			}
			// System.out.println("MyTime---"+MyTime);

			// 解析周数，此时没有 单 双 字,开始解析周数
			Pattern pattern1 = Pattern.compile("(.*?)周");
			Matcher matcher1 = pattern1.matcher(myTime);
			String add = "";
			while (matcher1.find()) {
				String Time = matcher1.group(1);
				// 形如1，2，3，4周 1-2，4-5，7周
				Pattern pattern5 = Pattern.compile("(.*?)\\,");
				Matcher matcher5 = pattern5.matcher(Time);
				if (matcher5.find()) {
					Log.i("Analysis", "," + Time);

					// 形如1，2，3，4周
					Pattern pattern2 = Pattern.compile("\\,");
					Matcher matcher2 = pattern5.matcher(Time + ",");
					while (matcher2.find()) {
						String a = match2(matcher2.group(1), danzhou,
								shuangzhou);
						if ("".equals(a) == false) {
							add = add + a + ",";
						}
					}
					re=add;
					// 没有，形如 1 1-2
				} else {
					String ar = match2(Time, danzhou, shuangzhou);
					Log.i("Analysis", ar);
					re=ar;
				}

			}

		}
		return re;
	}

	/**
	 * 匹配 -
	 * 
	 * @param Time
	 * @param danzhou
	 * @param shuangzhou
	 * @return
	 */
	private String match1(String Time, int danzhou, int shuangzhou) {
		String add = "";
		String firstNub = "", endNub = "";

		Pattern pattern3 = Pattern.compile("(.*?)\\-");
		Matcher matcher3 = pattern3.matcher(Time);
		while (matcher3.find()) {
			Log.i("Analysis", "-" + Time);
			firstNub = matcher3.group(1);
		}
		Pattern pattern4 = Pattern.compile("\\-(.*)");
		Matcher matcher4 = pattern4.matcher(Time);
		while (matcher4.find()) {
			// System.out.println("3---" + matcher4.group(1));
			endNub = matcher4.group(1);
		}
		if (!"".equals(firstNub) && !"".equals(endNub)) {
			int first = Integer.valueOf(firstNub);
			int end = Integer.valueOf(endNub);

			if (danzhou == 1) {

				for (int i = first; i <= end; i++) {
					if (i % 2 == 1) {
						add = add + i + ",";
					}
				}
			} else if (shuangzhou == 1) {
				for (int i = first; i <= end; i++) {
					if (i % 2 == 0) {
						add = add + i + ",";
					}
				}
			} else {
				for (int i = first; i <= end; i++) {
					add = add + i + ",";
				}
			}
		}
		return add;
	}

	/**
	 * 匹配 - 或者没有 -
	 * 
	 * @param list
	 * @return
	 */
	private String match2(String text, int danzhou, int shuangzhou) {
		String re = "";
		Pattern pattern1 = Pattern.compile("(.*)\\-");
		Matcher matcher1 = pattern1.matcher(text);
		if (matcher1.find()) {
			String r = match1(text, danzhou, shuangzhou);
			if (!"".equals(r)) {
				re = re + match1(text, danzhou, shuangzhou) + ",";
			}

		} else {
			re = re + text + ",";
		}

		return re;
	}
	
	//用作本地测试，读取本地缓存文档，避免频繁访问服务器
		private String testString(String param) {
			String returnText = "";
			try {
				AssetManager assetManager = context.getAssets();
				// param===="441.txt"
				InputStream in = assetManager.open(param);
				InputStreamReader inRead = new InputStreamReader(in, "UTF-8");
				BufferedReader buffRead = new BufferedReader(inRead);
				StringBuffer sBuff = new StringBuffer();
				String data = "";
				while ((data = buffRead.readLine()) != null) {
					sBuff.append(data);
				}
				returnText = sBuff.toString();
			} catch (Exception e) {
				Log.i("AnaLysis", e.toString());
			}
			Log.i("AnaLysis", returnText);
			return returnText;
		}
}
