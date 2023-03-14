package com.chartered4.add_listing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chartered4.R;
import com.chartered4.databinding.FragmentAmenitiesAndEquipmentBinding;
import com.chartered4.models.AmenitiesBean;
import com.chartered4.models.ListingTypeBean;
import com.chartered4.utils.AppDialogs;
import com.chartered4.utils.AppUtils;
import com.chartered4.utils.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by DELL on 14-Oct-18.
 */

public class AmenitiesAndEquipmentFragment extends Fragment implements View.OnClickListener {

    FragmentAmenitiesAndEquipmentBinding binding;

    ArrayList<AmenitiesBean> arrMainEquipment = new ArrayList<>();
    ArrayList<AmenitiesBean> arrMusicSystem = new ArrayList<>();
    ArrayList<AmenitiesBean> arrKitchen = new ArrayList<>();
    ArrayList<AmenitiesBean> arrHeads = new ArrayList<>();
    ArrayList<AmenitiesBean> arrWaterSportEquipment = new ArrayList<>();

    int spanCount = 2;
    int spacing = 5;
    boolean includeEdge = false;

    public AmenitiesAndEquipmentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAmenitiesAndEquipmentBinding.inflate(inflater, container, false);
        setListeners();

        setMainEquipment();
        setMusicSystem();
        setKitchen();
        setHeads();
        setWaterSportsEquipment();

