package com.example.ebreadshop.menuManagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ebreadshop.R;

import java.util.ArrayList;
import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

    private List<ProductWrapper> productList;
    //private List<String> keys;

    private NewProductAdapter.ItemClickListener listener;

    public NewProductAdapter(List<ProductWrapper> productList) {
        this.productList = productList;
    }

    /*
    public NewProductAdapter(List<Product> productList, List<String> keys) {
        this.productList = productList;
        this.keys = keys;
    }
    */

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_row_in_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductWrapper product = productList.get(position);

        holder.name.setText(productList.get(position).getProduct().getName());
        holder.unitPrice.setText(productList.get(position).getProduct().getPrice());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(product);
            }
        });

        //holder.bind(productList.get(position), keys.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void filterList(ArrayList<ProductWrapper> filteredList) {
        productList = filteredList;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(ProductWrapper product);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView unitPrice;

        public LinearLayout linearLayout;

        //private String key;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name_txt);
            unitPrice = view.findViewById(R.id.price_txt);

            linearLayout = view.findViewById(R.id.new_food_row);
        }

        /*
        public void bind(Product product, String key) {
            this.key = key;

            //.setText(product.get..());
        }
        */
    }
}
