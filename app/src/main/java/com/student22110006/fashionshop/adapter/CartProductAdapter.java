package com.student22110006.fashionshop.adapter;

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

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartViewHolder> {

    private List<Product> productList;
    private Context context;

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
    }

    @Override
    public int getItemCount() {
        return productList.size();
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
            binding.tvRating.setText("4.8 ★★★★★");

            double price = product.getPrice();
            double discount = product.getDiscount();
            double originalPrice = price / (1 - discount);

            binding.tvPrice.setText(String.format("$%.2f", price));
            binding.tvDiscount.setText(String.format("~$%.2f~", originalPrice));
            binding.tvOffer.setText(String.format("upto %d%% off", (int) (discount * 100)));

            Glide.with(context)
                    .load(product.getImageUrl())
                    .into(binding.imgProduct);
        }
    }
}
