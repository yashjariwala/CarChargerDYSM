package com.dysm.carchargerdysm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends MainActivity {
    private TextView loginemail, loginpassword;
    private FirebaseAuth firebasAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        firebasAuth=FirebaseAuth.getInstance();
        TextView registerrnowtext = findViewById(R.id.registernoetextloginpage);
        registerrnowtext.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Hello1" , Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        TextView forgotpassword = findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Hello1" , Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
            }
        });
        loginemail=findViewById(R.id.loginemail);
        loginpassword=findViewById(R.id.loginpassword);
        Button loginbutton =findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_email = loginemail.getText().toString();
                String login_password=loginpassword.getText().toString();
                if(TextUtils.isEmpty(login_email)|TextUtils.isEmpty(login_password)){
                    Toast.makeText(LoginActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }else{
                    login(login_email,login_password);
                }

            }
        });

    }

    private void login(String login_email, String login_password) {
        firebasAuth.signInWithEmailAndPassword(login_email,login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
             //   Toast.makeText(LoginActivity.this,"Yash is Great!  " +
               //         "Login Sucess",Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this,qrScan.class));
            }
            else{
                Toast.makeText(LoginActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
            }
            }

        });
    }

}
