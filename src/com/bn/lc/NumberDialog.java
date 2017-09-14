package com.bn.lc;

import android.app.Dialog;
import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class NumberDialog extends Dialog implements OnClickListener
{
	NumberListener jineListener;
	NumberListener rateListener;

	CalActivity activity;
	 int numberIds[]={
     		R.id.Button00,R.id.Button01,R.id.Button02,
     		R.id.Button03,R.id.Button04,R.id.Button05,
     		R.id.Button06,R.id.Button07,R.id.Button08,
     		R.id.Button09,R.id.Button010
     };//各个数字的id号
	 
	 int id;//传过来的参数
	
	 public NumberDialog(Context context) {
			super(context);
	 }
	 
	 //添加监听的方法
	 public void addJineListener(NumberListener jineListener)
	 {
		 this.jineListener=jineListener;
	 }
	 public void addRateListener(NumberListener rateListener)
	 {
		 this.rateListener=rateListener;
	 }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.num);
        //各数字的监听
        for(int id:numberIds)
        {
        	Button bnum=(Button)findViewById(id);
        	bnum.setOnClickListener(this);
        }
      //删除按钮监听
        ImageButton del=(ImageButton)findViewById(R.id.ImageButton01);
        del.setOnClickListener(this);
      //关闭按钮监听
        Button close=(Button)findViewById(R.id.close);
        close.setOnClickListener(this);
      //确定按钮监听
        Button bok=(Button)findViewById(R.id.bok);
        bok.setOnClickListener(this);
    }
	
	

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.ImageButton01://若点击的是确定则检查输入的密码
			EditText et=(EditText)findViewById(R.id.EditText01);
			et.setFocusable(false);
			et.setEnabled(false);
			String num=et.getText().toString();
			num=(num.length()>1?num.substring(0,num.length()-1):"");
			et.setText(num);
			break;
		case R.id.close:
			NumberDialog.this.cancel();
			break;
		case R.id.bok:
			et=(EditText)findViewById(R.id.EditText01);
			String result=et.getText().toString();
			Log.d("shu zhi:", result);
			if(jineListener!=null)
			{
				jineListener.doAction(result);
			}
			if(rateListener!=null)
			{
				rateListener.doAction(result);
			}
			
			NumberDialog.this.dismiss();
			break;
		case R.id.Button00:
		case R.id.Button01:
		case R.id.Button02:
		case R.id.Button03:
		case R.id.Button04:
		case R.id.Button05:
		case R.id.Button06:
		case R.id.Button07:
		case R.id.Button08:
		case R.id.Button09:
		case R.id.Button010:
			Button tempnum=(Button)v;
			et=(EditText)findViewById(R.id.EditText01);
        	if(!((et.getText().toString()).equals("0.0")))
        	{
        		et.append(tempnum.getText());
        	}
        	else
        	{
        		et.setText("");
        		et.append(tempnum.getText());
        	}
        	break;
			
			
		}
	}
	
	
	

}
