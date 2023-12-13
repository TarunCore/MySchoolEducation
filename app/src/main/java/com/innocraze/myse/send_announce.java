package com.innocraze.myse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class send_announce extends AppCompatActivity {

    private String receiverSchool;
    private Button sendAnnounceBtn,uploadImgBtn;
    private EditText edtAnnounceTxt, edtAnnounceLink;
    private static final int PICK_IMAGE_REQUEST = 1;
    private TextView alertImgTxt;
    private Uri imageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView cloudImg;
    private Switch switchToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_announce);
        Intent intent = getIntent();
        receiverSchool = intent.getStringExtra("ReceiveSchool");
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("class").child(receiverSchool).child("Announces");
        edtAnnounceTxt=findViewById(R.id.edtAnnounce);
        sendAnnounceBtn=findViewById(R.id.sendAnnounceBtn);
        uploadImgBtn=findViewById(R.id.uploadImgBtn);
        cloudImg=findViewById(R.id.cloudImg);
        alertImgTxt=findViewById(R.id.alertImgTxt);
        //uploadImgBtn.setEnabled(false);
        final ProgressDialog pg = new ProgressDialog(send_announce.this);
        pg.setTitle("Sending Announcement");
        pg.setMessage("Just a moment");
        displayImage();
        switchToggle = findViewById(R.id.switch1);

        switchToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uploadImgBtn.setEnabled(true);
                } else {
                    uploadImgBtn.setEnabled(false);
                    imageUri=null;
                    alertImgTxt.setVisibility(View.GONE);
                }
            }
        });


        uploadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    uploadImage();
            }
        });
        sendAnnounceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String announceTxt=edtAnnounceTxt.getText().toString();
                if(!announceTxt.equals("")){
                    if(switchToggle.isChecked() && imageUri==null){
                        Toast.makeText(send_announce.this,"Photo not selected",Toast.LENGTH_SHORT).show();
                    }else{
                        pg.show();
                        uploadFile(announceTxt,pg);
                    }

                }else{
                    Toast.makeText(send_announce.this,"Enter Announcement",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void uploadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            alertImgTxt.setVisibility(View.VISIBLE);
            Toast.makeText(send_announce.this,"Photo has been selected",Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadFile(String announceTxt,ProgressDialog pg) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Task<Uri> downloadUrlTask = taskSnapshot.getStorage().getDownloadUrl();
                        downloadUrlTask.addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                String downloadUrl = task.getResult().toString();
                                Upload upload = new Upload(downloadUrl);
                                String uploadId = databaseReference.push().getKey();
                                databaseReference.child(uploadId).child("imgUrl").setValue(downloadUrl);
                                databaseReference.child(uploadId).child("info").setValue(announceTxt);
                                databaseReference.child(uploadId).child("uDate").setValue(getDate().toString());
                                pg.dismiss();
                            }
                        });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(send_announce.this,"Image Upload Failure",Toast.LENGTH_SHORT).show();
                    });
        }else{
            String uploadId = databaseReference.push().getKey();
            databaseReference.child(uploadId).child("imgUrl").setValue("N");
            databaseReference.child(uploadId).child("info").setValue(announceTxt);
            databaseReference.child(uploadId).child("uDate").setValue(getDate().toString());
            Toast.makeText(send_announce.this,"Announcement Sent",Toast.LENGTH_SHORT).show();
            pg.dismiss();
        }
    }

    private void displayImage() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = postSnapshot.child("imageUrl").getValue(String.class);
                    if (imageUrl != null) {
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL);

                        Glide.with(send_announce.this)
                                .load(imageUrl)
                                .apply(options)
                                .into(cloudImg);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
    private String getDate(){
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }

}