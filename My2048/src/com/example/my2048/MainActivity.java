package com.example.my2048;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	final static int LEFT = -1;
	final static int RIGHT = 1;
	final static int UP = -4;
	final static int DOWN = 4;

	GridLayout gridLayout = null;
	TextView scoreText = null;
	TextView topScoreText =  null;
	Button gobackBtn = null;
	Button resetBtn = null;
	
	
	int score = 0;
	TopScore topScore;
	
	//用于保存空格的位置
	List<Integer> spaceList = new ArrayList<Integer>();
	
	//所有非空的格子
	NumberList numberList = new NumberList();
	
	//用于保存每次操作时，已经升级过的格子
	List<Integer> changeList = new ArrayList<Integer>();
	
	//用于表示本次滑动是否有格子移动过
	boolean moved = false;
	
	//保存历史记录，用于撤销操作
	Stack<History> historyStack = new Stack<History>();
	
	GestureDetector gd = null;
	
	/**
	 * 图标数组
	 */
	private final int[] icons = { R.drawable.kong, R.drawable.b2,  
            R.drawable.b4, R.drawable.b8, R.drawable.b16,  
            R.drawable.b32, R.drawable.b64, R.drawable.b128,  
            R.drawable.b256, R.drawable.b512, R.drawable.b1024 ,R.drawable.b2048 };  
	
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("程序启动");
		super.onCreate(savedInstanceState);
		
		  // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 隐藏状态栏,全屏显示
        // 第一种：
       getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		gridLayout = (GridLayout) findViewById(R.id.GridLayout1);
		
		LinearLayout Alllayout = (LinearLayout)findViewById(R.id.Alllayout);
		scoreText = (TextView) findViewById(R.id.score);
		topScoreText = (TextView) findViewById(R.id.topScore);
		gobackBtn = (Button) findViewById(R.id.gobackBut);
		resetBtn = (Button) findViewById(R.id.resetBut);	
		
		topScore = new TopScore(this);
		
		init();
		
		MygestureDetector mg = new MygestureDetector();

		gd = new GestureDetector(mg);
		gridLayout.setOnTouchListener(mg);
		gridLayout.setLongClickable(true);	
		
		Alllayout.setOnTouchListener(mg);
		Alllayout.setLongClickable(true);
		
		gobackBtn.setOnClickListener(new ButtonOnclickListener());
		resetBtn.setOnClickListener(new ButtonOnclickListener());
	}
	
	/**
	 * 初始化界面，填充空白格，并随机生成两个数字格
	 */
	public void init(){
		System.out.println("初始化");
		historyStack.clear();
		
		scoreText.setText(score+"");
		topScoreText.setText(topScore.getTopScode()+"");
		
		//首先在16个各种都填上空白的图片
		for(int i=0;i<16;i++){
			View view = View.inflate(this, R.layout.item, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			
			image.setBackgroundResource(icons[0]);
			spaceList.add(i);
			gridLayout.addView(view);	
		}
		
		//在界面中随机加入两个2或者4
		addRandomItem();
		addRandomItem();
		
		History history = new History(spaceList, numberList, score);
		historyStack.push(history);
		System.out.println("历史轨迹压入栈中   ");
		history.printLog();
	}
	
	/**
	 * 从空格列表中随机获取位置
	 * @return 在gridLayout中的位置
	 */
	public int getRandomIndex(){
		Random random = new Random();
		if(spaceList.size()>0)
		 return random.nextInt(spaceList.size());
		else 
		 return -1;	
	}
	
	/**
	 * 在空白格中随机加入数字2或4
	 */
	public void addRandomItem(){
		int index = getRandomIndex();
		if(index!=-1){
			//获取对应坐标所对应的View
			View view = gridLayout.getChildAt(spaceList.get(index));
			ImageView image = (ImageView) view.findViewById(R.id.image);
			//随机生成数字1或2
			int i = (int) Math.round(Math.random()+1);
			//将当前格子的图片置换为2或者4
			image.setBackgroundResource(icons[i]);	
		
			//在numList中加入该格子的信息
			numberList.add(spaceList.get(index), i);
			
			//在空白列表中去掉这个格子
			spaceList.remove(index);
		
		}
	}
	
	public class MygestureDetector implements OnGestureListener,OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub		
			return gd.onTouchEvent(event);
		}

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
	        // 参数解释：      
	        // e1：第1个ACTION_DOWN MotionEvent      
	        // e2：最后一个ACTION_MOVE MotionEvent      
	        // velocityX：X轴上的移动速度，像素/秒      
	        // velocityY：Y轴上的移动速度，像素/秒      
	        
	        // 触发条件 ：      
	        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒    
			
			if(e1.getX()-e2.getX()>100){
				move(LEFT);
				return true;
			}else	if(e1.getX()-e2.getX()<-100){
				move(RIGHT);
				return true;
			}else	if(e1.getY()-e2.getY()>100){
				move(UP);
				return true;
			}else	if(e1.getY()-e2.getY()<-00){
				move(DOWN);
				return true;
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	/**
	 * 用于获取移动方向上下一个格子的位置
	 * @param index      当前格子的位置
	 * @param direction  滑动方向
	 * @return 如果在边界在返回-1
	 */
	public int getNext(int index,int direction){
		
		int y = index/4;
		int x = index%4;
		
		if(x==3 && direction==RIGHT)	
			return -1;			
		if(x==0 && direction==LEFT)
			return -1;
		if(y==0 && direction==UP)
			return -1;
		if(y==3 && direction==DOWN)
			return -1;	
		return index+direction;	
	}
	
	/**
	 * 用于获取移动方向上前一个格子的位置
	 * @param index      当前格子的位置
	 * @param direction  滑动方向
	 * @return 如果在边界在返回-1
	 */
	public int getBefore(int index,int direction){
		
		int y = index/4;
		int x = index%4;
		
		if(x==0 && direction==RIGHT)	
			return -1;			
		if(x==3 && direction==LEFT)
			return -1;
		if(y==3 && direction==UP)
			return -1;
		if(y==0 && direction==DOWN)
			return -1;	
		return index-direction;	
	}
	

	/**
	 * 该方法用来交换当前格与目标空白格的位置
	 * @param thisIdx 当前格子的坐标
	 * @param nextIdx 目标空白格的坐标
	 */
	public void replace(int thisIdx, int nextIdx){
		moved = true;
		//获取当前格子的view，并将其置成空白格
		View thisView = gridLayout.getChildAt(thisIdx);
		ImageView image = (ImageView) thisView.findViewById(R.id.image);
		image.setBackgroundResource(icons[0]);
		
		//获取空白格的view,并将其背景置成当前格的背景
		View nextView = gridLayout.getChildAt(nextIdx);
		ImageView nextImage = (ImageView) nextView.findViewById(R.id.image);
		nextImage.setBackgroundResource(icons[numberList.getNumberByIndex(thisIdx)]);
		
		//在空白格列表中，去掉目标格，加上当前格
		spaceList.remove(spaceList.indexOf(nextIdx));
		spaceList.add(thisIdx);
		
		//在数字格列表中，当前格的坐标置换成目标格的坐标
		numberList.changeIndex(thisIdx, nextIdx);
	}
	
	/**
	 * 刚方法用于合并在移动方向上两个相同的格子
	 * @param thisIdx 当前格子的坐标
	 * @param nextIdx 目标格子的坐标
	 */
	public void levelup(int thisIdx, int nextIdx){
		
		//一次移动中，每个格子最多只能升级一次
		if(!changeList.contains(nextIdx)){
			moved = true;
			//获取当前格子的view，并将其置成空白格
			View thisView = gridLayout.getChildAt(thisIdx);
			ImageView image = (ImageView) thisView.findViewById(R.id.image);
			image.setBackgroundResource(icons[0]);
			
			
			//获取目标格的view,并将其背景置成当前格升级后的背景
			View nextView = gridLayout.getChildAt(nextIdx);
			ImageView nextImage = (ImageView) nextView.findViewById(R.id.image);
			nextImage.setBackgroundResource(icons[numberList.getNumberByIndex(nextIdx)+1]);
			
			//在空白格列表中加入当前格
			spaceList.add(thisIdx);
			//在数字列中删掉第一个格子
			numberList.remove(thisIdx);
			//将数字列表对应的内容升级
			numberList.levelup(nextIdx);
			
			changeList.add(nextIdx);
			
			//更新分数
			updateScore((int)Math.pow(2, numberList.getNumberByIndex(nextIdx)));

		}

	}
	
	/**
	 * 该方法为不同的滑动方向，执行不同的遍历顺序
	 * @param direction 滑动方向
	 */
	public void move(int direction){
		
		moved = false;
		
		changeList.clear();
				
		switch(direction){
		case RIGHT:
			for(int y=0;y<4;y++){
				for(int x=2;x>=0;x--){
					int thisIdx = 4*y +x;
					Change(thisIdx,direction);
				}
			}
			break;
		case LEFT:
			for(int y=0;y<4;y++){
				for(int x=1;x<=3;x++){
					int thisIdx = 4*y +x;
					Change(thisIdx,direction);
				}
			}
			break;
		case UP:
			for(int x=0;x<4;x++){
				for(int y=1;y<=3;y++){
					int thisIdx = 4*y +x;
					Change(thisIdx,direction);
				}
			}
			break;	
		case DOWN:
			for(int x=0;x<4;x++){
				for(int y=2;y>=0;y--){
					int thisIdx = 4*y +x;
					Change(thisIdx,direction);
				}
			}
			break;
		}
		
		//如果本次滑动有格子移动过，则随机填充新的格子
		if(moved)
			addRandomItem();
		
			History history = new History(spaceList, numberList, score);
			historyStack.push(history);
			System.out.println("历史轨迹压入栈中   ");
			history.printLog();
		
		if(spaceList.size()==0){
			if(!numberList.hasChance())
				over();
		}

	}
	
	/**
	 * 该方法，为每个符合条件的格子执行变动的操作，如置换，升级等
	 * @param thisIdx     当前格子的坐标
	 * @param direction   滑动方向
	 */
	public void Change(int thisIdx,int direction){
		if(numberList.contains(thisIdx)){
						
			int nextIdx = getLast(thisIdx, direction);
			if(nextIdx == thisIdx){
				//不能移动
				return;
			}else if(spaceList.contains(nextIdx)){
				//存在可以置换的空白格
				replace(thisIdx,nextIdx);
			}else{				
				if(numberList.getNumberByIndex(thisIdx) == numberList.getNumberByIndex(nextIdx)){
					//可以合并
					levelup(thisIdx, nextIdx);
				}else{
					int before = getBefore(nextIdx, direction);
					if(before != thisIdx){
						//存在可以置换的空白格
						replace(thisIdx,before);
					}
				}
			}
		}
	}
	
	/**
	 * 用于获取移动方向上最后一个空白格之后的位置
	 * @param index      当前格子的坐标
	 * @param direction  移动方向
	 * @return
	 */
	public int getLast(int thisIdx, int direction){
		 int nextIdx = getNext(thisIdx, direction);
		 if(nextIdx < 0)
			 return thisIdx;
		 else{
			 if(spaceList.contains(nextIdx))
				 return getLast(nextIdx, direction);
			 else
				 return nextIdx;
		 }		
	}
	
	/**
	 * 该方法用于更新分数
	 * @param add 新增的分数
	 */
	public void updateScore(int add){
		score += add;
		scoreText.setText(score+"");
		if(score>topScore.getTopScode())
			topScore.setTopScode(score);
		topScoreText.setText(topScore.getTopScode()+"");
	}
	
	public void over(){
		new AlertDialog.Builder(this)
			.setTitle("哎！结束了")
			.setMessage("游戏结束，您的本局的分数是"+score+"分，继续加油哦！")
			.setPositiveButton("重新开始",new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					reset();
				}
			})
			.setNegativeButton("结束游戏", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MainActivity.this.finish();
					
				}
			}).show();		
	}
	
	/**
	 * 清空界面，重新初始化
	 */
	public void reset(){
		spaceList.clear();
		numberList.clear();
		score = 0;
		gridLayout.removeAllViews();
		init();
	}
	
	/**
	 * 撤销操作，获取操作记录栈中最后的记录，并重绘界面
	 */
	public void goBack(){
		//至少应有一次有效滑动后才能撤销
		if(historyStack.size()>=2){
		    //将当前的界面记录在栈中弹出
			historyStack.pop();
			//取栈中第二个对象即为本次操作之前的界面的记录
			History history = historyStack.peek();
			
			numberList = history.getNumberList();
			spaceList = history.getSpaceList();
			score = history.getScore();	
			
			//调取方法，重绘界面
			drawViews(spaceList, numberList, score);
		}
	}
	
	/**
	 * 根据参数重绘界面
	 * @param spaceList      空白格列表
	 * @param numberList     数字格列表
	 * @param score          当前分数
	 */
	public void drawViews(List<Integer> spaceList, NumberList numberList, int score){
		scoreText.setText(score+"");	
		gridLayout.removeAllViews();			
		for(int i=0; i<16; i++){			
			View view = View.inflate(this, R.layout.item, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);			
							
			if(numberList.contains(i))
				image.setBackgroundResource(icons[numberList.getNumberByIndex(i)]);				
			else
				image.setBackgroundResource(icons[0]);	
			gridLayout.addView(view);
		}
	}
	
	class ButtonOnclickListener implements android.view.View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch(arg0.getId()){
			case R.id.resetBut:
				recreate();
				break;
				
			case R.id.gobackBut:
				goBack();
				break;
			}
		}


		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
