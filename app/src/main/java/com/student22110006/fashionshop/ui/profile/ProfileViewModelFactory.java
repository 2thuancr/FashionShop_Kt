package com.student22110006.fashionshop.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.student22110006.fashionshop.data.repository.CustomerRepository;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    private final CustomerRepository repository;

    public ProfileViewModelFactory(CustomerRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(repository); // lỗi sẽ xảy ra nếu Kotlin không công khai constructor này
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
