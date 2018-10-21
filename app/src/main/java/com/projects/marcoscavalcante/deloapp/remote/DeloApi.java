package com.projects.marcoscavalcante.deloapp.remote;


import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import com.projects.marcoscavalcante.deloapp.model.Cart;
import com.projects.marcoscavalcante.deloapp.model.Product;


public interface DeloApi {

    @GET("/products/{id}")
    Observable<Product> getProductById(@Path("id") int id);

    @GET("/products")
    Observable<List<Product>> getProducts();

    @POST("/cart")
    Observable<Cart> addProduct(@Body Cart load);

    @DELETE("/cart/{id}")
    Observable<Cart> removeProduct(@Body Cart load);
}
