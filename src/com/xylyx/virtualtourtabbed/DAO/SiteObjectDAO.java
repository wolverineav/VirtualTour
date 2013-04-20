package com.xylyx.virtualtourtabbed.DAO;

import java.util.ArrayList;
import java.util.List;

import com.xylyx.virtualtourtabbed.Objects.SiteObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SiteObjectDAO {
	  // Database fields
	  private SQLiteDatabase database;
	  private VTSQLiteHelper dbHelper;
	  private String[] allColumns = { VTSQLiteHelper.COLUMN_ID,
	      VTSQLiteHelper.COLUMN_SITENAME };

	  public SiteObjectDAO(Context context) {
	    dbHelper = new VTSQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public SiteObject createSiteObject(String siteName) {
	    ContentValues values = new ContentValues();
	    values.put(VTSQLiteHelper.COLUMN_SITENAME, siteName);
	    long insertId = database.insert(VTSQLiteHelper.TABLE_SITE, null,
	        values);
	    Cursor cursor = database.query(VTSQLiteHelper.TABLE_SITE,
	        allColumns, VTSQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    SiteObject newSiteName = cursorToSiteObject(cursor);
	    cursor.close();
	    return newSiteName;
	  }

	  public void deleteSiteObject(SiteObject siteObject) {
	    long id = siteObject.getId();
	    System.out.println("SiteObject deleted with id: " + id);
	    database.delete(VTSQLiteHelper.TABLE_SITE, VTSQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<SiteObject> getAllSiteObjects() {
	    List<SiteObject> siteObjects = new ArrayList<SiteObject>();

	    Cursor cursor = database.query(VTSQLiteHelper.TABLE_SITE,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      SiteObject siteName = cursorToSiteObject(cursor);
	      siteObjects.add(siteName);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return siteObjects;
	  }

	  private SiteObject cursorToSiteObject(Cursor cursor) {
	    SiteObject siteObject = new SiteObject();
	    siteObject.setId(cursor.getLong(0));
	    siteObject.setSiteName(cursor.getString(1));
	    return siteObject;
	  }
}
