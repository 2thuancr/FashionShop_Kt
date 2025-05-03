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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.NotificationAdapter;
import com.student22110006.fashionshop.data.model.notification.Notification;
import com.student22110006.fashionshop.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private List<Notification> notificationList;  // Sử dụng lớp MyNotification thay vì Notification
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
        adapter = new NotificationAdapter(notificationList, this::showNotificationDetails);
        RecyclerView recyclerView = binding.recyclerViewNotifications;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        Log.d("NotificationsFragment", "Adapter set to RecyclerView.");
    }

    private void showNotificationDetails(Notification notification) {
        // Lấy NavController từ NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Điều hướng sang notification_detail fragment
        navController.navigate(R.id.navigation_notification_detail);
    }

    private void loadDummyData() {
        notificationList.add(new Notification("Mừng tháng sinh nhật 🎁", "💥 Nhận voucher giảm 20%", "1 ngày trước", R.drawable.card_giftcard, true));
        notificationList.add(new Notification("ƯU ĐÃI TỪ PHILIPS", "✨ Dành riêng cho Maisonista", "25/04/2025", R.drawable.loyalty, false));
        Log.d("NotificationsFragment", "Dummy data loaded: " + notificationList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
