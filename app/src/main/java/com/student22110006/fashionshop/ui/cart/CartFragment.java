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

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.CartProductAdapter;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

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
        cartViewModel.loadProductData();

        setupRecyclerView();
        setupSelectAllCheckbox();
        setupPlaceOrderButton();

        cartViewModel.getCartProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.updateList(products);
            updateTotalPrice();
        });

        adapter.setOnQuantityChangeListener(() -> {
            updateTotalPrice();
        });
    }

    private void setupRecyclerView() {
        adapter = new CartProductAdapter(requireContext(), new ArrayList<>());
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
            ArrayList<Product> selectedProducts = adapter.getSelectedProducts();
            if (selectedProducts == null || selectedProducts.isEmpty()) {
                Toast.makeText(requireContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy NavController từ NavHostFragment
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            // Điều hướng tới CheckoutFragment bằng hành động được định nghĩa trong navigation graph
            navController.navigate(R.id.navigation_checkout);
        });
    }


    private void updateTotalPrice() {
        List<Product> selected = adapter.getSelectedProducts();
        double total = 0.0;
        for (Product p : selected) {
            total += p.getQuantity() * p.getPrice();
        }
        binding.tvTotalPrice.setText(String.format("$%.2f", total));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
