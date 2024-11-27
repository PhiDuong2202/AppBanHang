package com.example.appbtl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private Button logoutButton;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        logoutButton = findViewById(R.id.logoutButton);

        // Xử lý sự kiện khi nhấn nút đăng xuất
        logoutButton.setOnClickListener(v -> {
            logout();
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_cart) {
                startActivity(new Intent(SettingsActivity.this, CartActivity.class));
                finish();
                return true;
            } else if (item.getItemId() == R.id.navigation_categories) {
                // Điều hướng đến trang danh mục
                return true;
            } else if (item.getItemId() == R.id.navigation_favorites) {
                // Điều hướng đến trang yêu thích
                return true;
            } else if (item.getItemId() == R.id.navigation_settings) {
                startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void logout() {
        // Xóa thông tin đăng nhập (ví dụ từ SharedPreferences)
        // SharedPreferences prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        // prefs.edit().clear().apply();

        // Quay lại màn hình đăng nhập (LoginActivity)
        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();  // Đảm bảo đóng SettingsActivity để không thể quay lại
    }
}
