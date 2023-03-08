package com.chartered4;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.chartered4.add_listing.AddListingActivity;
import com.chartered4.databinding.ActivityLoginSignupBinding;
import com.chartered4.databinding.ContentSignInBinding;
import com.chartered4.databinding.ContentSignUpBinding;
import com.chartered4.models.LoginBean;
import com.chartered4.models.MyErrorMessage;
import com.chartered4.retrofit.RestClient;
import com.chartered4.utils.AppConstants;
import com.chartered4.utils.AppDialogs;
import com.chartered4.utils.AppUtils;
import com.chartered4.utils.SharedObjects;
import com.chartered4.utils.ViewDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignupActivity extends AppCompatActivity implements View.OnClickListener/*, TextView.OnEditorActionListener*/ {

    SharedObjects sharedObjects;

    ActivityLoginSignupBinding binding;
    ContentSignInBinding bindingSignIn ;
    ContentSignUpBinding bindingSignUp ;

    private ViewDialog viewDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bindingSignIn = binding.llSignInView;
        bindingSignUp = binding.llSignUpView;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sharedObjects = new SharedObjects(LoginSignupActivity.this);

        showSignInView();

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.sign_in)));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.sign_up)));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (binding.tabLayout.getSelectedTabPosition() == 0){
                    showSignInView();
                }else{
                    showSignUpView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        setListeners();

        if (!TextUtils.isEmpty(sharedObjects.getPreference(AppConstants.PreferenceConstants.REMEMBER_EMAIL)) &&
                !TextUtils.isEmpty(sharedObjects.getPreference(AppConstants.PreferenceConstants.REMEMBER_PASSWORD))){
            bindingSignIn.edtEmail.setText(sharedObjects.getPreference(AppConstants.PreferenceConstants.REMEMBER_EMAIL));
            bindingSignIn.edtPassword.setText(sharedObjects.getPreference(AppConstants.PreferenceConstants.REMEMBER_PASSWORD));
        }

    }

    public void openTermsConditions(View view){
        Intent intent = new Intent(LoginSignupActivity.this, URLsActivity.class);
        intent.putExtra(AppConstants.IntentConstants.TERMS, AppConstants.IntentConstants.TERMS);
        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void showSignInView(){
        bindingSignIn.getRoot().setVisibility(View.VISIBLE);
        bindingSignUp.getRoot().setVisibility(View.GONE);
    }

    private void showSignUpView(){
        bindingSignIn.getRoot().setVisibility(View.GONE);
        bindingSignUp.getRoot().setVisibility(View.VISIBLE);
    }

    private void setListeners() {

//        bindingSignIn.edtEmail.setText("sunil@yopmail.com");
//        bindingSignIn.edtPassword.setText("01234567");

//        bindingSignIn.edtEmail.setText("jaiminprogrammer+1@gmail.com");
//        bindingSignIn.edtPassword.setText("123456789");

//        "password": "123456789",
//                "username": "jaiminprogrammer+1@gmail.com"

        bindingSignIn.btnSignIn.setOnClickListener(this);
//        bindingSignIn.edtPassword.setOnEditorActionListener(this);

        bindingSignIn.cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sharedObjects.setPreference(AppConstants.PreferenceConstants.REMEMBER_EMAIL, bindingSignIn.edtEmail.getText().toString().trim());
                    sharedObjects.setPreference(AppConstants.PreferenceConstants.REMEMBER_PASSWORD, bindingSignIn.edtPassword.getText().toString().trim());
                }
            }
        });

        bindingSignIn.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindingSignIn.inputLayoutEmail.setErrorEnabled(false);
                bindingSignIn.inputLayoutEmail.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bindingSignIn.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindingSignIn.inputLayoutPassword.setErrorEnabled(false);
                bindingSignIn.inputLayoutPassword.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bindingSignUp.btnSignUp.setOnClickListener(this);

        bindingSignUp.edtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindingSignUp.inputLayoutFirstName.setErrorEnabled(false);
                bindingSignUp.inputLayoutFirstName.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bindingSignUp.edtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindingSignUp.inputLayoutLastName.setErrorEnabled(false);
                bindingSignUp.inputLayoutLastName.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bindingSignUp.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindingSignUp.inputLayoutEmail.setErrorEnabled(false);
                bindingSignUp.inputLayoutEmail.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bindingSignUp.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindingSignUp.inputLayoutPhone.setErrorEnabled(false);
                bindingSignUp.inputLayoutPhone.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bindingSignUp.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindingSignUp.inputLayoutPassword.setErrorEnabled(false);
                bindingSignUp.inputLayoutPassword.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnSignIn){
            if (AppUtils.isNetworkConnected(LoginSignupActivity.this)) {
                validateSignIn();
            } else {
                AppDialogs.showAlertDialog(getString(R.string.internet_msg), LoginSignupActivity.this);
            }
        }else if (viewId == R.id.btnSignUp){
            if (AppUtils.isNetworkConnected(LoginSignupActivity.this)) {
                validateSignUp();
            } else {
                AppDialogs.showAlertDialog(getString(R.string.internet_msg), LoginSignupActivity.this);
            }
        }
    }

    public void validateSignIn(){
        if (!validateEmailSignIn()) {
            return;
        }
        if (!validatePasswordSignIn()) {
            return;
        }
        checkLogin();
    }

    /*private void checkLogin() {
        Intent intent = new Intent(LoginSignupActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }*/

    private void checkLogin() {

        showProgressDialog(true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", bindingSignIn.edtEmail.getText().toString().trim());
        jsonObject.addProperty("password", bindingSignIn.edtPassword.getText().toString().trim());

//        "password": "123456789",
//                "username": "jaiminprogrammer+1@gmail.com"

        Call<JsonElement> call = RestClient.post().login(jsonObject);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {
                    dismissProgressDialog();
                    if (response.code() == 401){
                        //{"timeStamp":1678264033177,"code":null,"message":"Fail to Login. Please try again or Contact our Administrator. ","data":null}
                        MyErrorMessage myErrorMessage = new Gson().fromJson(response.errorBody().charStream(),MyErrorMessage.class);

                        AppDialogs.showAlertDialog(myErrorMessage.getMessage(), LoginSignupActivity.this);

                    }else if (response.isSuccessful()) {

                        Log.e("Login - ", response.body().toString());

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject.getString(AppConstants.ResponseStatus.status)
                                .equals(AppConstants.ResponseStatus.status_success)) {

                            if (jsonObject.get(AppConstants.ResponseStatus.data) instanceof JSONObject){
                                sharedObjects.setPreference(AppConstants.PreferenceConstants.STATUS, AppConstants.PreferenceConstants.STATUS_LOGIN);
                                LoginBean bean = new Gson().fromJson(response.body().toString(), LoginBean.class);
                                sharedObjects.setPreference(AppConstants.PreferenceConstants.USER_INFO, new Gson().toJson(bean.getData()));

                                Intent intent = new Intent(LoginSignupActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(LoginSignupActivity.this).toBundle());
//                                overridePendingTransition(0, 0);
                                finish();
                            }else{
                                AppDialogs.showAlertDialog(jsonObject.getString(AppConstants.ResponseStatus.data), LoginSignupActivity.this);
                            }

                        } else {
                            AppDialogs.showAlertDialog(jsonObject.getString(AppConstants.ResponseStatus.data),LoginSignupActivity.this);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                dismissProgressDialog();
                AppDialogs.showSnackBarAutoHide(getString(R.string.something_went_wrong), bindingSignIn.btnSignIn);
            }
        });
    }

    public void validateSignUp(){
        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validateEmailSignUp()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }
        if (!validatePasswordSignUp()) {
            return;
        }
        if (!bindingSignUp.cbListWatercraft.isChecked()){
            AppDialogs.showSnackBarAutoHide(getString(R.string.agree_i_want_to_list_my_watercraft), bindingSignUp.cbListWatercraft);
            return;
        }
        if (!bindingSignUp.cbAgreeTermsConditions.isChecked()){
            AppDialogs.showSnackBarAutoHide(getString(R.string.agree_terms_condition), bindingSignUp.cbAgreeTermsConditions);
            return;
        }
        registerUser();
    }

    private void registerUser() {

        showProgressDialog(true);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fn", bindingSignUp.edtFirstName.getText().toString().trim());
        jsonObject.addProperty("ln", bindingSignUp.edtLastName.getText().toString().trim());
        jsonObject.addProperty("em", bindingSignUp.edtEmail.getText().toString().trim());
        jsonObject.addProperty("ph", bindingSignUp.edtPhone.getText().toString().trim());
        jsonObject.addProperty("ps", bindingSignUp.edtPassword.getText().toString().trim());
        jsonObject.addProperty("cps", bindingSignUp.edtPassword.getText().toString().trim());
        jsonObject.addProperty("rl", "o");
        jsonObject.addProperty("tc", true);

        Call<JsonElement> call = RestClient.post().registration(jsonObject);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {
                    Log.e("Register - ", response.body().toString());
                    dismissProgressDialog();
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject.getString(AppConstants.ResponseStatus.status)
                                .equals(AppConstants.ResponseStatus.status_success)) {

                            //{"errorMsg":"","data":{"name":"Success","value":"created"},"result":"success"}
                            //{"errorMsg":"","data":{"name":"ERROR","value":"Account with this email already exist"},"result":"success"}

                            if (jsonObject.get(AppConstants.ResponseStatus.data) instanceof JSONObject){

                                JSONObject joData = jsonObject.getJSONObject(AppConstants.ResponseStatus.data);
                                String name = joData.getString("name");
                                String value = joData.getString("value");

                                if (name.equalsIgnoreCase(AppConstants.ResponseStatus.status_success)){
                                    AppDialogs.showAlertDialog("Your account has been created. Please login to continue.", LoginSignupActivity.this);

                                    bindingSignUp.edtFirstName.setText("");
                                    bindingSignUp.edtLastName.setText("");
                                    bindingSignUp.edtEmail.setText("");
                                    bindingSignUp.edtPhone.setText("");
                                    bindingSignUp.edtPassword.setText("");
                                    bindingSignUp.cbListWatercraft.setChecked(false);
                                    bindingSignUp.cbAgreeTermsConditions.setChecked(false);

                                    binding.tabLayout.getTabAt(0).select();

                                }else{
                                    AppDialogs.showAlertDialog(value, LoginSignupActivity.this);
                                }

                                /*sharedObjects.setPreference(AppConstants.STATUS, AppConstants.STATUS_LOGIN);
                                LoginBean bean = new Gson().fromJson(response.body().toString(), LoginBean.class);
                                sharedObjects.setPreference(AppConstants.USER_INFO, new Gson().toJson(bean.getData()));

                                Intent intent = new Intent(LoginSignupActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();*/
                            }else{
                                AppDialogs.showAlertDialog(jsonObject.getString(AppConstants.ResponseStatus.data), LoginSignupActivity.this);
                            }

                        } else {
                            AppDialogs.showAlertDialog(jsonObject.getString(AppConstants.ResponseStatus.data),LoginSignupActivity.this);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                dismissProgressDialog();
                AppDialogs.showSnackBarAutoHide(getString(R.string.something_went_wrong), bindingSignIn.btnSignIn);
            }
        });
    }

    private boolean validateEmailSignUp() {
        if (TextUtils.isEmpty(bindingSignUp.edtEmail.getText())){
            bindingSignUp.inputLayoutEmail.setErrorEnabled(true);
            bindingSignUp.inputLayoutEmail.setError(getString(R.string.errEmailRequired));
            return false;
        } else if (TextUtils.isEmpty(bindingSignUp.edtEmail.getText().toString().trim())){
            bindingSignUp.inputLayoutEmail.setErrorEnabled(true);
            bindingSignUp.inputLayoutEmail.setError(getString(R.string.errEmailRequired));
            return false;
        } else if (!AppUtils.isValidEmail(bindingSignUp.edtEmail.getText().toString().trim())) {
            bindingSignUp.inputLayoutEmail.setErrorEnabled(true);
            bindingSignUp.inputLayoutEmail.setError(getString(R.string.errEmailInValid));
            return false;
        }
        return true;
    }

    private boolean validateEmailSignIn() {
        if (TextUtils.isEmpty(bindingSignIn.edtEmail.getText())){
            bindingSignIn.inputLayoutEmail.setErrorEnabled(true);
            bindingSignIn.inputLayoutEmail.setError(getString(R.string.errEmailRequired));
            return false;
        }else if (TextUtils.isEmpty(bindingSignIn.edtEmail.getText().toString().trim())){
            bindingSignIn.inputLayoutEmail.setErrorEnabled(true);
            bindingSignIn.inputLayoutEmail.setError(getString(R.string.errEmailRequired));
            return false;
        } else if (!AppUtils.isValidEmail(bindingSignIn.edtEmail.getText().toString().trim())) {
            bindingSignIn.inputLayoutEmail.setErrorEnabled(true);
            bindingSignIn.inputLayoutEmail.setError(getString(R.string.errEmailInValid));
            return false;
        }
        return true;
    }

    private boolean validatePasswordSignUp() {
        if (TextUtils.isEmpty(bindingSignUp.edtPassword.getText())) {
            bindingSignUp.inputLayoutPassword.setError(getResources().getString(R.string.errPasswordRequired));
            bindingSignUp.inputLayoutPassword.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(bindingSignUp.edtPassword.getText().toString().trim())) {
            bindingSignUp.inputLayoutPassword.setError(getResources().getString(R.string.errPasswordRequired));
            bindingSignUp.inputLayoutPassword.setErrorEnabled(true);
            return false;
        }else if (bindingSignUp.edtPassword.getText().toString().trim().length() < 8){
            bindingSignUp.inputLayoutPassword.setError(getResources().getString(R.string.errPasswordLength));
            bindingSignUp.inputLayoutPassword.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validatePasswordSignIn() {
        if (TextUtils.isEmpty(bindingSignIn.edtPassword.getText())) {
            bindingSignIn.inputLayoutPassword.setError(getResources().getString(R.string.errPasswordRequired));
            bindingSignIn.inputLayoutPassword.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(bindingSignIn.edtPassword.getText().toString().trim())) {
            bindingSignIn.inputLayoutPassword.setError(getResources().getString(R.string.errPasswordRequired));
            bindingSignIn.inputLayoutPassword.setErrorEnabled(true);
            return false;
        }else if (bindingSignIn.edtPassword.getText().toString().trim().length() < 8){
            bindingSignIn.inputLayoutPassword.setError(getResources().getString(R.string.errPasswordLength));
            bindingSignIn.inputLayoutPassword.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validateFirstName() {
        if (TextUtils.isEmpty(bindingSignUp.edtFirstName.getText())) {
            bindingSignUp.inputLayoutFirstName.setError(getResources().getString(R.string.errFirstName));
            bindingSignUp.inputLayoutFirstName.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(bindingSignUp.edtFirstName.getText().toString().trim())) {
            bindingSignUp.inputLayoutFirstName.setError(getResources().getString(R.string.errFirstName));
            bindingSignUp.inputLayoutFirstName.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validateLastName() {
        if (TextUtils.isEmpty(bindingSignUp.edtLastName.getText())) {
            bindingSignUp.inputLayoutLastName.setError(getResources().getString(R.string.errLastName));
            bindingSignUp.inputLayoutLastName.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(bindingSignUp.edtLastName.getText().toString().trim())) {
            bindingSignUp.inputLayoutLastName.setError(getResources().getString(R.string.errLastName));
            bindingSignUp.inputLayoutLastName.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        if (TextUtils.isEmpty(bindingSignUp.edtPhone.getText())) {
            bindingSignUp.inputLayoutPhone.setError(getResources().getString(R.string.errPhoneRequired));
            bindingSignUp.inputLayoutPhone.setErrorEnabled(true);
            return false;
        }else if (TextUtils.isEmpty(bindingSignUp.edtPhone.getText().toString().trim())) {
            bindingSignUp.inputLayoutPhone.setError(getResources().getString(R.string.errPhoneRequired));
            bindingSignUp.inputLayoutPhone.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    public void showProgressDialog(boolean cancelable) {
        viewDialog = new ViewDialog(LoginSignupActivity.this);
        if (!LoginSignupActivity.this.isFinishing()) {
            viewDialog.showDialog(cancelable);
        }
    }

    public void dismissProgressDialog() {
        viewDialog.hideDialog();
    }

    /*@Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            AppUtils.hideKeyboard(bindingSignIn.btnLogin, LoginSignupActivity.this);
            if (AppUtils.isNetworkConnected(LoginSignupActivity.this)) {
                validateLogin();
            } else {
                AppDialogs.showAlertDialog(getString(R.string.internet_msg),LoginSignupActivity.this);
            }
            return true;
        }
        return false;
    }*/
}
