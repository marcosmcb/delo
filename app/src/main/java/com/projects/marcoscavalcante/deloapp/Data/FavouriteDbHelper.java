package com.projects.marcoscavalcante.deloapp.Data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.projects.marcoscavalcante.deloapp.Data.FavouriteContract.FavouriteEntry;


public class FavouriteDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DeloApp.db";


    private static final String SQL_CREATE_FAVOURITES =
            "CREATE TABLE " + FavouriteEntry.TABLE_NAME + " (" +
                    FavouriteEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                    FavouriteEntry.COLUMN_NAME + " TEXT," +
                    FavouriteEntry.COLUMN_CATEGORY + " TEXT," +
                    FavouriteEntry.COLUMN_STOCK + " INTEGER," +
                    FavouriteEntry.COLUMN_PRICE + " REAL," +
                    FavouriteEntry.COLUMN_OLD_PRICE + " REAL)" ;


    private static final String SQL_DELETE_FAVOURITES =
            "DROP TABLE IF EXISTS " + FavouriteEntry.TABLE_NAME;





    public FavouriteDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVOURITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FAVOURITES);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
