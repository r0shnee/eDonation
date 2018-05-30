package com.example.sweta.edonation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.sweta.edonation.R;
import com.example.sweta.edonation.pojoclasses.CurrentlyLooking;
import com.example.sweta.edonation.pojoclasses.Organization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OrganizationRegisterActivity extends AppCompatActivity
        implements View.OnClickListener {


    Toolbar toolbar;

    EditText orgname, orgemail, orglocation, orgphone, orgwebsite, orgpan,
            orgPassword, describeItems;
    String orgnameString, orgemailString, orglocationString, orgwebsiteString,
            orgPasswordString, orgDescribeItemsString;

    int orgphoneInt, orgpanInt, status = 0;
    CheckBox checkFood, checkClothes, checkBooks, checkStationery;
    private boolean foodBoolean;
    private boolean clothesBoolean;
    private boolean booksBoolean;
    private boolean stationeryBoolean;
    Button orgregister;
    DatabaseReference databaseOrganization;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_register);

        initComponent();
        initListeners();
        initToolbar();


        firebaseAuth = FirebaseAuth.getInstance();


        databaseOrganization = FirebaseDatabase.getInstance().
                getReference("OrganizationDetails");

    }

    private void initListeners() {
        orgregister.setOnClickListener(this);
    }

    private void initComponent() {
        toolbar = findViewById(R.id.toolBar);
        orgname = findViewById(R.id.orgName);
        orgemail = findViewById(R.id.orgEmail);
        orgPassword = findViewById(R.id.orgPassword);

        //orgConfirmPassword  = findViewById(R.id.orgConfirmPassword);

        orglocation = findViewById(R.id.orgnLocation);
        orgphone = findViewById(R.id.orgnPhone);
        orgwebsite = findViewById(R.id.orgnWebsite);
        orgpan = findViewById(R.id.orgnPan);
        checkFood = findViewById(R.id.food_checkbox);
        checkClothes = findViewById(R.id.clothes_checkbox);
        checkBooks = findViewById(R.id.books_checkbox);
        checkStationery = findViewById(R.id.stationery_checkbox);
        describeItems = findViewById(R.id.describeItems);
        orgregister = findViewById(R.id.registerBtn);


    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register Organization");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(OrganizationRegisterActivity.this,
                    MainDashboardActivity.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url);
        if (m.matches())
            return true;
        else
            return false;
    }


    @Override
    public void onClick(View v) {


        String phone;
        String panNo;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        orgnameString = orgname.getText().toString().trim();

        if (orgnameString.equals("")) {

            orgname.setError("Organization name cannot be empty");


        } else {
            orgemailString = orgemail.getText().toString().trim();
            if (orgemailString.equals("")) {

                orgemail.setError("Organization email cannot be empty");

            } else if (orgemailString.matches(emailPattern)) {

                orgPasswordString = orgPassword.getText().toString().trim();

                if (orgPasswordString.equals("")) {
                    orgPassword.setError("Password cannot be empty");
                } else if (orgPasswordString.length() <= 8){
                    orgPassword.setError("Password cannot be less than eight characters");

                } else if (orgPasswordString.contains("a-zA-Z1-9")) {
                    orgPassword.setError("Enter pasword containing numbers and alphabets");

                } else {
                    orglocationString = orglocation.getText().toString().trim();
                    if (orglocationString.equals("")) {
                        //Toast.makeText(this, "Organization location cannot be empty", Toast.LENGTH_SHORT).show();
                        orglocation.setError("Organization location cannot be empty");
                    } else {
                        phone = orgphone.getText().toString().trim();

                        if (phone.equals("")) {
                            //Toast.makeText(this, "Organization phone cannot be empty", Toast.LENGTH_SHORT).show();
                            orgphone.setError("Organization phone cannot be empty");

                        } else if (phone.length() != 10 && phone.length() != 7) {

                            //Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                            orgphone.setError("Enter valid number");

                        } else {
                            try {
                                orgphoneInt = Integer.parseInt(orgphone.getText().toString());
                            } catch (Exception e) {
                                orgphone.setError("Enter valid number");
                            }

                            orgwebsiteString = orgwebsite.getText().toString().trim();
                            boolean flag = isValidUrl(orgwebsiteString);
                            if (orgwebsiteString.equals("")) {
                                //Toast.makeText(this, "Organization website cannot be empty", Toast.LENGTH_SHORT).show();
                                orgwebsite.setError("Organization website cannot be empty");
                            } else if (flag == false) {
                                orgwebsite.setError("Invalid website");

                            } else {
                                panNo = orgpan.getText().toString();
                                if (panNo.equals("")) {
                                    orgpan.setError("Organization pan cannot be empty");
                                    //Toast.makeText(this, "Organization PAN No. cannot be empty", Toast.LENGTH_SHORT).show();
                                } else {
                                    orgpanInt = Integer.parseInt(orgpan.getText().toString());

                                    if (checkFood.isChecked()) {
                                        foodBoolean = true;
                                        //= "," + "Stationery"Log.i("food", currentlyLooking);
                                    }

                                    if (checkBooks.isChecked()) {

                                        booksBoolean = true;

                                        booksBoolean= true;

                                        //Log.i("clothes", currentlyLooking);
                                    }

                                    if (checkClothes.isChecked()) {

                                        clothesBoolean = true;
                                    }

                                    if (checkStationery.isChecked()) {
                                        stationeryBoolean = true;

                                        clothesBoolean =true;
                                    }

                                    if (checkStationery.isChecked()) {
                                        stationeryBoolean =true;

                                    }

                                    orgDescribeItemsString = describeItems.getText().toString();


                                    //this method creates user on the console on the basis of password and email given
                                    firebaseAuth.createUserWithEmailAndPassword(orgemailString, orgPasswordString)
                                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        //registered
                                                    }
                                                }
                                            });


                                    String orgId = databaseOrganization.push().getKey();


                                    CurrentlyLooking currentlyLooking=new CurrentlyLooking(foodBoolean,
                                            clothesBoolean,booksBoolean,stationeryBoolean);
                                    Organization org = new Organization(orgId,
                                            orgnameString, orgemailString, orgPasswordString,
                                            orglocationString, orgphoneInt, orgwebsiteString,
                                            orgpanInt,currentlyLooking,orgDescribeItemsString, status);

                                    databaseOrganization.child(orgId).setValue(org);
                                    Intent intent = new Intent(
                                            OrganizationRegisterActivity.this,
                                            OnVerifyActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }

                        }
                    }

                }
            } else {
                orgemail.setError("enter valid email");
                //Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
            }
        }
    }
}