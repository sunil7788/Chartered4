package com.chartered4.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chartered4.MainActivity;
import com.chartered4.R;
import com.chartered4.adjust_offer.AdjustOfferActivity;
import com.chartered4.databinding.FragmentHomeBinding;
import com.chartered4.models.InquiriesBean;
import com.chartered4.utils.SharedObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 14-Oct-18.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    FragmentHomeBinding binding;
    SharedObjects sharedObjects;

    ArrayList<InquiriesBean> arrInquiries;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) { // fragment is visible and have created
//            ((MainActivity) getActivity()).selectMenuItem(getActivity().getString(R.string.home));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getUserVisibleHint()) { // fragment is visible
//            ((MainActivity) getActivity()).selectMenuItem(getActivity().getString(R.string.booking_history));
        }
        setListeners();

        sharedObjects = new SharedObjects(getActivity());
        if (sharedObjects.getUserInfo() != null
                && !TextUtils.isEmpty(sharedObjects.getUserInfo().getFirstName())
                && !TextUtils.isEmpty(sharedObjects.getUserInfo().getLastName())
        ){
            binding.txtUserName.setText(sharedObjects.getUserInfo().getFirstName() + " " + sharedObjects.getUserInfo().getLastName());
        }

        arrInquiries = new ArrayList<>() ;
        arrInquiries.add(new InquiriesBean("Test"));
        arrInquiries.add(new InquiriesBean("Test"));
        arrInquiries.add(new InquiriesBean("Test"));
        arrInquiries.add(new InquiriesBean("Test"));
        arrInquiries.add(new InquiriesBean("Test"));

        setInquiriesAdapter();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.cardLogout.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
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

    private void setInquiriesAdapter() {
        if (!arrInquiries.isEmpty()){

            HomeInquiriesAdapter homeInquiriesAdapter = new HomeInquiriesAdapter(arrInquiries);
            binding.rvInquiries.setAdapter(homeInquiriesAdapter);
            homeInquiriesAdapter.setOnItemClickListener(new HomeInquiriesAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int position, InquiriesBean bean) {

                }

                @Override
                public void onItemRejectClickListener(int position, InquiriesBean bean) {

                }

                @Override
                public void onItemApproveClickListener(int position, InquiriesBean bean) {

                }

                @Override
                public void onItemAdjustClickListener(int position, InquiriesBean bean) {
                    startActivity(new Intent(getActivity(), AdjustOfferActivity.class));
                }

                @Override
                public void onItemChatClickListener(int position, InquiriesBean bean) {

                }
            });

            binding.rvInquiries.setVisibility(View.VISIBLE);
//            txtError.setVisibility(View.GONE);
        }else{
            binding.rvInquiries.setVisibility(View.GONE);
//            txtError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.cardLogout){
            ((MainActivity) getActivity()).removeAllPreferenceOnLogout();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).selectMenuItem(getActivity().getString(R.string.home));
    }

}
