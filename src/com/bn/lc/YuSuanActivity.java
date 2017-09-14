package com.bn.lc;


import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
public class YuSuanActivity extends Activity{
    private List<String> spendSubjectal=new ArrayList<String>();
    static List<String> yusuanAmountal=new ArrayList<String>();
	static List<String> yueAmountal=new ArrayList<String>();
	private int index;
	private double sum;
	private double yuesum;
	private double zongyue;
	private double spendAmount;
	private double zongSpendAmount;
	private double singleYue;
	TextView zongyusuanTv;
	TextView zongyusuanTv2;
	Dialog dialogCalculator;
	private NumberDialog num1;
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
        setContentView(R.layout.yusuan);
        num1=new NumberDialog(YuSuanActivity.this);
    	Button returnbutton=(Button)this.findViewById(R.id.fanhui);
    	returnbutton.setOnClickListener(new View.OnClickListener() 
    	{
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			YuSuanActivity.this.finish();
    		}
    	});
    	ListView yusuanlv=(ListView)this.findViewById(R.id.ListView01);
    	
    	spendSubjectal=DBUtil.getSubjects("spendsubject");
    	yusuanAmountal=DBUtil.getAmount("yusuan");
    	yueAmountal=DBUtil.getAmount("yusuanyue");
    	setYuSuanAdapter(yusuanlv,spendSubjectal,yusuanAmountal,yueAmountal);
    	sum=DBUtil.zongYusuan("yusuan");
    	zongyusuanTv=(TextView)this.findViewById(R.id.TextView02);
    	for(int i=0;i<spendSubjectal.size();i++){
    		if(Double.parseDouble(yusuanAmountal.get(i))!=0){
    			zongSpendAmount+=DBUtil.getYuAmountAllDetails(spendSubjectal.get(i));
    		}
    	}
    	yuesum=sum-zongSpendAmount;
        zongyusuanTv2=(TextView)this.findViewById(R.id.TextView03);
    	if(sum!=0)
    	{zongyusuanTv.setText(sum+"");
    	zongyusuanTv2.setText("Óà¶î£º  "+yuesum);}
    	else
    	{zongyusuanTv.setText("");	
    	 zongyusuanTv2.setText("Ô¤ËãÎ´ÉèÖÃ");}
    	ProgressBar pb=(ProgressBar)this.findViewById(R.id.progress_horizontal);
    	pb.setMax(100);
    	if(sum!=0){
    		pb.setSecondaryProgress(100);
    	       pb.setProgress((int)(100-100*(yuesum/sum)));
    	}
    	else{
    		pb.setSecondaryProgress(0);
        	pb.setProgress(0);
    	}
    	yusuanlv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				index=arg2;
				num1.show();
				num1.addJineListener(
						new NumberListener()
						{
							@Override
							public void doAction(String numstr) {
								double yusuanamount=Double.parseDouble(numstr);
		    					DBUtil.updateYusuan(index, yusuanamount, "yusuan");   //¸üÐÂÔ¤Ëã±í
		    					spendSubjectal=DBUtil.getSubjects("spendsubject");
		    					yusuanAmountal=DBUtil.getAmount("yusuan");
		    					List<String[]> fl=DBUtil.getAmountAllDetails("zhichu");  //Ô¤Ëã¿ÆÄ¿£¬×Ü¶î
		    	    			for(int i=0;i<fl.size();i++){
		    	    			   if((fl.get(i)[0]+"").equals (spendSubjectal.get(index)+""))
		    	    			   { 
		    	    			spendAmount=DBUtil.getYuAmountAllDetails((String) spendSubjectal.get(index));;
		    	    			break;
		    	    			}
		    	    			else
		    	    			spendAmount=0;
		    	    			   }
		    	    			if(Double.parseDouble((String) yusuanAmountal.get(index))==0)
		    	    			{   zongyue=0;}
		    	    			else
		    	    			zongyue=Double.parseDouble((String) yusuanAmountal.get(index))-spendAmount;
		    	    			DBUtil.updateYusuan(index, zongyue, "yusuanyue");
		    	    	        spendSubjectal=DBUtil.getSubjects("spendsubject");
		    	    	    	yusuanAmountal=DBUtil.getAmount("yusuan");
		    	    	    	yueAmountal=DBUtil.getAmount("yusuanyue");
		    	    	    	ListView yusuanlv=(ListView)findViewById(R.id.ListView01);
		    	    	    	setYuSuanAdapter(yusuanlv,spendSubjectal,yusuanAmountal,yueAmountal);
		    			    	sum=DBUtil.zongYusuan("yusuan");
		    			    	yuesum=DBUtil.zongYusuan("yusuanyue");
		    			    	ProgressBar pb=(ProgressBar)YuSuanActivity.this.findViewById(R.id.progress_horizontal);
		    			    	pb.setMax(100);
		    			    	pb.setSecondaryProgress(100);
		    			    	if(sum!=0){	
		    			    	       pb.setProgress((int)(100-100*(yuesum/sum)));
		    			    	}
		    			    	else{
		    			    		pb.setSecondaryProgress(0);
		    			        	pb.setProgress(0);
		    			        	zongyusuanTv.setText("");
		    				    	zongyusuanTv2.setText("Ô¤ËãÎ´ÉèÖÃ");
		    			    	}
		    	    	        zongyusuanTv2=(TextView)YuSuanActivity.this.findViewById(R.id.TextView03);
		    	    	        zongyusuanTv2.setText("Óà¶î£º  "+yuesum);
		    	    	        
		    			    	
		    			    	zongyusuanTv=(TextView)YuSuanActivity.this.findViewById(R.id.TextView02);
		    			    	zongyusuanTv.setText(sum+"");
							}
							
						}
				);
			}	
        });
	}
	public void setYuSuanAdapter(final ListView lv,final List<String> cl,final List<String> dl,final List<String> dl2){
    	BaseAdapter ba=new BaseAdapter(){
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return cl.size();
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
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				LinearLayout ll=new LinearLayout(YuSuanActivity.this);
				ll.setOrientation(LinearLayout.VERTICAL);
				ll.setPadding(5,5,5,5);
				//TextView ½ø¶ÈÌõ
				LinearLayout ll1=new LinearLayout(YuSuanActivity.this);
				ll1.setOrientation(LinearLayout.HORIZONTAL);
				ll1.setPadding(5,5,5,5);
				//TextView
				LinearLayout ll11=new LinearLayout(YuSuanActivity.this);
				ll11.setLayoutParams(new LayoutParams(150,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
				TextView tv=new TextView(YuSuanActivity.this);
    			tv.setText((CharSequence) cl.get(position));
    			tv.setTextSize(18);
    			tv.setTextColor(YuSuanActivity.this.getResources().getColor(R.color.black));
    			tv.setGravity(Gravity.LEFT);
				
			  
				//Óà¶î
				LinearLayout ll2=new LinearLayout(YuSuanActivity.this);
				ll2.setOrientation(LinearLayout.HORIZONTAL);
				ll2.setPadding(5,5,5,5);
				//×Ü¶î
				LinearLayout ll21=new LinearLayout(YuSuanActivity.this);
				ll21.setLayoutParams(new LayoutParams(300,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
				TextView tv2=new TextView(YuSuanActivity.this);
    			if(Double.parseDouble((String) dl.get(position))==0)
				tv2.setText("");
    			else
    			tv2.setText(dl.get(position)+"");	
    			tv2.setTextSize(14);
    			tv2.setTextColor(YuSuanActivity.this.getResources().getColor(R.color.black));
    			tv2.setGravity(Gravity.LEFT);
				//Óà¶î
    			//////////////////////////////////////////
				LinearLayout ll22=new LinearLayout(YuSuanActivity.this);
				TextView tv3=new TextView(YuSuanActivity.this);
				tv3.setSingleLine(true);
				tv3.setEllipsize(TruncateAt.END);
				if(Double.parseDouble((String) dl.get(position))==0)
    			{   //zongyue=0;
					tv3.setText("Ô¤ËãÎ´ÉèÖÃ");
				}
				else 
				{
				spendAmount=DBUtil.getYuAmountAllDetails(cl.get(position)+"");
				singleYue=Double.parseDouble((String) dl.get(position))-spendAmount;
				tv3.setText("Óà¶î£º  "+singleYue);
				DBUtil.updateYusuan(position, singleYue, "yusuanyue");
				}
    			tv3.setTextSize(14);
    			tv3.setTextColor(YuSuanActivity.this.getResources().getColor(R.color.black));
    			//½ø¶ÈÌõ
				LinearLayout ll12=new LinearLayout(YuSuanActivity.this);
				ProgressBar pb=new ProgressBar(YuSuanActivity.this,null, android.R.attr.progressBarStyleHorizontal);
				pb.setLayoutParams(new LayoutParams(300,30));
			    pb.setMax(100);
			    if(Double.parseDouble((String) dl.get(position))!=0)
			    {pb.setSecondaryProgress(100);
			    	if(singleYue>0){
			         
			        double chengji=100*(Double.parseDouble(dl2.get(position)+""))/Double.parseDouble(dl.get(position)+"");
			        pb.setProgress(100-(int) chengji);
			   }
			        else{
						  pb.setProgressDrawable(getResources().getDrawable(R.drawable.progress));
					    }
			    }
			    else{
			    	pb.setSecondaryProgress(0);
			    	pb.setProgress(0);
			    }
    		    ll11.addView(tv);
    		    ll12.addView(pb);
    		    ll21.addView(tv2);
    		    ll22.addView(tv3);
    		    ll1.addView(ll11);
    		    ll1.addView(ll12);
    		    ll2.addView(ll21);
    		    ll2.addView(ll22);
    		    ll.addView(ll1);
    		    ll.addView(ll2);
				return ll;
			}
    	};
    	lv.setAdapter(ba);
    	ba.notifyDataSetChanged();   
    }
	
	    	}
	    	

