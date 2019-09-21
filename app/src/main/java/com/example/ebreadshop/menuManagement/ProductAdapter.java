package com.example.ebreadshop.menuManagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ebreadshop.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    //private List<String> keys;

    private ProductAdapter.ItemClickListener listener;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    /*
    public ProductAdapter(List<Product> productList, List<String> keys) {
        this.productList = productList;
        this.keys = keys;
    }
    */

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_in_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = productList.get(position);

        holder.name.setText(productList.get(position).getName());
        //holder.unitPrice.setText((int) productList.get(position).getUnitPrice());

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

    public interface ItemClickListener {
        void onItemClick(Product product);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        //public TextView unitPrice;

        public LinearLayout linearLayout;

        //private String key;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.nametxt);
            //unitPrice = view.findViewById(R.id.uptxt);

            linearLayout = view.findViewById(R.id.food_row);
        }

        /*
        public void bind(Product product, String key) {
            this.key = key;

            //.setText(product.get..());
        }
        */
    }
}
