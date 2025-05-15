package com.student22110006.fashionshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.student22110006.fashionshop.data.model.order.OrderItem;
import com.student22110006.fashionshop.data.model.product.Product;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CartManager {
    private SharedPreferences sharedPreferences;
    private final String CART_KEY = "cart_items";
    private final Gson gson = new Gson();
    private static CartManager instance;
    private final MutableLiveData<List<OrderItem>> cartItems = new MutableLiveData<>(new ArrayList<>());

    private CartManager() {
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences("FashionShop", Context.MODE_PRIVATE);
        loadCartFromPrefs();
    }

    public void saveCartToPrefs() {
        if (sharedPreferences == null) return;
        List<OrderItem> items = cartItems.getValue();
        String json = gson.toJson(items);
        sharedPreferences.edit().putString(CART_KEY, json).apply();
        Log.d("CartManager", "Cart saved to SharedPreferences: " + json);
    }

    private void loadCartFromPrefs() {
        if (sharedPreferences == null) return;
        String json = sharedPreferences.getString(CART_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<OrderItem>>() {
            }.getType();
            List<OrderItem> items = gson.fromJson(json, type);
            cartItems.setValue(items);
        }
    }

    public LiveData<List<OrderItem>> getCartItems() {
        return cartItems;
    }

    public void addProduct(Product product) {
        List<OrderItem> currentItems = new ArrayList<>(cartItems.getValue());

        for (OrderItem item : currentItems) {
            if (item.getProductId() == product.getId()) {
                item.setAmount(item.getAmount() + 1);
                cartItems.setValue(currentItems);
                saveCartToPrefs();
                return;
            }
        }

        OrderItem newItem = new OrderItem();
        newItem.setProductId(product.getId());
        newItem.setName(product.getName());
        newItem.setImageUrl(product.getImageUrl());
        newItem.setAmount(1);
        newItem.setPrice(product.getPrice());
        newItem.setDiscount(product.getDiscount());
        newItem.setCampaignDiscountPercent(0.0);

        currentItems.add(newItem);
        cartItems.setValue(currentItems);
        saveCartToPrefs();
    }

    public void removeProduct(Product product) {
        List<OrderItem> currentItems = new ArrayList<>(cartItems.getValue());

        Iterator<OrderItem> iterator = currentItems.iterator();
        while (iterator.hasNext()) {
            OrderItem item = iterator.next();
            if (item.getProductId() == product.getId()) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    iterator.remove();
                }
                break;
            }
        }

        cartItems.setValue(currentItems);
        saveCartToPrefs();
    }

    public void removeProductById(int productId) {
        List<OrderItem> currentItems = new ArrayList<>(cartItems.getValue());

        Iterator<OrderItem> iterator = currentItems.iterator();
        while (iterator.hasNext()) {
            OrderItem item = iterator.next();
            if (item.getProductId() == productId) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    iterator.remove();
                }
                break;
            }
        }

        cartItems.setValue(currentItems);
        saveCartToPrefs();
    }

    public void clearCart() {
        cartItems.setValue(new ArrayList<>());
        saveCartToPrefs();
    }

    public double getTotalPrice() {
        double total = 0;
        for (OrderItem item : cartItems.getValue()) {
            double discountedPrice = item.getPrice() * (1 - item.getDiscount() / 100);
            total += discountedPrice * item.getAmount();
        }
        return total;
    }

    public void updateProductAmount(int productId, int newAmount) {
        List<OrderItem> currentItems = new ArrayList<>(cartItems.getValue());

        for (OrderItem item : currentItems) {
            if (item.getProductId() == productId) {
                if (newAmount <= 0) {
                    currentItems.remove(item);
                } else {
                    item.setAmount(newAmount);
                }
                break;
            }
        }

        cartItems.setValue(currentItems);
        saveCartToPrefs();
    }
}
