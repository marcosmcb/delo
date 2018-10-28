package com.projects.marcoscavalcante.deloapp.CartFragment;


import android.util.Log;

import com.projects.marcoscavalcante.deloapp.Model.Cart;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import java.util.HashMap;
import java.util.Map;

import retrofit2.HttpException;

public class CartPresenter implements CartContract.Repository.OnFinishedListener {

    private static final String TAG = CartPresenter.class.getName();
    private CartContract.View mView;
    private CartContract.Repository mRepository;
    private int numProductsSent;

    public CartPresenter(CartContract.View view){
        this.mView = view;
        this.mRepository = new CartRepository();
    }

    public void calculatePriceTotal(HashMap<Product, Integer> cart){
        float total = 0;
        if(mView != null) {
            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                total += entry.getKey().getPrice() * entry.getValue();
            }
            mView.updatePrice(total);
        }
    }


    public void sendCart(HashMap<Product, Integer> cart){
        if(mView != null){
            mView.showProgress();
            mRepository.sendProducts( this,  cart );
        }
    }

    @Override
    public void onFinished(Cart cart) {
        if(mView != null){
            mView.hideProgress();
            mView.showMessage("PRODUCTS HAVE BEEN SENT");
            mView.destroyCart();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mView != null) {
            mView.hideProgress();
            if (t instanceof HttpException) {

                HttpException httpException = (HttpException) t;
                switch (httpException.code()) {
                    case 400:
                        // BAD Request
                        mView.showErrorMessage( "Problem with request sent" );
                        break;
                    case 500:
                        // Internal Server Error
                        mView.showErrorMessage( "Problem while processing request" );
                        break;
                    default:
                        mView.showErrorMessage( "Requested cannot be processed for unknown reason" );
                        break;
                }
            }
        }
        Log.d( TAG, t.getMessage() );
    }
}
