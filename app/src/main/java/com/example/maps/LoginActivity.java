package com.example.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUser, editTextPassword;
    JSONObject login = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void onClickLogin(View v){
        DownloadTaskObject task = new DownloadTaskObject();
        try {
            login = task.execute("https://greenwayiade.herokuapp.com/api/users/" + editTextUser.getText().toString() + "/" + editTextPassword.getText().toString()).get();
            Intent intentLogin = new Intent(LoginActivity.this, MapsFinal.class);
            this.startActivity(intentLogin);
        } catch (InterruptedException e) {
            e.printStackTrace();
            login = null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            login = null;
        }
    }

    public void onClickSignup(View v){
        Intent intentSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intentSignUp);
    }
}