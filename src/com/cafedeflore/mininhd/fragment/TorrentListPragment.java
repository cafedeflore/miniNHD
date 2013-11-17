package com.cafedeflore.mininhd.fragment;

import java.util.Arrays;
import java.util.LinkedList;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.FrameLayout.LayoutParams;

import com.cafedeflore.mininhd.R;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

//public class TorrentListPragment extends PullToRefreshListFragment implements OnRefreshListener<ListView> {
public class TorrentListPragment extends Fragment implements OnRefreshListener<ListView> {
	private LinkedList<String> mListItems;
	private ArrayAdapter<String> mAdapter;

	private PullToRefreshListFragment mPullRefreshListFragment;
	private PullToRefreshListView mPullRefreshListView;

	private static final String ARG_POSITION = "position";
	
	public static TorrentListPragment newInstance(){
		TorrentListPragment f = new TorrentListPragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, 1);
		f.setArguments(b);
//		f.onCreateView(inflater, container, savedInstanceState)
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());

		mPullRefreshListView = new PullToRefreshListView(getActivity());
		params.setMargins(margin, margin, margin, margin);
		mPullRefreshListView.setLayoutParams(params);
		mPullRefreshListView.setLayoutParams(params);
		mPullRefreshListView.setGravity(Gravity.CENTER);
		mPullRefreshListView.setBackgroundResource(R.drawable.background_card);
		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));
		mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mListItems);
		
		// You can also just use setListAdapter(mAdapter) or
		// mPullRefreshListView.setAdapter(mAdapter)
		mPullRefreshListView.setAdapter(mAdapter);
		mPullRefreshListView.setOnRefreshListener(this);
	}

//	@Override
//	public PullToRefreshListView onCreatePullToRefreshListView(LayoutInflater inflater, Bundle savedInstanceState){
//		
////		v.setText("CARD " + (1));
//		
//		return mPullRefreshListView;
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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
		return mPullRefreshListView;
	}
	
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// Do work to refresh the list here.
		System.out.println("111111111");
		new GetDataTask().execute();
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				System.out.println(mListItems.size());
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addFirst("Added after refresh...");
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			System.out.println(mListItems.size());
			super.onPostExecute(result);
		}
	}

	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
}
