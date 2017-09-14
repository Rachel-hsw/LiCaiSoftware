package com.bn.lc;

import static com.bn.lc.Constant.fangshi;
import static com.bn.lc.Constant.shourukm;
import static com.bn.lc.Constant.titleContent;
import static com.bn.lc.Constant.zhichukm;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
public class LiCai1Activity extends Activity {
	private GridView gv_home;
	private int[] mDrawableIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);	
		  
	        //ȫ����ʾ�����ֻ�״̬�����ɼ�
	/*        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
	       getWindow().setFlags
	        (
	     
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	        		WindowManager.LayoutParams.FLAG_FULLSCREEN
	        );
	        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ǿ������
		initUI();
		//��ʼ�����ݵķ���
		initData();
		List<String> slist=DBUtil.getSubjects("incomesubject");
     	if(slist.size()==0){
     	   for(int i=0;i<shourukm.length;i++)
     		   DBUtil.insertSubjects(shourukm[i], "incomesubject");
     	}
     	List<String> zlist=DBUtil.getSubjects("spendsubject");
     	if(zlist.size()==0){
     		for(int i=0;i<zhichukm.length;i++)
     			DBUtil.insertSubjects(zhichukm[i], "spendsubject");
        }
     	List<String> flist=DBUtil.getSubjects("szmode");
       	if(flist.size()==0){
       		for(int i=0;i<fangshi.length;i++)
       		  DBUtil.insertSubjects(fangshi[i], "szmode");	
     	}  
       	for(int i=0;i<zhichukm.length;i++){
         	  DBUtil.insertYusuan(i,0,"yusuan");
         	  DBUtil.insertYusuan(i,0,"yusuanyue");
           }
        for(int i=0;i<titleContent.length;i++){
          	  DBUtil.insertTiXingTitle(titleContent[i],"tixingtitle");
           }
	}

	private void initData() {
		//׼������(����(9��),ͼƬ(9��))		
		mDrawableIds = new int[]{
				R.drawable.jizhang,R.drawable.shouzhi,R.drawable.baobiao,
				R.drawable.yuyin,R.drawable.yusuan,R.drawable.zuji,
				R.drawable.jisuan,R.drawable.tixing,R.drawable.shezhi,
				R.drawable.jianyi,R.drawable.tuichu
		};
		//�Ź���ؼ���������������(��ͬListView����������)
		gv_home.setAdapter(new MyAdapter());
		//ע��Ź��񵥸���Ŀ����¼�
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			//�����б���Ŀ����position
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				switch (position) {
				case 0:
					//�����Ի���
					startActivity(new Intent(getApplicationContext(), JiZhangActivity.class));
					break;
				case 1:
					//��ת��ͨ����ʿģ��
					startActivity(new Intent(getApplicationContext(), ShouZhiActivity.class));
					break;
				case 2:
					//��ת��ͨ����ʿģ��
					startActivity(new Intent(getApplicationContext(), BaoBiaoActivity.class));
					break;
				case 3:
					//��ת��ͨ����ʿģ��
					startActivity(new Intent(getApplicationContext(), YuyinActivity.class));
					break;
				case 4:
					//��ת��ͨ����ʿģ��
					startActivity(new Intent(getApplicationContext(), YuSuanActivity.class));
					break;
				case 5:
					//��ת��ͨ����ʿģ��
				startActivity(new Intent(getApplicationContext(), ZuJiActivity.class));
					break;
				case 6:
					startActivity(new Intent(getApplicationContext(), CalActivity.class));
					
					break;
				case 7:
					//��ת���߼����߹����б����
					startActivity(new Intent(getApplicationContext(), TiXingActivity.class));
					break;
				case 8:
					Intent intent = new Intent(getApplicationContext(),SheZhiActivity.class);
					startActivity(intent);
					break;
				case 9:
					//��ת���߼����߹����б����
					startActivity(new Intent(getApplicationContext(), JianYiFeedbackActivity.class));
					break;
				case 10:
					System.out.println("Exit`````````");
			    	System.exit(0);
					break;
				}
			}
		});
	}


	
	private void initUI() {
		gv_home = (GridView) findViewById(R.id.gv_home);
	}
	
	class MyAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			//��Ŀ������	�������� == ͼƬ����
			return mDrawableIds.length;
		}

		@Override
		public Object getItem(int position) {
			return mDrawableIds[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			iv_icon.setBackgroundResource(mDrawableIds[position]);
			
			return view;
		}
	}
}
