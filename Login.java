package com.example.identificationofpotholes;
import androidx.appcompat.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
    Button cirLoginButton;
    TextView cirsignup1;
    private AwesomeValidation awesomeValidation;
    ProgressDialog progressDialog;
    String EmailHolder, PasswordHolder ;
    TextView dataEmail; // a text field where the data to be sent is entered
    TextView dataPassword; // a text field where the data to be sent is entered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        cirLoginButton = (Button) findViewById(R.id.cirLoginButton);
        cirsignup1 = (TextView) findViewById(R.id.cirsignup1);
        cirLoginButton.setOnClickListener(mMyListener);
        cirsignup1.setOnClickListener(mMyListener);

        dataEmail = (TextView) findViewById(R.id.editTextEmail);
        dataPassword = (TextView) findViewById(R.id.editTextPassword);

        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        progressDialog = new ProgressDialog(LoginActivity.this);
    }
    private View.OnClickListener mMyListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId() /*to get clicked view id**/) {
                case R.id.cirLoginButton:
                    if (awesomeValidation.validate()) {
//                        Toast.makeText(LoginActivity.this, "Checking", Toast.LENGTH_LONG).show();
                        regUser();
                        Intent i = new Intent(getApplicationContext(), home.class);
                        startActivity(i);
                    }
                    break;
                case R.id.cirsignup1:
                    Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
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

        EmailHolder = dataEmail.getText().toString().trim();
        PasswordHolder = dataPassword.getText().toString().trim();
        String myurl = "http://192.168.55.106/myloginphp/login.php?email=" +
                "&email=" + EmailHolder + "&password=" + PasswordHolder;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, myurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        if (ServerResponse.equals("1")){
                            Toast.makeText(LoginActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(LoginActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(LoginActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(stringRequest);
    }
}
