package com.example.sushil.androidmaterialtest;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageView imgView1;
    private ImageView imgView2;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.app_loginbar);
        toolbar.setLogo(R.drawable.ic_action_plant);
        setSupportActionBar(toolbar);


        imgView1 = (ImageView) findViewById(R.id.imageView1);
        imgView2 = (ImageView) findViewById(R.id.imageView2);

        imgView1.setOnClickListener(this);
        imgView2.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.imageView1) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            CustomDialogClassSecond customDialogClassSecond = new CustomDialogClassSecond(this);
            customDialogClassSecond.show();
        }
    }
}
