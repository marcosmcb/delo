package com.projects.marcoscavalcante.deloapp.FavouriteFragment;

import com.projects.marcoscavalcante.deloapp.Model.Product;

import java.util.ArrayList;

public interface FavouriteContract {

    interface View{
        void showProgress();
        void hideProgress();
        void setProducts(ArrayList<Product> products);
    }
}
