package com.student22110006.fashionshop.ui.home;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.student22110006.fashionshop.data.repository.ProductRepository

class HomeViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
