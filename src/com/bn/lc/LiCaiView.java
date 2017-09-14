package com.bn.lc;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import static com.bn.lc.Constant.*;
public class LiCaiView extends View
{
	LiCaiActivity activity;
	Paint paint;
	Bitmap ibg;//背景
	Bitmap ibnlc;//标题
	Bitmap ijizhang;//记账
	Bitmap ishouzhi;//收支
	Bitmap ibaobiao;//报表
	Bitmap iyuyin;//语音
	Bitmap iyusuan;//预算
	Bitmap izuji;//足迹
	Bitmap ijisuan;//计算
	Bitmap itixing;//提醒
	Bitmap ishezhi;//设置
	Bitmap ijianyi;//建议
	Bitmap iexit;//退出
	/*public LiCaiView(LiCaiActivity activity) {
		super(activity);
		this.activity=activity;
		this.getHolder().addCallback(this);
		
		paint=new Paint();
		paint.setAntiAlias(true);//打开抗锯齿
		initBitmap(activity.getResources());//实例化各图片
	}*/
	public LiCaiView(LiCaiActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity=activity;
		 paint=new Paint();
		 paint.setAntiAlias(true);
		 initBitmap(activity.getResources());
	}
		public void initBitmap(Resources r)
	{
		ibg=BitmapFactory.decodeResource(r, R.drawable.back);
		ibnlc=BitmapFactory.decodeResource(r, R.drawable.back);
		ijizhang=BitmapFactory.decodeResource(r, R.drawable.jizhang);
		ishouzhi=BitmapFactory.decodeResource(r, R.drawable.shouzhi);
		ibaobiao=BitmapFactory.decodeResource(r, R.drawable.baobiao);
		iyuyin=BitmapFactory.decodeResource(r, R.drawable.yuyin);
		iyusuan=BitmapFactory.decodeResource(r, R.drawable.yusuan);
		izuji=BitmapFactory.decodeResource(r, R.drawable.zuji);
		ijisuan=BitmapFactory.decodeResource(r, R.drawable.jisuan);
		itixing=BitmapFactory.decodeResource(r, R.drawable.tixing);
		ishezhi=BitmapFactory.decodeResource(r, R.drawable.shezhi);
		ijianyi=BitmapFactory.decodeResource(r, R.drawable.jianyi);
		iexit=BitmapFactory.decodeResource(r, R.drawable.tuichu);
	}
	
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(ibg,BG_X,BG_Y,paint);//由左上角开始绘图，x,y坐标点
		canvas.drawBitmap(ibnlc, LC_X, LC_Y, paint);
		canvas.drawBitmap(ijizhang,JZ_X,JZ_Y,paint);
		canvas.drawBitmap(ishouzhi,SZ_X,SZ_Y,paint);
		canvas.drawBitmap(ibaobiao,BB_X,BB_Y,paint);
		canvas.drawBitmap(iyuyin,YY_X,YY_Y,paint);
		canvas.drawBitmap(iyusuan,YS_X,YS_Y,paint);
		canvas.drawBitmap(izuji,ZJ_X,ZJ_Y,paint);
		canvas.drawBitmap(ijisuan,JS_X,JS_Y,paint);
		canvas.drawBitmap(itixing,TX_X,TX_Y,paint);
		canvas.drawBitmap(ishezhi,SET_X,SET_Y,paint);
		canvas.drawBitmap(ijianyi,JY_X,JY_Y,paint);
		canvas.drawBitmap(iexit,EXIT_X,EXIT_Y,paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{   if(e.getAction()==MotionEvent.ACTION_DOWN){
		int x=(int)(e.getX());
		int y=(int)(e.getY());
				if(x>JZ_X && x<JZ_X+PIC_WIDTH&&y>JZ_Y&&y<JZ_Y+PIC_HEIGHT)
				{
		     	    activity.hd.sendEmptyMessage(0);
				}
				if(x>SZ_X && x<SZ_X+PIC_WIDTH&&y>SZ_Y&&y<SZ_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(1);
				}
				if(x>BB_X && x<BB_X+PIC_WIDTH&&y>BB_Y&&y<BB_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(2);
				}
				if(x>YY_X && x<YY_X+PIC_WIDTH&&y>YY_Y&&y<YY_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(3);
				}
				if(x>YS_X && x<YS_X+PIC_WIDTH&&y>YS_Y&&y<YS_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(4);
				}
				if(x>ZJ_X && x<ZJ_X+PIC_WIDTH&&y>ZJ_Y&&y<ZJ_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(5);
				}
				if(x>JS_X && x<JS_X+PIC_WIDTH&&y>JS_Y&&y<JS_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(6);
				}
				if(x>TX_X && x<TX_X+PIC_WIDTH&&y>TX_Y&&y<TX_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(7);
				}
				if(x>SET_X && x<SET_X+PIC_WIDTH&&y>SET_Y&&y<SET_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(8);
				}
				if(x>JY_X && x<JY_X+PIC_WIDTH&&y>JY_Y&&y<JY_Y+PIC_HEIGHT)
				{
					activity.hd.sendEmptyMessage(9);
				}
				if(x>EXIT_X && x<EXIT_X+PIC_WIDTH&&y>EXIT_Y&&y<EXIT_Y+PIC_HEIGHT)
				{
					System.exit(0);
				}
				return true;
		}
		return false;
	}
}