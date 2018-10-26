package com.projects.marcoscavalcante.deloapp.ProductDetailActivity;


import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailContract.View
{

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

    @BindView(R.id.tv_stock_label_detail)
    TextView mTextViewStockLabel;

    @BindView(R.id.tv_category_label_detail)
    TextView mTextViewCategoryLabel;

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

    @Override
    public void showMessage(String message) {
        Toast.makeText( getApplicationContext(), message, Toast.LENGTH_LONG ).show();
    }


    @Override
    public void setIsFavourite(boolean isFavourite) {
        Drawable img = null;
        if(isFavourite){
            img = getApplicationContext().getResources().getDrawable( R.drawable.ic_heart );
            mButtonFavourite.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null );
        } else {
            img = getApplicationContext().getResources().getDrawable( R.drawable.ic_heart_outlined );
            mButtonFavourite.setCompoundDrawablesWithIntrinsicBounds( img, null, null, null );
        }
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
                mPresenter.addToFavourites();
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
