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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private TextView stdNameTxt,classNameTxt,schoolNameTxt,schoolCodeTxt,classCodeTxt,profileTxt;
    private ImageView seeHomeWork,seeMarks,chatBtn,seeMyReport;
    SharedPreferences sp;
    private DatabaseReference classRef,schoolRef;
    private Button logout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        stdNameTxt=view.findViewById(R.id.username);
        classNameTxt=view.findViewById(R.id.classOFstd);
        schoolNameTxt=view.findViewById(R.id.pSchoolName);
        classCodeTxt=view.findViewById(R.id.pClassCode);
        schoolCodeTxt=view.findViewById(R.id.pSchoolCode);
        logout=view.findViewById(R.id.LOGOUTBTN);
        profileTxt=view.findViewById(R.id.profile);
//        seeMarks=view.findViewById(R.id.seeMarkBtn);
//        chatBtn=view.findViewById(R.id.chatBtn);
//        seeMyReport=view.findViewById(R.id.seeMyReport);

        sp = requireActivity().getSharedPreferences("LogIN", Context.MODE_PRIVATE);
        String t1 = sp.getString("StdName","");
        String t2 = sp.getString("cCode","");
        String t3 = sp.getString("sCode","");
        classCodeTxt.setText("Class Code: "+ t2);
        schoolCodeTxt.setText("School Code: "+ t3);
        profileTxt.setText(t1.substring(0,2));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("sCode","");
                editor.putString("cCode","");
                String tmp = "";
                editor.putString("StdName",tmp);
                editor.putString("sID","");
                editor.commit();
                Intent intent = new Intent(getActivity(),joinClass.class);

                startActivity(intent);
                requireActivity().finish();
            }
        });
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

        stdNameTxt.setText(t1);
        return view;
    }
}