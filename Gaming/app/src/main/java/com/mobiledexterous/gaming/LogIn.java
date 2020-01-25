package com.mobiledexterous.gaming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {

    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        phone = (TextView) findViewById(R.id.phonenoe);

        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GameUtilities.patientId = phone.getText().toString();
                Log.e("login","id "+phone.getText().toString());
                Intent myIntent = new Intent(LogIn.this,
                        ResearchHome.class);
                startActivity(myIntent);
            }
        });
    }
}
