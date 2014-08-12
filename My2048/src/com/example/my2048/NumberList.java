package com.example.my2048;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * ���ڱ������ָ��Ѿ����ָ��Ӧ������
 * @author Mr.Wang
 * 
 */
public class NumberList {

	//���list���ڱ������в�Ϊ�յĸ��ӵ����꣨��GridLayout�е�λ�ô�0��15��
	private List<Integer> stuffList = new ArrayList<Integer>();
	//���list���ڱ������в�Ϊ�յĸ��Ӷ�Ӧ�����֣���2Ϊ������ָ����
	private List<Integer> numberList = new ArrayList<Integer>();
	
	
	public NumberList() {
		super();
	}
	
	public NumberList(List<Integer> stuffList, List<Integer> numberList) {
		super();
		this.stuffList = stuffList;
		this.numberList = numberList;
	}


	/**
	 * �¼�������ָ�
	 * @param index   ���ָ��Ӧ��λ��
	 * @param number  ��Ӧ���ֵ�ָ������2Ϊ������
	 */
	public void add(int index, int number){
		stuffList.add(index);
		numberList.add(number);
	}
	
	/**
	 * �����жϵ�ǰλ���Ƿ�Ϊ���ָ�
	 * @param index  ��ǰλ��
	 * @return true��ʾ��
	 */
	public boolean contains(int index){
		return stuffList.contains(index);
	}
	
	/**
	 * ����ǰ�ĸ��Ӵ������б���ȥ��
	 * @param index
	 */
	public void remove(int index){
		int order = stuffList.indexOf(index);
		numberList.remove(order);
		stuffList.remove(order);
	}
	
	/**
	 * ����ǰ���Ӷ�Ӧ������������ָ����1
	 * @param index
	 */
	public void levelup(int index){
		int order = stuffList.indexOf(index);
		numberList.set(order, numberList.get(order)+1);
	}
	
	/**
	 * ����ǰ���Ӷ�Ӧ��λ���û�Ϊ�µ�λ��
	 * @param index      ��ǰλ��
	 * @param newIndex   �µ�λ��
	 */
	public void changeIndex(int index, int newIndex){
		stuffList.set(stuffList.indexOf(index), newIndex);
	}
	

	/**
	 * ͨ�����Ӷ�Ӧ��λ�û�ȡ���Ӧ������
	 * @param index   ��ǰλ��
	 * @return        ���Ӷ�Ӧ���ֵ�ָ��
	 */
	public int getNumberByIndex(int index){
		int order = stuffList.indexOf(index);
		return numberList.get(order) ;
	}
	
	/**
	 * ͨ�����Ӷ�Ӧ�ĺ�����������ȡ���Ӧ������
	 * @param x  ������
	 * @param y  ������
	 * @return   ���Ӷ�Ӧ���ֵ�ָ��
	 */
	public int getNumberByXY(int x,int y){
		
		if(x<0 || x>3 || y<0 || y>3)
			return -1;
		else {
			int order = stuffList.indexOf(4*x+y);
			return numberList.get(order) ;
		}
	}
	
	/**
	 * ������ָ����Ӧ������
	 */
	public void clear(){
		numberList.clear();
		stuffList.clear();
	}
	
	/**
	 * �ж��Ƿ��п��Ժϲ������ָ�
	 * @return ���򷵻�true
	 */
	public boolean hasChance(){
		for(int x = 0;x<=3;x++){
			for(int y=0;y<=3;y++){
				if(y<3){
					if(getNumberByXY(x,y)==getNumberByXY(x, y+1))
						return true;
				}
				if(x<3){
					if(getNumberByXY(x,y)==getNumberByXY(x+1, y))
						return true;
				}
			}			
		}
		return false;	
	}
	
	
	
	/**
	 * ��ȡ��Ӧ��Ÿ����е�λ��
	 */
	public int getIndexByOrder(int order){
		return stuffList.get(order);
	}
	
	/**
	 * ��ȡ��Ӧ��Ÿ����е�ָ��
	 */
	public int getNumberByOrder(int order){
		return numberList.get(order);
	}
	
	public int size(){
		return stuffList.size();
	}
	
	public String toString(){
		return stuffList.toString()+numberList.toString();
	}
	
	
	
	
	public List<Integer> getStuffList() {
		return stuffList;
	}

	public List<Integer> getNumberList() {
		return numberList;
	}

	public void printLog(){
		Log.i("stuffList", stuffList.toString());
		Log.i("numberList", numberList.toString());
	}
}
