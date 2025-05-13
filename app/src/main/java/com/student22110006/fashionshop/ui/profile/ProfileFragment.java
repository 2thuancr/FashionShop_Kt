package com.student22110006.fashionshop.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.data.repository.CustomerRepository;
import com.student22110006.fashionshop.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE = 100;
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    CustomerRepository repository = new CustomerRepository(); // hoặc inject
    ProfileViewModelFactory factory = new ProfileViewModelFactory(repository);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        // Lấy NavController từ NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Xử lý sự kiện bấm nút Edit Profile
        binding.btnEditProfile.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_edit_profile);
        });

        // Xử lý sự kiện bấm nút Change Password
        binding.btnChangePassword.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_change_password);
        });

        // Xử lý click vào avatar hoặc icon cây bút
        binding.avatar.setOnClickListener(v -> openImagePicker());
        binding.imgEditAvatar.setOnClickListener(v -> openImagePicker());

        // 👉 Thêm xử lý click vào LinearLayout để chuyển sang fragment đơn hàng
        binding.orderHistory.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_order_history);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Gọi API với thông tin người dùng (email, userName hoặc phoneNumber) từ SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("FashionShop", Activity.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        String userName = sharedPreferences.getString("userName", null);
        String phoneNumber = sharedPreferences.getString("phoneNumber", null);

        profileViewModel.fetchCustomerInfo(email, userName, phoneNumber);

        observeViewModel();
    }

    private void observeViewModel() {
        profileViewModel.getCustomerInfo().observe(getViewLifecycleOwner(), newInfo -> {
            if (newInfo != null) {
                binding.profileName.setText(newInfo.getCustomerName());
                binding.tvEmail.setText(newInfo.getEmail());
                binding.tvPhoneNumber.setText(newInfo.getPhoneNumber());
                binding.tvAddress.setText(newInfo.getAddress());
                binding.tvDoB.setText(newInfo.getDob());
            }
        });

        profileViewModel.getError().observe(getViewLifecycleOwner(), errorMsg ->
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show());
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            getActivity();
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    binding.avatar.setImageURI(selectedImageUri);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
