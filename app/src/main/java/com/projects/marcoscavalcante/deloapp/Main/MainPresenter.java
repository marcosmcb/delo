package com.projects.marcoscavalcante.deloapp.Main;

import com.projects.marcoscavalcante.deloapp.Model.Product;

import java.util.HashMap;

public class MainPresenter {

    private static final String TAG = MainPresenter.class.getName();
    private MainContract.View mMainView;
    private HashMap<Integer, Product> mProducstInCart;

    public MainPresenter(MainContract.View mainView){
        this.mMainView = mainView;
        this.mProducstInCart = new HashMap<>();
    }

    public void addProductToCart(Product product){
        if(mMainView != null){
            if( !mProducstInCart.containsKey( product.getProductId() ) ){
                mProducstInCart.put( product.getProductId(), product );
            }
        }
    }

    public HashMap<Integer, Product> retrieveCart(){
        return mProducstInCart;
    }
}
