package com.example.king.pintu;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Itemblock extends FrameLayout {

//	int imagedata[]={R.drawable.p1,R.drawable.p2,R.drawable.p3
//			,R.drawable.p4,R.drawable.p5,R.drawable.p6
//			,R.drawable.p7,R.drawable.p8,R.drawable.p9};
	
	int imagedata[];
	int Arrayid;//代表使用哪个数组资源

	
	TypedArray ima;

	
	ImageView iv ;
	LayoutParams params;
	private int imagenum;
	
	public Itemblock(Context context,int imagenum,int Arrayid) {
		super(context);
		this.imagenum=imagenum;
		this.Arrayid=Arrayid;
		init();
	}
	

	
	
	void init(){
		//根据arrayid 初始化数组
		
		ima=getResources().obtainTypedArray(Arrayid);
		imagedata=new int[ima.length()];
		for(int i=0;i<ima.length();i++){
			imagedata[i]=ima.getResourceId(i, 0);
		}
		
		
		 iv =new ImageView(getContext());
		 setimage(imagenum);
		// iv.setBackgroundColor(Color.BLUE);
		 params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		// params.setMargins(2, 2, 2, 2);
	//	 setBackgroundColor(Color.RED);
		 
		
		 
		 setPadding(2, 2, 2, 2);
		 
		 addView(iv,params);
	}
	
	
	 void setimage(int num ){
		 this.imagenum=num;
		 iv.setImageResource(imagedata[num]);
		 
	}
	 
	 int getnumber(){
		 return imagenum;
		 
	 }

}
