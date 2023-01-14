package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class messaging extends AppCompatActivity {

    private String Name;
    private DatabaseReference classRef;
    private MessageAdapter myMessageAdapter;
    private ArrayList<String> messages;
    private RecyclerView mRecyclerView;
    private EditText msg;
    private ImageButton sendBtn;
    private Toolbar mToolBar;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        msg=findViewById(R.id.edtMessage);
        sendBtn=findViewById(R.id.btnSendMsg);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mToolBar=findViewById(R.id.myToolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Class Chat");

        sp = getSharedPreferences("LogIN", Context.MODE_PRIVATE);
        String t1 = sp.getString("StdName","");
        String t2 = sp.getString("cCode","");
        String t3 = sp.getString("sCode","");
        classRef= FirebaseDatabase.getInstance().getReference().child("class").child(t3).child(t2).child("Chats");

        mRecyclerView = findViewById(R.id.MsgrecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(messaging.this));
        messages = new ArrayList<>();
        myMessageAdapter= new MessageAdapter(messaging.this,messages);
        mRecyclerView.setAdapter(myMessageAdapter);

        classRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String val=snapshot.getValue(String.class);
                messages.add(val);
                myMessageAdapter.notifyItemChanged(messages.size()-1);
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
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(msg.getText().toString().equals("")){
                    Toast.makeText(messaging.this,"Enter the message",Toast.LENGTH_SHORT).show();
                }
                else{
                    classRef.child(String.valueOf(messages.size()+1)).setValue(t1+">"+msg.getText().toString());
                    msg.setText("");
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}