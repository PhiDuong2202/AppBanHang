    package com.example.appbtl;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;
    import android.widget.Button;

    import java.util.List;

    public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

        private List<Product> productList;
        private OnProductAddClickListener onProductAddClickListener;

        public interface OnProductAddClickListener {
            void onAddClick(Product product);
        }

        // Đổi tên constructor thành ProductListAdapter thay vì ProductAdapter
        public ProductListAdapter(List<Product> productList, OnProductAddClickListener onProductAddClickListener) {
            this.productList = productList;
            this.onProductAddClickListener = onProductAddClickListener;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            Product product = productList.get(position);
            holder.productNameTextView.setText(product.getName());
            holder.productPriceTextView.setText(String.valueOf(product.getPrice()));

            // Xử lý sự kiện khi nhấn nút "Thêm sản phẩm"
            holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onProductAddClickListener != null) {
                        onProductAddClickListener.onAddClick(product);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        public static class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView productNameTextView, productPriceTextView;
            Button addToCartButton;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                productNameTextView = itemView.findViewById(R.id.productNameTextView);
                productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
                addToCartButton = itemView.findViewById(R.id.addToCartButton);
            }
        }
    }

