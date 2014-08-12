package com.example.my2048;

import java.util.ArrayList;
import java.util.List;

public class History {
	
	//��Ӧ��activity�еĿո��б�
	private List<Integer> spaceList;
	//��Ӧ��activity�е������б�
	private NumberList numberList;
	//��Ӧ��activity�еĵ�ǰ����
	private int score = 0;
	
	/**
	 * ���췽��
	 * @param sl  �ո��б�
	 * @param nl  �����б�
	 * @param s   ��ǰ����
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
	 * ��ӡ��־
	 */
	public void printLog(){
		System.out.println(this.spaceList.toString());
		this.numberList.printLog();
		System.out.println("score: "+this.score);
	}
}
