package com.example.ebreadshop.cart2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ebreadshop.R;
import com.example.ebreadshop.menuManagement.ViewFoodActivity;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;


public class ShoppingCartAdapter extends ArrayAdapter {
    Context context;
    ArrayList<ViewFoodActivity> items;

    public ShoppingCartAdapter(Context context, ArrayList<ViewFoodActivity> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cart_item, parent, false
            );
        }

        ViewFoodActivity currentItem = (ViewFoodActivity) getItem(position);

        ImageView img = listItemView.findViewById(R.id.cartItemIcon);
        Picasso.get()
                .load(context.getApplicationContext().getString(R.string.ip)
                        + String.valueOf(currentItem.getProductID())
                        + ".jpg")
                .fit()
                .into(img);

        ((TextView) listItemView.findViewById(R.id.name))
                .setText(currentItem.getTitle());

        String x = "x " + currentItem.getQuantity();
        ((TextView) listItemView.findViewById(R.id.cartItemQuantity))
                .setText(x);

        int itemPrice = 0;
        try {
            itemPrice = Integer.valueOf(NumberFormat.getCurrencyInstance()
                    .parse(String.valueOf(currentItem.getPrice()))
                    .toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ((TextView) listItemView.findViewById(R.id.cartItemPrice))
                .setText(NumberFormat.getCurrencyInstance().format(itemPrice));

        ((TextView) listItemView.findViewById(R.id.cartItemTotal))
                .setText(NumberFormat.getCurrencyInstance().format(itemPrice * currentItem.getQuantity()));

        // No idea how to implement remove individual item from cart
        // Appreciated if anyone can fix it.
//        ImageView removeFromCart = (ImageView) listItemView.findViewById(R.id.removeFromCart);
//        removeFromCart.setImageResource(R.drawable.ic_clear_black_24dp);
//        removeFromCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                items.remove()
//            }
//        });

        return listItemView;
    }
}
