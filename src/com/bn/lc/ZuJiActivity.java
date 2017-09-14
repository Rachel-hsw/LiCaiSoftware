package com.bn.lc;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;



public class ZuJiActivity  extends Activity implements LocationSource, AMapLocationListener {
	private MapView mMapView;
	private AMap aMap;
	OnLocationChangedListener mListener;
	AMapLocationClient mLocationClient;
	AMapLocationClientOption mLocationOption;
	boolean isFirstLoc=true;
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
            //������ʾ��λ��ť ���ҿ��Ե��
            UiSettings settings = aMap.getUiSettings();
            aMap.setLocationSource(this);//�����˶�λ�ļ���,����Ҫʵ��LocationSource�ӿ�
            // �Ƿ���ʾ��λ��ť
            settings.setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);//��ʾ��λ�㲢�ҿ��Դ�����λ,Ĭ����flase
            // ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
	        aMap.setMyLocationEnabled(true);
	       // ���ö�λ������Ϊ��λģʽ���ж�λ��������ͼ������������ת����
	       aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);}
	  //��ʼ����λ
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //���ö�λ�ص�����������Ҫʵ��AMapLocationListener�ӿڣ�AMapLocationListener�ӿ�ֻ��onLocationChanged��������ʵ�֣����ڽ����첽���صĶ�λ�����������AMapLocation���͡�
        mLocationClient.setLocationListener(this);
        //��ʼ����λ����
        mLocationOption = new AMapLocationClientOption();
        //���ö�λģʽΪHight_Accuracy�߾���ģʽ��Battery_SavingΪ�͹���ģʽ��Device_Sensors�ǽ��豸ģʽ
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //�����Ƿ񷵻ص�ַ��Ϣ��Ĭ�Ϸ��ص�ַ��Ϣ��
        mLocationOption.setNeedAddress(true);
        //�����Ƿ�ֻ��λһ��,Ĭ��Ϊfalse
        mLocationOption.setOnceLocation(false);
        //�����Ƿ�ǿ��ˢ��WIFI��Ĭ��Ϊǿ��ˢ��
        mLocationOption.setWifiActiveScan(true);
        //�����Ƿ�����ģ��λ��,Ĭ��Ϊfalse��������ģ��λ��
        mLocationOption.setMockEnable(false);
        //���ö�λ���,��λ����,Ĭ��Ϊ2000ms
        mLocationOption.setInterval(2000);
        //����λ�ͻ��˶������ö�λ����
        mLocationClient.setLocationOption(mLocationOption);
        //������λ
        mLocationClient.startLocation();
        //����ʾ����
        aMap.setMyLocationEnabled(false);// ����Ϊtrue��ʾ������ʾ��λ���㣬false��ʾ���ض�λ���㲢�����ж�λ��Ĭ����false��
     // ���� Marker ����¼�����
        aMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker m) {
				m.showInfoWindow();
				return false;
			}
		});
   
	
	 
	}
	  @Override
	  protected void onDestroy() {
	    super.onDestroy();
	    //��activityִ��onDestroyʱִ��mMapView.onDestroy()�����ٵ�ͼ
	    mMapView.onDestroy();
        //mLocationClient.stopLocation();//ֹͣ��λ
        mLocationClient.onDestroy();//���ٶ�λ�ͻ��ˡ�
        //���ٶ�λ�ͻ���֮����Ҫ���¿�����λ������Newһ��AMapLocationClient����
	  
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
	 }
	 /**
	  * ֹͣ��λ
	  */
	 @Override
	 public void deactivate() {
		 mListener = null;
	 }
	 /**
	  * ��λ�ɹ���ص�����
	  */
	 @Override
	 public void onLocationChanged(AMapLocation aMapLocation) {
		  if (aMapLocation != null) {
		        if (aMapLocation.getErrorCode() == 0) {
		            //��λ�ɹ��ص���Ϣ�����������Ϣ
		            aMapLocation.getLocationType();//��ȡ��ǰ��λ�����Դ�������綨λ���������ٷ���λ���ͱ�
		            aMapLocation.getLatitude();//��ȡγ��
		            aMapLocation.getLongitude();//��ȡ����
		            aMapLocation.getAccuracy();//��ȡ������Ϣ
		            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            Date date = new Date(aMapLocation.getTime());
		            df.format(date);//��λʱ��
		            aMapLocation.getAddress();//��ַ�����option������isNeedAddressΪfalse����û�д˽�������綨λ����л��е�ַ��Ϣ��GPS��λ�����ص�ַ��Ϣ��
		            aMapLocation.getCountry();//������Ϣ
		            aMapLocation.getProvince();//ʡ��Ϣ
		            aMapLocation.getCity();//������Ϣ
		            aMapLocation.getDistrict();//������Ϣ
		         /*   aMapLocation.getStreet();//�ֵ���Ϣ
		            aMapLocation.getStreetNum();//�ֵ����ƺ���Ϣ
*/		            aMapLocation.getCityCode();//���б���
		            aMapLocation.getAdCode();//��������

		            // ��������ñ�־λ����ʱ���϶���ͼʱ�����᲻�Ͻ���ͼ�ƶ�����ǰ��λ��
		            if (isFirstLoc) {
		                //�������ż���
		                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
		                //����ͼ�ƶ�����λ��
		                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
		                StringBuffer buffer = new StringBuffer();
		                buffer.append(aMapLocation.getCountry() + ""
		                        + aMapLocation.getProvince() + ""
		                        + aMapLocation.getCity() + ""
		                        + aMapLocation.getProvince() + ""
		                        + aMapLocation.getDistrict() + "");
		                      /*  + aMapLocation.get + ""
		                        + aMapLocation.getStreetNum());*/
		                //�����λ��ť �ܹ�����ͼ�������ƶ�����λ��
		                MarkerOptions mko = new MarkerOptions();
		        		mko.position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
		        		mko.title("������λ�ڣ�");
		        		mko.snippet(buffer.toString());
		        		/*mko.draggable(false);// �������ƶ����
		        		mko.setFlat(false);// �Ƿ�������ʾ
		        		mko.visible(true);*/
		        		/*mko.perspective(true);*/
		        		mko.icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_marker));
		        		aMap.addMarker(mko);
		                mListener.onLocationChanged(aMapLocation);
		                //��ȡ��λ��Ϣ
		                Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
		                isFirstLoc = false;
		            }
		        } else {
		            //��ʾ������ϢErrCode�Ǵ����룬errInfo�Ǵ�����Ϣ������������
		            Log.e("AmapError", "location Error, ErrCode:"
		                    + aMapLocation.getErrorCode() + ", errInfo:"
		                    + aMapLocation.getErrorInfo());
		            Toast.makeText(getApplicationContext(), "��λʧ��", Toast.LENGTH_LONG).show();
		        }
		    }
	 }
	}