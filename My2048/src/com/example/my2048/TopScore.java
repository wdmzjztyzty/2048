package com.example.my2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * �������ڱ���Ͷ�ȡ��߷�
 * @author Mr.Wang
 * 
 */
public class TopScore {
	private SharedPreferences sp;
	
	
	public TopScore(Context context){
		//��ȡperference�ļ������û�У���ᴴ��һ����ΪTopScore���ļ�
		sp = context.getSharedPreferences("TopScore", context.MODE_PRIVATE);
	}
	
	/**
	 * ���ڶ�ȡ��߷�
	 * @return ��߷�
	 */
	public int getTopScode(){	
		//��ȥ����TopScore����Ӧ��ֵ
		int topScore = sp.getInt("TopScore", 0);
		return topScore;
	}
	
	/**
	 * ����д����߷�
	 * @param topScore �µ���߷�
	 */
	public void setTopScode(int topScore){	
		//ʹ��Editor��д��perference�ļ�
		Editor editor = sp.edit();
		editor.putInt("TopScore", topScore);
		editor.commit();
	}
}
