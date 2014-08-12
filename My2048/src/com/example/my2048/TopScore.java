package com.example.my2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 该类用于保存和读取最高分
 * @author Mr.Wang
 * 
 */
public class TopScore {
	private SharedPreferences sp;
	
	
	public TopScore(Context context){
		//读取perference文件，如果没有，则会创建一个名为TopScore的文件
		sp = context.getSharedPreferences("TopScore", context.MODE_PRIVATE);
	}
	
	/**
	 * 用于读取最高分
	 * @return 最高分
	 */
	public int getTopScode(){	
		//对去键“TopScore”对应的值
		int topScore = sp.getInt("TopScore", 0);
		return topScore;
	}
	
	/**
	 * 用于写入最高分
	 * @param topScore 新的最高分
	 */
	public void setTopScode(int topScore){	
		//使用Editor类写入perference文件
		Editor editor = sp.edit();
		editor.putInt("TopScore", topScore);
		editor.commit();
	}
}
