﻿package model.page;

public class PageGroupResult {
	
	private int groupStartNumber;//그룹 시작 기본값 : 맨 아래 찍히는 시작 페이지 수
	private int groupEndNumber; //그룹 마지막 기본값 : 맨 아래 찍히는 마지막 페이지 수
	
	private boolean beforePage = true; 
	private boolean afterPage = true;
	
	private int selectPageNumber;
	
	public int getGroupStartNumber() {
		return groupStartNumber;
	}
	public void setGroupStartNumber(int groupStartNumber) {
		this.groupStartNumber = groupStartNumber;
	}
	public int getGroupEndNumber() {
		return groupEndNumber;
	}
	public void setGroupEndNumber(int groupEndNumber) {
		this.groupEndNumber = groupEndNumber;
	}
	public boolean isBeforePage() {
		return beforePage;
	}
	public void setBeforePage(boolean beforePage) {
		this.beforePage = beforePage;
	}
	public boolean isAfterPage() {
		return afterPage;
	}
	public void setAfterPage(boolean afterPage) {
		this.afterPage = afterPage;
	}
	public int getSelectPageNumber() {
		return selectPageNumber;
	}
	public void setSelectPageNumber(int selectPageNumber) {
		this.selectPageNumber = selectPageNumber;
	}	
	
}
