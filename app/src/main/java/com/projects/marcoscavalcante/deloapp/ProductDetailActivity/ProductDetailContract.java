package com.projects.marcoscavalcante.deloapp.ProductDetailActivity;


import com.projects.marcoscavalcante.deloapp.Model.Product;

public interface ProductDetailContract {

    interface View{
        void setImageProduct(int id);
        void setProductName(String name);
        void setProductPrice(float price);
        void setProductOldPrice(float oldPrice);
        void setProductStock(int quantity);
        void setProductCategory(String category);
        void setActionBarTitle(String productName);
        void showMessage(String message);
        void setIsFavourite(boolean isFavourite);
        void addProduct(Product product);
    }
}
