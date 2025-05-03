package com.student22110006.fashionshop.ui.checkout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.student22110006.fashionshop.data.model.product.Product;

import java.util.List;

public class CheckoutViewModel extends ViewModel {
    private final MutableLiveData<List<Product>> selectedProducts = new MutableLiveData<>();
    private final MutableLiveData<String> deliveryAddress = new MutableLiveData<>();

    public void setSelectedProducts(List<Product> products) {
        selectedProducts.setValue(products);
    }

    public LiveData<List<Product>> getSelectedProducts() {
        return selectedProducts;
    }

    public void setDeliveryAddress(String address) {
        deliveryAddress.setValue(address);
    }

    public LiveData<String> getDeliveryAddress() {
        return deliveryAddress;
    }
}
