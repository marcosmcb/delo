package com.projects.marcoscavalcante.deloapp.ProductFragment;

import android.util.Log;

import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.Remote.DeloApi;
import com.projects.marcoscavalcante.deloapp.Remote.NetworkApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ProductRepository implements ProductsContract.Repository
{
    private DeloApi mApi;
    private static final String TAG = ProductRepository.class.getName();
    private static final int NUM_RETRIES = 10;

    public ProductRepository(){
        mApi = NetworkApi.getInstance().getDeloApi();
    }


    @Override
    public void getProductsArrayList(OnFinishedListener onCallbackListener) {
        getObservableList().subscribeWith( getObserverList(onCallbackListener) );
    }

    public Observable< List<Product> > getObservableList() {
        return mApi
                .getProducts()
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .retry( NUM_RETRIES );
    }

    public DisposableObserver< List<Product> > getObserverList(final OnFinishedListener onCallbackListener) {

        return new DisposableObserver<List<Product>>() {
            @Override
            public void onNext(List<Product> products) {
                onCallbackListener.onFinished( new ArrayList<>(products) );
            }

            @Override
            public void onError(Throwable e) {
                onCallbackListener.onFailure( e );
            }

            @Override
            public void onComplete() {
                Log.d( TAG, "Products Finished Retrieving" );
            }
        };
    }




}
