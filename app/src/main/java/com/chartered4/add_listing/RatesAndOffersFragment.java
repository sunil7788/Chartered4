package com.chartered4.add_listing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chartered4.MainActivity;
import com.chartered4.R;
import com.chartered4.databinding.FragmentAmenitiesAndEquipmentBinding;
import com.chartered4.databinding.FragmentRatesAndOffersBinding;

/**
 * Created by DELL on 14-Oct-18.
 */

public class RatesAndOffersFragment extends Fragment implements View.OnClickListener {

    FragmentRatesAndOffersBinding binding;

    public RatesAndOffersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRatesAndOffersBinding.inflate(inflater, container, false);
        setListeners();
        return binding.getRoot();
    }

    private void setListeners() {
        binding.btnPrevious.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnPrevious){
            ((AddListingActivity) getActivity()).changePreviousPage();
        }
        if (viewId == R.id.btnSubmit){
        }
    }

}
