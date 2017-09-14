package com.bn.lc;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.MyLocationStyle;



public class Zui0Activity  extends Activity implements LocationSource, AMapLocationListener {
	private MapView mMapView;
	private AMap aMap;
	OnLocationChangedListener mListener;
	AMapLocationClient mlocationClient;
	AMapLocationClientOption mLocationOption;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.mapvie_gaode);
		  
	        //ȫ����ʾ�����ֻ�״̬�����ɼ�
	/*        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		    getWindow().setFlags
	        (
	     
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN
	        );
		 mMapView = (MapView) findViewById(R.id.myamap_view);
		 //��activityִ��onCreateʱִ��mMapView.onCreate(savedInstanceState)��������ͼ
	    mMapView.onCreate(savedInstanceState);
	    
	    if (aMap == null) {
	        aMap = mMapView.getMap();        
	    }
	 // ���ö�λ����
	    aMap.setLocationSource(this);
	    // ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
	    aMap.setMyLocationEnabled(true);
	    // ���ö�λ������Ϊ��λģʽ���ж�λ��������ͼ������������ת����
	    aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	
	 
	}
	  @Override
	  protected void onDestroy() {
	    super.onDestroy();
	    //��activityִ��onDestroyʱִ��mMapView.onDestroy()�����ٵ�ͼ
	    mMapView.onDestroy();	    
	    if(null != mlocationClient){
	        mlocationClient.onDestroy();
	    }
	  
	  }
	 @Override
	 protected void onResume() {
	    super.onResume();
	    //��activityִ��onResumeʱִ��mMapView.onResume ()�����»��Ƽ��ص�ͼ
	    mMapView.onResume();
	    }
	 @Override
	 protected void onPause() {
	    super.onPause();
	    //��activityִ��onPauseʱִ��mMapView.onPause ()����ͣ��ͼ�Ļ���
	    mMapView.onPause();
	    }
	 @Override
	 protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    //��activityִ��onSaveInstanceStateʱִ��mMapView.onSaveInstanceState (outState)�������ͼ��ǰ��״̬
	    mMapView.onSaveInstanceState(outState);
	  }
/**
	  * ���λ
	  */
	 @Override
	 public void activate(OnLocationChangedListener listener) {
	     mListener = listener;
	     if (mlocationClient == null) {
	         //��ʼ����λ
	         mlocationClient = new AMapLocationClient(this);
	         //��ʼ����λ����
	         mLocationOption = new AMapLocationClientOption();
	         //���ö�λ�ص�����
	         mlocationClient.setLocationListener(this);
	         //����Ϊ�߾��ȶ�λģʽ
	         mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
	         //���ö�λ����
	         mlocationClient.setLocationOption(mLocationOption);
	         // �˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����Ϊ�˼��ٵ������Ļ������������ģ�
	         // ע�����ú��ʵĶ�λʱ��ļ������С���֧��Ϊ2000ms���������ں���ʱ�����stopLocation()������ȡ����λ����
	         // �ڶ�λ�������ں��ʵ��������ڵ���onDestroy()����
	         // �ڵ��ζ�λ����£���λ���۳ɹ���񣬶��������stopLocation()�����Ƴ����󣬶�λsdk�ڲ����Ƴ�
	         mlocationClient.startLocation();//������λ
	     }
	 }
	 /**
	  * ֹͣ��λ
	  */
	 @Override
	 public void deactivate() {
	     mListener = null;
	     if (mlocationClient != null) {
	         mlocationClient.stopLocation();
	         mlocationClient.onDestroy();
	     }
	     mlocationClient = null;
	 }
	 /**
	  ��λ�ɹ���ص�����
	   **/
	 @Override
	 public void onLocationChanged(AMapLocation amapLocation) {
	     if (mListener != null&&amapLocation != null) {
	         if (amapLocation != null  &&amapLocation.getErrorCode() == 0) {
	             mListener.onLocationChanged(amapLocation);// ��ʾϵͳС����
	         } else {
	             String errText = "��λʧ��," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
	             Log.e("AmapErr",errText);
	         }
	     }
	 }
	}