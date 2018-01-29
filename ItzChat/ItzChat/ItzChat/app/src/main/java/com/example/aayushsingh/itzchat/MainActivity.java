package com.example.aayushsingh.itzchat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
   private Toolbar main_toolbar;
   private TabLayout main_tablayout;
   private ViewPager mainViewPager;
   private SectionPagerAdapter main_SectionPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //firebase
        mAuth = FirebaseAuth.getInstance();
        //toolbar
        main_toolbar=(Toolbar)findViewById(R.id.main_app_bar);
        setSupportActionBar(main_toolbar);
        getSupportActionBar().setTitle("ItzChat");
        //tabs
        mainViewPager=(ViewPager) findViewById(R.id.main_tabPager);
        main_SectionPagerAdapter =new SectionPagerAdapter(getSupportFragmentManager());

        mainViewPager.setAdapter(main_SectionPagerAdapter);
        main_tablayout=(TabLayout)findViewById(R.id.main_tab);
        main_tablayout.setupWithViewPager(mainViewPager);



    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){

            GotoStart();
        }


    }
    private void GotoStart(){
        Intent startIntent = new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);

         /*switch (item.getItemId()){
             case R.id.blog_out:
                 FirebaseAuth.getInstance().signOut();
                 GotoStart();*/
                 /*
             case R.id.baccount_setting:
                 Intent accountsetting =new Intent(MainActivity.this,SettingsActivity.class);
                 startActivity(accountsetting);
                 finish();*/
        if(item.getItemId()==R.id.blog_out){
             FirebaseAuth.getInstance().signOut();
             GotoStart();
         }
         if(item.getItemId()==R.id.baccount_setting){
             Intent accountsetting =new Intent(MainActivity.this,SettingsActivity.class);
             startActivity(accountsetting);

         }

        return true;
    }
}
