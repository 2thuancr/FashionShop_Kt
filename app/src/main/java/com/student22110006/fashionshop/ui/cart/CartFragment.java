package com.student22110006.fashionshop.ui.cart;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.student22110006.fashionshop.adapter.CartProductAdapter;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartViewModel cartViewModel;
    private CartProductAdapter adapter;

    private final String[] addresses = {
            "216 St Paul’s Rd, London N1 2LL, UK\nContact: +44-78423XZ",
            "45 Queen St, Manchester M1 2AB, UK\nContact: +44-77300YX",
            "123 Oxford Rd, Birmingham B1 1AA, UK\nContact: +44-70011AA"
    };

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
        setupChangeAddress();
        setupPlaceOrderButton();

        cartViewModel.getCartProducts().observe(getViewLifecycleOwner(), products -> {
            adapter.updateList(products);
            updateTotalPrice();
        });

        adapter.setOnQuantityChangeListener(() -> {
            updateTotalPrice();
        });

        String saved = getSavedAddress();
        if (saved != null) {
            binding.tvAddress.setText(saved);
        }
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

        adapter.setOnQuantityChangeListener(() -> {
            updateTotalPrice();
        });
    }

    private void setupSelectAllCheckbox() {
        binding.checkboxSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            adapter.selectAllItems(isChecked);
            updateTotalPrice();
        });
    }

    private void setupChangeAddress() {
        binding.ivChangeAddress.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Select Delivery Address");
            builder.setItems(addresses, (dialog, which) -> {
                String address = addresses[which];
                saveAddressToPrefs(address);
                binding.tvAddress.setText(address);
                Toast.makeText(requireContext(), "Address selected", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
    }

    private void setupPlaceOrderButton() {
        binding.btnPlaceOrder.setOnClickListener(v -> {
            List<Product> selectedProducts = adapter.getSelectedProducts();
            if (selectedProducts == null || selectedProducts.isEmpty()) {
                Toast.makeText(requireContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            String address = binding.tvAddress.getText().toString();
            if (address == null || address.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Please select a delivery address", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(requireContext())
                    .setTitle("Order Placed")
                    .setMessage("Your order has been placed successfully!")
                    .setPositiveButton("OK", (dialog, which) -> {
                        cartViewModel.setCartProducts(new ArrayList<>());
                    })
                    .show();
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

    private void saveAddressToPrefs(String address) {
        SharedPreferences prefs = requireContext().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        prefs.edit().putString("delivery_address", address).apply();
    }

    private String getSavedAddress() {
        SharedPreferences prefs = requireContext().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        return prefs.getString("delivery_address", null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
