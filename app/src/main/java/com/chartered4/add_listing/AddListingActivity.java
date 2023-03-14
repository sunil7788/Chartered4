package com.chartered4.add_listing;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.chartered4.R;
import com.chartered4.databinding.ActivityAddListingBinding;
import com.chartered4.models.AddListingBean;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class AddListingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAddListingBinding binding;
    private ViewPagerAdapter viewPagerAdapter;

    public static AddListingBean addListingBean;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddListingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        addListingBean = new AddListingBean();

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        /*binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        binding.viewPager.setPagingEnabled(false);
        binding.viewPager.setOffscreenPageLimit(1);

        setListeners();

        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add the fragments
        viewPagerAdapter.add(new BasicInformationFragment(), getString(R.string.basicInformation));
        viewPagerAdapter.add(new PhotosAndDescriptionFragment(), getString(R.string.photosAndDescription));
        viewPagerAdapter.add(new AmenitiesAndEquipmentFragment(), getString(R.string.amenitiesAndEquipment));
        viewPagerAdapter.add(new RatesAndOffersFragment(), getString(R.string.ratesAndOffers));

        // Set the adapter
        binding.viewPager.setAdapter(viewPagerAdapter);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                binding.stepView.go(position, true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void changeNextPage(){
        int pos = binding.viewPager.getCurrentItem() + 1;
        binding.viewPager.setCurrentItem(pos);
    }

    public void changePreviousPage(){
        int pos = binding.viewPager.getCurrentItem() - 1;
        binding.viewPager.setCurrentItem(pos);
    }

    private void setListeners() {
        binding.cardBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.cardBack){
            onBackPressed();
        }
    }

    public void loadFragment(Fragment fragment) {

        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.flContent, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

}