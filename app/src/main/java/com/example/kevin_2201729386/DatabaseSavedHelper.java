package com.example.kevin_2201729386;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.kevin_2201729386.DatabaseSaved.*;

import androidx.annotation.Nullable;

public class DatabaseSavedHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "savedmov.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseSavedHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_SAVEDMOV_TABLE =  "CREATE TABLE " +
                databaseSaved.TABLE_NAME + " (" +
                databaseSaved._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                databaseSaved.COLOUMN_JUDUL + " TEXT NOT NULL, " +
                databaseSaved.COLOUMN_YEAR + " TEXT NOT NULL, " +
                databaseSaved.COLOUMN_IDMOV + " TEXT NOT NULL, " +
                databaseSaved.COLOUMN_LINKGAM + " TEXT NOT NULL, " +
                databaseSaved.COLOUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_SAVEDMOV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + databaseSaved.TABLE_NAME);
        onCreate(db);

    }
}
