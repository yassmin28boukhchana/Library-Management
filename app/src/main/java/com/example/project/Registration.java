package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
EditText email,name,password ;
private FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth=FirebaseAuth.getInstance() ;
       /* if(auth.getCurrentUser() != null){
            startActivity(new Intent(Registration.this,MainActivity.class));
            finish();
        }*/
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
    }

    public void SignIn(View view) {
        startActivity(new Intent(Registration.this,Login.class));
    }

    public void SingUp(View view) {
        String userName = name.getText().toString() ;
        String userEmail = email.getText().toString() ;
        String userPassword = password.getText().toString() ;
        if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this, "Please Enter All fields", Toast.LENGTH_SHORT).show();
        }
        if(userPassword.length() < 6){
            Toast.makeText(this, "Password too short , Enter minimum 6 characters", Toast.LENGTH_SHORT).show();
        }
        auth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Registration.this,"successfully registered",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registration.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(Registration.this,MainActivity.class));
                }
            }
        });

    }
}