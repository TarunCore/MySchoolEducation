package com.innocraze.myse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class homework extends AppCompatActivity {

    private String receiverClass,receiverSchool;
    private DatabaseReference classRef;
    private Button sendIT;
    private EditText givenWork;
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
        //FirebaseMessaging.getInstance().subscribeToTopic("all");
        sendIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classRef.child("HomeWorks").child(getDate().toString()).setValue(getDate().toString()+">"+givenWork.getText().toString());
                //Toast.makeText(homework.this,receiverSchool+">"+receiverClass,Toast.LENGTH_SHORT).show();
                // uncheck this FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/"+receiverSchool+">"+receiverClass,"Do",givenWork.getText().toString(),getApplicationContext(),homework.this);
                //uncheck this notificationsSender.SendNotifications();
            }
        });



    }
    private String getDate(){
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }
}