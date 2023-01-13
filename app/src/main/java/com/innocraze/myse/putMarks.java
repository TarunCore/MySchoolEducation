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

public class putMarks extends AppCompatActivity {

    String stdID,classID,schoolID;
    private DatabaseReference classRef;
    private Button addMarkBtn;
    private EditText edtExam,edtMark,edtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_marks);
        Intent intent = getIntent();
        classID = intent.getStringExtra("Class");
        schoolID = intent.getStringExtra("SchoolID");
        stdID = intent.getStringExtra("StudentID");
        addMarkBtn=findViewById(R.id.addMarkBtn);
        edtExam=findViewById(R.id.edtExamName);
        edtMark=findViewById(R.id.edtMark);
        edtTotal=findViewById(R.id.edtTotal);
        classRef= FirebaseDatabase.getInstance().getReference().child("class").child(schoolID).child(classID).child("Student").child(stdID);
        addMarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toPut=edtExam.getText().toString()+">"+edtMark.getText().toString()+">"+edtTotal.getText().toString();
                Toast.makeText(putMarks.this,toPut,Toast.LENGTH_SHORT).show();
                classRef.child("Marks").child(edtExam.getText().toString()).setValue(toPut);
            }
        });



    }
}