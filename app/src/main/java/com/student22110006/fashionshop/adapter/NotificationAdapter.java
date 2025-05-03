package com.student22110006.fashionshop.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.student22110006.fashionshop.data.model.notification.Notification;
import com.student22110006.fashionshop.databinding.ItemNotificationBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notification> notificationList;
    private final OnNotificationClickListener listener;

    public interface OnNotificationClickListener {
        void onNotificationClick(Notification notification);
    }

    public NotificationAdapter(List<Notification> notificationList, OnNotificationClickListener listener) {
        this.notificationList = notificationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
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

        holder.itemView.setOnClickListener(v -> listener.onNotificationClick(notification));
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

