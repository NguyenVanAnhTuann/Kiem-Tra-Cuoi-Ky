package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Sign_In extends AppCompatActivity implements   View.OnClickListener {
    private EditText edt_email_in, edt_pass_in;
    private Button btn_signin_in;
    private Button layout_signUp;
    private FirebaseAuth mAuth;
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://myaplication-12e26-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        anhxa();
        layout_signUp.setOnClickListener(this);
        btn_signin_in.setOnClickListener(this);

    }
    public  void onClick(View v){
        switch (v.getId()){
            case R.id.buttonSignup:
                startActivity(new Intent(this, Sign_up.class));
                break;
            case R.id.buttonSignin:
                userSignIn();
                break;
        }
}

    private void userSignIn() {
        String email = edt_email_in.getText().toString().trim();
        String pass = edt_pass_in.getText().toString().trim();
        if(email.isEmpty()){
            edt_email_in.setError("Nhập Email");
            edt_email_in.requestFocus();
            return;

        } else if (pass.isEmpty()){
            edt_email_in.setError("Nhập Password");
            edt_email_in.requestFocus();

        }
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(Sign_In.this, MainActivity.class));
                        }else {
                            Toast.makeText(Sign_In.this,"Đăng nhập không thành công, vui lòng đăng nhập lại!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void anhxa() {
        btn_signin_in = findViewById(R.id.buttonSignin);
        edt_email_in = findViewById(R.id.editUser);
        edt_pass_in = findViewById(R.id.editPassword);
        layout_signUp = findViewById(R.id.buttonSignup);
    }


}
