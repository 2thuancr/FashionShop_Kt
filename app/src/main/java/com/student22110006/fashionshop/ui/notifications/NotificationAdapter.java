package com.student22110006.fashionshop.ui.notifications;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student22110006.fashionshop.databinding.ItemNotificationBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<MyNotification> notificationList;

    public NotificationAdapter(List<MyNotification> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng ItemNotificationBinding thay vì NotificationItemBinding
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        MyNotification notification = notificationList.get(position);
        Log.d("NotificationAdapter", "Binding: " + notification.getTitle() + " at position " + position);
        holder.binding.title.setText(notification.getTitle());
        holder.binding.message.setText(notification.getMessage());
        holder.binding.time.setText(notification.getDate());
        holder.binding.icon.setImageResource(notification.getIconResId());

        // Hiển thị chấm đỏ nếu thông báo mới
        if (notification.isNew()) {
            holder.binding.dot.setVisibility(View.VISIBLE);
        } else {
            holder.binding.dot.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    // ViewHolder
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;

        public NotificationViewHolder(@NonNull ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

