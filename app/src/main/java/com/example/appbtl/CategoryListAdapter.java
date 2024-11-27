package com.example.appbtl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private Context context;
    private List<NhomSanPham> categoryList;

    // Constructor
    public CategoryListAdapter(Context context, List<NhomSanPham> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    // Tạo ViewHolder từ layout item_category.xml
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    // Gán dữ liệu vào mỗi item
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        NhomSanPham category = categoryList.get(position);
        holder.categoryNameTextView.setText(category.getTen());
        // Bạn có thể cập nhật hình ảnh theo cách của mình
        // holder.categoryLogoImageView.setImageResource(R.drawable.default_logo);

        // Khi người dùng bấm vào item, chỉ phản hồi mà không thực hiện hành động gì
        holder.cardView.setOnClickListener(v -> {
            // Không làm gì khi bấm vào
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    // ViewHolder để ánh xạ các view trong mỗi item
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTextView;
        ImageView categoryLogoImageView;
        View cardView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
            categoryLogoImageView = itemView.findViewById(R.id.categoryLogoImageView);
            cardView = itemView;  // CardView bao quanh toàn bộ item
        }
    }
}
