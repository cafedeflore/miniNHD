package com.cafedeflore.mininhd.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cafedeflore.mininhd.BoardActivity;
import com.cafedeflore.mininhd.MyApp;
import com.cafedeflore.mininhd.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

public class ProfileFragment extends Fragment{
	
	Layout layout = null;
	private MyApp myApp = null;
	
	public static ProfileFragment newInstance(MyApp myApp){
		ProfileFragment f = new ProfileFragment();
		Bundle b = new Bundle();
		f.setArguments(b);
		f.setMyApp(myApp);
//		f.onCreateView(inflater, container, savedInstanceState)
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		LinearLayout fl = new LinearLayout(getActivity());
		fl.setLayoutParams(params);
		fl.setOrientation(1);
		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());
		String profile = "用户名：cafedeflore\n魔力值：456\n分享率：3.3 \n上传量：4 \n下载量：5 ";
		TextView v = new TextView(getActivity());
		params.setMargins(margin, margin, margin, margin);
		v.setLayoutParams(params);
		v.setLayoutParams(params);
		v.setGravity(Gravity.CENTER);
		
		v.setBackgroundResource(R.drawable.background_card);
//		v.setText(profile);
		v.setText(myApp.profileToString());
		
		
//		
//		TextView v1 = new TextView(getActivity());
//		params.setMargins(margin, margin, margin, margin);
////		v1.setLayoutParams(params);
////		v1.setLayoutParams(params);
//		v1.setGravity(Gravity.CENTER);
//		v1.setBackgroundResource(R.drawable.background_card);
//		v1.setText("你妹");
//		
		fl.addView(v,params);
//		fl.addView(v1);
		return fl;
	}
	
	/**
	 * @return the myApp
	 */
	public MyApp getMyApp() {
		return myApp;
	}

	/**
	 * @param myApp the myApp to set
	 */
	public void setMyApp(MyApp myApp) {
		this.myApp = myApp;
	}
	
}
