package com.projects.marcoscavalcante.deloapp.Adapter;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.R;
import com.projects.marcoscavalcante.deloapp.Utils.Pictures;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder> {

    private ArrayList<Product> mProducts;
    private HashMap<Integer, Integer> mProductsQuantity;
    private static final String TAG = ProductCartAdapter.class.getName();
    private Listener mListener;

    public ProductCartAdapter(ArrayList<Product> products, Listener listener){
        this.mProducts = products;
        this.mListener = listener;
        if( this.mProductsQuantity == null ){
            initializeQuantities();
        }
    }

    public void initializeQuantities(){
        mProductsQuantity = new HashMap<>();
        for(Product product : mProducts){
            mProductsQuantity.put( product.getProductId(), 1 );
        }
    }


    public interface Listener{
        void onChange();
        void onError(String message);
    }

    public HashMap<Product, Integer> getCart(){
        HashMap<Product, Integer> productsCart = new HashMap<>();
        for( Product prod : mProducts ){
            productsCart.put( prod, mProductsQuantity.get(prod.getProductId()) );
        }
        return productsCart;
    }


    public ProductCartAdapter.ProductCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        return new ProductCartAdapter.ProductCartViewHolder( inflater.inflate(R.layout.item_product_cart, parent, false) );
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductCartViewHolder holder, final int position) {
        final Product mProduct = mProducts.get( position );
        final int currQuantity = mProductsQuantity.get(mProduct.getProductId());

        holder.mTvName.setText( mProduct.getName() );
        holder.mTvNewPrice.setText( "£ " + String.format( "%.2f", mProduct.getPrice() )  );

        if( mProduct.getOldPrice() > 0 ){
            holder.mTvOldPrice.setText( "£ " + String.format( "%.2f", mProduct.getOldPrice() ) );
            holder.mTvOldPrice.setPaintFlags( holder.mTvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.mTvQuantityCart.setText( mProductsQuantity.get( mProduct.getProductId() ) + "" );

        Picasso
                .get()
                .load( Pictures.picturesUrl().get( mProduct.getProductId() ) )
                .into( holder.mIvProductCart );


        holder.mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if( (currQuantity+1) <= mProduct.getStock() ){
                mProductsQuantity.put( mProduct.getProductId(), currQuantity+1 );
                holder.mTvQuantityCart.setText( (currQuantity+1) + "" );
                notifyItemChanged( position );
                mListener.onChange();
            } else {
                mListener.onError("Sorry, Only " + mProduct.getStock() + " in stock!");
            }
            }
        });

        holder.mBtnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if( (currQuantity-1) > 0 ){
                mProductsQuantity.put( mProduct.getProductId(), currQuantity-1 );
                holder.mTvQuantityCart.setText( (currQuantity-1) + "" );
                notifyItemChanged( position );
                mListener.onChange();
            } else {
                mListener.onError("Sorry, you can't have 0 quantity");
            }
            }
        });

        holder.mBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductsQuantity.remove( mProduct.getProductId() );
                mProducts.remove( position );
                notifyDataSetChanged();
                mListener.onChange();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mProducts == null ? 0 : mProducts.size();
    }


    public class ProductCartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_quantity_cart)
        TextView mTvQuantityCart;

        @BindView(R.id.tv_old_price_cart)
        TextView mTvOldPrice;

        @BindView(R.id.tv_new_price_cart)
        TextView mTvNewPrice;

        @BindView(R.id.tv_product_name_cart)
        TextView mTvName;

        @BindView(R.id.ivProductCart)
        ImageView mIvProductCart;

        @BindView(R.id.btn_plus_cart)
        Button mBtnPlus;

        @BindView(R.id.btn_sub_cart)
        Button mBtnSub;

        @BindView(R.id.btn_remove_product_cart)
        Button mBtnRemove;

        public ProductCartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
