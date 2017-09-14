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
	String str="使用智能语音记账功能会产生网络流量, 请按住下方语音按钮使用本功能";
	int num;
	boolean click=true;
	ImageButton ok;
	ImageButton back;
	Button speak;
	EditText et;
	TextView inputnum;
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
	        setContentView(R.layout.yuyin);
	        
	        ok=(ImageButton)findViewById(R.id.ImageButton01);
	        back=(ImageButton)findViewById(R.id.ImageButton02);
	        speak=(Button)findViewById(R.id.voiceBtnPressSpeak);
	        et=(EditText)findViewById(R.id.EditText01);
	        et.setCursorVisible(false);//设置是否显示光标
	        
	        inputnum=(TextView)findViewById(R.id.inputnum);
	        String tvnum=inputnum.getText().toString().trim();
	        num=Integer.parseInt(tvnum.substring(6,9));
	        
	        ok.setOnClickListener(this);
	        back.setOnClickListener(this);
	        speak.setOnClickListener(this);
	        et.setOnClickListener(this);
	        et.addTextChangedListener(mTextWatcher); 
	        
	        //判断当前手机是否支持语音识别功能
	        PackageManager pm = getPackageManager();
	        List<ResolveInfo> list = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
            if(list.size()!=0)
            {
	             speak.setOnClickListener(this);
            }
            else
            {
            	 speak.setEnabled(false);
            	 Toast.makeText(this, "当前语音识别设备不可用...", Toast.LENGTH_SHORT).show();
            }

	        //添加图片主要用SpannableString和ImageSpan类
	        Drawable drawable = getResources().getDrawable(R.drawable.yy_voice_warning);  
	        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());  
	        //需要处理的文本，[smile]是需要被替代的文本  
	        SpannableString spannable = new SpannableString("[warning]"+str);  
	        //要让图片替代指定的文字就要用ImageSpan  
	        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);  
	        //开始替换，注意第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）  
	       //最后一个参数类似数学中的集合,[5,12)表示从5到12，包括5但不包括12  
	        spannable.setSpan(span,0,"[warning]".length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
	        et.setTextColor(R.color.warn);
	        et.setTextSize(15);
	        et.setText(spannable);  
	       
	 }

	//实时计算编辑框中的字符个数的监听方法
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
            inputnum.setText("您还可以输入"+num+"字");
            if (num==0) {  
                Toast.makeText(YuyinActivity.this,  
                        "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)  
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
				//et.setCursorVisible(true);//设置是否显示光标
				
				//启动手机内置的语言识别功能			
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);  //设置为当前手机的语言类型
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请说话，我识别");//出现语言识别界面上面需要显示的提示
				startActivityForResult(intent,VOICE_RECOGNITION_REQUEST_CODE);
				break;
			case R.id.EditText01:
				et=(EditText)findViewById(R.id.EditText01);
				if(click)
				{
					click=false;
					et.setText("");
				}
				et.setCursorVisible(true);//设置是否显示光标
		}
	}
	


	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data)
	 {
	        //回调获取从谷歌得到的数据 
	        if(requestCode==VOICE_RECOGNITION_REQUEST_CODE && resultCode==RESULT_OK)
	        {
	            //取得语音的字符
	            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	            String resultString="";
	            resultString=results.get(0);
	            et=(EditText)findViewById(R.id.EditText01);
	            inputnum=(TextView)findViewById(R.id.inputnum);
	            String strtemp=et.getText().toString().trim();
	            et.setText(strtemp+resultString+"。");
	            strtemp=et.getText().toString().trim();
	            int size=strtemp.length();
	            num=100-size;
	            if (num==0) {  
	                Toast.makeText(YuyinActivity.this,  
	                        "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)  
	                        .show();  
	            }  
	            inputnum.setText("您还可以输入"+num+"字");
	            et.addTextChangedListener(mTextWatcher); 
	        }
	        
	        super.onActivityResult(requestCode, resultCode, data);
	 }
}
