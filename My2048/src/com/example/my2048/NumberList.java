package com.example.my2048;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * 用于保存数字格，已经数字格对应的数字
 * @author Mr.Wang
 * 
 */
public class NumberList {

	//这个list用于保存所有不为空的格子的坐标（在GridLayout中的位置从0到15）
	private List<Integer> stuffList = new ArrayList<Integer>();
	//这个list用于保存所有不为空的格子对应的数字（以2为底数的指数）
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
	 * 新加入的数字格
	 * @param index   数字格对应的位置
	 * @param number  对应数字的指数（以2为底数）
	 */
	public void add(int index, int number){
		stuffList.add(index);
		numberList.add(number);
	}
	
	/**
	 * 用于判断当前位置是否为数字格
	 * @param index  当前位置
	 * @return true表示是
	 */
	public boolean contains(int index){
		return stuffList.contains(index);
	}
	
	/**
	 * 将当前的格子从数字列表中去掉
	 * @param index
	 */
	public void remove(int index){
		int order = stuffList.indexOf(index);
		numberList.remove(order);
		stuffList.remove(order);
	}
	
	/**
	 * 将当前格子对应的数字升级，指数加1
	 * @param index
	 */
	public void levelup(int index){
		int order = stuffList.indexOf(index);
		numberList.set(order, numberList.get(order)+1);
	}
	
	/**
	 * 将当前格子对应的位置置换为新的位置
	 * @param index      当前位置
	 * @param newIndex   新的位置
	 */
	public void changeIndex(int index, int newIndex){
		stuffList.set(stuffList.indexOf(index), newIndex);
	}
	

	/**
	 * 通过格子对应的位置获取其对应的数字
	 * @param index   当前位置
	 * @return        格子对应数字的指数
	 */
	public int getNumberByIndex(int index){
		int order = stuffList.indexOf(index);
		return numberList.get(order) ;
	}
	
	/**
	 * 通过格子对应的横纵坐标来获取其对应的数字
	 * @param x  横坐标
	 * @param y  纵坐标
	 * @return   格子对应数字的指数
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
	 * 清空数字格及其对应的数字
	 */
	public void clear(){
		numberList.clear();
		stuffList.clear();
	}
	
	/**
	 * 判断是否还有可以合并的数字格
	 * @return 有则返回true
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
	 * 获取对应序号格子中的位置
	 */
	public int getIndexByOrder(int order){
		return stuffList.get(order);
	}
	
	/**
	 * 获取对应序号格子中的指数
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
