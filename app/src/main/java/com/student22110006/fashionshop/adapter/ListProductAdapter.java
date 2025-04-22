package com.student22110006.fashionshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.data.model.product.Product;

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
        holder.textProductPrice.setText(product.getPrice() + "đ");

        Glide.with(holder.itemView.getContext())
                .load(product.getImageUrl())
                .into(holder.imageProduct);


        // TODO thêm sự kiện button Click

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageProduct;
        private TextView textProductName;
        private TextView textProductPrice;
        private Button buttonAddToCart;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageProduct = (ImageView) itemView.findViewById(R.id.imageProduct);
            textProductName = (TextView) itemView.findViewById(R.id.textProductName);
            textProductPrice = (TextView) itemView.findViewById(R.id.textProductPrice);
            buttonAddToCart = (Button) itemView.findViewById(R.id.buttonAddToCart);

            itemView.setOnClickListener(new View.OnClickListener() {
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