        return binding.getRoot();
    }

    private void setListeners() {
        binding.btnPrevious.setOnClickListener(this);
        binding.btnContinue.setOnClickListener(this);
    }

    private void setMainEquipment() {

        arrMainEquipment = new ArrayList<>();
        arrMainEquipment.add(new AmenitiesBean("Life Jackets", "lifeJackets",true));
        arrMainEquipment.add(new AmenitiesBean("Cabin","accessToCabin",false));
        arrMainEquipment.add(new AmenitiesBean("Air Conditioning", "ac",false));
        arrMainEquipment.add(new AmenitiesBean("BBQ","bbq",false));
        arrMainEquipment.add(new AmenitiesBean("Swim Platform","swimPlatform",false));

        AmenitiesAdapter amenitiesAdapter = new AmenitiesAdapter(arrMainEquipment);
        binding.rvMainEquipment.setAdapter(amenitiesAdapter);
        binding.rvMainEquipment.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        amenitiesAdapter.setOnItemClickListener(new AmenitiesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, AmenitiesBean bean) {
                if (bean.isChecked()){
                    arrMainEquipment.get(position).setChecked(false);
                }else{
                    arrMainEquipment.get(position).setChecked(true);
                }
                amenitiesAdapter.notifyItemChanged(position);
            }
        });

    }

    private void setMusicSystem() {

        arrMusicSystem = new ArrayList<>();
        arrMusicSystem.add(new AmenitiesBean("Bluetooth Audio","bluetooth",false));
        arrMusicSystem.add(new AmenitiesBean("Auxiliary Jack","audioJack",false));
        arrMusicSystem.add(new AmenitiesBean("CD Player","cdPlayer",false));
        arrMusicSystem.add(new AmenitiesBean("Radio","radio",false));
        arrMusicSystem.add(new AmenitiesBean("Television","television",false));
        arrMusicSystem.add(new AmenitiesBean("DVD Player","dvdPlayer",false));

        AmenitiesAdapter amenitiesAdapter = new AmenitiesAdapter(arrMusicSystem);
        binding.rvMusicSystem.setAdapter(amenitiesAdapter);
        binding.rvMusicSystem.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        amenitiesAdapter.setOnItemClickListener(new AmenitiesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, AmenitiesBean bean) {
                if (bean.isChecked()){
                    arrMusicSystem.get(position).setChecked(false);
                }else{
                    arrMusicSystem.get(position).setChecked(true);
                }
                amenitiesAdapter.notifyItemChanged(position);
            }
        });

    }

    private void setKitchen() {

        arrKitchen = new ArrayList<>();
        arrKitchen.add(new AmenitiesBean("Refrigerator", "refrigerator",false));
        arrKitchen.add(new AmenitiesBean("Freezer","freezer",false));
        arrKitchen.add(new AmenitiesBean("Stove","stove",false));
        arrKitchen.add(new AmenitiesBean("Oven","oven",false));
        arrKitchen.add(new AmenitiesBean("Microwave","microwave",false));
        arrKitchen.add(new AmenitiesBean("Ice Maker","iceMaker",false));

        AmenitiesAdapter amenitiesAdapter = new AmenitiesAdapter(arrKitchen);
        binding.rvKitchen.setAdapter(amenitiesAdapter);
        binding.rvKitchen.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        amenitiesAdapter.setOnItemClickListener(new AmenitiesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, AmenitiesBean bean) {
                if (bean.isChecked()){
                    arrKitchen.get(position).setChecked(false);
                }else{
                    arrKitchen.get(position).setChecked(true);
                }
                amenitiesAdapter.notifyItemChanged(position);
            }
        });

    }

    private void setHeads() {

        arrHeads = new ArrayList<>();
        arrHeads.add(new AmenitiesBean("Toilet","toilet",false));
        arrHeads.add(new AmenitiesBean("Shower","shower",false));
        arrHeads.add(new AmenitiesBean("Sink","sink",false));
        arrHeads.add(new AmenitiesBean("Hot Water","hotWater",false));

        AmenitiesAdapter amenitiesAdapter = new AmenitiesAdapter(arrHeads);
        binding.rvHeads.setAdapter(amenitiesAdapter);
        binding.rvHeads.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        amenitiesAdapter.setOnItemClickListener(new AmenitiesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, AmenitiesBean bean) {
                if (bean.isChecked()){
                    arrHeads.get(position).setChecked(false);
                }else{
                    arrHeads.get(position).setChecked(true);
                }
                amenitiesAdapter.notifyItemChanged(position);
            }
        });

    }

    private void setWaterSportsEquipment() {

        arrWaterSportEquipment = new ArrayList<>();
        arrWaterSportEquipment.add(new AmenitiesBean("Wakeboard/Water Ski's","wakeBoard",false));
        arrWaterSportEquipment.add(new AmenitiesBean("Paddle Board","paddleBoard",false));
        arrWaterSportEquipment.add(new AmenitiesBean("Floaties","floaties",false));
        arrWaterSportEquipment.add(new AmenitiesBean("Water Toys","waterToys",false));
        arrWaterSportEquipment.add(new AmenitiesBean("Jet Ski (extra cost)","jetKey",false));
        arrWaterSportEquipment.add(new AmenitiesBean("Fishing Gear","fishingGear",false));
        arrWaterSportEquipment.add(new AmenitiesBean("Snorkel Gear","snorkelGear",false));
        arrWaterSportEquipment.add(new AmenitiesBean("Dinghy","Dinghy",false));
        
        AmenitiesAdapter amenitiesAdapter = new AmenitiesAdapter(arrWaterSportEquipment);
        binding.rvWaterSportEquipment.setAdapter(amenitiesAdapter);
        binding.rvWaterSportEquipment.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        amenitiesAdapter.setOnItemClickListener(new AmenitiesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, AmenitiesBean bean) {
                if (bean.isChecked()){
                    arrWaterSportEquipment.get(position).setChecked(false);
                }else{
                    arrWaterSportEquipment.get(position).setChecked(true);
                }
                amenitiesAdapter.notifyItemChanged(position);
            }
        });

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
            AppUtils.hideKeyboard(binding.btnPrevious, getActivity());
            ((AddListingActivity) getActivity()).changePreviousPage();
        }
        if (viewId == R.id.btnContinue){
            AppUtils.hideKeyboard(binding.btnContinue, getActivity());
            ((AddListingActivity) getActivity()).changeNextPage();
        }
    }

}
