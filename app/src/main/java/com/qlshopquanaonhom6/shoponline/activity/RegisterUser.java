package com.qlshopquanaonhom6.shoponline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.qlshopquanaonhom6.shoponline.R;
import com.qlshopquanaonhom6.shoponline.model.User;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner,registerUser;
    private TextInputEditText editTextFullName,eidtTextAge,eidtTextEmail,eidtTextPassword;
    private View progressBar;

    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = findViewById(R.id.fullName);
        eidtTextAge = findViewById(R.id.age);
        eidtTextEmail = findViewById(R.id.email);
        eidtTextPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }
    private void registerUser(){
        String email = eidtTextEmail.getText().toString().trim();
        String password = eidtTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = eidtTextAge.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextFullName.setError("full name is required!");
            editTextFullName.requestFocus();
            return;
        }
        if (age.isEmpty()){
            eidtTextAge.setError("age is required!");
            eidtTextAge.requestFocus();
            return;
        }
        if (email.isEmpty()){
            eidtTextEmail.setError("email is required!");
            eidtTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eidtTextEmail.setError("LAM ON NHAP CAN THAN");
            eidtTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            eidtTextPassword.setError("Password id required!");
            eidtTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            eidtTextPassword.setError("mat khau phai dai hon 6 ky tu");
            eidtTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullName,age,email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "DANG KY THANH CONG", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);

                                            }else {
                                                Toast.makeText(RegisterUser.this, "DANG KY THAT BAI", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }
                        else{
                            Toast.makeText(RegisterUser.this, "DANG KY KHONG THANH CONG", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

    }
}