package com.projects.marcoscavalcante.deloapp.ProductFragment;

import com.projects.marcoscavalcante.deloapp.Remote.DeloApi;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

public class ProductRepositoryTest {

    @Mock
    private DeloApi mDeloApi;


    @Captor
    private ArgumentCaptor<ProductRepository.OnFinishedListener> mProductsListener;

}
