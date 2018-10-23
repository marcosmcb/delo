package com.projects.marcoscavalcante.deloapp.Model;

import java.util.HashMap;

public class Cart {

    private int cartId;
    private HashMap<Product, Integer> products;

    public Cart() {
    }

    public Cart(int cartId, HashMap<Product, Integer> products) {
        this.cartId = cartId;
        this.products = products;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }
}
