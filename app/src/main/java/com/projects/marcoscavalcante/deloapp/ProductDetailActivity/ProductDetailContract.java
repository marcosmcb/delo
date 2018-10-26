package com.projects.marcoscavalcante.deloapp.ProductDetailActivity;

import android.content.Intent;

public interface ProductDetailContract {

    interface View{
        void setImageProduct(int id);
        void setProductName(String name);
        void setProductPrice(float price);
        void setProductOldPrice(float oldPrice);
        void setProductStock(int quantity);
        void setProductCategory(String category);
        void setActionBarTitle(String productName);
    }
}
