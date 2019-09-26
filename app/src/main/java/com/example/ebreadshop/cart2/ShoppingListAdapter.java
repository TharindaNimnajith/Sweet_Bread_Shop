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

import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<ViewFoodActivity> {

    Context context;

    public ShoppingListAdapter(Context context, List<ViewFoodActivity> items) {
        super(context, 0, items);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        ViewFoodActivity currentItem = getItem(position);

        ImageView img = listItemView.findViewById(R.id.itemIcon);
        Picasso.get()
                .load(context.getApplicationContext().getString(R.string.ip)
                        + String.valueOf(currentItem.getProductID())
                        + ".jpg")
                .fit().centerCrop()
                .into(img);

        TextView name = listItemView.findViewById(R.id.name);
        name.setText(currentItem.getTitle());

        TextView description = listItemView.findViewById(R.id.description);
        description.setText(currentItem.getDescription());

        TextView discount = listItemView.findViewById(R.id.discount);
        description.setText(currentItem.getDiscount());


        TextView cost = listItemView.findViewById(R.id.unit_price);
        cost.setText(currentItem.getPrice());

        return listItemView;
    }
}
//.resizeDimen(R.dimen.forImage, R.dimen.forImage)