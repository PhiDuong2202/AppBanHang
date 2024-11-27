package com.example.appbtl;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private DatabaseHelper databaseHelper;  // Khởi tạo đối tượng DatabaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ các view
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordRegisterInput);
        databaseHelper = new DatabaseHelper(this);  // Khởi tạo đối tượng DatabaseHelper

        // Gán sự kiện cho nút đăng ký
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Kiểm tra thông tin nhập vào
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    // Lưu người dùng vào cơ sở dữ liệu
                    boolean isInserted = databaseHelper.addUser(username, password, "user"); // Giả sử vai trò là "user"

                    if (isInserted) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        finish();  // Đóng activity sau khi đăng ký thành công
                    } else {
                        Toast.makeText(RegisterActivity.this, "Lỗi khi đăng ký. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Phương thức để quay lại màn hình đăng nhập
    public void navigateToLogin(View view) {
        finish();  // Đóng màn hình đăng ký và quay lại màn hình trước đó
    }
}
