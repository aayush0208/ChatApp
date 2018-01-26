package com.example.aayushsingh.itzchat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {
private Toolbar statustoolbar;
private Button bstatus;
private DatabaseReference statusDatabase;
private FirebaseUser CurrentUser;
private TextInputLayout statustext;
private ProgressDialog statusprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        //show updated status in thus activity but fetching the data from settings activity
        String status_value=getIntent().getStringExtra("status_value");

        //firebase get database
        CurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid=CurrentUser.getUid();
        statusDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        //toolbar
        statustoolbar=(Toolbar) findViewById(R.id.status_appbar);
        setSupportActionBar(statustoolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statustext=(TextInputLayout) findViewById(R.id.status_input);
        bstatus=(Button) findViewById(R.id.b_savechanges);
        statustext.getEditText().setText(status_value);

         //Change Status Listener
        bstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //progressDialog
                statusprogress=new ProgressDialog((StatusActivity.this));
                statusprogress.setTitle("Saving Changes");
                statusprogress.setMessage("Please wait while we update your status");
                statusprogress.show();

                String status= statustext.getEditText().getText().toString();

                statusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            statusprogress.dismiss();
                        } else{
                            Toast.makeText(getApplicationContext(),"Unable to save changes,Please try again!",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });

    }
}
