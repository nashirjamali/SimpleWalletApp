package com.nashir.simplewalletapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "wallet.db";
    public static final String TABLE_NAME = "wallet_table";
    private static final int DATABASE_VERSION = 2;

    public static final String COL_1 = "ID";
    public static final String COL_2 = "TIME";
    public static final String COL_3 = "NAME";
    public static final String COL_4 = "NOMINAL";
    public static final String COL_5 = "TYPE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table wallet_table (id integer primary key autoincrement," +
                "time DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "name text," +
                "nominal integer," +
                "type text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String nominal, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_3, name);
        contentValues.put(COL_4, nominal);
        contentValues.put(COL_5, type);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public List<History> getAllData() {
        List<History> historyList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_2 + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                History history = new History(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getInt(3),
                        cursor.getInt(0));
                historyList.add(history);
            } while (cursor.moveToNext());
        }

        return historyList;
    }

    public int getDataCount() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        return cursor.getCount();
    }

    public List<LastTransaction> getLastTransaction() {
        List<LastTransaction> lastTransactionList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String today = formatter.format(date);

        Date threeDayBeforeDate = new Date(date.getTime() - (3 * 24 * 60 * 60 * 1000));
        String threeDayBefore = formatter.format(threeDayBeforeDate);


        String sql = "SELECT date(time), COUNT(id) FROM " + TABLE_NAME + " GROUP BY " + "date(time)";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);


        if (cursor.moveToFirst()) {
            do {
                LastTransaction lastTransaction = new LastTransaction(cursor.getString(0), cursor.getInt(1));
                lastTransactionList.add(lastTransaction);
            } while (cursor.moveToNext());
        }

        return lastTransactionList;
    }

    public int getBalance() {
        String sqlDebit = "SELECT SUM(nominal) as nominal FROM " + TABLE_NAME + " WHERE type = 'Debit'";
        String sqlKredit = "SELECT SUM(nominal) as nominal FROM " + TABLE_NAME + " WHERE type = 'Kredit'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorDebit = db.rawQuery(sqlDebit, null);
        Cursor cursorKredit = db.rawQuery(sqlKredit, null);

        if (cursorDebit.moveToFirst() && cursorKredit.moveToFirst()) {
            int balance = cursorDebit.getInt(0) - cursorKredit.getInt(0);
            return balance;
        }

        return 0;
    }

    public int getKredit(){
        String sqlKredit = "SELECT SUM(nominal) as nominal FROM " + TABLE_NAME + " WHERE type = 'Kredit'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorKredit = db.rawQuery(sqlKredit, null);
        if (cursorKredit.moveToFirst()) {
            return cursorKredit.getInt(0);
        }
        return 0;
    }

    public int getDebit(){
        String sqlDebit = "SELECT SUM(nominal) as nominal FROM " + TABLE_NAME + " WHERE type = 'Debit'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorDebit = db.rawQuery(sqlDebit, null);
        if (cursorDebit.moveToFirst()) {
            return cursorDebit.getInt(0);
        }
        return 0;
    }

    public boolean deleteData(int id){
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE  id = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return true;
    }
}
