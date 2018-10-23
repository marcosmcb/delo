package com.projects.marcoscavalcante.deloapp.ProductFragment;

import android.util.Log;

import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.Remote.NetworkApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductsPresenter {

    private static final String TAG = ProductsPresenter.class.getName();
    private static final int NUM_RETRIES = 10;
    private List<Product> mProductList;
    private ProductsView mProductsView;

    public ProductsPresenter(ProductsView productsView) {
        this.mProductsView = productsView;
    }


    public void onResume(){
        if(mProductsView != null){
            getObservableList().subscribeWith( getObserverList() );
        }
    }


    public Observable< List<Product> > getObservableList() {
        return NetworkApi
                .getInstance()
                .getDeloApi()
                .getProducts()
                .subscribeOn( Schedulers.io() )
                .observeOn(AndroidSchedulers.mainThread())
                .retry( NUM_RETRIES );
    }

    public DisposableObserver< List<Product> > getObserverList() {
        return new DisposableObserver<List<Product>>() {
            @Override
            public void onNext(List<Product> products) {
                Log.d( TAG,"Products have been retrieved => SIZE[" + products.size() + "]" );
                for( Product product : products ){
                    Log.d(TAG, "Product String => " + product.getName() );
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e( TAG, "Error retrieving Products => " + e );
            }

            @Override
            public void onComplete() {
                Log.d( TAG, "Products retrieved" );
            }
        };
    }


    public void onDestroy(){
        this.mProductsView = null;
    }
}
