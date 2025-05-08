package com.student22110006.fashionshop.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.student22110006.fashionshop.data.model.product.Product
import com.student22110006.fashionshop.data.model.product.ProductGetAllRequest
import com.student22110006.fashionshop.data.model.product.ProductSearchRequest
import com.student22110006.fashionshop.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _productList = MutableLiveData<List<Product>?>()
    val productList: LiveData<List<Product>?> get() = _productList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        // loadDummyProducts(1, 10) // Giả lập tải dữ liệu từ API
        search(1, 10) // Thay thế bằng việc gọi API thực tế
    }

    fun search(page: Int, pageSize: Int, query: String? = null) {
        viewModelScope.launch {
            try {
                // Thực hiện call API trong coroutine và sử dụng Dispatchers.IO cho các công việc tốn thời gian
                val response = withContext(Dispatchers.IO) {
                    val request = ProductSearchRequest(page, pageSize, query)
                    repository.search(request)
                }
                if (response.data != null) {
                    _productList.value = response.data.items
                } else {
                    _error.value = "Lỗi khi tải dữ liệu"
                }
            } catch (e: Exception) {
                _error.value = "Lỗi: ${e.message}"
                Log.e("SearchViewModel", "Error loading products: ${e.message}")
                // throw e;
            }
        }
    }

    private fun loadDummyProducts(page: Int, pageSize: Int) {
        val listProducts = ArrayList<Product>()

        // Thêm sản phẩm vào danh sách
        listProducts.add(
            Product(
                1,
                "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux",
                120000.0,
                20.0,
                17,
                "M - Áo thun unisex cổ tròn tay ngắn Star Big Lux",
                "https://product.hstatic.net/200000642007/product/50whs_3atst0153_2_9b6e53b833ee483cb9281843d361a1ed_e2eb29fcebe04276afd08adaf11d4dfd_grande.jpg"
            )
        )
        listProducts.add(
            Product(
                2,
                "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux",
                5000000.0,
                15.0,
                35,
                "X - Áo thun unisex cổ tròn tay ngắn Star Big Lux",
                "https://product.hstatic.net/200000642007/product/50bks_3atst0153_2_7fdcb64c273049c0bc81e02f02b8763c_e1cc238f10104daaa97e606121649107_grande.jpg"
            )
        )
        listProducts.add(
            Product(
                3,
                "MLB - Áo thun unisex cổ tròn tay ngắn Basic Coopers",
                1000000.0,
                0.0,
                4,
                "XL - Áo thun unisex cổ tròn tay ngắn Basic Coopers",
                "https://product.hstatic.net/200000642007/product/50ivs_3atsb1153_2_178394b32f8e4b52aa7871a37561a90c_a98589dd19e048cdacc3eedf2921e188_grande.jpg"
            )
        )
        listProducts.add(
            Product(
                4,
                "MLB - Áo sweatshirt unisex Basic Gorpcore Woven Piste",
                7000000.0,
                12.0,
                6,
                "M - Áo sweatshirt unisex Basic Gorpcore Woven Piste",
                "https://product.hstatic.net/200000642007/product/50bks_3amtb0851_2_ca3f7d7fa01b48a5948ccd8b2b5198c6_1634db3329414fe3b01b7ff5b96212c9_grande.jpg"
            )
        )
        listProducts.add(
            Product(
                5,
                "MLB - Áo sweatshirt unisex cổ tròn tay dài College T Varsity",
                2000000.0,
                0.0,
                15,
                "S - Áo sweatshirt unisex cổ tròn tay dài College T Varsity",
                "https://product.hstatic.net/200000642007/product/50crs_3amtv0651_2_e7e58bfad69a4b14a8f5c959513e31d3_d0ab112619dd4e2cb0520ffc1b2794df_grande.jpg"
            )
        )

        _productList.value = listProducts
    }

    fun filterProducts(type: String, size: String) {
        val filteredList = ArrayList<Product>()

        // Lọc danh sách sản phẩm và postValue lại cho LiveData
        productList.value?.let {
            for (product in it) {
                filteredList.add(product) // Đây là bước lọc thực tế
            }
        }

        _productList.value = filteredList
    }

    fun sortProducts(type: Int) {
        val sortedList = ArrayList<Product>(_productList.value ?: emptyList())

        when (type) {
            SortType.SORT_BY_PRICE_ASC -> sortedList.sortBy { it.price }
            SortType.SORT_BY_PRICE_DESC -> sortedList.sortByDescending { it.price }
            SortType.SORT_BY_DISCOUNT_ASC -> sortedList.sortBy { it.discount }
            SortType.SORT_BY_DISCOUNT_DESC -> sortedList.sortByDescending { it.discount }
            SortType.SORT_BY_NAME_ASC -> sortedList.sortBy { it.name }
            SortType.SORT_BY_NAME_DESC -> sortedList.sortByDescending { it.name }
        }

        _productList.value = sortedList
    }

    object SortType {
        const val SORT_BY_PRICE_ASC = 0
        const val SORT_BY_PRICE_DESC = 1
        const val SORT_BY_DISCOUNT_ASC = 2
        const val SORT_BY_DISCOUNT_DESC = 3
        const val SORT_BY_NAME_ASC = 4
        const val SORT_BY_NAME_DESC = 5
    }
}
