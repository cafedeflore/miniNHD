package com.cafedeflore.mininhd.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

import com.cafedeflore.libNHD.NHDException;
import com.cafedeflore.libNHD.Tools;
import com.cafedeflore.libNHD.data.TorrentEntity;
import com.cafedeflore.mininhd.MyApp;
import com.cafedeflore.mininhd.R;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

//public class TorrentListPragment extends PullToRefreshListFragment implements OnRefreshListener<ListView> {
public class TorrentListFragment extends Fragment implements OnRefreshListener<ListView> {
	private LinkedList<String> mListItems;
	private ArrayAdapter<String> mAdapter;
	private MyApp myApp = null;

	private PullToRefreshListFragment mPullRefreshListFragment;
	private PullToRefreshListView mPullRefreshListView;

	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> data;
	
	private ArrayList<TorrentEntity> torrentList = null;
	
	private static final String ARG_POSITION = "position";

	public static TorrentListFragment newInstance(MyApp myApp){
		TorrentListFragment f = new TorrentListFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, 1);
		f.setArguments(b);
		f.setMyApp(myApp);
//		f.onCreateView(inflater, container, savedInstanceState)
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());
		torrentList = new ArrayList<TorrentEntity>();
		mPullRefreshListView = new PullToRefreshListView(getActivity());
		params.setMargins(margin, margin, margin, margin);
		mPullRefreshListView.setLayoutParams(params);
		mPullRefreshListView.setLayoutParams(params);
		mPullRefreshListView.setGravity(Gravity.CENTER);
		mPullRefreshListView.setBackgroundResource(R.drawable.background_card);
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mListItems);
		
		putData();
//		simpleAdapter = new SimpleAdapter(getActivity(), data,
//				R.layout.torrent_item, new String[] { "torrent_type",
//						"second_title" }, new int[] { R.id.torrent_type, R.id.second_title });
//		
		simpleAdapter = new SimpleAdapter(getActivity(), data,
				R.layout.torrent_item, new String[] { "torrent_type", "seeding_num", "torrent_title",
						"second_title", "post_time", "resource_size" }, new int[] { R.id.torrent_type, R.id.seeding_num, R.id.torrent_title, 
						R.id.second_title, R.id.post_time, R.id.resource_size });
		// You can also just use setListAdapter(mAdapter) or
		// mPullRefreshListView.setAdapter(mAdapter)
		//mPullRefreshListView.setAdapter(mAdapter);
		mPullRefreshListView.setAdapter(simpleAdapter);
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println(arg2+ " ");// + arg3);	//结果证明两个值一样大...不科学,表index： 0~n-1
				//System.out.println(data.get(arg2).toString());
				System.out.println(torrentList.get(arg2).getDownloadUrl());
				try {
					myApp.getNhdService().getRequest(torrentList.get(arg2).getDownloadUrl());
				} catch (NHDException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mPullRefreshListView.setOnRefreshListener(this);
		new GetDataTask().execute();
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//
//		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
//				.getDisplayMetrics());
//
//		mPullRefreshListView = new PullToRefreshListView(getActivity());
//		params.setMargins(margin, margin, margin, margin);
//		mPullRefreshListView.setLayoutParams(params);
//		mPullRefreshListView.setLayoutParams(params);
//		mPullRefreshListView.setGravity(Gravity.CENTER);
//		mPullRefreshListView.setBackgroundResource(R.drawable.background_card);
//		
//		mListItems = new LinkedList<String>();
//		mListItems.addAll(Arrays.asList(mStrings));
//		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mListItems);
//
//		// You can also just use setListAdapter(mAdapter) or
//		// mPullRefreshListView.setAdapter(mAdapter)
//		mPullRefreshListView.setAdapter(mAdapter);
//		v.setText("CARD " + (1));
//		
//		fl.addView(mPullRefreshListView);
		
//		System.out.println(mPullRefreshListView.getParent());
		if(mPullRefreshListView.getParent()!=null){
//			FrameLayout layout = mPullRefreshListView.getParent();
			((ViewGroup)mPullRefreshListView.getParent()).removeView(mPullRefreshListView);
		}
		return mPullRefreshListView;
		
//		FrameLayout f1 = new FrameLayout(getActivity());
//		f1.setLayoutParams(params);
//		f1.addView(mPullRefreshListView);
//		
//		return f1;
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// Do work to refresh the list here.
		System.out.println("111111111");
		System.out.println(myApp.getPassword());
		new GetDataTask().execute();
	}
	
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
//				System.out.println(mListItems.size());
				//System.out.println( myApp.getNhdService().postRequest("http://www.nexushd.org/torrents.php") );
				html2torrents( myApp.getNhdService().postRequest("http://www.nexushd.org/torrents.php"));
//				Thread.sleep(4000);
			} catch (NHDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addFirst("Added after refresh...");
			mAdapter.notifyDataSetChanged();
			simpleAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			System.out.println(mListItems.size());
			super.onPostExecute(result);
		}
	}
	
	private static String getName(Element torrent){
		
		Element nameItem = torrent.child(1);
		nameItem = nameItem.select("td.embedded").first();
		
		if(nameItem.textNodes().size() < 1)
				return " ";
		return nameItem.textNodes().get(nameItem.textNodes().size()-1).toString();		//to be bug fixed
	}
	
	private void html2torrents(String html){
		if(html == null){
			return ;
		}
		data.clear();
		TorrentEntity torrentEntity = null;
		Document doc = Jsoup.parse(html);
		Map<String, Object> map = null;
		Element torrents_table = doc.select("table.torrents").first();
		Elements torrents = torrents_table.children().first().children();
		for (Element torrent : torrents){
			if(torrent.select("td.colhead").size()>0){
				continue;
			}
			torrentEntity = new TorrentEntity();
			new Tools().torrentToEntity(torrent, torrentEntity);
			torrentList.add(torrentEntity);
			
//			map=new HashMap<String, Object>();
//			map.put("torrent_type", R.drawable.cat101);
//			map.put("second_title", getName(torrent));
//			data.add(map);
			map=new HashMap<String, Object>();
			map.put("torrent_type", torrentEntity.getType());
			map.put("seeding_num", torrentEntity.getTorrentNumber() +"/"+ torrentEntity.getDownloadNumber() +"/"+ torrentEntity.getCompleteNumber());
			map.put("torrent_title", torrentEntity.getTitle());
			map.put("second_title", torrentEntity.getSecondTitle());
			map.put("post_time", torrentEntity.getTime());
			map.put("resource_size", torrentEntity.getSize());
			data.add(map);
		}
	}

	private void putData()
	{
		data=new ArrayList<Map<String,Object>>();
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

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
}
