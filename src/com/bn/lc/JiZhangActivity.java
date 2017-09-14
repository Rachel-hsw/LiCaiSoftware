package com.bn.lc;

import static com.bn.lc.Constant.DATE_DIALOG;
import static com.bn.lc.Constant.szIds;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("ResourceAsColor") public class JiZhangActivity extends Activity {
    private String isubject;     //科目
    private String imode;        //方式
    private String idate;        //日期
    private double iamount;      //金额
    private String inote;        //备注
    private String iplace;       //地点
    private String szStr;       //日常收入、日常支出
    
    private EditText amountEdit;
    private EditText noteEdit;
    private TextView dateEdit;
    private EditText placeEdit;
    
    Calendar c=null;
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
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
		setContentView(R.layout.bookkeeping);
		amountEdit=(EditText)findViewById(R.id.EditText01);
		noteEdit=(EditText)findViewById(R.id.EditText02);
		dateEdit=(TextView)findViewById(R.id.TextView02);
		placeEdit=(EditText)findViewById(R.id.EditText03);
		/***************************************************************/
		Intent intent=this.getIntent();
        String note=intent.getStringExtra("content");
		noteEdit.setText(note);
		/**************************************************************/
		//日常收入、日常支出
        Spinner shouzhiSp=(Spinner)this.findViewById(R.id.Spinner01);
        setSpAdapter(shouzhiSp,szIds.length,szIds);
        shouzhiSp.setOnItemSelectedListener(new ShouZhiSpListener()); //添加监听
        //科目
        Spinner kemuSp=(Spinner)this.findViewById(R.id.Spinner02);
        kemuSp.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				LinearLayout ll=(LinearLayout)arg1;
				TextView tvn=(TextView)ll.getChildAt(0);
				isubject=tvn.getText().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
        });
        //方式
        Spinner fangshiSp=(Spinner)this.findViewById(R.id.Spinner03);
        setSpinner(fangshiSp,"szmode");
        fangshiSp.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				LinearLayout ll=(LinearLayout)arg1;
				TextView tvn=(TextView)ll.getChildAt(0);
				imode=tvn.getText().toString();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
        });
        
      //返回主界面
        Button returnButton=(Button)this.findViewById(R.id.fanhui);
        returnButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JiZhangActivity.this.finish();
			}
		});
        //日期
        SimpleDateFormat   sDateFormat=new SimpleDateFormat("  yyyy-MM-dd"); //日期格式
        String dateStr=sDateFormat.format(new java.util.Date()); 
        dateEdit.setText(dateStr); 
        dateEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG);    //弹出系统日期对话框
			}
		});
        //保存
        Button saveButton=(Button)this.findViewById(R.id.Button01);
        saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    String amount=amountEdit.getText().toString().trim();  //金额
			    iamount=Double.parseDouble(amount);
				inote=noteEdit.getText().toString();    //备注
				idate=dateEdit.getText().toString().trim();
				iplace=placeEdit.getText().toString();
				if(amount.equals("")){             //金额为空，提示错误
					Toast.makeText(JiZhangActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
				}
				else{
					if(szStr.equals("日常收入")){
						DBUtil.insertZhangWu("shouru",isubject,idate,imode,iamount,iplace,inote);	
					}
					else{
						DBUtil.insertZhangWu("zhichu",isubject,idate,imode,iamount,iplace,inote);
					}
					Toast.makeText(JiZhangActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
					JiZhangActivity.this.finish();	//保存成功并返回主界面
				}
		}
		}); 
        //再记一笔
        Button againButton=(Button)this.findViewById(R.id.Button02);
        againButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
			    String amount=amountEdit.getText().toString().trim();  //金额
			    iamount=Double.parseDouble(amount);
				inote=noteEdit.getText().toString();    //备注
				idate=dateEdit.getText().toString().trim();
				iplace=placeEdit.getText().toString();
				if(amount.equals("")){
					Toast.makeText(JiZhangActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
				}
				else{
					if(szStr.equals("日常收入")){
						DBUtil.insertZhangWu("shouru",isubject,idate,imode,iamount,iplace,inote);	
					}
					else{
						DBUtil.insertZhangWu("zhichu",isubject,idate,imode,iamount,iplace,inote);
					}
				     amountEdit.setText("");
				     placeEdit.setText("");
				     noteEdit.setText("");
					Toast.makeText(JiZhangActivity.this,"保存成功,请继续添加",Toast.LENGTH_SHORT).show();
				}	
			}
		});
	}
	 public Dialog onCreateDialog(int id)
	    {
	       Dialog dialog=null;
	    	
	    	switch(id)
	    	{
	    	 case DATE_DIALOG://生成日期对话框
	    	   c=Calendar.getInstance();//获取日期对象
	 		   dialog=new DatePickerDialog(
	 		     this,
	 		     new DatePickerDialog.OnDateSetListener()
	 		     {
						@Override
						public void onDateSet(DatePicker arg0, int arg1, int arg2,int arg3) {				
							TextView dateedit=(TextView)findViewById(R.id.TextView02);
							dateedit.setTextSize(18);
							dateedit.setText(" "+arg1+"-"+((arg2+1)<10?"0"+(arg2+1):""+(arg2+1))+"-"+(arg3<10?"0"+arg3:""+arg3));
						}    		    	 
	 		     },
	 		     c.get(Calendar.YEAR),
	 		     c.get(Calendar.MONTH),
	 		     c.get(Calendar.DAY_OF_MONTH)    		     
	 		  );
	 	  break;
	    	}
	    	return dialog;
	    }
	//spinner适配器(从数组)
    public void setSpAdapter(final Spinner setspinner,final int size,final String msgIds[])
    {
    	BaseAdapter ba=new BaseAdapter()
        {
			@Override
			public int getCount() 
			{                           
				return  size;
			}
			@Override
			public Object getItem(int position) 
			{ 
				return null;
			}
			@Override
			public long getItemId(int arg0) 
			{ 
				return 0; 
			}
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{   
				LinearLayout ll=new LinearLayout(JiZhangActivity.this);
				ll.setOrientation(LinearLayout.VERTICAL);		
				TextView tv=new TextView(JiZhangActivity.this);
				tv.setText(msgIds[arg0]);   
			    tv.setTextSize(18);
				tv.setTextColor(R.color.black);
				ll.addView(tv);   
				return ll;
			}  
        };
        setspinner.setAdapter(ba);  
    }
    //spinner适配器（从表）
    public void setSpinner(final Spinner setspinner,final String tableName)
    {
    	BaseAdapter ba=new BaseAdapter()
        {  List<String> result=DBUtil.getSubjects(tableName);
			@Override
			public int getCount() 
			{                           
				return  result.size();
			}
			@Override
			public Object getItem(int position) 
			{ 
				return null;
			}
			@Override
			public long getItemId(int arg0) 
			{ 
				return 0; 
			}
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) 
			{
				LinearLayout ll=new LinearLayout(JiZhangActivity.this);
				ll.setOrientation(LinearLayout.VERTICAL);		
				TextView tv=new TextView(JiZhangActivity.this);
				tv.setText(result.get(arg0));   
			    tv.setTextSize(18);
				tv.setTextColor(R.color.black);
				ll.addView(tv);   
				return ll;
			}  
        };
        setspinner.setAdapter(ba);
    } 
    //科目随着日常收入、日常收支的变化而变化
    class ShouZhiSpListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			LinearLayout ll=(LinearLayout)arg1;
			TextView tvn=(TextView)ll.getChildAt(0);
			szStr=tvn.getText().toString();
			if(szStr.equals("日常收入"))
			{
				Spinner kemuSp=(Spinner)findViewById(R.id.Spinner02);
				setSpinner(kemuSp,"incomesubject");
			}
			else
			{
				Spinner kemuSp=(Spinner)findViewById(R.id.Spinner02);
				setSpinner(kemuSp,"spendsubject");
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    }
	
}
