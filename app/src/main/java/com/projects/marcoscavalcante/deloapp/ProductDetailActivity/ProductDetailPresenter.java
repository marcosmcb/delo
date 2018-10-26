package com.projects.marcoscavalcante.deloapp.ProductDetailActivity;

import android.content.Intent;

import com.projects.marcoscavalcante.deloapp.Model.Product;

public class ProductDetailPresenter {

    private ProductDetailContract.View mProductDetailView;
    private static final String TAG = ProductDetailPresenter.class.getName();


    public ProductDetailPresenter(ProductDetailContract.View productDetailView){
        this.mProductDetailView = productDetailView;
    }

    public void unwrapProduct(Intent intent){
        if(mProductDetailView != null) {
            if (intent != null && intent.getParcelableExtra(Intent.EXTRA_TEXT) != null) {
                Product mProduct = intent.getParcelableExtra(Intent.EXTRA_TEXT);

                mProductDetailView.setActionBarTitle( mProduct.getName() );
                mProductDetailView.setImageProduct( mProduct.getProductId() );
                mProductDetailView.setProductName( mProduct.getName() );
                mProductDetailView.setProductPrice( mProduct.getPrice() );
                mProductDetailView.setProductOldPrice( mProduct.getOldPrice() );
                mProductDetailView.setProductCategory( mProduct.getCategory() );
                mProductDetailView.setProductStock( mProduct.getStock() );
            }
        }
    }

    public void onDestroy(){
        mProductDetailView = null;
    }
}
