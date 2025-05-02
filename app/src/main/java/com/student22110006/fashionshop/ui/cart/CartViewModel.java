package com.student22110006.fashionshop.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.student22110006.fashionshop.data.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> cartProducts = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Product>> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<Product> products) {
        cartProducts.setValue(products);
    }

    public void addProduct(Product product) {
        List<Product> current = cartProducts.getValue();
        current.add(product);
        cartProducts.setValue(current);
    }

    public void removeProduct(Product product) {
        List<Product> current = cartProducts.getValue();
        current.remove(product);
        cartProducts.setValue(current);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product p : cartProducts.getValue()) {
            total += p.getPrice();
        }
        return total;
    }

    public void loadProductData() {
        List<Product> listProducts = new ArrayList<>();
        // Thêm sản phẩm vào danh sách
        listProducts.add(new Product(1, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", 120000.0, 20.0, 17, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", "https://product.hstatic.net/200000642007/product/50whs_3atst0153_2_9b6e53b833ee483cb9281843d361a1ed_e2eb29fcebe04276afd08adaf11d4dfd_grande.jpg"));
        listProducts.add(new Product(2, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", 5000000.0, 15.0, 35, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", "https://product.hstatic.net/200000642007/product/50bks_3atst0153_2_7fdcb64c273049c0bc81e02f02b8763c_e1cc238f10104daaa97e606121649107_grande.jpg"));
        listProducts.add(new Product(3, "MLB - Áo thun unisex cổ tròn tay ngắn Basic Coopers", 5000000.0, 0.0, 4, "MLB - Áo thun unisex cổ tròn tay ngắn Basic Coopers", "https://product.hstatic.net/200000642007/product/50ivs_3atsb1153_2_178394b32f8e4b52aa7871a37561a90c_a98589dd19e048cdacc3eedf2921e188_grande.jpg"));
        listProducts.add(new Product(4, "MLB - Áo sweatshirt unisex Basic Gorpcore Woven Piste", 5000000.0, 12.0, 6, "MLB - Áo sweatshirt unisex Basic Gorpcore Woven Piste", "https://product.hstatic.net/200000642007/product/50bks_3amtb0851_2_ca3f7d7fa01b48a5948ccd8b2b5198c6_1634db3329414fe3b01b7ff5b96212c9_grande.jpg"));
        listProducts.add(new Product(5, "MLB - Áo sweatshirt unisex cổ tròn tay dài College T Varsity", 5000000.0, 0.0, 15, "MLB - Áo sweatshirt unisex cổ tròn tay dài College T Varsity", "https://product.hstatic.net/200000642007/product/50crs_3amtv0651_2_e7e58bfad69a4b14a8f5c959513e31d3_d0ab112619dd4e2cb0520ffc1b2794df_grande.jpg"));

        cartProducts.setValue(listProducts);
    }
}
