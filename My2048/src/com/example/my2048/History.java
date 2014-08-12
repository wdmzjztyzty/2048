package com.example.my2048;

import java.util.ArrayList;
import java.util.List;

public class History {
	
	//对应主activity中的空格列表
	private List<Integer> spaceList;
	//对应主activity中的数字列表
	private NumberList numberList;
	//对应主activity中的当前分数
	private int score = 0;
	
	/**
	 * 构造方法
	 * @param sl  空格列表
	 * @param nl  数字列表
	 * @param s   当前分数
	 */
	History(List<Integer> sl, NumberList nl, int s) {
		spaceList = new ArrayList<Integer>(sl);
		numberList = new NumberList(new ArrayList<Integer>(nl.getStuffList()), new ArrayList<Integer>(nl.getNumberList()));
		this.score = s;
	}
	
	public List<Integer> getSpaceList(){
		return this.spaceList;
	}

	public NumberList getNumberList() {
		return this.numberList;
	}

	public int getScore() {
		return this.score;
	}
	
	/**
	 * 打印日志
	 */
	public void printLog(){
		System.out.println(this.spaceList.toString());
		this.numberList.printLog();
		System.out.println("score: "+this.score);
	}
}
