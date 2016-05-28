package com.example.learn.model;

import java.util.ArrayList;

public class WeekClass {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassPlace() {
		return classPlace;
	}
	public void setClassPlace(String classPlace) {
		this.classPlace = classPlace;
	}
	public String getClassWeek() {
		return classWeek;
	}
	public void setClassWeek(String classWeek) {
		this.classWeek = classWeek;
	}
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public String getMatchWeek() {
		return matchWeek;
	}
	public void setMatchWeek(String matchWeek) {
		this.matchWeek = matchWeek;
	}
	public String getTeatherName() {
		return teatherName;
	}
	public void setTeatherName(String teatherName) {
		this.teatherName = teatherName;
	}
	private String className;
	private String teatherName;
	private String classPlace;
	private String classWeek;
	private String classNum;
	private String matchWeek;
	private String id;
}
