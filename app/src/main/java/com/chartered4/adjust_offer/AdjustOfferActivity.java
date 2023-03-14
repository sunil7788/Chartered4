package com.chartered4.adjust_offer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.chartered4.R;
import com.chartered4.add_listing.AmenitiesAndEquipmentFragment;
import com.chartered4.add_listing.BasicInformationFragment;
import com.chartered4.add_listing.PhotosAndDescriptionFragment;
import com.chartered4.add_listing.RatesAndOffersFragment;
import com.chartered4.add_listing.ViewPagerAdapter;
import com.chartered4.databinding.ActivityAddListingBinding;
import com.chartered4.databinding.ActivityAdjustOfferBinding;
import com.chartered4.utils.AppConstants;
import com.chartered4.utils.AppUtils;
import com.chartered4.utils.SharedObjects;

import java.util.Calendar;
import java.util.Date;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class AdjustOfferActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAdjustOfferBinding binding;
    private Calendar calendar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdjustOfferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        calendar = Calendar.getInstance();

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString spannableString1 = new SpannableString("The customer has provided an alternate date. ");
        ForegroundColorSpan foregroundSpan1 = new ForegroundColorSpan(ContextCompat.getColor(AdjustOfferActivity.this, R.color.grey700));
        spannableString1.setSpan(foregroundSpan1, 0, spannableString1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(spannableString1);

        SpannableString spannableString2 = new SpannableString("Apply alternate date");
        spannableString2.setSpan(new StyleSpan(Typeface.BOLD), 0, spannableString2.length(), 0);
        ForegroundColorSpan foregroundSpan2 = new ForegroundColorSpan(ContextCompat.getColor(AdjustOfferActivity.this, R.color.colorPrimary));
        spannableString2.setSpan(foregroundSpan2, 0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
//                textPaint.setColor(ContextCompat.getColor(CartActivity.this,R.color.ratings_5));    // you can use custom color
                textPaint.setUnderlineText(true);    // this remove the underline
            }

        },0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(spannableString2);

        binding.txtNotes.setText(builder, TextView.BufferType.SPANNABLE);

        setListeners();
    }

    private void setListeners() {
        binding.cardBack.setOnClickListener(this);
        binding.edtDropOffDate.setOnClickListener(this);
        binding.edtDropOffTime.setOnClickListener(this);
        binding.edtPickUpDate.setOnClickListener(this);
        binding.edtPickUpTime.setOnClickListener(this);

        binding.edtMessage.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (binding.edtMessage.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.cardBack){
            onBackPressed();
        }
        if (viewId == R.id.edtPickUpTime){

            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker = new TimePickerDialog(AdjustOfferActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    binding.edtPickUpTime.setText(AppUtils.convertDateFormat(AppUtils.pad(selectedHour) + ":" + AppUtils.pad(selectedMinute),
                            AppConstants.DateFormats.TIME_FORMAT_24,
                            AppConstants.DateFormats.TIME_FORMAT_12));
                }
            }, hour, minute, false);
            mTimePicker.show();
        }
        if (viewId == R.id.edtPickUpDate){
            /*int currentYear = Integer.parseInt(SharedObjects.getTodaysDate(AppConstants.DateFormats.YYYY));
            int maxYear = currentYear - 15;
            Log.e("maxYear", maxYear + "");
            Calendar calendarMax = Calendar.getInstance();
            calendarMax.set(Calendar.DAY_OF_MONTH,31);
            calendarMax.set(Calendar.YEAR,maxYear);
            calendarMax.set(Calendar.MONTH,Calendar.DECEMBER);
            Date dateMax = calendarMax.getTime();*/

            DatePickerDialog datePickerDialog = new DatePickerDialog(AdjustOfferActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            binding.edtPickUpDate.setText(AppUtils.pad(dayOfMonth) + "-" + AppUtils.pad(monthOfYear + 1) + "-" + year);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.setCancelable(false);
            datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
//            datePickerDialog.getDatePicker().setMaxDate(dateMax.getTime());
//            datePickerDialog.getDatePicker().getTouchables().get(0).performClick();
            datePickerDialog.show();
        }
    }

}