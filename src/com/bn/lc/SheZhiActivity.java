package com.bn.lc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SheZhiActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.shezhi);
    	//�����Ŀ
        Button incomebutton=(Button)findViewById(R.id.Button01);
        incomebutton.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				Intent intent=new Intent();
    		        intent.setClass(SheZhiActivity.this, ShouRuKmActivity.class);
    		        startActivity(intent);
    			}
    		});
        
    	//֧����Ŀ
    	Button btspend=(Button)this.findViewById(R.id.Button02);
    	btspend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
		        intent.setClass(SheZhiActivity.this, ZhiChuKmActivity.class);
		        startActivity(intent);
			}
		});
    	//�˻�
    	Button btzhanghu=(Button)this.findViewById(R.id.Button03);
    	btzhanghu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
		        intent.setClass(SheZhiActivity.this, ZhangHuActivity.class);
		        startActivity(intent);
			}
		});
    	//��������
    	Button bttixing=(Button)this.findViewById(R.id.Button06);
    	bttixing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(SheZhiActivity.this, TiXingSheZhiActivity.class);
				startActivity(intent);
				//setContentView(R.layout.tixingshezhi);
			}
		});
    	//����
    	Button btabout=(Button)this.findViewById(R.id.Button07);
    	btabout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(SheZhiActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});
    	//����
    	Button returnbutton=(Button)this.findViewById(R.id.fanhui);
    	returnbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SheZhiActivity.this.finish();
			}
		});
	}
    public void goAbout(){
    	setContentView(R.layout.about);
    }
}
