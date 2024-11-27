package com.example.appbtl;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.content.Intent;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartItems;  // Danh sách sản phẩm trong giỏ hàng
    private Button checkoutButton;
    private TextView totalPriceTextView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Khởi tạo RecyclerView cho giỏ hàng
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo Adapter cho giỏ hàng
        cartItems = getCartItems();  // Hàm này lấy danh sách sản phẩm trong giỏ hàng (có thể lấy từ database hoặc SharedPreferences)
        cartAdapter = new CartAdapter(cartItems);
        cartRecyclerView.setAdapter(cartAdapter);

        // Hiển thị tổng giá trị giỏ hàng
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        double totalPrice = calculateTotalPrice(cartItems);
        totalPriceTextView.setText("Tổng: " + totalPrice + " VNĐ");

        // Xử lý sự kiện khi nhấn nút thanh toán
        checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(v -> {
            // Thực hiện thanh toán hoặc chuyển hướng đến màn hình thanh toán
            proceedToCheckout();
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                // Điều hướng về trang chủ (Home)
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                finish();  // Đóng CartActivity khi chuyển về trang chủ
                return true;
            } else if (item.getItemId() == R.id.navigation_cart) {
                // Ở lại trang giỏ hàng
                return true;
            } else if (item.getItemId() == R.id.navigation_categories) {
                // Điều hướng đến trang danh mục
                return true;
            } else if (item.getItemId() == R.id.navigation_favorites) {
                // Điều hướng đến trang yêu thích
                return true;
            } else if (item.getItemId() == R.id.navigation_settings) {
                startActivity(new Intent(CartActivity.this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    // Hàm tính tổng giá trị giỏ hàng
    private double calculateTotalPrice(List<Product> cartItems) {
        double total = 0;
        for (Product product : cartItems) {
            total += product.getPrice();
        }
        return total;
    }

    // Hàm lấy các sản phẩm trong giỏ hàng
    private List<Product> getCartItems() {
        // Ở đây bạn có thể lấy giỏ hàng từ SQLite hoặc SharedPreferences
        // Ví dụ, trả về một danh sách giả
        return CartHelper.getCartItems();  // Bạn có thể tạo một class hoặc method để lấy giỏ hàng
    }

    // Phương thức xử lý thanh toán
    private void proceedToCheckout() {
        // Tính tổng giá trị giỏ hàng
        double totalPrice = calculateTotalPrice(cartItems);

        // Tạo Intent và truyền tổng giá trị giỏ hàng
        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
        intent.putExtra("totalPrice", totalPrice);  // Truyền dữ liệu tổng giá trị giỏ hàng
        startActivity(intent);  // Mở màn hình thanh toán
    }
}
