package com.bn.lc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
public class TiXingActivity extends Activity{
    private List<String[]> al=new ArrayList<String[]>();
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
	       setContentView(R.layout.tixing);
	       ListView tixingLv=(ListView)findViewById(R.id.ListView01);
		   al=DBUtil.getTiXing();
		   setAdapter(tixingLv,al);
	       Button returnButton=(Button)findViewById(R.id.fanhui);
	       returnButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TiXingActivity.this.finish();
			}
		});
	      Button addButton=(Button)this.findViewById(R.id.add);
	      addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(TiXingActivity.this, addTiXingActivity.class);
				startActivity(intent);
			}
		});   
	      
	 }
	public void setAdapter(final ListView lv,final List<String[]> al){
    	final BaseAdapter ba=new BaseAdapter(){
    		@Override
    		public int getCount() {
    			// TODO Auto-generated method stub
    			return al.size();
    		}
    		@Override
    		public Object getItem(int position) {
    			// TODO Auto-generated method stub
    			return al.get(position);
    		}
    		@Override
    		public long getItemId(int position) {
    			// TODO Auto-generated method stub
    			return position;
    		}
    		@Override
    		public View getView(final int position,View convertView, ViewGroup parent) {
    			LinearLayout ll=new LinearLayout(TiXingActivity.this);
    			ll.setOrientation(LinearLayout.VERTICAL);
    			ll.setPadding(5,5,5,5);
    			ll.setGravity(Gravity.CENTER);
    			LinearLayout ll1=new LinearLayout(TiXingActivity.this);
    			ll1.setOrientation(LinearLayout.HORIZONTAL);
    			ll1.setPadding(5,5,5,5);
    			ll1.setGravity(Gravity.CENTER);
    			LinearLayout ll2=new LinearLayout(TiXingActivity.this);
    			ll2.setOrientation(LinearLayout.HORIZONTAL);
    			ll2.setGravity(Gravity.CENTER);
    			ImageView iv=new ImageView(TiXingActivity.this);
    			iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.clock));
    			TextView tv=new TextView(TiXingActivity.this);
    			tv.setText((CharSequence) al.get(position)[0]);
    			tv.setTextSize(18);
    			tv.setTextColor(TiXingActivity.this.getResources().getColor(R.color.black));
    			tv.setGravity(Gravity.LEFT);
    		    tv.setLayoutParams(new LayoutParams(120,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    		    tv.setEllipsize(TruncateAt.END);
    		    tv.setSingleLine();
    			//日期
    			TextView tv2=new TextView(TiXingActivity.this);
    			tv2.setText((CharSequence) al.get(position)[1]);
    			tv2.setTextSize(18);
    			tv2.setTextColor(TiXingActivity.this.getResources().getColor(R.color.black));
    			tv2.setGravity(Gravity.LEFT);
    			tv2.setPadding(80,0,0, 0);
    			//周期
    			TextView tv3=new TextView(TiXingActivity.this);
    			tv3.setText(al.get(position)[2]+"   "+al.get(position)[3]+" ");
    			tv3.setTextSize(18);
    			tv3.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
    			tv3.setGravity(Gravity.RIGHT);
    			tv3.setTextColor(TiXingActivity.this.getResources().getColor(R.color.blue));
    		    tv3.setSingleLine();
                ll1.addView(iv);
    			ll1.addView(tv);
    			ll1.addView(tv2);
    			ll2.addView(tv3);
    			ll.addView(ll1);
    			ll.addView(ll2);
    			return ll;
    		}   	
        };
        lv.setAdapter(ba);  
        ba.notifyDataSetChanged();
    }
	
	 
}
