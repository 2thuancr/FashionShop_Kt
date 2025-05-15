package com.student22110006.fashionshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.ItemProductBinding;
import com.student22110006.fashionshop.utils.CartManager;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ProductViewHolder> {
    private static final String TAG = "ListProductAdapter";
    private int maxWidthLayout = 0;
    private List<Product> productList;
    private Context context;
    private LayoutInflater inflater;

    private final ListProductAdapter.OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ListProductAdapter(Context context, List<Product> productList, ListProductAdapter.OnProductClickListener listener, int maxWidthLayout) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        this.maxWidthLayout = maxWidthLayout;
    }

    public ListProductAdapter(Context context, List<Product> productList, ListProductAdapter.OnProductClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        this.maxWidthLayout = 0;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        if (this.maxWidthLayout > 0) {
            // Set chiều rộng cho layout của item
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.width = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, this.maxWidthLayout, holder.itemView.getContext().getResources().getDisplayMetrics());
            holder.itemView.setLayoutParams(params);
        }

        // Lấy ra item product tại vị trí position
        Product product = productList.get(position);

        // Set giá trị cho các thành phần giao diện
        holder.binding.textProductName.setText(product.getName());
        // holder.binding.textProductDescription.setText(product.getDescription());
        double price = product.getPrice();

        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedPrice = formatter.format(price) + " đ";
        holder.binding.textProductPrice.setText(formattedPrice);

        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .into(holder.binding.imageProduct);

        // Hiển thị badge giảm giá nếu có
        if (product.getDiscount() > 0) {
            holder.binding.textSaleBadge.setVisibility(View.VISIBLE);
            holder.binding.textSaleBadge.setText("-" + product.getDiscount() + "%");
        } else {
            holder.binding.textSaleBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateProducts(List<Product> newProducts) {
        this.productList.clear();
        this.productList.addAll(newProducts);
        notifyDataSetChanged();
    }

    public void appendProducts(List<Product> newProducts) {
        int startPosition = productList.size();
        productList.addAll(newProducts);
        notifyItemRangeInserted(startPosition, newProducts.size());
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemProductBinding binding;

        public ProductViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(view -> {
                // Lấy ra item product tại vị trí position
                Product product = productList.get(getAdapterPosition());
                listener.onProductClick(product);
            });

            binding.buttonAddToCart.setOnClickListener(view -> {
                // Lấy ra item product tại vị trí position
                Product product = productList.get(getAdapterPosition());

                // Thêm sản phẩm vào giỏ hàng
                CartManager.getInstance().addProduct(product);

                // Hiển thị thông báo sản phẩm được chọn
                Toast.makeText(context, product.getName() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
