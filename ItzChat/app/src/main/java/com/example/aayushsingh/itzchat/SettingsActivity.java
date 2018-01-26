package com.example.aayushsingh.itzchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private CircleImageView settings_image;
    private Button b_status_j;
    private Button b_image_j;
    private TextView Name;
    private TextView Status;
    private DatabaseReference UserDatabse;
    private FirebaseUser CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings_image=(CircleImageView)findViewById(R.id.setting_img);
        b_status_j=(Button) findViewById(R.id.b_changestatus);
        b_image_j=(Button) findViewById(R.id.b_changeimage);
        Name=(TextView) findViewById(R.id.tv_username);
        Status=(TextView) findViewById(R.id.tv_status);
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
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
