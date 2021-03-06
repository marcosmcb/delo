package com.projects.marcoscavalcante.deloapp.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.R;
import com.projects.marcoscavalcante.deloapp.Utils.Pictures;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter< ProductAdapter.ProductsViewHolder > implements Filterable {

    private static final String TAG = ProductAdapter.class.getName();
    private ArrayList<Product> mProductsFiltered;
    private ArrayList<Product> mProducts;
    private Listener mListener;

    public ProductAdapter( ArrayList<Product> products, Listener listener ){
        this.mProductsFiltered = products;
        this.mProducts         = products;
        this.mListener         = listener;
    }


    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        return new ProductsViewHolder( inflater.inflate(R.layout.item_product, parent, false) );
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        final Product mProduct = mProductsFiltered.get(position);

        Log.d( TAG, "PRODUCT => " + mProduct.toString() );

        holder.mTvName.setText( mProduct.getName() );
        holder.mTvNewPrice.setText( "£ " + String.format( "%.2f", mProduct.getPrice() )  );

        if( mProduct.getOldPrice() > 0 ){
            holder.mTvOldPrice.setText( "£ " + String.format( "%.2f", mProduct.getOldPrice() ) );
            holder.mTvOldPrice.setPaintFlags( holder.mTvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked( mProduct );
            }
        });

        Picasso
                .get()
                .load( Pictures.picturesUrl().get( mProduct.getProductId() ) )
                .into( holder.mIvProduct );
    }

    @Override
    public int getItemCount() {
        return mProductsFiltered != null ? mProductsFiltered.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = constraint.toString();
                mProductsFiltered = mProducts;

                if( searchString.isEmpty() ){
                    mProductsFiltered = mProducts;
                } else {
                    ArrayList<Product> filteredList = new ArrayList<>();
                    for(Product product : mProductsFiltered){
                        if( product.getCategory().toLowerCase().contains( searchString.toLowerCase() ) ) {
                            filteredList.add( product );
                        }
                    }
                    mProductsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mProductsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mProductsFiltered = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface Listener{
        void onItemClicked( Product product );
    }


    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_item_product)
        LinearLayout mLayoutItem;

        @BindView(R.id.ll_product_info)
        LinearLayout mLayoutInfo;

        @BindView(R.id.tv_product_name)
        TextView mTvName;

        @BindView(R.id.tv_old_price)
        TextView mTvOldPrice;

        @BindView(R.id.tv_new_price)
        TextView mTvNewPrice;

        @BindView(R.id.ivProduct)
        ImageView mIvProduct;

        public ProductsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
