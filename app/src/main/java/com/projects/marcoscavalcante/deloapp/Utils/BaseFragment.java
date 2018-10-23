package com.projects.marcoscavalcante.deloapp.Utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected abstract int getLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate( getLayout(), container, false );
        return mRootView;
    }

    protected int getNumberOfColumns( boolean isBasket ) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        float scaleFactor = getResources().getDisplayMetrics().density * 100;
        int number =  displayMetrics.widthPixels;

        int nColumns = (int) ( (float) number / scaleFactor ) / 2;
        if (nColumns < 2) return 2; //to keep the grid aspect

        if( nColumns > 2 && isBasket )  return 3;

        return nColumns;
    }
}
