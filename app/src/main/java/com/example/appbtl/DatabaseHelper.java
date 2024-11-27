package com.example.appbtl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Phiên bản cơ sở dữ liệu và tên cơ sở dữ liệu
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "CuaHangDienThoaiDB";

    // Tên các bảng
    private static final String TABLE_NGUOI_DUNG = "NguoiDung";
    private static final String TABLE_NHOM_SAN_PHAM = "NhomSanPham";
    private static final String TABLE_SAN_PHAM = "SanPham";
    private static final String TABLE_DON_HANG = "DonHang";
    private static final String TABLE_CHI_TIET_DON_HANG = "ChiTietDonHang";

    // Bảng NguoiDung - Cột
    private static final String COLUMN_NGUOI_DUNG_ID = "id";
    private static final String COLUMN_NGUOI_DUNG_TEN_DANG_NHAP = "tenDangNhap";
    private static final String COLUMN_NGUOI_DUNG_MAT_KHAU = "matKhau";
    private static final String COLUMN_NGUOI_DUNG_VAI_TRO = "vaiTro";

    // Bảng NhomSanPham - Cột
    private static final String COLUMN_NHOM_SAN_PHAM_ID = "id";
    private static final String COLUMN_NHOM_SAN_PHAM_TEN = "ten";
    private static final String COLUMN_NHOM_SAN_PHAM_LOGO = "logoUrl";

    // Bảng SanPham - Cột
    private static final String COLUMN_SAN_PHAM_ID = "id";
    private static final String COLUMN_SAN_PHAM_TEN = "ten";
    private static final String COLUMN_SAN_PHAM_GIA = "gia";
    private static final String COLUMN_SAN_PHAM_MO_TA = "moTa";
    private static final String COLUMN_SAN_PHAM_NHOM_ID = "nhomId";

    // Bảng DonHang - Cột
    private static final String COLUMN_DON_HANG_ID = "id";
    private static final String COLUMN_DON_HANG_NGUOI_DUNG_ID = "nguoiDungId";
    private static final String COLUMN_DON_HANG_NGAY_DAT = "ngayDat";
    private static final String COLUMN_DON_HANG_TRANG_THAI = "trangThai";

    // Bảng ChiTietDonHang - Cột
    private static final String COLUMN_CHI_TIET_DON_HANG_ID = "id";
    private static final String COLUMN_CHI_TIET_DON_HANG_DON_HANG_ID = "donHangId";
    private static final String COLUMN_CHI_TIET_DON_HANG_SAN_PHAM_ID = "sanPhamId";
    private static final String COLUMN_CHI_TIET_DON_HANG_SO_LUONG = "soLuong";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng NguoiDung
        String CREATE_TABLE_NGUOI_DUNG = "CREATE TABLE " + TABLE_NGUOI_DUNG + "("
                + COLUMN_NGUOI_DUNG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NGUOI_DUNG_TEN_DANG_NHAP + " TEXT UNIQUE,"
                + COLUMN_NGUOI_DUNG_MAT_KHAU + " TEXT,"
                + COLUMN_NGUOI_DUNG_VAI_TRO + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_NGUOI_DUNG);

        // Tạo bảng NhomSanPham
        String CREATE_TABLE_NHOM_SAN_PHAM = "CREATE TABLE " + TABLE_NHOM_SAN_PHAM + "("
                + COLUMN_NHOM_SAN_PHAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NHOM_SAN_PHAM_TEN + " TEXT,"
                + COLUMN_NHOM_SAN_PHAM_LOGO + " TEXT)";
        db.execSQL(CREATE_TABLE_NHOM_SAN_PHAM);

        // Tạo bảng SanPham
        String CREATE_TABLE_SAN_PHAM = "CREATE TABLE " + TABLE_SAN_PHAM + "("
                + COLUMN_SAN_PHAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SAN_PHAM_TEN + " TEXT,"
                + COLUMN_SAN_PHAM_GIA + " REAL,"
                + COLUMN_SAN_PHAM_MO_TA + " TEXT,"
                + COLUMN_SAN_PHAM_NHOM_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_SAN_PHAM_NHOM_ID + ") REFERENCES "
                + TABLE_NHOM_SAN_PHAM + "(" + COLUMN_NHOM_SAN_PHAM_ID + "))";
        db.execSQL(CREATE_TABLE_SAN_PHAM);

        // Tạo bảng DonHang
        String CREATE_TABLE_DON_HANG = "CREATE TABLE " + TABLE_DON_HANG + "("
                + COLUMN_DON_HANG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DON_HANG_NGUOI_DUNG_ID + " INTEGER,"
                + COLUMN_DON_HANG_NGAY_DAT + " TEXT,"
                + COLUMN_DON_HANG_TRANG_THAI + " TEXT,"
                + "FOREIGN KEY(" + COLUMN_DON_HANG_NGUOI_DUNG_ID + ") REFERENCES "
                + TABLE_NGUOI_DUNG + "(" + COLUMN_NGUOI_DUNG_ID + "))";
        db.execSQL(CREATE_TABLE_DON_HANG);

        // Tạo bảng ChiTietDonHang
        String CREATE_TABLE_CHI_TIET_DON_HANG = "CREATE TABLE " + TABLE_CHI_TIET_DON_HANG + "("
                + COLUMN_CHI_TIET_DON_HANG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CHI_TIET_DON_HANG_DON_HANG_ID + " INTEGER,"
                + COLUMN_CHI_TIET_DON_HANG_SAN_PHAM_ID + " INTEGER,"
                + COLUMN_CHI_TIET_DON_HANG_SO_LUONG + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_CHI_TIET_DON_HANG_DON_HANG_ID + ") REFERENCES "
                + TABLE_DON_HANG + "(" + COLUMN_DON_HANG_ID + "),"
                + "FOREIGN KEY(" + COLUMN_CHI_TIET_DON_HANG_SAN_PHAM_ID + ") REFERENCES "
                + TABLE_SAN_PHAM + "(" + COLUMN_SAN_PHAM_ID + "))";
        db.execSQL(CREATE_TABLE_CHI_TIET_DON_HANG);

        String insertAdminQuery = "INSERT INTO " + TABLE_NGUOI_DUNG + " ("
                + COLUMN_NGUOI_DUNG_TEN_DANG_NHAP + ", "
                + COLUMN_NGUOI_DUNG_MAT_KHAU + ", "
                + COLUMN_NGUOI_DUNG_VAI_TRO + ") VALUES (?, ?, ?)";
        db.execSQL(insertAdminQuery, new Object[]{"admin", "1234", "admin"});

        String insertUserQuery = "INSERT INTO " + TABLE_NGUOI_DUNG + " ("
                + COLUMN_NGUOI_DUNG_TEN_DANG_NHAP + ", "
                + COLUMN_NGUOI_DUNG_MAT_KHAU + ", "
                + COLUMN_NGUOI_DUNG_VAI_TRO + ") VALUES (?, ?, ?)";
        db.execSQL(insertUserQuery, new Object[]{"duong", "1234", "user"});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE NhomSanPham ADD COLUMN logoUrl TEXT;");
        }
        // Xóa bảng nếu tồn tại và tạo lại bảng khi cập nhật cơ sở dữ liệu
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NGUOI_DUNG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHOM_SAN_PHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAN_PHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DON_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHI_TIET_DON_HANG);
        onCreate(db);
    }

    // Các phương thức truy vấn dữ liệu từ bảng
    public User getUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn người dùng theo ID
        Cursor cursor = db.query(
                TABLE_NGUOI_DUNG, // Tên bảng
                new String[]{COLUMN_NGUOI_DUNG_ID, COLUMN_NGUOI_DUNG_TEN_DANG_NHAP, COLUMN_NGUOI_DUNG_MAT_KHAU, COLUMN_NGUOI_DUNG_VAI_TRO}, // Các cột cần lấy
                COLUMN_NGUOI_DUNG_ID + "=?", // Điều kiện lọc theo ID người dùng
                new String[]{String.valueOf(userId)}, // Giá trị điều kiện
                null, null, null); // Không nhóm, sắp xếp hay giới hạn

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_ID);
            int tenDangNhapIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_TEN_DANG_NHAP);
            int matKhauIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_MAT_KHAU);
            int vaiTroIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_VAI_TRO);

            // Kiểm tra nếu các chỉ số cột hợp lệ (>= 0)
            if (idIndex >= 0 && tenDangNhapIndex >= 0 && matKhauIndex >= 0 && vaiTroIndex >= 0) {
                User user = new User();
                user.setId(cursor.getInt(idIndex));
                user.setTenDangNhap(cursor.getString(tenDangNhapIndex));
                user.setMatKhau(cursor.getString(matKhauIndex));
                user.setVaiTro(cursor.getString(vaiTroIndex));
                cursor.close();
                return user;
            } else {
                cursor.close();
                return null; // Nếu có cột không hợp lệ
            }
        }

        db.close();
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn tất cả người dùng
        Cursor cursor = db.query(
                TABLE_NGUOI_DUNG, // Tên bảng
                new String[]{COLUMN_NGUOI_DUNG_ID, COLUMN_NGUOI_DUNG_TEN_DANG_NHAP, COLUMN_NGUOI_DUNG_MAT_KHAU, COLUMN_NGUOI_DUNG_VAI_TRO}, // Các cột cần lấy
                null, // Không có điều kiện lọc
                null, // Không có điều kiện
                null, // Không nhóm
                null, // Không sắp xếp
                null); // Không giới hạn

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_ID);
                int tenDangNhapIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_TEN_DANG_NHAP);
                int matKhauIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_MAT_KHAU);
                int vaiTroIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_VAI_TRO);

                // Kiểm tra chỉ số cột có hợp lệ không
                if (idIndex >= 0 && tenDangNhapIndex >= 0 && matKhauIndex >= 0 && vaiTroIndex >= 0) {
                    // Tạo đối tượng User và thêm vào danh sách
                    User user = new User(
                            cursor.getInt(idIndex),
                            cursor.getString(tenDangNhapIndex),
                            cursor.getString(matKhauIndex),
                            cursor.getString(vaiTroIndex)
                    );
                    users.add(user);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        return users; // Trả về danh sách người dùng
    }

    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa người dùng dựa trên ID
        int rowsDeleted = db.delete(TABLE_NGUOI_DUNG, COLUMN_NGUOI_DUNG_ID + " = ?", new String[]{String.valueOf(userId)});

        db.close();

        // Nếu ít nhất 1 dòng được xóa, trả về true, nếu không trả về false
        return rowsDeleted > 0;
    }

    public boolean addUser(String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Tạo đối tượng ContentValues để chứa các giá trị cần chèn vào cơ sở dữ liệu
        ContentValues values = new ContentValues();
        values.put(COLUMN_NGUOI_DUNG_TEN_DANG_NHAP, username); // Tên đăng nhập
        values.put(COLUMN_NGUOI_DUNG_MAT_KHAU, password); // Mật khẩu
        values.put(COLUMN_NGUOI_DUNG_VAI_TRO, role); // Vai trò (ví dụ "user")

        // Chèn dữ liệu vào bảng NguoiDung
        long result = db.insert(TABLE_NGUOI_DUNG, null, values);

        db.close();

        // Trả về true nếu thêm thành công (id > 0), false nếu thất bại (id <= 0)
        return result > 0;
    }

    public boolean updateUser(int userId, String username, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Tạo đối tượng ContentValues để lưu trữ các giá trị cần cập nhật
        ContentValues values = new ContentValues();
        values.put(COLUMN_NGUOI_DUNG_TEN_DANG_NHAP, username);
        values.put(COLUMN_NGUOI_DUNG_MAT_KHAU, password);
        values.put(COLUMN_NGUOI_DUNG_VAI_TRO, role);

        // Cập nhật dữ liệu trong bảng NguoiDung theo ID
        int rowsAffected = db.update(TABLE_NGUOI_DUNG, values, COLUMN_NGUOI_DUNG_ID + " = ?", new String[]{String.valueOf(userId)});

        db.close();

        // Nếu có ít nhất một dòng bị ảnh hưởng, tức là cập nhật thành công
        return rowsAffected > 0;
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn người dùng theo tên đăng nhập
        Cursor cursor = db.query(
                TABLE_NGUOI_DUNG, // Tên bảng
                new String[]{COLUMN_NGUOI_DUNG_ID, COLUMN_NGUOI_DUNG_TEN_DANG_NHAP, COLUMN_NGUOI_DUNG_MAT_KHAU, COLUMN_NGUOI_DUNG_VAI_TRO}, // Các cột cần lấy
                COLUMN_NGUOI_DUNG_TEN_DANG_NHAP + "=?", // Điều kiện lọc theo tên đăng nhập
                new String[]{username}, // Giá trị điều kiện
                null, null, null); // Không nhóm, sắp xếp hay giới hạn

        if (cursor != null && cursor.moveToFirst()) {
            // Kiểm tra chỉ số cột trước khi sử dụng
            int idIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_ID);
            int tenDangNhapIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_TEN_DANG_NHAP);
            int matKhauIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_MAT_KHAU);
            int vaiTroIndex = cursor.getColumnIndex(COLUMN_NGUOI_DUNG_VAI_TRO);

            // Kiểm tra chỉ số cột hợp lệ (> 0)
            if (idIndex >= 0 && tenDangNhapIndex >= 0 && matKhauIndex >= 0 && vaiTroIndex >= 0) {
                User user = new User();
                user.setId(cursor.getInt(idIndex));
                user.setTenDangNhap(cursor.getString(tenDangNhapIndex));
                user.setMatKhau(cursor.getString(matKhauIndex));
                user.setVaiTro(cursor.getString(vaiTroIndex));
                cursor.close();
                return user;
            } else {
                cursor.close();
                return null; // Nếu có cột không hợp lệ
            }
        }

        db.close();
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    public void addNhomSanPham(String tenNhom, String logoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHOM_SAN_PHAM_TEN, tenNhom);  // Tên nhóm sản phẩm
        values.put(COLUMN_NHOM_SAN_PHAM_LOGO, logoUrl); // URL logo

        db.insert(TABLE_NHOM_SAN_PHAM, null, values);
        db.close();
    }

    public List<NhomSanPham> getAllNhomSanPham() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<NhomSanPham> categories = new ArrayList<>();

        // Truy vấn dữ liệu từ bảng NhomSanPham với các cột 'ten' và 'logoUrl'
        String selectQuery = "SELECT " + COLUMN_NHOM_SAN_PHAM_TEN + ", " + COLUMN_NHOM_SAN_PHAM_LOGO + " FROM " + TABLE_NHOM_SAN_PHAM;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NhomSanPham category = new NhomSanPham();

                // Kiểm tra cột tồn tại
                int tenIndex = cursor.getColumnIndex(COLUMN_NHOM_SAN_PHAM_TEN);
                int logoUrlIndex = cursor.getColumnIndex(COLUMN_NHOM_SAN_PHAM_LOGO);

                if (tenIndex != -1) {
                    category.setTen(cursor.getString(tenIndex));  // Lấy tên nhóm
                }

                if (logoUrlIndex != -1) {
                    category.setLogoUrl(cursor.getString(logoUrlIndex));  // Lấy logo URL
                }

                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public void updateCategory(String categoryName, String logoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHOM_SAN_PHAM_LOGO, logoUrl);  // Sử dụng cột 'logoUrl'

        db.update(TABLE_NHOM_SAN_PHAM, values, COLUMN_NHOM_SAN_PHAM_TEN + " = ?", new String[]{categoryName});
        db.close();
    }

    public void deleteCategory(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NHOM_SAN_PHAM, COLUMN_NHOM_SAN_PHAM_TEN + " = ?", new String[]{categoryName});
        db.close();
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_SAN_PHAM,
                new String[]{COLUMN_SAN_PHAM_ID, COLUMN_SAN_PHAM_TEN, COLUMN_SAN_PHAM_GIA, COLUMN_SAN_PHAM_MO_TA, COLUMN_SAN_PHAM_NHOM_ID},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_SAN_PHAM_ID);
                int tenIndex = cursor.getColumnIndex(COLUMN_SAN_PHAM_TEN);
                int giaIndex = cursor.getColumnIndex(COLUMN_SAN_PHAM_GIA);
                int moTaIndex = cursor.getColumnIndex(COLUMN_SAN_PHAM_MO_TA);
                int nhomIdIndex = cursor.getColumnIndex(COLUMN_SAN_PHAM_NHOM_ID);

                if (idIndex >= 0 && tenIndex >= 0 && giaIndex >= 0 && moTaIndex >= 0 && nhomIdIndex >= 0) {
                    Product product = new Product(
                            cursor.getInt(idIndex),
                            cursor.getString(tenIndex),
                            cursor.getDouble(giaIndex),
                            cursor.getString(moTaIndex),
                            cursor.getInt(nhomIdIndex)
                    );
                    products.add(product);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return products;
    }

    public boolean addProduct(String ten, double gia, String moTa, int nhomId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SAN_PHAM_TEN, ten);
        values.put(COLUMN_SAN_PHAM_GIA, gia);
        values.put(COLUMN_SAN_PHAM_MO_TA, moTa);
        values.put(COLUMN_SAN_PHAM_NHOM_ID, nhomId);

        long result = db.insert(TABLE_SAN_PHAM, null, values);
        db.close();

        return result > 0;
    }

    public boolean updateProduct(int productId, String ten, double gia, String moTa, int nhomId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SAN_PHAM_TEN, ten);
        values.put(COLUMN_SAN_PHAM_GIA, gia);
        values.put(COLUMN_SAN_PHAM_MO_TA, moTa);
        values.put(COLUMN_SAN_PHAM_NHOM_ID, nhomId);

        int rowsAffected = db.update(TABLE_SAN_PHAM, values, COLUMN_SAN_PHAM_ID + " = ?", new String[]{String.valueOf(productId)});

        db.close();

        // If at least one row was affected, return true, else false
        return rowsAffected > 0;
    }

    public boolean deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_SAN_PHAM, COLUMN_SAN_PHAM_ID + " = ?", new String[]{String.valueOf(productId)});

        db.close();

        return rowsDeleted > 0;
    }
}
