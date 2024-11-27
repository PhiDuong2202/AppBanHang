package com.example.appbtl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> cartItems;

    public CartAdapter(List<Product> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item giỏ hàng
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        // Gán dữ liệu sản phẩm vào ViewHolder
        Product product = cartItems.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // ViewHolder cho mỗi item giỏ hàng
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTextView, productPriceTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
        }
    }
}
