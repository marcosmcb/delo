package com.projects.marcoscavalcante.deloapp.ProductFragment;

import com.projects.marcoscavalcante.deloapp.Model.Product;
import java.util.ArrayList;

public interface ProductsContract {

    interface View{
        void showProgress();
        void hideProgress();
        void setProductsAdapter(ArrayList<Product> productArrayList);
        void showErrorMessage(String msg);
    }


    interface Repository{

        interface OnFinishedListener {
            void onFinished( ArrayList<Product> productArrayList );
            void onFailure( Throwable t );
        }

        void getProductsArrayList( OnFinishedListener onCallbackListener );
    }

}
