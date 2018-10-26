package com.projects.marcoscavalcante.deloapp.FavouriteFragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.projects.marcoscavalcante.deloapp.Data.FavouriteContract;

public class FavouriteLoader extends AsyncTaskLoader<Cursor> {

    private static final String TAG = FavouriteLoader.class.getSimpleName();
    private ContentResolver mContentResolver;
    private Cursor mData;

    public FavouriteLoader( Context context ){
        super(context);

        this.mData            = null;
        this.mContentResolver = context.getContentResolver();
    }


    @Override
    protected void onStartLoading() {
        if(mData != null){
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        try {
            return mContentResolver.query( FavouriteContract.FavouriteEntry.CONTENT_URI,
                    null,null,null, null);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void deliverResult(Cursor data) {
        mData = data;
        super.deliverResult(mData);
    }
}
