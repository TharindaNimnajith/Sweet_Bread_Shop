package com.example.ebreadshop.menuManagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ebreadshop.R;

import java.util.List;

public class   NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

    private List<Product> productList;

    public NewProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_row_in_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(productList.get(position).getName());
        holder.unitPrice.setText(productList.get(position).getUnitPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView unitPrice;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name_txt);
            unitPrice = view.findViewById(R.id.price_txt);
        }
    }
}
