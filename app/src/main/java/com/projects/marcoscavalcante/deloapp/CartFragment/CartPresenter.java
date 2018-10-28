package com.projects.marcoscavalcante.deloapp.CartFragment;


import com.projects.marcoscavalcante.deloapp.Model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartPresenter {

    private static final String TAG = CartPresenter.class.getName();
    private CartContract.View mView;

    public CartPresenter(CartContract.View view){
        this.mView = view;
    }

    public void calculatePriceTotal(HashMap<Product, Integer> cart){
        float total = 0;
        for(Map.Entry<Product, Integer> entry : cart.entrySet() ){
            total += entry.getKey().getPrice() * entry.getValue();
        }
        mView.updatePrice( total );
    }


}
