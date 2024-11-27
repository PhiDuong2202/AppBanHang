package com.example.appbtl;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

public class ManageCategoriesActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // Không còn dùng nữa
    private DatabaseHelper db;
    private CategoryAdapter adapter;
    private List<NhomSanPham> categoryList;
    private Uri imageUri; // Để lưu trữ đường dẫn ảnh đã chọn

    // Khai báo ActivityResultLauncher
    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        imageUri = selectedImageUri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ImageView imgCategoryLogoPreview = findViewById(R.id.imgCategoryLogoPreview);
                            imgCategoryLogoPreview.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(ManageCategoriesActivity.this, "Không thể tải ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);

        db = new DatabaseHelper(this);

        // Khai báo các phần tử giao diện
        ListView listView = findViewById(R.id.listViewCategories);
        Button btnAddCategory = findViewById(R.id.btnAddCategory);

        loadCategories(listView); // Truyền listView vào phương thức loadCategories

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog(null);
            }
        });
    }

    private void loadCategories(ListView listView) {
        categoryList = db.getAllNhomSanPham();
        adapter = new CategoryAdapter(this, categoryList);
        listView.setAdapter(adapter);
    }

    private void showCategoryDialog(final NhomSanPham category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(category == null ? "Thêm Nhóm Sản Phẩm" : "Chỉnh Sửa Nhóm Sản Phẩm");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_edit_category, null);
        builder.setView(view);

        EditText edtCategoryName = view.findViewById(R.id.edtCategoryName);
        EditText edtCategoryLogo = view.findViewById(R.id.edtCategoryLogo);
        ImageView imgCategoryLogoPreview = view.findViewById(R.id.imgCategoryLogoPreview);
        Button btnUploadImage = view.findViewById(R.id.btnUploadImage);

        if (category != null) {
            edtCategoryName.setText(category.getTen());
            edtCategoryLogo.setText(category.getLogoUrl());
            // Tải ảnh nếu có URL (giả sử bạn lưu trữ URL của ảnh)
            imgCategoryLogoPreview.setImageURI(Uri.parse(category.getLogoUrl()));
        }

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở bộ chọn ảnh khi người dùng bấm nút
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                pickImageLauncher.launch(intent); // Sử dụng ActivityResultLauncher thay cho startActivityForResult
            }
        });

        builder.setPositiveButton(category == null ? "Thêm" : "Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String categoryName = edtCategoryName.getText().toString();
                String categoryLogo = imageUri != null ? imageUri.toString() : edtCategoryLogo.getText().toString();

                if (category == null) {
                    db.addNhomSanPham(categoryName, categoryLogo);
                    Toast.makeText(ManageCategoriesActivity.this, "Đã thêm nhóm sản phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateCategory(categoryName, categoryLogo);
                    Toast.makeText(ManageCategoriesActivity.this, "Đã cập nhật nhóm sản phẩm!", Toast.LENGTH_SHORT).show();
                }
                loadCategories((ListView) findViewById(R.id.listViewCategories)); // Tải lại danh sách sau khi cập nhật
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public void openCategoryDialog(NhomSanPham category) {
        showCategoryDialog(category);
    }

    public void deleteCategory(final String categoryName) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa Nhóm Sản Phẩm")
                .setMessage("Bạn có chắc chắn muốn xóa nhóm sản phẩm này?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteCategory(categoryName);
                        Toast.makeText(ManageCategoriesActivity.this, "Đã xóa nhóm sản phẩm!", Toast.LENGTH_SHORT).show();
                        loadCategories((ListView) findViewById(R.id.listViewCategories)); // Tải lại danh sách sau khi xóa
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
