package com.degtyar.sergey.sqlliteapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by student on 30.11.2016.
 */

public class DbHelper extends SQLiteOpenHelper implements BaseColumns{

    public static final String DB_NAME = "names_database.db";
    public static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "names";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL="email";
    public static final String SQL_CREATE_TBL =
            "CREATE TABLE " + TABLE_NAME +
                    "("+
                    _ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_NAME+ " TEXT, "+
                    COLUMN_EMAIL+ " TEXT"+
                    ");";
    public DbHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //initial block;
        db.execSQL(SQL_CREATE_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade block
        db.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(db);
    }
}
