package com.student22110006.fashionshop.ui.cart;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.student22110006.fashionshop.data.model.order.Order;
import com.student22110006.fashionshop.data.model.order.OrderItem;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.utils.CartManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartViewModel extends ViewModel {
    private final CartManager cartManager = CartManager.getInstance();

    public LiveData<List<OrderItem>> getCartItems() {
        return cartManager.getCartItems();
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

    public void clearCart() {
        cartManager.clearCart();
    }

    public Order createOrder(int customerId) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setBusinessTime(new Date());
        order.setStatus(0); // Pending, chẳng hạn

        List<OrderItem> items = cartManager.getCartItems().getValue();
        order.setItems(items);

        double totalPrice = 0, totalDiscount = 0;
        for (OrderItem item : items) {
            double fullPrice = item.getPrice() * item.getAmount();
            double discountAmount = fullPrice * item.getDiscount() / 100;
            totalPrice += fullPrice - discountAmount;
            totalDiscount += discountAmount;
        }

        order.setTotalPrice(totalPrice);
        order.setTotalDiscount(totalDiscount);

        return order;
    }
}

