package com.bemo.tabryanmorris;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "project2.db";
    private static final int DB_version = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table barang(nama TEXT primary key, jumlah TEXT, jenis TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists barang");
        onCreate(sqLiteDatabase);
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result == 1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checusernamepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checknama (String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from barang where nama=?", new String[] {nama});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean insertbarang (String nama,String jumlah, String jenis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama",nama);
        values.put("jumlah",jumlah);
        values.put("jenis", jenis);
        long result = db.insert("barang", null,values);
        if (result==1) return false;
        else
            return true;
    }

    public boolean hapusbarang (String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from barang where nama=?", new String[]{nama});
        if (cursor.getCount()>0){
            long result = db.delete("barang", "nama=?", new String[]{nama});
            if (result == -1){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

    public Cursor tampilbarang(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from barang", null);
        return cursor;
    }

    public Boolean editbarang (String nama,String jumlah, String jenis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama",nama);
        values.put("jumlah", jumlah);
        values.put("jenis", jenis);
        Cursor cursor = db.rawQuery("Select * from barang where nama=?", new String[]{nama});
        if (cursor.getCount()>0){
            long result = db.update("barang",values, "nama=?",new String[]{nama});
            if (result == -1){
                return false;
            }else {
                return true;
            }

        }else {
            return  false;
        }
    }
}