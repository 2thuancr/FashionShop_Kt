package com.student22110006.fashionshop.ui.notifications;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.student22110006.fashionshop.R;

public class NotificationDetailFragment extends Fragment {

    private NotificationDetailViewModel mViewModel;

    public static NotificationDetailFragment newInstance() {
        return new NotificationDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NotificationDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}