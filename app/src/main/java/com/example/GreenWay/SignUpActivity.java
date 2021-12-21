package com.example.GreenWay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password, birthdate;
    Button signup;
    JSONArray users = null;
    String dayString, monthString, postBDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editCreateTextUsername);
        email = findViewById(R.id.editTextCreateEmail);
        password = findViewById(R.id.editTextCreatePassword);
        birthdate = findViewById(R.id.editTextCreateDate);
        signup = findViewById(R.id.buttonCreateAccount);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        dayString = String.valueOf(day);
                        if ( day < 10){
                            dayString = "0" + day;
                        }
                        monthString = String.valueOf(month);
                        if ( month < 10){
                            monthString = "0" + month;
                        }
                        postBDate = year+"-"+monthString+"-"+dayString;
                        String date = year+"-"+monthString+"-"+dayString;
                        birthdate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetUsers getUsers = new GetUsers();
                Intent intentCreateAcc = new Intent(SignUpActivity.this, LoginActivity.class);
                try{
                    users = getUsers.execute("https://greenwayiade.herokuapp.com/api/users").get();
                    JSONObject aux = new JSONObject(users.get(0).toString());
                    if (name.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Favor preencher o campo em vermelho", Toast.LENGTH_SHORT).show();
                        name.setHintTextColor(Color.RED);
                    }
                    if (password.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Favor preencher o campo em vermelho", Toast.LENGTH_SHORT).show();
                        password.setHintTextColor(Color.RED);
                    }
                    if (email.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Favor preencher o campo em vermelho", Toast.LENGTH_SHORT).show();
                        email.setHintTextColor(Color.RED);
                    } else {
                        Map<String, String> postData = new HashMap<>();
                        postData.put("bdate", birthdate.getText().toString());
                        postData.put("email", email.getText().toString());
                        postData.put("password", password.getText().toString());
                        postData.put("name", name.getText().toString());

                        PostData task = new PostData(postData);
                        task.execute("https://greenwayiade.herokuapp.com/api/users/created");

                        Log.e("Id Sign up activity", ""+ postData.toString());

                        startActivity(intentCreateAcc);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    users = null;
                }
            }
        });
    }
}