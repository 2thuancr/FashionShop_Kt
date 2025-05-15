package com.student22110006.fashionshop.ui.cart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.CheckoutProductAdapter;
import com.student22110006.fashionshop.data.model.order.Order;
import com.student22110006.fashionshop.data.model.order.OrderItem;
import com.student22110006.fashionshop.databinding.FragmentCheckoutBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutFragment extends Fragment {

    private CheckoutProductAdapter checkoutProductAdapter;
    private FragmentCheckoutBinding binding;
    private CheckoutViewModel checkoutViewModel;
    private double totalPrice = 0;

    private final String[] addresses = {
            "70 D1, Phường Hiệp Phú, Tp. Thủ Đức, HCM\nContact: +84-12345AB",
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

        // Khởi tạo ViewModel và Adapter
        checkoutProductAdapter = new CheckoutProductAdapter(requireContext(), new ArrayList<>());
        binding.rvCheckoutProducts.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvCheckoutProducts.setAdapter(checkoutProductAdapter);

        Bundle args = getArguments();
        if (args != null) {
            String cartJson = args.getString("cart_selected_items_json");
            if (cartJson != null) {
                Type type = new TypeToken<List<OrderItem>>() {
                }.getType();

                List<OrderItem> orderItemList = new Gson().fromJson(cartJson, type);
                if (orderItemList != null) {
                    checkoutProductAdapter.updateList(orderItemList);
                }

                totalPrice = orderItemList.stream().mapToDouble(item -> item.getAmount() * item.getPrice()).sum();
                binding.tvTotalPrice.setText(String.format("%.0f đ", totalPrice));
            }
        }

        // Quan sát địa chỉ giao hàng (nếu có)
        checkoutViewModel.getDeliveryAddress().observe(getViewLifecycleOwner(), address -> {
            if (address != null) {
                binding.tvAddress.setText(address);
            }
        });
        setupChangeAddress();

        // Quan sát phương thức giao hàng
        checkoutViewModel.getPaymentMethod().observe(getViewLifecycleOwner(), method -> {
            if (method != null) {
                binding.tvPaymentMethod.setText(method);
            }
        });

        // Xử lý sự kiện xác nhận đơn hàng
        binding.btnConfirmOrder.setOnClickListener(v -> {
            String address = binding.tvAddress.getText().toString().trim();
            if (address.trim().isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng điền địa chỉ nhận hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy customer ID từ SharedPreferences
            SharedPreferences prefs = requireContext().getSharedPreferences("FashionShop", Context.MODE_PRIVATE);
            String customerId = prefs.getString("customerId", "");
            if (customerId == "") {
                Toast.makeText(requireContext(), "Vui lòng đăng nhập để đặt hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            Order order = new Order();
            order.setDeliveryAddress(address);
            order.setPaymentMethod(checkoutViewModel.getPaymentMethod().getValue());
            order.setItems(checkoutProductAdapter.getOrderItemList());
            order.setCustomerId(Integer.parseInt(customerId));
            // order.setBusinessTime(new Date());
            order.setStatus(0); // Trạng thái đơn hàng (0: Đang chờ xử lý)
            order.setTotalPrice(totalPrice);
            order.setTotalDiscount(0); // Giả sử không có giảm giá

            checkoutViewModel.checkout(order);
        });

        String savedAddress = getSavedAddress();
        if (savedAddress != null) {
            binding.tvAddress.setText(savedAddress);
        }

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });
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
