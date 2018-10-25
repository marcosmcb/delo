package com.projects.marcoscavalcante.deloapp.ProductFragment;


import android.util.Log;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import java.util.ArrayList;
import retrofit2.HttpException;

public class ProductsPresenter implements ProductsContract.Repository.OnFinishedListener {

    private ProductsContract.View mProductsView;
    private ProductsContract.Repository mProduRepository;
    private static final String TAG = ProductsPresenter.class.getName();

    public ProductsPresenter(ProductsContract.View view){
        this.mProductsView = view;
        this.mProduRepository = new ProductRepository();
    }


    public void retrieveProducts(){
        if(mProductsView != null){
            mProductsView.showProgress();
            mProduRepository.getProductsArrayList(this);
        }
    }


    @Override
    public void onFinished(ArrayList<Product> productArrayList) {
        if(mProductsView != null){
            mProductsView.setProductsAdapter( productArrayList );
            mProductsView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mProductsView != null) {
            if (t instanceof HttpException) {

                HttpException httpException = (HttpException) t;
                switch (httpException.code()) {
                    case 400:
                        // BAD Request
                        mProductsView.showErrorMessage( "Problem with request sent" );
                        break;
                    case 500:
                        // Internal Server Error
                        mProductsView.showErrorMessage( "Problem while processing request" );
                        break;
                    default:
                        mProductsView.showErrorMessage( "Requested cannot be processed for unknown reason" );
                        break;
                }
            }
        }
        Log.d( TAG, t.getMessage() );
    }


    public void onItemClicked(Product product){
        if(mProductsView != null){
            mProductsView.displayProduct(product);
        }
    }

    public void onDestroy(){
        mProductsView = null;
    }
}
