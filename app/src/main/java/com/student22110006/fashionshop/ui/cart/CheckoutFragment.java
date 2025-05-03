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
import com.student22110006.fashionshop.adapter.CheckoutProductAdapter;
import com.student22110006.fashionshop.databinding.FragmentCheckoutBinding;

import java.util.ArrayList;

public class CheckoutFragment extends Fragment {

    private CheckoutProductAdapter productAdapter;
    private FragmentCheckoutBinding binding;
    private CartViewModel cartViewModel;
    private CheckoutViewModel checkoutViewModel;

    private final String[] addresses = {
            "216 St Paul’s Rd, London N1 2LL, UK\nContact: +44-78423XZ",
            "45 Queen St, Manchester M1 2AB, UK\nContact: +44-77300YX",
            "123 Oxford Rd, Birmingham B1 1AA, UK\nContact: +44-70011AA"
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo CheckoutViewModel
        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        // Khởi tạo CartViewModel và Adapter
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        productAdapter = new CheckoutProductAdapter(requireContext(), new ArrayList<>());
        binding.rvCheckoutProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCheckoutProducts.setAdapter(productAdapter);

        // Quan sát danh sách sản phẩm trong giỏ hàng
        cartViewModel.getCartProducts().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                productAdapter.updateList(products);
            }
        });

        // Quan sát địa chỉ giao hàng (nếu có)
        checkoutViewModel.getDeliveryAddress().observe(getViewLifecycleOwner(), address -> {
            if (address != null) {
                binding.tvAddress.setText(address);
            }
        });
        setupChangeAddress();

        // Quan sát phương thức giao hàng
        checkoutViewModel.getDeliveryMethod().observe(getViewLifecycleOwner(), method -> {
            if (method != null) {
                binding.tvDeliveryMethod.setText(method);
            }
        });

        // Xử lý sự kiện xác nhận đơn hàng
        binding.btnConfirmOrder.setOnClickListener(v -> {
            String address = binding.tvAddress.getText().toString();
            if (address == null || address.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Please select a delivery address", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Gửi thông tin đặt hàng lên server
            Toast.makeText(requireContext(), "Order placed!", Toast.LENGTH_SHORT).show();
        });

        String savedAddress = getSavedAddress();
        if (savedAddress != null) {
            binding.tvAddress.setText(savedAddress);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void saveAddressToPrefs(String address) {
        SharedPreferences prefs = requireContext().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        prefs.edit().putString("delivery_address", address).apply();
    }

    private String getSavedAddress() {
        SharedPreferences prefs = requireContext().getSharedPreferences("cart_prefs", Context.MODE_PRIVATE);
        return prefs.getString("delivery_address", null);
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

}
