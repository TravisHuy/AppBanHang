package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText email,password;
    TextView regiseter,forgotPassword;
    ImageView google,facebook,twitter;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        anhXa();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();

            }
        });
        regiseter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
       forgotPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               resetPassword();
           }
       });
       google.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(LoginActivity.this,GoogleSignInActivity.class);
               startActivity(intent);
           }
       });
       facebook.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(LoginActivity.this,FacebookAuthActivity.class);
               startActivity(intent);
           }
       });
       twitter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(LoginActivity.this,TwitterSignInActivity.class);
               startActivity(intent);
           }
       });
    }

    private void resetPassword() {
        String userEmail=email.getText().toString().trim();
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(LoginActivity.this,"Vui lòng nhập email",Toast.LENGTH_SHORT).show();
            return;
        }
        auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Yêu cầu khôi phục mật khẩu đã được gửi đến email của bạn",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Khôi phục mật khẩu thât bại" +task.getException(),Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    private void loginUser() {
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(LoginActivity.this,"Vui lòng nhập email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(LoginActivity.this,"Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<6){
            Toast.makeText(LoginActivity.this,"Mật khẩu phải hơn 6 kí tự", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Đăng nhập thất bại"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void anhXa() {
        login=findViewById(R.id.button_login);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        regiseter=findViewById(R.id.login_register);
        forgotPassword=findViewById(R.id.forget_password);
        google=findViewById(R.id.btn_google);
        facebook=findViewById(R.id.btn_facebook);
        twitter=findViewById(R.id.btn_twitter);
    }
}