package com.bn.lc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class YuyinActivity extends Activity implements OnClickListener{

	LiCaiView lcv;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
	//String resultString="";
	String str="ʹ�������������˹��ܻ������������, �밴ס�·�������ťʹ�ñ�����";
	int num;
	boolean click=true;
	ImageButton ok;
	ImageButton back;
	Button speak;
	EditText et;
	TextView inputnum;
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ǿ������
	        setContentView(R.layout.yuyin);
	        
	        ok=(ImageButton)findViewById(R.id.ImageButton01);
	        back=(ImageButton)findViewById(R.id.ImageButton02);
	        speak=(Button)findViewById(R.id.voiceBtnPressSpeak);
	        et=(EditText)findViewById(R.id.EditText01);
	        et.setCursorVisible(false);//�����Ƿ���ʾ���
	        
	        inputnum=(TextView)findViewById(R.id.inputnum);
	        String tvnum=inputnum.getText().toString().trim();
	        num=Integer.parseInt(tvnum.substring(6,9));
	        
	        ok.setOnClickListener(this);
	        back.setOnClickListener(this);
	        speak.setOnClickListener(this);
	        et.setOnClickListener(this);
	        et.addTextChangedListener(mTextWatcher); 
	        
	        //�жϵ�ǰ�ֻ��Ƿ�֧������ʶ����
	        PackageManager pm = getPackageManager();
	        List<ResolveInfo> list = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
            if(list.size()!=0)
            {
	             speak.setOnClickListener(this);
            }
            else
            {
            	 speak.setEnabled(false);
            	 Toast.makeText(this, "��ǰ����ʶ���豸������...", Toast.LENGTH_SHORT).show();
            }

	        //���ͼƬ��Ҫ��SpannableString��ImageSpan��
	        Drawable drawable = getResources().getDrawable(R.drawable.yy_voice_warning);  
	        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());  
	        //��Ҫ������ı���[smile]����Ҫ��������ı�  
	        SpannableString spannable = new SpannableString("[warning]"+str);  
	        //Ҫ��ͼƬ���ָ�������־�Ҫ��ImageSpan  
	        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);  
	        //��ʼ�滻��ע���2�͵�3��������ʾ�����￪ʼ�滻�������滻������start��end��  
	       //���һ������������ѧ�еļ���,[5,12)��ʾ��5��12������5��������12  
	        spannable.setSpan(span,0,"[warning]".length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	        et.setTextColor(R.color.warn);
	        et.setTextSize(15);
	        et.setText(spannable);  
	       
	 }

	//ʵʱ����༭���е��ַ������ļ�������
	TextWatcher mTextWatcher = new TextWatcher() {  
        private CharSequence temp;  
        private int editStart;  
        private int editEnd;  
        @Override  
        public void onTextChanged(CharSequence s, int start, int before, int count) {  
            // TODO Auto-generated method stub  
             temp = s;  
        }  
         
        @Override  
        public void beforeTextChanged(CharSequence s, int start, int count,  
                int after) {  
            // TODO Auto-generated method stub   
        }  
          
        @Override  
        public void afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
            editStart=et.getSelectionStart();  
            editEnd = et.getSelectionEnd(); 
            num=100-temp.length();
            inputnum.setText("������������"+num+"��");
            if (num==0) {  
                Toast.makeText(YuyinActivity.this,  
                        "������������Ѿ����������ƣ�", Toast.LENGTH_SHORT)  
                        .show();  
                s.delete(editStart-1, editEnd);  
                int tempSelection = editStart;  
                et.setText(s);  
                et.setSelection(tempSelection);  
            }  
        }  
    };
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.ImageButton01:
				Log.d("save Button", "cheng gong");
				String content=et.getText().toString().trim();
				Intent  goJiZhang=new Intent(YuyinActivity.this,JiZhangActivity.class);
				goJiZhang.putExtra("content", content);
		        startActivity(goJiZhang); 
				break;
			case R.id.ImageButton02:
				Log.d("back Button", "cheng gong");
				Intent  backintent=new Intent(YuyinActivity.this,LiCaiActivity.class);
		        startActivity(backintent);  
		        break;
			case R.id.voiceBtnPressSpeak:
				et=(EditText)findViewById(R.id.EditText01);
				if(click)
				{
					click=false;
					et.setText("");
				}
				//et.setCursorVisible(true);//�����Ƿ���ʾ���
				
				//�����ֻ����õ�����ʶ����			
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);  //����Ϊ��ǰ�ֻ�����������
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "��˵������ʶ��");//��������ʶ�����������Ҫ��ʾ����ʾ
				startActivityForResult(intent,VOICE_RECOGNITION_REQUEST_CODE);
				break;
			case R.id.EditText01:
				et=(EditText)findViewById(R.id.EditText01);
				if(click)
				{
					click=false;
					et.setText("");
				}
				et.setCursorVisible(true);//�����Ƿ���ʾ���
		}
	}
	


	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data)
	 {
	        //�ص���ȡ�ӹȸ�õ������� 
	        if(requestCode==VOICE_RECOGNITION_REQUEST_CODE && resultCode==RESULT_OK)
	        {
	            //ȡ���������ַ�
	            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	            String resultString="";
	            resultString=results.get(0);
	            et=(EditText)findViewById(R.id.EditText01);
	            inputnum=(TextView)findViewById(R.id.inputnum);
	            String strtemp=et.getText().toString().trim();
	            et.setText(strtemp+resultString+"��");
	            strtemp=et.getText().toString().trim();
	            int size=strtemp.length();
	            num=100-size;
	            if (num==0) {  
	                Toast.makeText(YuyinActivity.this,  
	                        "������������Ѿ����������ƣ�", Toast.LENGTH_SHORT)  
	                        .show();  
	            }  
	            inputnum.setText("������������"+num+"��");
	            et.addTextChangedListener(mTextWatcher); 
	        }
	        
	        super.onActivityResult(requestCode, resultCode, data);
	 }
}
