package com.projects.marcoscavalcante.deloapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FavouriteProvider extends ContentProvider {


    private FavouriteDbHelper mFavouriteDbHelper;

    public static final int FAVOURITES = 100;
    public static final int FAVOURITES_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher( );


    public static UriMatcher buildUriMatcher( )
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI( FavouriteContract.AUTHORITY, FavouriteContract.PATH_FAVOURITES, FAVOURITES );
        uriMatcher.addURI( FavouriteContract.AUTHORITY, FavouriteContract.PATH_FAVOURITES + "/#", FAVOURITES_WITH_ID );

        return uriMatcher;
    }



    @Override
    public boolean onCreate() {
        Context context = getContext();
        mFavouriteDbHelper = new FavouriteDbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mFavouriteDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch(match)
        {
            case FAVOURITES:
                retCursor = db.query(FavouriteContract.FavouriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case FAVOURITES_WITH_ID:

                String id = uri.getPathSegments().get(1);

                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                retCursor = db.query(FavouriteContract.FavouriteEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mFavouriteDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        long id;

        switch(match) {
            case FAVOURITES:
                id = db.insert(FavouriteContract.FavouriteEntry.TABLE_NAME, null, values);
                returnUri = getResponse(id, FavouriteContract.FavouriteEntry.CONTENT_URI);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mFavouriteDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int numberOfDeletedItems;

        switch (match)
        {
            case FAVOURITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mWhereClause = FavouriteContract.FavouriteEntry.COLUMN_ID + "=?";
                String[] mWhereArgs = new String[]{id};

                numberOfDeletedItems = db.delete(FavouriteContract.FavouriteEntry.TABLE_NAME,
                        mWhereClause,
                        mWhereArgs);

                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if(numberOfDeletedItems != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfDeletedItems;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private Uri getResponse( long id, Uri tableUri )
    {
        if(id > 0)
        {
            return ContentUris.withAppendedId(tableUri, id);
        }
        else
        {
            throw new android.database.SQLException("Failed to insert row into " + tableUri);
        }
    }
}
