package com.chartered4.add_listing;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chartered4.MainActivity;
import com.chartered4.R;
import com.chartered4.databinding.FragmentBasicInfoBinding;
import com.chartered4.utils.AppDialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DELL on 14-Oct-18.
 */

public class BasicInformationFragment extends Fragment implements View.OnClickListener {

    FragmentBasicInfoBinding binding;

    List<String> arrListingType = new ArrayList<>();
    List<String> arrOperatorIncluded = new ArrayList<>();

    public BasicInformationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBasicInfoBinding.inflate(inflater, container, false);

        setListeners();

        setListingType();

        setOperatorIncluded();

        return binding.getRoot();
    }

    private void setListingType() {
        arrListingType = Arrays.asList(getResources().getStringArray(R.array.arrListingType));

        ArrayAdapter<String> adapterListingType =
                new ArrayAdapter<>(getActivity(), R.layout.itemview_spinner, arrListingType);

        binding.actListingType.setAdapter(adapterListingType);
    }

    private void setOperatorIncluded() {
        arrOperatorIncluded = Arrays.asList(getResources().getStringArray(R.array.arrOperatorIncluded));

        ArrayAdapter<String> adapterListingType =
                new ArrayAdapter<>(getActivity(), R.layout.itemview_spinner, arrOperatorIncluded);

        binding.actOperatorIncluded.setAdapter(adapterListingType);
    }

    private void setListeners() {
        binding.btnContinue.setOnClickListener(this);
        binding.imgAdd.setOnClickListener(this);
        binding.imgMinus.setOnClickListener(this);

        binding.edtListingTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputLayoutListingTitle.setErrorEnabled(false);
                binding.inputLayoutListingTitle.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edtCapacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputLayoutCapacity.setErrorEnabled(false);
                binding.inputLayoutCapacity.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.edtLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.inputLayoutLocation.setErrorEnabled(false);
                binding.inputLayoutLocation.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btnContinue) {
            validateData();
        }
        if (viewId == R.id.imgAdd){
            int capacity = 0;
            if (!TextUtils.isEmpty(binding.edtCapacity.getText().toString().trim())){
                capacity = Integer.parseInt(binding.edtCapacity.getText().toString().trim());
            }
            capacity++;
            binding.edtCapacity.setText(String.valueOf(capacity));
        }
        if (viewId == R.id.imgMinus){
            int capacity = 0;
            if (!TextUtils.isEmpty(binding.edtCapacity.getText().toString().trim())){
                capacity = Integer.parseInt(binding.edtCapacity.getText().toString().trim());
            }
            if (capacity > 0){
                capacity--;
            }
            binding.edtCapacity.setText(String.valueOf(capacity));
        }
    }

    public void validateData(){
        if (!validateListingTitle()) {
            return;
        }
        if (!validateListingType()) {
            return;
        }
        if (!validateLocation()) {
            return;
        }
        if (!validateCapacity()) {
            return;
        }
        if (!validateOperatorIncluded()) {
            return;
        }
        ((AddListingActivity) getActivity()).changeNextPage();
    }

    private boolean validateListingTitle() {
        if (TextUtils.isEmpty(binding.edtListingTitle.getText())) {
            binding.inputLayoutListingTitle.setError(getResources().getString(R.string.errListingTitle));
            binding.inputLayoutListingTitle.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(binding.edtListingTitle.getText().toString().trim())) {
            binding.inputLayoutListingTitle.setError(getResources().getString(R.string.errListingTitle));
            binding.inputLayoutListingTitle.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validateLocation() {
        if (TextUtils.isEmpty(binding.edtLocation.getText())) {
            binding.inputLayoutLocation.setError(getResources().getString(R.string.errLocationOfVessel));
            binding.inputLayoutLocation.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(binding.edtLocation.getText().toString().trim())) {
            binding.inputLayoutLocation.setError(getResources().getString(R.string.errLocationOfVessel));
            binding.inputLayoutLocation.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validateCapacity() {
        if (TextUtils.isEmpty(binding.edtCapacity.getText())) {
            AppDialogs.showSnackBarAutoHide(getResources().getString(R.string.errCapacity), binding.edtCapacity);

//            binding.inputLayoutCapacity.setError(getResources().getString(R.string.errCapacity));
//            binding.inputLayoutCapacity.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(binding.edtCapacity.getText().toString().trim())) {
            AppDialogs.showSnackBarAutoHide(getResources().getString(R.string.errCapacity), binding.edtCapacity);

//            binding.inputLayoutCapacity.setError(getResources().getString(R.string.errCapacity));
//            binding.inputLayoutCapacity.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validateListingType() {
        if (TextUtils.isEmpty(binding.actListingType.getText())) {
            AppDialogs.showSnackBarAutoHide(getResources().getString(R.string.errListingType), binding.actListingType);
//            binding.inputLayoutCapacity.setError(getResources().getString(R.string.errCapacity));
//            binding.inputLayoutCapacity.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(binding.actListingType.getText().toString().trim())) {
            AppDialogs.showSnackBarAutoHide(getResources().getString(R.string.errListingType), binding.actListingType);
//            binding.inputLayoutCapacity.setError(getResources().getString(R.string.errCapacity));
//            binding.inputLayoutCapacity.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validateOperatorIncluded() {
        if (TextUtils.isEmpty(binding.actOperatorIncluded.getText())) {
            AppDialogs.showSnackBarAutoHide(getResources().getString(R.string.errOperatorIncluded), binding.actOperatorIncluded);
//            binding.inputLayoutCapacity.setError(getResources().getString(R.string.errCapacity));
//            binding.inputLayoutCapacity.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(binding.actOperatorIncluded.getText().toString().trim())) {
            AppDialogs.showSnackBarAutoHide(getResources().getString(R.string.errOperatorIncluded), binding.actOperatorIncluded);
//            binding.inputLayoutCapacity.setError(getResources().getString(R.string.errCapacity));
//            binding.inputLayoutCapacity.setErrorEnabled(true);
            return false;
        }
        return true;
    }
}
