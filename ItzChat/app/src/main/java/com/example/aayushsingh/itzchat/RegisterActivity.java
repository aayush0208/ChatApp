package com.example.aayushsingh.itzchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout reg_name, reg_email, reg_password;
    private Button bcreate_account;
    private FirebaseAuth mAuth;
    private Toolbar register_toolbar;
    //progress bar
    private ProgressDialog reg_progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //toolbar
        register_toolbar=(Toolbar)findViewById(R.id.register_app_bar);
        setSupportActionBar(register_toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        reg_progressdialog=new ProgressDialog(this);

        // firebase
        mAuth = FirebaseAuth.getInstance();
        //regsiter activity
        reg_name = (TextInputLayout) findViewById(R.id.textInput_name);
        reg_email = (TextInputLayout) findViewById(R.id.textInput_email);
        reg_password = (TextInputLayout) findViewById(R.id.textInput_password);
        bcreate_account = (Button) findViewById(R.id.bcreate_account);


        bcreate_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = reg_name.getEditText().getText().toString();
                String email = reg_email.getEditText().getText().toString();
                String password = reg_password.getEditText().getText().toString();
                if (!TextUtils.isEmpty(name)|| !TextUtils.isEmpty(email)|| !TextUtils.isEmpty(password)){
                    //progressbar
                    reg_progressdialog.setTitle("Registering User");
                    reg_progressdialog.setMessage("Please wait while we create your account!");
                    reg_progressdialog.setCanceledOnTouchOutside(false);
                    reg_progressdialog.show();
                    register_user(name, email, password);
                }

            }
        });
    }
//Get the user name ,email and password to create a database
    private void register_user(String name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    reg_progressdialog.dismiss();
                    Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    reg_progressdialog.hide();
                    Toast.makeText(RegisterActivity.this, "Can't Sign In.Please try again!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
