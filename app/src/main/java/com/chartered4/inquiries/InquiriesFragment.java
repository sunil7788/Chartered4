package com.chartered4.inquiries;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chartered4.MainActivity;
import com.chartered4.R;
import com.chartered4.adjust_offer.AdjustOfferActivity;
import com.chartered4.databinding.FragmentAmenitiesAndEquipmentBinding;
import com.chartered4.databinding.FragmentInquiriesBinding;
import com.chartered4.home.HomeInquiriesAdapter;
import com.chartered4.models.InquiriesBean;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * Created by DELL on 14-Oct-18.
 */

public class InquiriesFragment extends Fragment {

    FragmentInquiriesBinding binding;
    ArrayList<InquiriesBean> arrInquiries;

    public InquiriesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) { // fragment is visible and have created
//            ((MainActivity) getActivity()).selectMenuItem(getActivity().getString(R.string.booking_history));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInquiriesBinding.inflate(inflater, container, false);

//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getUserVisibleHint()) { // fragment is visible
//            ((MainActivity) getActivity()).selectMenuItem(getActivity().getString(R.string.booking_history));
        }

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.newStr)));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.existing)));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (binding.tabLayout.getSelectedTabPosition() == 0){
//                    showSignInView();
                }else{
//                    showSignUpView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        arrInquiries = new ArrayList<>() ;
        arrInquiries.add(new InquiriesBean("Test"));
        arrInquiries.add(new InquiriesBean("Test"));
        arrInquiries.add(new InquiriesBean("Test"));
        arrInquiries.add(new InquiriesBean("Test"));
        arrInquiries.add(new InquiriesBean("Test"));

        setInquiriesAdapter();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        setHasOptionsMenu(true);
        /*toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle(getResources().getString(R.string.booking_history));
        toolbar.setSubtitle("");
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).selectMenuItem(getResources().getString(R.string.booking_history));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).selectMenuItem(getActivity().getString(R.string.inquiries));
    }

    private void setInquiriesAdapter() {
        if (!arrInquiries.isEmpty()){

            InquiriesAdapter inquiriesAdapter = new InquiriesAdapter(arrInquiries);
            binding.rvInquiries.setAdapter(inquiriesAdapter);
            inquiriesAdapter.setOnItemClickListener(new InquiriesAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int position, InquiriesBean bean) {
                    startActivity(new Intent(getActivity(), AdjustOfferActivity.class));
                }
            });

            binding.rvInquiries.setVisibility(View.VISIBLE);
//            txtError.setVisibility(View.GONE);
        }else{
            binding.rvInquiries.setVisibility(View.GONE);
//            txtError.setVisibility(View.VISIBLE);
        }

    }


}
