package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Sign_up extends AppCompatActivity{
    private EditText edt_email_up, edt_pass_up;
    private Button btn_signup_up;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        anhxa();
        mAuth = FirebaseAuth.getInstance();
        btn_signup_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp_user();
            }
        });

    }

    private void SignUp_user() {
        String email = edt_email_up.getText().toString().trim();

        String pass = edt_pass_up.getText().toString().trim();
        if(email.isEmpty()){
            edt_email_up.setError("Nhập Email");
            edt_email_up.findFocus();
            return;

        }else if (pass.isEmpty()){
            edt_pass_up.setError("Nhập Password");
            edt_pass_up.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User( email,pass);
                            FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Sign_up.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(Sign_up.this,"Đăng ký không thành công, Đăng ký lại!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else {
                            Toast.makeText(Sign_up.this,"Đăng ký không thành công, Đăng ký lại!",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    //anh xa
    private void anhxa(){
        edt_email_up=  findViewById(R.id.editUser1);

        edt_pass_up=  findViewById(R.id.editPassword1);
        btn_signup_up=  findViewById(R.id.buttonSignup1);
    }
}