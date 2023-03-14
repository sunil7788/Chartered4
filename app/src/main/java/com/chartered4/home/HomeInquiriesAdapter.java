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
import com.chartered4.databinding.ItemViewHomeInquiriesBinding;
import com.chartered4.models.InquiriesBean;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;


public class HomeInquiriesAdapter extends RecyclerView.Adapter<HomeInquiriesAdapter.ViewHolder> {

    ArrayList<InquiriesBean> list;
    Context context;
    OnItemClickListener onItemClickListener;

    public HomeInquiriesAdapter(ArrayList<InquiriesBean> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(ItemViewHomeInquiriesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            InquiriesBean bean = list.get(position);
//            holder.txtDate.setText("11 Feb, 8:00");

            if (position == 0){
                holder.llExpiresOn.setVisibility(View.VISIBLE);
            }else{
                holder.llExpiresOn.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.cardAction.getVisibility() == View.VISIBLE){
                        holder.cardAction.setVisibility(View.GONE);
                        holder.txtViewMore.setVisibility(View.GONE);
                    }else{
                        holder.cardAction.setVisibility(View.VISIBLE);
                        holder.txtViewMore.setVisibility(View.VISIBLE);
                    }
                        /*if (onItemClickListener != null) {
                            onItemClickListener.onItemClickListener(holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
                        }*/
                }
            });

            holder.txtViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
                    }
                }
            });

            holder.llAdjust.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemAdjustClickListener(holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
                    }
                }
            });

            holder.llReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemRejectClickListener(holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
                    }
                }
            });

            holder.llApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemApproveClickListener(holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
                    }
                }
            });

            holder.llChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemChatClickListener(holder.getAdapterPosition(), list.get(holder.getAdapterPosition()));
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
        void onItemRejectClickListener(int position, InquiriesBean bean);
        void onItemApproveClickListener(int position, InquiriesBean bean);
        void onItemAdjustClickListener(int position, InquiriesBean bean);
        void onItemChatClickListener(int position, InquiriesBean bean);
    }

    public ArrayList<InquiriesBean> adapterList() {
        return list;
    }

    public InquiriesBean getItem(int position) {
        return list.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDay, txtMonthYear, txtTime, txtName, txtDuration, txtBoat, txtAdults, txtChild, txtAmount, txtViewMore;
        MaterialCardView cardAction;
        LinearLayout llReject, llAdjust, llChat, llApprove, llExpiresOn;

        public ViewHolder(ItemViewHomeInquiriesBinding itemView) {
            super(itemView.getRoot());

            txtDay = itemView.txtDay;
            txtMonthYear = itemView.txtMonthYear;
            txtTime = itemView.txtTime;
            txtName = itemView.txtName;
            txtDuration = itemView.txtDuration;
            txtBoat = itemView.txtBoat;
            txtAdults = itemView.txtAdults;
            txtChild = itemView.txtChild;
            txtAmount = itemView.txtAmount;
            llReject = itemView.llReject;
            llAdjust = itemView.llAdjust;
            llChat = itemView.llChat;
            llApprove = itemView.llApprove;
            llExpiresOn = itemView.llExpiresOn;
            txtViewMore = itemView.txtViewMore;
            cardAction = itemView.cardAction;
        }
    }
}
