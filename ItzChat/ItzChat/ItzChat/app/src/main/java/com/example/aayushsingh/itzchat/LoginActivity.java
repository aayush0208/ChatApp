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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Toolbar login_toolbar;
    private Button blogin;
    private TextInputLayout log_email;
    private TextInputLayout log_password;
    private FirebaseAuth mAuth;
    private ProgressDialog logprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //toolbar
        login_toolbar=(Toolbar)findViewById(R.id.login_app_bar);
        setSupportActionBar(login_toolbar);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //firebase sign in existing user
        mAuth = FirebaseAuth.getInstance();
        //progress Dialog

        logprogressDialog =new ProgressDialog(this);

        ////////////////////////////////////////////
        blogin =(Button)findViewById(R.id.blog_in);
        log_email=(TextInputLayout)findViewById(R.id.login_email);
        log_password=(TextInputLayout)findViewById(R.id.logintext_password);

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String logemail = log_email.getEditText().getText().toString();
                String logpassword=log_password.getEditText().getText().toString();

                if(!TextUtils.isEmpty(logemail) || !TextUtils.isEmpty(logpassword)){

                    logprogressDialog.setTitle("Logging In");
                    logprogressDialog.setMessage("Please wait while we check your credentials!");
                    logprogressDialog.setCanceledOnTouchOutside(false);
                    logprogressDialog.show();

                    loginuser(logemail,logpassword);

                }
            }
        });


    }

    private void loginuser(String logemail, String logpassword) {

        mAuth.signInWithEmailAndPassword(logemail,logpassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            logprogressDialog.dismiss();

                            Intent startintent = new Intent(LoginActivity.this,MainActivity.class);
                            startintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(startintent);
                            finish();
                        }
                        else{
                            logprogressDialog.hide();
                            Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }


}
