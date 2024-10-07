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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email,password ;
    private FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance() ;
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

    }

    public void SingIn(View view) {
        String userEmail = email.getText().toString() ;
        String userPassword = password.getText().toString() ;
        if( userEmail.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this, "Please Enter All fields", Toast.LENGTH_SHORT).show();
        }
        if(userPassword.length() < 6){
            Toast.makeText(this, "Password too short , Enter minimum 6 characters", Toast.LENGTH_SHORT).show();
        }
        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   Toast.makeText(Login.this,"Login successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,MainActivity.class));
                }
                else {
                    Toast.makeText(Login.this,"Error!"+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void SignUp(View view) {
        startActivity(new Intent(Login.this,Registration.class));
    }
}