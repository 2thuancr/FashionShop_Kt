package com.student22110006.fashionshop.ui.checkout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.student22110006.fashionshop.data.model.product.Product;

import java.util.List;

public class CheckoutViewModel extends ViewModel {
    private final MutableLiveData<String> deliveryAddress = new MutableLiveData<>();
    private final MutableLiveData<String> deliveryMethod = new MutableLiveData<>();

    public void setDeliveryAddress(String address) {
        deliveryAddress.setValue(address);
    }

    public LiveData<String> getDeliveryAddress() {
        return deliveryAddress;
    }

    // Getter và Setter cho phương thức giao hàng
    public LiveData<String> getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String method) {
        deliveryMethod.setValue(method);
    }
}
