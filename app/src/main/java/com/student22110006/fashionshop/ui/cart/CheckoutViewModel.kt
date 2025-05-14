// CheckoutViewModel.kt
package com.student22110006.fashionshop.ui.cart

import androidx.lifecycle.*
import com.student22110006.fashionshop.data.model.ApiResponse
import com.student22110006.fashionshop.data.model.order.Order
import com.student22110006.fashionshop.data.repository.OrderRepository
import kotlinx.coroutines.launch

class CheckoutViewModel : ViewModel() {
    private val orderRepository = OrderRepository()

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
