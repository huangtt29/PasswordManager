package com.example.passwordmanager;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HTT on 2018/1/1.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private List<Detail_item> records;
    private DetailAdapter.OnItemClickListener mOnItemClickListener;
    public DetailAdapter(List<Detail_item> records_)
    {
        records=records_;
    }
    public boolean isDeleteMode = false;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView detail_icon;
        TextView detail_title;
        TextView detail_username;
        ImageView deleteBtn;
        ImageView rightIcon;
        public ViewHolder(View view)
        {
            super(view);
            detail_icon=(ImageView)view.findViewById(R.id.detail_icon);
            detail_title=(TextView)view.findViewById(R.id.detail_title);
            detail_username=(TextView)view.findViewById(R.id.detail_username);
            deleteBtn=(ImageView) view.findViewById(R.id.delete);
            rightIcon=(ImageView)view.findViewById(R.id.right_icon2);
        }
    }
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item,parent,false);
        DetailAdapter.ViewHolder holder=new DetailAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final DetailAdapter.ViewHolder holder, final int position)
    {
        Detail_item record=records.get(position);
        holder.detail_icon.setImageResource(record.getDetail_icon());
        holder.detail_username.setText(record.getDetail_username());
        holder.detail_title.setText(record.getDetail_title());
        if(isDeleteMode) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
            holder.rightIcon.setVisibility(View.GONE);
        } else {
            holder.deleteBtn.setVisibility(View.GONE);
            holder.rightIcon.setVisibility(View.VISIBLE);
        }

        if(mOnItemClickListener!=null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.getAdapterPosition());
                    return false;
                }
            });

            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onDeleteClick(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return records.size();
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(DetailAdapter.OnItemClickListener a){
        mOnItemClickListener=a;
    }
}
