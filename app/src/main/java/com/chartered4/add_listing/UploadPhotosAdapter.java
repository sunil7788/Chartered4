package com.chartered4.add_listing;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chartered4.R;
import com.chartered4.databinding.ItemViewListingTypeBinding;
import com.chartered4.databinding.ItemViewUploadPhotosBinding;
import com.chartered4.models.ListingTypeBean;
import com.chartered4.utils.ItemMoveCallback;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class UploadPhotosAdapter extends RecyclerView.Adapter<UploadPhotosAdapter.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract{

    ArrayList<File> list;
    Context context;
    OnItemClickListener onItemClickListener;

    public UploadPhotosAdapter(ArrayList<File> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(ItemViewUploadPhotosBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            File bean = list.get(holder.getAdapterPosition());

            Picasso.get().load(bean).into(holder.imgPreview);

            if (position == 0){
                holder.cardMain.setStrokeWidth(10);
                holder.cardMain.setStrokeColor(context.getColor(R.color.colorPrimary));
            }else{
                holder.cardMain.setStrokeWidth(0);
            }

            holder.fabDelete.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, File bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPreview;
        FloatingActionButton fabDelete;
        MaterialCardView cardMain;

        public ViewHolder(ItemViewUploadPhotosBinding itemView) {
            super(itemView.getRoot());

            fabDelete = itemView.fabDelete ;
            imgPreview = itemView.imgPreview ;
            cardMain = itemView.cardMain ;
        }
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
       /* if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);*/

        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyDataSetChanged();
    }

    @Override
    public void onRowSelected(ViewHolder myViewHolder) {
        Log.e("onRowSelected", "called");
        myViewHolder.itemView.setAlpha(0.7f);
    }

    @Override
    public void onRowClear(ViewHolder myViewHolder) {
        Log.e("onRowClear", "called");
        myViewHolder.itemView.setAlpha(1f);
    }
}
