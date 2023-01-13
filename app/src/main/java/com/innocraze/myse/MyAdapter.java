package com.innocraze.myse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> works;
    //private DatabaseReference classRef;

    public MyAdapter(Context context, ArrayList<String> works) {
        this.context = context;
        this.works = works;
//        classRef = FirebaseDatabase.getInstance().getReference().child("class").child("10021");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.workitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String[] s=works.get(position).split(">",2);
        holder.date.setText(s[0]);
        holder.work.setText(s[1]);
    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView work,date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            work=itemView.findViewById(R.id.homeWorkTxts);
            date=itemView.findViewById(R.id.dateForHome);
        }
    }
}
