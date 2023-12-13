package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class homework extends AppCompatActivity {

    private String receiverClass,receiverSchool;
    private DatabaseReference classRef;
    private DatabaseReference homeWorkRef;
    private Button sendIT;
    private EditText givenWork;

    private RecyclerView teacherSide;
    private ArrayList<String> whatToname;
    private MyAdapter mWorkAdapter;
    private ArrayAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        Intent intent = getIntent();
        receiverClass = intent.getStringExtra("ReceiveClass");
        receiverSchool = intent.getStringExtra("ReceiveSchool");
        sendIT=findViewById(R.id.sendworkBtn);
        givenWork=findViewById(R.id.edtHomeWork);
        classRef= FirebaseDatabase.getInstance().getReference().child("class").child(receiverSchool).child(receiverClass);
        homeWorkRef= FirebaseDatabase.getInstance().getReference().child("class").child(receiverSchool).child(receiverClass).child("HomeWorks");
        //FirebaseMessaging.getInstance().subscribeToTopic("all");

        teacherSide = findViewById(R.id.homeWorkTeacherSide);
        teacherSide.setLayoutManager(new LinearLayoutManager(homework.this));
        whatToname = new ArrayList<>();
        mWorkAdapter= new MyAdapter(homework.this,whatToname);
        teacherSide.setAdapter(mWorkAdapter);

        sendIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(givenWork.getText().toString().equals("")){
                    Toast.makeText(homework.this,"Input is empty",Toast.LENGTH_LONG).show();
                }else{
                    classRef.child("HomeWorks").child(getDate().toString()).setValue(getDate().toString()+">"+givenWork.getText().toString());
                    //Toast.makeText(homework.this,receiverSchool+">"+receiverClass,Toast.LENGTH_SHORT).show();
                    // uncheck this FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/"+receiverSchool+">"+receiverClass,"Do",givenWork.getText().toString(),getApplicationContext(),homework.this);
                    //uncheck this notificationsSender.SendNotifications();
                    Toast.makeText(homework.this,"Homework has been sent!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeWorkRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String val=snapshot.getValue().toString();
                whatToname.add(0,val);
                mWorkAdapter.notifyItemChanged(whatToname.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
    private String getDate(){
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
}