package com.student22110006.fashionshop.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.student22110006.fashionshop.adapter.OrderHistoryAdapter;
import com.student22110006.fashionshop.data.model.order.OrderItem;
import com.student22110006.fashionshop.databinding.FragmentOrderHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFragment extends Fragment {

    private FragmentOrderHistoryBinding binding;
    private OrderHistoryAdapter adapter;

    public static OrderHistoryFragment newInstance() {
        return new OrderHistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadSampleData();
    }

    private void setupRecyclerView() {
        binding.recyclerViewOrders.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new OrderHistoryAdapter(requireContext(), new ArrayList<>());
        binding.recyclerViewOrders.setAdapter(adapter);
    }

    private void loadSampleData() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(1, "Tạm Biệt Tôi Của Nhiều Năm Về Trước", "https://product.hstatic.net/200000642007/product/50whs_3atst0153_2_9b6e53b833ee483cb9281843d361a1ed_e2eb29fcebe04276afd08adaf11d4dfd_grande.jpg", 1, 71000));
        orderItems.add(new OrderItem(2, "Nếu Như Tôi Nói Nhớ, Em Có Tin Không", "https://product.hstatic.net/200000642007/product/50whs_3atst0153_2_9b6e53b833ee483cb9281843d361a1ed_e2eb29fcebe04276afd08adaf11d4dfd_grande.jpg", 2, 148600));
        orderItems.add(new OrderItem(3, "Combo 3 Bông Tẩy Trang Jomi", "https://product.hstatic.net/200000642007/product/50whs_3atst0153_2_9b6e53b833ee483cb9281843d361a1ed_e2eb29fcebe04276afd08adaf11d4dfd_grande.jpg", 3, 260231));

        adapter = new OrderHistoryAdapter(requireContext(), orderItems);
        binding.recyclerViewOrders.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
