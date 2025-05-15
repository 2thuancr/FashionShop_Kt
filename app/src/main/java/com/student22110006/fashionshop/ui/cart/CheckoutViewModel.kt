// CheckoutViewModel.kt
package com.student22110006.fashionshop.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.student22110006.fashionshop.data.model.ApiResponse
import com.student22110006.fashionshop.data.model.order.Order
import com.student22110006.fashionshop.data.repository.OrderRepository
import kotlinx.coroutines.launch


class CheckoutViewModel : ViewModel() {
    private val orderRepository = OrderRepository()

    private val billId = MutableLiveData<Int>()
    fun getBillId(): LiveData<Int> {
        return billId
    }

    fun setBillId(id: Int) {
        billId.value = id
    }

    private val _deliveryAddress = MutableLiveData<String>()
    val deliveryAddress: LiveData<String> get() = _deliveryAddress

    private val _paymentMethod = MutableLiveData<String>()
    val paymentMethod: LiveData<String> get() = _paymentMethod

    private val _orderResult = MutableLiveData<ApiResponse<Order>>()
    val orderResult: LiveData<ApiResponse<Order>> get() = _orderResult

    fun setDeliveryAddress(address: String) {
        _deliveryAddress.value = address
    }

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }

    fun createOrder(order: Order) {
        viewModelScope.launch {
            try {
                val response = orderRepository.createOrder(order)
                if (response.isSuccess && response.data != null) {
                    // Lưu orderId để dùng khi checkout
                    setBillId(response.data.id)
                } else {
                    // Xử lý lỗi
                }
            } catch (e: Exception) {
                // Xử lý ngoại lệ
            }
        }
    }

    fun checkout(order: Order) {
        viewModelScope.launch {
            try {
                val response = orderRepository.checkout(order)
                _orderResult.postValue(response)
            } catch (e: Exception) {
                val error = ApiResponse<Order>(
                    isSuccess = false,
                    message = e.message ?: "Unknown error",
                    errorCode = null,
                    exceptionDetail = e.toString(),
                    data = null
                )
                _orderResult.postValue(error)
            }
        }
    }
}
