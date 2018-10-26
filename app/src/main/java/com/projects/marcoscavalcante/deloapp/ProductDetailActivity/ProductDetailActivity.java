package com.projects.marcoscavalcante.deloapp.ProductDetailActivity;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.marcoscavalcante.deloapp.R;
import com.projects.marcoscavalcante.deloapp.Utils.Pictures;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailContract.View {

    @BindView(R.id.ivProductDetail)
    ImageView mImageViewProduct;

    @BindView(R.id.tv_product_name_detail)
    TextView mTextViewName;

    @BindView(R.id.tv_old_price_detail)
    TextView mTextViewOldPrice;

    @BindView(R.id.tv_new_price_detail)
    TextView mTextViewNewPrice;

    @BindView(R.id.tv_category_detail)
    TextView mTextViewCategory;

    @BindView(R.id.tv_stock_detail)
    TextView mTextViewStock;

    @BindView(R.id.btn_add_cart_detail)
    Button mButtonAddCart;

    @BindView(R.id.btn_favourite_detail)
    Button mButtonFavourite;

    @BindView(R.id.ll_old_price_details)
    LinearLayout mLinearLayoutOldPrice;

    private static final String TAG = ProductDetailActivity.class.getName();
    private ProductDetailPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details );
        ButterKnife.bind(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = new ProductDetailPresenter(this);
        mPresenter.unwrapProduct( getIntent() );

        setListeners();

    }


    @Override
    public void setImageProduct(int id) {
        Picasso
                .get()
                .load( Pictures.picturesUrl().get( id ) )
                .into( mImageViewProduct );
    }

    @Override
    public void setProductName(String name) {
        mTextViewName.setText(name);
    }

    @Override
    public void setProductPrice(float price) {
        mTextViewNewPrice.setText( "£ " + String.format("%.2f", price) );
    }

    @Override
    public void setProductOldPrice(float oldPrice) {
        if(oldPrice > 0){
            mTextViewOldPrice.setText( "£ " + String.format("%.2f", oldPrice) );
            mTextViewOldPrice.setPaintFlags( mTextViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
        } else {
            mLinearLayoutOldPrice.setVisibility(View.GONE);
        }
    }

    @Override
    public void setProductStock(int quantity) {
        if(quantity > 0){
            mTextViewStock.setText( quantity+"" );
        } else {
            mTextViewStock.setText( getString(R.string.message_out_of_stock) );
        }
    }

    @Override
    public void setProductCategory(String category) {
        mTextViewCategory.setText(category);
    }

    @Override
    public void setActionBarTitle(String productName) {
        getSupportActionBar().setTitle(productName);
    }


    private void setListeners() {

        mButtonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getApplicationContext(), "Added to Favourite", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getIntent().addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
