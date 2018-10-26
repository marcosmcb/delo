package com.projects.marcoscavalcante.deloapp.ProductFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.projects.marcoscavalcante.deloapp.Adapter.ProductAdapter;
import com.projects.marcoscavalcante.deloapp.Main.MainActivity;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.ProductDetailActivity.ProductDetailActivity;
import com.projects.marcoscavalcante.deloapp.R;
import com.projects.marcoscavalcante.deloapp.Utils.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductFragment extends BaseFragment implements ProductsContract.View,
        ProductAdapter.Listener {

    @BindView(R.id.rv_product)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_products_fragment)
    TextView mTvErrorMessage;


    private static final String TAG = ProductFragment.class.getName();
    private ProductsPresenter mPresenter;
    private ProgressBar mProgressBar;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind( ProductFragment.this, mRootView );

        bindViewFromActivity();

        mPresenter = new ProductsPresenter(this);
        mPresenter.retrieveProducts();
    }

    private void bindViewFromActivity() {
        ( (MainActivity) getActivity()).setDrawableIndicator(true);
        mProgressBar = getActivity().findViewById(R.id.pb_products_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.retrieveProducts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_products;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility( View.VISIBLE );
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility( View.GONE );
    }

    @Override
    public void setProductsAdapter(ArrayList<Product> productArrayList) {
        mRecyclerView.setLayoutManager( new GridLayoutManager( getContext(), getNumberOfColumns() ));
        mRecyclerView.setAdapter( new ProductAdapter(productArrayList, this ));
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(String msg) {
        mTvErrorMessage.setText(msg);
    }

    @Override
    public void displayProduct(Product product) {
        // Go to Different Activity
        Intent mIntent = new Intent( getActivity(), ProductDetailActivity.class);
        mIntent.putExtra( Intent.EXTRA_TEXT, product );
        startActivity( mIntent );
    }

    @Override
    public void onItemClicked(Product product) {
        mPresenter.onItemClicked(product);
    }

    public void filterItemsByCategory(String category){
        if(mRecyclerView.getAdapter() != null) {
            ((ProductAdapter) mRecyclerView.getAdapter()).getFilter().filter(category);
        }
    }
}
