package com.innocraze.myse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> markss;

    public MarkAdapter(Context context, ArrayList<String> markss) {
        this.context = context;
        this.markss = markss;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mark_item,parent,false);
        return new MarkAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String[] splitIT = markss.get(position).split(">",5);
        int v=splitIT.length;
        if(v==4){
            holder.name.setText(splitIT[0]);
            holder.mark1.setText(splitIT[1]);
            holder.mark2.setText(splitIT[2]);
            holder.remark.setVisibility(View.VISIBLE);
            holder.remark.setText("Remark: "+splitIT[3]);
        }
        else{
            holder.name.setText(splitIT[0]);
            holder.mark1.setText(splitIT[1]);
            holder.mark2.setText(splitIT[2]);
        }



    }

    @Override
    public int getItemCount() {
        return markss.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,mark1,mark2,remark;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.examNameTXT);
            mark1=itemView.findViewById(R.id.markTXT1);
            mark2=itemView.findViewById(R.id.markTXT2);
            remark=itemView.findViewById(R.id.remarkTXT);
        }
    }
}
