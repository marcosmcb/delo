package com.projects.marcoscavalcante.deloapp.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.projects.marcoscavalcante.deloapp.Data.FavouriteContract.FavouriteEntry;

public class Product implements Parcelable {

    @SerializedName("productId")
    @Expose
    private int productId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("price")
    @Expose
    private float price;

    @SerializedName("oldPrice")
    @Expose
    private float oldPrice;

    @SerializedName("stock")
    @Expose
    private int stock;

    public Product(int productId, String name, String category, float price, float oldPrice, int stock) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.oldPrice = oldPrice;
        this.stock = stock;
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    protected Product(Parcel in) {
        productId = in.readInt();
        name = in.readString();
        category = in.readString();
        price = in.readFloat();
        oldPrice = in.readFloat();
        stock = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeFloat(price);
        dest.writeFloat(oldPrice);
        dest.writeInt(stock);
    }


    @Override
    public String toString() {
        return "{ productId: " + productId +
                ", name: " + name +
                ", category: " + category +
                ", price:" + price +
                ", oldPrice:" + oldPrice +
                ", stock: " + stock + "}";
    }


    public ContentValues getContentValues( )
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put( FavouriteEntry.COLUMN_ID, this.getProductId());
        contentValues.put( FavouriteEntry.COLUMN_NAME, this.getName());
        contentValues.put( FavouriteEntry.COLUMN_CATEGORY, this.getCategory());
        contentValues.put( FavouriteEntry.COLUMN_PRICE, this.getPrice());
        contentValues.put( FavouriteEntry.COLUMN_OLD_PRICE, this.getOldPrice());
        contentValues.put( FavouriteEntry.COLUMN_STOCK, this.getStock());

        return contentValues;
    }


    public Product(Cursor cursor){
        this.productId  = cursor.getInt( cursor.getColumnIndex( FavouriteEntry.COLUMN_ID ) );
        this.stock      = cursor.getInt( cursor.getColumnIndex( FavouriteEntry.COLUMN_STOCK ) );
        this.price      = cursor.getFloat( cursor.getColumnIndex( FavouriteEntry.COLUMN_PRICE ) );
        this.oldPrice   = cursor.getFloat( cursor.getColumnIndex( FavouriteEntry.COLUMN_OLD_PRICE ) );
        this.category   = cursor.getString( cursor.getColumnIndex( FavouriteEntry.COLUMN_NAME ) );
        this.name       = cursor.getString( cursor.getColumnIndex( FavouriteEntry.COLUMN_NAME ) );
    }

}
