package com.projects.marcoscavalcante.deloapp.CartFragment;


public interface CartContract {

    interface View{
        void updatePrice(float newPrice);
        void showProgress();
        void hideProgress();
    }


    interface Repository{

        interface OnFinishedListener {
            void onFinished( String msg );
            void onFailure( Throwable t );
        }

        void sendProducts( CartContract.Repository.OnFinishedListener onCallbackListener );
    }

}
