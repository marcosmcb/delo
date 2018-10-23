package com.projects.marcoscavalcante.deloapp.Remote;


import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import com.projects.marcoscavalcante.deloapp.Model.Cart;
import com.projects.marcoscavalcante.deloapp.Model.Product;


public interface DeloApi {

    @GET("/menu/{id}")
    Observable<Product> getProductById(@Path("id") int id);

    @GET("/menu")
    Observable<List<Product>> getProducts();

    @POST("/cart")
    Observable<Cart> addProduct(@Body Cart load);

    @DELETE("/cart/{id}")
    Observable<Cart> removeProduct(@Body Cart load);
}
