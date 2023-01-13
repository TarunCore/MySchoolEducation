package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class seeMarks extends AppCompatActivity {

    private DatabaseReference classRef;
    private ListView listView;
    private String sID;

    private ArrayList<String> marks;
    private RecyclerView mRecyclerView;
    private MyAdapter mWorkAdapter;
    private ArrayAdapter mArrayAdapter;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_marks);

        mRecyclerView = findViewById(R.id.recycleMarks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(seeMarks.this));
        marks = new ArrayList<>();
        mWorkAdapter= new MyAdapter(seeMarks.this,marks);
        mRecyclerView.setAdapter(mWorkAdapter);
        sp = getSharedPreferences("LogIN", Context.MODE_PRIVATE);
        String t1 = sp.getString("StdName","");
        String t2 = sp.getString("cCode","");
        String t3 = sp.getString("sCode","");
        String t4 = sp.getString("sID","");
        classRef= FirebaseDatabase.getInstance().getReference().child("class").child(t3).child(t2).child("Student").child(t4);
        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String val = snapshot.child("Marks").getValue(String.class);
                marks.add(val);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}