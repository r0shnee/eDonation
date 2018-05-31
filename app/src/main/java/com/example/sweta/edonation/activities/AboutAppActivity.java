package com.example.sweta.edonation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sweta.edonation.R;

public class AboutAppActivity extends AppCompatActivity implements View.OnClickListener{

    private android.support.v7.widget.Toolbar toolbar;
     private Button contact_us_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        initComponents();
        initToolbar();
        initListener();
    }

    private void initComponents() {
        toolbar=findViewById(R.id.toolBar);
        contact_us_btn=findViewById(R.id.contact_us_btn);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("e-Donation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(AboutAppActivity.this,
                    MainDashboardActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    public void initListener(){
        contact_us_btn.setOnClickListener(this);
    }

    public void onClick(View v){
        //sending message through gmail
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"eDonation@gmail.com"});
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "send"));

    }
}