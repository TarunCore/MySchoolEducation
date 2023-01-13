package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class student extends AppCompatActivity {

    private TextView stdNameTxt,classNameTxt,schoolNameTxt;
    private ImageView seeHomeWork,seeMarks;
    SharedPreferences sp;
    private DatabaseReference classRef,schoolRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        stdNameTxt=findViewById(R.id.stdNameTXT);
        classNameTxt=findViewById(R.id.classNameTXT);
        schoolNameTxt=findViewById(R.id.schoolNameTXT);
        seeHomeWork=findViewById(R.id.seeworkBtn);
        seeMarks=findViewById(R.id.seeMarkBtn);

        sp = getSharedPreferences("LogIN", Context.MODE_PRIVATE);
        String t1 = sp.getString("StdName","");
        String t2 = sp.getString("cCode","");
        String t3 = sp.getString("sCode","");
//        Toast.makeText(student.this, t2, Toast.LENGTH_SHORT).show();
//        Toast.makeText(student.this, t3, Toast.LENGTH_SHORT).show();

        classRef= FirebaseDatabase.getInstance().getReference().child(t3).child(t2);

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String sname=snapshot.child("SchoolName").getValue().toString();
//                schoolNameTxt.setText(sname); // school name should be added
//                String cname=snapshot.child("ClassName").getValue().toString();
//                classNameTxt.setText(cname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        seeHomeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student.this,seeHomework.class);

                intent.putExtra("ReceiveSchool",t3.toString());  //these are unique ids
                intent.putExtra("ReceiveClass",t2.toString());
                startActivity(intent);
            }
        });

        stdNameTxt.setText(t1);


    }
}