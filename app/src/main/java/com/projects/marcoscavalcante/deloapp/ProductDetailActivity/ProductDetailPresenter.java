package com.projects.marcoscavalcante.deloapp.ProductDetailActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.projects.marcoscavalcante.deloapp.Data.FavouriteContract.FavouriteEntry;
import com.projects.marcoscavalcante.deloapp.Main.App;
import com.projects.marcoscavalcante.deloapp.Model.Product;

public class ProductDetailPresenter {

    private ProductDetailContract.View mProductDetailView;
    private Product mProduct;
    private ContentResolver mContentResolver;
    private static final String TAG = ProductDetailPresenter.class.getName();


    public ProductDetailPresenter(ProductDetailContract.View productDetailView){
        this.mProductDetailView = productDetailView;
        this.mContentResolver = App.getAppContext().getContentResolver();
    }

    public void unwrapProduct(Intent intent){
        if(mProductDetailView != null) {
            if (intent != null && intent.getParcelableExtra(Intent.EXTRA_TEXT) != null) {
                mProduct = intent.getParcelableExtra(Intent.EXTRA_TEXT);

                mProductDetailView.setActionBarTitle( mProduct.getName() );
                mProductDetailView.setImageProduct( mProduct.getProductId() );
                mProductDetailView.setProductName( mProduct.getName() );
                mProductDetailView.setProductPrice( mProduct.getPrice() );
                mProductDetailView.setProductOldPrice( mProduct.getOldPrice() );
                mProductDetailView.setProductCategory( mProduct.getCategory() );
                mProductDetailView.setProductStock( mProduct.getStock() );

                isProductInDb( mProduct.getProductId() );
            }
        }
    }

    public boolean isProductInDb(int productId){
        String mSelection = FavouriteEntry.COLUMN_ID + " = " + productId;
        Cursor mCursor = null;
        boolean isPresent;

        try
        {
            Log.d(TAG, "Performing QUERY to get product by ID");
            mCursor = mContentResolver.query( FavouriteEntry.CONTENT_URI,
                    null,
                    mSelection,
                    null,
                    FavouriteEntry.COLUMN_ID);
        }
        catch(Exception e)
        {
            Log.e(TAG, "Failed to query data");
            e.printStackTrace();
        }


        isPresent = (mCursor != null && mCursor.moveToFirst());
        mProductDetailView.setIsFavourite( isPresent );

        return isPresent;

    }

    public void onDestroy(){
        mProductDetailView = null;
    }

    public void addToFavourites(){

        if( isProductInDb(mProduct.getProductId()) ){
            mProductDetailView.showMessage("Product is already in Favourite list");
            Log.d(TAG,"**********Product is already in Favourite list******" );
            return;
        }

        Uri resultUri = mContentResolver.insert( FavouriteEntry.CONTENT_URI, mProduct.getContentValues() );


        if( resultUri != null ){
            mProductDetailView.setIsFavourite( true );
            mProductDetailView.showMessage("Product added to Favourites!");
        } else {
            mProductDetailView.showMessage("Product cannot be added :(");
        }
    }


    public void addToCart(){
        if(mProductDetailView != null){
            if(mProduct.getStock() > 0){
                mProductDetailView.showMessage("Product has been added to Cart!");
                mProductDetailView.addProduct( mProduct );
            } else {
                mProductDetailView.showMessage("Sorry, Product is out of stock!");
            }
        }
    }
}
