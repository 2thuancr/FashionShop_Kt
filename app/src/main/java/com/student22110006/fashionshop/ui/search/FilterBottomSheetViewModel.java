package com.student22110006.fashionshop.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FilterBottomSheetViewModel extends ViewModel {
    private final MutableLiveData<FilterOptions> filterOptions = new MutableLiveData<>();

    public LiveData<FilterOptions> getFilterOptions() {
        return filterOptions;
    }

    public void applyFilter(String selectedType, String selectedSize, int minPrice, int maxPrice) {
        filterOptions.setValue(new FilterOptions(selectedType, selectedSize, minPrice, maxPrice));
    }

    public static class FilterOptions {
        public final String type;
        public final String size;
        public final int minPrice;
        public final int maxPrice;

        public FilterOptions(String type, String size, int minPrice, int maxPrice) {
            this.type = type;
            this.size = size;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
        }
    }
}
