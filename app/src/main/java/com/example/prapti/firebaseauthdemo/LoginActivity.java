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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity {
    EditText email,pwd;
    Button btn;
    TextView txt;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        btn = findViewById(R.id.btn);
        txt = findViewById(R.id.txt);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

                if( mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "You Are Logged In...", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this , HomeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();

                }
            }
        };
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
                    Toast.makeText(LoginActivity.this, "Fields are empty....", Toast.LENGTH_SHORT).show();
                }

                else if(!(emailid.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(emailid,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Error , Please Login Again...", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome = new Intent(LoginActivity.this , HomeActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
