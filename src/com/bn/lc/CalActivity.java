package com.bn.lc;

import static com.bn.lc.Constant.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CalActivity extends Activity{
	
	
	CalActivity calactivity;
	
	static int last;//返回的页面
	//获取当前日期
	Calendar c=Calendar.getInstance();
	int year=c.get(Calendar.YEAR);
	int month=c.get(Calendar.MONTH)+1;
	int day=c.get(Calendar.DAY_OF_MONTH);
	String curdate=year+"-"+month+"-"+day;
	String curdate2=year+"年"+month+"月";
	
	final int List_DIALOG1=1;//贷款类型列表对话框的数组
	final int List_DIALOG2=2;//年列表对话框的数组
	final int List_DIALOG3=3;//月列表对话框的数组
	final int List_DIALOG4=4;//商业贷款利率列表对话框的数组
	final int List_DIALOG5=5;//还款方式1
	final int List_DIALOG6=6;//公积金贷款利率列表对话框的数组
	final int List_DIALOG7=7;//公积金还款方式2
	final int List_DIALOG8=8;//还款方式3
	final int List_DIALOG9=9;//原贷款类型
	final int List_DIALOG10=10;//原贷款方式列表对话框的数组
//	final int List_DIALOG11=11;//还款方式列表对话框的数组
	final int List_DIALOG12=12;//整存整取储蓄存期列表对话框
	final int List_DIALOG13=13;//储蓄存期2
	final int List_DIALOG14=14;//支取频度
	
	final int Date_DIALOG1=15;//第一次还款时间
	final int Date_DIALOG2=16;//预计提前还款时间
	final int Date_DIALOG3=17;//存入日期
	final int Date_DIALOG4=18;//提取日期
	
	private NumberDialog num1;
	private NumberDialog num2;
	private NumberDialog num3;
	private NumberDialog num4;
	
	Date inputdate;//存入日期
	double inputmoney=0;//存入金额，每月存入金额
	double yearrate;//年利率
	Date outputdate;//提取日期
	double durdate;//储蓄存期
	Date startdate;//初始存入日期
	double yuerate=0;//月利率
	double pindu=1;//支取频度
	double times;//支取次数
	double dnian;//贷款期限年数
	double dyue;//贷款期限月数
	int hkfs;
	List<String[]> detailList=new ArrayList<String[]>();
	List<Double> tqList=new ArrayList<Double>();
	
	double businessinputmoney;
	double businessyearrate;
	double businessdurdate;
	double jijininputmoney;
	double jijinyearrate;
	double jijindurdate;
	
	private int hkyear;
	private int hkmonth;
	private int tqhkyear;
	private int tqhkmonth;
	private int timeinterval;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//按下手机返回按钮时
    	if(keyCode==4){
    		goBack(last);
    		return true;
    	}
    	return false;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        goCal();
        
	}
	
	public void goBack(int last)
	{
		switch(last)
		{
			case P_MAIN:
				CalActivity.this.finish();
				break;
			case P_JISUAN:
				goCal();
				break;
			case P_FD_GJJ:
				goJiJinfangdai();
				break;
			case P_FD_SYDK:
				goBusinessFangdai();
				break;
			case P_FD_ZHX:
				goZHfangdai();
				break;
			case P_TQHK_SYDK:
				goHuankuan();
				break;
			case P_TQHU_GJJ:
				goHuankuan();
				break;
			case P_HQCX:
				goSubHuoqi();
				break;
			case P_LCZQ:
				goSubLingCunZhengQu();
				break;
			case P_ZCZQ:
				goSubZhengCunZhengQu();
				break;
			case P_ZCLQ:
				goSubZhengCunLingQu();
				break;
		}
	}
	
	public void goCal()
	{
		last=P_MAIN;
		setContentView(R.layout.cal1);
        
        Button fangdai=(Button)findViewById(R.id.fd);
    	Button huankuan=(Button)findViewById(R.id.tqhk);
    	Button huoqi=(Button)findViewById(R.id.hqcx);
    	Button lczq=(Button)findViewById(R.id.lczq);
    	Button zczq=(Button)findViewById(R.id.zczq);
    	Button zclq=(Button)findViewById(R.id.zclq);
    	fangdai.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goBusinessFangdai();
					}
				}
    	);
    	huankuan.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goHuankuan();
					}
				}
    	);
    	huoqi.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goSubHuoqi();
					}
				}
    	);
    	lczq.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goSubLingCunZhengQu();
					}
				}
    	);
    	zczq.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goSubZhengCunZhengQu();
					}
				}
    	);
    	zclq.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						Log.d("zhu", "go qian");
						goSubZhengCunLingQu();
						Log.d("zhu", "go hou");
					}
				}
    	);
	}
	
	 //房贷
    public void goBusinessFangdai()
    {
    	last=P_JISUAN;
    	setContentView(R.layout.businessfangdai);
		Log.d("fangdai jianting", "jin ru fang dai!");
		num1=new NumberDialog(CalActivity.this);
		num2=new NumberDialog(CalActivity.this);
		
		Button back=(Button)findViewById(R.id.back);
		Button cal=(Button)findViewById(R.id.jscal);
		Button daikuan=(Button)findViewById(R.id.strB);
		Button jine=(Button)findViewById(R.id.jine);
		Button nian=(Button)findViewById(R.id.nian);
		Button yue=(Button)findViewById(R.id.yue);
		Button input=(Button)findViewById(R.id.shuru);
		Button lilv=(Button)findViewById(R.id.lilv);
		Button hkmode=(Button)findViewById(R.id.hkmode1);
	    
		hkfs=1;
		back.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						goCal();
					}
				}
		);
		
		daikuan.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG1);
					}
				}
		);
		nian.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG2);
					}
				}
		);
		yue.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG3);
					}
				}
		);
		input.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG4);
					}
				}
		);
		hkmode.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG5);
					}
				}
		);
		jine.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						//弹出数字键盘
						num1.show();
					}
				}
		);
		
		lilv.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
							
						//弹出数字键盘
						num2.show();
					}
				}
		);
		num2.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button lilv=(Button)findViewById(R.id.lilv);
						lilv.setText(numstr); 
					}
						
				}
		);
		
		
		num1.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button jine=(Button)findViewById(R.id.jine);
						jine.setText(numstr);
					}
					
				}
		);
		cal.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						Button jine=(Button)findViewById(R.id.jine);
						Button lilv=(Button)findViewById(R.id.lilv);
						
						inputmoney=(Double.parseDouble(jine.getText().toString()))*10000;
						durdate=dnian*12+dyue;
						yearrate=(Double.parseDouble(lilv.getText().toString()))/100;
						goBusinessFangDaiResult();
						
						
					}
				}
		);
		
    }
    
    //组合型
    public void goZHfangdai()
    {
    	last=P_JISUAN;
    	setContentView(R.layout.zhfangdai);
    	num1=new NumberDialog(CalActivity.this);
		num2=new NumberDialog(CalActivity.this);
	 	num3=new NumberDialog(CalActivity.this);
		num4=new NumberDialog(CalActivity.this);
    	Button back=(Button)findViewById(R.id.back);
    	Button cal=(Button)findViewById(R.id.jscal);
    	Button zuhe=(Button)findViewById(R.id.zuhe);
		Button businessjine=(Button)findViewById(R.id.bjine);
		Button jijinjine=(Button)findViewById(R.id.jjine);
		Button nian=(Button)findViewById(R.id.nian);
		Button yue=(Button)findViewById(R.id.yue);
		Button businessinput=(Button)findViewById(R.id.shuru);
		Button businesslilv=(Button)findViewById(R.id.lilv);
		Button jijininput=(Button)findViewById(R.id.jijininput);
		Button jijinlilv=(Button)findViewById(R.id.jijinlilv);
		Button hkmode=(Button)findViewById(R.id.hkmode1);
		hkfs=1;
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goCal();
					}
				}
    	);
    	cal.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						Button businessjine=(Button)findViewById(R.id.bjine);
						//Button jijinjine=(Button)findViewById(R.id.jjine);
						Button businesslilv=(Button)findViewById(R.id.lilv);
						//Button jijinlilv=(Button)findViewById(R.id.jijinlilv);
						businessinputmoney=(Double.parseDouble(businessjine.getText().toString()))*10000;
						businessyearrate=(Double.parseDouble(businesslilv.getText().toString()))/100;
						
						jijininputmoney=(Double.parseDouble(businessjine.getText().toString()))*10000;
						durdate=dnian*12+dyue;
						jijinyearrate=(Double.parseDouble(businesslilv.getText().toString()))/100;
						goZHFangDaiResult();
					}
				}
    	);
    	
    	zuhe.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG1);
					}
				}
    	);
    	businessjine.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num1.show();
					}
				}
    	);
    	jijinjine.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num3.show();
					}
				}
    	);
    	nian.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG2);
					}
				}
    	);
    	yue.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG3);
					}
				}
    	);
    	businessinput.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG4);
					}
				}
    	);
    	businesslilv.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num2.show();
					}
				}
    	);
    	jijininput.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG6);
					}
				}
    	);
    	jijinlilv.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num4.show();
					}
				}
    	);
    	hkmode.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG5);
					}
				}
    	);
    	
		num1.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button bjine=(Button)findViewById(R.id.bjine);
						bjine.setText(numstr);
					}
					
				}
		);
		num2.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button blilv=(Button)findViewById(R.id.lilv);
						blilv.setText(numstr); 
					}
					
				}
		);
		num3.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button jjine=(Button)findViewById(R.id.jjine);
						jjine.setText(numstr);
					}
					
				}
		);
		num4.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button jlilv=(Button)findViewById(R.id.jijinlilv);
						jlilv.setText(numstr); 
					}
					
				}
		);
    	
    }
    //公积金
    public void goJiJinfangdai()
    {
    	last=P_JISUAN;
    	setContentView(R.layout.jijinfangdai);
    	num1=new NumberDialog(CalActivity.this);
		num2=new NumberDialog(CalActivity.this);
    	Button back=(Button)findViewById(R.id.back);
    	Button cal=(Button)findViewById(R.id.jscal);
    	Button jijin=(Button)findViewById(R.id.jijin);
    	Button jine=(Button)findViewById(R.id.jine);
    	Button nian=(Button)findViewById(R.id.nian);
    	Button yue=(Button)findViewById(R.id.yue);
    	Button input=(Button)findViewById(R.id.jijininput);
    	Button lilv=(Button)findViewById(R.id.jijinlilv);
    	Button hkmode=(Button)findViewById(R.id.hkmode2);
    	hkfs=1;
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goCal();
					}
				}
    	);
    	cal.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						Button jine=(Button)findViewById(R.id.jine);
						Button lilv=(Button)findViewById(R.id.jijinlilv);
						inputmoney=(Double.parseDouble(jine.getText().toString()))*10000;
						durdate=dnian*12+dyue;
						yearrate=(Double.parseDouble(lilv.getText().toString()))/100;
						goJiJinFangDaiResult();
					}
				}
    	);
    	
    	jijin.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG1);
					}
				}
    	);
    	jine.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num1.show();
					}
				}
    	);
    	nian.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG2);
					}
				}
    	);
    	yue.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG3);
					}
				}
    	);
    	input.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG6);
					}
				}
    	);
    	lilv.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num2.show();
					}
				}
    	);
    	hkmode.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG7);
					}
				}
    	);
    	num1.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button jine=(Button)findViewById(R.id.jine);
						jine.setText(numstr);
					}
					
				}
		);
		num2.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button lilv=(Button)findViewById(R.id.jijinlilv);
						lilv.setText(numstr); 
					}
					
				}
		);
    }
    /***************************************************************************/
    /***************************************************************************/
    /****************************************************************************/
    //提前还款
    public void goHuankuan()
    {
    	last=P_JISUAN;
    	Log.d("huan kuan", "huan kuan");
    	num1=new NumberDialog(CalActivity.this);
		num2=new NumberDialog(CalActivity.this);
    	setContentView(R.layout.businesshuankuan);
    	Button back=(Button)findViewById(R.id.back);
    	Button cal=(Button)findViewById(R.id.jscal);
    	Button strB=(Button)findViewById(R.id.ydkleix);//商业贷款
    	Button debx=(Button)findViewById(R.id.ydkmode);//等额本息
    	Button jine=(Button)findViewById(R.id.jineid);//原贷款金额
    	Button nian=(Button)findViewById(R.id.nian);//年份
    	Button yue=(Button)findViewById(R.id.yue);//月份
    	Button input=(Button)findViewById(R.id.shuru);//手动输入
    	Button lilv=(Button)findViewById(R.id.lilv);//贷款利率
    	Button hktime=(Button)findViewById(R.id.hktime);//第一次还款时间
    	hktime.setText(curdate2);
    	Button tqhktime=(Button)findViewById(R.id.tqhktime);//预计提前还款时间
    	tqhktime.setText(curdate2);
    	//Button hkmode=(Button)findViewById(R.id.hkmode3);//还款方式
    	hkfs=0;
    	hkyear=tqhkyear=new java.util.Date().getYear()+1900;
    	hkmonth=tqhkmonth=new java.util.Date().getMonth()+1;
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goCal();
					}
				}
    	);
    	cal.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						Button jine=(Button)findViewById(R.id.jineid);//原贷款金额
						Button lilv=(Button)findViewById(R.id.lilv);//贷款利率
						//Button hktime=(Button)findViewById(R.id.hktime);//第一次还款时间
						//Button tqhktime=(Button)findViewById(R.id.tqhktime);//预计提前还款时间
						inputmoney=(Double.parseDouble(jine.getText().toString()))*10000;
						durdate=dnian*12+dyue;
						yearrate=(Double.parseDouble(lilv.getText().toString()))/100;
						if(tqhkyear>hkyear){
							timeinterval=(tqhkyear-hkyear)*12+tqhkmonth-hkmonth;	
						}
						else if(tqhkyear==hkyear){
							if(tqhkmonth<hkmonth){
								timeinterval=612;
							}
							else{
							timeinterval=tqhkmonth-hkmonth;}
						}
						else{
							timeinterval=612;
						}
						if(timeinterval>(int)durdate){
							Toast.makeText(CalActivity.this,"请检查选择的日期",Toast.LENGTH_SHORT).show();
						}
						else
						    {goHuanKuanResult();}	
					}	
				}
    	);
    	strB.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG9);
					}
				}
    	);
    	debx.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG10);
					}
				}
    	);
    	nian.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG2);
					}
				}
    	);
    	yue.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG3);
					}
				}
    	);
    	input.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG4);
					}
				}
    	);
    	hktime.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(Date_DIALOG1);
					}
				}
    	);
    	tqhktime.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(Date_DIALOG2);
					}
				}
    	);
    	jine.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num1.show();
					}
				}
    	);
    	lilv.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num2.show();
					}
				}
    	);
    	num1.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button jine=(Button)findViewById(R.id.jineid);
						jine.setText(numstr);
					}
					
				}
		);
		num2.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button lilv=(Button)findViewById(R.id.lilv);
						lilv.setText(numstr); 
					}
					
				}
		);
    }
    //活期储蓄
    public void goSubHuoqi()
    {
    	last=P_JISUAN;
    	setContentView(R.layout.hqcx);
    	num1=new NumberDialog(CalActivity.this);
		num2=new NumberDialog(CalActivity.this);
    	Button back=(Button)findViewById(R.id.back);
    	Button cal=(Button)findViewById(R.id.jscal);
    	Button bdate=(Button)findViewById(R.id.bdate);
    	bdate.setText(curdate);
    	Button lilv=(Button)findViewById(R.id.lilvid);
    	Button jine=(Button)findViewById(R.id.crje);
    	Button tqrq=(Button)findViewById(R.id.bdate2);
    	tqrq.setText(curdate);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goCal();
					}
				}
    	);
    	
    	bdate.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(Date_DIALOG3);
					}
				}
    	);
    	tqrq.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(Date_DIALOG4);
					}
				}
    	);
    	
    	jine.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						//弹出数字键盘
						num1.show();
					}
				}
		);
    	lilv.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num2.show();
					}
				}
    	);
    	
    	num1.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button jine=(Button)findViewById(R.id.crje);
						jine.setText(numstr);
					}
					
				}
		);
		num2.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button lilv=(Button)findViewById(R.id.lilvid);
						lilv.setText(numstr); 
					}
					
				}
		);
		cal.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						Button bdate=(Button)findViewById(R.id.bdate);
				    	Button lilv=(Button)findViewById(R.id.lilvid);
				    	Button jine=(Button)findViewById(R.id.crje);
				    	Button tqrq=(Button)findViewById(R.id.bdate2);
						inputdate=StringToDate(bdate.getText().toString(),"yyyy-MM-dd");
				    	inputmoney=Double.parseDouble(jine.getText().toString());
				    	yearrate=(Double.parseDouble(lilv.getText().toString()))/100;
				    	outputdate=StringToDate(tqrq.getText().toString(),"yyyy-MM-dd");
				    	goHQCXResult();
					}
				}
    	);
    	
    	
    }
    
   //零存整取
    public void goSubLingCunZhengQu()
    {
    	last=P_JISUAN;
    	setContentView(R.layout.lczq);
    	num1=new NumberDialog(CalActivity.this);
		num2=new NumberDialog(CalActivity.this);
		durdate=12;
    	Button back=(Button)findViewById(R.id.back);
    	Button cal=(Button)findViewById(R.id.jscal);
    	Button savetime=(Button)findViewById(R.id.oneyear);
    	Button inputtime=(Button)findViewById(R.id.bdate);
    	inputtime.setText(curdate);
    	Button inputjine=(Button)findViewById(R.id.jineid);
    	Button lilv=(Button)findViewById(R.id.nlilv);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goCal();
					}
				}
    	);
    	
    	savetime.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG12);
						
					}
				}
    	);
    	inputtime.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(Date_DIALOG3);
					}
				}
    	);
    	
    	inputjine.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						//弹出数字键盘
						num1.show();
					}
				}
		);
    	lilv.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num2.show();
					}
				}
    	);
    	
    	num1.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button inputjine=(Button)findViewById(R.id.jineid);
						inputjine.setText(numstr);
					}
					
				}
		);
		num2.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button lilv=(Button)findViewById(R.id.nlilv);
						lilv.setText(numstr); 
					}
					
				}
		);
    	
		cal.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						Button inputjine=(Button)findViewById(R.id.jineid);
						if((Float.parseFloat(inputjine.getText().toString()))<5)
						{
							Toast.makeText(CalActivity.this, "5元起存",Toast.LENGTH_SHORT).show();
						}
						else
						{
							Button lilv=(Button)findViewById(R.id.nlilv);
							Button inputtime=(Button)findViewById(R.id.bdate);
							startdate=StringToDate(inputtime.getText().toString(),"yyyy-MM-dd");
					    	inputmoney=Double.parseDouble(inputjine.getText().toString());
					    	yearrate=(Double.parseDouble(lilv.getText().toString()))/100;
					    	yuerate=yearrate/12;
							goLCZQResult();
						}
						
					}
				}
    	);
    }
    
    //整存整取
    public void goSubZhengCunZhengQu()
    {
    	last=P_JISUAN;
    	setContentView(R.layout.zczq);
    	num1=new NumberDialog(CalActivity.this);
		num2=new NumberDialog(CalActivity.this);
    	Button back=(Button)findViewById(R.id.back);
    	Button cal=(Button)findViewById(R.id.jscal);
    	Button savetime=(Button)findViewById(R.id.threemonth);
    	Button inputtime=(Button)findViewById(R.id.bdate);
    	inputtime.setText(curdate);
    	Button jine=(Button)findViewById(R.id.jineid);
    	Button lilv=(Button)findViewById(R.id.nlilv);
    	
    	durdate=(float)3/12;
    	
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goCal();
					}
				}
    	);
    	
    	savetime.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG13);
					}
				}
    	);
    	inputtime.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(Date_DIALOG3);
					}
				}
    	);
    	jine.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						//弹出数字键盘
						num1.show();
					}
				}
		);
    	lilv.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num2.show();
					}
				}
    	);
    	
    	num1.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button inputjine=(Button)findViewById(R.id.jineid);
						inputjine.setText(numstr);
					}
					
				}
		);
		num2.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button lilv=(Button)findViewById(R.id.nlilv);
						lilv.setText(numstr); 
					}
					
				}
		);
		
		cal.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						Button jine=(Button)findViewById(R.id.jineid);
						if((Float.parseFloat(jine.getText().toString()))<50)
						{
							Toast.makeText(CalActivity.this, "50元起存",Toast.LENGTH_SHORT).show();
						}
						else
						{
							Button lilv=(Button)findViewById(R.id.nlilv);
							inputmoney=Double.parseDouble(jine.getText().toString());
					    	yearrate=(Double.parseDouble(lilv.getText().toString()))/100;
							goZCZQResult();
						}
					}
				}
    	);
    	
    	
    }
    
    //整存零取
    public void goSubZhengCunLingQu()
    {
    	last=P_JISUAN;
    	Log.d("zheng cun ling qu", "qian");
    	setContentView(R.layout.zclq);
    	Log.d("zheng cun ling qu", "hou");
    	num1=new NumberDialog(CalActivity.this);
		num2=new NumberDialog(CalActivity.this);
    	Button back=(Button)findViewById(R.id.back);
    	Button cal=(Button)findViewById(R.id.jscal);
    	Button savetime=(Button)findViewById(R.id.oneyear);
    	Button inputtime=(Button)findViewById(R.id.bdate);
    	inputtime.setText(curdate);
    	Button inputjine=(Button)findViewById(R.id.jineid);
    	Button nlilv=(Button)findViewById(R.id.nlilv);
    	Button zqpd=(Button)findViewById(R.id.everymonth);
    	
    	durdate=12;
    	
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goCal();
					}
				}
    	);
    	
    	savetime.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG12);
					}
				}
    	);
    	inputtime.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(Date_DIALOG3);
					}
				}
    	);
    	zqpd.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(List_DIALOG14);
					}
				}
    	);
    	inputjine.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						//弹出数字键盘
						num1.show();
					}
				}
		);
    	nlilv.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						num2.show();
					}
				}
    	);
    	
    	num1.addJineListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button inputjine=(Button)findViewById(R.id.jineid);
						inputjine.setText(numstr);
					}
					
				}
		);
		num2.addRateListener(
				new NumberListener()
				{

					@Override
					public void doAction(String numstr) {
						Button lilv=(Button)findViewById(R.id.nlilv);
						lilv.setText(numstr); 
					}
					
				}
		);
		cal.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						Button inputjine=(Button)findViewById(R.id.jineid);
						if((Float.parseFloat(inputjine.getText().toString()))<1000)
						{
							Toast.makeText(CalActivity.this, "1000 元起存",Toast.LENGTH_SHORT).show();
						}
						else
						{
							Button nlilv=(Button)findViewById(R.id.nlilv);
							times=(float)durdate/pindu;
					    	inputmoney=Double.parseDouble(inputjine.getText().toString());
					    	yearrate=(Double.parseDouble(nlilv.getText().toString()))/100;
							goZCLQResult();
						}
					}
				}
    	);
    }
    
    
    //房贷计算结果
    public void goJiJinFangDaiResult()
    {
    	last=P_FD_GJJ;
    	setContentView(R.layout.businessfdresult);
    	Log.d("fangdai ji suan", "fang dai ji suan");
    	Button back=(Button)findViewById(R.id.back);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goJiJinfangdai();
					}
				}
    	);
    	Button detailButton=(Button)findViewById(R.id.xiangqing);
    	detailButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.detail);
				Button back=(Button)findViewById(R.id.back);
		    	back.setOnClickListener(
		    			new OnClickListener() {
							@Override
							public void onClick(View v) {
								goBusinessFangDaiResult();
							}
						}
		    	);
		    	ListView detailLv=(ListView)findViewById(R.id.ListView01);
		    	//detailLv.setAdapter()
		    	if(hkfs==1){
		    		detailList=DBUtil.getDetail("dengebenxidetail");
			    	setListAdapter(detailLv,detailList);
			    	Log.v("detaillist.size(",detailList.size()+"");
		    	}
		    	else{
		    		detailList=DBUtil.getDetail("dengebenjindetail");
			    	setListAdapter(detailLv,detailList);
			    	Log.v("detaillist.size(",detailList.size()+"");
		    	}
		    	
			}
		});
    	Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button02);
		double ratesum=0.0;
		double sum=0.0;
		String str1;
		String str2;
		if(hkfs==1)
		{
			DBUtil.deleteAllFromTable("dengebenxidetail");
			for(int i=1;i<=durdate;i++){
				
				DBUtil.insertDetail("dengebenxidetail", i, 0, 0, 0, 0);
			}
			sum=jsDengeBenxi(inputmoney,yearrate,durdate);
			ratesum=sum-inputmoney;
		}
		else
		{
			DBUtil.deleteAllFromTable("dengebenjindetail");
			for(int i=1;i<=durdate;i++){
				
				DBUtil.insertDetail("dengebenjindetail", i, 0, 0, 0, 0);
			}
			ratesum=jsDengeBenjin(inputmoney,yearrate,durdate);
			sum=ratesum+inputmoney;
		}
		
		java.text.DecimalFormat df=new java.text.DecimalFormat("#0.00");
		str1 = df.format(ratesum);
		str2=df.format(sum);
		b1.setText(str1);
		b2.setText(str2);
    	
    }
    
    public void goBusinessFangDaiResult()
    {
    	last=P_FD_SYDK;
    	setContentView(R.layout.businessfdresult);
    	Button back=(Button)findViewById(R.id.back);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goBusinessFangdai();
					}
				}
    	);
    	Button detailButton=(Button)findViewById(R.id.xiangqing);
    	detailButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.detail);
				Button back=(Button)findViewById(R.id.back);
		    	back.setOnClickListener(
		    			new OnClickListener() {
							@Override
							public void onClick(View v) {
								goBusinessFangDaiResult();
							}
						}
		    	);
		    	ListView detailLv=(ListView)findViewById(R.id.ListView01);
		    	//detailLv.setAdapter()
		    	if(hkfs==1){
		    		detailList=DBUtil.getDetail("dengebenxidetail");
			    	setListAdapter(detailLv,detailList);
			    	Log.v("detaillist.size(",detailList.size()+"");
		    	}
		    	else{
		    		detailList=DBUtil.getDetail("dengebenjindetail");
			    	setListAdapter(detailLv,detailList);
			    	Log.v("detaillist.size(",detailList.size()+"");
		    	}
		    	
			}
		});
    	Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button02);
		double ratesum=0.0;
		double sum=0.0;
		String str1;
		String str2;
		if(hkfs==1)
		{
			DBUtil.deleteAllFromTable("dengebenxidetail");
			for(int i=1;i<=durdate;i++){
				
				DBUtil.insertDetail("dengebenxidetail", i, 0, 0, 0, 0);
			}
			
			sum=jsDengeBenxi(inputmoney,yearrate,durdate);
			ratesum=sum-inputmoney;
		}
		else
		{
			DBUtil.deleteAllFromTable("dengebenjindetail");
			for(int i=1;i<=durdate;i++){
				
				DBUtil.insertDetail("dengebenjindetail", i, 0, 0, 0, 0);
			}
			ratesum=jsDengeBenjin(inputmoney,yearrate,durdate);
			sum=ratesum+inputmoney;
		}
		java.text.DecimalFormat df=new java.text.DecimalFormat("#0.00");
		str1 = df.format(ratesum);
		str2=df.format(sum);
		b1.setText(str1);
		b2.setText(str2);
    	
    }
    /***************************************************************/
    /****************************************************************/
    /**************************************************************/
    // 提前还款计算结果 
    public void goHuanKuanResult()
    {
    	last=P_TQHK_SYDK;
    	setContentView(R.layout.businesshkresult);
    	Button back=(Button)findViewById(R.id.back);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goHuankuan();
					}
				}
    	);
    	Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button02);
		Button b3=(Button)findViewById(R.id.Button03);
		Button b4=(Button)findViewById(R.id.Button04);
		//double ratesum=0.0;
		//double sum=0.0;
		double returnratesum=0.0;
		double returnsum=0.0;
		double saveratesum=0.0;
		double monthsum=0.0;
		String str1;
		String str2;
		String str3;
		String str4;
		Log.v("ckjlfkdjf", hkfs+"");
    	if(hkfs==0){
    	tqList=jsTqDengeBenxi(inputmoney,yearrate,timeinterval,durdate);
    	
    	returnsum=tqList.get(0);
    	returnratesum=tqList.get(1);
    	saveratesum=jsTqDengeBenxi(inputmoney,yearrate,durdate,durdate).get(1)-jsTqDengeBenxi(inputmoney,yearrate,timeinterval+1,durdate).get(1);
    	monthsum=tqList.get(3)*(1+yearrate/12);
    	}
    	else{//?????????????
    		returnratesum=jsTqDengeBenjin(inputmoney,yearrate,durdate,timeinterval,0);
    		returnsum=(inputmoney/durdate)*timeinterval+returnratesum;
    		saveratesum=jsTqDengeBenjin(inputmoney,yearrate,durdate,durdate,timeinterval+1);
    		monthsum=(inputmoney-(inputmoney/durdate)*timeinterval)*(1+yearrate/12);
    	}
    	java.text.DecimalFormat df=new java.text.DecimalFormat("#0.00");
		str1 = df.format(returnratesum);
		str2=df.format(returnsum);
		str3 = df.format(saveratesum);
		str4=df.format(monthsum);
    	b1.setText(str1);
    	b2.setText(str2);
    	b3.setText(str3);
    	b4.setText(str4);
    }
    
    //活期储蓄计算结果
    public void goHQCXResult()
    {
    	last=P_HQCX;
    	setContentView(R.layout.hqcxresult);
    	
    //	Button b1=(Button)findViewById(R.id.Button01);
    	Button b2=(Button)findViewById(R.id.Button02);
    	Button back=(Button)findViewById(R.id.back);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goSubHuoqi();
					}
				}
    	);
    	
    	
    	double benxi=inputmoney+inputmoney*yearrate*(((outputdate.getTime()-inputdate.getTime())/1000/60/60/24/365));
    	String str=String.valueOf(benxi);
    	b2.setText(str);
    }
    
    //零存整取计算结果
    public void goLCZQResult()
    {
    	last=P_LCZQ;
    	setContentView(R.layout.lczqresult);
    	Button back=(Button)findViewById(R.id.back);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goSubLingCunZhengQu();
						
					}
				}
    	);
    	Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button03);
		double inputsum=inputmoney*durdate;
		double benxi=inputsum+inputmoney*((durdate+1)/2*durdate)*yuerate;
		String str1=String.valueOf(inputsum);
		String str2=String.valueOf(benxi);
		b1.setText(str1);
		b2.setText(str2);
    }
    
    //整存整取计算结果
    public void goZCZQResult()
    {
    	last=P_ZCZQ;
    	setContentView(R.layout.zczqresult);
    	Button b1=(Button)findViewById(R.id.Button02);
    	Button back=(Button)findViewById(R.id.back);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goSubZhengCunZhengQu();
					}
				}
    	);
    	double benxi=inputmoney*(1+yearrate*durdate);
    	String str=String.valueOf(benxi);
    	b1.setText(str);
    }
    
    //整存零取计算结果
    public void goZCLQResult()

    {
    	last=P_ZCLQ;
    	setContentView(R.layout.zclqresult);
    	Button b1=(Button)findViewById(R.id.Button01);
    	Button b2=(Button)findViewById(R.id.Button03);
    	Button back=(Button)findViewById(R.id.back);
    	back.setOnClickListener(
    			new OnClickListener() {
					@Override
					public void onClick(View v) {
						goSubZhengCunLingQu();
					}
				}
    	);
    	double lixisum=(inputmoney+inputmoney/times)/2*times*pindu*(yearrate/12);
    	double perzhiqu=inputmoney/times;
    	String str1=String.valueOf(lixisum);
    	String str2=String.valueOf(perzhiqu);
    	b1.setText(str1);
    	b2.setText(str2);
    }
    
  /*************************************************************************/
    /************************************************************************/
   public void goZHFangDaiResult()
   {
	   last=P_FD_ZHX;
	    setContentView(R.layout.zhfangdairesult);
	   	Button back=(Button)findViewById(R.id.back);
	   	back.setOnClickListener(
	   			new OnClickListener() {
						@Override
						public void onClick(View v) {
							goZHfangdai();
						}
					}
	   	);
	   	Button detailButton=(Button)findViewById(R.id.xiangqing);
    	detailButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setContentView(R.layout.detail);
				Button back=(Button)findViewById(R.id.back);
		    	back.setOnClickListener(
		    			new OnClickListener() {
							@Override
							public void onClick(View v) {
								goZHfangdai();
							}
						}
		    	);
		    	ListView detailLv=(ListView)findViewById(R.id.ListView01);
		    	if(hkfs==1){
		    		detailList=DBUtil.getDetail("dengebenxidetail");
			    	setListAdapter(detailLv,detailList);
			    	Log.v("detaillist.size(",detailList.size()+"");
		    	}
		    	else{
		    		detailList=DBUtil.getDetail("dengebenjindetail");
			    	setListAdapter(detailLv,detailList);
			    	Log.v("detaillist.size(",detailList.size()+"");
		    	}
		    	
			}
		});
    	Button b1=(Button)findViewById(R.id.Button01);
		Button b2=(Button)findViewById(R.id.Button02);
    	Button b3=(Button)findViewById(R.id.Button03);
		Button b4=(Button)findViewById(R.id.Button04);
		double ratesum=0.0;
		double sum=0.0;
		double shangdaiRateSum=0.0;
		double jijinRateSum=0.0;
		double shangdaiSum=0.0;
		double jijinSum=0.0;
		String str1;
		String str2;
		String str3;
		String str4;
		if(hkfs==1)
		{
			DBUtil.deleteAllFromTable("dengebenxidetail");
			for(int i=1;i<=durdate;i++){
				
				DBUtil.insertDetail("dengebenxidetail", i, 0, 0, 0, 0);
			}
			shangdaiSum=jsDengeBenxi(businessinputmoney,businessyearrate,durdate);
			jijinSum=jsDengeBenxi(jijininputmoney,jijinyearrate,durdate);
			sum=shangdaiSum+jijinSum;
			ratesum=sum-businessinputmoney-jijininputmoney;
			shangdaiRateSum=shangdaiSum-businessinputmoney;
			jijinRateSum=jijinSum-jijininputmoney;
			jsZuHeBenjin(businessinputmoney,jijininputmoney,businessyearrate,jijinyearrate,durdate);
		}
		else
		{
			DBUtil.deleteAllFromTable("dengebenjindetail");
			for(int i=1;i<=durdate;i++){
				
				DBUtil.insertDetail("dengebenjindetail", i, 0, 0, 0, 0);
			}
			
			ratesum=jsDengeBenjin(businessinputmoney,businessyearrate,durdate)+jsDengeBenjin(jijininputmoney,jijinyearrate,durdate);
			sum=ratesum+businessinputmoney+jijininputmoney;
			shangdaiRateSum=jsDengeBenjin(businessinputmoney,businessyearrate,durdate);
			jijinRateSum=jsDengeBenjin(jijininputmoney,jijinyearrate,durdate);
			jsZuHeBenjin(businessinputmoney,jijininputmoney,businessyearrate,jijinyearrate,durdate);
		}
		str1=String.valueOf(shangdaiRateSum);
		str2=String.valueOf(jijinRateSum);
		str3=String.valueOf(ratesum);
		str4=String.valueOf(sum);
		b1.setText(str1);
		b2.setText(str2);
		b3.setText(str3);
		b4.setText(str4);
   }
    
  /*****************************************************************/
   public double jsDengeBenjin(double loanAmount,double loanRate,double loanPeroid){
	  double interestPayment=0.0;
	  double dengeBenjin2=loanAmount/loanPeroid;
	   for(int i=0;i<loanPeroid;i++){
		   interestPayment+=(loanAmount-i*dengeBenjin2)*(loanRate/12);//累计利息总额
		   double benjin2=dengeBenjin2;
		   double yubenjin2=loanAmount-(i+1)*benjin2;
		   double lixi2=(loanAmount-i*benjin2)*(loanRate/12);
		   double benxi2=benjin2+lixi2;
		   long benjin=(long)benjin2;
		   long yubenjin=(long)yubenjin2;
		   long lixi=(long)lixi2;
		   long benxi=(long)benxi2;
		   DBUtil.updateDetail(i+1, benxi, lixi, benjin, yubenjin,"dengebenjindetail");
	   }
	   
	return interestPayment;
   }
   public double jsTqDengeBenjin(double loanAmount,double loanRate,double loanPeroid,double interval,int start){
		  double interestPayment=0.0;
		  double dengeBenjin2=loanAmount/loanPeroid;
		   for(int i=start;i<interval;i++){
			   interestPayment+=((loanAmount-i*dengeBenjin2)*(loanRate/12));//累计利息总额
		   }
		   
		return interestPayment;
	   }
   public List<Double> jsTqDengeBenxi(double loanAmount,double loanRate,double interval,double loanPeroid){
	   List<Double> tqList=new ArrayList<Double>();
		  double benxi2=0.0;
		  double lixi2=0.0;
		  double benjin2=0.0;
		  double yubenjin2=loanAmount;
		  double lixi=0.0;
		  double benxi=0.0;
	      for(int i=0;i<interval;i++){
	    	  benxi2=loanAmount*(loanRate/12)*Math.pow(1+loanRate/12, loanPeroid)/(Math.pow(1+loanRate/12, loanPeroid)-1);
	    	  lixi2=yubenjin2*(loanRate/12);
 		      benjin2=benxi2-lixi2;
 		  yubenjin2-=benjin2;
 		  lixi+=lixi2;
 		  benxi+=benxi2;
 		  
	      }
	      tqList.clear();
	      tqList.add(benxi);
	      tqList.add(lixi);
	      tqList.add(benjin2);
	      tqList.add(yubenjin2);
	      //DBUtil.updateTq( 1, benxi, lixi, benjin2, yubenjin2,"tqdengebenxi");
	     
		return tqList;
	   }
   public double jsDengeBenxi(double loanAmount,double loanRate,double loanPeroid){
		  double sumPayment=0.0;
		  double yubenjin2=loanAmount;
	      sumPayment=loanAmount*(loanRate/12)*Math.pow(1+loanRate/12, loanPeroid)/(Math.pow(1+loanRate/12, loanPeroid)-1)*loanPeroid;
	      for(int i=0;i<loanPeroid;i++){
	    	  double benxi2=loanAmount*(loanRate/12)*Math.pow(1+loanRate/12, loanPeroid)/(Math.pow(1+loanRate/12, loanPeroid)-1);
	    	  double lixi2=yubenjin2*(loanRate/12);
    		  double benjin2=benxi2-lixi2;
    		  yubenjin2-=benjin2;
    		  long benjin=(long)benjin2;
   		      long yubenjin=(long)yubenjin2;
   		      long lixi=(long)lixi2;
   		      long benxi=(long)benxi2;
    		  DBUtil.updateDetail( i+1, benxi, lixi, benjin, yubenjin,"dengebenxidetail");
	      }
		return sumPayment;
	   }
   public void jsZuHeBenjin(double businessLoanAmount,double jijinLoanAmount,double businessLoanRate,double jijinLoanRate,double loanPeroid){
	   double businessDengeBenjin2=businessLoanAmount/loanPeroid;
		   for(int i=0;i<loanPeroid;i++){
			   double businessBenjin2=businessDengeBenjin2;
			   double businessYubenjin2=businessLoanAmount-(i+1)*businessBenjin2;
			   double businessLixi2=(businessLoanAmount-i*businessBenjin2)*(businessLoanRate/12);
			   double businessBenxi2=businessBenjin2+businessLixi2;
			   long businessBenjin=(long)businessBenjin2;
			   long businessYubenjin=(long)businessYubenjin2;
			   long businessLixi=(long)businessLixi2;
			   long businessBenxi=(long)businessBenxi2;
		double jijinDengeBenjin2=jijinLoanAmount/loanPeroid;	   
			   double jijinBenjin2=jijinDengeBenjin2;
			   double jijinYubenjin2=jijinLoanAmount-(i+1)*jijinBenjin2;
			   double jijinLixi2=(jijinLoanAmount-i*jijinBenjin2)*(jijinLoanRate/12);
			   double jijinBenxi2=jijinBenjin2+jijinLixi2;
			   long jijinBenjin=(long)jijinBenjin2;
			   long jijinYubenjin=(long)jijinYubenjin2;
			   long jijinLixi=(long)jijinLixi2;
			   long jijinBenxi=(long)jijinBenxi2;
			   
			   long benxi=businessBenxi+jijinBenxi;
			   long lixi=businessLixi+jijinLixi;
			   long benjin=businessBenjin+jijinBenjin;
			   long yubenjin=businessYubenjin+jijinYubenjin;
			   DBUtil.updateDetail(i+1, benxi, lixi, benjin, yubenjin,"dengebenjindetail");
		   }
   }
   public void jsZuHeBenxi(double businessLoanAmount,double jijinLoanAmount,double businessLoanRate,double jijinLoanRate,double loanPeroid){
	   double businessYubenjin2=businessLoanAmount;
	   double jijinYubenjin2=jijinLoanAmount;
	   for(int i=0;i<loanPeroid;i++){
	    	  double businessBenxi2=businessLoanAmount*(businessLoanRate/12)*Math.pow(1+businessLoanRate/12, loanPeroid)/(Math.pow(1+businessLoanRate/12, loanPeroid)-1);
	    	  double businessLixi2=businessYubenjin2*(businessLoanRate/12);
 		      double businessBenjin2=businessBenxi2-businessLixi2;
 		      businessYubenjin2-=businessBenjin2;
 		      long businessBenjin=(long)businessBenjin2;
		      long businessYubenjin=(long)businessYubenjin2;
		      long businessLixi=(long)businessLixi2;
		      long businessBenxi=(long)businessBenxi2;
       
			  double jijinBenxi2=jijinLoanAmount*(jijinLoanRate/12)*Math.pow(1+jijinLoanRate/12, loanPeroid)/(Math.pow(1+jijinLoanRate/12, loanPeroid)-1);
			  double jijinLixi2=jijinYubenjin2*(jijinLoanRate/12);
		 	  double jijinBenjin2=jijinBenxi2-jijinLixi2;
		 	  jijinYubenjin2-=jijinBenjin2;
		 	  long jijinBenjin=(long)jijinBenjin2;
			  long jijinYubenjin=(long)jijinYubenjin2;
			  long jijinLixi=(long)jijinLixi2;
			  long jijinBenxi=(long)jijinBenxi2;		      
		      
			  long benxi=businessBenxi+jijinBenxi;
			  long lixi=businessLixi+jijinLixi;
			  long benjin=businessBenjin+jijinBenjin;
			  long yubenjin=businessYubenjin+jijinYubenjin;
			  DBUtil.updateDetail(i+1, benxi, lixi, benjin, yubenjin,"dengebenxidetail");
		   }
   }
   /*******************************************************************/
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
				TableLayout tl=new TableLayout(CalActivity.this);
				tl.setOrientation(TableLayout.HORIZONTAL);
				
				tl.setShrinkAllColumns(false);
				
				  TableRow tr=new TableRow(CalActivity.this);
				  for(int j=0;j<5;j++)
				  { TextView tv=new TextView(CalActivity.this);
					  if(j==0){
						tv.setWidth(70);
						}	
					  else{
						tv.setWidth(100);
					}
				    tv.setText(bl.get(position)[j]);
				    tr.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
							android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
						//tv.setEllipsize(TruncateAt.END);
						tv.setTextSize(14);
						//tv.setSingleLine(true);
						tv.setGravity(Gravity.CENTER);
						tv.setTextColor(CalActivity.this.getResources().getColor(R.color.black));	
						tr.addView(tv);}
						tl.addView(tr);
						return tl;
			}         
   	};
   	lv.setAdapter(ba);	
   	}
   //日期转换格式
   public static Date StringToDate(String dateStr,String formatStr){
		DateFormat sdf=new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	} 
   //对话框方法
    @Override
    public Dialog onCreateDialog(int id)
    {
    	Dialog dialog=null;
    	switch(id)
    	{
	    	case List_DIALOG1://生成列表对话框
		    	  Builder b=new AlertDialog.Builder(this);
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.lt);//设置标题
		  		  b.setItems(
		  			 R.array.houselt_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								switch(which)
								{
									case 0:
										goBusinessFangdai();
										break;
									case 1:
										goJiJinfangdai();
										break;
									case 2:
										goZHfangdai();
										break;
								}
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG2:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.loadyears);//设置标题
		  		  b.setItems(
		  			 R.array.year_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button nian=(Button)findViewById(R.id.nian);
								nian.setText(getResources().getStringArray(R.array.year_array)[which]);
								dnian=(double)which+1;
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG3:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.loadyears);//设置标题
		  		  b.setItems(
		  			 R.array.month_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button yue=(Button)findViewById(R.id.yue);
								yue.setText(getResources().getStringArray(R.array.month_array)[which]);
								dyue=which+1;
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG4:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.strBrate);//设置标题
		  		  b.setItems(
		  			 R.array.BRate_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button shuru=(Button)findViewById(R.id.shuru);
								Button lilv=(Button)findViewById(R.id.lilv);
								shuru.setText(getResources().getStringArray(R.array.BRate_array)[which]);
								
								switch(which)
								{
								case 0:
									lilv.setText("0.0"); 
									lilv.setEnabled(true);
									break;
								case 1:
									lilv.setText("4.28"); 
									lilv.setEnabled(false);
									break;
								case 2:
									lilv.setText("6.73"); 
									lilv.setEnabled(false);
									break;
								case 3:
									lilv.setText("6.73"); 
									lilv.setEnabled(false);
									break;
								case 4:
									lilv.setText("6.73"); 
									lilv.setEnabled(false);
									break;
								case 5:
									lilv.setText("4.16");
									lilv.setEnabled(false);
									break;
								case 6:
									lilv.setText("4.75"); 
									lilv.setEnabled(false);
									break;
								case 7:
									lilv.setText("5.05"); 
									lilv.setEnabled(false);
									break;
								case 8:
									lilv.setText("6.53"); 
									lilv.setEnabled(false);
									break;
								case 9:
									lilv.setText("5.94");
									lilv.setEnabled(false);
									break;
								case 10:
									lilv.setText("6.53"); 
									lilv.setEnabled(false);
									break;
								case 11:
									lilv.setText("6.14");
									lilv.setEnabled(false);
									break;
								case 12:
									lilv.setText("6.4"); 
									lilv.setEnabled(false);
									break;
								case 13:
									lilv.setText("6.6"); 
									lilv.setEnabled(false);
									break;
								case 14:
									lilv.setText("6.8"); 
									lilv.setEnabled(false);
									break;
								case 15:
									lilv.setText("7.05"); 
									lilv.setEnabled(false);
									break;
								}
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG5:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.pt);//设置标题
		  		  b.setItems(
		  			 R.array.opaytype_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button hkmode1=(Button)findViewById(R.id.hkmode1);
								hkmode1.setText(getResources().getStringArray(R.array.opaytype_array)[which]);
								switch(which)
								{
								case 0:
									hkfs=1;break;
								case 1:
									hkfs=2;break;
								}
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG6:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.jijinrate);//设置标题
		  		  b.setItems(
		  			 R.array.CRate_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Button jijinlilv=(Button)findViewById(R.id.jijininput);
								Button lilv=(Button)findViewById(R.id.jijinlilv);
								jijinlilv.setText(getResources().getStringArray(R.array.BRate_array)[which]);
								
								switch(which)
								{
								case 0:
									lilv.setText("0.0"); 
									lilv.setEnabled(true);
									break;
								case 1:
									lilv.setText("4.05"); 
									lilv.setEnabled(false);
									break;
								case 2:
									lilv.setText("3.87"); 
									lilv.setEnabled(false);
									break;
								case 3:
									lilv.setText("4.05"); 
									lilv.setEnabled(false);
									break;
								case 4:
									lilv.setText("4.3"); 
									lilv.setEnabled(false);
									break;
								case 5:
									lilv.setText("4.5");
									lilv.setEnabled(false);
									break;
								case 6:
									lilv.setText("4.7"); 
									lilv.setEnabled(false);
									break;
								case 7:
									lilv.setText("4.9"); 
									lilv.setEnabled(false);
									break;
								}
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG7:
	    		 b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.pt);//设置标题
		  		  b.setItems(
		  			 R.array.housept_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button hkmode2=(Button)findViewById(R.id.hkmode2);
								hkmode2.setText(getResources().getStringArray(R.array.housept_array)[which]);
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG8:
	    		 b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.pt);//设置标题
		  		  b.setItems(
		  			 R.array.pt_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button hkmode3=(Button)findViewById(R.id.hkmode3);
								hkmode3.setText(getResources().getStringArray(R.array.pt_array)[which]);
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG9:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.oloadtype);//设置标题
		  		  b.setItems(
		  			 R.array.loadtype_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								switch(which)
								{
									case 0:
										goHuankuan();
										break;
									case 1:
										goHuankuan();
										break;
								}
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG10:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.opaytype);//设置标题
		  		  b.setItems(
		  			 R.array.opaytype_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button ydkmode=(Button)findViewById(R.id.ydkmode);
								ydkmode.setText(getResources().getStringArray(R.array.opaytype_array)[which]);
								hkfs=which;
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	
	    	case List_DIALOG12:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.savetime);//设置标题
		  		  b.setItems(
		  			 R.array.sl_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button savetime1=(Button)findViewById(R.id.oneyear);
								savetime1.setText(getResources().getStringArray(R.array.sl_array)[which]);
								switch(which)
								{
									case 0:
										durdate=1*12;break;
									case 1:
										durdate=3*12;break;
									case 2:
										durdate=5*12;break;
								}
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case List_DIALOG13:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.savetime);//设置标题
		  		  b.setItems(
		  			 R.array.ll_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								//Button savetime2=(Button)findViewById(R.id.threemonth);
								//savetime2.setText(getResources().getStringArray(R.array.ll_array)[which]);
								//Button lilv=(Button)findViewById(R.id.nlilv);
								switch(which)
								{
								case 0:
									durdate=(float)3/12;
									break;
								case 1:
									durdate=(float)6/12;
									break;
								case 2:
									durdate=1;
									break;
								case 3:
									durdate=2;
									break;
								case 4:
									durdate=3;
									break;
								case 5:
									durdate=5;
									break;
								}
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	
	    	case List_DIALOG14:
		  		  b=new AlertDialog.Builder(this); 
		  		  b.setIcon(R.drawable.item);//设置图标
		  		  b.setTitle(R.string.zqpd);//设置标题
		  		  b.setItems(
		  			 R.array.pd_array, 
		  			 new DialogInterface.OnClickListener()
		      		 {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//对话框关闭时的业务代码		
								Button zqpd=(Button)findViewById(R.id.everymonth);
								zqpd.setText(getResources().getStringArray(R.array.pd_array)[which]);
								switch(which)
								{
									case 0:
										pindu=1;break;
									case 1:
										pindu=3;break;
									case 2:
										pindu=6;break;
								}
							}      			
		      		 }
		  		   );
		  		  dialog=b.create();
		  		  break;
	    	case Date_DIALOG1://生成日期对话框
	    		   c=Calendar.getInstance();//获取日期对象
	    		   dialog=new DatePickerDialog(
	    		     this,
	    		     new DatePickerDialog.OnDateSetListener()
	    		     { 
						@Override
						public void onDateSet(DatePicker arg0, int arg1, int arg2,int arg3) {
							//设置日期同时要做的工作	
							c=Calendar.getInstance();//获取日期对象
							c.set(arg1, arg2, arg3);				
							//设置系统时间
							boolean b=SystemClock.setCurrentTimeMillis(c.getTimeInMillis());							
							System.out.println("date: "+b);
							Button hktime=(Button)findViewById(R.id.hktime);
							hktime.setText(""+arg1+"年"+(arg2+1)+"月");
							hkyear=arg1;
							hkmonth=arg2+1;
						}    		    	 
	    		     },
	    		     c.get(Calendar.YEAR),
	    		     c.get(Calendar.MONTH),
	    		     c.get(Calendar.DAY_OF_MONTH)    		     
	    		  );
	    	  break;
	    	case Date_DIALOG2://生成日期对话框
	    		   c=Calendar.getInstance();//获取日期对象
	    		   dialog=new DatePickerDialog(
	    		     this,
	    		     new DatePickerDialog.OnDateSetListener()
	    		     {
						@Override
						public void onDateSet(DatePicker arg0, int arg1, int arg2,int arg3) {
							//设置日期同时要做的工作	
							c=Calendar.getInstance();//获取日期对象
							c.set(arg1, arg2, arg3);				
							//设置系统时间
							boolean b=SystemClock.setCurrentTimeMillis(c.getTimeInMillis());							
							System.out.println("date: "+b);
							Button tqhktime=(Button)findViewById(R.id.tqhktime);
							tqhktime.setText(""+arg1+"年"+(arg2+1)+"月");
							tqhkyear=arg1;
							tqhkmonth=arg2+1;
						}    		    	 
	    		     },
	    		     c.get(Calendar.YEAR),
	    		     c.get(Calendar.MONTH),
	    		     c.get(Calendar.DAY_OF_MONTH)    		     
	    		  );
	    	  break;
	    	case Date_DIALOG3://生成日期对话框
	    		   c=Calendar.getInstance();//获取日期对象
	    		   dialog=new DatePickerDialog(
	    		     this,
	    		     new DatePickerDialog.OnDateSetListener()
	    		     {
						@Override
						public void onDateSet(DatePicker arg0, int arg1, int arg2,int arg3) {
							//设置日期同时要做的工作	
							c=Calendar.getInstance();//获取日期对象
							c.set(arg1, arg2, arg3);				
							//设置系统时间
							boolean b=SystemClock.setCurrentTimeMillis(c.getTimeInMillis());							
							System.out.println("date: "+b);
							Button bdate=(Button)findViewById(R.id.bdate);
							bdate.setText(""+arg1+"-"+(arg2+1)+"-"+arg3);
						}    		    	 
	    		     },
	    		     c.get(Calendar.YEAR),
	    		     c.get(Calendar.MONTH),
	    		     c.get(Calendar.DAY_OF_MONTH)    		     
	    		  );
	    	  break;
	    	case Date_DIALOG4://生成日期对话框
	    		   c=Calendar.getInstance();//获取日期对象
	    		   dialog=new DatePickerDialog(
	    		     this,
	    		     new DatePickerDialog.OnDateSetListener()
	    		     {
						@Override
						public void onDateSet(DatePicker arg0, int arg1, int arg2,int arg3) {
							//设置日期同时要做的工作	
							c=Calendar.getInstance();//获取日期对象
							c.set(arg1, arg2, arg3);				
							//设置系统时间
							boolean b=SystemClock.setCurrentTimeMillis(c.getTimeInMillis());							
							System.out.println("date: "+b);
							Button bdate2=(Button)findViewById(R.id.bdate2);
							bdate2.setText(""+arg1+"-"+(arg2+1)+"-"+arg3);
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
}
