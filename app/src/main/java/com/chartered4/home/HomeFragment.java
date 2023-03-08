package com.chartered4.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chartered4.MainActivity;
import com.chartered4.R;
import com.chartered4.add_listing.AddListingActivity;
import com.chartered4.databinding.FragmentHomeBinding;
import com.chartered4.utils.SharedObjects;

/**
 * Created by DELL on 14-Oct-18.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    FragmentHomeBinding binding;
    SharedObjects sharedObjects;

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

        return binding.getRoot();
    }

    private void setListeners() {
        binding.cardNotification.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.cardNotification){
            ((MainActivity) getActivity()).removeAllPreferenceOnLogout();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).selectMenuItem(getActivity().getString(R.string.home));
    }

}
