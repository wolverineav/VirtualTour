package com.xylyx.virtualtourtabbed.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class VTSQLiteHelper extends SQLiteOpenHelper {
	
	//public static final String TABLE_COMMENTS = "comments";
	public static final String TABLE_SITE = "site";
	public static final String TABLE_INVENTORY = "inventory";
//	public static final String TABLE_SITEINVENTORY = "siteinventory";
	//Every table has primary key:
	public static final String COLUMN_ID = "_id";
	//Site Table
	public static final String COLUMN_SITENAME = "sitename";
	//Inventory Table
	public static final String COLUMN_SITEID = "siteid";
	public static final String COLUMN_OBJNAME = "objname";
	public static final String COLUMN_MEDIATYPE = "mediatype";
	public static final String COLUMN_MEDIA = "media";
	//SiteInventory Table
//	public static final String COLUMN_SITEID = "siteid";
//	public static final String COLUMN_INVENTORYID = "inventoryid";
	//DB name and version used while opening the connection
	private static final String DATABASE_NAME = "virtualtour.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
	private static final String CREATE_SITE_TABLE = "create table "
			+ TABLE_SITE + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_SITENAME
			+ " text not null);";
	private static final String CREATE_INVENTORY_TABLE = "create table "
			+ TABLE_INVENTORY + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_SITEID 
			+ " integer not null, " + COLUMN_OBJNAME + " text not null, "
			+ COLUMN_MEDIATYPE + " integer not null, " + COLUMN_MEDIA + " text not null);";
//	private static final String CREATE_SITEINV_TABLE = "create table "
//			+ TABLE_SITEINVENTORY + "(" + COLUMN_ID
//			+ " integer primary key autoincrement, "
//			+ "FOREIGN KEY (" + COLUMN_SITEID + ") REFERENCES "+ TABLE_SITE +" (" + COLUMN_ID + "), "
//			+ "FOREIGN KEY (" + COLUMN_INVENTORYID + ") REFERENCES "+ TABLE_INVENTORY +" (" + COLUMN_ID + "));";
			

	public VTSQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public VTSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(CREATE_SITE_TABLE);
		database.execSQL(CREATE_INVENTORY_TABLE);
		//database.execSQL(CREATE_SITEINV_TABLE);
		Log.v("onCreate", "done.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(VTSQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITEINVENTORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITE);
		onCreate(db);
	}

}
