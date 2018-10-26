package com.projects.marcoscavalcante.deloapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavouriteContract {

    public static final String AUTHORITY        = "com.projects.marcoscavalcante.deloapp";
    public static final Uri BASE_CONTENT_URI    = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVOURITES  = "favourites";


    public static final class FavouriteEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();

        public static final String TABLE_NAME = "product_favourites";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_PRICE =  "price";
        public static final String COLUMN_OLD_PRICE = "oldPrice";
        public static final String COLUMN_STOCK = "stock";
    }

}
