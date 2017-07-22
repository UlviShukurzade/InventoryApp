package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

/**
 * Created by User on 21/07/2017.
 */

public class ItemsCursorAdapter extends CursorAdapter{
    public static final String LOG_TAG = ItemsCursorAdapter.class.getSimpleName();

    public ItemsCursorAdapter(Context contex, Cursor c){super(contex,c,0);}

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        TextView tvName = (TextView)view.findViewById(R.id.name);
        final TextView tvQuantity = (TextView)view.findViewById(R.id.quantity);
        TextView tvPrice = (TextView)view.findViewById(R.id.price);
        Button sellButton = (Button)view.findViewById(R.id.sellButton);


        String name = cursor.getString(cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_NAME));
        final int quantityInt = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_ITEM_QUANTITY));
        String quantity = String.valueOf(quantityInt);
        int priceInt = cursor.getInt(cursor.getColumnIndex(InventoryEntry.COLUMN_ITEM_PRICE));
        String price = String.valueOf(priceInt);

        final int position = cursor.getPosition();

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPosition(position);
                long id = cursor.getLong(cursor.getColumnIndex(InventoryEntry._ID));
                //uri construction
                Uri uri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);


                ContentValues values = new ContentValues();

                int quantity = Integer.parseInt(tvQuantity.getText().toString());
                quantity-=1;
                if (quantity<0){
                    quantity=0;
                    Toast.makeText(view.getContext(),view.getContext().getString(R.string.editor_no_available_products), Toast.LENGTH_SHORT).show();
                }
                values.put(InventoryEntry.COLUMN_ITEM_QUANTITY,quantity);
                int rowsAffected = view.getContext().getContentResolver().update(uri, values, null, null);
                if (rowsAffected == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Log.v(LOG_TAG, view.getContext().getString(R.string.adapter_update_product_failed));
                } else {
                    // Otherwise, the update was successful and we can log it.
                    Log.v(LOG_TAG, view.getContext().getString(R.string.adapter_update_product_successful));
                }
            }
        });
        if (TextUtils.isEmpty(price)) {
            price = context.getString(R.string.unknown_price);
        }

        tvName.setText(name);
        tvQuantity.setText(quantity);
        tvPrice.setText(price);

    }
}
