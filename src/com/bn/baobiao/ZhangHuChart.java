package com.bn.baobiao;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.Paint.Align;

public class ZhangHuChart extends AbstractDemoChart{
    String[] outputmode=new String[50];//支出方式
    float[] outputmoney=new float[50];//支出金额
    int n=0;
    public ZhangHuChart(String[] outputmode,float[] outputmoney)
    {
    	this.outputmode=outputmode;
    	this.outputmoney=outputmoney;
    }
    
	@Override
	 public Intent execute(Context context) {
	    String title="账户";
	    n=outputmode.length;
	    int[] colors = new int[]{ Color.BLUE};
	    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
	    setChartSettings(renderer, "个人支出报表", "支付方式", "金额", 0.5,
	        3, 0, 5000, Color.GRAY, Color.LTGRAY);
	    renderer.setXLabels(5);
	    renderer.setYLabels(10);
	    for(int i=0;i<n;i++)
	    {
	    	renderer.addTextLabel(i+1, outputmode[i]);
	    }
	    renderer.setDisplayChartValues(true);
	    renderer.setChartValuesTextSize(12);
	    renderer.setXLabelsAlign(Align.CENTER);
	    renderer.setYLabelsAlign(Align.LEFT);
	    // renderer.setPanEnabled(false);
	    // renderer.setZoomEnabled(false);
	    renderer.setZoomRate(1.1f);
	    renderer.setBarSpacing(0.5);
	    renderer.setXLabels(0);//设置X轴显示的刻度标签的个数
	    return ChartFactory.getBarChartIntent(context, buildBarDataset(title, outputmoney), renderer,
	        Type.STACKED);
	  }

}
