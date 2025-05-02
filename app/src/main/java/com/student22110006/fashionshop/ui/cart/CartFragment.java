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
import android.widget.TextView;
import android.widget.Toast;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.CartProductAdapter;
import com.student22110006.fashionshop.databinding.FragmentCartBinding;
import com.student22110006.fashionshop.databinding.FragmentSettingsBinding;
import com.student22110006.fashionshop.ui.notifications.NotificationsViewModel;

import java.util.ArrayList;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        cartViewModel.loadProductData();

        adapter = new CartProductAdapter(requireContext(), new ArrayList<>());
        binding.rvCartProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCartProducts.setAdapter(adapter);

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

        binding.btnPlaceOrder.setOnClickListener(v -> {
            double total = cartViewModel.getTotalPrice();
            // TODO: Thực hiện logic đặt hàng
            Toast.makeText(requireContext(), "Order placed! Total: $" + total, Toast.LENGTH_LONG).show();
        });


        cartViewModel.getCartProducts().observe(getViewLifecycleOwner(), products -> {
            adapter = new CartProductAdapter(requireContext(), products);
            binding.rvCartProducts.setAdapter(adapter);

            // Optional: update total price
            double total = cartViewModel.getTotalPrice();
            binding.tvTotalPrice.setText(String.format("$%.2f", total));
        });

        String saved = getSavedAddress();
        if (saved != null) {
            binding.tvAddress.setText(saved);
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

}
