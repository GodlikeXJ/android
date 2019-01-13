package com.example.king.pintu;

import android.app.Activity;
import android.os.Bundle;
import android.text.BoringLayout.Metrics;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	Itemblock item[][];
	int mx=2,my=2;
	int count=3;
	GridLayout gl;
	int perblockwidth;
	int arrayid=R.array.three1;
	ImageView fulliv;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);

		/*获取布局，按钮，并为布局设置行列数*/
		 gl=(GridLayout) findViewById(R.id.gl);
		Button reset=(Button) findViewById(R.id.reset);
		Button changePicture=(Button) findViewById(R.id.changePicture);
		Button upto=(Button) findViewById(R.id.upto);
		 fulliv=(ImageView) findViewById(R.id.fulliv);


		/*初始化块数*/

		init();

		/*点击reset时重新布局*/
			reset.setOnClickListener(new OnClickListener(){

				/*重新为每一块添加 事件监听器
				 * 随机赋值图片
				 *
				 * */
				public void onClick(View v) {
					for(int i=0;i<count;i++){
						for(int j=0;j<count;j++){
							item[i][j].setOnClickListener(MainActivity.this);
						}
					}
					
					radomAdd();
				}
			});

		/*点击更换图片
		 * 根据count的不同
		 * count==3时，先检查是什么arrayid，再切换到另外一个arrayid
		 * count==4时，相同
		 *
		 * */
			changePicture.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if(count==3){
						if(arrayid==R.array.three1){
							arrayid=R.array.three2;
							init();
							}else{
								arrayid=R.array.three1;
								init();
							}
					
					}else{
						if(arrayid==R.array.four1){
							arrayid=R.array.four2;
							init();
							}else{
								arrayid=R.array.four1;
								init();
							}
					}
				}
			});


		//更换难度
		/*如果count==3  就切换到count==4的状态，并设置初始数组，初始化
		 * 如果count==4  相同动作
		 *
		 * */
			upto.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if(count==3){
						arrayid=R.array.four1;
						count=4;
						init();
						
					}else{
						arrayid=R.array.three1;
						count=3;
						init();
						
					}
					
					
				}
			});
			
	

	}
	/*
	 * 获取屏幕宽度，并根据宽度与cout的值计算出每一块的长宽
	 * 为布局管理器设置 行列数
	 * 创建数组盛放建立的count*count个方块，并设置初始值为空白快，并为每一块设置事件监听器
	 * 根据arrayid设置 完整的图片
	 * 随机添加图片
	 *
	 *
	 * */
public void init(){
	gl.removeAllViews();

	/*获取屏幕宽度，并计算出每块的宽度*/
	DisplayMetrics metrics=new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(metrics);
	int screemwidth=metrics.widthPixels;
	//int screemheight=metrics.heightPixels;
	 perblockwidth=screemwidth/count;
	 
	 gl.setRowCount(count);
	gl.setColumnCount(count);
	 
	item=new Itemblock[count][count];
	for(int i=0;i<count;i++){
		for(int j=0;j<count;j++){
			item[i][j]=new Itemblock(this, count*count-1,arrayid);
			gl.addView(item[i][j],perblockwidth,perblockwidth);
			item[i][j].setOnClickListener(this);
		}
	}
	
	switch(arrayid){
	case R.array.three1:
		fulliv.setImageResource(R.drawable.fullp);
		break;
	case R.array.three2:
		fulliv.setImageResource(R.drawable.fullx);
		break;
	case R.array.four1:
		fulliv.setImageResource(R.drawable.fullz);
	break;
	case R.array.four2:
		fulliv.setImageResource(R.drawable.fullv);
	break;
	
	
	
	}


	/*随机添加布局*/
	radomAdd();
}



	/*判断是否完成*/
public boolean isVictory(){
	for(int i=0;i<count;i++){
		for(int j=0;j<count;j++){
			if(item[i][j].getnumber()!=i*count+j){
			return false;
			}
		}
	}
	return true;
}


public void radomAdd(){
		int temp=-1;
		int list[]=new int[count*count];
		int num=0;
	/*为数组设置初值*/
		for(int i=0;i<count*count;i++){
			list[i]=i;
		}

	/*随机交换数字*/
		for(int i=0;i<count*count*8;i++){
			int a=(int) (Math.random()*(count*count-1));
			int b=(int) (Math.random()*(count*count-1));
			//	Log.e("获得的随机数", a+":"+b);
			//根据随机数 交换数字
			temp=list[a];
			list[a]=list[b];
			list[b]=temp;
		}




	/*计算出逆序数*/
		for(int i=0;i<count*count-1;i++){
			for(int j=i;j<count*count-1;j++){
				if(list[i]>list[j]){
					num++;
				}
			}
		}
		
		Log.e("逆序数", num+"");



	/* 再只有三行的时候：
	 * 如果是奇数，就调整1  2 的位置，使逆序数变成偶数
	 * 如果是四行的情况下
	 * 如果是奇数就调整1  2 的位置，使逆序数变成偶数
	 * “
	 * 有解规则：
	 * 如果是奇数行(3行)的情况下：逆序数+空白行的行号+空白行的列号 ，是偶数，才有解
	 *
	 * 如果是偶数行(4行)的情况下： 空白行在从下往上数，在奇数行(从1开始数) ，则逆序数必须是偶数，，如果空白行所在的行数(从下往上数，从1开始)是偶数，则逆序数必须是奇数
	 *
	 *
	 * ”
	 *
	 *
	 *
	 * */
		if(num%2!=0){
			temp=list[1];
			list[1]=list[2];
			list[2]=temp;
			Log.e("我把逆序数调成偶数了", "balabala");
		}


	/*根据随机出来的数组，为xx添加图片*/
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				item[i][j].setimage(list[i*count+j]);
			}
		}
		
	}
	

	public void onClick(View v) {
		int x = -1,y=-1;
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				if(item[i][j]==v){
					x=i;
					y=j;
					break;
				}
			}
		}
	find88();
	if(x!=-1&&y!=-1){
	if((Math.abs(mx-x)==1&&my==y)||(Math.abs(my-y)==1&&mx==x)){
		//说明横向相邻
		int imagenumber=item[x][y].getnumber();
		item[mx][my].setimage(imagenumber);
		item[x][y].setimage(count*count-1);
		
		
	}else{
		//			Log.e("信息", "点的不对");
	}
	}	
	
	if(isVictory()){
		Toast.makeText(this, "你赢了，请重新开始吧！！", Toast.LENGTH_LONG).show();
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				item[i][j].setOnClickListener(null);
			}
		}
		
	}
	
	}

	/*查找那一块是空白快，并把查找到的值放在mx，my 中*/
	public void find88(){
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				if(item[i][j].getnumber()==count*count-1){
					mx=i;
					my=j;
				}
				}
			}
		}
		
	
}
