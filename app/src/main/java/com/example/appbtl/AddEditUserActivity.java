package com.example.appbtl;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditUserActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtRole;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtRole = findViewById(R.id.edtRole);
        btnSave = findViewById(R.id.btnSave);

        // Lấy thông tin từ Intent nếu đang sửa người dùng
        int userId = getIntent().getIntExtra("userId", -1);
        if (userId != -1) {
            User user = new DatabaseHelper(this).getUser(userId);
            edtUsername.setText(user.getTenDangNhap());
            edtPassword.setText(user.getMatKhau());
            edtRole.setText(user.getVaiTro());
        }

        btnSave.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            String role = edtRole.getText().toString();

            if (userId == -1) {
                // Thêm người dùng
                boolean result = new DatabaseHelper(this).addUser(username, password, role);
                if (result) {
                    Toast.makeText(this, "Thêm người dùng thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thêm người dùng thất bại!", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Cập nhật người dùng
                boolean result = new DatabaseHelper(this).updateUser(userId, username, password, role);
                if (result) {
                    Toast.makeText(this, "Cập nhật người dùng thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cập nhật người dùng thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });
    }
}
