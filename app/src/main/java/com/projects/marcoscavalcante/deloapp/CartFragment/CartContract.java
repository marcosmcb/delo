package com.projects.marcoscavalcante.deloapp.CartFragment;


import com.projects.marcoscavalcante.deloapp.Model.Cart;
import com.projects.marcoscavalcante.deloapp.Model.Product;

import java.util.HashMap;

public interface CartContract {

    interface View{
        void updatePrice(float newPrice);
        void showErrorMessage(String msg);
        void showMessage(String msg);
        void showProgress();
        void hideProgress();
        void destroyCart();
    }


    interface Repository{

        interface OnFinishedListener {
            void onFinished( Cart cart );
            void onFailure( Throwable t );
        }

        void sendProducts(CartContract.Repository.OnFinishedListener onCallbackListener, HashMap<Product, Integer> products);
    }

}
