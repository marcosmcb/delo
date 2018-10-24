package com.projects.marcoscavalcante.deloapp.ProductFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.projects.marcoscavalcante.deloapp.R;
import com.projects.marcoscavalcante.deloapp.Utils.BaseFragment;
import butterknife.ButterKnife;

public class ProductFragment extends BaseFragment {


    private static final String TAG = ProductFragment.class.getName();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind( ProductFragment.this, mRootView );
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_products;
    }
}
