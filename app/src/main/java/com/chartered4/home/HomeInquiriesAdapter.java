package com.chartered4.home;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chartered4.R;
import com.chartered4.databinding.ItemviewHomeInquiriesBinding;
import com.chartered4.models.InquiriesBean;

import java.util.ArrayList;


public class HomeInquiriesAdapter extends RecyclerView.Adapter<HomeInquiriesAdapter.ViewHolder> {

    ArrayList<InquiriesBean> list;
    Context context;
    OnItemClickListener onItemClickListener;
//    ItemviewHomeInquiriesBinding binding;

    public HomeInquiriesAdapter(ArrayList<InquiriesBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        binding = ;
        return new ViewHolder(ItemviewHomeInquiriesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_home_inquiries, parent, false);
//        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            InquiriesBean bean = list.get(position);
            holder.itemviewHomeInquiriesBinding.txtDate.setText("11 Feb, 8:32");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, InquiriesBean bean);
    }

    public ArrayList<InquiriesBean> adapterList() {
        return list;
    }

    public InquiriesBean getItem(int position) {
        return list.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemviewHomeInquiriesBinding itemviewHomeInquiriesBinding;

        public ViewHolder(ItemviewHomeInquiriesBinding binding) {
            super(binding.getRoot());
            this.itemviewHomeInquiriesBinding = binding;

        }
    }
}
