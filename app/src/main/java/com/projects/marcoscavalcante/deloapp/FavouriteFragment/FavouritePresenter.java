package com.projects.marcoscavalcante.deloapp.FavouriteFragment;

import android.content.Loader;
import android.database.Cursor;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import java.util.ArrayList;

public class FavouritePresenter {

    private FavouriteContract.View mFavouriteView;
    private ArrayList<Product> mProducts;
    private static final String TAG = FavouritePresenter.class.getName();


    public FavouritePresenter(FavouriteContract.View favouriteView){
        this.mProducts        = new ArrayList<>();
        this.mFavouriteView   = favouriteView;
    }


    public void loadProducts(){
        if(mFavouriteView != null){
        }
    }

    public void removeProduct(Product product){
        if(mFavouriteView != null){
        }
    }


    public void setProducts(Cursor data) {

        if( data == null ) return;

        while( data.moveToNext() )
        {
            mProducts.add( new Product(data) );
        }

        mFavouriteView.setProducts( mProducts );
    }
}
