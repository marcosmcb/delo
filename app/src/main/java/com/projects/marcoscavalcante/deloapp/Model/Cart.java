package com.projects.marcoscavalcante.deloapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("cartId")
    @Expose
    private int cartId;

    @SerializedName("productId")
    @Expose
    private int productId;

    public Cart(int cartId, int productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    @Override
    public String toString() {
        return "{ cartId: " + cartId + ", productId: " + productId + " }";
    }
}
