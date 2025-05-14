package com.student22110006.fashionshop.ui.profile

import android.util.Log
import androidx.lifecycle.*
import com.student22110006.fashionshop.data.model.customer.CustomerInfoData
import com.student22110006.fashionshop.data.model.customer.CustomerInfoGetInput
import com.student22110006.fashionshop.data.repository.CustomerRepository
import kotlinx.coroutines.launch

class ProfileViewModel @JvmOverloads constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _customerInfo = MutableLiveData<CustomerInfoData?>()
    val customerInfo: MutableLiveData<CustomerInfoData?> get() = _customerInfo

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchCustomerInfo(email: String?, userName: String?, phoneNumber: String?) {
        val request = CustomerInfoGetInput(email, null, null)
        Log.d("ProfileViewModel", "Fetching customer info with request: ${request.email}")
        viewModelScope.launch {
            try {
                val response = repository.getCustomerInfo(request)
                if (response.isSuccess) {
                    _customerInfo.postValue(response.data)
                } else {
                    _error.postValue(response.message ?: "Không thể lấy thông tin")
                }
            } catch (e: Exception) {
                _error.postValue("Lỗi kết nối: ${e.message}")
            }
        }
    }
}
