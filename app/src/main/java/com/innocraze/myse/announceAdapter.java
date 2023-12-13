package com.innocraze.myse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class announceAdapter extends RecyclerView.Adapter<announceAdapter.MyViewHolder>{

    Context context;
    ArrayList<announceData> arr;

    public announceAdapter(Context context, ArrayList<announceData> arr) {
        this.context = context;
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.announce_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        announceData announcedata = arr.get(position);
        holder.t1.setText(announcedata.getInfo());
        holder.t2.setText("Date: "+announcedata.getuDate());
        if(!announcedata.getImgUrl().equals("N")){
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(context)
                    .load(announcedata.getImgUrl())
                    .apply(options)
                    .into(holder.img);
        }else{
            holder.img.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView t1,t2;
        ImageView img;
        CardView imgC;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.dataTxt);
            t2=itemView.findViewById(R.id.dateTxt);

            img=itemView.findViewById(R.id.announceImg);
        }
    }
}
