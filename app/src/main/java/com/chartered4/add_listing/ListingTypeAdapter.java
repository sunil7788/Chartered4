package com.chartered4.add_listing;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chartered4.R;
import com.chartered4.databinding.ItemViewHomeInquiriesBinding;
import com.chartered4.databinding.ItemViewListingTypeBinding;
import com.chartered4.home.HomeInquiriesAdapter;
import com.chartered4.models.ListingTypeBean;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ListingTypeAdapter extends RecyclerView.Adapter<ListingTypeAdapter.ViewHolder> {

    ArrayList<ListingTypeBean> list;
    Context context;
    OnItemClickListener onItemClickListener;

    public ListingTypeAdapter(ArrayList<ListingTypeBean> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(ItemViewListingTypeBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            ListingTypeBean bean = list.get(holder.getAdapterPosition());

            if (!TextUtils.isEmpty(bean.getTitle())){
                holder.txtName.setText(bean.getTitle());
            }else{
                holder.txtName.setText("");
            }

            holder.imgIcon.setBackground(bean.getIcon());

            if (bean.isChecked()) {
                holder.imgTick.setVisibility(View.VISIBLE);
                holder.cardMain.setCardBackgroundColor(context.getColor(R.color.colorPrimary100));
            } else {
                holder.imgTick.setVisibility(View.GONE);
                holder.cardMain.setCardBackgroundColor(context.getColor(R.color.white));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(holder.getLayoutPosition(), bean);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getSelectedCount() {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                count++;
            }
        }
        return count;
    }

    public String getSelectedText() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                sb.append(list.get(i).getTitle());
                sb.append(", ");
            }
        }
        if (!TextUtils.isEmpty(sb.toString())){
            return sb.deleteCharAt(sb.length() - 2).toString();
        }else{
            return "";
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
        void onItemClickListener(int position, ListingTypeBean bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        ImageView imgTick, imgIcon;
        MaterialCardView cardMain;

        public ViewHolder(ItemViewListingTypeBinding itemView) {
            super(itemView.getRoot());

            txtName = itemView.txtName ;
            imgTick = itemView.imgTick ;
            imgIcon = itemView.imgIcon ;
            cardMain = itemView.cardMain ;
        }
    }
}
