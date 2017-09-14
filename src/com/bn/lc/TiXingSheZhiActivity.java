package com.bn.lc;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import static com.bn.lc.Constant.*;
public class TiXingSheZhiActivity extends Activity{
	Dialog dialogMenu;
	public static int bellIndex;
	private Button lingshengButton;
	private RadioGroup rgMode;
	private SeekBar volumeSB;
	MediaPlayer mp;
	AudioManager am;
    public static int volume;
    private int lingshengIndex;
    private int fangshiIndex;
    int max;
    int volumeProgress;
    public static int modeIndex;
    private SharedPreferences modeSp;
    private SharedPreferences bellSp;
    private SharedPreferences volumeSp;
    SharedPreferences.Editor bellEditor;
    SharedPreferences.Editor modeEditor;
    SharedPreferences.Editor volumeEditor;
    
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
         
		
		setContentView(R.layout.tixingshezhi);
		bellSp=getSharedPreferences("actm", Context.MODE_PRIVATE); //保存提醒方式
        modeSp=getSharedPreferences("actm2", Context.MODE_PRIVATE);//保存提醒铃声
        volumeSp=getSharedPreferences("actm3", Context.MODE_PRIVATE);
        modeIndex=modeSp.getInt("modeIndex",2);
        bellIndex=bellSp.getInt("bellIndex",0);
		mp=new MediaPlayer();
		am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		volumeSB=(SeekBar)this.findViewById(R.id.SeekBar01);
        volumeProgress=volumeSp.getInt("volume",volumeSB.getMax()/2);
		volumeSB.setProgress(volumeProgress);
		switch(modeIndex){
		case 0:RadioButton rb01=(RadioButton)findViewById(R.id.RadioButton03);
    		   rb01.setChecked(true);
    		   break;
		case 1:RadioButton rb02=(RadioButton)findViewById(R.id.RadioButton04);
    	       rb02.setChecked(true);
    	       break;
		case 2:RadioButton rb03=(RadioButton)findViewById(R.id.RadioButton05);
    	       rb03.setChecked(true);
    	       break;
		}
		lingshengButton=(Button)this.findViewById(R.id.button01);
		lingshengButton.setText(BELLNAME[bellIndex]);
		lingshengButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(MENU_DIALOG);
			}});
		rgMode=(RadioGroup)this.findViewById(R.id.RadioGroup02);
		rgMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId){
				case R.id.RadioButton04: 
					fangshiIndex=1;
					break;
				case R.id.RadioButton03:
					fangshiIndex=0;
					break;
				case R.id.RadioButton05:
					fangshiIndex=2;
					break;
				}
				modeEditor=modeSp.edit();
				modeEditor.putInt("modeIndex",fangshiIndex);
				modeEditor.commit();
			}});
		
		volumeSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				max=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				volumeProgress=volumeSB.getProgress();
				volume=volumeProgress*max/100;
				am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND);
				volumeEditor=volumeSp.edit();
				volumeEditor.putInt("volume",volumeProgress);
				volumeEditor.commit();	
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			}
		});	
		Button returnButton=(Button)TiXingSheZhiActivity.this.findViewById(R.id.fanhui);
    	returnButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(TiXingSheZhiActivity.this, modeIndex+"   "+index,Toast.LENGTH_SHORT).show();
				TiXingSheZhiActivity.this.finish();
			}
		});
    	
	}	
	
	 //对话框
    @Override
    public Dialog onCreateDialog(int id)
    {
       Dialog dialog=null;
    	
    	switch(id)
    	{
    	case MENU_DIALOG:
    		AlertDialog.Builder builder = new AlertDialog.Builder(TiXingSheZhiActivity.this);
			builder.setTitle("选择提醒铃声");
			builder.setSingleChoiceItems(BELLNAME,bellIndex, new  DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(mp!=null){mp.release();}
					mp=MediaPlayer.create(TiXingSheZhiActivity.this,Constant.SONGID[which]);
					 try 
					 {
						mp.start();
					 } catch (Exception e1) 
					 {					
						e1.printStackTrace();
					 }	
					 mp.setOnCompletionListener(//歌曲播放结束事件的监听器
							 new OnCompletionListener()
								{
									@Override
									public void onCompletion(MediaPlayer arg0) 
									{//歌曲播放结束停止播放并更新界面状态
										arg0.stop();
									}			
								}				 
					 );	
					 lingshengIndex=which;
					 
				}
				
			});
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mp.stop();
					bellEditor=bellSp.edit();
					bellEditor.putInt("bellIndex",lingshengIndex);
					bellEditor.commit();
					lingshengButton.setText(Constant.BELLNAME[lingshengIndex]);
					
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mp.stop();	
				}
			});
			dialogMenu= builder.create();
			dialog=dialogMenu;
        	break;
    	}
	return dialog;
}	
}
