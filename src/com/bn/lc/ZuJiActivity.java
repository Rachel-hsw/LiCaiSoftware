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
		  
	        //全屏显示，即手机状态栏不可见
	/*        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		    getWindow().setFlags
	        (
	     
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN
	        );
		 mMapView = (MapView) findViewById(R.id.myamap_view);
		 //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
	    mMapView.onCreate(savedInstanceState);
	    
	    if (aMap == null) {
            aMap = mMapView.getMap();
            //设置显示定位按钮 并且可以点击
            UiSettings settings = aMap.getUiSettings();
            aMap.setLocationSource(this);//设置了定位的监听,这里要实现LocationSource接口
            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	        aMap.setMyLocationEnabled(true);
	       // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
	       aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);}
	  //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听，这里要实现AMapLocationListener接口，AMapLocationListener接口只有onLocationChanged方法可以实现，用于接收异步返回的定位结果，参数是AMapLocation类型。
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        //不显示蓝点
        aMap.setMyLocationEnabled(false);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
     // 定义 Marker 点击事件监听
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
	    //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
	    mMapView.onDestroy();
        //mLocationClient.stopLocation();//停止定位
        mLocationClient.onDestroy();//销毁定位客户端。
        //销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象。
	  
	  }
	 @Override
	 protected void onResume() {
	    super.onResume();
	    //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
	    mMapView.onResume();
	    }
	 @Override
	 protected void onPause() {
	    super.onPause();
	    //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
	    mMapView.onPause();
	    }
	 @Override
	 protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
	    mMapView.onSaveInstanceState(outState);
	  }
	/**
	  * 激活定位
	  */
	 @Override
	 public void activate(OnLocationChangedListener listener) {
		  mListener = listener;
	 }
	 /**
	  * 停止定位
	  */
	 @Override
	 public void deactivate() {
		 mListener = null;
	 }
	 /**
	  * 定位成功后回调函数
	  */
	 @Override
	 public void onLocationChanged(AMapLocation aMapLocation) {
		  if (aMapLocation != null) {
		        if (aMapLocation.getErrorCode() == 0) {
		            //定位成功回调信息，设置相关消息
		            aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
		            aMapLocation.getLatitude();//获取纬度
		            aMapLocation.getLongitude();//获取经度
		            aMapLocation.getAccuracy();//获取精度信息
		            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            Date date = new Date(aMapLocation.getTime());
		            df.format(date);//定位时间
		            aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
		            aMapLocation.getCountry();//国家信息
		            aMapLocation.getProvince();//省信息
		            aMapLocation.getCity();//城市信息
		            aMapLocation.getDistrict();//城区信息
		         /*   aMapLocation.getStreet();//街道信息
		            aMapLocation.getStreetNum();//街道门牌号信息
*/		            aMapLocation.getCityCode();//城市编码
		            aMapLocation.getAdCode();//地区编码

		            // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
		            if (isFirstLoc) {
		                //设置缩放级别
		                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
		                //将地图移动到定位点
		                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
		                StringBuffer buffer = new StringBuffer();
		                buffer.append(aMapLocation.getCountry() + ""
		                        + aMapLocation.getProvince() + ""
		                        + aMapLocation.getCity() + ""
		                        + aMapLocation.getProvince() + ""
		                        + aMapLocation.getDistrict() + "");
		                      /*  + aMapLocation.get + ""
		                        + aMapLocation.getStreetNum());*/
		                //点击定位按钮 能够将地图的中心移动到定位点
		                MarkerOptions mko = new MarkerOptions();
		        		mko.position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
		        		mko.title("您现在位于：");
		        		mko.snippet(buffer.toString());
		        		/*mko.draggable(false);// 不允许移动标记
		        		mko.setFlat(false);// 是否贴地显示
		        		mko.visible(true);*/
		        		/*mko.perspective(true);*/
		        		mko.icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_marker));
		        		aMap.addMarker(mko);
		                mListener.onLocationChanged(aMapLocation);
		                //获取定位信息
		                Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
		                isFirstLoc = false;
		            }
		        } else {
		            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
		            Log.e("AmapError", "location Error, ErrCode:"
		                    + aMapLocation.getErrorCode() + ", errInfo:"
		                    + aMapLocation.getErrorInfo());
		            Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
		        }
		    }
	 }
	}