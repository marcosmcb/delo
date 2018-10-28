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
import android.widget.Button;
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

    @BindView(R.id.btn_cart_cancel)
    Button mBtnCancel;

    @BindView(R.id.btn_cart_checkout)
    Button mBtnCheckout;

    private ProgressBar mProgressBar;
    private static final String TAG = CartFragment.class.getName();
    private CartPresenter mPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind( CartFragment.this, mRootView );
        ( (MainActivity) getActivity()).setDrawableIndicator(false);
        mProgressBar = getActivity().findViewById(R.id.pb_products_fragment);

        if(mPresenter == null) {
            mPresenter = new CartPresenter(this);
        }
        setItems( ((MainActivity) getActivity()).retrieveCart() );
        
        setListeners();
    }

    private void setListeners() {

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destroyCart();
            }
        });

        mBtnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<Product, Integer> cart = ((ProductCartAdapter) mRecyclerView.getAdapter()).getCart();

                if(cart.size() == 0){
                    showErrorMessage("There is nothing on the cart to be sent");
                } else {
                    mPresenter.sendCart(cart);
                }
            }
        });
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

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void destroyCart() {
        ( (ProductCartAdapter) mRecyclerView.getAdapter()).destroyCart();
        updatePrice(0);
    }

    private void updateCartPrice() {
        HashMap<Product, Integer> cart = ( (ProductCartAdapter) mRecyclerView.getAdapter()).getCart();
        mPresenter.calculatePriceTotal( cart );
    }
}
