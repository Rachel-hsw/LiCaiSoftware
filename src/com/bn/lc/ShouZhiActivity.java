package com.bn.lc;

import static com.bn.lc.Constant.DETAIL_DIALOG;
import static com.bn.lc.Constant.shourutj;
import static com.bn.lc.Constant.zhichutj;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("ResourceAsColor") public class ShouZhiActivity extends Activity {
	private static String isubject;
    private static String imode;
    private static String idate;
    private static String iamount;
    private String dateStr;
    private String yearStr;
    private String lastyearStr;
    private String monthStr;
    private String lastmonthStr;
    private String lastthreemonthStr;
    private String lastmonthdayStr;
    private Spinner szCategory;
    private List<String[]> bl=new ArrayList<String[]>();
    Dialog dialogDetailConfirm;
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
		setContentView(R.layout.szqk);
		szCategory=(Spinner)findViewById(R.id.Spinner01); 
		SimpleDateFormat   sDateFormat=new SimpleDateFormat("  yyyy-MM-dd"); 
	    dateStr=sDateFormat.format(new java.util.Date()).trim(); //获取当前日期
	    SimpleDateFormat   sMonthFormat=new SimpleDateFormat("  yyyy-MM"); 
	    SimpleDateFormat   sYearFormat=new SimpleDateFormat("  yyyy");
	    yearStr=sYearFormat.format(new java.util.Date()).trim(); //获取当前年份
	    lastyearStr=(Integer.parseInt(yearStr)-1)+""; //获取去年年份
	    monthStr=sMonthFormat.format(new java.util.Date()).trim(); //获取当前年月
	    lastmonthStr=yearStr+"-"+((new java.util.Date().getMonth()+1<10)?"0"+new java.util.Date().getMonth():new java.util.Date().getMonth());//获取今年上月日期
	    lastthreemonthStr=((new java.util.Date().getMonth()>2)?yearStr+"-"+(new java.util.Date().getMonth()-2):lastyearStr+"-"+(12+new java.util.Date().getMonth()-2))+"-"+"31";//今年前三个月
	    lastmonthdayStr=lastmonthStr+"-"+"31";
	     setSpAdapter(szCategory,shourutj.length,shourutj);//初始化spinner
	     setListener(szCategory,"shouru");
	     RadioGroup szRg=(RadioGroup)this.findViewById(R.id.RadioGroup01);
	     szRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				//根据选中的单选按钮改变spinner的内容
			    if(R.id.RadioButton01==checkedId) {
			    	setSpAdapter(szCategory,shourutj.length,shourutj);
			    	setListener(szCategory,"shouru");
			    }
			    else{
			    	setSpAdapter(szCategory,zhichutj.length,zhichutj);
			    	setListener(szCategory,"zhichu");
			    }	
			}
		});
	   //返回按钮
	 	Button returnbutton=(Button)this.findViewById(R.id.fanhui);
	 	returnbutton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				ShouZhiActivity.this.finish();
				}
			});
	}
	//spinner监听器(从数组)
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
				LinearLayout ll=new LinearLayout(ShouZhiActivity.this);
				ll.setOrientation(LinearLayout.VERTICAL);		
				TextView tv=new TextView(ShouZhiActivity.this);
				tv.setText(msgIds[arg0]);   
			    tv.setTextSize(18);
				tv.setTextColor(R.color.black);
				ll.addView(tv);   
				return ll;
			}  
        };
        setspinner.setAdapter(ba);  
    }
    public void setListener(Spinner setspinner,final String tableName){
    	setspinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ListView detaillv=(ListView)findViewById(R.id.ListView01);
				switch(arg2){
		    	case 0:
		    	bl=DBUtil.getTodayDetails(tableName,dateStr);
		    	setListAdapter(detaillv,bl);
		    	break;
		    	case 1:
		    	bl=DBUtil.getMonthYearDetails(tableName, monthStr);
		    	setListAdapter(detaillv,bl);
		    	break;
		    	case 2:
		    	bl=DBUtil.getMonthYearDetails(tableName, lastmonthStr);
			    setListAdapter(detaillv,bl);
			    break;
		    	case 3:
		        bl=DBUtil.getLastThreeMonthDetails(tableName, lastthreemonthStr,lastmonthdayStr);
				setListAdapter(detaillv,bl);
				break;
		    	case 4:
		    	bl=DBUtil.getMonthYearDetails(tableName, yearStr);
			    setListAdapter(detaillv,bl);
			    break;
		    	case 5:
			    bl=DBUtil.getMonthYearDetails(tableName, lastyearStr);
				setListAdapter(detaillv,bl);
				break;
		    	case 6:
		    	bl=DBUtil.getAllDetails(tableName);
		    	setListAdapter(detaillv,bl);
		    	break;
			}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
        });
    }
    public void setListAdapter(final ListView lv,final List<String[]> bl){
    	BaseAdapter ba=new BaseAdapter(){
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return bl.size();
			}
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			@Override
			public View getView(int position, View convertView,
					ViewGroup parent) {
				// TODO Auto-generated method stub
				TableLayout tl=new TableLayout(ShouZhiActivity.this);
				//tl.setOrientation(TableLayout.HORIZONTAL);
				tl.setShrinkAllColumns(false);
				
				  TableRow tr=new TableRow(ShouZhiActivity.this);
				  for(int j=0;j<4;j++)
				  { TextView tv=new TextView(ShouZhiActivity.this);
				   if(j==0||j==1||j==2){	
					  if(j==1){
						tv.setWidth(110);
						tv.setPadding(20, 20, 5, 5);
						}
					  if(j==0){
						tv.setWidth(110);
						tv.setPadding(0, 20, 5, 5);}
					  if(j==2){
						tv.setWidth(110);
						tv.setPadding(20, 20, 5, 5);
					}	
						}
					  else{
						tv.setWidth(140);
						tv.setPadding(20, 15, 5, 5);
					}
				    tv.setText(bl.get(position)[j]);
				    tr.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
							android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
						tv.setEllipsize(TruncateAt.END);
						tv.setTextSize(14);
						tv.setSingleLine(true);
						tv.setGravity(Gravity.CENTER);
						tv.setTextColor(ShouZhiActivity.this.getResources().getColor(R.color.black));	
						tr.addView(tv);}
						tl.addView(tr);
						return tl;
			}         
    	};
    	lv.setAdapter(ba);	
    	lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TableLayout tl=(TableLayout)arg1;
				TableRow tr=(TableRow)tl.getChildAt(0);
				for(int i=0;i<4;i++){
					final TextView tv=(TextView)tr.getChildAt(i);
					TextPaint mTextPaint=tv.getPaint();
					final float textWidth=mTextPaint.measureText(bl.get(arg2)[i]);
					if(i==1){
						if(textWidth>84.0){
							tv.setOnClickListener(new View.OnClickListener() {	
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									isubject=bl.get(arg2)[0];
									iamount=bl.get(arg2)[1];
									imode=bl.get(arg2)[2];
									idate=bl.get(arg2)[3];
									showDialog(DETAIL_DIALOG);
								}
							});	
						}
					}
					if(i==0||i==2){
						if(textWidth>100.0){
						    tv.setOnClickListener(new View.OnClickListener() {	
							@Override
							public void onClick(View v) {
								isubject=bl.get(arg2)[0];
								iamount=bl.get(arg2)[1];
								imode=bl.get(arg2)[2];
								idate=bl.get(arg2)[3];
								showDialog(DETAIL_DIALOG);
								
							}
						});
					}
					}
				}
			}
    		
    	});
    }
	 public Dialog onCreateDialog(int id)
	    {
	       Dialog dialog=null;
	    	
	    	switch(id)
	    	{case DETAIL_DIALOG:
	        	AlertDialog.Builder abDetailConfirm=new AlertDialog.Builder(ShouZhiActivity.this);
	        	dialogDetailConfirm=abDetailConfirm.create();
	        	dialog=dialogDetailConfirm;
	        	break;
	    	}
	    	return dialog;
	    }
	 @Override
		public void onPrepareDialog(int id,Dialog dialog) 
		{
			super.onPrepareDialog(id, dialog);
	    	switch(id){
	    	case DETAIL_DIALOG:
	        	dialog.setContentView(R.layout.detaildialog);
	        	TextView subjecttv=(TextView)dialog.findViewById(R.id.TextView01);
	        	subjecttv.setText(isubject);
	        	TextView amounttv=(TextView)dialog.findViewById(R.id.TextView02);
	        	amounttv.setText(iamount);
	        	TextView modetv=(TextView)dialog.findViewById(R.id.TextView03);
	        	modetv.setText(imode);
	        	TextView datetv=(TextView)dialog.findViewById(R.id.TextView04);
	        	datetv.setText(idate);
	        	Button closebutton=(Button)dialog.findViewById(R.id.Button01);
	        	closebutton.setOnClickListener(new View.OnClickListener() {
	    			@Override
	    			public void onClick(View v) {
	    				// TODO Auto-generated method stub
	    				dialogDetailConfirm.cancel();
	    			}
	    		});
	        	break;
	    	}
	    	}
	

}
