package com.projects.marcoscavalcante.deloapp.CartFragment;

import android.util.Log;

import com.projects.marcoscavalcante.deloapp.Model.Cart;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.Remote.DeloApi;
import com.projects.marcoscavalcante.deloapp.Remote.NetworkApi;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CartRepository implements CartContract.Repository {

    private DeloApi mApi;
    private static final String TAG = CartRepository.class.getName();
    private static final int NUM_RETRIES = 10;

    public CartRepository(){
        this.mApi = NetworkApi.getInstance().getDeloApi();
    }

    @Override
    public void sendProducts(OnFinishedListener onCallbackListener, HashMap<Product, Integer> products) {
        for( Product product : products.keySet() ){
            sendProduct(product.getProductId()).subscribeWith( sendProductObserver(onCallbackListener) );
        }
    }


    public Observable<Cart> sendProduct(int productId) {
        return mApi
                .addProduct(productId)
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .retry( NUM_RETRIES );
    }

    public DisposableObserver< Cart > sendProductObserver(final OnFinishedListener onCallbackListener) {

        return new DisposableObserver<Cart>() {
            @Override
            public void onNext(Cart cart) {
                Log.d(TAG, "CART => " + cart.toString() );
                onCallbackListener.onFinished( cart );
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
