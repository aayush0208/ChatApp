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

        bneed_account=(Button) findViewById(R.id.bcreate_account);
        b_signin=(Button) findViewById(R.id.b_signin);


      /*  newbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent =new Intent(StartActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        }); */
    }
    public void doAction(View view){
        switch (view.getId()){
            case R.id.bneed_account:{
                Intent registerIntent =new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
            case R.id.b_signin:{
                Intent loginIntent =new Intent(StartActivity.this,LoginActivity.class);
                startActivity(loginIntent);

            }

        }
    }
}
