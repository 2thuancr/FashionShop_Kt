package com.student22110006.fashionshop.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.utils.CartManager;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private final CartManager cartManager = CartManager.getInstance();

    public LiveData<List<Product>> getCartProducts() {
        return cartManager.getCartProducts();
    }

    public void addProduct(Product product) {
        cartManager.addProduct(product);
    }

    public void removeProduct(Product product) {
        cartManager.removeProduct(product);
    }

    public double getTotalPrice() {
        return cartManager.getTotalPrice();
    }
}

