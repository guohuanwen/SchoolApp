package com.example.learn.model;

import java.util.ArrayList;

public class MyClass {

	public ArrayList<String> getClassName() {
		return className;
	}

	public void setClassName(ArrayList<String> className) {
		this.className = className;
	}

	public ArrayList<String> getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(ArrayList<String> teacherName) {
		this.teacherName = teacherName;
	}

	public ArrayList<String> getClassPlace() {
		return classPlace;
	}

	public void setClassPlace(ArrayList<String> classPlace) {
		this.classPlace = classPlace;
	}

	public ArrayList<String> getClassWeek() {
		return classWeek;
	}

	public void setClassWeek(ArrayList<String> classWeek) {
		this.classWeek = classWeek;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	

	public ArrayList<String> getMatchWeek() {
		return matchWeek;
	}

	public void setMatchWeek(ArrayList<String> matchWeek) {
		this.matchWeek = matchWeek;
	}



	private ArrayList<String> className;
	private ArrayList<String> teacherName;
	private ArrayList<String> classPlace;
	private ArrayList<String> classWeek;
	private String classNum;
	private ArrayList<String> matchWeek;

}
