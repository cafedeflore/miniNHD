package com.cafedeflore.mininhd;

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

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cafedeflore.libNHD.NHDException;
import com.cafedeflore.libNHD.Tools;
import com.cafedeflore.libNHD.data.TorrentEntity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;

public final class BoardActivity extends ListActivity {

	private LinkedList<String> mListItems;
	private PullToRefreshListView mPullRefreshListView;
	private ArrayAdapter<String> mAdapter;
	private MyApp myApp = null;
	
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> data;
	
	private String html = null;
	
	private ArrayList<TorrentEntity> torrentList = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_list);
		myApp = (MyApp)getApplication();
		torrentList = new ArrayList<TorrentEntity>();
		Intent intent = getIntent();
		if(intent.getStringExtra("html")!=null){
			html = intent.getStringExtra("html");
			System.out.println(html);
		}
		else{
			finish();
			return ;
		}
		
		putData();
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});
		
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

		// Add an end-of-list listener
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(BoardActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
			}
		});

		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);

		simpleAdapter = new SimpleAdapter(BoardActivity.this, data,
				R.layout.torrent_item, new String[] { "torrent_type", "seeding_num", "torrent_title",
						"second_title", "post_time", "resource_size" }, new int[] { R.id.torrent_type, R.id.seeding_num, R.id.torrent_title, 
						R.id.second_title, R.id.post_time, R.id.resource_size });
		actualListView.setAdapter(simpleAdapter);
		new GetDataTask().execute();
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
//				System.out.println(mListItems.size());
				//System.out.println( myApp.getNhdService().postRequest("http://www.nexushd.org/torrents.php") );
				html2torrents( myApp.getNhdService().postRequest(html));
//				Thread.sleep(4000);
			} catch (NHDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			
			simpleAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			
			super.onPostExecute(result);
		}
	}

	private void putData()
	{
		data=new ArrayList<Map<String,Object>>();
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
	
	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
}
