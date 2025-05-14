package com.student22110006.fashionshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.data.model.order.OrderItem;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.ItemCartProductBinding;
import com.student22110006.fashionshop.utils.CartManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartViewHolder> {

    private final CartManager cartManager;
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

    public CartProductAdapter(Context context, List<OrderItem> orderItemList, CartManager cartManager) {
        this.context = context;
        this.orderItemList = orderItemList;
        this.cartManager = cartManager;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCartProductBinding binding = ItemCartProductBinding.inflate(inflater, parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        OrderItem item = orderItemList.get(position);
        holder.bind(item);

        // Cập nhật checkbox theo trạng thái chọn
        boolean isChecked = selectedProductIds.contains(item.getProductId());
        holder.binding.checkboxSelect.setChecked(isChecked);

        // Cập nhật quantity
        holder.binding.tvQuantity.setText(String.valueOf(item.getAmount()));

        holder.binding.btnIncrease.setOnClickListener(v -> {
            cartManager.updateProductAmount(item.getProductId(), item.getAmount() + 1);
            notifyItemChanged(position);

            if (selectionChangeListener != null)
                selectionChangeListener.onSelectionChanged(checkAllSelected());

            if (quantityChangeListener != null)
                quantityChangeListener.onQuantityChanged();
        });

        holder.binding.btnDecrease.setOnClickListener(v -> {
            // Kiểm tra nếu số lượng sản phẩm > 1
            if (item.getAmount() > 1) {
                // Cập nhật số lượng sản phẩm
                cartManager.updateProductAmount(item.getProductId(), item.getAmount() - 1);

                // Cập nhật giao diện
                notifyItemChanged(position);

                // Gọi listener nếu có thay đổi về selection
                if (selectionChangeListener != null) {
                    selectionChangeListener.onSelectionChanged(checkAllSelected());
                }

                // Gọi listener nếu có thay đổi về quantity
                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged();
                }
            } else {
                // Khi số lượng giảm xuống 1, yêu cầu người dùng xác nhận xóa sản phẩm
                showDeleteConfirmationDialog(item, position);
            }
        });
    }

    private void showDeleteConfirmationDialog(OrderItem item, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Xóa sản phẩm khỏi giỏ hàng
                    orderItemList.remove(position);
                    notifyItemRemoved(position);

                    // Lưu lại thay đổi vào SharedPreferences
                    CartManager.getInstance().saveCartToPrefs();

                    // Cập nhật lại UI
                    if (selectionChangeListener != null) {
                        selectionChangeListener.onSelectionChanged(checkAllSelected());
                    }
                    if (quantityChangeListener != null) {
                        quantityChangeListener.onQuantityChanged();
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private boolean checkAllSelected() {
        return selectedProductIds.size() == orderItemList.size() && !orderItemList.isEmpty();
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public List<OrderItem> getSelectedItems() {
        List<OrderItem> selected = new ArrayList<>();
        for (OrderItem item : orderItemList) {
            if (selectedProductIds.contains(item.getProductId())) {
                selected.add(item);
            }
        }
        return selected;
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
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private final ItemCartProductBinding binding;

        public CartViewHolder(ItemCartProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrderItem item) {
            binding.tvProductTitle.setText(item.getName());

            double price = item.getPrice();
            double discount = item.getDiscount();
            double originalPrice = price / (1 - discount);

            binding.tvPrice.setText(String.format("$%.2f", price));
            binding.tvDiscount.setText(String.format("~$%.2f~", originalPrice));
            binding.tvQuantity.setText(String.valueOf(item.getAmount()));

            Glide.with(context)
                    .load(item.getImageUrl())
                    .into(binding.imgProduct);

            binding.checkboxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) selectedProductIds.add(item.getProductId());
                else selectedProductIds.remove(item.getProductId());
                if (selectionChangeListener != null) {
                    selectionChangeListener.onSelectionChanged(checkAllSelected());
                }
                if (quantityChangeListener != null) quantityChangeListener.onQuantityChanged();
            });
        }
    }
}
