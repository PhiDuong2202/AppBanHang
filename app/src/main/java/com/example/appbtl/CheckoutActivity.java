package com.example.appbtl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckoutActivity extends AppCompatActivity {

    private EditText customerNameEditText, customerGenderEditText, customerAddressEditText, customerPhoneEditText;
    private RadioGroup paymentMethodRadioGroup;
    private TextView totalPriceTextView;
    private Button confirmCheckoutButton;


    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Áp dụng Edge-to-Edge với ViewCompat và WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các view từ layout
        customerNameEditText = findViewById(R.id.customerNameEditText);
        customerGenderEditText = findViewById(R.id.customerGenderEditText);
        customerAddressEditText = findViewById(R.id.customerAddressEditText);
        customerPhoneEditText = findViewById(R.id.customerPhoneEditText);
        paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        confirmCheckoutButton = findViewById(R.id.confirmCheckoutButton);

        // Thiết lập tham chiếu đến Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        // Hiển thị tổng giá trị giỏ hàng trên CheckoutActivity
        totalPriceTextView.setText("Tổng: " + totalPrice + " VNĐ");

        // Sự kiện khi nhấn nút xác nhận thanh toán
        confirmCheckoutButton.setOnClickListener(v -> {
            String name = customerNameEditText.getText().toString().trim();
            String gender = customerGenderEditText.getText().toString().trim();
            String address = customerAddressEditText.getText().toString().trim();
            String phone = customerPhoneEditText.getText().toString().trim();
            String paymentMethod = getSelectedPaymentMethod();

            if (validateInput(name, gender, address, phone, paymentMethod)) {
                saveOrderToFirebase(name, gender, address, phone, paymentMethod);
            } else {
                Toast.makeText(CheckoutActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức lấy phương thức thanh toán đã chọn
    private String getSelectedPaymentMethod() {
        int selectedId = paymentMethodRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        return selectedRadioButton != null ? selectedRadioButton.getText().toString() : null;
    }

    // Phương thức kiểm tra dữ liệu đầu vào
    private boolean validateInput(String name, String gender, String address, String phone, String paymentMethod) {
        return !name.isEmpty() && !gender.isEmpty() && !address.isEmpty() && !phone.isEmpty() && paymentMethod != null;
    }

    // Phương thức lưu đơn hàng vào Firebase
    private void saveOrderToFirebase(String name, String gender, String address, String phone, String paymentMethod) {
        // Tạo ID cho đơn hàng
        String orderId = databaseReference.push().getKey();

        // Tạo đối tượng Order và lưu vào Firebase
        if (orderId != null) {
            Order order = new Order(orderId, name, gender, address, phone, paymentMethod);
            databaseReference.child(orderId).setValue(order).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(CheckoutActivity.this, "Đơn hàng đã được lưu vào Firebase", Toast.LENGTH_SHORT).show();
                    // Xử lý sau khi lưu thành công (chuyển đến màn hình xác nhận hoặc màn hình chính)
                } else {
                    Toast.makeText(CheckoutActivity.this, "Lỗi khi lưu đơn hàng", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Lớp Order đại diện cho đối tượng đơn hàng
    public static class Order {
        public String orderId;
        public String name;
        public String gender;
        public String address;
        public String phone;
        public String paymentMethod;

        public Order() {
            // Constructor mặc định cho Firebase
        }

        public Order(String orderId, String name, String gender, String address, String phone, String paymentMethod) {
            this.orderId = orderId;
            this.name = name;
            this.gender = gender;
            this.address = address;
            this.phone = phone;
            this.paymentMethod = paymentMethod;
        }
    }
}
