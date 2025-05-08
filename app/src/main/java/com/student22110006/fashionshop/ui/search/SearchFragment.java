package com.student22110006.fashionshop.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.ListProductAdapter;
import com.student22110006.fashionshop.databinding.FragmentSearchBinding;
import com.student22110006.fashionshop.data.model.product.Product;

import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private ListProductAdapter adapter;
    private SearchViewModel searchViewModel;
    private boolean isLoading = false;
    private int currentPage = 1;
    private final int pageSize = 10;
    private boolean isLastPage = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
        binding.recyclerViewProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) return;

                int totalItemCount = layoutManager.getItemCount();
                int[] lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null);
                int lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);

                // Kiểm tra nếu đã cuộn đến gần cuối danh sách
                if (!isLoading && !isLastPage && lastVisibleItemPosition + 3 >= totalItemCount) {
                    isLoading = true;
                    currentPage++;
                    searchViewModel.search(currentPage, pageSize, null);
                }
            }
        });

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Lắng nghe LiveData sản phẩm từ ViewModel
        searchViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            isLoading = false;

            if (products == null || products.isEmpty() || products.size() < pageSize) {
                isLastPage = true; // Không còn dữ liệu để tải
            }

            if (binding.recyclerViewProducts.getAdapter() == null) {
                adapter = new ListProductAdapter(requireContext(), products, this::showProductDetails);
                binding.recyclerViewProducts.setAdapter(adapter);
            } else {
                adapter.appendProducts(products); // -> bạn cần thêm hàm này trong adapter
            }
        });

        searchViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            // Xử lý khi có lỗi
            if (getContext() != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
            Log.e("SearchFragment", "Error: " + error);
        });
        // Mặc định lấy 10 sản phẩm
        // searchViewModel.loadProducts(1, 10);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0 || lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
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

    private void showProductDetails(Product product) {
        // Lấy NavController từ NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Điều hướng sang navigation_product_detail fragment
        navController.navigate(R.id.navigation_product_detail);
    }
}
