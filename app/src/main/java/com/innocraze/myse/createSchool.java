package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class createSchool extends AppCompatActivity {

    private Button create;
    private EditText name,district,edtEmail,edtPassword;
    private DatabaseReference schoolRef,generalRef,classRef;
    private FirebaseAuth mAuth;
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_school);
        Spinner spinnerStates=findViewById(R.id.state_spinner);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStates.setAdapter(adapter);
        create=findViewById(R.id.createBtn1);
        name=findViewById(R.id.schoolName);
        district=findViewById(R.id.schoolDistrict);
        edtEmail=findViewById(R.id.schoolEmail);
        edtPassword=findViewById(R.id.schoolPassword);

        ProgressDialog pg= new ProgressDialog(createSchool.this);
        pg.setTitle("Authentication");
        pg.setMessage("Account is being created in our servers....");
        schoolRef= FirebaseDatabase.getInstance().getReference().child("school");
        generalRef= FirebaseDatabase.getInstance().getReference().child("General");
        classRef = FirebaseDatabase.getInstance().getReference().child("class");
        mAuth=FirebaseAuth.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")||district.getText().toString().equals("")||
                        edtEmail.getText().toString().equals("")||
                        edtPassword.getText().toString().equals("")) {
                    Toast.makeText(createSchool.this, "All fields are Mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    pg.show();
                    generalRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            id = snapshot.child("last").getValue().toString();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {

                                Toast.makeText(createSchool.this,id, Toast.LENGTH_SHORT).show();
                                schoolRef.child(mAuth.getCurrentUser().getUid()).child("Name")
                                        .setValue(name.getText().toString());
                                schoolRef.child(mAuth.getCurrentUser().getUid()).child("Id")
                                        .setValue(id);
                                schoolRef.child(mAuth.getCurrentUser().getUid()).child("Email")
                                        .setValue(edtEmail.getText().toString());
                                schoolRef.child(mAuth.getCurrentUser().getUid()).child("Class").child("C1")
                                        .setValue("Class 1");
                                classRef.child(id).child("C1").child("ClassName").setValue("Class 1");
                                classRef.child(id).child("SchoolName").setValue(name.getText().toString()); //add schol to class data
                                classRef.child(id).child("C1").child("Circular").setValue("Nothing");

                                generalRef.child("last").setValue(Integer.parseInt(id)+1);
                                Toast.makeText(createSchool.this, name.getText().toString()+"Successfully logged In!", Toast.LENGTH_SHORT).show();
                                pg.dismiss();
                                Intent intent = new Intent(createSchool.this, schoolManage.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(createSchool.this,"Error"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                pg.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }
}