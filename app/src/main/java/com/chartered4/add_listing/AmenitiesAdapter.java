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
import com.chartered4.databinding.ItemViewAmenitiesBinding;
import com.chartered4.databinding.ItemViewListingTypeBinding;
import com.chartered4.models.AmenitiesBean;
import com.chartered4.models.ListingTypeBean;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.ViewHolder> {

    ArrayList<AmenitiesBean> list;
    Context context;
    OnItemClickListener onItemClickListener;

    public AmenitiesAdapter(ArrayList<AmenitiesBean> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(ItemViewAmenitiesBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            AmenitiesBean bean = list.get(holder.getAdapterPosition());

            if (!TextUtils.isEmpty(bean.getTitle())){
                holder.txtName.setText(bean.getTitle());

                if (bean.getTitle().equalsIgnoreCase("Life Jackets")){
                    holder.cbSelect.setClickable(false);
                    holder.cbSelect.setEnabled(false);
                }else{
                    holder.cbSelect.setClickable(true);
                    holder.cbSelect.setEnabled(true);
                }

            }else{
                holder.txtName.setText("");
            }

            if (bean.isChecked()) {
                holder.cbSelect.setChecked(true);
            } else {
                holder.cbSelect.setChecked(false);
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
        void onItemClickListener(int position, AmenitiesBean bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        MaterialCheckBox cbSelect;
        MaterialCardView cardMain;

        public ViewHolder(ItemViewAmenitiesBinding itemView) {
            super(itemView.getRoot());

            txtName = itemView.txtName ;
            cbSelect = itemView.cbSelect ;
            cardMain = itemView.cardMain ;
        }
    }
}
