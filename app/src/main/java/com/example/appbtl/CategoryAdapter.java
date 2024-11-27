package com.example.appbtl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<NhomSanPham> {

    private Context context;
    private List<NhomSanPham> categoryList;
    private DatabaseHelper db;

    public CategoryAdapter(Context context, List<NhomSanPham> categoryList) {
        super(context, R.layout.list_item_category, categoryList);
        this.context = context;
        this.categoryList = categoryList;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_category, parent, false);
        }

        NhomSanPham category = categoryList.get(position);
        TextView txtCategoryName = convertView.findViewById(R.id.txtCategoryName);
        ImageButton btnEdit = convertView.findViewById(R.id.btnEditCategory);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDeleteCategory);

        txtCategoryName.setText(category.getTen());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức công khai openCategoryDialog
                ((ManageCategoriesActivity) context).openCategoryDialog(category);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ManageCategoriesActivity) context).deleteCategory(category.getTen());
            }
        });

        return convertView;
    }

}
