package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class joinClass extends AppCompatActivity {

    private EditText edtSchoolCode,edtClassCode;
    private Button btnSearch, btnJoin;
    private ListView listView;
    SharedPreferences sp;

    private ArrayList<String> stdList;
    private DatabaseReference classRef;
    private ArrayAdapter mArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);
        edtSchoolCode=findViewById(R.id.edtSchoolCode);
        edtClassCode=findViewById(R.id.edtClassCode);

        //listView=findViewById(R.id.selectStd);
        stdList=new ArrayList<>();
        mArrayAdapter = new ArrayAdapter(joinClass.this,android.R.layout.simple_list_item_1,stdList); //see this line
        //listView.setAdapter(mArrayAdapter);


        sp=getSharedPreferences("LogIN", Context.MODE_PRIVATE);

        btnSearch=findViewById(R.id.searchClassBtn);
        btnJoin=findViewById(R.id.joinClassBtn);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classRef= FirebaseDatabase.getInstance().getReference().child("class").child(edtSchoolCode.getText().toString())
                        .child(edtClassCode.getText().toString()).child("StudentList");
                classRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String val=snapshot.getValue(String.class);
                        stdList.add(val);
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
            }
        });

        Spinner spinnerStates2=findViewById(R.id.stdSpinner);  //Main spinner
        mArrayAdapter = new ArrayAdapter(joinClass.this,android.R.layout.simple_spinner_dropdown_item,stdList);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStates2.setAdapter(mArrayAdapter);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getSelected = spinnerStates2.getSelectedItem().toString();
                int getSelectedPos = spinnerStates2.getSelectedItemPosition();
                Toast.makeText(joinClass.this, getSelected, Toast.LENGTH_SHORT).show();
                String sCode=edtSchoolCode.getText().toString();
                String cCode=edtClassCode.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("sCode",sCode);
                editor.putString("cCode",cCode);
                editor.putString("StdName",getSelected);
                editor.putString("sID","S"+String.valueOf(getSelectedPos+1));
                editor.commit();
                Intent intent = new Intent(joinClass.this,student.class);
                startActivity(intent);

            }
        });
    }
}