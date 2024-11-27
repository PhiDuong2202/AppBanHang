package com.example.appbtl;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_products);

        recyclerView = findViewById(R.id.recyclerView);
        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        databaseHelper = new DatabaseHelper(this);

        // Lấy tất cả sản phẩm từ cơ sở dữ liệu
        productList = databaseHelper.getAllProducts();

        // Cài đặt adapter cho RecyclerView
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onEditClick(Product product) {
                // Chỉnh sửa sản phẩm
                showAddEditProductDialog(product); // Truyền sản phẩm cần chỉnh sửa
            }

            @Override
            public void onDeleteClick(Product product) {
                // Xóa sản phẩm
                deleteProduct(product);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productAdapter);

        // Xử lý nút thêm sản phẩm
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog để thêm sản phẩm mới
                showAddEditProductDialog(null);  // Truyền null khi thêm sản phẩm mới
            }
        });
    }

    private void showAddEditProductDialog(final Product product) {
        // Tạo một Dialog để thêm hoặc chỉnh sửa sản phẩm
        final Dialog addEditDialog = new Dialog(this);
        addEditDialog.setContentView(R.layout.add_edit_product);  // Layout cho Dialog thêm/sửa sản phẩm
        addEditDialog.setTitle(product == null ? "Thêm sản phẩm" : "Chỉnh sửa sản phẩm");

        // Tìm các phần tử trong Dialog
        EditText nameEditText = addEditDialog.findViewById(R.id.editTextProductName);
        EditText priceEditText = addEditDialog.findViewById(R.id.editTextProductPrice);
        EditText descriptionEditText = addEditDialog.findViewById(R.id.editTextProductDescription);
        Button saveButton = addEditDialog.findViewById(R.id.buttonSaveProduct);

        // Nếu là chế độ chỉnh sửa, điền thông tin vào các EditText
        if (product != null) {
            nameEditText.setText(product.getName());
            priceEditText.setText(String.valueOf(product.getPrice()));
            descriptionEditText.setText(product.getDescription());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các EditText
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                double price = Double.parseDouble(priceEditText.getText().toString());
                int categoryId = product != null ? product.getCategoryId() : 1; // Default categoryId = 1 nếu là thêm mới

                if (product == null) {
                    // Nếu là thêm sản phẩm mới
                    if (databaseHelper.addProduct(name, price, description, categoryId)) {
                        Toast.makeText(ManageProductsActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        productList.add(new Product(name, price, description, categoryId));  // Thêm sản phẩm mới vào danh sách
                        productAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ManageProductsActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu là chỉnh sửa sản phẩm
                    if (databaseHelper.updateProduct(product.getId(), name, price, description, categoryId)) {
                        Toast.makeText(ManageProductsActivity.this, "Chỉnh sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        product.setName(name);
                        product.setPrice(price);
                        product.setDescription(description);
                        productAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ManageProductsActivity.this, "Chỉnh sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                addEditDialog.dismiss();  // Đóng dialog sau khi lưu
            }
        });

        // Hiển thị Dialog
        addEditDialog.show();
    }

    private void deleteProduct(Product product) {
        // Xóa sản phẩm
        if (databaseHelper.deleteProduct(product.getId())) {
            productList.remove(product);
            productAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
