package com.chartered4.add_listing;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chartered4.MainActivity;
import com.chartered4.R;
import com.chartered4.databinding.FragmentBasicInfoBinding;
import com.chartered4.models.AddListingBean;
import com.chartered4.models.ListingTypeBean;
import com.chartered4.utils.AppDialogs;
import com.chartered4.utils.AppUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DELL on 14-Oct-18.
 */

public class BasicInformationFragment extends Fragment implements View.OnClickListener {

    FragmentBasicInfoBinding binding;

    List<String> arrOperatorIncluded = new ArrayList<>();
    ArrayList<ListingTypeBean> arrListingType = new ArrayList<>();

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
        arrListingType = new ArrayList<>();
        arrListingType.add(new ListingTypeBean("P0001","Yacht/Motor", ContextCompat.getDrawable(getActivity(), R.drawable.ic_yacht), false));
        arrListingType.add(new ListingTypeBean("P0002","Jet-Skis", ContextCompat.getDrawable(getActivity(), R.drawable.ic_jetski), false));
        arrListingType.add(new ListingTypeBean("P0003","Kayaks/Paddles", ContextCompat.getDrawable(getActivity(), R.drawable.ic_kayak), false));
        arrListingType.add(new ListingTypeBean("P0004","Fishing", ContextCompat.getDrawable(getActivity(), R.drawable.ic_fishing), false));
        arrListingType.add(new ListingTypeBean("P0008","Sail", ContextCompat.getDrawable(getActivity(), R.drawable.ic_sail), false));
        arrListingType.add(new ListingTypeBean("P0005","Pontoons/House", ContextCompat.getDrawable(getActivity(), R.drawable.ic_pontoons), false));
        arrListingType.add(new ListingTypeBean("P0006","Lessons", ContextCompat.getDrawable(getActivity(), R.drawable.ic_lessons), false));

        /*<string-array name="arrListingType">
        <!--<item>Select Listing Type</item>-->
        <item>Yacht/Motor</item>
        <item></item>
        <item>Kayaks/Paddles</item>
        <item></item>
        <item>Sail</item>
        <item></item>
        <item>Lessons</item>
    </string-array>*/

        /*ArrayAdapter<String> adapterListingType =
                new ArrayAdapter<>(getActivity(), R.layout.itemview_spinner, arrListingType);

        binding.actListingType.setAdapter(adapterListingType);*/
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
        binding.edtListingType.setOnClickListener(this);

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
            AppUtils.hideKeyboard(binding.btnContinue, getActivity());
            validateData();
        }
        if (viewId == R.id.edtListingType) {
            showListingTypeDialog();
        }
        if (viewId == R.id.imgAdd){
            AppUtils.hideKeyboard(binding.imgAdd, getActivity());
            int capacity = 0;
            if (!TextUtils.isEmpty(binding.edtCapacity.getText().toString().trim())){
                capacity = Integer.parseInt(binding.edtCapacity.getText().toString().trim());
            }
            capacity++;
            binding.edtCapacity.setText(String.valueOf(capacity));
        }
        if (viewId == R.id.imgMinus){
            AppUtils.hideKeyboard(binding.imgMinus, getActivity());
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

        AddListingActivity.addListingBean.setBoatName(binding.edtListingTitle.getText().toString().trim());
        ArrayList<AddListingBean.Types> arrSelectedListingType = new ArrayList<>();
        for (int i = 0; i < arrListingType.size(); i++) {
            if (arrListingType.get(i).isChecked()){
                arrSelectedListingType.add(new AddListingBean.Types(arrListingType.get(i).getId()));
            }
        }
        AddListingActivity.addListingBean.setTypes(arrSelectedListingType);
        AddListingActivity.addListingBean.setLocation(binding.edtLocation.getText().toString().trim());
        AddListingActivity.addListingBean.setCapacity(binding.edtCapacity.getText().toString().trim());
        AddListingActivity.addListingBean.setCaptainIncluded(binding.actOperatorIncluded.getText().toString().trim());

        Log.e("Basic :", new Gson().toJson(AddListingActivity.addListingBean));

        ((AddListingActivity) getActivity()).changeNextPage();
    }

    public Dialog dialogListingType;
    ListingTypeAdapter listingTypeAdapter ;

    public void showListingTypeDialog() {
        dialogListingType = new Dialog(getActivity());
        dialogListingType.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogListingType.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogListingType.setContentView(R.layout.dialog_listing_type);
        dialogListingType.setCancelable(true);

        Window window = dialogListingType.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        wlp.dimAmount = 0.8f;
        window.setAttributes(wlp);
        dialogListingType.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        FloatingActionButton fabClose = dialogListingType.findViewById(R.id.fabClose);
        TextView txtTitle = dialogListingType.findViewById(R.id.txtTitle);
        txtTitle.setText(getResources().getString(R.string.listingType));
        RecyclerView rvListingType = dialogListingType.findViewById(R.id.rvListingType);

        listingTypeAdapter = new ListingTypeAdapter(arrListingType);
        rvListingType.setAdapter(listingTypeAdapter);
        listingTypeAdapter.setOnItemClickListener(new ListingTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, ListingTypeBean bean) {
                /*if (listingTypeAdapter.getSelectedCount() < 3){
                    if (bean.isChecked()){
                        arrListingType.get(position).setChecked(false);
                    }else{
                        arrListingType.get(position).setChecked(true);
                    }
                    listingTypeAdapter.notifyItemChanged(position);

                    binding.edtListingType.setText(listingTypeAdapter.getSelectedText());

                }else{
                    AppDialogs.showSnackBar(getActivity(), getString(R.string.listingTypeMessage), rvListingType);
                }*/

                if (bean.isChecked()){
                    arrListingType.get(position).setChecked(false);
                }else{
                    if (listingTypeAdapter.getSelectedCount() > 2){
                        AppDialogs.showSnackBar(getActivity(), getString(R.string.listingTypeMessage), rvListingType);
                        return;
                    }
                    arrListingType.get(position).setChecked(true);
                }
                listingTypeAdapter.notifyItemChanged(position);

                binding.edtListingType.setText(listingTypeAdapter.getSelectedText());

            }
        });

        /*productSearchAdapter.setOnItemClickListener(new ProductSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, ProductBean bean) {
                callback.productCallBack(bean);
                dialogListingType.dismiss();
            }

            @Override
            public void onLongClickListener(int position, ProductBean bean) {
                SharedObjects sharedObjects = new SharedObjects(context);
                sharedObjects.dbHandler.deleteProduct(bean.getId());
            }

            @Override
            public void onViewHideListener(boolean hideShow) {
                rvProductSearch.setVisibility(hideShow ? View.VISIBLE : View.GONE);
                txtProductError.setVisibility(hideShow ? View.GONE : View.VISIBLE);
            }
        });*/


        fabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListingType.dismiss();
            }
        });

        if (!dialogListingType.isShowing()) {
            dialogListingType.show();
        }
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
        if (TextUtils.isEmpty(binding.edtListingType.getText())) {
            AppDialogs.showSnackBarAutoHide(getResources().getString(R.string.errListingType), binding.edtListingType);
//            binding.inputLayoutCapacity.setError(getResources().getString(R.string.errCapacity));
//            binding.inputLayoutCapacity.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(binding.edtListingType.getText().toString().trim())) {
            AppDialogs.showSnackBarAutoHide(getResources().getString(R.string.errListingType), binding.edtListingType);
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
