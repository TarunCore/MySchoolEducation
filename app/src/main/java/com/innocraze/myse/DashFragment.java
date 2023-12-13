package com.innocraze.myse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DashFragment extends Fragment {
    private TextView stdNameTxt,classNameTxt,schoolNameTxt;
    private ImageView seeHomeWork,seeMarks,chatBtn,seeMyReport;
    SharedPreferences sp;
    private DatabaseReference classRef,schoolRef;
    private ImageView logout;
    private ImageView seeAnnounceBtn;
    public DashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash, container, false);
        stdNameTxt=view.findViewById(R.id.stdNameTXT);
        classNameTxt=view.findViewById(R.id.classNameTXT);
        schoolNameTxt=view.findViewById(R.id.schoolNameTXT);
        seeHomeWork=view.findViewById(R.id.seeworkBtn);
        seeMarks=view.findViewById(R.id.seeMarkBtn);
        chatBtn=view.findViewById(R.id.chatBtn);
        seeMyReport=view.findViewById(R.id.seeMyReport);
        seeAnnounceBtn=view.findViewById(R.id.seeAnnounceBtn);

        sp = requireActivity().getSharedPreferences("LogIN", Context.MODE_PRIVATE);
        String t1 = sp.getString("StdName","");
        String t2 = sp.getString("cCode","");
        String t3 = sp.getString("sCode","");

        classRef= FirebaseDatabase.getInstance().getReference().child("class").child(t3);

        classRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String sname=snapshot.child("SchoolName").getValue().toString();
                schoolNameTxt.setText(sname.toUpperCase()); // school name should be added
                String cname=snapshot.child(t2).child("ClassName").getValue().toString();
                classNameTxt.setText(cname.toUpperCase());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        seeHomeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),seeHomework.class);
                intent.putExtra("ReceiveSchool",t3.toString());  //these are unique ids
                intent.putExtra("ReceiveClass",t2.toString());
                startActivity(intent);
            }
        });
        seeMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),seeMarks.class);
                intent.putExtra("SID",t2.toString());
                startActivity(intent);
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),messaging.class);
                intent.putExtra("SName",t1);
                startActivity(intent);
            }
        });
        stdNameTxt.setText(t1.toUpperCase());
        seeMyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),myReport.class);
                intent.putExtra("SName",t1);
                intent.putExtra("ClassName",classNameTxt.getText().toString());
                startActivity(intent);
            }
        });
        seeAnnounceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),seeAnnounce.class);
                intent.putExtra("ReceiveSchool",t3);
                startActivity(intent);
            }
        });

        return view;
    }
}