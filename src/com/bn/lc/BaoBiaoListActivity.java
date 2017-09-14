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
	static int last;//���ص�ҳ��
	int cur_id=2;//��ǰѡ�е�id�������ж��죬�ܣ��µ�ѡ��
	int select;//1��ʾѡ����˻���2��ʾѡ��Ŀ�Ŀ
	String tdate;//�����ı�
	String date01;//�ı�����ǰ�ߵ����ڣ����ѡ������죬������������
	String date02;//�ı����к�ߵ����ڣ����ѡ������죬������������
	String output[]=new String[1000];//֧����Ŀ����
	float money[]=new float[1000];//֧�����
	int n=0;
	
	//��ȡ��ǰ����
	Calendar c=Calendar.getInstance();
	int cur_year=c.get(Calendar.YEAR);
	int cur_month=c.get(Calendar.MONTH)+1;
	int cur_day=c.get(Calendar.DAY_OF_MONTH);
	int cur_week=c. get(Calendar.DAY_OF_WEEK);//���������ڼ�
	int curmonth_days;//��ǰ�µ�����
	
	String curdate;//��ǰ����
	String cur_last_date;//��ǰ�����һ�������
	String cur_first_date;//��ǰ�µĵ�һ������
	String date_day;//���һ�θı���������
	String date_week;//���һ�θı���ܵ�����
	String date_month;//���һ�θı���µ�����
	String cur_week_first;//��ǰ����һ������
	String cur_week_last;//��ǰ�� ���յ�����
	
	/*���漸���ַ�����Ϊ���ڻ�ȡ�ı����е�����ʱ�����ݿ������ڸ�ʽ��Ӧ���趨*/
	String use_day;
	String use_week_first;
	String use_week_last;
	String use_month_first;
	String use_month_last;
	
	int flag1=0;//���Ƿ񱻵��
	int flag2=0;//���Ƿ񱻵��
	int flag3=0;//���Ƿ񱻵��
	int year,month,day,days;//�����������Ҽ��������,daysΪ��������
	
	boolean isSunday=true;//��ǰ���Ƿ�������
	boolean isDateChange=true;//�ж��ı����е������Ƿ���intent��������
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//�����ֻ����ذ�ťʱ
    	if(keyCode==4){
    		BaoBiaoListActivity.this.finish();
    		return true;
    	}
    	return false;
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags
        (
        		WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ǿ������
        setContentView(R.layout.baobiaolist);
        
        Intent intent=this.getIntent();
        cur_id=intent.getIntExtra("cur_id", cur_id);
        select=intent.getIntExtra("select", select);
        tdate=intent.getStringExtra("tvdate");
    	String outputtemp[]=new String[50];//֧����Ŀ����
    	float moneytemp[]=new float[50];//֧�����
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
        curdate=cur_year+"��"+(cur_month>9?cur_month:"0"+cur_month)+"��"+(cur_day>9?cur_day:"0"+cur_day)+"��";//��ǰ����
        use_day=cur_year+"-"+(cur_month>9?cur_month:"0"+cur_month)+"-"+(cur_day>9?cur_day:"0"+cur_day);//��ǰ����
        
        
        cur_first_date=cur_year+"��"+(cur_month>9?cur_month:"0"+cur_month)+"��"+"01��";//��ǰ�µĵ�һ�������
        cur_last_date=(cur_month>9?cur_month:"0"+cur_month)+"��"+curmonth_days+"��";//��ǰ�µ����һ�������
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
        //��õ�ǰ����һ������
        for(int i=1;i<cur_week;i++)
        {
        	minusDay(cur_week_first);
        	cur_week_first=year+"��"+
							(month>9?month:"0"+month)+"��"+
							(day>9?day:"0"+day)+"��";
        	use_week_first=year+"-"+
							(month>9?month:"0"+month)+"-"+
							(day>9?day:"0"+day);
        }
        //��õ�ǰ�����յ�����
        for(int i=1;i<=7-cur_week;i++)
        {
        	isSunday=false;
        	addDay(cur_week_last);
        	cur_week_last=year+"��"+
        					(month>9?month:"0"+month)+"��"+
        					(day>9?day:"0"+day)+"��";
        	use_week_last=year+"-"+
							(month>9?month:"0"+month)+"-"+
							(day>9?day:"0"+day);
        }
        
        if(!isSunday)
        {
        	cur_week_last=(month>9?month:"0"+month)+"��"+(day>9?day:"0"+day)+"��";
        }
        else
        {
        	cur_week_last=(cur_month>9?cur_month:"0"+cur_month)+"��"+(cur_day>9?cur_day:"0"+cur_day)+"��";
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
			BaoBiaoListActivity.this.finish();//�����ϸ�����  
			break;
		case R.id.day:
			Button bbday=(Button)findViewById(R.id.day);
			Button bbweek=(Button)findViewById(R.id.week);
	        Button bbmonth=(Button)findViewById(R.id.month);
			bbday.setBackgroundResource(R.drawable.bb_ydg_switch_day_on);
			bbweek.setBackgroundResource(R.drawable.bb_ydg_switch_week_off);
			bbmonth.setBackgroundResource(R.drawable.bb_ydg_switch_month_off);
			Toast.makeText(BaoBiaoListActivity.this, "�챻ѡ��", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(BaoBiaoListActivity.this, "�ܱ�ѡ��", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(BaoBiaoListActivity.this, "�±�ѡ��", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(BaoBiaoListActivity.this, "��ǰ�ǿ�Ŀ����ͼ", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(BaoBiaoListActivity.this, "��ǰ���˻�����ͼ", Toast.LENGTH_SHORT).show();
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
				date_day=year+"��"+
								(month>9?month:"0"+month)+"��"+
								(day>9?day:"0"+day)+"��";
				use_day=year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);
				tv.setText(date_day);
			}
			else if(cur_id==1)
			{
				flag2=2;
				date_week=tv.getText().toString();
				
				minusDay(date_week);
				date_week=year+"��"+
								(month>9?month:"0"+month)+"��"+
								(day>9?day:"0"+day)+"��";
				//�������������������
				String sunday_date=(month>9?month:"0"+month)+"��"+
									(day>9?day:"0"+day)+"��";
				use_week_last=year+"-"+
							(month>9?month:"0"+month)+"-"+
							(day>9?day:"0"+day);
				for(int i=1;i<=6;i++)
				{
					minusDay(date_week);
					date_week=year+"��"+
								(month>9?month:"0"+month)+"��"+
								(day>9?day:"0"+day)+"��";
				}
				//��������һ���������
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
				date_month=year+"��"+
						   (month>9?month:"0"+month)+"��"+
							"01��"+"-"+
							(month>9?month:"0"+month)+"��"+
						    days+"��";
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
				date_day=year+"��"+
								(month>9?month:"0"+month)+"��"+
								(day>9?day:"0"+day)+"��";
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
					date_week=year+"��"+
								(month>9?month:"0"+month)+"��"+
								(day>9?day:"0"+day)+"��";
				}
				//��������һ���������
				String monday_date=date_week;
				use_week_first=year+"-"+
							(month>9?month:"0"+month)+"-"+
							(day>9?day:"0"+day);
				for(int i=1;i<=6;i++)
				{
					addDay(date_week);
					date_week=year+"��"+
							(month>9?month:"0"+month)+"��"+
							(day>9?day:"0"+day)+"��";
				}
				//�������������������
				String sunday_date=(month>9?month:"0"+month)+"��"+
									(day>9?day:"0"+day)+"��";
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
				date_month=year+"��"+
								(month>9?month:"0"+month)+"��"+
								"01��"+"-"+
								(month>9?month:"0"+month)+"��"+
								 days+"��";
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
		
		if(select==1)   //���˻�����
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
	        	Toast.makeText(this, "û����Ӧ֧��", Toast.LENGTH_SHORT).show();
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
		else     //����Ŀ����
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
	        	Toast.makeText(this, "û����Ӧ֧��", Toast.LENGTH_SHORT).show();
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
	      //ΪListView׼������������
	        BaseAdapter ba=new BaseAdapter()
	        {
				@Override
				public int getCount() {
					return n;//�ܹ�5��ѡ��
				}

				@Override
				public Object getItem(int arg0) { return null; }

				@Override
				public long getItemId(int arg0) { return 0; }

				@Override
				public View getView(int arg0, View arg1, ViewGroup arg2) {
					/*
					 * ��̬����ÿ���������Ӧ��View��ÿ��������View��LinearLayout
					 *�а���һ��ImageView��һ��TextView����
					*/
					
					//��ʼ��LinearLayout
					LinearLayout ll=new LinearLayout(BaoBiaoListActivity.this);
					ll.setOrientation(LinearLayout.HORIZONTAL);		//���ó���	
					ll.setPadding(5,5,5,5);//������������
					ll.setBackgroundResource(R.color.white);
					
					//��ʼ��ImageView
					ImageView  i1=new ImageView(BaoBiaoListActivity.this);
					i1.setImageDrawable(getResources().getDrawable(imgoutput));//����ͼƬ
					i1.setScaleType(ImageView.ScaleType.FIT_XY);
					i1.setLayoutParams(new Gallery.LayoutParams(25,25));
					i1.setPadding(0, 10, 0,2);
					ll.addView(i1);//��ӵ�LinearLayout��
					
					//��ʼ��TextView
					TextView tv=new TextView(BaoBiaoListActivity.this);
					tv.setText(output[arg0]);//��������
					tv.setTextSize(15);//���������С
					tv.setTextColor(BaoBiaoListActivity.this.getResources().getColor(R.color.gray));//����������ɫ
					tv.setPadding(5,5,120,5);//������������
				    tv.setGravity(Gravity.LEFT);
					ll.addView(tv);//��ӵ�LinearLayout��	
					
					ImageView  i2=new ImageView(BaoBiaoListActivity.this);
					i2.setImageDrawable(getResources().getDrawable(imgmoney));//����ͼƬ
					i2.setScaleType(ImageView.ScaleType.FIT_XY);
					i2.setLayoutParams(new Gallery.LayoutParams(25,25));
					i1.setPadding(0, 8, 0,0);
					ll.addView(i2);//��ӵ�LinearLayout��
					
					TextView tvmoney=new TextView(BaoBiaoListActivity.this);
					tvmoney.setText(": "+money[arg0]);//��������
					tvmoney.setTextSize(15);//���������С
					tvmoney.setTextColor(BaoBiaoListActivity.this.getResources().getColor(R.color.gray));//����������ɫ
					tvmoney.setPadding(0,5,5,5);//������������
				    tvmoney.setGravity(Gravity.LEFT);
					ll.addView(tvmoney);//��ӵ�LinearLayout��
					
					return ll;
				}        	
	        };
	        
	        lv.setAdapter(ba);//ΪListView��������������
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
	
	public static int getMonthDays(int year, int month) //����ÿ���µ�����
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


