package com.student22110006.fashionshop.ui.checkout;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.CartProductAdapter;
import com.student22110006.fashionshop.adapter.ListProductAdapter;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.FragmentCheckoutBinding;
import com.student22110006.fashionshop.ui.cart.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CheckoutFragment extends Fragment {

    private CartProductAdapter productAdapter;
    private FragmentCheckoutBinding binding;
    private CartViewModel cartViewModel;
    private CheckoutViewModel checkoutViewModel;

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
        productAdapter = new CartProductAdapter(requireContext(), new ArrayList<>());
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
                binding.tvDeliveryAddress.setText(address);
            }
        });

        // Quan sát phương thức giao hàng
        checkoutViewModel.getDeliveryMethod().observe(getViewLifecycleOwner(), method -> {
            if (method != null) {
                binding.tvDeliveryMethod.setText(method);
            }
        });

        // Xử lý sự kiện xác nhận đơn hàng
        binding.btnConfirmOrder.setOnClickListener(v -> {
            // TODO: Gửi thông tin đặt hàng lên server
            Toast.makeText(requireContext(), "Order placed!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
