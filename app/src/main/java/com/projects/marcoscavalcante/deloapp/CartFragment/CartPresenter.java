package com.projects.marcoscavalcante.deloapp.CartFragment;


import java.util.ArrayList;

public class CartPresenter {

    private static final String TAG = CartPresenter.class.getName();
    private CartContract.View mView;

    public CartPresenter(CartContract.View view){
        this.mView = view;
    }

}
