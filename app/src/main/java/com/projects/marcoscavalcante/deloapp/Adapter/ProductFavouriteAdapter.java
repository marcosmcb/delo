package com.projects.marcoscavalcante.deloapp.Adapter;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductFavouriteAdapter extends RecyclerView.Adapter<ProductFavouriteAdapter.ProductFavouriteViewHolder> {

    private ArrayList<Product> mProductsArrayList;
    private Listener mListener;

    public ProductFavouriteAdapter(ArrayList<Product> products, Listener listener){
        this.mProductsArrayList = products;
        this.mListener          = listener;
    }


    public interface Listener{
        void onProductAddedToCart( Product product);
        void onProductRemoved(Product product);
        void onError(String message);
    }

    @NonNull
    @Override
    public ProductFavouriteAdapter.ProductFavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        return new ProductFavouriteAdapter.ProductFavouriteViewHolder( inflater.inflate(R.layout.item_favourite, parent, false) );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductFavouriteViewHolder holder, int position) {
        final Product mProduct = mProductsArrayList.get( position );

        holder.mTvName.setText( mProduct.getName() );
        holder.mTvNewPrice.setText( "£ " + String.format( "%.2f", mProduct.getPrice() )  );

        if( mProduct.getOldPrice() > 0 ){
            holder.mTvOldPrice.setText( "£ " + String.format( "%.2f", mProduct.getOldPrice() ) );
            holder.mTvOldPrice.setPaintFlags( holder.mTvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        Picasso
                .get()
                .load( Pictures.picturesUrl().get( mProduct.getProductId() ) )
                .into( holder.mIvProductFavourite );

        holder.mBtnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mProduct.getStock() > 0 ){
                    mListener.onProductAddedToCart( mProduct );
                } else {
                    mListener.onError("Sorry, Product is out of stock!");
                }

            }
        });

        holder.mBtnRemoveFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onProductRemoved( mProduct );
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductsArrayList == null ? 0 : mProductsArrayList.size();
    }


    public class ProductFavouriteViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_product_name_favourite)
        TextView mTvName;

        @BindView(R.id.tv_old_price_favourite)
        TextView mTvOldPrice;

        @BindView(R.id.tv_new_price_favourite)
        TextView mTvNewPrice;

        @BindView(R.id.ivProductFavourite)
        ImageView mIvProductFavourite;

        @BindView(R.id.btn_add_cart_favourite)
        Button mBtnAddCart;

        @BindView(R.id.btn_remove_favourite)
        Button mBtnRemoveFavourite;

        public ProductFavouriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
