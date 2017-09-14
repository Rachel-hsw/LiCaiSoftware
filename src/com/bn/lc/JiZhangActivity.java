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
    private String isubject;     //��Ŀ
    private String imode;        //��ʽ
    private String idate;        //����
    private double iamount;      //���
    private String inote;        //��ע
    private String iplace;       //�ص�
    private String szStr;       //�ճ����롢�ճ�֧��
    
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
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ǿ������
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
		//�ճ����롢�ճ�֧��
        Spinner shouzhiSp=(Spinner)this.findViewById(R.id.Spinner01);
        setSpAdapter(shouzhiSp,szIds.length,szIds);
        shouzhiSp.setOnItemSelectedListener(new ShouZhiSpListener()); //��Ӽ���
        //��Ŀ
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
        //��ʽ
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
        
      //����������
        Button returnButton=(Button)this.findViewById(R.id.fanhui);
        returnButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JiZhangActivity.this.finish();
			}
		});
        //����
        SimpleDateFormat   sDateFormat=new SimpleDateFormat("  yyyy-MM-dd"); //���ڸ�ʽ
        String dateStr=sDateFormat.format(new java.util.Date()); 
        dateEdit.setText(dateStr); 
        dateEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG);    //����ϵͳ���ڶԻ���
			}
		});
        //����
        Button saveButton=(Button)this.findViewById(R.id.Button01);
        saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    String amount=amountEdit.getText().toString().trim();  //���
			    iamount=Double.parseDouble(amount);
				inote=noteEdit.getText().toString();    //��ע
				idate=dateEdit.getText().toString().trim();
				iplace=placeEdit.getText().toString();
				if(amount.equals("")){             //���Ϊ�գ���ʾ����
					Toast.makeText(JiZhangActivity.this, "��������", Toast.LENGTH_SHORT).show();
				}
				else{
					if(szStr.equals("�ճ�����")){
						DBUtil.insertZhangWu("shouru",isubject,idate,imode,iamount,iplace,inote);	
					}
					else{
						DBUtil.insertZhangWu("zhichu",isubject,idate,imode,iamount,iplace,inote);
					}
					Toast.makeText(JiZhangActivity.this,"����ɹ�",Toast.LENGTH_SHORT).show();
					JiZhangActivity.this.finish();	//����ɹ�������������
				}
		}
		}); 
        //�ټ�һ��
        Button againButton=(Button)this.findViewById(R.id.Button02);
        againButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
			    String amount=amountEdit.getText().toString().trim();  //���
			    iamount=Double.parseDouble(amount);
				inote=noteEdit.getText().toString();    //��ע
				idate=dateEdit.getText().toString().trim();
				iplace=placeEdit.getText().toString();
				if(amount.equals("")){
					Toast.makeText(JiZhangActivity.this, "��������", Toast.LENGTH_SHORT).show();
				}
				else{
					if(szStr.equals("�ճ�����")){
						DBUtil.insertZhangWu("shouru",isubject,idate,imode,iamount,iplace,inote);	
					}
					else{
						DBUtil.insertZhangWu("zhichu",isubject,idate,imode,iamount,iplace,inote);
					}
				     amountEdit.setText("");
				     placeEdit.setText("");
				     noteEdit.setText("");
					Toast.makeText(JiZhangActivity.this,"����ɹ�,��������",Toast.LENGTH_SHORT).show();
				}	
			}
		});
	}
	 public Dialog onCreateDialog(int id)
	    {
	       Dialog dialog=null;
	    	
	    	switch(id)
	    	{
	    	 case DATE_DIALOG://�������ڶԻ���
	    	   c=Calendar.getInstance();//��ȡ���ڶ���
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
	//spinner������(������)
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
    //spinner���������ӱ�
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
    //��Ŀ�����ճ����롢�ճ���֧�ı仯���仯
    class ShouZhiSpListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			LinearLayout ll=(LinearLayout)arg1;
			TextView tvn=(TextView)ll.getChildAt(0);
			szStr=tvn.getText().toString();
			if(szStr.equals("�ճ�����"))
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
