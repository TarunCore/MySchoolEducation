package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myReport extends AppCompatActivity {

    private DatabaseReference stRepRef;
    private String t11,t22;
    private TextView stName,className,report;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);
        Intent intent = getIntent();
        t11 = intent.getStringExtra("SName");
        t22= intent.getStringExtra("ClassName");
        stName=findViewById(R.id.username);
        className=findViewById(R.id.classOFstd);
        report=findViewById(R.id.reportSentByT);

        stName.setText(t11);
        className.setText(t22);

        sp = getSharedPreferences("LogIN", Context.MODE_PRIVATE);
        String t1 = sp.getString("StdName","");
        String t2 = sp.getString("cCode","");
        String t3 = sp.getString("sCode","");
        String t4 = sp.getString("sID","");
        stRepRef= FirebaseDatabase.getInstance().getReference().child("class").child(t3).child(t2).child("Student").child(t4).child("Report");
        stRepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                report.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}