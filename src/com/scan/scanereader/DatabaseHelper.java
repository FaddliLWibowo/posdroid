package com.scan.scanereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "dbserver";
	public static final String NAMA = "servername";
	public static final String KEY_ID = "idserver";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	// method createTable untuk membuat table WISATA
	public void createTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS SERVER");
		db.execSQL("CREATE TABLE if not exists SERVER (idserver INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "servername TEXT);");
	}
	
	// method generateData untuk mengisikan data ke table Wisata.
		public void generateData(SQLiteDatabase db) {
			ContentValues cv = new ContentValues();
			cv.put(NAMA, "http://santrinulis.com");
			db.insert("SERVER", NAMA, cv);
		}

		// method delAllAdata untuk menghapus data di table Wisata.
		public void delAllData(SQLiteDatabase db) {
			db.delete("SERVER", null, null);
		}

		public Cursor fetchAllWisata(SQLiteDatabase db) {
			return db.query("SERVER", new String[] { KEY_ID, NAMA }, null, null,
					null, null, null);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			createTable(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

}
