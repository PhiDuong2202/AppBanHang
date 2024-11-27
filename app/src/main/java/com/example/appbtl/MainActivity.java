package com.example.appbtl;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.Intent;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoriesRecyclerView;
    private CategoryListAdapter categoryAdapter;
    private DatabaseHelper dbHelper;
    private List<NhomSanPham> categories;

    private RecyclerView productsRecyclerView;
    private ProductListAdapter productAdapter;
    private List<Product> productList;
    private List<Product> filteredProductList;

    private BottomNavigationView bottomNavigationView;
    private EditText searchBar;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Khởi tạo RecyclerView và danh sách thể loại
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);

        // Lấy danh sách thể loại từ cơ sở dữ liệu
        categories = dbHelper.getAllNhomSanPham();

        // Thiết lập LayoutManager cho RecyclerView
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Khởi tạo Adapter và thiết lập cho RecyclerView
        categoryAdapter = new CategoryListAdapter(this, categories);
        categoriesRecyclerView.setAdapter(categoryAdapter);

        productsRecyclerView = findViewById(R.id.productsRecyclerView);

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        productList = dbHelper.getAllProducts();

        filteredProductList = new ArrayList<>(productList);

        // Thiết lập RecyclerView với ProductListAdapter
        productAdapter = new ProductListAdapter(productList, new ProductListAdapter.OnProductAddClickListener() { // Đổi tên ở đây
            @Override
            public void onAddClick(Product product) {
                // Xử lý khi thêm sản phẩm vào giỏ hàng
                addToCart(product);
            }
        });

        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productsRecyclerView.setAdapter(productAdapter);

        searchBar = findViewById(R.id.searchBar);
        searchButton = findViewById(R.id.searchButton);

        // Thiết lập sự kiện nhấn nút tìm kiếm
        searchButton.setOnClickListener(v -> {
            String query = searchBar.getText().toString();
            filterProducts(query);  // Lọc sản phẩm khi nhấn nút tìm kiếm
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                if (!(MainActivity.this instanceof MainActivity)) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();  // Đảm bảo rằng MainActivity là trang chính, không quay lại các Activity trước đó
                }
                return true;
            } else if (item.getItemId() == R.id.navigation_cart) {
                // Chuyển đến trang giỏ hàng khi bấm vào giỏ hàng
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_categories) {
                // Điều hướng đến trang danh mục
                return true;
            } else if (item.getItemId() == R.id.navigation_favorites) {
                // Điều hướng đến trang yêu thích
                return true;
            } else if (item.getItemId() == R.id.navigation_settings) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    private void filterProducts(String query) {
        if (query.isEmpty()) {
            // Nếu không có từ khóa, hiển thị tất cả sản phẩm
            filteredProductList.clear();
            filteredProductList.addAll(productList);
        } else {
            List<Product> filteredList = new ArrayList<>();
            for (Product product : productList) {
                // Kiểm tra nếu tên sản phẩm chứa từ khóa tìm kiếm
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(product);
                }
            }
            filteredProductList.clear();
            filteredProductList.addAll(filteredList);
        }

        // Cập nhật lại danh sách trong adapter
        productAdapter.notifyDataSetChanged();

    }

    private void addToCart(Product product) {
        // Thêm sản phẩm vào giỏ hàng
        CartHelper.addToCart(product);
        // Hiển thị thông báo
        Toast.makeText(this, "Đã thêm " + product.getName() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }
}
