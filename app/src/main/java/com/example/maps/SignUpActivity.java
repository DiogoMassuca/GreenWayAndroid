package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onClickCreateAcc(View v){
        Intent intentCreateAcc = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intentCreateAcc);
    }
}