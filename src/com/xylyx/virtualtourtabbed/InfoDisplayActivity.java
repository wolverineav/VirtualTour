package com.xylyx.virtualtourtabbed;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xylyx.virtualtourtabbed.DAO.CONSTANTS;
import com.xylyx.virtualtourtabbed.DAO.InventoryObjectDAO;
import com.xylyx.virtualtourtabbed.Objects.InventoryObject;

public class InfoDisplayActivity extends Activity {
	
	/**
	 * The DB connection for the relevant Sites. First screen requires the site data only,
	 * not the objects within the Site. 
	 * */
	private InventoryObjectDAO inventoryDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_display);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		InventoryObject invObj;
		
	      //Setup the database connection
  		inventoryDAO = new InventoryObjectDAO(this);
  		inventoryDAO.open();
  		
  		Intent intent = getIntent();
  		long invObjID = intent.getLongExtra(TourActivity.INV_OBJ_ID, 0);
  		Log.v("InfoDisplayActivity", "******* inv obj id passed is: " + invObjID);
  		
  		invObj = inventoryDAO.getInventoryObject(invObjID);
  		if(invObj == null){
  			Log.v("InfoDisplayActivity", "******* invObj returned by DAO is null !!!");
  		}
  		Log.v("InfoDisplayActivity", "****** invObjMedia is: " + invObj.getMedia());
  		//TextView infoView = new TextView(getBaseContext());
  		//infoView.setText(invObj.getMedia());
  		//infoView.setGravity(Gravity.CENTER);
  		Log.v("**** MediaType: ", String.valueOf(invObj.getMediaType()));
  		switch(invObj.getMediaType()){
  		case CONSTANTS.JPG:
  			TextView tv = (TextView) findViewById(R.id.textView);
  			tv.setVisibility(View.INVISIBLE);
  			ImageView iv = (ImageView) findViewById(R.id.imageView);
  			iv.setVisibility(View.VISIBLE);
  			URL url;
  			try {
  				url = new URL(invObj.getMedia());
  				iv.setTag(url.toString());
  			} catch (MalformedURLException e1) {
  				// TODO Auto-generated catch block
  				e1.printStackTrace();
  			}
  			new DownloadImageTask().execute(iv);
  			break;
  		default:
  		case CONSTANTS.TXT:
  			TextView infoView = (TextView) findViewById(R.id.textView);
  	  		infoView.setText("Retrieved Info: " + invObj.getMedia());
  	  		infoView.setGravity(Gravity.CENTER);
  			
  		}

  		//TextView infoView = (TextView) findViewById(R.id.textView);
  		//infoView.setText("Retrieved Info: " + invObj.getMedia());
  		//infoView.setGravity(Gravity.CENTER);
 		
  		//this.setContentView(R.id.textView);
	  	//setContentView(infoView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_info_display, menu);
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

}
