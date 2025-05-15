package com.student22110006.fashionshop.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.student22110006.fashionshop.data.model.product.Product
import com.student22110006.fashionshop.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _product = MutableLiveData<Product?>()
    public val product: MutableLiveData<Product?> = _product

    private val _relatedProducts = MutableLiveData<List<Product>?>()
    val relatedProducts: MutableLiveData<List<Product>?> = _relatedProducts

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getProductById(productId)
                if (response.isSuccess) {
                    _product.value = response.data
                    loadRelatedProducts(productId)
                } else {
                    _error.value = response.message ?: "Không lấy được thông tin sản phẩm."
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    private fun loadRelatedProducts(productId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getRelatedProducts(productId)
                if (response.isSuccess) {
                    _relatedProducts.value = response.data
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
