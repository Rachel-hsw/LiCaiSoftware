package com.bn.lc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.bn.lc.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BaoBiaoListActivity extends Activity implements OnClickListener{
	
	int imgmoney=R.drawable.bb_ydg_money_cny;
	int imgoutput=R.drawable.bb_ydg_item_icon_otherexpenses;
	static int last;//返回的页面
	int cur_id=2;//当前选中的id，用于判断天，周，月的选择
	int select;//1表示选择的账户，2表示选择的科目
	String tdate;//日期文本
	String date01;//文本框中前边的日期，如果选择的是天，则是整个日期
	String date02;//文本框中后边的日期，如果选择的是天，则是整个日期
	String output[]=new String[1000];//支出科目或金额
	float money[]=new float[1000];//支出金额
	int n=0;
	
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
	boolean isDateChange=true;//判断文本框中的日期是否是intent发过来的
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//按下手机返回按钮时
    	if(keyCode==4){
    		BaoBiaoListActivity.this.finish();
    		return true;
    	}
    	return false;
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        setContentView(R.layout.baobiaolist);
        
        Intent intent=this.getIntent();
        cur_id=intent.getIntExtra("cur_id", cur_id);
        select=intent.getIntExtra("select", select);
        tdate=intent.getStringExtra("tvdate");
    	String outputtemp[]=new String[50];//支出科目或金额
    	float moneytemp[]=new float[50];//支出金额
    	outputtemp=intent.getStringArrayExtra("output");
    	moneytemp=intent.getFloatArrayExtra("outputmoney");
        date01=intent.getStringExtra("date01");
        date02=intent.getStringExtra("date02");
        n=moneytemp.length;
        //===================
        for(int i=0;i<moneytemp.length;i++)
        {
        	money[i]=moneytemp[i];
        	output[i]=outputtemp[i];
        }
        
        
        Button bday=(Button)findViewById(R.id.day);
        Button bweek=(Button)findViewById(R.id.week);
        Button bmonth=(Button)findViewById(R.id.month);
        Button back=(Button)findViewById(R.id.back);
        Button bbleft=(Button)findViewById(R.id.bleft);
        Button bbright=(Button)findViewById(R.id.bright);
        Button bkemu=(Button)findViewById(R.id.kemu);
        Button bzhanghu=(Button)findViewById(R.id.zhanghu);

        bday.setOnClickListener(this);
        bweek.setOnClickListener(this);
        bmonth.setOnClickListener(this);
        back.setOnClickListener(this);
        bbleft.setOnClickListener(this);
        bbright.setOnClickListener(this);
        bkemu.setOnClickListener(this);
        bzhanghu.setOnClickListener(this);
        
        switch(cur_id)
        {
	        case 0:
	        	Button bbday=(Button)findViewById(R.id.day);
				Button bbweek=(Button)findViewById(R.id.week);
		        Button bbmonth=(Button)findViewById(R.id.month);
				bbday.setBackgroundResource(R.drawable.bb_ydg_switch_day_on);
				bbweek.setBackgroundResource(R.drawable.bb_ydg_switch_week_off);
				bbmonth.setBackgroundResource(R.drawable.bb_ydg_switch_month_off);
	        	break;
	        case 1:
	        	bbday=(Button)findViewById(R.id.day);
				bbweek=(Button)findViewById(R.id.week);
		        bbmonth=(Button)findViewById(R.id.month);
				bbday.setBackgroundResource(R.drawable.bb_ydg_switch_day_off);
				bbweek.setBackgroundResource(R.drawable.bb_ydg_switch_week_on);
				bbmonth.setBackgroundResource(R.drawable.bb_ydg_switch_month_off);
	        	break;
	        case 2:
	        	bbday=(Button)findViewById(R.id.day);
				bbweek=(Button)findViewById(R.id.week);
		        bbmonth=(Button)findViewById(R.id.month);
				bbday.setBackgroundResource(R.drawable.bb_ydg_switch_day_off);
				bbweek.setBackgroundResource(R.drawable.bb_ydg_switch_week_off);
				bbmonth.setBackgroundResource(R.drawable.bb_ydg_switch_month_on);
	        	break;
        }
        
        if(select==1)
        {
        	Button bbkemu=(Button)findViewById(R.id.kemu);
	        Button bbzhanghu=(Button)findViewById(R.id.zhanghu);
	        bbkemu.setBackgroundResource(R.drawable.bb_ydg_kemu_off);
			bbzhanghu.setBackgroundResource(R.drawable.bb_ydg_zhanghu_on);
        }
        else 
        {
			Button bbkemu=(Button)findViewById(R.id.kemu);
	        Button bbzhanghu=(Button)findViewById(R.id.zhanghu);
	        bbkemu.setBackgroundResource(R.drawable.bb_ydg_kemu_on);
			bbzhanghu.setBackgroundResource(R.drawable.bb_ydg_zhanghu_off);
        }

        
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
        	use_week_last=year+"-"+
							(month>9?month:"0"+month)+"-"+
							(day>9?day:"0"+day);
        }
        
        if(!isSunday)
        {
        	cur_week_last=(month>9?month:"0"+month)+"月"+(day>9?day:"0"+day)+"日";
        }
        else
        {
        	cur_week_last=(cur_month>9?cur_month:"0"+cur_month)+"月"+(cur_day>9?cur_day:"0"+cur_day)+"日";
        }
    	
     //   tvdate.setText(cur_first_date+"-"+cur_last_date);
        tvdate.setText(tdate);
        
        goList();
	}

	
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.back:
			BaoBiaoListActivity.this.finish();//返回上个界面  
			break;
		case R.id.day:
			Button bbday=(Button)findViewById(R.id.day);
			Button bbweek=(Button)findViewById(R.id.week);
	        Button bbmonth=(Button)findViewById(R.id.month);
			bbday.setBackgroundResource(R.drawable.bb_ydg_switch_day_on);
			bbweek.setBackgroundResource(R.drawable.bb_ydg_switch_week_off);
			bbmonth.setBackgroundResource(R.drawable.bb_ydg_switch_month_off);
			Toast.makeText(BaoBiaoListActivity.this, "天被选中", Toast.LENGTH_SHORT).show();
			TextView tv=(TextView)findViewById(R.id.date01);
			cur_id=0;
			isDateChange=false;
			if(flag1!=1)
			{
				
		        tv.setText(curdate);
			}
			else
			{
				
		        tv.setText(date_day);
			}
			searchDUBtilForList();
			goList();
			break;
		case R.id.week:
			bbday=(Button)findViewById(R.id.day);
			bbweek=(Button)findViewById(R.id.week);
	        bbmonth=(Button)findViewById(R.id.month);
			bbday.setBackgroundResource(R.drawable.bb_ydg_switch_day_off);
			bbweek.setBackgroundResource(R.drawable.bb_ydg_switch_week_on);
			bbmonth.setBackgroundResource(R.drawable.bb_ydg_switch_month_off);
			Toast.makeText(BaoBiaoListActivity.this, "周被选中", Toast.LENGTH_SHORT).show();
		    tv=(TextView)findViewById(R.id.date01);
			cur_id=1;
			isDateChange=false;
			if(flag2!=2)
			{
				
		        tv.setText(cur_week_first+"-"+cur_week_last);
			}
			else
			{
				
		        tv.setText(date_week);
			}
			searchDUBtilForList();
			goList();
			break;
		case R.id.month:
			bbday=(Button)findViewById(R.id.day);
			bbweek=(Button)findViewById(R.id.week);
	        bbmonth=(Button)findViewById(R.id.month);
			bbday.setBackgroundResource(R.drawable.bb_ydg_switch_day_off);
			bbweek.setBackgroundResource(R.drawable.bb_ydg_switch_week_off);
			bbmonth.setBackgroundResource(R.drawable.bb_ydg_switch_month_on);
			Toast.makeText(BaoBiaoListActivity.this, "月被选中", Toast.LENGTH_SHORT).show();
			tv=(TextView)findViewById(R.id.date01);
			cur_id=2;
			isDateChange=false;
			if(flag3!=3)
			{
				tv.setText(cur_first_date+"-"+cur_last_date);
			}
			else
			{
		        tv.setText(date_month);
			}
			searchDUBtilForList();
			goList();
			break;
		case R.id.kemu:
			select=2;
			Button bbkemu=(Button)findViewById(R.id.kemu);
	        Button bbzhanghu=(Button)findViewById(R.id.zhanghu);
	        bbkemu.setBackgroundResource(R.drawable.bb_ydg_kemu_on);
			bbzhanghu.setBackgroundResource(R.drawable.bb_ydg_zhanghu_off);
			Toast.makeText(BaoBiaoListActivity.this, "当前是科目柱形图", Toast.LENGTH_SHORT).show();
		//	tv=(TextView)findViewById(R.id.date01);
			searchDUBtilForList();
			goList();
			break;
		case R.id.zhanghu:
			select=1;
			bbkemu=(Button)findViewById(R.id.kemu);
	        bbzhanghu=(Button)findViewById(R.id.zhanghu);
	        bbkemu.setBackgroundResource(R.drawable.bb_ydg_kemu_off);
			bbzhanghu.setBackgroundResource(R.drawable.bb_ydg_zhanghu_on);
			Toast.makeText(BaoBiaoListActivity.this, "当前是账户柱形图", Toast.LENGTH_SHORT).show();
			tv=(TextView)findViewById(R.id.date01);
			searchDUBtilForList();
			goList();
			break;
		case R.id.bleft:
			isDateChange=false;
			tv=(TextView)findViewById(R.id.date01);
			if(cur_id==0)
			{
				flag1=1;
				date_day=tv.getText().toString();
				minusDay(date_day);
				date_day=year+"年"+
								(month>9?month:"0"+month)+"月"+
								(day>9?day:"0"+day)+"日";
				use_day=year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);
				tv.setText(date_day);
			}
			else if(cur_id==1)
			{
				flag2=2;
				date_week=tv.getText().toString();
				
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
				tv.setText(date_week);
			}
			else if(cur_id==2)
			{
				flag3=3;
				date_month=tv.getText().toString();
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
				tv.setText(date_month);
			}
			searchDUBtilForList();
			goList();
			break;
		case R.id.bright:
			isDateChange=false;
			tv=(TextView)findViewById(R.id.date01);
			if(cur_id==0)
			{
				flag1=1;
				date_day=tv.getText().toString();
				addDay(date_day);
				date_day=year+"年"+
								(month>9?month:"0"+month)+"月"+
								(day>9?day:"0"+day)+"日";
				use_day=year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);
				tv.setText(date_day);
			}
			else if(cur_id==1)
			{
				flag2=2;
				date_week=tv.getText().toString();
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
				tv.setText(date_week);
			}
			else if(cur_id==2)
			{
				flag3=3;
				date_month=tv.getText().toString();
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
				tv.setText(date_month);
			}
			searchDUBtilForList();
			goList();
			break;
		}
		
	}
	
	public void searchDUBtilForList()
	{
		//Toast.makeText(this, use_day, Toast.LENGTH_SHORT).show();
		
		if(select==1)   //按账户分类
		{
			
			List<String[]> zhanghuzhichu=new ArrayList<String[]>();
			if(isDateChange)
			{
				Toast.makeText(this, date01+"    "+date02, Toast.LENGTH_SHORT).show();
				zhanghuzhichu=DBUtil.searchZhanghuZhichu(date01, date02);
			}
			else
			{
				switch(cur_id)
				{
					case 0:
				        zhanghuzhichu=DBUtil.searchZhanghuZhichu(use_day, use_day);
						break;
					case 1:
						zhanghuzhichu=DBUtil.searchZhanghuZhichu(use_week_first, use_week_last);
						break;
					case 2:
						zhanghuzhichu=DBUtil.searchZhanghuZhichu(use_month_first, use_month_last);
						break;
				}
			}
			if(zhanghuzhichu.size()==0)
	        {
	        	Toast.makeText(this, "没有相应支出", Toast.LENGTH_SHORT).show();
	        	n=0;
	        	goList();
	        }
			else
			{
				String[] str= {""};
		        for(int i=0;i<zhanghuzhichu.size();i++)
		        {
		        	str=zhanghuzhichu.get(i);  
		        	Toast.makeText(this, str.length+"", Toast.LENGTH_SHORT).show();
		        	output[i]=str[0];
		        	money[i]=Float.parseFloat(str[1]);
		        }
		        n=zhanghuzhichu.size();
			}
	        
	      //  Toast.makeText(this, zhanghuzhichu.size()+"", Toast.LENGTH_SHORT).show();
	      //  Toast.makeText(this,use_day, Toast.LENGTH_SHORT).show();
	      //  Toast.makeText(this,str.length+"", Toast.LENGTH_SHORT).show();
	      //  Log.d("outputmode", outputmode[0]+outputmode[1]);
	     //   Log.d("outputmoney", " "+outputmoney[0]+outputmoney[1]+"");
	        
		}
		else     //按科目分类
		{
			
			List<String[]> kemuzhichu=new ArrayList<String[]>();
			if(isDateChange)
			{
				Toast.makeText(this, date01+"    "+date02, Toast.LENGTH_SHORT).show();
				kemuzhichu=DBUtil.searchKeMuZhichu(date01, date02);
			}
			else
			{
				switch(cur_id)
				{
					case 0:
				        kemuzhichu=DBUtil.searchKeMuZhichu(use_day, use_day);
						break;
					case 1:
						kemuzhichu=DBUtil.searchKeMuZhichu(use_week_first, use_week_last);
						break;
					case 2:
						kemuzhichu=DBUtil.searchKeMuZhichu(use_month_first, use_month_last);
						break;
				}
			}
			if(kemuzhichu.size()==0)
	        {
	        	Toast.makeText(this, "没有相应支出", Toast.LENGTH_SHORT).show();
	        	n=0;
	        	goList();
	        }
			else
			{
				String[] str={""};
		        for(int i=0;i<kemuzhichu.size();i++)
		        {
		        	str=kemuzhichu.get(i);  
		        	Toast.makeText(this, str.length+"", Toast.LENGTH_SHORT).show();
		        	output[i]=str[0];
		        	money[i]=Float.parseFloat(str[1]);
		        }
		        n=kemuzhichu.size();
			}
		        
		      //  Toast.makeText(this, zhanghuzhichu.size()+"", Toast.LENGTH_SHORT).show();
		      //  Toast.makeText(this,use_day, Toast.LENGTH_SHORT).show();
		      //  Toast.makeText(this,str.length+"", Toast.LENGTH_SHORT).show();
		      //  Log.d("outputmode", outputmode[0]+outputmode[1]);
		     //   Log.d("outputmoney", " "+outputmoney[0]+outputmoney[1]+"");
		        
		}
		
	}
	
	public void goList()
	{
		 ListView lv=(ListView)findViewById(R.id.baobiaolist);
	      //为ListView准备内容适配器
	        BaseAdapter ba=new BaseAdapter()
	        {
				@Override
				public int getCount() {
					return n;//总共5个选项
				}

				@Override
				public Object getItem(int arg0) { return null; }

				@Override
				public long getItemId(int arg0) { return 0; }

				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					/*
					 * 动态生成每个下拉项对应的View，每个下拉项View由LinearLayout
					 *中包含一个ImageView及一个TextView构成
					*/
					
					//初始化LinearLayout
					LinearLayout ll=new LinearLayout(BaoBiaoListActivity.this);
					ll.setOrientation(LinearLayout.HORIZONTAL);		//设置朝向	
					ll.setPadding(5,5,5,5);//设置四周留白
					ll.setBackgroundResource(R.color.white);
					
					//初始化ImageView
					ImageView  i1=new ImageView(BaoBiaoListActivity.this);
					i1.setImageDrawable(getResources().getDrawable(imgoutput));//设置图片
					i1.setScaleType(ImageView.ScaleType.FIT_XY);
					i1.setLayoutParams(new Gallery.LayoutParams(25,25));
					i1.setPadding(0, 10, 0,2);
					ll.addView(i1);//添加到LinearLayout中
					
					//初始化TextView
					TextView tv=new TextView(BaoBiaoListActivity.this);
					tv.setText(output[arg0]);//设置内容
					tv.setTextSize(15);//设置字体大小
					tv.setTextColor(BaoBiaoListActivity.this.getResources().getColor(R.color.gray));//设置字体颜色
					tv.setPadding(5,5,120,5);//设置四周留白
				    tv.setGravity(Gravity.LEFT);
					ll.addView(tv);//添加到LinearLayout中	
					
					ImageView  i2=new ImageView(BaoBiaoListActivity.this);
					i2.setImageDrawable(getResources().getDrawable(imgmoney));//设置图片
					i2.setScaleType(ImageView.ScaleType.FIT_XY);
					i2.setLayoutParams(new Gallery.LayoutParams(25,25));
					i1.setPadding(0, 8, 0,0);
					ll.addView(i2);//添加到LinearLayout中
					
					TextView tvmoney=new TextView(BaoBiaoListActivity.this);
					tvmoney.setText(": "+money[arg0]);//设置内容
					tvmoney.setTextSize(15);//设置字体大小
					tvmoney.setTextColor(BaoBiaoListActivity.this.getResources().getColor(R.color.gray));//设置字体颜色
					tvmoney.setPadding(0,5,5,5);//设置四周留白
				    tvmoney.setGravity(Gravity.LEFT);
					ll.addView(tvmoney);//添加到LinearLayout中
					
					return ll;
				}        	
	        };
	        
	        lv.setAdapter(ba);//为ListView设置内容适配器
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


