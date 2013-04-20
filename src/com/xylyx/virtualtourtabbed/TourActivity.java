package com.xylyx.virtualtourtabbed;

import java.util.Iterator;
import java.util.List;

import com.xylyx.virtualtourtabbed.DAO.CONSTANTS;
import com.xylyx.virtualtourtabbed.DAO.InventoryObjectDAO;
import com.xylyx.virtualtourtabbed.Objects.InventoryObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TourActivity extends ListActivity {
	
	public final static String INV_OBJ_ID = "com.xylyx.VirtualTour.INV_OBJ_ID";

	/**
	 * The DB connection for the relevant Sites. First screen requires the site data only,
	 * not the objects within the Site. 
	 * */
	private InventoryObjectDAO inventoryDAO;
	
	private static ArrayAdapter<InventoryObject> adapter;
	// This is the Adapter being used to display the list's data
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Show the Up button in the action bar.
		// Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        Intent intent = getIntent();
        //String message = intent.getStringExtra(StartActivity.SITE_ID);
        long siteId = intent.getLongExtra(StartActivity.SITE_ID, 0);
        long[] siteIdList = intent.getLongArrayExtra(StartActivity.SITE_IDS);
        Log.v("**** TourActivity ****", " siteID recvd is : " + siteId);
        for(int i=0; i<siteIdList.length; i++){
        	Log.v("**** TourActivity ****", " siteIdList["+i+"]: " + siteIdList[i]);        	
        }
        
      //Setup the database connection
      		inventoryDAO = new InventoryObjectDAO(this);
      		inventoryDAO.open();
      		
      		adapter = new ArrayAdapter<InventoryObject>(this, android.R.layout.simple_list_item_1);
      		//adapter = new ArrayAdapter<SiteObject>(this, android.R.layout.simple_list_item_1, values);
      		
      		//Add objects to the db
      		InventoryObject invObject = null;
      		String[] invObjects = new String[] { "Aurdino", "MacBook Air", "MarioBros."};
      		int[] invObjType = new int[] {CONSTANTS.TXT, CONSTANTS.TXT, CONSTANTS.JPG};
      		String[] invObjectsInfo = new String[] { "Aurdino: in dino dil mera..", 
      				"MacBook Air: hawa hawai!",
      				"http://blog.8bitlibrary.com/wp-content/uploads/2010/02/super-mario-bros1.jpg"};
      		long[] sites = new long[] {siteIdList[0], siteIdList[1], siteIdList[0]};
      		for(int i=0; i<invObjects.length; i++){
      			invObject = inventoryDAO.createInventoryObject(invObjects[i], invObjType[i], invObjectsInfo[i], sites[i]);
      			//adapter.add(invObject);
      		}
      		
      		List<InventoryObject> inventoryObjects = inventoryDAO.getAllInventoryObjects(siteId);
      		Iterator<InventoryObject> it = inventoryObjects.iterator();
      		while(it.hasNext()){
      			adapter.add(it.next());
      		}      		
      		adapter.notifyDataSetChanged();
      		
      		setListAdapter(adapter);      	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tour, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		
		Intent intent = new Intent(v.getContext() , InfoDisplayActivity.class);
		InventoryObject invObj = (InventoryObject) getListView().getItemAtPosition(position);
		intent.putExtra(INV_OBJ_ID, invObj.getId());
		startActivity(intent);
	}
	
}
