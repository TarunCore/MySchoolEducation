package com.innocraze.myse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class Welcome extends AppCompatActivity {

    private Button createSchool,joinClassbtn,logSchool;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mAuth = FirebaseAuth.getInstance();
        createSchool=findViewById(R.id.createSchool);
        logSchool=findViewById(R.id.schoolLog);
        joinClassbtn=findViewById(R.id.joinClass);
        sp=getSharedPreferences("LogIN", Context.MODE_PRIVATE);

        createSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, createSchool.class);
                startActivity(intent);
            }
        });
        joinClassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, joinClass.class);
                startActivity(intent);
            }
        });
        logSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, schoolLogin.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        user= mAuth.getCurrentUser();
        if(user != null)
        {
            Intent intent = new Intent(Welcome.this,schoolManage.class);
            startActivity(intent);
            finish();
        }
        else{
            String toCheck = sp.getString("StdName","");
            if (!toCheck.equals("")){
                Intent intent = new Intent(Welcome.this,StudentMain.class);
                startActivity(intent);
                finish();
            }
        }

    }
}