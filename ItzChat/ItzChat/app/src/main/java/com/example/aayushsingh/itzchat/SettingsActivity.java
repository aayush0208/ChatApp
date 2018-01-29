package com.example.aayushsingh.itzchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private CircleImageView settings_image;
    private Button b_status_j;
    private Button b_image_j;
    private TextView Name;
    private TextView Status;
    private DatabaseReference UserDatabse;
    private FirebaseUser CurrentUser;
    private StorageReference ImageStorage;
    //
    private static final int GALLERY_PICK=1;
    private static final int MAX_LENGTH=100;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings_image=(CircleImageView)findViewById(R.id.setting_img);
        b_status_j=(Button) findViewById(R.id.b_changestatus);
        b_image_j=(Button) findViewById(R.id.b_changeimage);
        Name=(TextView) findViewById(R.id.tv_username);
        Status=(TextView) findViewById(R.id.tv_status);
        //Initialisze storage refrence
        ImageStorage= FirebaseStorage.getInstance().getReference();

        //onClick Listener

        b_status_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String status_value= Status.getText().toString();
                Intent statusintent=new Intent(SettingsActivity.this,StatusActivity.class);
                statusintent.putExtra("status_value", status_value);
                startActivity(statusintent);
            }
        });

        //read daata from database
        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();//read user data
        String user_uid=CurrentUser.getUid();//reads user uid

        UserDatabse=FirebaseDatabase.getInstance().getReference().child("Users").child(user_uid);

        UserDatabse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                String thumb=dataSnapshot.child("thumb_image").getValue().toString();

                Name.setText(name);
                Status.setText(status);
                Picasso.with(SettingsActivity.this).load(image).into(settings_image);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        b_image_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SettingsActivity.this);

            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==GALLERY_PICK && resultCode==RESULT_OK){
            Uri imageuri = data.getData();
            CropImage.activity(imageuri)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //progressDialog
                progressDialog=new ProgressDialog(SettingsActivity.this);
                progressDialog.setTitle("Uploading Image");
                progressDialog.setMessage("Please wait while your image is Uploading");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Uri resultUri = result.getUri();
                final String current_user_id= CurrentUser.getUid();
                //Uplaoding file to the firebase storage
                StorageReference filepath = ImageStorage.child("profile_images").child(current_user_id+".jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            String downlaod_url=task.getResult().getDownloadUrl().toString();
                            UserDatabse.child("image").setValue(downlaod_url)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(SettingsActivity.this,"Uploading Successful",Toast.LENGTH_LONG)
                                                .show();
                                    }

                                }
                            });

                        }else {

                            Toast.makeText(SettingsActivity.this,"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
        // Random String Generator to give random name to uploaded images
   /* public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }*/
}
