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
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.ItemCartProductBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartViewHolder> {

    private List<Product> productList = new ArrayList<>();
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

    public CartProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
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
        Product product = productList.get(position);
        holder.bind(product);

        // Cập nhật checkbox theo trạng thái chọn
        boolean isChecked = selectedProductIds.contains(product.getId());
        holder.binding.checkboxSelect.setChecked(isChecked);

        // Cập nhật quantity
        holder.binding.tvQuantity.setText(String.valueOf(product.getQuantity()));

        holder.binding.btnIncrease.setOnClickListener(v -> {
            int qty = product.getQuantity(); // Giả sử model có getQuantity()
            product.setQuantity(qty + 1);
            notifyItemChanged(position);

            if (selectionChangeListener != null)
                selectionChangeListener.onSelectionChanged(checkAllSelected());

            if (quantityChangeListener != null) quantityChangeListener.onQuantityChanged();
        });

        holder.binding.btnDecrease.setOnClickListener(v -> {
            int qty = product.getQuantity();
            if (qty > 1) {
                product.setQuantity(qty - 1);
                notifyItemChanged(position);

                if (selectionChangeListener != null)
                    selectionChangeListener.onSelectionChanged(checkAllSelected());

                if (quantityChangeListener != null) quantityChangeListener.onQuantityChanged();
            }
        });

    }

    private boolean checkAllSelected() {
        return selectedProductIds.size() == productList.size() && !productList.isEmpty();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public List<Product> getSelectedProducts() {
        List<Product> selected = new ArrayList<>();
        for (Product p : productList) {
            if (selectedProductIds.contains(p.getId())) {
                selected.add(p);
            }
        }
        return selected;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectAllItems(boolean isSelected) {
        selectedProductIds.clear(); // Xoá trước nếu chọn lại

        if (isSelected) {
            for (Product p : productList) {
                selectedProductIds.add(p.getId());
            }
        }

        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Product> newList) {
        this.productList = newList;
        // selectedProductIds.clear();
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private final ItemCartProductBinding binding;

        public CartViewHolder(ItemCartProductBinding binding) {
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

            binding.checkboxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedProductIds.add(product.getId());
                } else {
                    selectedProductIds.remove(product.getId());
                }

                if (selectionChangeListener != null) {
                    selectionChangeListener.onSelectionChanged(checkAllSelected());
                }

                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged();
                }
            });
        }
    }
}
