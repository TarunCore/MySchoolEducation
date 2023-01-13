package com.innocraze.myse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> messsages;

    public MessageAdapter(Context context, ArrayList<String> messsages) {
        this.context = context;
        this.messsages = messsages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message_item,parent,false);
        return new MessageAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String[] splitted  = messsages.get(position).split(">",5);
        holder.name.setText(splitted[0]);
            holder.mes.setText(splitted[1]);
    }

    @Override
    public int getItemCount() {
        return messsages.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,mes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sentName);
            mes=itemView.findViewById(R.id.sentTXT);
        }
    }
}
