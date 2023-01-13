package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class schoolManage extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> namesList;
    private ArrayAdapter mArrayAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference classRef,schoolRef,classAddRef;
    public  ArrayList<String> ids;
    public String idToRef;
    private ImageButton addClass;
    private ImageView logout;
    private EditText edtClass;
    private TextView lastClasstxt,nameSchool;
    private String lastClass;
    private TextView schoolName,schoolACTName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_manage);
        mAuth = FirebaseAuth.getInstance();
        schoolName=findViewById(R.id.idSchool); //for ID
        schoolACTName=findViewById(R.id.nameSchool);
        addClass=findViewById(R.id.addClassBtn);
        edtClass=findViewById(R.id.edtClass);
        lastClasstxt=findViewById(R.id.lastClasstxt);
        logout=findViewById(R.id.logoutBtn);

        listView = findViewById(R.id.ListviewClassList);
        namesList = new ArrayList<>();
        ids = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter(schoolManage.this,android.R.layout.simple_list_item_1,namesList); //see this line
        listView.setAdapter(mArrayAdapter);
        schoolRef= FirebaseDatabase.getInstance().getReference().child("school");
        classAddRef= FirebaseDatabase.getInstance().getReference().child("class");

        //school id is retrieved
        schoolRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                idToRef = snapshot.child(mAuth.getCurrentUser().getUid()).child("Id").getValue().toString();
                schoolName.setText(idToRef);
                String val = snapshot.child(mAuth.getCurrentUser().getUid()).child("Name").getValue().toString();
                schoolACTName.setText(val);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Toast.makeText(schoolManage.this, idToRef, Toast.LENGTH_SHORT).show();

        classRef = FirebaseDatabase.getInstance().getReference().child("class").child(schoolName.getText().toString());

        classRef = FirebaseDatabase.getInstance().getReference().child("school").child(mAuth.getCurrentUser().getUid()).child("Class");
        classRef.addChildEventListener(new ChildEventListener() {
         @Override
         public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
             String val=snapshot.getValue(String.class);
             namesList.add(val);
             lastClass = "C"+String.valueOf(namesList.size()+1);
             lastClasstxt.setText(lastClass);
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

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtClass.getText().toString().equals("")){
                    Toast.makeText(schoolManage.this, "Enter Class Name", Toast.LENGTH_SHORT).show();
                }
                else{
                    String tmp=lastClasstxt.getText().toString();
                    schoolRef.child(mAuth.getCurrentUser().getUid()).child("Class").child(tmp).setValue(edtClass.getText().toString());
                    classAddRef.child(schoolName.getText().toString()).child(tmp).child("ClassName").setValue(edtClass.getText().toString());
                    edtClass.setText("");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(schoolManage.this, classManage.class);
                //intent.putExtra("ReceiverId",ids.get(position));
                //intent.putExtra("ReceiveClass",namesList.get(i));  //look after this line properly
                intent.putExtra("ReceiveSchool",schoolName.getText().toString());
                intent.putExtra("ReceiveClass","C"+String.valueOf(i+1));
                startActivity(intent);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(schoolManage.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        });

    }
}