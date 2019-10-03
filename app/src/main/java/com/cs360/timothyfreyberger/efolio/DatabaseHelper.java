package com.cs360.timothyfreyberger.efolio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Contacts.db";
    public static final String TABLE_NAME = "contact_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Address";
    public static final String COL_4 = "Phone";
    public static final String COL_5 = "Email";
    public static final String COL_6 = "Date";
    public static final String COL_7 = "Time";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ("+
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_2 + " TEXT, " + COL_3 + " TEXT, " + COL_4 + " TEXT, " +
                COL_5 + " TEXT, " + COL_6 + " TEXT, " + COL_7 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String name, String address, String phone, String email, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, name);
        contentValues.put(COL_3, address);
        contentValues.put(COL_4, phone);
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, date);
        contentValues.put(COL_7, time);

        long result = db.insert(TABLE_NAME,null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData (String id, String name, String address, String phone, String email, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, address);
        contentValues.put(COL_4, phone);
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, date);
        contentValues.put(COL_7, time);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[] { id });
        return true;


    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?", new String[] { id });
    }
}
