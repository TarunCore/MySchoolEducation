package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class seeAnnounce extends AppCompatActivity {

    private String receiverSchool;
    private DatabaseReference announceRef;
    private RecyclerView myRecylerView;
    private announceAdapter myAdapter;
    private ArrayList<announceData> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_announce);

        Intent intent = getIntent();
        receiverSchool = intent.getStringExtra("ReceiveSchool");
        announceRef= FirebaseDatabase.getInstance().getReference().child("class").child(receiverSchool).child("Announces");
        myRecylerView=findViewById(R.id.recycleAnnounce);
        myRecylerView.setHasFixedSize(true);
        myRecylerView.setLayoutManager(new LinearLayoutManager(this));
        arr=new ArrayList<>();
        myAdapter=new announceAdapter(this,arr);
        myRecylerView.setAdapter(myAdapter);

        announceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    announceData announcedata = dataSnapshot.getValue(announceData.class);
                    arr.add(0, announcedata);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}