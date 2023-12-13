package com.innocraze.myse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SetHomeWorkAdapter extends RecyclerView.Adapter<SetHomeWorkAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> works, keyOfHomeWorks;
    DatabaseReference homeRef;
    //private DatabaseReference classRef;

    public SetHomeWorkAdapter(Context context, ArrayList<String> works, DatabaseReference cRef, ArrayList<String> keyOfHomeWorks) {
        this.context = context;
        this.works = works;
        homeRef = cRef;
        this.keyOfHomeWorks=keyOfHomeWorks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.workitemset,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Toast.makeText(this.context,works.get(0),Toast.LENGTH_SHORT).show();
        String[] s=works.get(position).split(">",2);
        if(getDate().toString().equals(s[0])){
            holder.date.setText("Today: "+s[0]);
            holder.work.setText(s[1]);
            holder.green.setVisibility(View.VISIBLE);
        }else{
            holder.date.setText(s[0]);
            holder.work.setText(s[1]);
            holder.green.setVisibility(View.GONE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    homeRef.child(keyOfHomeWorks.get(position)).removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView work,date;
        ImageButton delete;
        ImageView green;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            work=itemView.findViewById(R.id.homeWorkTxts);
            date=itemView.findViewById(R.id.dateForHome);
            green=itemView.findViewById(R.id.greenCir);
            delete=itemView.findViewById(R.id.imgDeleteBtn);
        }
    }
    private String getDate(){
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
}
