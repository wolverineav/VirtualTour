package com.xylyx.virtualtourtabbed;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xylyx.virtualtourtabbed.DAO.SiteObjectDAO;
import com.xylyx.virtualtourtabbed.Objects.SiteObject;

@SuppressLint("DefaultLocale")
public class StartActivity extends FragmentActivity implements
		ActionBar.TabListener {

	//public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public final static String SITE_ID = "com.xylyx.VirtualTour.SITEID";
	public final static String SITE_IDS = "com.xylyx.VirtualTour.SITEIDS";
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	/**
	 * The DB connection for the relevant Sites. First screen requires the site data only,
	 * not the objects within the Site. 
	 * */
	private SiteObjectDAO siteDAO;
	
	protected static ArrayAdapter<SiteObject> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//Setup the database connection
		siteDAO = new SiteObjectDAO(this);
		siteDAO.open();
		
		adapter = new ArrayAdapter<SiteObject>(this, android.R.layout.simple_list_item_1);
		//adapter = new ArrayAdapter<SiteObject>(this, android.R.layout.simple_list_item_1, values);
		
		//Add objects to the db
		SiteObject site = null;
		String[] siteNames = new String[] { "Hunt Library", "Centennial Campus"};
		for(int i=0; i<siteNames.length; i++){
			site = siteDAO.createSiteObject(siteNames[i]);
			adapter.add(site);
		}
		adapter.notifyDataSetChanged();
		
		//List<SiteObject> values = siteDAO.getAllSiteObjects();
		
		// Use the SimpleCursorAdapter to show the
	    // elements in a ListView
		
		
		
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}
	
	@Override
	  protected void onResume() {
	    siteDAO.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    siteDAO.close();
	    super.onPause();
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add("Add Sites to DB");
		getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			Bundle args;
			switch (position) {
				case 0:
					fragment = new BrowseFragment();
					args = new Bundle();
					args.putInt(BrowseFragment.ARG_SECTION_NUMBER, position + 1);
					fragment.setArguments(args);
					return fragment;
				case 1:
					fragment = new DownloadFragment();
					args = new Bundle();
					args.putInt(DownloadFragment.ARG_SECTION_NUMBER, position + 1);
					fragment.setArguments(args);
					return fragment;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@SuppressLint("DefaultLocale")
		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class BrowseFragment extends ListFragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		

		public BrowseFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			Log.v("BrowseFragment", "onCreate");
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			//Array list of maps			
			setListAdapter(StartActivity.adapter);
			
			return super.onCreateView(inflater, container, savedInstanceState);
		}
		
		@Override
		public void onListItemClick(ListView l, View v, int position, long id){
			
			Intent intent = new Intent(getActivity(), TourActivity.class);
			SiteObject site = (SiteObject) getListView().getItemAtPosition(position);
			intent.putExtra(SITE_ID, site.getId());
			
			int count = adapter.getCount();
			long[] siteIdList = new long[count];
			//intent.putExtra("COUNT", count);
			SiteObject so;
			for(int i=0; i<count; i++){
				so = (SiteObject) getListView().getItemAtPosition(i);
				siteIdList[i] = so.getId();
				//intent.putExtra("SITE"+i, so.getId());						
			}
			intent.putExtra(SITE_IDS, siteIdList);
			startActivity(intent);
		}
		
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DownloadFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DownloadFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.TOP);
			textView.setText("Download Maps\n" + Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}
	}

}
