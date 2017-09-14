package com.bn.lc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.bn.baobiao.ZhangHuChart;
import com.bn.baobiao.KeMuChart;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class BaoBiaoActivity extends Activity implements OnClickListener{
	String msgZhichu[][]={
			{"1","医疗","2012-04-14","我的信用卡","50","唐山南湖","医疗花了12元"},
			{"2","住房","2012-04-14","我的信用卡","2000","唐山南湖","住房花了5000元"},
			{"3","教育","2012-04-14","我的活期","500","唐山南湖","教育花了12元"},
			{"4","旅行","2012-04-14","我的活期","5000","河北联合大学","旅行花了5000元"},
			{"5","吃饭","2012-04-14","我的现金","12.56","河北联合大学","吃饭花了12元"},
			{"6","教育","2012-04-14","我的现金","5000","河北联合大学","教育花了5000元"}
			};
	
	static int last;//返回的页面
	
	int cur_id=2;//当前选中的id，用于判断天，周，月的选择
	int select;//1表示选择的账户，2表示选择的科目
	
	//获取当前日期
	Calendar c=Calendar.getInstance();
	int cur_year=c.get(Calendar.YEAR);
	int cur_month=c.get(Calendar.MONTH)+1;
	int cur_day=c.get(Calendar.DAY_OF_MONTH);
	int cur_week=c. get(Calendar.DAY_OF_WEEK);//当天是星期几
	int curmonth_days;//当前月的天数
	
	String curdate;//当前日期
	String cur_last_date;//当前月最后一天的日期
	String cur_first_date;//当前月的第一天日期
	String date_day;//最后一次改变的天的日期
	String date_week;//最后一次改变的周的日期
	String date_month;//最后一次改变的月的日期
	String cur_week_first;//当前周周一的日期
	String cur_week_last;//当前周 周日的日期
	
	/*下面几个字符串是为了在获取文本框中的日期时与数据库中日期格式对应而设定*/
	String use_day;
	String use_week_first;
	String use_week_last;
	String use_month_first;
	String use_month_last;
	
	int flag1=0;//天是否被点过
	int flag2=0;//周是否被点过
	int flag3=0;//月是否被点过
	int year,month,day,days;//触发向左、向右键后的日期,days为本月天数
	
	boolean isSunday=true;//当前天是否是周日
	boolean click_list=false;//点击的是否是列表按钮
	boolean click_baobiao=false;//显示报表按钮
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//按下手机返回按钮时
    	if(keyCode==4){
    		BaoBiaoActivity.this.finish();
    		return true;
    	}
    	return false;
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        setContentView(R.layout.baobiao);
        
       // DBUtil.dropTable("zhichu");
       // DBUtil.dropTable("shouru");
        RadioButton rbzhanghu=(RadioButton)findViewById(R.id.zhanghu);
        RadioButton rbkemu=(RadioButton)findViewById(R.id.kemu);
        Button bday=(Button)findViewById(R.id.day);
        Button bweek=(Button)findViewById(R.id.week);
        Button bmonth=(Button)findViewById(R.id.month);
        Button back=(Button)findViewById(R.id.back);
        Button baobiao=(Button)findViewById(R.id.baobiao);
        Button list=(Button)findViewById(R.id.list);
        Button bbleft=(Button)findViewById(R.id.bleft);
        Button bbright=(Button)findViewById(R.id.bright);
        
        rbzhanghu.setOnClickListener(this);
        rbkemu.setOnClickListener(this);
        bday.setOnClickListener(this);
        bweek.setOnClickListener(this);
        bmonth.setOnClickListener(this);
        back.setOnClickListener(this);
        baobiao.setOnClickListener(this);
        list.setOnClickListener(this);
        bbleft.setOnClickListener(this);
        bbright.setOnClickListener(this);

        
        TextView tvdate=(TextView)findViewById(R.id.date01);
        curmonth_days=getMonthDays(cur_year,cur_month);
        curdate=cur_year+"年"+(cur_month>9?cur_month:"0"+cur_month)+"月"+(cur_day>9?cur_day:"0"+cur_day)+"日";//当前日期
        use_day=cur_year+"-"+(cur_month>9?cur_month:"0"+cur_month)+"-"+(cur_day>9?cur_day:"0"+cur_day);//当前日期
        
        
        cur_first_date=cur_year+"年"+(cur_month>9?cur_month:"0"+cur_month)+"月"+"01日";//当前月的第一天的日期
        cur_last_date=(cur_month>9?cur_month:"0"+cur_month)+"月"+curmonth_days+"日";//当前月的最后一天的日期
        use_month_first=cur_year+"-"+(cur_month>9?cur_month:"0"+cur_month)+"-"+"01";
        use_month_last=cur_year+"-"+(cur_month>9?cur_month:"0"+cur_month)+"-"+curmonth_days;
        
        cur_week_first=curdate;
        cur_week_last=curdate;
        if(cur_week==1)
        {
        	cur_week=7;
        }
        else
        {
        	cur_week=cur_week-1;
        }
        //获得当前周周一的日期
        for(int i=1;i<cur_week;i++)
        {
        	minusDay(cur_week_first);
        	cur_week_first=year+"年"+
							(month>9?month:"0"+month)+"月"+
							(day>9?day:"0"+day)+"日";
        	use_week_first=year+"-"+
							(month>9?month:"0"+month)+"-"+
							(day>9?day:"0"+day);
        }
        //获得当前周周日的日期
        for(int i=1;i<=7-cur_week;i++)
        {
        	isSunday=false;
        	addDay(cur_week_last);
        	cur_week_last=year+"年"+
        					(month>9?month:"0"+month)+"月"+
        					(day>9?day:"0"+day)+"日";
        	
        }
        
        if(!isSunday)
        {
        	cur_week_last=(month>9?month:"0"+month)+"月"+(day>9?day:"0"+day)+"日";
        	use_week_last=year+"-"+
						(month>9?month:"0"+month)+"-"+
						(day>9?day:"0"+day);
        }
        else
        {
        	cur_week_last=(cur_month>9?cur_month:"0"+cur_month)+"月"+(cur_day>9?cur_day:"0"+cur_day)+"日";
        	use_week_last=cur_year+"-"+(cur_month>9?cur_month:"0"+cur_month)+"-"+(cur_day>9?cur_day:"0"+cur_day);
        }
    	
        tvdate.setText(cur_first_date+"-"+cur_last_date);

	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.zhanghu:
				select=1;
				break;
			case R.id.kemu:
				select=2;
				break;
			case R.id.day:
				Button bbday=(Button)findViewById(R.id.day);
				Button bbweek=(Button)findViewById(R.id.week);
		        Button bbmonth=(Button)findViewById(R.id.month);
				bbday.setBackgroundResource(R.drawable.tx_top_normal_btn_bg_active);
				bbweek.setBackgroundResource(R.drawable.tx_top_normal_btn_bg);
				bbmonth.setBackgroundResource(R.drawable.tx_top_normal_btn_bg);
				//Toast.makeText(BaoBiaoActivity.this, "天被选中", Toast.LENGTH_SHORT).show();
				
				cur_id=0;
				
				if(flag1!=1)
				{
					TextView tvdate=(TextView)findViewById(R.id.date01);
			        tvdate.setText(curdate);
				}
				else
				{
					TextView tvdate=(TextView)findViewById(R.id.date01);
			        tvdate.setText(date_day);
				}
				break;
			case R.id.week:
				bbday=(Button)findViewById(R.id.day);
				bbweek=(Button)findViewById(R.id.week);
		        bbmonth=(Button)findViewById(R.id.month);
				bbday.setBackgroundResource(R.drawable.tx_top_normal_btn_bg);
				bbweek.setBackgroundResource(R.drawable.tx_top_normal_btn_bg_active);
				bbmonth.setBackgroundResource(R.drawable.tx_top_normal_btn_bg);
				//Toast.makeText(BaoBiaoActivity.this, "周被选中", Toast.LENGTH_SHORT).show();

				cur_id=1;
				
				if(flag2!=2)
				{
					TextView tvdate=(TextView)findViewById(R.id.date01);
			        tvdate.setText(cur_week_first+"-"+cur_week_last);
				}
				else
				{
					TextView tvdate=(TextView)findViewById(R.id.date01);
			        tvdate.setText(date_week);
				}
				break;
			case R.id.month:
				bbday=(Button)findViewById(R.id.day);
				bbweek=(Button)findViewById(R.id.week);
		        bbmonth=(Button)findViewById(R.id.month);
				bbday.setBackgroundResource(R.drawable.tx_top_normal_btn_bg);
				bbweek.setBackgroundResource(R.drawable.tx_top_normal_btn_bg);
				bbmonth.setBackgroundResource(R.drawable.tx_top_normal_btn_bg_active);
				//Toast.makeText(BaoBiaoActivity.this, "月被选中", Toast.LENGTH_SHORT).show();
				
				cur_id=2;
				
				if(flag3!=3)
				{
					TextView tvdate=(TextView)findViewById(R.id.date01);
					tvdate.setText(cur_first_date+"-"+cur_last_date);
				}
				else
				{
					TextView tvdate=(TextView)findViewById(R.id.date01);
			        tvdate.setText(date_month);
				}

				break;
			case R.id.bleft:
				TextView tvdate=(TextView)findViewById(R.id.date01);
				if(cur_id==0)
				{
					flag1=1;
					date_day=tvdate.getText().toString();
					minusDay(date_day);
					date_day=year+"年"+
									(month>9?month:"0"+month)+"月"+
									(day>9?day:"0"+day)+"日";
					use_day=year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);
					tvdate.setText(date_day);
				}
				else if(cur_id==1)
				{
					flag2=2;
					date_week=tvdate.getText().toString();
					
					minusDay(date_week);
					date_week=year+"年"+
									(month>9?month:"0"+month)+"月"+
									(day>9?day:"0"+day)+"日";
					//本周星期日那天的日期
					String sunday_date=(month>9?month:"0"+month)+"月"+
										(day>9?day:"0"+day)+"日";
					use_week_last=year+"-"+
								(month>9?month:"0"+month)+"-"+
								(day>9?day:"0"+day);
					for(int i=1;i<=6;i++)
					{
						minusDay(date_week);
						date_week=year+"年"+
									(month>9?month:"0"+month)+"月"+
									(day>9?day:"0"+day)+"日";
					}
					//本周星期一那天的日期
					String monday_date=date_week;
					use_week_first=year+"-"+
								(month>9?month:"0"+month)+"-"+
								(day>9?day:"0"+day);
					date_week=monday_date+"-"+sunday_date;
					
					tvdate.setText(date_week);
				}
				else if(cur_id==2)
				{
					flag3=3;
					date_month=tvdate.getText().toString();
					year=Integer.parseInt(date_month.substring(0,4));
					month=Integer.parseInt(date_month.substring(5,7));
					day=Integer.parseInt(date_month.substring(8,10));
					minusMonth(date_month);
					date_month=year+"年"+
							   (month>9?month:"0"+month)+"月"+
								"01日"+"-"+
								(month>9?month:"0"+month)+"月"+
							    days+"日";
					use_month_first=year+"-"+(month>9?month:"0"+month)+"-"+"01";
					use_month_last=year+"-"+(month>9?month:"0"+month)+"-"+days;
					tvdate.setText(date_month);
				}
				break;
			case R.id.bright:
				tvdate=(TextView)findViewById(R.id.date01);
				if(cur_id==0)
				{
					flag1=1;
					date_day=tvdate.getText().toString();
					addDay(date_day);
					date_day=year+"年"+
									(month>9?month:"0"+month)+"月"+
									(day>9?day:"0"+day)+"日";
					use_day=year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);
					tvdate.setText(date_day);
				}
				else if(cur_id==1)
				{
					flag2=2;
					date_week=tvdate.getText().toString();
					for(int i=1;i<=7;i++)
					{
						addDay(date_week);
						date_week=year+"年"+
									(month>9?month:"0"+month)+"月"+
									(day>9?day:"0"+day)+"日";
					}
					//本周星期一那天的日期
					String monday_date=date_week;
					use_week_first=year+"-"+
								(month>9?month:"0"+month)+"-"+
								(day>9?day:"0"+day);
					for(int i=1;i<=6;i++)
					{
						addDay(date_week);
						date_week=year+"年"+
								(month>9?month:"0"+month)+"月"+
								(day>9?day:"0"+day)+"日";
					}
					//本周星期日那天的日期
					String sunday_date=(month>9?month:"0"+month)+"月"+
										(day>9?day:"0"+day)+"日";
					use_week_last=year+"-"+
								(month>9?month:"0"+month)+"-"+
								(day>9?day:"0"+day);
					date_week=monday_date+"-"+sunday_date;
					tvdate.setText(date_week);
				}
				else if(cur_id==2)
				{
					flag3=3;
					date_month=tvdate.getText().toString();
					year=Integer.parseInt(date_month.substring(0,4));
					month=Integer.parseInt(date_month.substring(5,7));
					day=Integer.parseInt(date_month.substring(8,10));
					addMonth(date_month);
					date_month=year+"年"+
									(month>9?month:"0"+month)+"月"+
									"01日"+"-"+
									(month>9?month:"0"+month)+"月"+
									 days+"日";
					use_month_first=year+"-"+(month>9?month:"0"+month)+"-"+"01";
					use_month_last=year+"-"+(month>9?month:"0"+month)+"-"+days;
					tvdate.setText(date_month);
				}
				break;
			case R.id.back:
				BaoBiaoActivity.this.finish();//返回上个界面  
				break;
			case R.id.baobiao:
				click_baobiao=true;
				searchDUBtilForBaoBiaoOrList();
				break;
			case R.id.list:
				click_list=true;
				searchDUBtilForBaoBiaoOrList();
				break;
		}
	}
	
	public void searchDUBtilForBaoBiaoOrList()
	{
		Intent intent = null;
		String date01="";//文本框中前边的日期，如果选择的是天，则是整个日期
		String date02="";//文本框中后边的日期，如果选择的是天，则是整个日期
		//DBUtil.deleteZhichu("1");
		//DBUtil.deleteZhichu("2");
		//DBUtil.deleteZhichu("3");
		//DBUtil.deleteZhichu("4");
		//DBUtil.deleteZhichu("5");
		//DBUtil.deleteZhichu("6");
		//DBUtil.insertZhichu(msgZhichu);
		Toast.makeText(this, use_day, Toast.LENGTH_SHORT).show();
		if(select==1)   //按账户分类
		{
			List<String[]> zhanghuzhichu=new ArrayList<String[]>();
			switch(cur_id)
			{
				case 0:
					date01=use_day;
					date02=use_day;
			        zhanghuzhichu=DBUtil.searchZhanghuZhichu(use_day, use_day);
					break;
				case 1:
					date01=use_week_first;
					date02=use_week_last;
					zhanghuzhichu=DBUtil.searchZhanghuZhichu(use_week_first, use_week_last);
					break;
				case 2:
					date01=use_month_first;
					date02=use_month_last;
					zhanghuzhichu=DBUtil.searchZhanghuZhichu(use_month_first, use_month_last);
					break;
			}
			String outputmode[]=new String[zhanghuzhichu.size()];
	        float outputmoney[] =new float[zhanghuzhichu.size()];
	        String[] str= {""};
	        for(int i=0;i<zhanghuzhichu.size();i++)
	        {
	        	str=zhanghuzhichu.get(i);  	
	        	outputmode[i]=str[0];
	        	outputmoney[i]=Float.parseFloat(str[1]);
	        }
	      //  Toast.makeText(this, zhanghuzhichu.size()+"", Toast.LENGTH_SHORT).show();
	      //  Toast.makeText(this,use_day, Toast.LENGTH_SHORT).show();
	      //  Toast.makeText(this,str.length+"", Toast.LENGTH_SHORT).show();
	      //  Log.d("outputmode", outputmode[0]+outputmode[1]);
	     //   Log.d("outputmoney", " "+outputmoney[0]+outputmoney[1]+"");
	        if(zhanghuzhichu.size()==0)
	        {
	        	Toast.makeText(this, "没有相应支出", Toast.LENGTH_SHORT).show();
	        }
	        else if(click_list)
	        {
	        	click_list=false;
	        	TextView tv=(TextView)findViewById(R.id.date01);
	        	String tvdate=tv.getText().toString();
	        	intent= new Intent(BaoBiaoActivity.this, BaoBiaoListActivity.class);
	        	intent.putExtra("cur_id", cur_id);
	        	intent.putExtra("select", select);
	        	intent.putExtra("tvdate", tvdate);
	        	intent.putExtra("output", outputmode);
	        	intent.putExtra("outputmoney", outputmoney);
	        	intent.putExtra("date01", date01);
	        	intent.putExtra("date02", date02);
	        }
	        else if(click_baobiao)
	        {
	        	click_baobiao=false;
	        	intent=(new ZhangHuChart(outputmode,outputmoney)).execute(this);
	        }
			
		}
		else     //按科目分类
		{
			List<String[]> kemuzhichu=new ArrayList<String[]>();
			switch(cur_id)
			{
				case 0:
					date01=use_day;
					date02=use_day;
			        kemuzhichu=DBUtil.searchKeMuZhichu(use_day, use_day);
					break;
				case 1:
					date01=use_week_first;
					date02=use_week_last;
					kemuzhichu=DBUtil.searchKeMuZhichu(use_week_first, use_week_last);
					break;
				case 2:
					date01=use_month_first;
					date02=use_month_last;
					kemuzhichu=DBUtil.searchKeMuZhichu(use_month_first, use_month_last);
					break;
			}
			 	String outputsubject[]=new String[kemuzhichu.size()];
		        float outputmoney[] =new float[kemuzhichu.size()];
		        String[] str= {""};
		        for(int i=0;i<kemuzhichu.size();i++)
		        {
		        	str=kemuzhichu.get(i);  	
		        	outputsubject[i]=str[0];
		        	outputmoney[i]=Float.parseFloat(str[1]);
		        }
		      //  Toast.makeText(this, zhanghuzhichu.size()+"", Toast.LENGTH_SHORT).show();
		      //  Toast.makeText(this,use_day, Toast.LENGTH_SHORT).show();
		      //  Toast.makeText(this,str.length+"", Toast.LENGTH_SHORT).show();
		      //  Log.d("outputmode", outputmode[0]+outputmode[1]);
		     //   Log.d("outputmoney", " "+outputmoney[0]+outputmoney[1]+"");
		         if(kemuzhichu.size()==0)
		        {
		        	Toast.makeText(this, "没有相应支出", Toast.LENGTH_SHORT).show();
		        }
		        else if(click_list)
		        {
		        	click_list=false;
		        	TextView tv=(TextView)findViewById(R.id.date01);
		        	String tvdate=tv.getText().toString();
		        	intent= new Intent(BaoBiaoActivity.this, BaoBiaoListActivity.class);
		        	intent.putExtra("cur_id", cur_id);
		        	intent.putExtra("select", select);
		        	intent.putExtra("tvdate", tvdate);
		        	intent.putExtra("output", outputsubject);
		        	intent.putExtra("outputmoney", outputmoney);
		        	intent.putExtra("date01", date01);
		        	intent.putExtra("date02", date02);
		        }
		        else if(click_baobiao)
		        {
		        	click_list=false;
		        	intent=(new KeMuChart(outputsubject,outputmoney)).execute(this);
		        }
		}
		if(intent!=null)
		{
			startActivity(intent);
		}
		
	}
	
	public void goList()
	{
		
		Intent baobiaolist= new Intent(BaoBiaoActivity.this, BaoBiaoListActivity.class);
	    startActivity(baobiaolist); 
	}
	
	public void addDay(String date)
	{
		year=Integer.parseInt(date.substring(0,4));
		month=Integer.parseInt(date.substring(5,7));
		day=Integer.parseInt(date.substring(8,10));
		
		int days=getMonthDays(year,month);
		if(day==days)
		{
			day=1;
			addMonth(date);
		}
		else
		{
			day=day+1;
		}
	}
	
	public void addMonth(String date)
	{
		month=Integer.parseInt(date.substring(5,7));
		if(month==12)
		{
			month=1;
			days=31;
		    addYear(date);
		}
		else
		{
			month=month+1;
			days=getMonthDays(year,month);
		}
	}
	
	public void addYear(String date)
	{
		year=Integer.parseInt(date.substring(0,4));
		year=year+1;
	}
	
	public void minusDay(String date)
	{
		year=Integer.parseInt(date.substring(0,4));
		month=Integer.parseInt(date.substring(5,7));
		day=Integer.parseInt(date.substring(8,10));
		
		if(day==1)
		{
			int days=getMonthDays(year,(month==1?12:month-1));
			day=days;
			minusMonth(date);
		}
		else
		{
			day=day-1;
		}
	}
	
	public void minusMonth(String date)
	{
		month=Integer.parseInt(date.substring(5,7));
		if(month==1)
		{
			month=12;
			days=31;
			minusYear(date);
		}
		else
		{
			month=month-1;
			days=getMonthDays(year,month);
		}
	}
	
	public void minusYear(String date)
	{
		year=Integer.parseInt(date.substring(0,4));
		year=year-1;
	}
	
	public static int getMonthDays(int year, int month) //计算每个月的天数
	{
		switch (month)
		{
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
			{
				return 31;
			}
			case 4:
			case 6:
			case 9:
			case 11:
			{
				return 30;
			}
			case 2:
			{
				if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
					return 29;
				else
					return 28;
			}
		}
		return 0;
	}

	
	
}
