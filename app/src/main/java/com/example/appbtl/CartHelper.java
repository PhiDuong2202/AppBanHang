package com.example.appbtl;

import java.util.ArrayList;
import java.util.List;

public class CartHelper {

    // Danh sách giỏ hàng (có thể thay bằng cơ sở dữ liệu hoặc SharedPreferences)
    private static List<Product> cartItems = new ArrayList<>();

    // Thêm sản phẩm vào giỏ hàng
    public static void addToCart(Product product) {
        cartItems.add(product);
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public static List<Product> getCartItems() {
        return cartItems;
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public static void removeProductFromCart(Product product) {
        cartItems.remove(product);
    }

    // Tính tổng giá trị giỏ hàng
    public static double getTotalPrice() {
        double total = 0;
        for (Product product : cartItems) {
            total += product.getPrice();
        }
        return total;
    }

    // Làm trống giỏ hàng
    public static void clearCart() {
        cartItems.clear();
    }
}
