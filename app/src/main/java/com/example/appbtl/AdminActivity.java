package com.example.appbtl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Ánh xạ các nút từ layout và thiết lập sự kiện click
        Button btnManageUsers = findViewById(R.id.btnManageUsers);
        Button btnManageCategories = findViewById(R.id.btnManageCategories);
        Button btnManageProducts = findViewById(R.id.btnManageProducts);
        Button btnManageOrders = findViewById(R.id.btnManageOrders);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Điều hướng đến ManageUsersActivity
        btnManageUsers.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageUsersActivity.class);
            startActivity(intent);
        });

        // Điều hướng đến ManageCategoriesActivity
        btnManageCategories.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageCategoriesActivity.class);
            startActivity(intent);
        });

        // Điều hướng đến ManageProductsActivity
        btnManageProducts.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageProductsActivity.class);
            startActivity(intent);
        });

        // Điều hướng đến ManageOrdersActivity
        btnManageOrders.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageOrdersActivity.class);
            startActivity(intent);
        });

        // Đăng xuất và quay lại LoginActivity
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
