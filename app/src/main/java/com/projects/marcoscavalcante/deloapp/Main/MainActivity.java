package com.projects.marcoscavalcante.deloapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.projects.marcoscavalcante.deloapp.CartFragment.CartFragment;
import com.projects.marcoscavalcante.deloapp.FavouriteFragment.FavouriteFragment;
import com.projects.marcoscavalcante.deloapp.Model.Product;
import com.projects.marcoscavalcante.deloapp.ProductFragment.ProductFragment;
import com.projects.marcoscavalcante.deloapp.R;
import com.projects.marcoscavalcante.deloapp.Utils.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.bn_products)
    BottomNavigationView mBottomNavigationView;

    @BindView(R.id.pb_products_fragment)
    ProgressBar mProgressBar;

    private static final String TAG = MainActivity.class.getName();
    private ActionBarDrawerToggle mToggle;
    private BaseFragment mCurrentFragment;
    private MainPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();



        mNavigationView.setNavigationItemSelectedListener(this);
        setBottomNavListener();

        if( mPresenter == null ){
            mPresenter = new MainPresenter(this);
        }

        mCurrentFragment = new ProductFragment();
        setFragment( mCurrentFragment );

        checkForProduct( getIntent() );
    }

    public void checkForProduct(Intent intent) {
        if(intent != null && intent.getParcelableExtra(Intent.EXTRA_TEXT) != null) {
            Product product = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            addProductToCart( product );
        }
    }

    public void addProductToCart(Product product){
        mPresenter.addProductToCart( product );
    }

    public ArrayList<Product> retrieveCart(){
        return mPresenter.retrieveCart();
    }

    public void setDrawableIndicator(boolean isEnabled){
        mToggle.setDrawerIndicatorEnabled(isEnabled);
        mToggle.syncState();
    }


    @Override
    protected void onDestroy() {
        Log.d( TAG, "Destroy Activity" );
        mPresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch( id ){

            case R.id.nav_men_footwear:
                sendCategory( getString( R.string.men_footwear ) );
                break;
            case R.id.nav_men_casualwear:
                sendCategory( getString( R.string.men_casualwear ) );
                break;
            case R.id.nav_men_formalwear:
                sendCategory( getString( R.string.men_formalwear ) );
                break;

            case R.id.nav_women_footwear:
                sendCategory( getString( R.string.women_footwear ) );
                break;
            case R.id.nav_women_casualwear:
                sendCategory( getString( R.string.women_casualwear ) );
                break;
            case R.id.nav_women_formalwear:
                sendCategory( getString( R.string.women_formalwear ) );
                break;
            case R.id.nav_all:
                sendCategory( getString( R.string.all_products ) );
                break;
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendCategory(String category) {
        if(this.mCurrentFragment instanceof ProductFragment){
            ((ProductFragment) mCurrentFragment).filterItemsByCategory( category );
        }
    }


    private void setBottomNavListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_all_products:
                        setFragment( new ProductFragment() );
                        break;

                    case R.id.action_favourites:
                        setFragment( new FavouriteFragment() );
                        break;

                    case R.id.action_cart:
                        setFragment( new CartFragment() );
                        break;
                }

                return true;
            }
        });
    }


    public void setFragment(BaseFragment fragment) {
        mCurrentFragment = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations( R.animator.slide_in_left, R.animator.slide_out_right, 0, 0 )
                .replace( R.id.frame_container, fragment )
                .commit();
    }
}
