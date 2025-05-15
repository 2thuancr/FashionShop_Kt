package com.student22110006.fashionshop.ui.cart;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.CartProductAdapter;
import com.student22110006.fashionshop.data.model.order.OrderItem;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.FragmentCartBinding;
import com.student22110006.fashionshop.utils.CartManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartViewModel cartViewModel;
    private CartProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        setupRecyclerView();
        setupSelectAllCheckbox();
        setupPlaceOrderButton();

        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), products -> {
            adapter.updateList(products);
            updateTotalPrice();
        });

        adapter.setOnQuantityChangeListener(this::updateTotalPrice);
    }

    private void setupRecyclerView() {
        adapter = new CartProductAdapter(requireContext(), new ArrayList<>(), CartManager.getInstance());
        binding.rvCartProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCartProducts.setAdapter(adapter);

        adapter.setOnSelectionChangeListener(allSelected -> {
            binding.checkboxSelectAll.setOnCheckedChangeListener(null);
            binding.checkboxSelectAll.setChecked(allSelected);
            setupSelectAllCheckbox(); // Gán lại listener sau khi cập nhật state
        });

        adapter.setOnQuantityChangeListener(this::updateTotalPrice);
    }

    private void setupSelectAllCheckbox() {
        binding.checkboxSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            adapter.selectAllItems(isChecked);
            updateTotalPrice();
        });
    }

    private void setupPlaceOrderButton() {
        binding.btnPlaceOrder.setOnClickListener(v -> {
            List<OrderItem> selectedItems = adapter.getSelectedItems();
            if (selectedItems == null || selectedItems.isEmpty()) {
                Toast.makeText(requireContext(), "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert danh sách sang JSON
            String cartJson = new Gson().toJson(selectedItems);

            // Tạo Bundle để truyền dữ liệu
            Bundle bundle = new Bundle();
            bundle.putString("cart_selected_items_json", cartJson);

            // Điều hướng tới CheckoutFragment kèm dữ liệu
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_checkout, bundle);
        });
    }

    private void updateTotalPrice() {
        List<OrderItem> selected = adapter.getSelectedItems();
        double total = 0.0;
        for (OrderItem item : selected) {
            total += item.getAmount() * item.getPrice();
        }

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = formatter.format(total) + " đ";
        binding.tvTotalPrice.setText(formattedPrice);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
