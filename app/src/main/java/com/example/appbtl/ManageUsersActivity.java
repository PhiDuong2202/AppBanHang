package com.example.appbtl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private Button btnAddUser;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<User> userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        // Khởi tạo database helper
        dbHelper = new DatabaseHelper(this);

        // Khởi tạo ListView và adapter
        listViewUsers = findViewById(R.id.listViewUsers);
        btnAddUser = findViewById(R.id.btnAddUser);

        // Load danh sách người dùng từ database
        List<User> userList = dbHelper.getAllUsers();

        // Tạo adapter tùy chỉnh để hiển thị tên và vai trò
        userAdapter = new ArrayAdapter<User>(this, R.layout.list_item_user, userList) {
            @Override
            public View getView(int position, View convertView, @NonNull android.view.ViewGroup parent) {
                // Tạo hoặc tái sử dụng view
                View view = convertView;
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.list_item_user, parent, false);
                }

                // Lấy đối tượng User tại vị trí hiện tại
                User currentUser = getItem(position);

                // Ánh xạ các TextView
                TextView usernameTextView = view.findViewById(R.id.userNameText);
                TextView roleTextView = view.findViewById(R.id.roleText);

                // Cập nhật thông tin cho các TextView
                if (currentUser != null) {
                    usernameTextView.setText(currentUser.getTenDangNhap());  // Hiển thị tên đăng nhập
                    roleTextView.setText(currentUser.getVaiTro());  // Hiển thị vai trò người dùng
                }

                return view;
            }
        };

        // Gán adapter cho ListView
        listViewUsers.setAdapter(userAdapter);

        // Sự kiện nhấn vào nút "Thêm Người Dùng"
        btnAddUser.setOnClickListener(v -> onAddUserClicked());

        // Sự kiện nhấn vào một người dùng trong danh sách
        listViewUsers.setOnItemClickListener((parent, view, position, id) -> {
            User selectedUser = (User) parent.getItemAtPosition(position);
            onEditUser(selectedUser.getId());
        });

        // Sự kiện nhấn giữ để xóa người dùng
        listViewUsers.setOnItemLongClickListener((parent, view, position, id) -> {
            User selectedUser = (User) parent.getItemAtPosition(position);
            onDeleteUser(selectedUser.getId());
            return true;
        });
    }

    // Chuyển đến màn hình thêm người dùng
    public void onAddUserClicked() {
        Intent intent = new Intent(this, AddEditUserActivity.class);
        startActivity(intent);
    }

    // Chỉnh sửa người dùng
    public void onEditUser(int userId) {
        Intent intent = new Intent(this, AddEditUserActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    // Xóa người dùng
    public void onDeleteUser(int userId) {
        boolean result = dbHelper.deleteUser(userId);
        if (result) {
            Toast.makeText(this, "Xóa người dùng thành công!", Toast.LENGTH_SHORT).show();
            // Cập nhật lại danh sách người dùng sau khi xóa
            List<User> updatedUserList = dbHelper.getAllUsers();
            userAdapter.clear();
            userAdapter.addAll(updatedUserList);
        } else {
            Toast.makeText(this, "Xóa người dùng thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
