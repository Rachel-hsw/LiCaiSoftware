package com.bn.lc;

import java.util.List;

import static com.bn.lc.Constant.*;
import com.bn.lc.DBUtil;
import com.bn.lc.LiCaiActivity;
import com.bn.lc.R;
import com.bn.lc.LiCaiView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LiCaiActivity extends Activity {
	int last;
	int[] msgIds={R.string.jinri,R.string.benzhou,R.string.benyue,
			R.string.shangzhou,R.string.shangyue,R.string.qiansanyue,
			R.string.jinnian,R.string.qunian,R.string.quanbu};//spinner的各选项id
	
	static String iamount;//金额
	static String isubject; //科目
	static String idate;//日期
	static String imode;//支付方式
	static String inote;//备注
	
	LiCaiView lcv;
	
	OnCheckedChangeListener listener;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//按下手机返回按钮时
    	if(keyCode==4){
    		goBack();
    		return true;
    	}
    	return false;
	}
	Handler hd=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case 0:
					goJiZhangView();
					break;
				case 1:
					goShouZhiView();
					break;
				case 2:
					goBaoBiaoView();
					break;
				case 3:
					goYuYinView();
					break;
				case 4:
					goYuSuanView();
					break;
				case 5:
					goZuJiView();
					break;
				case 6:
					goJiSuanView();
					break;
				case 7:
					goTiXingView();
					break;
				case 8:
					goSheZhiView();
					break;
				case 9:
					goJianYiView();
					break;
				case 10:
					goExitView();
					break;
				
			}
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏显示，即手机状态栏不可见
/*        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
       getWindow().setFlags
        (
     
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        goMain(); //去主页
        
    }
    public void goBack()
	{
		System.exit(0);
	}
    
    public void goJiZhangView()
    {
    	System.out.println("JiZhang`````````");
    	
    	Intent  jizhang= new Intent(LiCaiActivity.this, JiZhangActivity.class);
        startActivity(jizhang);  
    }
    public void goShouZhiView()
    {
    	Intent intent= new Intent();
        intent.setClass(LiCaiActivity.this, ShouZhiActivity.class);
        startActivity(intent);
    }
    public void goBaoBiaoView()
    {
    	System.out.println("BaoBiao`````````");
    	Intent  baobiao= new Intent(LiCaiActivity.this, BaoBiaoActivity.class);
        startActivity(baobiao);  
    }
    public void goYuYinView()
    {
    	System.out.println("YuYin`````````");
    	Intent  speech= new Intent(LiCaiActivity.this, YuyinActivity.class);
        startActivity(speech);  
    }
    public void goYuSuanView()
    {
    	System.out.println("YuSuan`````````");
    	Intent intent=new Intent();
        intent.setClass(LiCaiActivity.this, YuSuanActivity.class);
        startActivity(intent);
    }
    public void goZuJiView()
    {
    	System.out.println("ZuJi`````````");
    /*	Intent zj=new Intent(LiCaiActivity.this,ZuJiActivity.class);
    	startActivity(zj);*/
    }
    public void goJiSuanView()
    {
    	System.out.println("JiSuan`````````");
    	Intent cal = new Intent(LiCaiActivity.this, CalActivity.class);
        startActivity(cal);  
    }
    public void goTiXingView()
    {
    	Intent intent= new Intent();
        intent.setClass(LiCaiActivity.this, TiXingActivity.class);
        startActivity(intent);	
    }
    public void goSheZhiView()
    {
    	System.out.println("SheZhi`````````");
    	Intent intent= new Intent();
        intent.setClass(LiCaiActivity.this, SheZhiActivity.class);
        startActivity(intent);	
    }
    public void goJianYiView()
    {
    	System.out.println("JianYi`````````");
    	Intent jy=new Intent(LiCaiActivity.this,JianYiFeedbackActivity.class);
    	startActivity(jy);
    }
    public void goExitView()
    {
    	System.out.println("Exit`````````");
    	System.exit(0);
    }
    public void goMain(){
    	  if(lcv==null)
      	{ 
      		lcv=new LiCaiView(this);
      	}
    		setContentView(lcv);
    		List<String> slist=DBUtil.getSubjects("incomesubject");
         	if(slist.size()==0){
         	   for(int i=0;i<shourukm.length;i++)
         		   DBUtil.insertSubjects(shourukm[i], "incomesubject");
         	}
         	List<String> zlist=DBUtil.getSubjects("spendsubject");
         	if(zlist.size()==0){
         		for(int i=0;i<zhichukm.length;i++)
         			DBUtil.insertSubjects(zhichukm[i], "spendsubject");
            }
         	List<String> flist=DBUtil.getSubjects("szmode");
           	if(flist.size()==0){
           		for(int i=0;i<fangshi.length;i++)
           		  DBUtil.insertSubjects(fangshi[i], "szmode");	
         	}  
           	for(int i=0;i<zhichukm.length;i++){
             	  DBUtil.insertYusuan(i,0,"yusuan");
             	  DBUtil.insertYusuan(i,0,"yusuanyue");
               }
            for(int i=0;i<titleContent.length;i++){
              	  DBUtil.insertTiXingTitle(titleContent[i],"tixingtitle");
               }
    }
    
    public void goTOJudge(String time)
    {
    	
    }
    
}