package com.student22110006.fashionshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student22110006.fashionshop.databinding.ItemImageSliderBinding;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {

    private final List<String> imageUrls;

    public ImageSliderAdapter(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageSliderBinding binding = ItemImageSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);

        // Load ảnh từ internet bằng Glide
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ItemImageSliderBinding binding;

        public ImageViewHolder(ItemImageSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int imageResId) {
            binding.imageView.setImageResource(imageResId);
        }
    }
}