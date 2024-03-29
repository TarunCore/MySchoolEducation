package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class schoolLogin extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtEmail ,edtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_login);
        btnLogin = findViewById(R.id.logIN);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword =findViewById(R.id.edtPassword);
        final ProgressDialog pg = new ProgressDialog(schoolLogin.this);
        pg.setTitle("Authentication");
        pg.setMessage("Please wait until authentication finishes");
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString();

                if(email.equals("")|| password.equals(""))
                {
                    Toast.makeText(schoolLogin.this,"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }
                else {
                    pg.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                pg.dismiss();
                                Toast.makeText(schoolLogin.this,"Successfully Signed in",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(schoolLogin.this,schoolManage.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                pg.dismiss();
                                Toast.makeText(schoolLogin.this,"error :"+task.getException().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}