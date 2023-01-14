package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class classManage extends AppCompatActivity {

    private String receiverClass,receiverSchool,lastNameOfStd;
    private ImageButton addStudentbtn;
    private ListView listView;
    private EditText edtStudent;
    private TextView className,lastStd;
    private ImageView homeworkBtn,circularBtn,studyBtn,shareBtn;

    private ArrayList<String> stdList;
    private ArrayAdapter mArrayAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference classRef,schoolRef,stdListRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manage);
        Intent intent = getIntent();
        receiverClass = intent.getStringExtra("ReceiveClass");
        receiverSchool = intent.getStringExtra("ReceiveSchool");
        edtStudent=findViewById(R.id.edtStudent);
        addStudentbtn=findViewById(R.id.addStudentBtn);
        className=findViewById(R.id.classNametxt);
        lastStd=findViewById(R.id.lastStdtxt);
        homeworkBtn=findViewById(R.id.btnHomeWork);
        shareBtn=findViewById(R.id.btnShare);
        studyBtn=findViewById(R.id.btnStudyMat);

        listView = findViewById(R.id.ListviewStdList);
        stdList = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter(classManage.this,android.R.layout.simple_list_item_1,stdList); //see this line
        listView.setAdapter(mArrayAdapter);
        className.setText(receiverClass);

        classRef= FirebaseDatabase.getInstance().getReference().child("class").child(receiverSchool).child(receiverClass);
        stdListRef= FirebaseDatabase.getInstance().getReference().child("class").child(receiverSchool).child(receiverClass).child("StudentList");

        stdListRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String val=snapshot.getValue(String.class);
                    stdList.add(val);
                    lastNameOfStd = "S"+String.valueOf(stdList.size()+1);
                    lastStd.setText(lastNameOfStd);
                    mArrayAdapter.notifyDataSetChanged();
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

        addStudentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtStudent.getText().toString().equals("")){
                    Toast.makeText(classManage.this, "Enter Student Name", Toast.LENGTH_SHORT).show();
                }
                else{
                    String tmp=lastStd.getText().toString();
                    classRef.child("StudentList").child(tmp).setValue(edtStudent.getText().toString());
                    classRef.child("Student").child(tmp).child("Name").setValue(edtStudent.getText().toString());
                }
            }
        });
        homeworkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(classManage.this, homework.class);
                intent.putExtra("ReceiveSchool",receiverSchool);
                intent.putExtra("ReceiveClass",receiverClass);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(classManage.this, putMarks.class);
                //intent.putExtra("ReceiverId",ids.get(position));
                //intent.putExtra("ReceiveClass",namesList.get(i));  //look after this line properly
                intent.putExtra("Class",receiverClass);
                intent.putExtra("SchoolID",receiverSchool);
                intent.putExtra("StudentID","S"+String.valueOf(i+1));
                startActivity(intent);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,receiverSchool+"\n"+receiverClass);
                shareIntent.setType("text/plain");
                shareIntent = Intent.createChooser(shareIntent,"Share via: ");
                startActivity(shareIntent);
            }
        });
    }
}