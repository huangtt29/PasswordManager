package com.example.passwordmanager;

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

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private List<Grouping_item> records;
    private GroupAdapter.OnItemClickListener mOnItemClickListener;
    public GroupAdapter(List<Grouping_item> records_)
    {
        records=records_;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView grouping_icon;
        TextView grouping_title;
        TextView grouping_num;
        public ViewHolder(View view)
        {
            super(view);
            grouping_icon=(ImageView)view.findViewById(R.id.grouping_icon);
            grouping_title=(TextView)view.findViewById(R.id.grouping_title);
            grouping_num=(TextView)view.findViewById(R.id.grouping_num);
        }
    }
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grouping_item,parent,false);
        GroupAdapter.ViewHolder holder=new GroupAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(final GroupAdapter.ViewHolder holder, int position)
    {
        Grouping_item record=records.get(position);
        holder.grouping_icon.setImageResource(record.getGrouping_icon());
        holder.grouping_title.setText(record.getGrouping_title());
        holder.grouping_num.setText(record.getGrouping_num());
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
    public void setOnItemClickListener(GroupAdapter.OnItemClickListener a){
        mOnItemClickListener=a;
    }
}
