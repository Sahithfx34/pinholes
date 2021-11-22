package com.example.identificationofpotholes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class home extends AppCompatActivity implements View.OnClickListener {
    private CardView cirsettings,ciradd,cirabout,cirlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CardView cirsettings = findViewById(R.id.cirsettings);
        CardView ciradd = (CardView) findViewById(R.id.ciradd);
        CardView cirabout = (CardView) findViewById(R.id.cirabout);
        CardView cirlocation = (CardView) findViewById(R.id.cirlocation);
        cirsettings.setOnClickListener(this);
        ciradd.setOnClickListener(this);
        cirabout.setOnClickListener(this);
        cirlocation.setOnClickListener(this);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.cirsettings: i = new Intent(this,settings.class);
                startActivity(i);
                break;
            case R.id.ciradd : i = new Intent (this,add.class);
                startActivity(i);
                break;
            case R.id.cirabout : i = new Intent (this,about.class);
                startActivity(i);
                break;
            case R.id.cirlocation: i = new Intent (this,location.class);
                startActivity(i);
                break;
            default:
                break;

        }

    }
}
