package com.projects.marcoscavalcante.deloapp.Main;

import com.projects.marcoscavalcante.deloapp.Model.Product;
import java.util.ArrayList;

public class MainPresenter {

    private static final String TAG = MainPresenter.class.getName();
    private MainContract.View mMainView;
    private ArrayList<Product> mProducstInCart;

    public MainPresenter(MainContract.View mainView){
        this.mMainView = mainView;
        this.mProducstInCart = new ArrayList<>();
    }

    public void addProductToCart(Product product){
        if(mMainView != null){
            if( !isProductInCart(product) ){
                mProducstInCart.add( product );
            }
        }
    }


    private boolean isProductInCart(Product productToBeInserted){
        for(Product product : mProducstInCart){
            if(productToBeInserted.getProductId() == product.getProductId() ){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> retrieveCart(){
        return mProducstInCart;
    }
}
