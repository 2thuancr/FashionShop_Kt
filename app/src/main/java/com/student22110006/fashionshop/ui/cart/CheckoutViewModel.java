package com.student22110006.fashionshop.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckoutViewModel extends ViewModel {
    private final MutableLiveData<String> deliveryAddress = new MutableLiveData<>();
    private final MutableLiveData<String> paymentMethod = new MutableLiveData<>();

    public void setDeliveryAddress(String address) {
        deliveryAddress.setValue(address);
    }

    public LiveData<String> getDeliveryAddress() {
        return deliveryAddress;
    }

    // Getter và Setter cho phương thức giao hàng
    public LiveData<String> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String method) {
        paymentMethod.setValue(method);
    }
}
