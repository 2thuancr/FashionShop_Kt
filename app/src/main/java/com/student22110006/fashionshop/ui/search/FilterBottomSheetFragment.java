package com.student22110006.fashionshop.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.databinding.FragmentFilterBottomSheetBinding;

import java.util.Arrays;
import java.util.List;

public class FilterBottomSheetFragment extends BottomSheetDialogFragment {

    private FilterBottomSheetViewModel viewModel;
    private FragmentFilterBottomSheetBinding binding;

    public interface OnFilterApplyListener {
        void onFilterApplied(String type, String size);
    }

    private OnFilterApplyListener listener;

    public void setOnFilterApplyListener(OnFilterApplyListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(FilterBottomSheetViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupSpinners();

        binding.seekBarMinPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Cập nhật giá trị max khi người dùng kéo seekbar
                binding.tvMinPrice.setText("Min Price: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.seekBarMaxPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Cập nhật giá trị max khi người dùng kéo seekbar
                binding.tvMaxPrice.setText("Max Price: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.btnApply.setOnClickListener(v -> {
            String type = binding.spinnerType.getSelectedItem().toString();
            String size = binding.spinnerSize.getSelectedItem().toString();
            int minPrice = binding.seekBarMinPrice.getProgress();
            int maxPrice = binding.seekBarMaxPrice.getProgress();

            // Gọi ViewModel
            viewModel.applyFilter(type, size, minPrice, maxPrice);

            // Gọi callback nếu có
            if (listener != null) {
                listener.onFilterApplied(type, size);
            }

            dismiss(); // Đóng BottomSheet
        });
    }


    private void setupSpinners() {
        List<String> typeList = Arrays.asList("Tất cả", "Áo", "Quần", "Váy", "Phụ kiện");
        List<String> sizeList = Arrays.asList("Tất cả", "S", "M", "L", "XL", "XXL");

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, typeList);
        typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinnerType.setAdapter(typeAdapter);

        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, sizeList);
        sizeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinnerSize.setAdapter(sizeAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
