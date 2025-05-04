package com.student22110006.fashionshop.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        // Lấy NavController từ NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Xử lý sự kiện bấm nút Edit Profile
        binding.btnEditProfile.setOnClickListener(v -> {
            // Điều hướng sang EditProfileFragment
            navController.navigate(R.id.navigation_edit_profile);
        });
        // Xử lý sự kiện bấm nút Change Password
        binding.btnChangePassword.setOnClickListener(v -> {
            // Điều hướng sang ChangePasswordFragment
            navController.navigate(R.id.navigation_change_password);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
