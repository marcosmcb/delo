package com.projects.marcoscavalcante.deloapp.ProductDetailActivity;


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
    }
}
