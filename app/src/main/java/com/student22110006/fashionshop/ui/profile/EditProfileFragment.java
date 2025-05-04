package com.student22110006.fashionshop.ui.profile;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.databinding.FragmentEditProfileBinding;

import java.util.Calendar;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel mViewModel;
    private FragmentEditProfileBinding binding;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        // Nếu có nút back thì xử lý nó ở đây
        binding.imgBack.setOnClickListener(v -> requireActivity().onBackPressed());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // tránh memory leak
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        binding.editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày hiện tại
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Tạo DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), // đổi tên theo Activity của bạn
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                // Gán ngày được chọn vào EditText
                                String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                                binding.editDob.setText(date);
                            }
                        },
                        year, month, day
                );

                datePickerDialog.show();
            }
        });
        // Gán danh sách ngôn ngữ cho AutoCompleteTextView
        String[] languages = getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.dropdown_item,  // Layout mới cho mỗi item
                R.id.text1,               // TextView trong layout
                languages
        );
        binding.autoLanguage.setAdapter(adapter);
        // Mở dropdown khi click
        binding.autoLanguage.setOnClickListener(v -> binding.autoLanguage.showDropDown());
    }
}
