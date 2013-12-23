package com.cafedeflore.mininhd.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cafedeflore.libNHD.data.TorrentEntity;
import com.cafedeflore.mininhd.BoardActivity;
import com.cafedeflore.mininhd.HomeActivity;
import com.cafedeflore.mininhd.LoginActivity;
import com.cafedeflore.mininhd.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

public class TypeListFragment extends Fragment{
	private ImageView imageView;
	private GridView gridView;
	private int[] resIds = new int[] { R.drawable.cat101, R.drawable.cat102,
			R.drawable.cat103, R.drawable.cat104, R.drawable.cat105, R.drawable.cat106,
			R.drawable.cat107, R.drawable.cat108, R.drawable.cat109, R.drawable.cat110, R.drawable.cat111 };
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gridView = new GridView(getActivity());
		//gridView.setId(R.layout.type_list);
		gridView.setBackgroundResource(R.drawable.background_card);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < resIds.length; i++) {
			Map<String, Object> cell = new HashMap<String, Object>();
			cell.put("imageview", resIds[i]);
			list.add(cell);
		}
	
		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
					list, R.layout.cell, new String[] { "imageview" },
					new int[] { R.id.ivCell });
		
		gridView.setAdapter(simpleAdapter);
//		gridView.setVerticalSpacing(20);
		gridView.setNumColumns(2);
		gridView.setOnItemClickListener(new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			System.out.println(arg2+ " ");// + arg3);	//结果证明两个值一样大...不科学,表index： 0~n-1
			Intent intent = new Intent();
			intent.setClass(getActivity(), BoardActivity.class);
			intent.putExtra("html", "http://www.nexushd.org/torrents.php?cat=" + (101 + arg2));
			System.out.println("starting new activity");
			startActivity(intent);
			
			//System.out.println(data.get(arg2).toString());
			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
		if(gridView.getParent()!=null){
//			FrameLayout layout = mPullRefreshListView.getParent();
			((ViewGroup)gridView.getParent()).removeView(gridView);
		}
		return gridView;
	}
}
