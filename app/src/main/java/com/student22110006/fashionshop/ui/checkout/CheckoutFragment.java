package com.student22110006.fashionshop.ui.checkout;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.FragmentCheckoutBinding;

import java.util.List;

public class CheckoutFragment extends Fragment {
    private FragmentCheckoutBinding binding;
    private CheckoutViewModel viewModel;

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

        viewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);

        viewModel.getSelectedProducts().observe(getViewLifecycleOwner(), products -> {
            // TODO: Hiển thị danh sách sản phẩm trong RecyclerView
        });

        viewModel.getDeliveryAddress().observe(getViewLifecycleOwner(), address -> {
            binding.tvDeliveryAddress.setText(address);
        });

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
