package com.student22110006.fashionshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student22110006.fashionshop.data.model.order.OrderItem;
import com.student22110006.fashionshop.databinding.ItemRowOrderHistoryBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private final List<OrderItem> orderList;
    private final Context context;


    public OrderHistoryAdapter(Context context, List<OrderItem> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ItemRowOrderHistoryBinding binding;

        public OrderViewHolder(@NonNull ItemRowOrderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRowOrderHistoryBinding binding = ItemRowOrderHistoryBinding.inflate(inflater, parent, false);
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderItem item = orderList.get(position);
        holder.binding.tvProductName.setText(item.getName());
        holder.binding.tvQuantity.setText("Số lượng: " + item.getAmount());

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = formatter.format(item.getPrice()) + " đ";
        holder.binding.tvPrice.setText(formattedPrice);

        Glide.with(context).load(item.getImageUrl()).into(holder.binding.imgProduct);

        holder.binding.btnViewDetails.setOnClickListener(v -> {
            // Xử lý xem chi tiết
        });

        holder.binding.btnBuyAgain.setOnClickListener(v -> {
            // Xử lý mua lại
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
