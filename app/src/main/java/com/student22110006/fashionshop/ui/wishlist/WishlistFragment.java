package com.student22110006.fashionshop.ui.wishlist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.databinding.FragmentWishlistBinding;
import com.student22110006.fashionshop.ui.notifications.NotificationsViewModel;

public class WishlistFragment extends Fragment {

    private FragmentWishlistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        WishlistViewModel wishlistViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);

        binding = FragmentWishlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textWishlist;
        wishlistViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}