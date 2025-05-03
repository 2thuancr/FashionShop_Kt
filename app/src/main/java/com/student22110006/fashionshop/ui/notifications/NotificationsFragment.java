package com.student22110006.fashionshop.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private List<MyNotification> notificationList;  // Sử dụng lớp MyNotification thay vì Notification
    private NotificationAdapter adapter;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        initRecyclerView();
        loadDummyData();
        return root;
    }

    private void initRecyclerView() {
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        RecyclerView recyclerView = binding.recyclerViewNotifications;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        Log.d("NotificationsFragment", "Adapter set to RecyclerView.");
    }

    private void loadDummyData() {
        notificationList.add(new MyNotification("Mừng tháng sinh nhật 🎁", "💥 Nhận voucher giảm 20%", "1 ngày trước", R.drawable.ic_gift, true));
        notificationList.add(new MyNotification("ƯU ĐÃI TỪ PHILIPS", "✨ Dành riêng cho Maisonista", "25/04/2025", R.drawable.ic_speaker, false));
        Log.d("NotificationsFragment", "Dummy data loaded: " + notificationList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
