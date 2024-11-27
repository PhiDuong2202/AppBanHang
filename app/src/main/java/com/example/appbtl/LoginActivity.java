package com.example.appbtl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo các thành phần giao diện
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
    }

    // Phương thức điều hướng đến màn hình đăng ký
    public void navigateToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    // Phương thức đăng nhập
    public void login(View view) {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Kiểm tra nếu tên đăng nhập hoặc mật khẩu rỗng
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Tên đăng nhập và mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra người dùng từ cơ sở dữ liệu
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        User user = dbHelper.getUserByUsername(username); // Lấy người dùng theo tên đăng nhập

        // Nếu người dùng không tồn tại hoặc mật khẩu sai
        if (user == null || !user.getMatKhau().equals(password)) {
            Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đăng nhập thành công, kiểm tra vai trò của người dùng
        if (user.getVaiTro().equals("admin")) {
            // Nếu là admin, chuyển đến màn hình quản trị
            Toast.makeText(this, "Đăng nhập thành công với quyền admin!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AdminActivity.class);  // Giao diện Admin
            startActivity(intent);
        } else {
            // Nếu là người dùng, chuyển đến màn hình người dùng chính
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);  // Giao diện người dùng
            startActivity(intent);
        }
    }
}
