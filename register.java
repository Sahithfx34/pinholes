package com.example.identificationofpotholes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    Button cirRegistrationButton;
    TextView cirloginButton;
    private AwesomeValidation awesomeValidation;
    TextView dataName; // a text field to display the request response
    TextView dataEmail; // a text field where the data to be sent is entered
    TextView dataMobile; // a text field where the data to be sent is entered
    TextView dataPassword; // a text field where the data to be sent is entered
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String NameHolder, EmailHolder, MobileHolder, PasswordHolder ;

    // Storing server url into String variable.
    String HttpUrl = "http://192.168.55.106/myloginphp/signup.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        cirRegistrationButton = (Button) findViewById(R.id.cirRegistrationButton);
        cirloginButton = (TextView) findViewById(R.id.cirloginButton);
        cirRegistrationButton.setOnClickListener(mMyListener);
        cirloginButton.setOnClickListener(mMyListener);

        dataName = (TextView) findViewById(R.id.editTextName);
        dataEmail = (TextView) findViewById(R.id.editTextEmail);
        dataMobile = (TextView) findViewById(R.id.editTextmobile);
        dataPassword = (TextView) findViewById(R.id.editTextPassword);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.editTextmobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);
        awesomeValidation.addValidation(this, R.id.editTextPassword, regexPassword, R.string.passworderror);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(RegistrationActivity.this);

        progressDialog = new ProgressDialog(RegistrationActivity.this);
    }
    private View.OnClickListener mMyListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cirRegistrationButton:
                    if (awesomeValidation.validate()) {
                        regUser();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                    break;
                case R.id.cirloginButton:
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    break;
                default:
                    break;
            }
        }
    };
    public void regUser(){
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        NameHolder = dataName.getText().toString().trim();
        EmailHolder = dataEmail.getText().toString().trim();
        MobileHolder = dataMobile.getText().toString().trim();
        PasswordHolder = dataPassword.getText().toString().trim();

        String myurl = "http://192.168.55.106/myloginphp/signup.php?name=" + NameHolder +
                "&email=" + EmailHolder + "&mobile="+MobileHolder + "&password=" + PasswordHolder;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, myurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing response message coming from server.
                        Toast.makeText(RegistrationActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(RegistrationActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(stringRequest);
    }
}
