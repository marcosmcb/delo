package com.projects.marcoscavalcante.deloapp.CartFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.marcoscavalcante.deloapp.Adapter.ProductCartAdapter;
import com.projects.marcoscavalcante.deloapp.Adapter.ProductFavouriteAdapter;
import com.projects.marcoscavalcante.deloapp.Main.MainActivity;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.R;
import com.projects.marcoscavalcante.deloapp.Utils.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends BaseFragment implements CartContract.View, ProductCartAdapter.Listener {

    @BindView(R.id.rv_cart)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_price_cart)
    TextView mTvPriceCart;

    private ProgressBar mProgressBarMainActivity;
    private TextView mTextViewErrorMessage;
    private SearchView mSearchView;
    private static final String TAG = CartFragment.class.getName();
    private CartPresenter mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind( CartFragment.this, mRootView );
        ( (MainActivity) getActivity()).setDrawableIndicator(false);


        if(mPresenter == null) {
            mPresenter = new CartPresenter(this);
        }
        setItems( ((MainActivity) getActivity()).retrieveCart() );
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_cart;
    }

    public void setItems(ArrayList<Product> productsCart) {
        mRecyclerView.setLayoutManager( new LinearLayoutManager( getContext() ));
        mRecyclerView.setAdapter( new ProductCartAdapter( productsCart, this ));
        mRecyclerView.getAdapter().notifyDataSetChanged();
        updateCartPrice();
    }

    @Override
    public void onChange() {
        updateCartPrice();
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatePrice(float newPrice) {
        mTvPriceCart.setText( "£ " + String.format("%.2f", newPrice) );
    }

    private void updateCartPrice() {
        HashMap<Product, Integer> cart = ( (ProductCartAdapter) mRecyclerView.getAdapter()).getCart();
        mPresenter.calculatePriceTotal( cart );
    }
}
