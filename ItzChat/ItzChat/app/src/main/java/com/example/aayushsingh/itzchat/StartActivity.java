package com.example.aayushsingh.itzchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button bneed_account;
    private Button b_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        bneed_account=(Button) findViewById(R.id.bmake_account);
        b_signin=(Button) findViewById(R.id.b_signin);


       bneed_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent =new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(regIntent);
            }
        });
    }
    public void doAction(View view){
        switch (view.getId()){
          /*  case R.id.bmake_account:{
                Intent registerIntent =new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }*/
            case R.id.b_signin:{
                Intent loginIntent =new Intent(StartActivity.this,LoginActivity.class);
                startActivity(loginIntent);

            }

        }}

}
