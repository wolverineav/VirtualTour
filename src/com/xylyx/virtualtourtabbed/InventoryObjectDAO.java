package com.xylyx.virtualtourtabbed;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class InventoryObjectDAO {
	  // Database fields
	  private SQLiteDatabase database;
	  private VTSQLiteHelper dbHelper;
	  private String[] allColumnsInv = { VTSQLiteHelper.COLUMN_ID,
	      VTSQLiteHelper.COLUMN_OBJNAME, VTSQLiteHelper.COLUMN_MEDIATYPE,
	      VTSQLiteHelper.COLUMN_MEDIA };
	  private String[] allColumnsSiteInv = { VTSQLiteHelper.COLUMN_ID,
			  VTSQLiteHelper.COLUMN_SITEID, VTSQLiteHelper.COLUMN_INVENTORYID };

	  public InventoryObjectDAO(Context context) {
	    dbHelper = new VTSQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public InventoryObject createInventoryObject(String objectName, String mediaType, String media) {
	    ContentValues values = new ContentValues();
	    values.put(VTSQLiteHelper.COLUMN_OBJNAME, objectName);
	    values.put(VTSQLiteHelper.COLUMN_MEDIATYPE, mediaType);
	    values.put(VTSQLiteHelper.COLUMN_MEDIA, media);
	    try{
	    long insertId = database.insert(VTSQLiteHelper.TABLE_INVENTORY, null,
	        values);
	    Cursor cursor = database.query(VTSQLiteHelper.TABLE_INVENTORY,
		        allColumnsInv, VTSQLiteHelper.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    InventoryObject newObjectName = cursorToInventoryObject(cursor);
		    cursor.close();
		    return newObjectName;
	    } catch (Exception excptn){
	    	System.out.println("excpt while inset invObj: " + excptn.getMessage());	    	
	    }
	    
	    return null;
	  }

	  public void deleteInventoryObject(InventoryObject inventoryObject) {
	    long id = inventoryObject.getId();
	    System.out.println("InventoryObject deleted with id: " + id);
	    database.delete(VTSQLiteHelper.TABLE_INVENTORY, VTSQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public InventoryObject getInventoryObject(long InvObjID){
		  InventoryObject invObj = null;
		  //String[] invObjId = {String.valueOf(InvObjID)};
		  Cursor cursor = database.query(VTSQLiteHelper.TABLE_INVENTORY, allColumnsInv,
				  VTSQLiteHelper.COLUMN_ID + " = " + InvObjID, null, null, null, null);
		  if(cursor.moveToFirst()){
			  invObj = cursorToInventoryObject(cursor);
		  }
		  cursor.close();
		  return invObj;
		  
	  }
	  public List<InventoryObject> getAllInventoryObjects(long inventoryObjID) {
	    List<InventoryObject> inventoryObjects = new ArrayList<InventoryObject>();

	    //Cursor cursor = database.query(VTSQLiteHelper.TABLE_SITE, allColumns, null, null, null, null, null);
	    //String siteId = String.valueOf(siteID);
	    String[] invObjId = {String.valueOf(inventoryObjID)};
	    Cursor cursor = database.rawQuery("select * from inventory where _ID IN (select inventoryid from siteinventory where siteid = ?)", invObjId);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      InventoryObject objectName = cursorToInventoryObject(cursor);
	      inventoryObjects.add(objectName);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return inventoryObjects;
	  }

	  private InventoryObject cursorToInventoryObject(Cursor cursor) {
		InventoryObject inventoryObject = new InventoryObject();
	    inventoryObject.setId(cursor.getLong(0));
	    inventoryObject.setObjName(cursor.getString(cursor.getColumnIndex(VTSQLiteHelper.COLUMN_OBJNAME)));
	    inventoryObject.setMediaType(cursor.getString(cursor.getColumnIndex(VTSQLiteHelper.COLUMN_MEDIATYPE)));
	    inventoryObject.setMedia(cursor.getString(cursor.getColumnIndex(VTSQLiteHelper.COLUMN_MEDIA)));
	    return inventoryObject;
	  }

}
