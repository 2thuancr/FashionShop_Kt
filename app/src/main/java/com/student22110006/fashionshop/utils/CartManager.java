package com.student22110006.fashionshop.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.student22110006.fashionshop.data.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final MutableLiveData<List<Product>> cartProducts = new MutableLiveData<>(new ArrayList<>());

    private CartManager() {
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public LiveData<List<Product>> getCartProducts() {
        return cartProducts;
    }

    public void addProduct(Product product) {
        List<Product> current = new ArrayList<>(cartProducts.getValue());
        current.add(product);
        cartProducts.setValue(current);
    }

    public void removeProduct(Product product) {
        List<Product> current = new ArrayList<>(cartProducts.getValue());
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
}
