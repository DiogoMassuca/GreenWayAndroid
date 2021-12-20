package com.example.GreenWay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUser, editTextPassword;
    JSONArray logins = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void onClickLogin(View v){
        DownloadTaskArray task = new DownloadTaskArray();
        ArrayList<String>userNames = new ArrayList<>();
        ArrayList<String>userPasswords = new ArrayList<>();

        try {
            logins = task.execute("https://greenwayiade.herokuapp.com/api/users/").get();
            Intent intentLogin = new Intent(LoginActivity.this, ProfileActivity.class);

            for (int i = 0; i < logins.length(); i++){
                userNames.add(logins.getJSONObject(i).getString("name"));
                userPasswords.add(logins.getJSONObject(i).getString("password"));

                if (editTextUser.getText().toString().equals(userNames.get(i)) && editTextPassword.getText().toString().equals(userPasswords.get(i)))
                    this.startActivity(intentLogin);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            logins = null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            logins = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClickSignup(View v){
        Intent intentSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intentSignUp);
    }
}