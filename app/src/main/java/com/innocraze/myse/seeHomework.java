package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class seeHomework extends AppCompatActivity {

    private String receiverClass,receiverSchool;
    private DatabaseReference classRef;
    private ListView listView;

    private ArrayList<String> homeList;

    private ArrayList<String> whatToname;
    private RecyclerView mRecyclerView;
    private MyAdapter mWorkAdapter;
    private ArrayAdapter mArrayAdapter;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_homework);

        listView=findViewById(R.id.listHome);
        homeList = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter(seeHomework.this,android.R.layout.simple_list_item_1,homeList); //see this line
        listView.setAdapter(mArrayAdapter);

        mRecyclerView = findViewById(R.id.recycleWorks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(seeHomework.this));
        whatToname = new ArrayList<>();
        mWorkAdapter= new MyAdapter(seeHomework.this,whatToname);
        mRecyclerView.setAdapter(mWorkAdapter);
        sp = getSharedPreferences("LogIN", Context.MODE_PRIVATE);
        String t1 = sp.getString("StdName","");
        String t2 = sp.getString("cCode","");
        String t3 = sp.getString("sCode","");
//        Toast.makeText(student.this, t2, Toast.LENGTH_SHORT).show();
//        Toast.makeText(student.this, t3, Toast.LENGTH_SHORT).show();

        classRef= FirebaseDatabase.getInstance().getReference().child("class").child(t3).child(t2).child("HomeWorks");

        classRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String val=snapshot.getValue(String.class);
                whatToname.add(0,val);
                homeList.add(0,val);
                mArrayAdapter.notifyDataSetChanged();
                mWorkAdapter.notifyItemChanged(whatToname.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}