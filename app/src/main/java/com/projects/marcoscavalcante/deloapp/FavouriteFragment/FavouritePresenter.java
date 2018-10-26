package com.projects.marcoscavalcante.deloapp.FavouriteFragment;

import android.content.ContentResolver;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import com.projects.marcoscavalcante.deloapp.Main.App;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.Data.FavouriteContract.FavouriteEntry;
import java.util.ArrayList;

public class FavouritePresenter {

    private FavouriteContract.View mFavouriteView;
    private ContentResolver mContentResolver;
    private ArrayList<Product> mProducts;
    private static final String TAG = FavouritePresenter.class.getName();


    public FavouritePresenter(FavouriteContract.View favouriteView){
        this.mFavouriteView   = favouriteView;
        this.mContentResolver = App.getAppContext().getContentResolver();
    }


    public void removeProduct(Product product){
        if(mFavouriteView != null){
            Uri uri = FavouriteEntry.CONTENT_URI.buildUpon().appendPath( product.getProductId()+"" ).build();

            int hasRemoved = mContentResolver.delete(uri, null, null);

            if(hasRemoved > 0){
                mFavouriteView.showRemoveSuccessful();
            }
        }
    }


    public void setProducts(Cursor data) {
        if( data == null ) return;
        mProducts = new ArrayList<>();
        while( data.moveToNext() )
        {
            mProducts.add( new Product(data) );
        }

        mFavouriteView.setProducts( mProducts );
    }
}
