package com.cafedeflore.mininhd;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;
import com.cafedeflore.mininhd.fragment.ProfileFragment;
import com.cafedeflore.mininhd.fragment.TorrentListFragment;
import com.cafedeflore.mininhd.fragment.TypeListFragment;

public class HomeActivity extends FragmentActivity{
	private final Handler handler = new Handler();
	
	private MyApp myApp;
	
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	
	private Drawable oldBackground = null;
	private int currentColor = 0xFF96AA39;
	
	private static Boolean isExit = false;
    private static Boolean hasTask = false;
    
    TorrentListFragment torrentlist = null;
    Timer tExit = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            isExit = false;
            hasTask = true;
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		myApp = (MyApp)getApplication();
		
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		
		pager.setAdapter(adapter);
		
		//TODO to be understand
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		
		pager.setPageMargin(pageMargin);
		
		tabs.setViewPager(pager);
		
		changeColor(currentColor);
		changeColor(currentColor);
//		@Override
//		public boolean onCreateOptionsMenu(Menu menu) {
//			getMenuInflater().inflate(R.menu.main, menu);
//			return true;
//		}
		
	}
	
	private void changeColor(int newColor) {
		tabs.setIndicatorColor(newColor);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActionBar().setDisplayShowTitleEnabled(false);
			getActionBar().setDisplayShowTitleEnabled(true);

		}

		currentColor = newColor;

	}
	
	public void onColorClicked(View v) {

		int color = Color.parseColor(v.getTag().toString());
		changeColor(color);

	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};
	
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "������Ϣ", "��Դ����", "������Դ"};//, "�ҵĶ���"};

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
//			return SuperAwesomeCardFragment.newInstance(position);
			switch(position){
			case 0:
//				 if(torrentlist == null){
//					 torrentlist = new TorrentListFragment(myApp);
//				 }
//				 return torrentlist;
//				return SuperAwesomeCardFragment.newInstance(position);
				return ProfileFragment.newInstance(myApp);
//				return new TorrentListPragment();
			case 1:return TorrentListFragment.newInstance(myApp);
			case 2:return new TypeListFragment();
				//return SuperAwesomeCardFragment.newInstance(position);
			case 3:return SuperAwesomeCardFragment.newInstance(position);
			}
			return null;
		}
//		
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object){
//			container.removeView((View) object);
//		}
//		
//		@Override
//		public boolean isViewFromObject(View view, Object object) {
//			return view == object;
//		}
	}
	
	@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit == false) {
                isExit = true;
                Toast.makeText(this, "�ٰ�һ�η��ؼ��˳�NHD", Toast.LENGTH_SHORT).show();
                if (!hasTask) {
                    tExit.schedule(task, 2000);
                }
            } else {
            	isExit = false;
                finish();
            }
        }
        return false;
    }
	
}
