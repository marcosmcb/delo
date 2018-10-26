package com.projects.marcoscavalcante.deloapp.ContentProvider;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.projects.marcoscavalcante.deloapp.Data.FavouriteContract;
import com.projects.marcoscavalcante.deloapp.Data.FavouriteDbHelper;
import com.projects.marcoscavalcante.deloapp.Data.FavouriteProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ContentProviderTest {

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    private static final Uri TEST_FAVOURITES = FavouriteContract.FavouriteEntry.CONTENT_URI;
    private static final Uri TEST_FAVOURITES_WITH_ID = TEST_FAVOURITES.buildUpon().appendPath("1").build();

    @Before
    public void setUp()
    {
        FavouriteDbHelper dbHelper = new FavouriteDbHelper(mContext);
        SQLiteDatabase database    = dbHelper.getWritableDatabase();
        database.delete(FavouriteContract.FavouriteEntry.TABLE_NAME,null, null);
    }

    @Test
    public void testProviderRegistry()
    {
        String packageName = mContext.getPackageName();
        String taskProviderClassName = FavouriteProvider.class.getName();
        ComponentName componentName = new ComponentName(packageName, taskProviderClassName);

        try {
            PackageManager pm = mContext.getPackageManager();

            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            String actualAuthority    = providerInfo.authority;
            String expectedAuthority  = packageName;

            /* Make sure that the registered authority matches the authority from the Contract */
            String incorrectAuthority =
                    "Error: TaskContentProvider registered with authority: " + actualAuthority +
                            " instead of expected authority: " + expectedAuthority;

            assertEquals(incorrectAuthority, actualAuthority, expectedAuthority);

        } catch (PackageManager.NameNotFoundException e) {
            String providerNotRegisteredAtAll =
                    "Error: TaskContentProvider not registered at " + mContext.getPackageName();

            fail(providerNotRegisteredAtAll);
        }

    }


    @Test
    public void testUriMatcher()
    {

        /* Create a URI matcher that the TaskContentProvider uses */
        UriMatcher testMatcher = FavouriteProvider.buildUriMatcher();

        /* Test that the code returned from our matcher matches the expected TASKS int */
        String tasksUriDoesNotMatch = "Error: The TASKS URI was matched incorrectly.";
        int actualTasksMatchCode = testMatcher.match(TEST_FAVOURITES);
        int expectedTasksMatchCode = FavouriteProvider.FAVOURITES;

        assertEquals(tasksUriDoesNotMatch,
                actualTasksMatchCode,
                expectedTasksMatchCode);

        /* Test that the code returned from our matcher matches the expected TASK_WITH_ID */
        String taskWithIdDoesNotMatch =
                "Error: The TASK_WITH_ID URI was matched incorrectly.";
        int actualTaskWithIdCode = testMatcher.match(TEST_FAVOURITES_WITH_ID);
        int expectedTaskWithIdCode = FavouriteProvider.FAVOURITES_WITH_ID;

        assertEquals(taskWithIdDoesNotMatch, actualTaskWithIdCode, expectedTaskWithIdCode);
    }


    /**
     * Tests inserting a single row of data via a ContentResolver
     */

    @Test
    public void testInsert()
    {
        /* Create values to insert */
        ContentValues testMovieValues = new ContentValues();
        testMovieValues.put(FavouriteContract.FavouriteEntry.COLUMN_ID, 20000);
        testMovieValues.put(FavouriteContract.FavouriteEntry.COLUMN_NAME, "Testing PRODUCT NAME");

        /* TestContentObserver allows us to test if notifyChange was called appropriately */
        TestUtilities.TestContentObserver taskObserver = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        /* Register a content observer to be notified of changes to data at a given URI (tasks) */
        contentResolver.registerContentObserver( FavouriteContract.FavouriteEntry.CONTENT_URI,true, taskObserver);

        Uri uri = contentResolver.insert(FavouriteContract.FavouriteEntry.CONTENT_URI, testMovieValues);

        Uri expectedUri = ContentUris.withAppendedId(FavouriteContract.FavouriteEntry.CONTENT_URI, 20000);

        String insertProviderFailed = "Unable to insert item through Provider";
        assertEquals(insertProviderFailed, uri, expectedUri);

        taskObserver.waitForNotificationOrFail();

        contentResolver.unregisterContentObserver(taskObserver);
    }

    /**
     * Inserts data, then tests if a query for the tasks directory returns that data as a Cursor
     */
    @Test
    public void testQuery()
    {

        /* Get access to a writable database */
        FavouriteDbHelper dbHelper = new FavouriteDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        /* Create values to insert */
        ContentValues testMovieValues = new ContentValues();
        testMovieValues.put(FavouriteContract.FavouriteEntry.COLUMN_ID, 2000);
        testMovieValues.put(FavouriteContract.FavouriteEntry.COLUMN_NAME, "Testing PRODUCT NAME");

        /* Insert ContentValues into database and get a row ID back */
        long taskRowId = database.insert(
                /* Table to insert values into */
                FavouriteContract.FavouriteEntry.TABLE_NAME,
                null,
                /* Values to insert into table */
                testMovieValues);

        String insertFailed = "Unable to insert directly into the database";
        assertTrue(insertFailed, taskRowId != -1);

        /* We are done with the database, close it now. */
        database.close();

        /* Perform the ContentProvider query */
        Cursor taskCursor = mContext.getContentResolver().query(
                FavouriteContract.FavouriteEntry.CONTENT_URI,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Sort order to return in Cursor */
                null);


        String queryFailed = "Query failed to return a valid Cursor";
        assertTrue(queryFailed, taskCursor != null);

        /* We are done with the cursor, close it now. */
        taskCursor.close();
    }

    /**
     * Tests deleting a single row of data via a ContentResolver
     */
    @Test
    public void testDelete()
    {
        /* Access writable database */
        FavouriteDbHelper helper = new FavouriteDbHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase database = helper.getWritableDatabase();

        /* Create a new row of task data */
        ContentValues testTaskValues = new ContentValues();
        testTaskValues.put(FavouriteContract.FavouriteEntry.COLUMN_ID, 2000);
        testTaskValues.put(FavouriteContract.FavouriteEntry.COLUMN_NAME, "Testing PRODUCT NAME");

        /* Insert ContentValues into database and get a row ID back */
        long taskRowId = database.insert(
                /* Table to insert values into */
                FavouriteContract.FavouriteEntry.TABLE_NAME,
                null,
                /* Values to insert into table */
                testTaskValues);

        /* Always close the database when you're through with it */
        database.close();

        String insertFailed = "Unable to insert into the database";
        assertTrue(insertFailed, taskRowId != -1);


        /* TestContentObserver allows us to test if notifyChange was called appropriately */
        TestUtilities.TestContentObserver taskObserver = TestUtilities.getTestContentObserver();

        ContentResolver contentResolver = mContext.getContentResolver();

        /* Register a content observer to be notified of changes to data at a given URI (tasks) */
        contentResolver.registerContentObserver(
                /* URI that we would like to observe changes to */
                FavouriteContract.FavouriteEntry.CONTENT_URI,
                /* Whether or not to notify us if descendants of this URI change */
                true,
                /* The observer to register (that will receive notifyChange callbacks) */
                taskObserver);



        /* The delete method deletes the previously inserted row with id = 1 */
        Uri uriToDelete = FavouriteContract.FavouriteEntry.CONTENT_URI.buildUpon().appendPath("2000").build();
        int tasksDeleted = contentResolver.delete(uriToDelete, null, null);

        String deleteFailed = "Unable to delete item in the database";
        assertTrue(deleteFailed, tasksDeleted != 0);

        /*
         * If this fails, it's likely you didn't call notifyChange in your delete method from
         * your ContentProvider.
         */
        taskObserver.waitForNotificationOrFail();

        /*
         * waitForNotificationOrFail is synchronous, so after that call, we are done observing
         * changes to content and should therefore unregister this observer.
         */
        contentResolver.unregisterContentObserver(taskObserver);
    }
}
