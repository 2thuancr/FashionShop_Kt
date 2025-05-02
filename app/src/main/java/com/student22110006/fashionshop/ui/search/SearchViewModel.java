package com.student22110006.fashionshop.ui.search;

import static com.student22110006.fashionshop.ui.search.SearchViewModel.SortType.SORT_BY_PRICE_ASC;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.student22110006.fashionshop.data.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<Product>> productList = new MutableLiveData<>();

    public SearchViewModel() {
        LoadProductData();
    }

    private void LoadProductData() {
        List<Product> listProducts = new ArrayList<>();

        // Thêm sản phẩm vào danh sách
        listProducts.add(new Product(1, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", 120000.0, 20.0, 17, "M - Áo thun unisex cổ tròn tay ngắn Star Big Lux", "https://product.hstatic.net/200000642007/product/50whs_3atst0153_2_9b6e53b833ee483cb9281843d361a1ed_e2eb29fcebe04276afd08adaf11d4dfd_grande.jpg"));
        listProducts.add(new Product(2, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", 5000000.0, 15.0, 35, "X - Áo thun unisex cổ tròn tay ngắn Star Big Lux", "https://product.hstatic.net/200000642007/product/50bks_3atst0153_2_7fdcb64c273049c0bc81e02f02b8763c_e1cc238f10104daaa97e606121649107_grande.jpg"));
        listProducts.add(new Product(3, "MLB - Áo thun unisex cổ tròn tay ngắn Basic Coopers", 1000000.0, 0.0, 4, "XL - Áo thun unisex cổ tròn tay ngắn Basic Coopers", "https://product.hstatic.net/200000642007/product/50ivs_3atsb1153_2_178394b32f8e4b52aa7871a37561a90c_a98589dd19e048cdacc3eedf2921e188_grande.jpg"));
        listProducts.add(new Product(4, "MLB - Áo sweatshirt unisex Basic Gorpcore Woven Piste", 7000000.0, 12.0, 6, "M - Áo sweatshirt unisex Basic Gorpcore Woven Piste", "https://product.hstatic.net/200000642007/product/50bks_3amtb0851_2_ca3f7d7fa01b48a5948ccd8b2b5198c6_1634db3329414fe3b01b7ff5b96212c9_grande.jpg"));
        listProducts.add(new Product(5, "MLB - Áo sweatshirt unisex cổ tròn tay dài College T Varsity", 2000000.0, 0.0, 15, "S - Áo sweatshirt unisex cổ tròn tay dài College T Varsity", "https://product.hstatic.net/200000642007/product/50crs_3amtv0651_2_e7e58bfad69a4b14a8f5c959513e31d3_d0ab112619dd4e2cb0520ffc1b2794df_grande.jpg"));

        productList.setValue(listProducts);
    }

    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    public void filterProducts(String type, String size) {
        // Lọc danh sách sản phẩm và postValue lại cho LiveData
        List<Product> filteredList = new ArrayList<>();

        for (Product product : productList.getValue()) {
            filteredList.add(product);
        }
        productList.setValue(filteredList);
    }

    public void sortProducts(int type) {
        // Sắp xếp danh sách và postValue lại cho LiveData
        List<Product> sortedList = new ArrayList<>(productList.getValue());
        switch (type) {
            case SORT_BY_PRICE_ASC:
                sortedList.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
                break;
            case SortType.SORT_BY_PRICE_DESC:
                sortedList.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
                break;
            case SortType.SORT_BY_DISCOUNT_ASC:
                sortedList.sort((p1, p2) -> Double.compare(p1.getDiscount(), p2.getDiscount()));
                break;
            case SortType.SORT_BY_DISCOUNT_DESC:
                sortedList.sort((p1, p2) -> Double.compare(p2.getDiscount(), p1.getDiscount()));
                break;
            case SortType.SORT_BY_NAME_ASC:
                sortedList.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
                break;
            case SortType.SORT_BY_NAME_DESC:
                sortedList.sort((p1, p2) -> p2.getName().compareTo(p1.getName()));
                break;
            default:
                break;
        }

        productList.setValue(sortedList);
    }

    public static class SortType {
        public static final int SORT_BY_PRICE_ASC = 0;
        public static final int SORT_BY_PRICE_DESC = 1;
        public static final int SORT_BY_DISCOUNT_ASC = 2;
        public static final int SORT_BY_DISCOUNT_DESC = 3;
        public static final int SORT_BY_NAME_ASC = 4;
        public static final int SORT_BY_NAME_DESC = 5;
    }
}

