package com.example.passwordmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2018/1/6.
 */

public class VarietyAdapter extends RecyclerView.Adapter<VarietyAdapter.ViewHolder> {
    private List<variety> records;
    private VarietyAdapter.OnItemClickListener mOnItemClickListener;
    public VarietyAdapter(List<variety> records_)
    {
        records=records_;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView variety_icon;
        TextView variety_name;
        public ViewHolder(View view)
        {
            super(view);
            variety_icon=(ImageView)view.findViewById(R.id.variety_icon);
            variety_name=(TextView)view.findViewById(R.id.variety_name);
        }
    }
    @Override
    public VarietyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.variety_item,parent,false);
        VarietyAdapter.ViewHolder holder=new VarietyAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final VarietyAdapter.ViewHolder holder, int position)
    {
        variety record=records.get(position);
        holder.variety_icon.setImageResource(record.getVariety_icon());
        holder.variety_name.setText(record.getVariety_name());
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
    }
    public void setOnItemClickListener(VarietyAdapter.OnItemClickListener a){
        mOnItemClickListener=a;
    }
}
