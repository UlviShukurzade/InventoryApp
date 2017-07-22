package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by User on 21/07/2017.
 */

public class InventoryDbHelper extends SQLiteOpenHelper{

    private static final  String DATABASE_NAME= "inventory.db";

    //db version

    private static final int DATABASE_VERSION = 1;

    public InventoryDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME+" ( "
                +InventoryEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "
                +InventoryEntry.COLUMN_ITEM_NAME+" TEXT NOT NULL, "
                +InventoryEntry.COLUMN_ITEM_QUANTITY+ " INTEGER NOT NULL DEFAULT 0, "
                +InventoryEntry.COLUMN_ITEM_PRICE+ " REAL NOT NULL, "
                +InventoryEntry.COLUMN_SUPPLIER+" TEXT, "
                +InventoryEntry.COLUMN_PICTURE+" TEXT);";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
