package com.projects.marcoscavalcante.deloapp.FavouriteFragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.projects.marcoscavalcante.deloapp.Adapter.ProductFavouriteAdapter;
import com.projects.marcoscavalcante.deloapp.Main.MainActivity;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.R;
import com.projects.marcoscavalcante.deloapp.Utils.BaseFragment;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FavouriteFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>,
        FavouriteContract.View, ProductFavouriteAdapter.Listener {

    @BindView(R.id.rv_favourite)
    RecyclerView mRecyclerView;

    private static final String TAG    = FavouriteFragment.class.getName();
    private static final int LOADER_ID = 11;
    private FavouritePresenter mFavouritePresenter;
    private ProgressBar mProgressBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind( FavouriteFragment.this, mRootView );
        ( (MainActivity) getActivity()).setDrawableIndicator(false);


        bindViewFromActivity();
        mFavouritePresenter = new FavouritePresenter( this );
    }


    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(LOADER_ID, null, this).forceLoad();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_favourites;
    }

    @Override
    public void onProductAddedToCart(Product product) {

    }

    @Override
    public void onProductRemoved(Product product) {
        mFavouritePresenter.removeProduct(product);
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
    public void setProducts(ArrayList<Product> products) {
        mRecyclerView.setLayoutManager( new GridLayoutManager( getContext(), getNumberOfColumns() ));
        mRecyclerView.setAdapter( new ProductFavouriteAdapter( products, this ));
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void bindViewFromActivity() {
        mProgressBar = getActivity().findViewById(R.id.pb_products_fragment);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        showProgress();
        return new FavouriteLoader( this.getContext() );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mFavouritePresenter.setProducts( data );
        hideProgress();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
