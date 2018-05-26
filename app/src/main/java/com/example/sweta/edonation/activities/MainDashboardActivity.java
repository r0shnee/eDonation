package com.example.sweta.edonation.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sweta.edonation.OrganizationLoginActivity;
import com.example.sweta.edonation.OrganizationRegisterActivity;
import com.example.sweta.edonation.R;
import com.example.sweta.edonation.adaptersandviewholders.ListAdapter;
import com.example.sweta.edonation.pojoclasses.Organization;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    NavigationView navigationView;
    RecyclerView recyclerView;
    Toolbar toolbar;
    private List<Organization> organizationList;
    private ListAdapter adapter;
    DatabaseReference reference;
    //Button btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);


        reference = FirebaseDatabase.getInstance().getReference("OrganizationDetails");

        initComponents();
        initToolbar();
        initActionBar();
        checkwifi();
        setListener();
        initRecyclerView();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.recyclerViewOrganizationList);
       // btnAdmin = findViewById(R.id.adminBtn);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

    }

    private void initActionBar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setListener() {
        navigationView.setNavigationItemSelectedListener(this);
        //btnAdmin.setOnClickListener(this);

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }


    private void initRecyclerView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        organizationList = new ArrayList<>();

        final DatabaseReference dbOrganization = FirebaseDatabase.getInstance().
                getReference("OrganizationDetails");

        dbOrganization.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //this method executes when successful

                if (dataSnapshot.exists()) {
                    for (DataSnapshot organizationSnapshot : dataSnapshot.getChildren()) {
                        Organization org = organizationSnapshot.getValue(Organization.class);

                        int status = org.getStatus();
                        if (status == 1) {
                            organizationList.add(org);
                        }
                    }

                    adapter = new ListAdapter(MainDashboardActivity.this,
                            organizationList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //this method executes when error

            }
        });

        if (recyclerView == null) {
            Toast.makeText(this, "No Data Found!!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_registerOrg:
                Intent intent = new Intent(MainDashboardActivity.this,
                        OrganizationRegisterActivity.class);
                startActivity(intent);
                break;


            case R.id.nav_loginOrg:
                Intent intent1 = new Intent(MainDashboardActivity.this, OrganizationLoginActivity.class);
                startActivity(intent1);
                break;

            case R.id.nav_aboutApp:
                Intent intent3 = new Intent(MainDashboardActivity.this, AdminActivity.class);
                startActivity(intent3);
                break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*
    @Override
    public void onClick(View v) {

        //if (v == btnAdmin) {
            Intent intent = new Intent(MainDashboardActivity.this,
                    AdminActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);


        return cm.getActiveNetworkInfo() != null;


    }

    private void checkwifi() {

        boolean check = isNetworkConnected();
        if (check == true) {
            initComponents();
            setListener();

        } else {

            Toast toast = Toast.makeText(this, "Connect to a network",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            initComponents();
            setListener();
        }

    }
}
