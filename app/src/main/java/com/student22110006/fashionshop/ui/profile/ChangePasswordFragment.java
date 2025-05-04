package com.student22110006.fashionshop.ui.profile;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.databinding.FragmentChangePasswordBinding;

public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel mViewModel;
    private FragmentChangePasswordBinding binding;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);

        // Xử lý sự kiện bấm nút Back
        binding.imgBack.setOnClickListener(v -> requireActivity().onBackPressed());
// Xử lý sự kiện nhấn nút Save
        binding.layoutSave.setOnClickListener(v -> {
            String oldPass = binding.editOldPassword.getText().toString().trim();
            String newPass = binding.editNewPassword.getText().toString().trim();
            String confirmPass = binding.editConfirmPassword.getText().toString().trim();

            // 1. Kiểm tra rỗng
            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(getContext(), "Xin vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // 2. Kiểm tra mật khẩu mới và xác nhận
            if (!newPass.equals(confirmPass)) {
                Toast.makeText(getContext(), "Mật khẩu mới và xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Xử lý tiếp như gọi ViewModel để đổi mật khẩu
            Toast.makeText(getContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // tránh memory leak
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        // TODO: Use the ViewModel
    }

}
