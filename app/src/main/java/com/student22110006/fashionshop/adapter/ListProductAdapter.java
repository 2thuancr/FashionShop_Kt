package com.student22110006.fashionshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.ui.product.ProductDetailActivity;

import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ProductViewHolder> {
    private static final String TAG = "ListProductAdapter";
    private List<Product> productList;
    private Context context;
    private LayoutInflater inflater;

    public ListProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Lấy ra item product tại vị trí position
        Product product = productList.get(position);

        // Set giá trị cho các thành phần giao diện
        holder.textProductName.setText(product.getName());
        // holder.textProductDescription.setText(product.getDescription());
        holder.textProductPrice.setText(product.getPrice() + "đ");

        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .into(holder.imageProduct);

        // Hiển thị badge giảm giá nếu có
        if (product.getDiscount() > 0) {
            holder.textSaleBadge.setVisibility(View.VISIBLE);
            holder.textSaleBadge.setText("-" + product.getDiscount() + "%");
        } else {
            holder.textSaleBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageProduct;
        private TextView textProductName;
        // private TextView textProductDescription;
        private TextView textProductPrice;
        private TextView textSaleBadge;
        private Button buttonAddToCart;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageProduct = (ImageView) itemView.findViewById(R.id.imageProduct);
            textProductName = (TextView) itemView.findViewById(R.id.textProductName);
            // textProductDescription = (TextView) itemView.findViewById(R.id.textProductDescription);
            textProductPrice = (TextView) itemView.findViewById(R.id.textProductPrice);
            textSaleBadge = (TextView) itemView.findViewById(R.id.textSaleBadge);
            buttonAddToCart = (Button) itemView.findViewById(R.id.buttonAddToCart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Lấy ra item product tại vị trí position
                    Product product = productList.get(getAdapterPosition());

                    // Chuyển sang màn hình Product Detail
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("productId", product.getId());
                    context.startActivity(intent);
                }
            });

            buttonAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Lấy ra item product tại vị trí position
                    Product product = productList.get(getAdapterPosition());
                    // Hiển thị thông báo sản phẩm được chọn
                    Toast.makeText(context, product.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
