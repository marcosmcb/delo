package com.projects.marcoscavalcante.deloapp.ContentProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.projects.marcoscavalcante.deloapp.Data.FavouriteContract;
import com.projects.marcoscavalcante.deloapp.Data.FavouriteDbHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private final Context mContext = InstrumentationRegistry.getTargetContext();
    private final Class mDbHelperClass = FavouriteDbHelper.class;

    @Before
    public void setUp()
    {
        deleteDatabase();
    }

    @Test
    public void createDatabaseTest( ) throws Exception
    {
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String databaseIsNotOpen = "The database should be open and isn't";
        assertEquals( databaseIsNotOpen, true, database.isOpen() );

        Cursor tableNameCursor = database.rawQuery("SELECT NAME FROM sqlite_master WHERE type ='table' and name IN ('" +
                FavouriteContract.FavouriteEntry.TABLE_NAME + "')", null);


        String errorInCreatingDatabase =
                "Error: This means that the database has not been created correctly";

        assertTrue(errorInCreatingDatabase, tableNameCursor.getCount() == 1 );

        tableNameCursor.close();
    }

    @Test
    public void insertSingleRecordTest() throws Exception
    {
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor( Context.class ).newInstance(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues testValues = new ContentValues();
        testValues.put(FavouriteContract.FavouriteEntry.COLUMN_ID, "1000");
        testValues.put(FavouriteContract.FavouriteEntry.COLUMN_NAME, "Cool shoes, blue");
        testValues.put(FavouriteContract.FavouriteEntry.COLUMN_PRICE, 34.99);
        testValues.put(FavouriteContract.FavouriteEntry.COLUMN_OLD_PRICE, 0.0);
        testValues.put(FavouriteContract.FavouriteEntry.COLUMN_CATEGORY, "Men's shoes");
        testValues.put(FavouriteContract.FavouriteEntry.COLUMN_STOCK, 3);


        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = database.insert(
                FavouriteContract.FavouriteEntry.TABLE_NAME,
                null,
                testValues);

        /* If the insert fails, database.insert returns -1 */
        assertNotEquals("Unable to insert into the table FAVOURITES", -1, firstRowId);

        /*
         * Query the database and receive a Cursor. A Cursor is the primary way to interact with
         * a database in Android.
         */
        Cursor wCursor = database.query(
                /* Name of table on which to perform the query */
                FavouriteContract.FavouriteEntry.TABLE_NAME,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        /* Cursor.moveToFirst will return false if there are no records returned from your query */
        String emptyQueryError = "Error: No Records returned from MOVIE query";
        assertTrue(emptyQueryError, wCursor.moveToFirst());

        wCursor.close();

        dbHelper.close();
    }

    /**
     * Deletes the entire database.
     */
    private void deleteDatabase()
    {
        try
        {
            /* Use reflection to get the database name from the db helper class */
            Field f = mDbHelperClass.getDeclaredField("DATABASE_NAME");
            f.setAccessible(true);
            mContext.deleteDatabase((String)f.get(null));
        }
        catch (NoSuchFieldException ex){
            fail("Make sure you have a member called DATABASE_NAME in the DbHelper");
        }catch (Exception ex){
            fail(ex.getMessage());
        }

    }




}
