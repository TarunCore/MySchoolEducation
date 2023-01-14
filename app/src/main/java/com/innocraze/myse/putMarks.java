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
    private Button addMarkBtn,addRepBtn;
    private EditText edtExam,edtMark,edtTotal,edtRemarks,edtReport;

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
        edtRemarks=findViewById(R.id.edtRemark);
        edtReport=findViewById(R.id.edtReport);
        addRepBtn=findViewById(R.id.addReport);
        classRef= FirebaseDatabase.getInstance().getReference().child("class").child(schoolID).child(classID).child("Student").child(stdID);
        addMarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtExam.getText().toString().equals("")||edtMark.getText().toString().equals("")||
                        edtTotal.getText().toString().equals("")) {
                    Toast.makeText(putMarks.this, "Three fields are Mandatory", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(edtRemarks.getText().toString().equals("")){
                        String toPut = edtExam.getText().toString() + ">" + edtMark.getText().toString() + ">" + edtTotal.getText().toString();
                        Toast.makeText(putMarks.this, toPut, Toast.LENGTH_SHORT).show();
                        classRef.child("Marks").child(edtExam.getText().toString()).setValue(toPut);
                    }
                    else{
                        String toPut = edtExam.getText().toString() + ">" + edtMark.getText().toString() + ">" + edtTotal.getText().toString()+ ">" + edtRemarks.getText().toString();
                        Toast.makeText(putMarks.this, toPut, Toast.LENGTH_SHORT).show();
                        classRef.child("Marks").child(edtExam.getText().toString()).setValue(toPut);
                    }

                }
            }
        });
        addRepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtReport.getText().toString().equals("")){
                    Toast.makeText(putMarks.this, "Enter report", Toast.LENGTH_SHORT).show();
                }
                else{
                    classRef.child("Report").setValue(edtReport.getText().toString());
                }
            }
        });


    }
}