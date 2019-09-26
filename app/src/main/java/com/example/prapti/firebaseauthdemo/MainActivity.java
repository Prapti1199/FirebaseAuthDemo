package com.example.prapti.firebaseauthdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email,pwd;
    Button btn;
    TextView txt;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        btn = findViewById(R.id.btn);
        txt = findViewById(R.id.signin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid = email.getText().toString();
                String password = pwd.getText().toString();
                if(emailid.isEmpty()){
                    email.setError("Please enter email id");
                    email.requestFocus();
                }
                else if(password.isEmpty()){
                    pwd.setError("Please enter your password");
                    pwd.requestFocus();
                }

                else if(emailid.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty....", Toast.LENGTH_SHORT).show();
                }

                else if(!(emailid.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "SignUp Unsuccessful , Please Try Again...", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
