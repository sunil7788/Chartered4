package com.chartered4.add_listing;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

        SpannableStringBuilder builder = new SpannableStringBuilder();

        //
        SpannableString spannableString1 = new SpannableString("Provide a special offer for a set amount of hours, e.g. For 6 hours apply a");
        ForegroundColorSpan foregroundSpan1 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.primaryText));
        spannableString1.setSpan(foregroundSpan1, 0, spannableString1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(spannableString1);

//        int textSize1 = getResources().getDimensionPixelSize(R.dimen.cart_text);
        SpannableString spannableString2 = new SpannableString("10% OR $100 discount.");
//        spannableString2.setSpan(new AbsoluteSizeSpan(textSize1), 0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString2.length(), 0);
        ForegroundColorSpan foregroundSpan2 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.primaryText));
        spannableString2.setSpan(foregroundSpan2, 0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Log.e("freeCancellation","clicked");
//                startActivity(new Intent(CartActivity.this, URLSActivity.class)
//                        .putExtra(AppConstants.URLS.CANCELLATION_POLICY, AppConstants.URLS.CANCELLATION_POLICY));
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
//                textPaint.setColor(ContextCompat.getColor(CartActivity.this,R.color.ratings_5));    // you can use custom color
//                textPaint.setUnderlineText(false);    // this remove the underline
            }

        },0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(spannableString2);
/*
        SpannableString spannableString3 = new SpannableString(" upto 2 hours before the appointment time");
        ForegroundColorSpan foregroundSpan3 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.primaryText));
        spannableString3.setSpan(foregroundSpan3, 0, spannableString3.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(spannableString3);*/

        binding.txtNotes.setText(builder, TextView.BufferType.SPANNABLE);

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
