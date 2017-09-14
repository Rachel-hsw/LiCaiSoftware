package com.bn.lc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
public class AlarmActivity extends Activity
{   TiXingSheZhiActivity activity;
	MediaPlayer mp;
	private int index;
	private int modeIndex;
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {Intent i=getIntent();
   index=i.getIntExtra("bellIndex",0);
   modeIndex=i.getIntExtra("modeIndex", 2);
  //Toast.makeText(AlarmActivity.this, modeIndex+"   "+index,Toast.LENGTH_SHORT).show();
    super.onCreate(savedInstanceState);
    mp=new MediaPlayer();
    mp=MediaPlayer.create(AlarmActivity.this,Constant.SONGID[index]);
    final Vibrator mVibrator= ( Vibrator )getApplication().getSystemService (Service.VIBRATOR_SERVICE); 
    Intent intent=getIntent();
    String title=intent.getStringExtra("titleContent");
    new AlertDialog.Builder(AlarmActivity.this)
        .setIcon(R.drawable.clock)
        .setTitle("ƒ÷÷”œÏ¡À!!")
        .setMessage(title)
        .setPositiveButton("πÿµÙÀ¸",
         new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int whichButton)
          {
            AlarmActivity.this.finish();
            mp.stop();
            mVibrator.cancel();
          }
        })
        .show();
    switch(modeIndex){
    case 0:
    		mp.start();
    		break;
    case 1:	
    	mVibrator.vibrate( new long[]{100,10,100,1000},0);
    	break;
    case 2:
    	mp.start();
    	mVibrator.vibrate( new long[]{100,10,100,1000},0);
    	break;	
    }
  } 
}
