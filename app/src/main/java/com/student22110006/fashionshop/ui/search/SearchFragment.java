package com.student22110006.fashionshop.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.student22110006.fashionshop.adapter.ListProductAdapter;
import com.student22110006.fashionshop.databinding.FragmentSearchBinding;
import com.student22110006.fashionshop.data.model.product.Product;

import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private ListProductAdapter adapter;
    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Thiết lập EditText
        binding.searchEditText.requestFocus();

        binding.btnSort.setOnClickListener(v -> {
            // TODO: Mở hộp thoại sắp xếp hoặc thay đổi cách sắp xếp danh sách
            showSortDialog();
        });

        binding.btnFilter.setOnClickListener(v -> {
            // TODO: Mở hộp thoại lọc hoặc chuyển sang màn hình lọc
            showFilterDialog();
        });

        // Thiết lập RecyclerView
        binding.recyclerViewProducts.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        // Lắng nghe LiveData sản phẩm từ ViewModel
        searchViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            adapter = new ListProductAdapter(requireContext(), products);
            binding.recyclerViewProducts.setAdapter(adapter);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showSortDialog() {
        String[] sortOptions = {"Giá tăng dần", "Giá giảm dần", "Khuyến mãi tăng dần", "Khuyến mãi giảm dần", "Tên A-Z", "Tên Z-A",};

        new AlertDialog.Builder(requireContext())
                .setTitle("Sắp xếp theo")
                .setItems(sortOptions, (dialog, which) -> {
                    // Gọi hàm sortProducts() của ViewModel để thực hiện sắp xếp
                    searchViewModel.sortProducts(which);
                })
                .show();
    }

    private void showFilterDialog() {
        // Tạo dialog hoặc bottom sheet để lọc theo các tiêu chí như giá, kích thước, màu sắc, v.v.
        FilterBottomSheetFragment bottomSheet = new FilterBottomSheetFragment();
        bottomSheet.setOnFilterApplyListener((type, size) -> {
            // TODO: Gọi ViewModel hoặc cập nhật UI theo bộ lọc
            searchViewModel.filterProducts(type, size);
            Toast.makeText(requireContext(), "Loại: " + type + ", Kích thước: " + size, Toast.LENGTH_SHORT).show();
        });
        bottomSheet.show(getParentFragmentManager(), "FilterBottomSheet");
    }
}
