package com.student22110006.fashionshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.data.model.order.OrderItem;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.ItemCheckoutProductBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.CheckoutProductViewHolder> {

    private List<OrderItem> orderItemList = new ArrayList<>();
    private Context context;
    private final Set<Integer> selectedProductIds = new HashSet<>();

    public interface OnSelectionChangeListener {
        void onSelectionChanged(boolean allSelected);
    }

    private OnSelectionChangeListener selectionChangeListener;

    public void setOnSelectionChangeListener(OnSelectionChangeListener listener) {
        this.selectionChangeListener = listener;
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    private OnQuantityChangeListener quantityChangeListener;

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    public CheckoutProductAdapter(Context context, List<OrderItem> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public CheckoutProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCheckoutProductBinding binding = ItemCheckoutProductBinding.inflate(inflater, parent, false);
        return new CheckoutProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutProductViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);

        holder.binding.tvProductTitle.setText(orderItem.getName());
        holder.binding.tvQuantity.setText(String.valueOf(orderItem.getAmount()));

        double price = orderItem.getPrice();
        double discount = orderItem.getDiscount();
        double originalPrice = price / (1 - discount);

        holder.binding.tvPrice.setText(String.format("$%.2f", price));
        holder.binding.tvDiscount.setText(String.format("$%.2f", originalPrice));

        Glide.with(context)
                .load(orderItem.getImageUrl())
                .into(holder.binding.imgProduct);
    }


    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectAllItems(boolean isSelected) {
        selectedProductIds.clear(); // Xoá trước nếu chọn lại

        if (isSelected) {
            for (OrderItem p : orderItemList) {
                selectedProductIds.add(p.getProductId());
            }
        }

        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<OrderItem> newList) {
        this.orderItemList = newList;
        // selectedProductIds.clear();
        notifyDataSetChanged();
    }

    public class CheckoutProductViewHolder extends RecyclerView.ViewHolder {
        private final ItemCheckoutProductBinding binding;

        public CheckoutProductViewHolder(ItemCheckoutProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.tvProductTitle.setText(product.getName());
            binding.tvVariation.setText("Variations: " + product.getSize());

            double price = product.getPrice();
            double discount = product.getDiscount();
            double originalPrice = price / (1 - discount);

            binding.tvPrice.setText(String.format("$%.2f", price));
            binding.tvDiscount.setText(String.format("~$%.2f~", originalPrice));

            Glide.with(context)
                    .load(product.getImageUrl())
                    .into(binding.imgProduct);
        }
    }
}
